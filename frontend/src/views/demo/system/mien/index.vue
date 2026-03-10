<template>
  <div>
    <!-- ─── Error banner ──────────────────────────────────────────────────── -->
    <div
      v-if="loadError"
      style="
        margin-bottom: 12px;
        padding: 12px;
        border: 1px solid #ffa39e;
        border-radius: 6px;
        background: #fff1f0;
        color: #a8071a;
      "
    >
      <strong>Lỗi tải dữ liệu:</strong> {{ loadError }}
      <pre style="max-height: 200px; margin-top: 8px; overflow: auto; white-space: pre-wrap">{{
        lastResultsDebug
      }}</pre>
    </div>

    <!-- ─── Debug panel ───────────────────────────────────────────────────── -->
    <Alert
      v-if="showDebug && !loadError"
      type="info"
      show-icon
      style="margin-bottom: 12px"
      :message="'Debug: ' + (debugNote || 'no note')"
    >
      <template #description>
        <pre style="max-height: 200px; overflow: auto; white-space: pre-wrap">{{
          lastResultsDebug
        }}</pre>
      </template>
    </Alert>

    <!-- ─── Table ─────────────────────────────────────────────────────────── -->
    <BasicTable @register="registerTable">
      <template #toolbar>
        <Button type="primary" @click="handleCreate">
          <template #icon><PlusOutlined /></template>
          Tạo mới
        </Button>
        <Button @click="handleRefresh">
          <template #icon><ReloadOutlined /></template>
          Làm mới
        </Button>
        <Button style="margin-left: 8px" @click="toggleDebug">
          {{ showDebug ? 'Ẩn debug' : 'Hiện debug' }}
        </Button>
      </template>

      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'action'">
          <TableAction
            :actions="[
              {
                icon: 'ant-design:delete-outlined',
                color: 'error',
                tooltip: 'Xóa',
                popConfirm: {
                  title: 'Bạn có chắc chắn muốn xóa tên miền này?',
                  placement: 'left',
                  confirm: () => handleDelete(record as MienDto),
                },
              },
            ]"
          />
        </template>
      </template>

      <!-- ─── Expanded row ─────────────────────────────────────────────── -->
      <template #expandedRowRender="{ record }">
        <div class="p-4">
          <!-- Thông tin cơ sở -->
          <Descriptions title="Thông tin cơ sở" :column="3" bordered size="small">
            <DescriptionsItem label="Mã cơ sở">
              {{ record.coSo?.ma || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Tên cơ sở">
              {{ record.coSo?.ten || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Số điện thoại">
              {{ record.coSo?.sdt || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Địa chỉ văn phòng" :span="2">
              {{ record.coSo?.dcVanPhong || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Địa chỉ kho">
              {{ record.coSo?.dcKho || '-' }}
            </DescriptionsItem>
          </Descriptions>

          <Divider />

          <!-- Hình ảnh -->
          <div v-if="record.tepTin?.duongDan">
            <strong>Hình ảnh / Logo:</strong>
            <div style="margin-top: 8px">
              <Image
                :width="120"
                :src="record.tepTin.duongDan"
                :preview="true"
                style=" border: 1px solid #f0f0f0;border-radius: 6px"
              />
            </div>
          </div>

          <Divider v-if="record.tepTin?.duongDan" />

          <!-- Thông tin tên miền liên hệ -->
          <div>
            <strong style="font-size: 14px">Thông tin liên hệ của tên miền</strong>
            <template
              v-if="record.thongTinTenMiens && record.thongTinTenMiens.length > 0"
            >
              <div
                v-for="(ttm, idx) in record.thongTinTenMiens"
                :key="ttm.id"
                style="
                  margin-top: 12px;
                  padding: 12px;
                  border: 1px solid #e8e8e8;
                  border-radius: 8px;
                  background: #fafafa;
                "
              >
                <div
                  style="
                    display: flex;
                    align-items: center;
                    justify-content: space-between;
                    margin-bottom: 8px;
                  "
                >
                  <span style=" color: #262626;font-weight: 600">Liên hệ #{{ idx + 1 }}</span>
                  <Tag :color="ttm.trangThai === 1 ? 'success' : 'error'">
                    {{ ttm.trangThai === 1 ? 'Kích hoạt' : 'Vô hiệu' }}
                  </Tag>
                </div>
                <Descriptions :column="3" size="small">
                  <DescriptionsItem label="Số điện thoại">
                    {{ ttm.sdt || '-' }}
                  </DescriptionsItem>
                  <DescriptionsItem label="Email">
                    {{ ttm.email || '-' }}
                  </DescriptionsItem>
                  <DescriptionsItem label="Ngày tạo">
                    {{ ttm.taoLuc ? new Date(ttm.taoLuc).toLocaleString('vi-VN') : '-' }}
                  </DescriptionsItem>
                </Descriptions>
              </div>
            </template>
            <template v-else>
              <div style="margin-top: 8px; color: #999; font-style: italic">
                Chưa có thông tin liên hệ
              </div>
            </template>
          </div>
        </div>
      </template>
    </BasicTable>

    <!-- ─── Create modal ──────────────────────────────────────────────────── -->
    <MienModal
      @register="registerModal"
      @success="handleSuccess"
      :coSoOptions="coSoOptions"
    />
  </div>
</template>

<script lang="ts" setup>
  import { onMounted, onBeforeUnmount, ref, onErrorCaptured, computed } from 'vue';
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { PlusOutlined, ReloadOutlined } from '@ant-design/icons-vue';
  import { useModal } from '@/components/Modal';
  import { columns, searchFormSchema } from './mien.data';
  import { filterMien, deleteMien, getAllCoSo } from './mien';
  import type { MienDto } from './mien';
  import MienModal from './MienModal.vue';
  import {
    Alert,
    Button,
    Descriptions,
    DescriptionsItem,
    Divider,
    Image,
    Tag,
    message,
  } from 'ant-design-vue';

  defineOptions({ name: 'MienManagement' });

  // ─── State ────────────────────────────────────────────────────────────────
  const coSoOptions = ref<any[]>([]);
  const loadError = ref<string | null>(null);
  const lastResults = ref<any>(null);
  const showDebug = ref(false);
  const debugNote = ref<string | null>(null);

  const lastResultsDebug = computed(() => {
    try {
      return JSON.stringify(lastResults.value, null, 2);
    } catch (e) {
      return String(lastResults.value);
    }
  });

  // ─── Modal ────────────────────────────────────────────────────────────────
  const [registerModal, { openModal }] = useModal();

  // ─── Table ────────────────────────────────────────────────────────────────
  const [registerTable, { reload, getForm }] = useTable({
    title: 'Danh sách tên miền',
    api: async (params) => {
      try {
        const response = await filterMien(params);
        if (!response?.data?.content) {
          console.warn('filterMien returned unexpected shape', response);
          message.warn('API trả về dữ liệu không đúng, kiểm tra console');
          return { items: [], total: 0 };
        }
        return {
          items: response.data.content,
          total: response.data.totalElements ?? response.data.content.length ?? 0,
        };
      } catch (error: any) {
        console.error('Error fetching data (filterMien):', error);
        message.error(`Lỗi tải dữ liệu: ${error?.message ?? String(error)}`);
        return { items: [], total: 0 };
      }
    },
    columns,
    formConfig: {
      labelWidth: 120,
      schemas: searchFormSchema,
      autoSubmitOnEnter: true,
      submitFunc: async () => { await reload(); },
      resetFunc: async () => { await reload(); },
    },
    useSearchForm: true,
    showTableSetting: true,
    bordered: true,
    showIndexColumn: true,
    rowKey: 'id',
    pagination: {
      pageSize: 20,
      showSizeChanger: true,
      pageSizeOptions: ['10', '20', '50', '100'],
      showQuickJumper: true,
      showTotal: (total) => `Tổng ${total} bản ghi`,
    },
    expandRowByClick: false,
    canResize: true,
    actionColumn: {
      width: 80,
      title: 'Thao tác',
      dataIndex: 'action',
      fixed: 'right',
    },
  });

  // ─── Helper ───────────────────────────────────────────────────────────────
  function resolveList(res: any) {
    if (!res) return [];
    const payload = res.data ?? res;
    if (Array.isArray(payload)) return payload;
    if (payload && Array.isArray(payload.content)) return payload.content;
    return [];
  }

  // ─── Load options ─────────────────────────────────────────────────────────
  async function loadOptions() {
    loadError.value = null;
    lastResults.value = null;
    debugNote.value = 'start loadOptions';

    try {
      const settled = await Promise.allSettled([getAllCoSo()]);
      lastResults.value = settled;
      debugNote.value = 'after allSettled';

      const [coSoRes] = settled;

      if (coSoRes.status === 'fulfilled') {
        const list = resolveList(coSoRes.value);
        coSoOptions.value = list.map((item: any) => ({
          label: item.ten ?? item.ma ?? String(item.id),
          value: item.id,
        }));
      } else {
        console.error('getAllCoSo failed', coSoRes.reason);
        loadError.value = 'Không tải được danh sách cơ sở: ' + String(coSoRes.reason);
      }

      // Cập nhật search form options
      try {
        const form = getForm();
        if (form && typeof form.updateSchema === 'function') {
          form.updateSchema([
            { field: 'coSoId', componentProps: { options: coSoOptions.value } },
          ]);
        }
      } catch (err) {
        console.error('Failed updateSchema on form', err);
        lastResults.value = { ...lastResults.value, updateSchemaError: String(err) };
      }

      if (!loadError.value) {
        debugNote.value = 'loaded ok';
      }
    } catch (err) {
      console.error('Unexpected error in loadOptions:', err);
      loadError.value = 'Lỗi nội bộ khi tải dữ liệu danh mục, xem console.';
      lastResults.value = err;
    }
  }

  // ─── Handlers ─────────────────────────────────────────────────────────────
  function handleCreate() {
    try {
      openModal(true, { coSoOptions: coSoOptions.value });
    } catch (err) {
      console.error('handleCreate error:', err);
      message.error('Không thể mở modal: ' + String(err));
    }
  }

  async function handleDelete(record: MienDto) {
    try {
      const result = await deleteMien(record.id);
      if (result && result.status === 200) {
        message.success('Xóa thành công');
        reload();
      } else {
        console.error('deleteMien returned unexpected', result);
        message.error((result as any)?.message || 'Có lỗi xảy ra khi xóa');
      }
    } catch (error) {
      console.error('Delete error:', error);
      message.error('Có lỗi xảy ra khi xóa');
    }
  }

  function handleRefresh() {
    reload();
  }

  function handleSuccess() {
    reload();
  }

  function toggleDebug() {
    showDebug.value = !showDebug.value;
  }

  // ─── Global error capture ─────────────────────────────────────────────────
  function onWindowError(e: ErrorEvent) {
    console.error('Window error event:', e);
    loadError.value = e.message || String(e);
    message.error('Có lỗi trang (xem console): ' + (e.message || 'unknown'));
  }

  function onUnhandledRejection(e: PromiseRejectionEvent) {
    console.error('Unhandled promise rejection:', e.reason);
    loadError.value = String(e.reason ?? 'Unhandled rejection');
    message.error('Promise bị từ chối (xem console)');
  }

  onErrorCaptured((err, _instance, info) => {
    console.error('onErrorCaptured:', { err, info });
    loadError.value = String((err as any)?.message ?? err);
    lastResults.value = { vueError: String(err), info };
    return false;
  });

  onMounted(() => {
    window.addEventListener('error', onWindowError);
    window.addEventListener('unhandledrejection', onUnhandledRejection);
    loadOptions();
  });

  onBeforeUnmount(() => {
    window.removeEventListener('error', onWindowError);
    window.removeEventListener('unhandledrejection', onUnhandledRejection);
  });
</script>

<style lang="less" scoped>
</style>
