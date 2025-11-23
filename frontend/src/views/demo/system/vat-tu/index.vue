<template>
  <div>
    <!-- Debug / Error banner -->
    <div v-if="loadError" style="margin-bottom:12px;padding:12px;border:1px solid #ffa39e;border-radius:6px;background:#fff1f0;color:#a8071a">
      <strong>Lỗi tải dữ liệu:</strong> {{ loadError }}
      <pre style="max-height:200px;margin-top:8px;overflow:auto;white-space:pre-wrap;">{{ lastResultsDebug }}</pre>
    </div>

    <a-alert
      v-if="showDebug && !loadError"
      type="info"
      show-icon
      style="margin-bottom:12px"
      :message="'Debug: ' + (debugNote || 'no note')"
    >
      <template #description>
        <pre style="max-height:200px;overflow:auto;white-space:pre-wrap;">{{ lastResultsDebug }}</pre>
      </template>
    </a-alert>

    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button type="primary" @click="handleCreate">
          <template #icon>
            <PlusOutlined />
          </template>
          Tạo mới
        </a-button>
        <a-button @click="handleRefresh">
          <template #icon>
            <ReloadOutlined />
          </template>
          Làm mới
        </a-button>
        <a-button style="margin-left:8px" @click="toggleDebug">
          {{ showDebug ? 'Hide debug' : 'Show debug' }}
        </a-button>
      </template>
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'action'">
          <TableAction
            :actions="[
              {
                icon: 'clarity:note-edit-line',
                tooltip: 'Chỉnh sửa',
                onClick: () => handleEdit(record as VatTuDto),
              },
              {
                icon: 'ant-design:delete-outlined',
                color: 'error',
                tooltip: 'Xóa',
                popConfirm: {
                  title: 'Bạn có chắc chắn muốn xóa?',
                  placement: 'left',
                  confirm: () => handleDelete(record as VatTuDto),
                },
              },
            ]"
          />
        </template>
      </template>
      <template #expandedRowRender="{ record }">
        <div class="p-4">
          <a-descriptions title="Dữ liệu riêng" :column="2" bordered size="small">
            <template v-if="record.duLieuRieng && Object.keys(record.duLieuRieng).length > 0">
              <a-descriptions-item
                v-for="(value, key) in record.duLieuRieng"
                :key="key"
                :label="value.ten"
              >
                {{ value.giaTri }} {{ value.donVi ? `(${value.donVi})` : '' }}
              </a-descriptions-item>
            </template>
            <template v-else>
              <a-descriptions-item label="Thông tin">
                Không có dữ liệu riêng
              </a-descriptions-item>
            </template>
          </a-descriptions>

          <a-divider />

          <a-descriptions title="Thông tin nhóm vật tư" :column="2" bordered size="small">
            <a-descriptions-item label="Mã nhóm">
              {{ record.nhomVatTu?.ma || '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="Tên nhóm">
              {{ record.nhomVatTu?.ten || '-' }}
            </a-descriptions-item>
          </a-descriptions>

          <a-divider />

          <a-descriptions title="Thương hiệu & Nhà cung cấp" :column="2" bordered size="small">
            <a-descriptions-item label="Thương hiệu">
              {{ record.thuongHieu?.ten || '-' }}
            </a-descriptions-item>
          </a-descriptions>

          <a-divider />

          <a-descriptions title="Thông tin giá" :column="1" bordered size="small">
            <template v-if="record.thongTinGias && record.thongTinGias.length > 0">
              <a-descriptions-item label="Danh sách giá">
                <div v-for="(gia, idx) in record.thongTinGias[0]?.dsGia" :key="idx" style="margin-bottom: 8px">
                  <strong>{{ gia.tenCoSo }}:</strong> 
                  Giá nhập: {{ formatCurrency(gia.giaNhap) }} | 
                  Giá bán: {{ formatCurrency(gia.giaBan) }}
                </div>
              </a-descriptions-item>
            </template>
            <template v-else>
              <a-descriptions-item label="Thông tin">
                Chưa có thông tin giá
              </a-descriptions-item>
            </template>
          </a-descriptions>

          <a-divider v-if="record.anhVatTus && record.anhVatTus.length > 0" />

          <div v-if="record.anhVatTus && record.anhVatTus.length > 0">
            <strong>Hình ảnh:</strong>
            <div style="display: flex; flex-wrap: wrap; margin-top: 8px; gap: 8px">
              <a-image
                v-for="anh in record.anhVatTus"
                :key="anh.id"
                :width="100"
                :src="anh.tepTin?.url || anh.tepTin?.duongDan || ''"
                :preview="true"
              />
            </div>
          </div>

          <a-divider v-if="record.sheetLink" />

          <div v-if="record.sheetLink">
            <strong>Sheet Link:</strong>
            <div style="margin-top: 8px">
              <a :href="record.sheetLink" target="_blank" rel="noopener noreferrer">
                {{ record.sheetLink }}
              </a>
            </div>
          </div>
        </div>
      </template>
    </BasicTable>
    <VatTuModal
      @register="registerModal"
      @success="handleSuccess"
      :nhomVatTuOptions="nhomVatTuOptions"
      :thuongHieuOptions="thuongHieuOptions"
    />
    <UpdateVatTuModal
      @register="registerUpdateModal"
      @success="handleSuccess"
    />
  </div>
</template>

<script lang="ts" setup>
  import { onMounted, onBeforeUnmount, ref, onErrorCaptured } from 'vue';
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { PlusOutlined, ReloadOutlined } from '@ant-design/icons-vue';
  import { useModal } from '@/components/Modal';
  import { columns, searchFormSchema } from './vatTu.data';
  import { filterVatTu, deleteVatTu, getAllNhomVatTu, getAllThuongHieu } from './vatTu';
  import type { VatTuDto } from './vatTu';
  import VatTuModal from './VatTuModal.vue';
  import UpdateVatTuModal from './UpdateVatTuModal.vue';
  import { message } from 'ant-design-vue';
  import { computed } from 'vue';

  defineOptions({ name: 'VatTuManagement' });

  const nhomVatTuOptions = ref<any[]>([]);
  const thuongHieuOptions = ref<any[]>([]);

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

  const [registerModal, { openModal }] = useModal();
  const [registerUpdateModal, { openModal: openUpdateModal }] = useModal();
  const [registerTable, { reload, getForm }] = useTable({
    title: 'Danh sách vật tư',
    api: async (params) => {
      try {
        const response = await filterVatTu(params);
        if (!response || !response.data || !response.data.content) {
          console.warn('filterVatTu returned unexpected shape', response);
          message.warn('API trả về dữ liệu không đúng, kiểm tra console');
          return { items: [], total: 0 };
        }
        return {
          items: response.data.content,
          total: response.data.totalElements ?? response.data.content.length ?? 0,
        };
      } catch (error: any) {
        console.error('Error fetching data (filterVatTu):', error);
        message.error(`Error fetching data: ${error?.message ?? String(error)}`);
        return {
          items: [],
          total: 0,
        };
      }
    },
    columns,
    formConfig: {
      labelWidth: 120,
      schemas: searchFormSchema,
      autoSubmitOnEnter: true,
      submitFunc: async () => {
        await reload();
      },
      resetFunc: async () => {
        await reload();
      },
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
      width: 120,
      title: 'Thao tác',
      dataIndex: 'action',
      fixed: 'right',
    },
  });

  // helper to safely extract array payload
  function resolveList(res: any) {
    if (!res) return [];
    const payload = res.data ?? res;
    if (Array.isArray(payload)) return payload;
    if (payload && Array.isArray(payload.content)) return payload.content;
    return [];
  }

  async function loadOptions() {
    loadError.value = null;
    lastResults.value = null;
    debugNote.value = 'start loadOptions';
    try {
      const settled = await Promise.allSettled([
        getAllNhomVatTu(),
        getAllThuongHieu(),
      ]);
      lastResults.value = settled;
      debugNote.value = 'after allSettled';

      const [nhomVatTuRes, thuongHieuRes] = settled;

      if (nhomVatTuRes.status === 'fulfilled') {
        const list = resolveList(nhomVatTuRes.value);
        nhomVatTuOptions.value = list.map((item: any) => ({
          label: item.ten ?? item.ma ?? String(item.id),
          value: item.id,
          thuocTinhRieng: item.thuocTinhRieng,
        }));
      } else {
        console.error('getAllNhomVatTu failed', nhomVatTuRes.reason);
        loadError.value = 'Không tải được danh sách nhóm vật tư: ' + String(nhomVatTuRes.reason);
      }

      if (thuongHieuRes.status === 'fulfilled') {
        const list = resolveList(thuongHieuRes.value);
        thuongHieuOptions.value = list.map((item: any) => ({
          label: item.ten ?? item.tenQuocTe ?? String(item.id),
          value: item.id,
        }));
      } else {
        console.error('getAllThuongHieu failed', thuongHieuRes.reason);
        // do not set fatal loadError for optional lists
      }

      // update search form options (defensive)
      try {
        const form = getForm();
        if (form && typeof form.updateSchema === 'function') {
          form.updateSchema([
            { field: 'nhomVatTuId', componentProps: { options: nhomVatTuOptions.value } },
            { field: 'thuongHieuId', componentProps: { options: thuongHieuOptions.value } },
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

  function formatCurrency(value: number) {
    if (!value && value !== 0) return '0đ';
    try {
      return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value);
    } catch (err) {
      console.error('formatCurrency error', err);
      return String(value);
    }
  }

  function handleCreate() {
    try {
      openModal(true, {
        isUpdate: false,
        nhomVatTuOptions: nhomVatTuOptions.value,
        thuongHieuOptions: thuongHieuOptions.value,
      });
    } catch (err) {
      console.error('handleCreate error:', err);
      message.error('Không thể mở modal: ' + String(err));
    }
  }

  function handleEdit(record: VatTuDto) {
    try {
      openUpdateModal(true, {
        record,
      });
    } catch (err) {
      console.error('handleEdit error:', err);
      message.error('Không thể mở modal chỉnh sửa: ' + String(err));
    }
  }

  async function handleDelete(record: VatTuDto) {
    try {
      const result = await deleteVatTu(record.id);
      if (result && (result.status === 200)) {
        message.success('Xóa thành công');
        reload();
      } else {
        console.error('deleteVatTu returned unexpected', result);
        message.error(result?.message || 'Có lỗi xảy ra khi xóa');
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

  // Global page-level error logging to help debug runtime exceptions on this page
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

  onErrorCaptured((err, instance, info) => {
    console.error('onErrorCaptured:', { err, info, instance });
    loadError.value = String(err?.message ?? err);
    lastResults.value = { vueError: String(err), info };
    // return false to stop propagation (we still want global handlers)
    return false;
  });

  function toggleDebug() {
    showDebug.value = !showDebug.value;
  }

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
  :deep(.ant-descriptions-item-label) {
    background-color: #fafafa;
    font-weight: 600;
  }
</style>