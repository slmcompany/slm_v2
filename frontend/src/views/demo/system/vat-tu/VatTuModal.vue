<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    :title="getTitle"
    :width="1000"
    @ok="handleSubmit"
  >
    <BasicForm @register="registerForm">
      <template #duLieuRieng="{ model }">
        <div class="du-lieu-rieng-container">
          <a-alert 
            v-if="duLieuRiengList.length === 0"
            message="Vui lòng chọn nhóm vật tư để hiển thị các thuộc tính riêng"
            type="info"
            show-icon
            style="margin-bottom: 16px"
          />
          
          <div 
            v-for="(item, index) in duLieuRiengList" 
            :key="index"
            class="du-lieu-rieng-item"
          >
            <a-card size="small" :title="item.ten">
              <a-row :gutter="16">
                <a-col :span="12">
                  <a-form-item label="Đơn vị" v-if="item.donVi">
                    <a-input :value="item.donVi" disabled />
                  </a-form-item>
                </a-col>
                <a-col :span="12">
                  <a-form-item label="Giá trị" :required="false">
                    <a-input 
                      v-model:value="item.giaTri" 
                      placeholder="Nhập giá trị"
                    />
                  </a-form-item>
                </a-col>
              </a-row>
            </a-card>
          </div>
        </div>
      </template>

      <template #dsGia="{ model, field }">
        <div class="ds-gia-container" v-if="field === 'dsGia'">
          <a-button 
            type="dashed" 
            block 
            @click="handleAddGia"
            style="margin-bottom: 16px"
          >
            <template #icon>
              <PlusOutlined />
            </template>
            Thêm thông tin giá
          </a-button>
          
          <a-empty 
            v-if="dsGiaList.length === 0" 
            description="Chưa có thông tin giá"
            :image-style="{ height: '60px' }"
          />
          
          <div 
            v-for="(item, index) in dsGiaList" 
            :key="`gia-item-${index}`"
            class="gia-item"
          >
            <div style="display: flex; align-items: center; justify-content: space-between; margin-bottom: 8px;">
              <span style="font-size: 14px; font-weight: 500;">Thông tin giá {{ index + 1 }}</span>
              <a-button 
                type="primary" 
                danger 
                size="small"
                @click="handleRemoveGia(index)"
              >
                <template #icon>
                  <DeleteOutlined />
                </template>
                Xóa
              </a-button>
            </div>
            
            <a-card size="small" :bordered="true">
              <a-row :gutter="16">
                <a-col :span="8">
                  <a-form-item label="Mã cơ sở" :required="true">
                    <Select 
                      :value="item.maCoSo"
                      @change="(val) => { item.maCoSo = val; handleCoSoChange(item, val); }"
                      placeholder="Chọn cơ sở"
                      style="width: 100%"
                    >
                      <SelectOption value="HN">HN - Hà Nội</SelectOption>
                      <SelectOption value="HCM">HCM - Hồ Chí Minh</SelectOption>
                    </Select>
                  </a-form-item>
                </a-col>
                <a-col :span="8">
                  <a-form-item label="Tên cơ sở">
                    <a-input 
                      v-model:value="item.tenCoSo" 
                      placeholder="Tên cơ sở"
                      disabled
                    />
                  </a-form-item>
                </a-col>
                <a-col :span="8">
                  <a-form-item label="Giá nhập" :required="true">
                    <InputNumber
                      v-model:value="item.giaNhap" 
                      placeholder="Nhập giá nhập"
                      :min="0"
                      :formatter="value => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="value => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </a-form-item>
                </a-col>
                <a-col :span="8">
                  <a-form-item label="Giá bán" :required="true">
                    <InputNumber
                      v-model:value="item.giaBan" 
                      placeholder="Nhập giá bán"
                      :min="0"
                      :formatter="value => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="value => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </a-form-item>
                </a-col>
              </a-row>
            </a-card>
          </div>
        </div>
      </template>

      <template #files="{ model }">
        <div class="files-container">
          <Upload
            ref="antUpload"
            :file-list="fileList"
            list-type="picture-card"
            :before-upload="beforeUpload"
            @remove="handleRemoveFile"
            @change="onUploadChange"
            :multiple="true"
            accept="image/*"
            :show-upload-list="true"
          >
            <div v-if="fileList.length < 8" @click.stop.prevent="triggerNativeFileInput" style="cursor:pointer">
              <PlusOutlined />
              <div style="margin-top: 8px">Tải ảnh lên</div>
            </div>
          </Upload>

          <input
            ref="nativeFileInput"
            type="file"
            style="display: none"
            multiple
            accept="image/*"
            @change="onNativeFiles"
          />

          <div class="upload-hint">Tối đa 8 ảnh, định dạng: JPG, PNG, GIF</div>
        </div>
      </template>
    </BasicForm>
  </BasicModal>
</template>
  
<script lang="ts" setup>
  import { ref, computed, unref, watch } from 'vue';
  import { Upload, InputNumber, Empty, Select, SelectOption } from 'ant-design-vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { BasicForm, useForm } from '@/components/Form';
  import { PlusOutlined, DeleteOutlined } from '@ant-design/icons-vue';
  import { formSchema } from './vatTu.data';
  import { createVatTu, updateVatTu, GiaInfo, ThuocTinh } from './vatTu';
  import { message } from 'ant-design-vue';
  import type { UploadProps } from 'ant-design-vue';

  defineOptions({ name: 'VatTuModal' });

  const props = defineProps<{
    nhomVatTuOptions: Array<{ label: string; value: number; thuocTinhRieng?: Record<string, ThuocTinh> }>;
    thuongHieuOptions: Array<{ label: string; value: number }>;
  }>();

  const emit = defineEmits(['success', 'register']);

  const isUpdate = ref(false);
  const recordId = ref<number>();
  const duLieuRiengList = ref<Array<ThuocTinh & { key?: string }>>([]);
  const dsGiaList = ref<GiaInfo[]>([]);
  const fileList = ref<any[]>([]);

  // Options cho select cơ sở
  const coSoOptions = ref([
    { label: 'HN - Hà Nội', value: 'HN' },
    { label: 'HCM - Hồ Chí Minh', value: 'HCM' }
  ]);

  const nativeFileInput = ref<HTMLInputElement | null>(null);
  const antUpload = ref<any>(null);

  const [registerForm, { resetFields, setFieldsValue, validate, updateSchema, getFieldsValue }] = useForm({
    labelWidth: 140,
    schemas: formSchema,
    showActionButtonGroup: false,
    baseColProps: { span: 24 },
  });

  const [registerModal, { setModalProps, closeModal }] = useModalInner(async (data) => {
    resetFields();
    setModalProps({ confirmLoading: false });
    isUpdate.value = !!data?.isUpdate;
    duLieuRiengList.value = [];
    dsGiaList.value = [];
    fileList.value = [];

    updateSchema([
      {
        field: 'nhomVatTuId',
        componentProps: {
          options: props.nhomVatTuOptions,
          onChange: (v: any) => handleNhomChange(v),
        },
      },
      {
        field: 'thuongHieuId',
        componentProps: {
          options: props.thuongHieuOptions,
        },
      },
    ]);

    if (unref(isUpdate)) {
      recordId.value = data.record.id;
      
      if (data.record.duLieuRieng && Object.keys(data.record.duLieuRieng).length > 0) {
        duLieuRiengList.value = Object.entries(data.record.duLieuRieng).map(([key, value]) => {
          const v = value as any;
          return {
            key,
            ...v,
          };
        });
      }

      if (data.record.thongTinGias && data.record.thongTinGias.length > 0) {
        dsGiaList.value = (data.record.thongTinGias[0].dsGia || []).map((g: any) => ({
          maCoSo: g.maCoSo || '',
          tenCoSo: g.tenCoSo || '',
          giaNhap: g.giaNhap != null ? Number(g.giaNhap) : 0,
          giaBan: g.giaBan != null ? Number(g.giaBan) : 0,
          giaNhapRaw: g.giaNhap != null ? formatNumber(Number(g.giaNhap)) : '',
          giaBanRaw: g.giaBan != null ? formatNumber(Number(g.giaBan)) : '',
        }));
      }
      
      if (Array.isArray(data.record.anhVatTus) && data.record.anhVatTus.length > 0) {
        fileList.value = data.record.anhVatTus.map((a: any, i: number) => {
          const duongDan = a.tepTin?.duongDan || '';
          return {
            uid: String(a.id ?? `exist-${i}`),
            name: a.tepTin?.tenTepGoc || a.tepTin?.tenLuuTru || `img-${i}`,
            status: 'done',
            url: duongDan,
            thumbUrl: duongDan,
            response: { url: duongDan },
            type: a.tepTin?.loaiTepTin || 'image/jpeg',
            isExisting: true,
          };
        });
      }
      
      setFieldsValue({
        ...data.record,
        nhomVatTuId: data.record.nhomVatTu?.id,
        thuongHieuId: data.record.thuongHieu?.id,
      });

      handleNhomChange(data.record.nhomVatTu?.id);
    } else {
      duLieuRiengList.value = [];
    }
  });

  function handleNhomChange(newNhomVatTuId: any) {
    try {
      if (!newNhomVatTuId) {
        duLieuRiengList.value = []; 
        return;
      }
      const selectedNhom = props.nhomVatTuOptions.find(opt => String(opt.value) === String(newNhomVatTuId));
      if (selectedNhom?.thuocTinhRieng) {
        duLieuRiengList.value = Object.entries(selectedNhom.thuocTinhRieng).map(([key, value]) => {
          const v = value as any;
          return {
            key,
            ten: v.ten ?? key,
            donVi: v.donVi ?? '',
            giaTri: v.giaTri ?? null,
          };
        });
      } else {
        if (!unref(isUpdate)) {
          duLieuRiengList.value = [];
        }
      }
    } catch (err) {
      console.error('handleNhomChange error', err);
    }
  }

  watch(
    () => {
      try {
        const fields = typeof getFieldsValue === 'function' ? getFieldsValue() : undefined;
        return fields?.nhomVatTuId;
      } catch (e) {
        return undefined;
      }   
    },
    (newNhomVatTuId) => {
      try {
        if (!newNhomVatTuId) {
          duLieuRiengList.value = [];
          return;
        }
        const selectedNhom = props.nhomVatTuOptions.find(opt => String(opt.value) === String(newNhomVatTuId));
        if (selectedNhom?.thuocTinhRieng) {
          duLieuRiengList.value = Object.entries(selectedNhom.thuocTinhRieng).map(([key, value]) => {
            const v = value as any;
            return {
              key,
              ten: v.ten ?? key,
              donVi: v.donVi ?? '',
              giaTri: v.giaTri ?? null,
            };
          });
        } else {
          if (!unref(isUpdate)) {
            duLieuRiengList.value = [];
          }
        }
      } catch (err) {
        console.error('watch nhomVatTuId error', err);
      } 
    },
    { immediate: true }
  );

  const getTitle = computed(() => (!unref(isUpdate) ? 'Tạo vật tư' : 'Chỉnh sửa vật tư'));

  function handleAddGia() {
    const newGia = {
      maCoSo: '',
      tenCoSo: '',
      giaNhap: 0,
      giaBan: 0,
      giaNhapRaw: '0',
      giaBanRaw: '0',
    };
    dsGiaList.value.push(newGia);
    console.log('Added new gia:', newGia);
    console.log('Current dsGiaList:', dsGiaList.value);
  }
  
  function handleRemoveGia(index: number) {
    dsGiaList.value.splice(index, 1);
  }

  function handleCoSoChange(item: any, value: string) {
    const coSoMap: Record<string, string> = {
      'HN': 'Hà Nội',
      'HCM': 'Hồ Chí Minh'
    };
    item.tenCoSo = coSoMap[value] || '';
    console.log('Selected cơ sở:', value, '-> Tên:', item.tenCoSo);
  }

  function triggerNativeFileInput() {
    try {
      const antEl = antUpload.value?.$el ?? antUpload.value;
      const inputInside = antEl?.querySelector?.('input[type=file]');
      if (inputInside) {
        inputInside.click();
        return;
      }
      nativeFileInput.value?.click();
    } catch (e) {
      console.error('triggerNativeFileInput error', e);
      nativeFileInput.value?.click();
    }
  }

  function onNativeFiles(e: Event) {
    const input = e.target as HTMLInputElement;
    const files = Array.from(input.files || []);
    if (!files.length) return;
    for (const f of files) {
      const isImage = f.type && f.type.startsWith && f.type.startsWith('image/');
      if (!isImage) {
        message.error('Chỉ chấp nhận file ảnh!');
        continue;
      }
      const isLt5M = f.size / 1024 / 1024 < 5;
      if (!isLt5M) {
        message.error('Kích thước ảnh phải nhỏ hơn 5MB!');
        continue;
      }
      try {
        const url = URL.createObjectURL(f);
        const newFile = {
          uid: f.uid ?? `${Date.now()}_${Math.random().toString(36).slice(2,8)}`,
          name: f.name,
          status: 'done',
          originFileObj: f,
          url,
          thumbUrl: url,
        };
        if (fileList.value.length >= 8) {
          message.warning('Đã đạt tối đa 8 ảnh');
          break;
        }
        fileList.value = [...fileList.value, newFile];
      } catch (err) {
        console.error('onNativeFiles push error', err);
      }
    }
    input.value = '';
  }
  
  const beforeUpload: UploadProps['beforeUpload'] = (file) => {
    const isImage = file.type && file.type.startsWith && file.type.startsWith('image/');
    if (!isImage) {
      message.error('Chỉ chấp nhận file ảnh!');
      return false;
    }
    const isLt5M = file.size / 1024 / 1024 < 5;
    if (!isLt5M) {
      message.error('Kích thước ảnh phải nhỏ hơn 5MB!');
      return false;
    }
    try {
      const url = URL.createObjectURL(file as File);
      const nf = {
        uid: file.uid ?? `${Date.now()}`,
        name: file.name,
        status: 'done',
        originFileObj: file,
        url,
        thumbUrl: url,
      };
      fileList.value = [...fileList.value, nf];
    } catch (e) {
      console.error('beforeUpload push error', e);
    }
    return false;
  };

  function onUploadChange(e: any) {
    try {
      const fl = (e && e.fileList) || [];
      fileList.value = fl.map((f: any) => {
        if (f.url || f.thumbUrl) {
          return {
            uid: f.uid,
            name: f.name,
            status: f.status || 'done',
            url: f.url || f.thumbUrl,
            thumbUrl: f.thumbUrl || f.url,
            originFileObj: f.originFileObj || f.originFile,
          };
        }
        const origin = f.originFile || f.originFileObj;
        const url = origin ? URL.createObjectURL(origin) : undefined;
        return {
          uid: f.uid,
          name: f.name,
          status: f.status || 'done',
          url,
          thumbUrl: url,
          originFileObj: origin,
        };
      });
    } catch (err) {
      console.error('onUploadChange error', err);
    }
  }

  async function handleSubmit() {
    try {
      const values = await validate();
      setModalProps({ confirmLoading: true });

      for (let i = 0; i < dsGiaList.value.length; i++) {
        const gia = dsGiaList.value[i] as any;
        if (!gia.maCoSo || !gia.tenCoSo) {
          message.error(`Vui lòng nhập Mã/Tên cơ sở cho thông tin giá ${i + 1}`);
          setModalProps({ confirmLoading: false });
          return;
        }
        if (gia.giaNhap === null || gia.giaNhap === undefined || gia.giaBan === null || gia.giaBan === undefined) {
          message.error(`Vui lòng nhập đầy đủ Giá nhập và Giá bán cho thông tin giá ${i + 1}`);
          setModalProps({ confirmLoading: false });
          return;
        }
      }
      
      const duLieuRiengMap = {};
      if (duLieuRiengList.value.length > 0) {
        duLieuRiengList.value.forEach((item) => {
          const key = item.key || `thuoctinh_${Math.random().toString(36).substr(2, 9)}`;
          duLieuRiengMap[key] = {
            ten: item.ten,
            donVi: item.donVi || '',
            giaTri: item.giaTri || null,
          };
        });
      }
      
      const dsGiaPayload = dsGiaList.value.length > 0
        ? dsGiaList.value.map((g: any) => ({
            maCoSo: g.maCoSo,
            tenCoSo: g.tenCoSo,
            giaNhap: Number(parseNumberFromString(g.giaNhapRaw ?? '') ?? g.giaNhap ?? 0),
            giaBan: Number(parseNumberFromString(g.giaBanRaw ?? '') ?? g.giaBan ?? 0),
          }))
        : undefined;

      const submitData = {
        nhomVatTuId: values.nhomVatTuId,
        thuongHieuId: values.thuongHieuId || undefined,
        ten: values.ten,
        sheetLink: values.sheetLink || undefined,
        donVi: values.donVi || undefined,
        moTaBaoGia: values.moTaBaoGia || undefined,
        moTaHopDong: values.moTaHopDong || undefined,
        duLieuRieng: duLieuRiengMap,
        trangThai: values.trangThai,
        dsGia: dsGiaPayload,
      };

      const files = fileList.value.map(f => f.originFileObj).filter(Boolean);

      let result;
      if (unref(isUpdate)) {
        result = await updateVatTu(recordId.value!, submitData, files);
      } else {
        result = await createVatTu(submitData, files);
      }

      if (result.status === 200 || result.status === 201) {
        message.success(unref(isUpdate) ? 'Cập nhật thành công' : 'Tạo mới thành công');
        closeModal();
        emit('success');
      } else {
        message.error(result.message || 'Có lỗi xảy ra');
      }
    } catch (error: any) {
      console.error('Submit error:', error);
      message.error(error?.message || 'Có lỗi xảy ra khi lưu dữ liệu');
    } finally {
      setModalProps({ confirmLoading: false });
    }
  }

  function formatNumber(value: number | null | undefined) {
    if (value === null || value === undefined || isNaN(Number(value))) return '';
    return new Intl.NumberFormat('en-US').format(Number(value));
  }

  function parseNumberFromString(raw: string) {
    if (!raw && raw !== '0') return null;
    const cleaned = String(raw).replace(/[^\d.-]/g, '');
    if (cleaned === '') return null;
    const n = Number(cleaned);
    return Number.isFinite(n) ? n : null;
  }

  function handleRemoveFile(file: any) {
    try {
      fileList.value = fileList.value.filter((f: any) => !(f.uid === file.uid || f.name === file.name));
      try {
        const url = file.url || file.thumbUrl;
        if (url && url.startsWith && url.startsWith('blob:')) {
          URL.revokeObjectURL(url);
        }
      } catch (e) {
        // ignore
      }
    } catch (err) {
      console.error('handleRemoveFile error', err);
    }
  }
</script>

<style lang="less" scoped>
.du-lieu-rieng-container {
  .du-lieu-rieng-item {
    margin-bottom: 16px;
    
    &:last-child {
      margin-bottom: 0;
    }
  }
  
  :deep(.ant-card-head) {
    min-height: 40px;
    padding: 0 12px;
    
    .ant-card-head-title {
      padding: 8px 0;
      font-size: 14px;
    }
  }
  
  :deep(.ant-card-body) {
    padding: 12px;
  }
  
  :deep(.ant-form-item) {
    margin-bottom: 8px;
  }
}

.ds-gia-container {
  .gia-item {
    margin-bottom: 16px;
    
    &:last-child {
      margin-bottom: 0;
    }
  }
  
  :deep(.ant-card) {
    .ant-card-head {
      min-height: 48px;
      padding: 0 16px;
      border-bottom: 1px solid rgb(255 255 255 / 10%);
      background: rgb(255 255 255 / 4%);
      
      .ant-card-head-title {
        padding: 12px 0;
        font-size: 14px;
        font-weight: 500;
      }
    }
    
    .ant-card-body {
      padding: 16px;
    }
  }
  
  :deep(.ant-form-item) {
    margin-bottom: 12px;
  }
}

.files-container {
  .upload-hint {
    margin-top: 8px;
    color: #999;
    font-size: 12px;
  }
}
</style>