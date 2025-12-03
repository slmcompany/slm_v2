<template>
  <div>
    <!-- Debug / Error banner -->
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

    <BasicTable @register="registerTable">
      <template #toolbar>
        <Button type="primary" @click="handleCreate">
          <template #icon>
            <PlusOutlined />
          </template>
          Tạo mới
        </Button>
        <Button @click="handleRefresh">
          <template #icon>
            <ReloadOutlined />
          </template>
          Làm mới
        </Button>
        <Button style="margin-left: 8px" @click="toggleDebug">
          {{ showDebug ? 'Hide debug' : 'Show debug' }}
        </Button>
      </template>
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'action'">
          <TableAction
            :actions="[
              {
                icon: 'clarity:note-edit-line',
                tooltip: 'Chỉnh sửa',
                onClick: () => handleEdit(record),
              },
              {
                icon: 'ant-design:delete-outlined',
                color: 'error',
                tooltip: 'Xóa',
                popConfirm: {
                  title: 'Bạn có chắc chắn muốn xóa?',
                  placement: 'left',
                  confirm: () => handleDelete(record),
                },
              },
            ]"
          />
        </template>
      </template>
      <template #expandedRowRender="{ record }">
        <div class="p-4">
          <Descriptions title="Thông tin cơ bản" :column="2" bordered size="small">
            <DescriptionsItem label="Nhóm trọn gói">
              {{ record.nhomTronGoi?.ten || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Cơ sở">
              {{ record.coSo?.ten || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Loại hệ thống">
              {{ record.loaiHeThong || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Loại pha">
              {{ record.loaiPha || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Sản lượng">
              {{ record.sanLuongToiThieu || 0 }} - {{ record.sanLuongToiDa || 0 }} kW
            </DescriptionsItem>
            <DescriptionsItem label="Tổng giá">
              {{ formatCurrency(record.tongGia) }}
            </DescriptionsItem>
            <DescriptionsItem label="Mô tả" :span="2">
              {{ record.moTa || '-' }}
            </DescriptionsItem>
          </Descriptions>

          <Divider />

          <Descriptions title="Danh sách vật tư" :column="1" bordered size="small">
            <template v-if="record.vatTuTronGois && record.vatTuTronGois.length > 0">
              <DescriptionsItem label="Vật tư">
                <Table
                  :columns="vatTuColumns"
                  :data-source="record.vatTuTronGois"
                  :pagination="false"
                  size="small"
                  bordered
                >
                  <template #bodyCell="{ column, record: vatTu }">
                    <template v-if="column.key === 'ten'">
                      {{ vatTu.vatTu?.ten || '-' }}
                    </template>
                    <template v-else-if="column.key === 'soLuong'">
                      {{ vatTu.soLuong || 0 }}
                    </template>
                    <template v-else-if="column.key === 'gia'">
                      {{ formatCurrency(vatTu.gia) }}
                    </template>
                    <template v-else-if="column.key === 'gm'"> {{ vatTu.gm || 0 }}% </template>
                    <template v-else-if="column.key === 'thoiGianBaoHanh'">
                      {{ vatTu.thoiGianBaoHanh || 0 }} tháng
                    </template>
                    <template v-else-if="column.key === 'duocBaoHanh'">
                      <Tag :color="vatTu.duocBaoHanh ? 'success' : 'default'">
                        {{ vatTu.duocBaoHanh ? 'Có' : 'Không' }}
                      </Tag>
                    </template>
                    <template v-else-if="column.key === 'moTa'">
                      {{ vatTu.moTa || '-' }}
                    </template>
                  </template>
                </Table>
              </DescriptionsItem>
            </template>
            <template v-else>
              <DescriptionsItem label="Thông tin"> Chưa có vật tư </DescriptionsItem>
            </template>
          </Descriptions>

          <Divider v-if="record.tepTin?.duongDan" />

          <div v-if="record.tepTin?.duongDan">
            <strong>Hình ảnh:</strong>
            <div style="margin-top: 8px">
              <Image :width="200" :src="record.tepTin.duongDan" :preview="true" />
            </div>
          </div>
        </div>
      </template>
    </BasicTable>

    <!-- Create Modal -->
    <CreateTronGoiModal @register="registerCreateModal" @success="handleSuccess" />

    <!-- Update Modal -->
    <UpdateTronGoiModal @register="registerUpdateModal" @success="handleSuccess" />
  </div>
</template>

<script lang="ts" setup>
  import { onMounted, onBeforeUnmount, ref, computed, onErrorCaptured } from 'vue';
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { PlusOutlined, ReloadOutlined } from '@ant-design/icons-vue';
  import { useModal } from '@/components/Modal';
  import { columns, searchFormSchema } from './tronGoi.data';
  import { filterTronGoi, getAllNhomTronGoi, TronGoiDto, deleteTronGoi } from './tronGoi';
  import CreateTronGoiModal from './CreateTronGoiModal.vue';
  import UpdateTronGoiModal from './UpdateTronGoiModal.vue';
  import { Alert, Button, Descriptions, DescriptionsItem, Divider, Image, message, Table, Tag } from 'ant-design-vue';

  defineOptions({ name: 'TronGoiManagement' });

  const nhomTronGoiOptions = ref<any[]>([]);
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

  const vatTuColumns = [
    {
      title: 'Tên vật tư',
      key: 'ten',
      dataIndex: ['vatTu', 'ten'],
    },
    {
      title: 'Số lượng',
      key: 'soLuong',
      dataIndex: 'soLuong',
      width: 100,
    },
    {
      title: 'Giá',
      key: 'gia',
      dataIndex: 'gia',
      width: 150,
    },
    {
      title: 'GM',
      key: 'gm',
      dataIndex: 'gm',
      width: 80,
    },
    {
      title: 'Bảo hành',
      key: 'thoiGianBaoHanh',
      dataIndex: 'thoiGianBaoHanh',
      width: 120,
    },
    {
      title: 'Được BH',
      key: 'duocBaoHanh',
      dataIndex: 'duocBaoHanh',
      width: 100,
    },
    {
      title: 'Mô tả',
      key: 'moTa',
      dataIndex: 'moTa',
    },
  ];

  const [registerCreateModal, { openModal: openCreateModal }] = useModal();
  const [registerUpdateModal, { openModal: openUpdateModal }] = useModal();

  const [registerTable, { reload, getForm }] = useTable({
    title: 'Danh sách trọn gói',
    api: async (params) => {
      try {
        const response = await filterTronGoi(params);
        if (!response || !response.data || !response.data.content) {
          console.warn('filterTronGoi returned unexpected shape', response);
          message.warn('API trả về dữ liệu không đúng, kiểm tra console');
          return { items: [], total: 0 };
        }
        return {
          items: response.data.content,
          total: response.data.totalElements ?? response.data.content.length ?? 0,
        };
      } catch (error: any) {
        console.error('Error fetching data (filterTronGoi):', error);
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
      const settled = await Promise.allSettled([getAllNhomTronGoi()]);
      lastResults.value = settled;
      debugNote.value = 'after allSettled';

      const [nhomTronGoiRes] = settled;

      if (nhomTronGoiRes.status === 'fulfilled') {
        const list = resolveList(nhomTronGoiRes.value);
        nhomTronGoiOptions.value = list.map((item: any) => ({
          label: item.ten || String(item.id),
          value: item.id,
        }));
      } else {
        console.error('getAllNhomTronGoi failed', nhomTronGoiRes.reason);
        loadError.value =
          'Không tải được danh sách nhóm trọn gói: ' + String(nhomTronGoiRes.reason);
      }

      try {
        const form = getForm();
        if (form && typeof form.updateSchema === 'function') {
          form.updateSchema([
            { field: 'nhomTronGoiId', componentProps: { options: nhomTronGoiOptions.value } },
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
      openCreateModal(true, {});
    } catch (err) {
      console.error('handleCreate error:', err);
      message.error('Không thể mở modal: ' + String(err));
    }
  }

  function handleEdit(record: TronGoiDto) {
    try {
      openUpdateModal(true, {
        record,
      });
    } catch (err) {
      console.error('handleEdit error:', err);
      message.error('Không thể mở modal chỉnh sửa: ' + String(err));
    }
  }

  async function handleDelete(record: TronGoiDto) {
    try {
      const result = await deleteTronGoi(record.id);
      if (result && result.status === 200) {
        message.success('Xóa thành công');
        reload();
      } else {
        message.error(result?.message || 'Có lỗi xảy ra khi xóa');
      }
    } catch (error) {
      console.error('Delete error:', error);
      message.error('Có lỗi xảy ra khi xóa');
    }
  }

  async function handleRefresh() {
    await reload();
    message.success('Đã làm mới dữ liệu');
  }

  async function handleSuccess() {
    await reload();
    message.success('Đã làm mới dữ liệu');
  }

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
</style>
