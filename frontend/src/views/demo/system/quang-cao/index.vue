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
    </div>

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
          <Descriptions title="Thông tin chi tiết" :column="2" bordered size="small">
            <DescriptionsItem label="Tiêu đề">
              {{ record.tieuDe || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Vị trí">
              {{ record.viTri || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Ngành hàng">
              {{ record.nganhHang?.ten || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Mã ngành hàng">
              {{ record.nganhHang?.ma || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="SĐT Sale">
              {{ record.nganhHang?.sdtSale || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="SĐT Tech">
              {{ record.nganhHang?.sdtTech || '-' }}
            </DescriptionsItem>
          </Descriptions>

          <Divider />

          <div v-if="record.tepTin?.duongDan">
            <strong>Ảnh quảng cáo:</strong>
            <div style="margin-top: 8px">
              <Image
                :width="200"
                :src="record.tepTin.duongDan"
                :preview="true"
              />
            </div>
          </div>
        </div>
      </template>
    </BasicTable>
    <QuangCaoModal
      @register="registerModal"
      @success="handleSuccess"
      :nganhHangOptions="nganhHangOptions"
    />
  </div>
</template>

<script lang="ts" setup>
  import { onMounted, onBeforeUnmount, ref, onErrorCaptured } from 'vue';
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { PlusOutlined, ReloadOutlined } from '@ant-design/icons-vue';
  import { useModal } from '@/components/Modal';
  import { columns, searchFormSchema } from './quangCao.data';
  import { filterQuangCao, getAllNganhHang, deleteQuangCao } from './quangCao';
  import type { QuangCaoDto } from './quangCao';
  import QuangCaoModal from './QuangCaoModal.vue';
  import { Descriptions, DescriptionsItem, Divider, Image, message } from 'ant-design-vue';

  defineOptions({ name: 'QuangCaoManagement' });

  const nganhHangOptions = ref<any[]>([]);
  const loadError = ref<string | null>(null);

  const [registerModal, { openModal }] = useModal();
  const [registerTable, { reload, getForm }] = useTable({
    title: 'Danh sách quảng cáo',
    api: async (params) => {
      try {
        const response = await filterQuangCao(params);
        if (!response || !response.data || !response.data.content) {
          console.warn('filterQuangCao returned unexpected shape', response);
          message.warn('API trả về dữ liệu không đúng, kiểm tra console');
          return { items: [], total: 0 };
        }
        return {
          items: response.data.content,
          total: response.data.totalElements ?? response.data.content.length ?? 0,
        };
      } catch (error: any) {
        console.error('Error fetching data (filterQuangCao):', error);
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
    try {
      const nganhHangRes = await getAllNganhHang();
      const list = resolveList(nganhHangRes);
      nganhHangOptions.value = list.map((item: any) => ({
        label: item.ten ?? item.ma ?? String(item.id),
        value: item.id,
      }));

      try {
        const form = getForm();
        if (form && typeof form.updateSchema === 'function') {
          form.updateSchema([
            { field: 'nganhHangId', componentProps: { options: nganhHangOptions.value } },
          ]);
        }
      } catch (err) {
        console.error('Failed updateSchema on form', err);
      }
    } catch (err) {
      console.error('Unexpected error in loadOptions:', err);
      loadError.value = 'Lỗi nội bộ khi tải dữ liệu danh mục, xem console.';
    }
  }

  function handleCreate() {
    try {
      openModal(true, {
        isUpdate: false,
        nganhHangOptions: nganhHangOptions.value,
      });
    } catch (err) {
      console.error('handleCreate error:', err);
      message.error('Không thể mở modal: ' + String(err));
    }
  }

  function handleEdit(record: QuangCaoDto) {
    try {
      openModal(true, {
        record,
        isUpdate: true,
        nganhHangOptions: nganhHangOptions.value,
      });
    } catch (err) {
      console.error('handleEdit error:', err);
      message.error('Không thể mở modal chỉnh sửa: ' + String(err));
    }
  }

  async function handleDelete(record: QuangCaoDto) {
    try {
      const result = await deleteQuangCao(record.id);
      if (result && result.status === 200) {
        message.success('Xóa thành công');
        reload();
      } else {
        console.error('deleteQuangCao returned unexpected', result);
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