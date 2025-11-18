<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    title="Chỉnh sửa trọn gói"
    :width="1200"
    @ok="handleSubmit"
    :confirmLoading="loading"
  >
    <a-form
      ref="formRef"
      :model="formState"
      :label-col="{ span: 6 }"
      :wrapper-col="{ span: 18 }"
    >
      <!-- Thông tin cơ bản -->
      <a-divider orientation="left">Thông tin cơ bản</a-divider>
      
      <a-form-item
        label="Tên trọn gói"
        name="ten"
        :rules="[{ required: true, message: 'Vui lòng nhập tên trọn gói' }]"
      >
        <a-input
          v-model:value="formState.ten"
          placeholder="Nhập tên trọn gói"
          :maxlength="400"
        />
      </a-form-item>

      <a-form-item
        label="Nhóm trọn gói"
        name="nhomTronGoiId"
        :rules="[{ required: true, message: 'Vui lòng chọn nhóm trọn gói' }]"
      >
        <a-select
          v-model:value="formState.nhomTronGoiId"
          placeholder="Chọn nhóm trọn gói"
          show-search
          :filter-option="filterOption"
          :options="nhomTronGoiOptions"
        />
      </a-form-item>

      <a-form-item
        label="Loại hệ thống"
        name="loaiHeThong"
        :rules="[{ required: true, message: 'Vui lòng chọn loại hệ thống' }]"
      >
        <a-select
          v-model:value="formState.loaiHeThong"
          placeholder="Chọn loại hệ thống"
          :options="[
            { label: 'On-Grid', value: 'On-Grid' },
            { label: 'Hy-Brid', value: 'Hy-Brid' },
          ]"
        />
      </a-form-item>

      <a-form-item
        label="Loại pha"
        name="loaiPha"
        :rules="[{ required: true, message: 'Vui lòng chọn loại pha' }]"
      >
        <a-select
          v-model:value="formState.loaiPha"
          placeholder="Chọn loại pha"
          :options="[
            { label: '1 pha', value: '1 pha' },
            { label: '3 pha', value: '3 pha' },
          ]"
        />
      </a-form-item>

      <a-form-item label="Mô tả" name="moTa">
        <a-textarea
          v-model:value="formState.moTa"
          placeholder="Nhập mô tả"
          :rows="3"
        />
      </a-form-item>

      <a-form-item
        label="Tổng giá"
        name="tongGia"
        :rules="[{ required: true, message: 'Vui lòng nhập tổng giá' }]"
      >
        <a-input-number
          v-model:value="formState.tongGia"
          placeholder="Nhập tổng giá"
          :min="0"
          :formatter="value => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
          :parser="value => value.replace(/\$\s?|(,*)/g, '')"
          style="width: 100%"
        />
      </a-form-item>

      <a-form-item label="Bán chạy" name="banChay">
        <a-radio-group v-model:value="formState.banChay" button-style="solid">
          <a-radio-button :value="true">Có</a-radio-button>
          <a-radio-button :value="false">Không</a-radio-button>
        </a-radio-group>
      </a-form-item>

      <a-form-item
        label="Trạng thái"
        name="trangThai"
        :rules="[{ required: true, message: 'Vui lòng chọn trạng thái' }]"
      >
        <a-radio-group v-model:value="formState.trangThai" button-style="solid">
          <a-radio-button :value="1">Kích hoạt</a-radio-button>
          <a-radio-button :value="0">Vô hiệu</a-radio-button>
        </a-radio-group>
      </a-form-item>

      <!-- Thông tin cơ sở -->
      <a-divider orientation="left">Thông tin cơ sở</a-divider>
      
      <a-form-item :wrapper-col="{ span: 24 }">
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
                  <a-select
                    v-model:value="item.coSoId"
                    placeholder="Chọn cơ sở"
                    show-search
                    :filter-option="filterOption"
                    :options="coSoOptions"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-form-item label="Sản lượng TT (kW)" :required="true">
                  <a-input-number
                    v-model:value="item.sanLuongToiThieu"
                    placeholder="Sản lượng tối thiểu"
                    :min="0"
                    style="width: 100%"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="8">
                <a-form-item label="Sản lượng TĐ (kW)" :required="true">
                  <a-input-number
                    v-model:value="item.sanLuongToiDa"
                    placeholder="Sản lượng tối đa"
                    :min="0"
                    style="width: 100%"
                  />
                </a-form-item>
              </a-col>
            </a-row>
          </a-card>
        </div>
      </a-form-item>

      <!-- Vật tư trong gói -->
      <a-divider orientation="left">Vật tư trong gói</a-divider>
      
      <a-form-item :wrapper-col="{ span: 24 }">
        <a-row :gutter="16" style="margin-bottom: 16px">
          <a-col :span="12">
            <a-form-item label="Nhóm vật tư" :label-col="{ span: 8 }">
              <a-select
                v-model:value="selectedNhomVatTu"
                placeholder="Chọn nhóm vật tư"
                show-search
                :filter-option="filterOption"
                :options="nhomVatTuOptions"
                @change="handleNhomVatTuChange"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="Thương hiệu" :label-col="{ span: 8 }">
              <a-select
                v-model:value="selectedThuongHieu"
                placeholder="Chọn thương hiệu"
                show-search
                :filter-option="filterOption"
                :options="thuongHieuOptions"
                @change="handleThuongHieuChange"
              />
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
                  <a-select
                    v-model:value="item.vatTuId"
                    placeholder="Chọn vật tư"
                    show-search
                    :filter-option="filterOption"
                    :options="vatTuOptions"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="Số lượng" :required="true">
                  <a-input-number
                    v-model:value="item.soLuong"
                    placeholder="Nhập số lượng"
                    :min="0"
                    style="width: 100%"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="Giá" :required="true">
                  <a-input-number
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
                  <a-input-number
                    v-model:value="item.gm"
                    placeholder="Nhập GM"
                    :min="0"
                    :max="100"
                    style="width: 100%"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="Thời gian BH (tháng)">
                  <a-input-number
                    v-model:value="item.thoiGianBaoHanh"
                    placeholder="Nhập thời gian"
                    :min="0"
                    style="width: 100%"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="12">
                <a-form-item label="Được bảo hành">
                  <a-radio-group v-model:value="item.duocBaoHanh" button-style="solid">
                    <a-radio-button :value="true">Có</a-radio-button>
                    <a-radio-button :value="false">Không</a-radio-button>
                  </a-radio-group>
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
      </a-form-item>

      <!-- Hình ảnh -->
      <a-divider orientation="left">Hình ảnh</a-divider>
      
      <a-form-item label="Tải ảnh lên" :wrapper-col="{ span: 18 }">
        <a-upload
          v-model:file-list="fileList"
          list-type="picture-card"
          :before-upload="beforeUpload"
          @remove="handleRemoveFile"
          accept="image/*"
          :max-count="1"
        >
          <div v-if="fileList.length < 1">
            <PlusOutlined />
            <div style="margin-top: 8px">Tải ảnh lên</div>
          </div>
        </a-upload>
        <div class="upload-hint">Tối đa 1 ảnh, định dạng: JPG, PNG, GIF</div>
      </a-form-item>
    </a-form>
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref, reactive } from 'vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { PlusOutlined, DeleteOutlined } from '@ant-design/icons-vue';
  import { 
    updateTronGoi,
    getAllCoSo, 
    getAllNhomTronGoi,
    getAllThuongHieu,
    filterVatTu,
    getAllNhomVatTu,
    type ThongTinTronGoiCoSoCreatingDto,
    type VatTuTronGoiCreatingDto,
    type CoSoDto,
    type NhomTronGoiDto,
    type ThuongHieuDto,
    type VatTuDto,
    type NhomVatTuDto,
  } from './tronGoi';
  import { message } from 'ant-design-vue';
  import type { UploadProps } from 'ant-design-vue';

  defineOptions({ name: 'UpdateTronGoiModal' });

  const emit = defineEmits(['success', 'register']);

  const formRef = ref();
  const loading = ref(false);
  const recordId = ref<number>();
  
  const formState = reactive({
    ten: '',
    nhomTronGoiId: undefined as number | undefined,
    loaiHeThong: undefined as string | undefined,
    loaiPha: undefined as string | undefined,
    moTa: '',
    tongGia: 0,
    banChay: false,
    trangThai: 1,
  });

  const tronGoiCoSosList = ref<ThongTinTronGoiCoSoCreatingDto[]>([]);
  const vatTuTronGoisList = ref<Array<VatTuTronGoiCreatingDto & { trangThai: number }>>([]);
  const fileList = ref<any[]>([]);

  const coSoOptions = ref<Array<{ label: string; value: number }>>([]);
  const nhomTronGoiOptions = ref<Array<{ label: string; value: number }>>([]);
  const nhomVatTuOptions = ref<Array<{ label: string; value: number }>>([]);
  const thuongHieuOptions = ref<Array<{ label: string; value: number }>>([]);
  const vatTuOptions = ref<Array<{ label: string; value: number }>>([]);

  const selectedNhomVatTu = ref<number | undefined>(undefined);
  const selectedThuongHieu = ref<number | undefined>(undefined);

  const [registerModal, { closeModal }] = useModalInner(async (data) => {
    await loadOptions();
    
    if (data?.record) {
      recordId.value = data.record.id;
      
      // Load form data
      Object.assign(formState, {
        ten: data.record.ten || '',
        nhomTronGoiId: data.record.nhomTronGoi?.id,
        loaiHeThong: data.record.loaiHeThong,
        loaiPha: data.record.loaiPha,
        moTa: data.record.moTa || '',
        tongGia: data.record.tongGia || 0,
        banChay: data.record.banChay || false,
        trangThai: data.record.trangThai ?? 1,
      });

      // Load cơ sở
      if (data.record.coSo) {
        tronGoiCoSosList.value = [{
          coSoId: data.record.coSo.id,
          sanLuongToiThieu: data.record.sanLuongToiThieu || 0,
          sanLuongToiDa: data.record.sanLuongToiDa || 0,
        }];
      }

      // Load vật tư
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

      // Load file
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
        const data = coSoRes.value.data;
        const list = Array.isArray(data) ? data : (data.content || []);
        coSoOptions.value = list.map((item: CoSoDto) => ({
          label: item.ten || item.ma || String(item.id),
          value: item.id,
        }));
      }

      if (nhomTronGoiRes.status === 'fulfilled' && nhomTronGoiRes.value?.data) {
        const data = nhomTronGoiRes.value.data;
        const list = Array.isArray(data) ? data : (data.content || []);
        nhomTronGoiOptions.value = list.map((item: NhomTronGoiDto) => ({
          label: item.ten || String(item.id),
          value: item.id,
        }));
      }

      if (thuongHieuRes.status === 'fulfilled' && thuongHieuRes.value?.data) {
        const list = Array.isArray(thuongHieuRes.value.data) ? thuongHieuRes.value.data : [];
        thuongHieuOptions.value = list.map((item: ThuongHieuDto) => ({
          label: item.ten || item.tenQuocTe || String(item.id),
          value: item.id,
        }));
      }

      if (nhomVatTuRes.status === 'fulfilled' && nhomVatTuRes.value?.data?.content) {
        const list = Array.isArray(nhomVatTuRes.value.data.content) ? nhomVatTuRes.value.data.content : [];
        nhomVatTuOptions.value = list.map((item: NhomVatTuDto) => ({
          label: item.ten || String(item.id),
          value: item.id,
        }));
      }
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

  function filterOption(input: string, option: any) {
    return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  }

  function handleAddCoSo() {
    const coSoIdBegin = coSoOptions.value.length > 0 ? coSoOptions.value[0].value : undefined;
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
    const vatTuIdBegin = vatTuOptions.value.length > 0 ? vatTuOptions.value[0].value : undefined;
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

    return false;
  };

  function handleRemoveFile() {
    fileList.value = [];
  }

  async function handleSubmit() {
    try {
      await formRef.value?.validate();
      loading.value = true;

      if (!recordId.value) {
        message.error('Không tìm thấy ID bản ghi');
        return;
      }

      // Validate cơ sở
      if (tronGoiCoSosList.value.length === 0) {
        message.error('Vui lòng thêm ít nhất một cơ sở');
        return;
      }

      for (let i = 0; i < tronGoiCoSosList.value.length; i++) {
        const coSo = tronGoiCoSosList.value[i];
        if (!coSo.coSoId) {
          message.error(`Vui lòng chọn cơ sở cho thông tin ${i + 1}`);
          return;
        }
      }

      // Validate vật tư
      if (vatTuTronGoisList.value.length === 0) {
        message.error('Vui lòng thêm ít nhất một vật tư');
        return;
      }

      for (let i = 0; i < vatTuTronGoisList.value.length; i++) {
        const vatTu = vatTuTronGoisList.value[i];
        if (!vatTu.vatTuId) {
          message.error(`Vui lòng chọn vật tư cho thông tin ${i + 1}`);
          return;
        }
      }

      const submitData = {
        ...formState,
        tronGoiCoSos: tronGoiCoSosList.value,
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
      const result = await updateTronGoi(recordId.value, submitData, file);

      if (result.status === 200 || result.status === 201) {
        message.success('Cập nhật thành công');
        closeModal();
        emit('success');
      } else {
        message.error(result.message || 'Có lỗi xảy ra');
      }
    } catch (error: any) {
      console.error('Submit error:', error);
      if (error.errorFields) {
        message.error('Vui lòng kiểm tra lại thông tin');
      } else {
        message.error(error?.message || 'Có lỗi xảy ra khi lưu dữ liệu');
      }
    } finally {
      loading.value = false;
    }
  }
</script>

<style lang="less" scoped>
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
  margin-bottom: 16px;
}

.upload-hint {
  margin-top: 8px;
  color: #999;
  font-size: 12px;
}
</style>