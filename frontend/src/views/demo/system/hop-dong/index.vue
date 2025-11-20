<template>
  <div>
    <BasicTable @register="registerTable">
      <template #toolbar>
        <a-button type="primary" @click="handleCreate">
          <template #icon><PlusOutlined /></template>
          Tạo mới
        </a-button>
        <a-button @click="handleRefresh">
          <template #icon><ReloadOutlined /></template>
          Làm mới
        </a-button>
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
          <a-descriptions title="Thông tin cơ bản" :column="2" bordered size="small">
            <a-descriptions-item label="Tên hợp đồng">
              {{ record.ten || '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="Cơ sở">
              {{ record.coSo?.ten || '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="Ngành hàng">
              {{ record.nghanhHang?.ten || '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="Loại hệ thống">
              {{ record.loaiHeThong || '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="Loại pha">
              {{ record.loaiPha || '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="Sản lượng">
              {{ record.sanLuongToiThieu || 0 }} - {{ record.sanLuongToiDa || 0 }} kW
            </a-descriptions-item>
            <a-descriptions-item label="Giá khung sắt">
              {{ formatCurrency(record.giaKhungSat) }}
            </a-descriptions-item>
            <a-descriptions-item label="Tổng giá">
              {{ formatCurrency(record.tongGia) }}
            </a-descriptions-item>
            <a-descriptions-item label="Mô tả" :span="2">
              {{ record.moTa || '-' }}
            </a-descriptions-item>
          </a-descriptions>

          <a-divider />

          <a-descriptions title="Thông tin khách hàng" :column="2" bordered size="small">
            <a-descriptions-item label="Họ và tên">
              {{ record.khachHang?.hoVaTen || '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="Số điện thoại">
              {{ record.khachHang?.sdt || '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="Email">
              {{ record.khachHang?.email || '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="Địa chỉ">
              {{ record.khachHang?.diaChi || '-' }}
            </a-descriptions-item>
            <a-descriptions-item label="Người giới thiệu" :span="2">
              {{ record.nguoiGioiThieu?.hoVaTen || '-' }}
            </a-descriptions-item>
          </a-descriptions>

          <a-divider />

          <a-descriptions title="Danh sách vật tư" :column="1" bordered size="small">
            <template v-if="record.vatTuHopDongs && record.vatTuHopDongs.length > 0">
              <a-descriptions-item label="Vật tư">
                <a-table
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
                </a-table>
              </a-descriptions-item>
            </template>
            <template v-else>
              <a-descriptions-item label="Thông tin">
                Chưa có vật tư
              </a-descriptions-item>
            </template>
          </a-descriptions>
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
  import { message } from 'ant-design-vue';

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
  :deep(.ant-descriptions-item-label) {
    background-color: #fafafa;
    font-weight: 600;
  }
</style>