<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    title="Tạo hợp đồng"
    :width="1200"
    @ok="handleSubmit"
    :confirmLoading="loading"
  >
    <Form ref="formRef" :model="formState" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
      <!-- THÔNG TIN CƠ BẢN -->
      <Divider orientation="center">Thông tin cơ bản</Divider>
      
      <FormItem label="Tên hợp đồng" name="ten" :rules="[{ required: true, message: 'Vui lòng nhập tên' }]">
        <Input v-model:value="formState.ten" placeholder="Nhập tên hợp đồng" :maxlength="400" />
      </FormItem>

      <Row :gutter="16">
        <Col :span="12">
          <FormItem label="Cơ sở" name="coSoId" :rules="[{ required: true, message: 'Vui lòng chọn' }]" :label-col="{ span: 12 }" :wrapper-col="{ span: 12 }">
            <Select
              v-model:value="formState.coSoId"
              placeholder="Chọn cơ sở"
              show-search
              :filter-option="filterOption"
              :options="coSoOptions"
              @change="handleChangCoSo"
            />
          </FormItem>
        </Col>
        <Col :span="12">
          <FormItem label="Ngành hàng" name="nghanhHangId" :rules="[{ required: true, message: 'Vui lòng chọn' }]" :label-col="{ span: 12 }" :wrapper-col="{ span: 12 }">
            <Select
              v-model:value="formState.nghanhHangId"
              placeholder="Chọn ngành hàng"
              show-search
              :filter-option="filterOption"
              :options="nghanhHangOptions"
            />
          </FormItem>
        </Col>
      </Row>

      <Row :gutter="16">
        <Col :span="12">
          <FormItem label="Loại hệ thống" name="loaiHeThong" :rules="[{ required: true, message: 'Vui lòng chọn' }]" :label-col="{ span: 12 }" :wrapper-col="{ span: 12 }">
            <Select
              v-model:value="formState.loaiHeThong"
              placeholder="Chọn loại hệ thống"
              :options="[
                { label: 'On-Grid', value: 'On-Grid' },
                { label: 'Hy-Brid', value: 'Hy-Brid' },
              ]"
            />
          </FormItem>
        </Col>
        <Col :span="12">
          <FormItem label="Loại pha" name="loaiPha" :rules="[{ required: true, message: 'Vui lòng chọn' }]" :label-col="{ span: 12 }" :wrapper-col="{ span: 12 }">
            <Select
              v-model:value="formState.loaiPha"
              placeholder="Chọn loại pha"
              :options="[
                { label: '1 pha', value: '1 pha' },
                { label: '3 pha', value: '3 pha' },
              ]"
              @change="handleSoPhaChange"
            />
          </FormItem>
        </Col>
      </Row>

      <Row :gutter="16">
        <Col :span="12">
          <FormItem label="Sản lượng TT (kW)" name="sanLuongToiThieu" :label-col="{ span: 12 }" :wrapper-col="{ span: 12 }">
            <InputNumber v-model:value="formState.sanLuongToiThieu" :min="0" style="width: 100%" />
          </FormItem>
        </Col>
        <Col :span="12">
          <FormItem label="Sản lượng TĐ (kW)" name="sanLuongToiDa" :label-col="{ span: 12 }" :wrapper-col="{ span: 12 }">
            <InputNumber v-model:value="formState.sanLuongToiDa" :min="0" style="width: 100%" />
          </FormItem>
        </Col>
      </Row>

      <FormItem label="Mô tả" name="moTa">
        <Textarea v-model:value="formState.moTa" placeholder="Nhập mô tả" :rows="3" />
      </FormItem>

      <!-- THÔNG TIN KHÁCH HÀNG -->
      <Divider orientation="center">Thông tin khách hàng</Divider>

      <Row :gutter="16">
        <Col :span="12">
          <FormItem label="Họ và tên" name="hoVaTenKhachHang" :rules="[{ required: true, message: 'Vui lòng nhập' }]" :label-col="{ span: 12 }" :wrapper-col="{ span: 12 }">
            <Input v-model:value="formState.hoVaTenKhachHang" placeholder="Nhập họ tên" />
          </FormItem>
        </Col>
        <Col :span="12">
          <FormItem label="Số điện thoại" name="sdtKhachHang" :rules="[{ required: true, message: 'Vui lòng nhập' }]" :label-col="{ span: 12 }" :wrapper-col="{ span: 12 }">
            <Input v-model:value="formState.sdtKhachHang" placeholder="Nhập SĐT" />
          </FormItem>
        </Col>
      </Row>

      <Row :gutter="16">
        <Col :span="12">
          <FormItem label="Email" name="emailKhachHang" :rules="[{ type: 'email', message: 'Email không hợp lệ' }]" :label-col="{ span: 12 }" :wrapper-col="{ span: 12 }">
            <Input v-model:value="formState.emailKhachHang" placeholder="Nhập email" />
          </FormItem>
        </Col>
        <Col :span="12">
          <FormItem label="Giới tính" name="gioiTinhKhachHang" :label-col="{ span: 12 }" :wrapper-col="{ span: 12 }">
            <RadioGroup v-model:value="formState.gioiTinhKhachHang" button-style="solid">
              <RadioButton :value="true">Nam</RadioButton>
              <RadioButton :value="false">Nữ</RadioButton>
            </RadioGroup>
          </FormItem>
        </Col>
      </Row>

      <FormItem label="Địa chỉ" name="diaChiKhachHang">
        <Textarea v-model:value="formState.diaChiKhachHang" placeholder="Nhập địa chỉ" :rows="2" />
      </FormItem>

      <FormItem label="Người giới thiệu" name="nguoiGioiThieuId">
        <Select
          v-model:value="formState.nguoiGioiThieuId"
          placeholder="Chọn người giới thiệu"
          show-search
          allow-clear
          :filter-option="filterOption"
          :options="nguoiDungOptions"
        />
      </FormItem>

      <!-- VẬT TƯ TRONG HỢP ĐỒNG -->
      <Divider orientation="center">Vật tư trong hợp đồng</Divider>

      <VatTuGroupSection
        v-for="group in vatTuGroups"
        :key="group.code"
        :title="group.title"
        :items="group.items"
        :options="group.options"
        :vat-tu-data="group.data"
        :co-so-ma="selectedCoSoMa"
        :show-add-button="group.allowMultiple"
        @add="handleAddVatTu(group.code)"
        @remove="(index) => handleRemoveVatTu(group.code, index)"
        @update="(index, value) => handleUpdateVatTu(group.code, index, value)"
      />

      <!-- TỔNG GIÁ -->
      <Divider />
      <Row :gutter="16">
        <Col :span="12">
          <FormItem label="Giá khung sắt" name="giaKhungSat" :label-col="{ span: 12 }" :wrapper-col="{ span: 12 }">
            <InputNumber
              v-model:value="formState.giaKhungSat"
              :min="0"
              :formatter="formatCurrency"
              :parser="parseCurrency"
              style="width: 100%"
            />
          </FormItem>
        </Col>
        <Col :span="12">
          <FormItem label="Tổng giá" name="tongGia" :rules="[{ required: true, message: 'Vui lòng nhập' }]" :label-col="{ span: 12 }" :wrapper-col="{ span: 12 }">
            <InputNumber
              v-model:value="formState.tongGia"
              :min="0"
              :formatter="formatCurrency"
              :parser="parseCurrency"
              style="width: 100%"
            />
          </FormItem>
        </Col>
      </Row>

      <Row>
        <Col :span="24" style="text-align: center">
          <Button type="primary" @click="tinhTongGia">Tính tổng giá</Button>
        </Col>
      </Row>
    </Form>
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref, reactive, computed, watch } from 'vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import {
    Button, Col, Divider, Form, FormItem, Input, 
    InputNumber, RadioButton, RadioGroup, Row, Select, Textarea, message
  } from 'ant-design-vue';
  import VatTuGroupSection from './VatTuGroupSection.vue';
  import {
    createHopDong, getAllCoSo, getAllNganhHang, getAllNguoiDung, filterVatTu,
    type HopDongCreateDto, type VatTuHopDongCreatingDto, type VatTuDto
  } from './hopDong.ts';
  import { ceil } from 'lodash-es';

  defineOptions({ name: 'CreateHopDongModal' });
  const emit = defineEmits(['success', 'register']);

  const formRef = ref();
  const loading = ref(false);
  
  const formState = reactive<HopDongCreateDto>({
    coSoId: undefined,
    nghanhHangId: undefined,
    ten: '',
    loaiHeThong: 'On-Grid',
    loaiPha: '1 pha',
    sanLuongToiThieu: 0,
    sanLuongToiDa: 0,
    giaKhungSat: 0,
    moTa: '',
    nguoiGioiThieuId: undefined,
    emailKhachHang: '',
    sdtKhachHang: '',
    hoVaTenKhachHang: '',
    gioiTinhKhachHang: true,
    sinhNhatKhachHang: null,
    diaChiKhachHang: '',
    tongGia: 0,
    vatTuHopDongs: [],
  });

  // Options
  const coSoOptions = ref<Array<{ label: string; value: number; ma: string }>>([]);
  const nghanhHangOptions = ref<Array<{ label: string; value: number }>>([]);
  const nguoiDungOptions = ref<Array<{ label: string; value: number }>>([]);

  // Vat tu data
  const tamPinData = ref<VatTuDto[]>([]);
  const bienTanData = ref<VatTuDto[]>([]);
  const pinLuuTruData = ref<VatTuDto[]>([]);
  const heKhungNhomData = ref<VatTuDto[]>([]);
  const heDayDienData = ref<VatTuDto[]>([]);
  const tuDienData = ref<VatTuDto[]>([]);
  const heTiepDiaData = ref<VatTuDto[]>([]);
  const tronGoiLapDatData = ref<VatTuDto[]>([]);

  // Vat tu lists
  const tamPinList = ref<VatTuHopDongCreatingDto[]>([]);
  const bienTanList = ref<VatTuHopDongCreatingDto[]>([]);
  const pinLuuTruList = ref<VatTuHopDongCreatingDto[]>([]);
  const heKhungNhomList = ref<VatTuHopDongCreatingDto[]>([]);
  const heDayDienList = ref<VatTuHopDongCreatingDto[]>([]);
  const tuDienList = ref<VatTuHopDongCreatingDto[]>([]);
  const heTiepDiaList = ref<VatTuHopDongCreatingDto[]>([]);
  const tronGoiLapDatList = ref<VatTuHopDongCreatingDto[]>([]);

  const selectedCoSoMa = computed(() => {
    const coSo = coSoOptions.value.find(c => c.value === formState.coSoId);
    return coSo?.ma || 'HN';
  });

  const vatTuGroups = computed(() => [
    {
      code: 'TAM_PIN',
      title: 'Tấm pin',
      items: tamPinList.value,
      options: tamPinData.value.map(v => ({ label: v.ten, value: v.id })),
      data: tamPinData.value,
      allowMultiple: false,
    },
    {
      code: 'BIEN_TAN',
      title: 'Biến tần',
      items: bienTanList.value,
      options: bienTanData.value.map(v => ({ label: v.ten, value: v.id })),
      data: bienTanData.value,
      allowMultiple: true,
    },
    {
      code: 'PIN_LUU_TRU',
      title: 'Pin lưu trữ',
      items: pinLuuTruList.value,
      options: pinLuuTruData.value.map(v => ({ label: v.ten, value: v.id })),
      data: pinLuuTruData.value,
      allowMultiple: true,
    },
    {
      code: 'HE_KHUNG_NHOM',
      title: 'Hệ khung nhôm',
      items: heKhungNhomList.value,
      options: heKhungNhomData.value.map(v => ({ label: v.ten, value: v.id })),
      data: heKhungNhomData.value,
      allowMultiple: true,
    },
    {
      code: 'HE_DAY_DIEN',
      title: 'Hệ dây điện',
      items: heDayDienList.value,
      options: heDayDienData.value.map(v => ({ label: v.ten, value: v.id })),
      data: heDayDienData.value,
      allowMultiple: true,
    },
    {
      code: 'TU_DIEN',
      title: 'Tủ điện',
      items: tuDienList.value,
      options: tuDienData.value.map(v => ({ label: v.ten, value: v.id })),
      data: tuDienData.value,
      allowMultiple: true,
    },
    {
      code: 'HE_TIEP_DIA',
      title: 'Hệ tiếp địa',
      items: heTiepDiaList.value,
      options: heTiepDiaData.value.map(v => ({ label: v.ten, value: v.id })),
      data: heTiepDiaData.value,
      allowMultiple: true,
    },
    {
      code: 'TRON_GOI_LAP_DAT',
      title: 'Trọn gói lắp đặt',
      items: tronGoiLapDatList.value,
      options: tronGoiLapDatData.value.map(v => ({ label: v.ten, value: v.id })),
      data: tronGoiLapDatData.value,
      allowMultiple: true,
    },
  ]);

  // Watch cơ sở thay đổi để cập nhật giá
  watch(() => formState.coSoId, (newCoSoId) => {
    if (newCoSoId) {
      updateAllPricesByCoSo();
    }
  });

  const [registerModal, { closeModal }] = useModalInner(async () => {
    resetForm();
    await loadOptions();
    await loadAllVatTu();
  });

  function resetForm() {
    Object.assign(formState, {
      coSoId: undefined,
      nghanhHangId: undefined,
      ten: '',
      loaiHeThong: 'On-Grid',
      loaiPha: '1 pha',
      sanLuongToiThieu: 0,
      sanLuongToiDa: 0,
      giaKhungSat: 0,
      moTa: '',
      nguoiGioiThieuId: undefined,
      emailKhachHang: '',
      sdtKhachHang: '',
      hoVaTenKhachHang: '',
      gioiTinhKhachHang: true,
      sinhNhatKhachHang: null,
      diaChiKhachHang: '',
      tongGia: 0,
      vatTuHopDongs: [],
    });
    
    tamPinList.value = [];
    bienTanList.value = [];
    pinLuuTruList.value = [];
    heKhungNhomList.value = [];
    heDayDienList.value = [];
    tuDienList.value = [];
    heTiepDiaList.value = [];
    tronGoiLapDatList.value = [];
  }

  async function loadOptions() {
    try {
      const [coSoRes, nghanhHangRes, nguoiDungRes] = await Promise.allSettled([
        getAllCoSo(),
        getAllNganhHang(),
        getAllNguoiDung(),
      ]);

      if (coSoRes.status === 'fulfilled' && coSoRes.value?.data) {
        const list = Array.isArray(coSoRes.value.data) ? coSoRes.value.data : coSoRes.value.data.content || [];
        coSoOptions.value = list.map((item: any) => ({
          label: item.ten || item.ma,
          value: item.id,
          ma: item.ma,
        }));
      }

      if (nghanhHangRes.status === 'fulfilled' && nghanhHangRes.value?.data) {
        const list = Array.isArray(nghanhHangRes.value.data) ? nghanhHangRes.value.data : nghanhHangRes.value.data.content || [];
        nghanhHangOptions.value = list.map((item: any) => ({
          label: item.ten,
          value: item.id,
        }));
      }

      if (nguoiDungRes.status === 'fulfilled' && nguoiDungRes.value?.data) {
        const list = Array.isArray(nguoiDungRes.value.data) ? nguoiDungRes.value.data : nguoiDungRes.value.data.content || [];
        nguoiDungOptions.value = list.map((item: any) => ({
          label: item.hoVaTen || item.email,
          value: item.id,
        }));
      }
    } catch (error) {
      console.error('Error loading options:', error);
      message.error('Không thể tải dữ liệu danh mục');
    }
  }

  async function loadAllVatTu() {
    try {
      const groups = [
        { code: 'TAM_PIN', dataRef: tamPinData, listRef: tamPinList, init: true },
        { code: 'BIEN_TAN', dataRef: bienTanData, listRef: bienTanList, init: false },
        { code: 'PIN_LUU_TRU', dataRef: pinLuuTruData, listRef: pinLuuTruList, init: false },
        { code: 'HE_KHUNG_NHOM', dataRef: heKhungNhomData, listRef: heKhungNhomList, init: false },
        { code: 'HE_DAY_DIEN', dataRef: heDayDienData, listRef: heDayDienList, init: false },
        { code: 'TU_DIEN', dataRef: tuDienData, listRef: tuDienList, init: false },
        { code: 'HE_TIEP_DIA', dataRef: heTiepDiaData, listRef: heTiepDiaList, init: false },
        { code: 'TRON_GOI_LAP_DAT', dataRef: tronGoiLapDatData, listRef: tronGoiLapDatList, init: false },
      ];

      for (const group of groups) {
        const response = await filterVatTu(group.code);
        
        if (response?.data?.content && response.data.content.length > 0) {
          group.dataRef.value = response.data.content;
          
          if (group.init) {
            const firstVatTu = response.data.content[0];
            const latestGia = firstVatTu.thongTinGias[firstVatTu.thongTinGias.length - 1];
            const giaInfo = latestGia?.dsGia.find((g: any) => g.maCoSo === selectedCoSoMa.value);
            
            group.listRef.value = [{
              vatTuId: firstVatTu.id,
              moTa: firstVatTu.moTaBaoGia || '',
              soLuong: 1,
              giaBan: giaInfo?.giaBan || 0,
              gm: firstVatTu.nhomVatTu.gm,
              thoiGianBaoHanh: 0,
              duocBaoHanh: true,
              trangThai: 1,
            }];
          }
        }
      }
    } catch (error) {
      console.error('Error loading vat tu:', error);
      message.error('Không thể tải danh sách vật tư');
    }
  }

  function filterOption(input: string, option: any) {
    return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  }

  function formatCurrency(value: any) {
    return `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  }

  function parseCurrency(value: any) {
    return value.replace(/\$\s?|(,*)/g, '');
  }

  // Cập nhật giá tất cả vật tư khi đổi cơ sở
  function updateAllPricesByCoSo() {
    const allLists = [
      { list: tamPinList, data: tamPinData },
      { list: bienTanList, data: bienTanData },
      { list: pinLuuTruList, data: pinLuuTruData },
      { list: heKhungNhomList, data: heKhungNhomData },
      { list: heDayDienList, data: heDayDienData },
      { list: tuDienList, data: tuDienData },
      { list: heTiepDiaList, data: heTiepDiaData },
      { list: tronGoiLapDatList, data: tronGoiLapDatData },
    ];

    allLists.forEach(({ list, data }) => {
      list.value.forEach((item, index) => {
        const vatTu = data.value.find(v => v.id === item.vatTuId);
        if (vatTu) {
          const latestGia = vatTu.thongTinGias[vatTu.thongTinGias.length - 1];
          const giaInfo = latestGia?.dsGia.find((g: any) => g.maCoSo === selectedCoSoMa.value);
          if (giaInfo) {
            list.value[index].giaBan = giaInfo.giaBan || 0;
          }
        }
      });
    });

    message.success('Đã cập nhật giá theo cơ sở mới');
  }

  function handleChangCoSo() {
    // Hàm này được gọi khi thay đổi cơ sở
    // Watch sẽ tự động xử lý việc cập nhật giá
  }

  async function handleSoPhaChange() {
    const response = await filterVatTu('TU_DIEN');
    if (response?.data?.content) {
      tuDienData.value = response.data.content.filter((v: VatTuDto) => {
        const soPha = v.duLieuRieng?.so_pha?.giaTri as string;
        return soPha?.includes(formState.loaiPha);
      });
    }
  }

  function handleAddVatTu(code: string) {
    const groupMap: Record<string, { data: any; list: any }> = {
      BIEN_TAN: { data: bienTanData, list: bienTanList },
      PIN_LUU_TRU: { data: pinLuuTruData, list: pinLuuTruList },
      HE_KHUNG_NHOM: { data: heKhungNhomData, list: heKhungNhomList },
      HE_DAY_DIEN: { data: heDayDienData, list: heDayDienList },
      TU_DIEN: { data: tuDienData, list: tuDienList },
      HE_TIEP_DIA: { data: heTiepDiaData, list: heTiepDiaList },
      TRON_GOI_LAP_DAT: { data: tronGoiLapDatData, list: tronGoiLapDatList },
    };

    const group = groupMap[code];
    if (!group || group.data.value.length === 0) {
      message.error('Không có vật tư để thêm');
      return;
    }

    const firstVatTu = group.data.value[0];
    const latestGia = firstVatTu.thongTinGias[firstVatTu.thongTinGias.length - 1];
    const giaInfo = latestGia?.dsGia.find((g: any) => g.maCoSo === selectedCoSoMa.value);

    const newItem: VatTuHopDongCreatingDto = {
      vatTuId: firstVatTu.id,
      moTa: firstVatTu.moTaBaoGia || '',
      soLuong: 1,
      giaBan: giaInfo?.giaBan || 0,
      gm: firstVatTu.nhomVatTu.gm,
      thoiGianBaoHanh: 0,
      duocBaoHanh: true,
      trangThai: 1,
    };

    group.list.value.push(newItem);
    
    // Nếu là hệ khung nhôm, tính lại số lượng
    if (code === 'HE_KHUNG_NHOM') {
      updateHeKhungNhomQuantities();
    }
  }

  function handleRemoveVatTu(code: string, index: number) {
    const listMap: Record<string, any> = {
      BIEN_TAN: bienTanList,
      PIN_LUU_TRU: pinLuuTruList,
      HE_KHUNG_NHOM: heKhungNhomList,
      HE_DAY_DIEN: heDayDienList,
      TU_DIEN: tuDienList,
      HE_TIEP_DIA: heTiepDiaList,
      TRON_GOI_LAP_DAT: tronGoiLapDatList,
    };

    listMap[code]?.value.splice(index, 1);
  }

  function handleUpdateVatTu(code: string, index: number, value: VatTuHopDongCreatingDto) {
    const listMap: Record<string, any> = {
      TAM_PIN: tamPinList,
      BIEN_TAN: bienTanList,
      PIN_LUU_TRU: pinLuuTruList,
      HE_KHUNG_NHOM: heKhungNhomList,
      HE_DAY_DIEN: heDayDienList,
      TU_DIEN: tuDienList,
      HE_TIEP_DIA: heTiepDiaList,
      TRON_GOI_LAP_DAT: tronGoiLapDatList,
    };

    if (listMap[code]) {
      listMap[code].value[index] = value;
      
      // Nếu thay đổi tấm pin, cập nhật số lượng hệ khung nhôm
      if (code === 'TAM_PIN') {
        updateHeKhungNhomQuantities();
      }
      
      // Nếu thay đổi loại vật tư hệ khung nhôm, cập nhật số lượng
      if (code === 'HE_KHUNG_NHOM') {
        updateHeKhungNhomQuantities();
      }
    }
  }

  function calculateHeKhungNhomQuantity(ma: string, tamPinQty: number): number {
    if (ma.includes('kep_bien')) return ceil(tamPinQty / 5) * 4 + 4;
    if (ma.includes('kep_giua')) return ceil(tamPinQty / 5) * 8 + 4;
    if (ma.includes('full_rail')) return ceil((tamPinQty * 1.2 * 2) / 4);
    if (ma.includes('thanh_noi')) return ceil((tamPinQty * 1.2 * 2) / 4) * 2 + 4;
    if (ma.includes('chan_l')) return ceil((tamPinQty * 1.2 * 2) / 4) * 6;
    if (ma.includes('kep_tiep_dia')) return ceil(tamPinQty / 5) + 1;
    if (ma.includes('la_tiep_dia')) return (ceil(tamPinQty / 5) * 8 + 4) / 2;
    if (ma.includes('kep_day_dien')) return tamPinQty * 4;
    return 1;
  }

  function updateHeKhungNhomQuantities() {
    if (tamPinList.value.length === 0) return;

    heKhungNhomList.value.forEach((item, index) => {
      const vatTu = heKhungNhomData.value.find(v => v.id === item.vatTuId);
      if (!vatTu) return;

      const ma = vatTu.ma;
      const tamPinQty = tamPinList.value[0].soLuong;
      
      const newQty = calculateHeKhungNhomQuantity(ma, tamPinQty);
      heKhungNhomList.value[index].soLuong = newQty;
    });
  }

  function tinhTongGia() {
    const allVatTu = [
      ...tamPinList.value,
      ...bienTanList.value,
      ...pinLuuTruList.value,
      ...heKhungNhomList.value,
      ...heDayDienList.value,
      ...tuDienList.value,
      ...heTiepDiaList.value,
      ...tronGoiLapDatList.value,
    ];

    let total = formState.giaKhungSat || 0;
    allVatTu.forEach(v => {
      total += (v.giaBan || 0) * (v.soLuong || 0);
    });

    formState.tongGia = total;
    message.success('Đã tính tổng giá');
  }

  async function handleSubmit() {
    try {
      await formRef.value?.validate();
      loading.value = true;

      const allVatTu = [
        ...tamPinList.value,
        ...bienTanList.value,
        ...pinLuuTruList.value,
        ...heKhungNhomList.value,
        ...heDayDienList.value,
        ...tuDienList.value,
        ...heTiepDiaList.value,
        ...tronGoiLapDatList.value,
      ];

      if (allVatTu.length === 0) {
        message.error('Vui lòng thêm ít nhất một vật tư');
        return;
      }

      const submitData: HopDongCreateDto = {
        ...formState,
        vatTuHopDongs: allVatTu,
      };

      const result = await createHopDong(submitData);
      
      if (result.status === 200 || result.status === 201) {
        message.success('Tạo mới thành công');
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
  :deep(.ant-select) {
    width: 100%;
  }
</style>