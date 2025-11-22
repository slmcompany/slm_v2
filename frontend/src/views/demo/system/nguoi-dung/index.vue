<template>
  <div>
    <BasicTable @register="">
      <template #toolbar>
        <a-button type="primary" @click="">
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
                onClick: () => {},
              },
              {
                icon: 'ant-design:delete-outlined',
                color: 'error',
                tooltip: 'Xóa',
                popConfirm: {
                  title: 'Bạn có chắc chắn muốn xóa?',
                  placement: 'left',
                  confirm: () => {},
                },
              },
            ]"
          />
        </template>
      </template>
    </BasicTable>
  </div>
</template>

<script setup lang="ts">
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { PlusOutlined, ReloadOutlined } from '@ant-design/icons-vue';
  import { filterNguoiDung } from './nguoiDung';
  import {columns} from './nguoiDung.data'

  defineOptions({ name: 'NguoiDungManagement' });

  const [registerTable, { reload, getForm }] = useTable({
    title: 'Danh sách nhóm vật tư',
    api: async (params) => {
      try {
        const response = await filterNguoiDung(params);

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
      // schemas: searchFormSchema,
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


  function handleRefresh() {
    reload();
  }
</script>

<style scoped></style>
