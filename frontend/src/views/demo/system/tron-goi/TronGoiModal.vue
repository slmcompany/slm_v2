<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    :title="getTitle"
    :width="1200"
    @ok="handleSubmit"
  >
    <BasicForm @register="registerForm">
      <template #tronGoiCoSos="{ model }">
        <div class="tron-goi-co-sos-container">
          <a-button 
            type="dashed" 
            block 
            @click="handleAddCoSo"
            style="margin-bottom: 16px"
          >
            <template #icon>
              <PlusOutlined />
            </template>
            Thêm cơ sở
          </a-button>
          
          <div 
            v-for="(item, index) in tronGoiCoSosList" 
            :key="index"
            class="co-so-item"
          >
            <a-card size="small" :title="`Cơ sở ${index + 1}`">
              <template #extra>
                <a-button 
                  type="link" 
                  danger 
                  size="small"
                  @click="handleRemoveCoSo(index)"
                >
                  <DeleteOutlined />
                </a-button>
              </template>
              
              <a-row :gutter="16">
                <a-col :span="8">
                  <a-form-item label="Cơ sở" :required="true">
                    <Select
                      :value="item.coSoId"
                      placeholder="Chọn cơ sở"
                      show-search
                      :filter-option="filterOption"
                      :options="coSoOptions"
                      @change="value => item.coSoId = value"
                    >
                  </Select>
                  </a-form-item>
                </a-col>
                <a-col :span="8">
                  <a-form-item label="Sản lượng tối thiểu (kW)" :required="true">
                    <InputNumber
                      v-model:value="item.sanLuongToiThieu"
                      placeholder="Sản lượng tối thiểu (kW)"
                      :min="0"
                      style="width: 100%"
                    />
                  </a-form-item>
                </a-col>
                <a-col :span="8">
                  <a-form-item label="Sản lượng tối đa (kW)" :required="true">
                    <InputNumber
                      v-model:value="item.sanLuongToiDa"
                      placeholder="Sản lượng tối đa (kW)"
                      :min="0"
                      style="width: 100%"
                    />
                  </a-form-item>
                </a-col>
              </a-row>
            </a-card>
          </div>
        </div>
      </template>

      <template #vatTuTronGois="{ model }">
        <div class="vat-tu-tron-gois-container">
          <a-row :gutter="16" style="margin-bottom: 16px">
            <a-col :span="12">
              <a-form-item label="Nhóm vật tư">
                <Select
                  :value="selectedNhomVatTu"
                  placeholder="Chọn nhóm vật tư"
                  show-search
                  :filter-option="filterOption"
                  :options="nhomVatTuOptions"
                  @change="value => handleNhomVatTuChange(value)"
                ></Select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="Thương hiệu">
                <Select
                  :value="selectedThuongHieu"
                  placeholder="Chọn thương hiệu"
                  show-search
                  :filter-option="filterOption"
                  :options="thuongHieuOptions"
                  @change="value=>handleThuongHieuChange(value)"
                ></Select>
              </a-form-item>
            </a-col>
          </a-row>

          <a-button 
            type="dashed" 
            block 
            @click="handleAddVatTu"
            style="margin-bottom: 16px"
          >
            <template #icon>
              <PlusOutlined />
            </template>
            Thêm vật tư
          </a-button>
          
          <div 
            v-for="(item, index) in vatTuTronGoisList" 
            :key="index"
            class="vat-tu-item"
          >
            <a-card size="small" :title="`Vật tư ${index + 1}`">
              <template #extra>
                <a-button 
                  type="link" 
                  danger 
                  size="small"
                  @click="handleRemoveVatTu(index)"
                >
                  <DeleteOutlined />
                </a-button>
              </template>
              
              <a-row :gutter="16">
                <a-col :span="12">
                  <a-form-item label="Vật tư" :required="true">
                    <Select
                      v-model:value="item.vatTuId"
                      placeholder="Chọn vật tư"
                      show-search
                      :filter-option="filterOption"
                      :options="vatTuOptions"
                    ></Select>
                  </a-form-item>
                </a-col>
                <a-col :span="12">
                  <a-form-item label="Số lượng" :required="true">
                    <InputNumber
                      v-model:value="item.soLuong"
                      placeholder="Nhập số lượng"
                      :min="0"
                      style="width: 100%"
                      :tooltip="`Số lượng của vật tư `+index+` trong trọn gói`"
                    />
                  </a-form-item>
                </a-col>
                <a-col :span="12">
                  <a-form-item label="Giá" :required="true">
                    <InputNumber
                      v-model:value="item.gia"
                      placeholder="Nhập giá"
                      :min="0"
                      :formatter="value => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="value => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </a-form-item>
                </a-col>
                <a-col :span="12">
                  <a-form-item label="GM (%)" :required="true">
                    <InputNumber
                      v-model:value="item.gm"
                      placeholder="Nhập GM"
                      :min="0"
                      :max="100"
                      style="width: 100%"
                    />
                  </a-form-item>
                </a-col>
                <a-col :span="12">
                  <a-form-item label="Thời gian bảo hành (tháng)">
                    <InputNumber
                      v-model:value="item.thoiGianBaoHanh"
                      placeholder="Nhập thời gian"
                      :min="0"
                      style="width: 100%"
                    />
                  </a-form-item>
                </a-col>
                <a-col :span="12">
                  <a-form-item label="Được bảo hành">
                    <RadioButtonGroup 
                      v-model:value="item.duocBaoHanh"
                      :is-btn="true"
                      :options="[
                        { label: 'Có', value: true },
                        { label: 'Không', value: false }
                      ]"
                    />
                  </a-form-item>
                </a-col>
                <a-col :span="24">
                  <a-form-item label="Mô tả">
                    <a-textarea
                      v-model:value="item.moTa"
                      placeholder="Nhập mô tả"
                      :rows="2"
                    />
                  </a-form-item>
                </a-col>
              </a-row>
            </a-card>
          </div>
        </div>
      </template>

      <template #file="{ model }">
        <div class="file-container">
          <Upload
            ref="antUpload"
            :file-list="fileList"
            list-type="picture-card"
            :before-upload="beforeUpload"
            @remove="handleRemoveFile"
            @change="onUploadChange"
            accept="image/*"
            :show-upload-list="true"
            :max-count="1"
          >
            <div v-if="fileList.length < 1" @click.stop.prevent="triggerNativeFileInput" style="cursor:pointer">
              <PlusOutlined />
              <div style="margin-top: 8px">Tải ảnh lên</div>
            </div>
          </Upload>

          <input
            ref="nativeFileInput"
            type="file"
            style="display: none"
            accept="image/*"
            @change="onNativeFile"
          />

          <div class="upload-hint">Tối đa 1 ảnh, định dạng: JPG, PNG, GIF</div>
        </div>
      </template>
    </BasicForm>
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref, computed, unref } from 'vue';
  import { Upload, InputNumber, Select, SelectOption } from 'ant-design-vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { BasicForm, useForm } from '@/components/Form';
  import { PlusOutlined, DeleteOutlined } from '@ant-design/icons-vue';
  import RadioButtonGroup from '@/components/Form/src/components/RadioButtonGroup.vue';
  import { formSchema } from './tronGoi.data';
  import { 
    createTronGoi, 
    getAllCoSo, 
    getAllNhomTronGoi,
    getAllThuongHieu,
    filterVatTu,
    ThongTinTronGoiCoSoCreatingDto,
    VatTuTronGoiCreatingDto,
    CoSoDto,
    NhomTronGoiDto,
    ThuongHieuDto,
    VatTuDto,
    NhomVatTuDto,
    getAllNhomVatTu,
    ResponseData,
    PageResponse
  } from './tronGoi';
  import { message } from 'ant-design-vue';
  import type { UploadProps } from 'ant-design-vue';
import { s } from 'node_modules/vite/dist/node/types.d-aGj9QkWt';

  defineOptions({ name: 'TronGoiModal' });

  const emit = defineEmits(['success', 'register']);

  const isUpdate = ref(false);
  const recordId = ref<number>();
  const tronGoiCoSosList = ref<ThongTinTronGoiCoSoCreatingDto[]>([]);
  const vatTuTronGoisList = ref<Array<VatTuTronGoiCreatingDto & { trangThai: number }>>([]);
  const nhomVatTuRes = ref<ResponseData<PageResponse<NhomVatTuDto>>>(null);
  const fileList = ref<any[]>([]);
  const nativeFileInput = ref<HTMLInputElement | null>(null);
  const antUpload = ref<any>(null);

  const coSoOptions = ref<Array<{ label: string; value: number }>>([]);
  const nhomTronGoiOptions = ref<Array<{ label: string; value: number }>>([]);
  const nhomVatTuOptions = ref<Array<{ label: string; value: number }>>([]);
  const thuongHieuOptions = ref<Array<{ label: string; value: number }>>([]);
  const vatTuOptions = ref<Array<{ label: string; value: number }>>([]);

  const selectedNhomVatTu = ref<number | null>(null);
  const selectedThuongHieu = ref<number | null>(null);

  const [registerForm, { resetFields, setFieldsValue, validate, updateSchema }] = useForm({
    labelWidth: 160,
    schemas: formSchema,
    showActionButtonGroup: false,
    baseColProps: { span: 24 },
  });

  const [registerModal, { setModalProps, closeModal }] = useModalInner(async (data) => {
    resetFields();
    setModalProps({ confirmLoading: false });
    isUpdate.value = !!data?.isUpdate;
    tronGoiCoSosList.value = [];
    vatTuTronGoisList.value = [];
    nhomVatTuRes.value = null;
    fileList.value = [];
    selectedNhomVatTu.value = null;
    selectedThuongHieu.value = null;

    await loadOptions();

    if (unref(isUpdate)) {
      recordId.value = data.record.id;
      
      if (data.record.coSo) {
        tronGoiCoSosList.value = [{
          coSoId: data.record.coSo.id,
          sanLuongToiThieu: data.record.sanLuongToiThieu || 0,
          sanLuongToiDa: data.record.sanLuongToiDa || 0,
        }];
      }

      if (data.record.vatTuTronGois && data.record.vatTuTronGois.length > 0) {
        vatTuTronGoisList.value = data.record.vatTuTronGois.map((v: any) => ({
          vatTuId: v.vatTu?.id || 0,
          moTa: v.moTa || '',
          soLuong: v.soLuong || 0,
          gia: v.gia || 0,
          gm: v.gm || 0,
          thoiGianBaoHanh: v.thoiGianBaoHanh || 0,
          duocBaoHanh: v.duocBaoHanh || false,
          trangThai: v.trangThai || 1,
        }));
      }

      if (data.record.tepTin?.url) {
        fileList.value = [{
          uid: String(data.record.tepTin.id ?? 'exist-1'),
          name: data.record.tepTin.ten || 'image',
          status: 'done',
          url: data.record.tepTin.url,
          thumbUrl: data.record.tepTin.url,
          isExisting: true,
        }];
      }

      setFieldsValue({
        ...data.record,
        nhomTronGoiId: data.record.nhomTronGoi?.id,
      });
    }
  });

  async function loadOptions() {
    try {
      const [coSoRes, nhomTronGoiRes, thuongHieuRes, nhomVatTuRes] = await Promise.allSettled([
        getAllCoSo(),
        getAllNhomTronGoi(),
        getAllThuongHieu(),
        getAllNhomVatTu(),
      ]);

      if (coSoRes.status === 'fulfilled' && coSoRes.value?.data) {
        // Response có thể là PageResponse hoặc Array
        const data = coSoRes.value.data;
        const list = Array.isArray(data) ? data : (data.content || []);
        coSoOptions.value = list.map((item: CoSoDto) => ({
          label: item.ten || item.ma || String(item.id),
          value: item.id,
        }));
      }

      if (nhomTronGoiRes.status === 'fulfilled' && nhomTronGoiRes.value?.data) {
        // Response có thể là PageResponse hoặc Array
        const data = nhomTronGoiRes.value.data;
        const list = Array.isArray(data) ? data : (data.content || []);
        nhomTronGoiOptions.value = list.map((item: NhomTronGoiDto) => ({
          label: item.ten || String(item.id),
          value: item.id,
        }));
        
        // Extract unique nhomVatTu from nganhHang if available
        const nhomVatTuSet = new Set<string>();
        list.forEach((item: any) => {
          if (item.nganhHang?.ten) {
            nhomVatTuSet.add(JSON.stringify({
              label: item.nganhHang.ten,
              value: item.nganhHang.id
            }));
          }
        });
        nhomVatTuOptions.value = Array.from(nhomVatTuSet).map(s => JSON.parse(s));
      }

      if (thuongHieuRes.status === 'fulfilled' && thuongHieuRes.value?.data) {
        const list = Array.isArray(thuongHieuRes.value.data) ? thuongHieuRes.value.data : [];
        thuongHieuOptions.value = list.map((item: ThuongHieuDto) => ({
          label: item.ten || item.tenQuocTe || String(item.id),
          value: item.id,
        }));
        selectedThuongHieu.value = undefined;
      }

      if (nhomVatTuRes.status === 'fulfilled' && nhomVatTuRes.value?.data?.content) {
        const list = Array.isArray(nhomVatTuRes.value.data.content) ? nhomVatTuRes.value.data.content : [];
        nhomVatTuOptions.value = list.map((item: NhomVatTuDto) => ({
          label: item.ten || String(item.id),
          value: item.id,
        }));
        selectedNhomVatTu.value = undefined;
      }

      updateSchema([
        {
          field: 'nhomTronGoiId',
          componentProps: {
            options: nhomTronGoiOptions.value,
          },
        },
      ]);
    } catch (error) {
      console.error('Error loading options:', error);
      message.error('Không thể tải dữ liệu danh mục');
    }
  }

  async function handleNhomVatTuChange(value: number) {
    selectedNhomVatTu.value = value;
    await loadVatTuOptions();
  }

  async function handleThuongHieuChange(value: number) {
    selectedThuongHieu.value = value;
    await loadVatTuOptions();
  }

  async function loadVatTuOptions() {
    try {
      const response = await filterVatTu(selectedNhomVatTu.value, selectedThuongHieu.value);
      if (response?.data?.content) {
        vatTuOptions.value = response.data.content.map((item: VatTuDto) => ({
          label: item.ten || String(item.id),
          value: item.id,
        }));
      }
    } catch (error) {
      console.error('Error loading vat tu:', error);
      message.error('Không thể tải danh sách vật tư');
    }
  }

  const getTitle = computed(() => (!unref(isUpdate) ? 'Tạo trọn gói' : 'Chỉnh sửa trọn gói'));

  function filterOption(input: string, option: any) {
    return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  }

  function handleAddCoSo() {
    let coSoIdBegin = coSoOptions.value.length > 0 ? coSoOptions.value[0].value : undefined;
    tronGoiCoSosList.value.push({
      coSoId: coSoIdBegin,
      sanLuongToiThieu: 0,
      sanLuongToiDa: 0,
    });
  }

  function handleRemoveCoSo(index: number) {
    tronGoiCoSosList.value.splice(index, 1);
  }

  function handleAddVatTu() {
    let vatTuIdBegin = vatTuOptions.value.length > 0 ? vatTuOptions.value[0].value : undefined;
    vatTuTronGoisList.value.push({
      vatTuId: vatTuIdBegin,
      moTa: '',
      soLuong: 0,
      gia: 0,
      gm: 0,
      thoiGianBaoHanh: 0,
      duocBaoHanh: false,
      trangThai: 1,
    });
  }

  function handleRemoveVatTu(index: number) {
    vatTuTronGoisList.value.splice(index, 1);
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

  function onNativeFile(e: Event) {
    const input = e.target as HTMLInputElement;
    const file = input.files?.[0];
    if (!file) return;

    const isImage = file.type && file.type.startsWith('image/');
    if (!isImage) {
      message.error('Chỉ chấp nhận file ảnh!');
      return;
    }

    const isLt5M = file.size / 1024 / 1024 < 5;
    if (!isLt5M) {
      message.error('Kích thước ảnh phải nhỏ hơn 5MB!');
      return;
    }

    try {
      const url = URL.createObjectURL(file);
      fileList.value = [{
        uid: `${Date.now()}`,
        name: file.name,
        status: 'done',
        originFileObj: file,
        url,
        thumbUrl: url,
      }];
    } catch (err) {
      console.error('onNativeFile error', err);
    }

    input.value = '';
  }

  const beforeUpload: UploadProps['beforeUpload'] = (file) => {
    const isImage = file.type && file.type.startsWith('image/');
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
      fileList.value = [{
        uid: file.uid ?? `${Date.now()}`,
        name: file.name,
        status: 'done',
        originFileObj: file,
        url,
        thumbUrl: url,
      }];
    } catch (e) {
      console.error('beforeUpload error', e);
    }

    return false;
  };

  function onUploadChange(e: any) {
    try {
      const fl = (e && e.fileList) || [];
      if (fl.length > 0) {
        const f = fl[0];
        const origin = f.originFile || f.originFileObj;
        const url = origin ? URL.createObjectURL(origin) : f.url || f.thumbUrl;
        fileList.value = [{
          uid: f.uid,
          name: f.name,
          status: f.status || 'done',
          url,
          thumbUrl: url,
          originFileObj: origin,
        }];
      } else {
        fileList.value = [];
      }
    } catch (err) {
      console.error('onUploadChange error', err);
    }
  }

  function handleRemoveFile() {
    try {
      const url = fileList.value[0]?.url || fileList.value[0]?.thumbUrl;
      if (url && url.startsWith('blob:')) {
        URL.revokeObjectURL(url);
      }
      fileList.value = [];
    } catch (err) {
      console.error('handleRemoveFile error', err);
    }
  }

  async function handleSubmit() {
    try {
      const values = await validate();
      setModalProps({ confirmLoading: true });

      // Validate tronGoiCoSos
      if (tronGoiCoSosList.value.length === 0) {
        message.error('Vui lòng thêm ít nhất một cơ sở');
        setModalProps({ confirmLoading: false });
        return;
      }

      for (let i = 0; i < tronGoiCoSosList.value.length; i++) {
        const coSo = tronGoiCoSosList.value[i];
        if (!coSo.coSoId) {
          message.error(`Vui lòng chọn cơ sở cho thông tin ${i + 1}`);
          setModalProps({ confirmLoading: false });
          return;
        }
      }

      // Validate vatTuTronGois
      if (vatTuTronGoisList.value.length === 0) {
        message.error('Vui lòng thêm ít nhất một vật tư');
        setModalProps({ confirmLoading: false });
        return;
      }

      for (let i = 0; i < vatTuTronGoisList.value.length; i++) {
        const vatTu = vatTuTronGoisList.value[i];
        if (!vatTu.vatTuId) {
          message.error(`Vui lòng chọn vật tư cho thông tin ${i + 1}`);
          setModalProps({ confirmLoading: false });
          return;
        }
      }

      const submitData = {
        tronGoiCoSos: tronGoiCoSosList.value,
        nhomTronGoiId: values.nhomTronGoiId,
        ten: values.ten,
        loaiHeThong: values.loaiHeThong,
        loaiPha: values.loaiPha,
        moTa: values.moTa || '',
        tongGia: values.tongGia,
        banChay: values.banChay,
        vatTuTronGois: vatTuTronGoisList.value.map(v => ({
          vatTuId: v.vatTuId,
          moTa: v.moTa || '',
          soLuong: v.soLuong,
          gia: v.gia,
          gm: v.gm,
          thoiGianBaoHanh: v.thoiGianBaoHanh || 0,
          duocBaoHanh: v.duocBaoHanh,
          trangThai: v.trangThai,
        })),
      };

      const file = fileList.value[0]?.originFileObj || null;

      const result = await createTronGoi(submitData, file);

      if (result.status === 200 || result.status === 201) {
        message.success('Tạo mới thành công');
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
</script>

<style lang="less" scoped>
.tron-goi-co-sos-container,
.vat-tu-tron-gois-container {
  .co-so-item,
  .vat-tu-item {
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

.file-container {
  .upload-hint {
    margin-top: 8px;
    color: #999;
    font-size: 12px;
  }
}
</style>