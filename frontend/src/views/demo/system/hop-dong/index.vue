<template>
  <div>
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
            <DescriptionsItem label="Tên hợp đồng">
              {{ record.ten || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Cơ sở">
              {{ record.coSo?.ten || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Ngành hàng">
              {{ record.nghanhHang?.ten || '-' }}
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
            <DescriptionsItem label="Giá khung sắt">
              {{ formatCurrency(record.giaKhungSat) }}
            </DescriptionsItem>
            <DescriptionsItem label="Tổng giá">
              {{ formatCurrency(record.tongGia) }}
            </DescriptionsItem>
            <DescriptionsItem label="Mô tả" :span="2">
              {{ record.moTa || '-' }}
            </DescriptionsItem>
          </Descriptions>

          <Divider />

          <Descriptions title="Thông tin khách hàng" :column="2" bordered size="small">
            <DescriptionsItem label="Họ và tên">
              {{ record.khachHang?.hoVaTen || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Số điện thoại">
              {{ record.khachHang?.sdt || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Email">
              {{ record.khachHang?.email || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Địa chỉ">
              {{ record.khachHang?.diaChi || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Người giới thiệu" :span="2">
              {{ record.nguoiGioiThieu?.hoVaTen || '-' }}
            </DescriptionsItem>
          </Descriptions>

          <Divider />

          <Descriptions title="Danh sách vật tư" :column="1" bordered size="small">
            <template v-if="record.vatTuHopDongs && record.vatTuHopDongs.length > 0">
              <DescriptionsItem label="Vật tư">
                <Table
                  :columns="vatTuColumns"
                  :data-source="record.vatTuHopDongs"
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
                    <template v-else-if="column.key === 'giaHienThi'">
                      {{ formatCurrency(vatTu.giaHienThi) }}
                    </template>
                    <template v-else-if="column.key === 'gm'">
                      {{ vatTu.gm || 0 }}%
                    </template>
                    <template v-else-if="column.key === 'thoiGianBaoHanh'">
                      {{ vatTu.thoiGianBaoHanh || 0 }}
                    </template>
                    <template v-else-if="column.key === 'duocBaoHanh'">
                      <a-tag :color="vatTu.duocBaoHanh ? 'success' : 'default'">
                        {{ vatTu.duocBaoHanh ? 'Có' : 'Không' }}
                      </a-tag>
                    </template>
                    <template v-else-if="column.key === 'moTa'">
                      {{ vatTu.moTa || '-' }}
                    </template>
                  </template>
                </Table>
              </DescriptionsItem>
            </template>
            <template v-else>
              <DescriptionsItem label="Thông tin">
                Chưa có vật tư
              </DescriptionsItem>
            </template>
          </Descriptions>
        </div>
      </template>
    </BasicTable>

    <CreateHopDongModal @register="registerCreateModal" @success="handleSuccess" />
  </div>
</template>

<script lang="ts" setup>
  import { onMounted, ref } from 'vue';
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { PlusOutlined, ReloadOutlined } from '@ant-design/icons-vue';
  import { useModal } from '@/components/Modal';
  import { columns, searchFormSchema, vatTuColumns } from './hopDong.data.ts';
  import { filterHopDong, getAllCoSo, getAllNganhHang, deleteHopDong, type HopDongDto } from './hopDong.ts';
  import CreateHopDongModal from './CreateHopDongModal.vue';
  import { Button, Descriptions, DescriptionsItem, Divider, message, Table } from 'ant-design-vue';

  defineOptions({ name: 'HopDongManagement' });

  const [registerCreateModal, { openModal: openCreateModal }] = useModal();

  const [registerTable, { reload, getForm }] = useTable({
    title: 'Danh sách hợp đồng',
    api: async (params) => {
      try {
        const response = await filterHopDong(params);
        if (!response || !response.data || !response.data.content) {
          console.warn('filterHopDong returned unexpected shape', response);
          return { items: [], total: 0 };
        }
        return {
          items: response.data.content,
          total: response.data.totalElements ?? response.data.content.length ?? 0,
        };
      } catch (error: any) {
        console.error('Error fetching data:', error);
        message.error(`Lỗi tải dữ liệu: ${error?.message ?? String(error)}`);
        return { items: [], total: 0 };
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

  async function loadSearchOptions() {
    try {
      const [coSoRes, nghanhHangRes] = await Promise.allSettled([
        getAllCoSo(),
        getAllNganhHang(),
      ]);

      const coSoOptions = ref<any[]>([]);
      const nghanhHangOptions = ref<any[]>([]);

      if (coSoRes.status === 'fulfilled' && coSoRes.value?.data) {
        const list = Array.isArray(coSoRes.value.data) ? coSoRes.value.data : coSoRes.value.data.content || [];
        coSoOptions.value = list.map((item: any) => ({
          label: item.ten || item.ma,
          value: item.id,
        }));
      }

      if (nghanhHangRes.status === 'fulfilled' && nghanhHangRes.value?.data) {
        const list = Array.isArray(nghanhHangRes.value.data) ? nghanhHangRes.value.data : nghanhHangRes.value.data.content || [];
        nghanhHangOptions.value = list.map((item: any) => ({
          label: item.ten,
          value: item.id,
        }));
      }

      const form = getForm();
      if (form && typeof form.updateSchema === 'function') {
        form.updateSchema([
          { field: 'coSoId', componentProps: { options: coSoOptions.value } },
          { field: 'nghanhHangId', componentProps: { options: nghanhHangOptions.value } },
        ]);
      }
    } catch (error) {
      console.error('Error loading search options:', error);
    }
  }

  function formatCurrency(value: number) {
    if (!value && value !== 0) return '0đ';
    try {
      return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value);
    } catch {
      return String(value);
    }
  }

  function handleCreate() {
    openCreateModal(true, {});
  }

  async function handleDelete(record: HopDongDto) {
    try {
      const result = await deleteHopDong(record.id);
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

  function handleRefresh() {
    reload();
  }

  function handleSuccess() {
    reload();
  }

  onMounted(() => {
    loadSearchOptions();
  });
</script>

<style lang="less" scoped>
</style>