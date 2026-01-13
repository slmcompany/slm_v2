<!-- src/views/demo/system/bai-viet/index.vue -->

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
                onClick: () => handleEdit(record as BaiVietDto),
              },
              {
                icon: 'ant-design:delete-outlined',
                color: 'error',
                tooltip: 'Xóa',
                popConfirm: {
                  title: 'Chức năng này hiện tại chưa được hỗ trợ',
                  placement: 'left',
                  okText: 'Đã hiểu',
                  cancelText: null,
                  confirm: () => handleDelete(record as BaiVietDto),
                },
              },
            ]"
          />
        </template>
      </template>
      <template #expandedRowRender="{ record }">
        <div class="p-4">
          <Descriptions title="Thông tin chi tiết" :column="2" bordered size="small">
            <DescriptionsItem label="ID">
              {{ record.id }}
            </DescriptionsItem>
            <DescriptionsItem label="Loại bài viết">
              {{ getLoaiBaiVietText(record.loaiBaiViet) }}
            </DescriptionsItem>
            <DescriptionsItem label="Tiêu đề" :span="2">
              {{ record.tieuDe || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Đường dẫn Youtube" :span="2">
              <a
                v-if="record.duongDanYoutube"
                :href="record.duongDanYoutube"
                target="_blank"
                rel="noopener noreferrer"
              >
                {{ record.duongDanYoutube }}
              </a>
              <span v-else style="color: #999">-</span>
            </DescriptionsItem>
            <DescriptionsItem label="Người tạo">
              {{ record.taoBoi?.ten || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Email người tạo">
              {{ record.taoBoi?.email || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Trạng thái">
              <Tag :color="record.trangThai === 1 ? 'success' : 'error'">
                {{ record.trangThai === 1 ? 'Hiển thị' : 'Ẩn' }}
              </Tag>
            </DescriptionsItem>
            <DescriptionsItem label="Liên quan">
              {{ record.lienQuan || '-' }}
            </DescriptionsItem>
          </Descriptions>

          <Divider />

          <Descriptions title="Hình ảnh" :column="2" bordered size="small">
            <DescriptionsItem label="Ảnh bìa">
              <div v-if="record.anhBia">
                <Image
                  :width="200"
                  :src="record.anhBia.duongDan"
                  :preview="true"
                  style="border-radius: 4px"
                />
                <div style="margin-top: 8px; color: #999; font-size: 12px">
                  {{ record.anhBia.tenTepGoc }}
                </div>
              </div>
              <span v-else style="color: #999">Chưa có ảnh bìa</span>
            </DescriptionsItem>
            <DescriptionsItem label="Ảnh ngoài">
              <div v-if="record.anhNgoai">
                <Image
                  :width="200"
                  :src="record.anhNgoai.duongDan"
                  :preview="true"
                  style="border-radius: 4px"
                />
                <div style="margin-top: 8px; color: #999; font-size: 12px">
                  {{ record.anhNgoai.tenTepGoc }}
                </div>
              </div>
              <span v-else style="color: #999">Chưa có ảnh ngoài</span>
            </DescriptionsItem>
          </Descriptions>

          <Divider v-if="record.noiDung" />

          <div v-if="record.noiDung">
            <strong>Nội dung:</strong>
            <div style="margin-top: 8px">
              <a :href="record.noiDung.duongDan" target="_blank" rel="noopener noreferrer">
                <FileTextOutlined style="margin-right: 8px" />
                {{ record.noiDung.tenTepGoc }}
              </a>
              <div style="margin-top: 4px; color: #999; font-size: 12px">
                Kích thước: {{ formatFileSize(record.noiDung.kichCo) }}
              </div>
            </div>
          </div>

          <Divider />

          <Descriptions title="Thông tin thời gian" :column="2" bordered size="small">
            <DescriptionsItem label="Ngày tạo">
              {{ record.taoLuc ? new Date(record.taoLuc).toLocaleString('vi-VN') : '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Ngày sửa">
              {{ record.suaLuc ? new Date(record.suaLuc).toLocaleString('vi-VN') : '-' }}
            </DescriptionsItem>
          </Descriptions>
        </div>
      </template>
    </BasicTable>
  </div>
</template>

<script lang="ts" setup>
  import { BasicTable, useTable, TableAction } from '@/components/Table';
  import { PlusOutlined, ReloadOutlined, FileTextOutlined } from '@ant-design/icons-vue';
  import { columns, searchFormSchema } from './baiViet.data';
  import { filterBaiViet, deleteBaiViet } from './baiViet';
  import type { BaiVietDto } from './baiViet';
  import { Button, Descriptions, DescriptionsItem, Divider, Image, message, Tag } from 'ant-design-vue';
  import { useRouter } from 'vue-router';

  defineOptions({ name: 'BaiVietManagement' });

  const router = useRouter();

  const [registerTable, { reload }] = useTable({
    title: 'Danh sách bài viết',
    api: async (params) => {
      try {
        const response = await filterBaiViet(params);
        if (!response || !response.data || !response.data.content) {
          console.warn('filterBaiViet returned unexpected shape', response);
          message.warn('API trả về dữ liệu không đúng, kiểm tra console');
          return { items: [], total: 0 };
        }
        return {
          items: response.data.content,
          total: response.data.totalElements ?? response.data.content.length ?? 0,
        };
      } catch (error: any) {
        console.error('Error fetching data (filterBaiViet):', error);
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

  function handleCreate() {
    try {
      // Mở tab mới tới trang tạo bài viết
      const route = router.resolve({ name: 'BaiVietManagement' });
      window.open(route.href, '_blank');
    } catch (err) {
      console.error('handleCreate error:', err);
      message.error('Không thể mở trang tạo bài viết: ' + String(err));
    }
  }

  function handleEdit(record: BaiVietDto) {
    try {
      // Mở tab mới tới trang sửa bài viết với ID
      const route = router.resolve({
        name: 'SuaBaiVietManagement',
        query: { id: record.id },
      });
      window.open(route.href, '_blank');
    } catch (err) {
      console.error('handleEdit error:', err);
      message.error('Không thể mở trang chỉnh sửa: ' + String(err));
    }
  }

  async function handleDelete(record: BaiVietDto) {
    try {
      const result = await deleteBaiViet(record.id);
      if (result && result.status === 501) {
        message.warning(result.message || 'Chức năng này hiện tại chưa được hỗ trợ');
      } else if (result && result.status === 200) {
        message.success('Xóa thành công');
        reload();
      } else {
        message.error(result?.message || 'Có lỗi xảy ra khi xóa');
      }
    } catch (error) {
      console.error('Delete error:', error);
      message.error('Chức năng này hiện tại chưa được hỗ trợ');
    }
  }

  function handleRefresh() {
    reload();
  }

  function getLoaiBaiVietText(loai: string): string {
    const map = {
      MEGA_STORY: 'MEGA STORY',
      HOI_DAP: 'HỎI ĐÁP',
      HUONG_DAN_BAO_HANH: 'HƯỚNG DẪN BẢO HÀNH',
    };
    return map[loai] || loai;
  }

  function formatFileSize(bytes: number): string {
    if (!bytes) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return Math.round(bytes / Math.pow(k, i) * 100) / 100 + ' ' + sizes[i];
  }
</script>

<style lang="less" scoped>
</style>