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
                onClick: () => handleEdit(record),
              },
              {
                icon: 'ant-design:delete-outlined',
                color: 'error',
                tooltip: 'Xóa',
                onClick: () => handleDelete(record),
              },
            ]"
          />
        </template>
      </template>
      <template #expandedRowRender="{ record }">
        <div class="p-4">
          <Descriptions title="Thông tin cá nhân" :column="2" bordered size="small">
            <DescriptionsItem label="Họ và tên">
              {{ record.hoVaTen || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Email">
              {{ record.email || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Số điện thoại">
              {{ record.sdt || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Giới tính">
              {{ record.gioiTinh === true ? 'Nam' : 'Nữ' }}
            </DescriptionsItem>
            <DescriptionsItem label="Sinh nhật">
              {{ record.sinhNhat ? new Date(record.sinhNhat).toLocaleDateString('vi-VN') : '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Địa chỉ">
              {{ record.diaChi || '-' }}
            </DescriptionsItem>
          </Descriptions>

          <Divider />

          <Descriptions title="Thông tin công việc" :column="2" bordered size="small">
            <DescriptionsItem label="Phân quyền">
              <Tag v-if="record.phanQuyen === 'ADMIN'" color="red">Quản trị</Tag>
              <Tag v-else-if="record.phanQuyen === 'MANAGER'" color="orange">Quản lý</Tag>
              <Tag v-else color="blue">Người dùng</Tag>
            </DescriptionsItem>
            <DescriptionsItem label="Cơ sở">
              {{ record.coSo?.ten || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Phần trăm hoa hồng">
              {{ record.phanTramHoaHong ? `${record.phanTramHoaHong}%` : '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Tổng hoa hồng">
              {{ record.tongHoaHong ? formatCurrency(record.tongHoaHong) : '0 đ' }}
            </DescriptionsItem>
            <DescriptionsItem label="Ngân hàng">
              {{ record.nganHang || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Mã ngân hàng">
              {{ record.maNganHang || '-' }}
            </DescriptionsItem>
          </Descriptions>

          <div v-if="record.khachHangs && record.khachHangs.length > 0">
            <Divider />
            <h4>Danh sách khách hàng ({{ record.khachHangs.length }})</h4>
            <Table
              :columns="khachHangColumns"
              :data-source="record.khachHangs"
              :pagination="false"
              size="small"
              :scroll="{ x: 800 }"
            ></Table>
          </div>
        </div>
      </template>
    </BasicTable>
  </div>
</template>

<script lang="ts" setup>
  import { onMounted, onBeforeUnmount, ref, onErrorCaptured } from 'vue';
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { PlusOutlined, ReloadOutlined } from '@ant-design/icons-vue';
  import { columns, searchFormSchema } from './nguoiDung.data';
  import { filterNguoiDung } from './nguoiDung';
  import type { NguoiDungDto } from './nguoiDung';
  import { Button, Descriptions, DescriptionsItem, Divider, message, Table, Tag } from 'ant-design-vue';

  defineOptions({ name: 'NguoiDungManagement' });

  const loadError = ref<string | null>(null);

  const khachHangColumns = [
    { title: 'Họ và tên', dataIndex: 'hoVaTen', width: 150 },
    { title: 'Email', dataIndex: 'email', width: 180 },
    { title: 'Số điện thoại', dataIndex: 'sdt', width: 120 },
    { 
      title: 'Giới tính', 
      dataIndex: 'gioiTinh', 
      width: 80,
      customRender: ({ record }: any) => record.gioiTinh === true ? 'Nam' : 'Nữ'
    },
    { title: 'Địa chỉ', dataIndex: 'diaChi', width: 200 },
    {
      title: 'Đã bán được hàng',
      dataIndex: 'daBanDuocHang',
      width: 130,
      customRender: ({ record }: any) => record.daBanDuocHang ? 'Có' : 'Chưa'
    },
  ];

  const [registerTable, { reload }] = useTable({
    title: 'Danh sách người dùng',
    api: async (params) => {
      try {
        const response = await filterNguoiDung(params);
        if (!response || !response.data || !response.data.content) {
          console.warn('filterNguoiDung returned unexpected shape', response);
          message.warn('API trả về dữ liệu không đúng, kiểm tra console');
          return { items: [], total: 0 };
        }
        return {
          items: response.data.content,
          total: response.data.totalElements ?? response.data.content.length ?? 0,
        };
      } catch (error: any) {
        console.error('Error fetching data (filterNguoiDung):', error);
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

  function formatCurrency(value: number) {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND'
    }).format(value);
  }

  function handleCreate() {
    message.info('Chức năng đang phát triển');
  }

  function handleEdit(record: NguoiDungDto) {
    message.info('Chức năng đang phát triển');
  }

  function handleDelete(record: NguoiDungDto) {
    message.info('Chức năng đang phát triển');
  }

  function handleRefresh() {
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
  });

  onBeforeUnmount(() => {
    window.removeEventListener('error', onWindowError);
    window.removeEventListener('unhandledrejection', onUnhandledRejection);
  });
</script>

<style lang="less" scoped>
</style>