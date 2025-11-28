<template>
  <div>
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
      </template>
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'action'">
          <TableAction
            :actions="[
              {
                icon: 'clarity:note-edit-line',
                tooltip: 'Chỉnh sửa',
                onClick: () => handleEdit(record as NhomVatTuDto),
              },
              {
                icon: 'ant-design:delete-outlined',
                color: 'error',
                tooltip: 'Xóa',
                popConfirm: {
                  title: 'Bạn có chắc chắn muốn xóa?',
                  placement: 'left',
                  confirm: () => handleDelete(record as NhomVatTuDto),
                },
              },
            ]"
          />
        </template>
      </template>
      <template #expandedRowRender="{ record }">
        <div class="p-4">
          <Descriptions title="Thuộc tính riêng" :column="2" bordered size="small">
            <template v-if="record.thuocTinhRieng && Object.keys(record.thuocTinhRieng).length > 0">
              <DescriptionsItem
                v-for="(value, key) in record.thuocTinhRieng"
                :key="key"
                :label="value.ten"
              >
                {{ value.giaTri }} {{ value.donVi ? `(${value.donVi})` : '' }}
              </DescriptionsItem>
            </template>
            <template v-else>
              <DescriptionsItem label="Thông tin">
                Không có thuộc tính riêng
              </DescriptionsItem>
            </template>
          </Descriptions>

          <Divider />

          <Descriptions title="Thông tin ngành hàng" :column="2" bordered size="small">
            <DescriptionsItem label="Mã ngành hàng">
              {{ record.nghanhHang?.ma || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Tên ngành hàng">
              {{ record.nghanhHang?.ten || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="SĐT Sale">
              {{ record.nghanhHang?.sdtSale || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="SĐT Tech">
              {{ record.nghanhHang?.sdtTech || '-' }}
            </DescriptionsItem>
          </Descriptions>
        </div>
      </template>
    </BasicTable>
    <NhomVatTuModal
      @register="registerModal"
      @success="handleSuccess"
      :nghanhHangOptions="nghanhHangOptions"
    />
  </div>
</template>

<script lang="ts" setup>
  import { onMounted, ref } from 'vue';
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { PlusOutlined, ReloadOutlined } from '@ant-design/icons-vue';
  import { useModal } from '@/components/Modal';
  import { columns, searchFormSchema } from './nhomVatTu.data';
  import { filterNhomVatTu, deleteNhomVatTu, getAllNganhHang } from './nhomVatTu';
  import type { ResponseData, PageResponse, NhomVatTuDto } from './nhomVatTu';
  import NhomVatTuModal from './NhomVatTuModal.vue';
  import { Button, Descriptions, DescriptionsItem, Divider, message } from 'ant-design-vue';

  defineOptions({ name: 'NhomVatTuManagement' });

  const nghanhHangOptions = ref<any[]>([]);

  const [registerModal, { openModal }] = useModal();
  const [registerTable, { reload, getForm }] = useTable({
    title: 'Danh sách nhóm vật tư',
    api: async (params) => {
      try {
        const response = await filterNhomVatTu(params);

        return {
          items: response.data.content,
          total: response.data.totalElements,
        };
      } catch (error) {
        console.error('Error fetching data:', error);
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

  // Load danh sách ngành hàng và cập nhật vào search form
  async function loadNghanhHangOptions() {
    try {
      const res = await getAllNganhHang();
      const list = res?.data ?? res;
      const rawData = Array.isArray(list) ? list : [];

      // Format thành options cho Select component
      nghanhHangOptions.value = rawData.map((item: any) => ({
        label: item.ten,
        value: item.id,
      }));

      console.log('Loaded nghanh hang options:', nghanhHangOptions.value);

      // Cập nhật options cho search form
      const form = getForm();
      if (form) {
        form.updateSchema({
          field: 'nghanhHangId',
          componentProps: {
            options: nghanhHangOptions.value,
          },
        });
      }
    } catch (err) {
      console.error('Failed to load nghanh hang:', err);
      message.error('Không thể tải danh sách ngành hàng');
    }
  }

  function handleCreate() {
    openModal(true, {
      isUpdate: false,
    });
  }

  function handleEdit(record: NhomVatTuDto) {
    openModal(true, {
      record,
      isUpdate: true,
    });
  }

  async function handleDelete(record: NhomVatTuDto) {
    try {
      const result = await deleteNhomVatTu(record.id);
      if (result.status === 200) {
        message.success('Xóa thành công');
        reload();
      } else {
        message.error(result.message || 'Có lỗi xảy ra');
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

  onMounted(() => {
    loadNghanhHangOptions();
  });
</script>

<style lang="less" scoped>
</style>
