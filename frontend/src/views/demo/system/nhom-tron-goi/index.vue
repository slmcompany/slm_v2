<template>
  <div>
    <!-- Debug / Error banner -->
    <div v-if="loadError" style="margin-bottom:12px;padding:12px;border:1px solid #ffa39e;border-radius:6px;background:#fff1f0;color:#a8071a">
      <strong>Lỗi tải dữ liệu:</strong> {{ loadError }}
      <pre style="max-height:200px;margin-top:8px;overflow:auto;white-space:pre-wrap;">{{ lastResultsDebug }}</pre>
    </div>

    <Alert
      v-if="showDebug && !loadError"
      type="info"
      show-icon
      style="margin-bottom:12px"
      :message="'Debug: ' + (debugNote || 'no note')"
    >
      <template #description>
        <pre style="max-height:200px;overflow:auto;white-space:pre-wrap;">{{ lastResultsDebug }}</pre>
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
        <Button style="margin-left:8px" @click="toggleDebug">
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
                onClick: () => handleEdit(record as NhomTronGoiDto),
              },
              {
                icon: 'ant-design:delete-outlined',
                color: 'error',
                tooltip: 'Xóa',
                popConfirm: {
                  title: 'Bạn có chắc chắn muốn xóa?',
                  placement: 'left',
                  confirm: () => handleDelete(record as NhomTronGoiDto),
                },
              },
            ]"
          />
        </template>
      </template>

      <template #expandedRowRender="{ record }">
        <div class="p-4">
          <Descriptions title="Thông tin ngành hàng" :column="2" bordered size="small">
            <DescriptionsItem label="Mã ngành hàng">
              {{ record.nganhHang?.ma || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="Tên ngành hàng">
              {{ record.nganhHang?.ten || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="SĐT Sale">
              {{ record.nganhHang?.sdtSale || '-' }}
            </DescriptionsItem>
            <DescriptionsItem label="SĐT Tech">
              {{ record.nganhHang?.sdtTech || '-' }}
            </DescriptionsItem>
          </Descriptions>

          <a-divider />

          <Descriptions title="Thông tin thương hiệu" :column="2" bordered size="small">
            <DescriptionsItem label="Thương hiệu tấm pin">
              <a-tag color="blue">{{ record.thuongHieuTamPin?.ten || '-' }}</a-tag>
              <span v-if="record.thuongHieuTamPin?.tenQuocTe" style="margin-left: 8px; color: #999;">
                ({{ record.thuongHieuTamPin.tenQuocTe }})
              </span>
            </DescriptionsItem>
            <DescriptionsItem label="Thương hiệu inverter">
              <a-tag color="green">{{ record.thuongHieuInverter?.ten || '-' }}</a-tag>
              <span v-if="record.thuongHieuInverter?.tenQuocTe" style="margin-left: 8px; color: #999;">
                ({{ record.thuongHieuInverter.tenQuocTe }})
              </span>
            </DescriptionsItem>
            <DescriptionsItem label="Thương hiệu pin lưu trữ" :span="2">
              <a-tag color="orange">{{ record.thuongHieuPinLuuTru?.ten || '-' }}</a-tag>
              <span v-if="record.thuongHieuPinLuuTru?.tenQuocTe" style="margin-left: 8px; color: #999;">
                ({{ record.thuongHieuPinLuuTru.tenQuocTe }})
              </span>
            </DescriptionsItem>
          </Descriptions>

          <Divider v-if="record.nganhHang?.anhNgang || record.nganhHang?.anhVuong" />

          <div v-if="record.nganhHang?.anhNgang || record.nganhHang?.anhVuong">
            <strong>Hình ảnh ngành hàng:</strong>
            <div style="display: flex; flex-wrap: wrap; margin-top: 8px; gap: 8px">
              <a-image
                v-if="record.nganhHang?.anhNgang"
                :width="150"
                :src="record.nganhHang.anhNgang"
                :preview="true"
              />
              <a-image
                v-if="record.nganhHang?.anhVuong"
                :width="100"
                :src="record.nganhHang.anhVuong"
                :preview="true"
              />
            </div>
          </div>
        </div>
      </template>
    </BasicTable>

    <NhomTronGoiModal
      @register="registerModal"
      @success="handleSuccess"
      :nganhHangOptions="nganhHangOptions"
    />
  </div>
</template>

<script lang="ts" setup>
import { onMounted, onBeforeUnmount, ref, computed, onErrorCaptured } from 'vue';
import { BasicTable, useTable, TableAction } from '@/components/Table';
import { PlusOutlined, ReloadOutlined } from '@ant-design/icons-vue';
import { useModal } from '@/components/Modal';
import { columns, searchFormSchema } from './nhomTronGoi.data';
import {
  nhomTronGoiFilter,
  getNhomTronGoiById,
  convertToFilterRequest,
  getAllNganhHang,
  getAllThuongHieu
} from './nhomTronGoi';
import type { NganhHangDto, NhomTronGoiDto } from './nhomTronGoi';
import NhomTronGoiModal from './NhomTronGoiModal.vue';
import { Button, Descriptions, DescriptionsItem, Divider, message } from 'ant-design-vue';
import { defHttp, realHttp } from '@/utils/http/axios';

defineOptions({ name: 'NhomTronGoiManagement' });

const nganhHangOptions = ref<any[]>([]);
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

const [registerTable, { reload, getForm }] = useTable({
  title: 'Danh sách nhóm trọn gói',
  api: async (params) => {
    try {
      // const filterRequest = convertToFilterRequest(params);
      const response = await nhomTronGoiFilter(params);
      
      if (!response || !response.data || !response.data.content) {
        console.error('nhomTronGoiFilter returned unexpected shape', response);
        message.error('API trả về dữ liệu không đúng, kiểm tra console');
        return { items: [], total: 0 };
      }

      return {
        items: response.data.content,
        total: response.data.totalElements ?? response.data.content.length ?? 0,
      };
    } catch (error: any) {
      console.error('Error fetching data (nhomTronGoiFilter):', error);
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

// Helper to safely extract array payload
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
    // Load ngành hàng
    const nganhHangRes = await getAllNganhHang();
    
    lastResults.value = nganhHangRes?.data ?? nganhHangRes;
    debugNote.value = 'after loadOptions';

    const list = resolveList(nganhHangRes.data);
    nganhHangOptions.value = list.map((item: any) => ({
      label: item.ten ?? item.ma ?? String(item.id),
      value: item.id,
    }));

    console.log('Loaded nghanh hang options:', nganhHangOptions.value);

    // Update search form options
    try {
      const form = getForm();
      if (form && typeof form.updateSchema === 'function') {
        form.updateSchema([
          { field: 'nganhHangId', componentProps: { nganhHangOptions: nganhHangOptions.value } },
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

async function handleEdit(record: NhomTronGoiDto) {
  try {
    
    openModal(true, {
      record: record,
      isUpdate: true,
      nganhHangOptions: nganhHangOptions.value,
    });
  } catch (err) {
    console.error('handleEdit error:', err);
    // Fallback: sử dụng data từ list
    openModal(true, {
      record: record,
      isUpdate: true,
      nganhHangOptions: nganhHangOptions.value,
    });
  }
}

async function handleDelete(record: NhomTronGoiDto) {
  try {
    const result = await realHttp.delete(
      { url: `/nhom-tron-goi/soft-delete/${record.id}` },
      { isTransformResponse: false }
    );

    if (result && (result.status === 200)) {
      message.success('Xóa thành công');
      reload();
    } else {
      console.error('Delete returned unexpected', result);
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

function toggleDebug() {
  showDebug.value = !showDebug.value;
}

// Global page-level error logging
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