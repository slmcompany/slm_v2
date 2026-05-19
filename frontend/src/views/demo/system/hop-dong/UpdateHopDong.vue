<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    title="Cập nhật hợp đồng"
    :width="1200"
    @ok="handleSubmit"
    :confirmLoading="loading"
  >
    <Spin :spinning="loadingInit" tip="Đang tải dữ liệu...">
      <Form ref="formRef" :model="formState" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
        <Divider orientation="center">Thông tin cơ bản</Divider>

        <FormItem
          label="Ngày bắt đầu bảo hành"
          name="taoLuc"
          :rules="[{ required: true, message: 'Vui lòng nhập ngày áp dụng bảo hành' }]"
        >
          <DatePicker
            v-model:value="formState.taoLuc"
            placeholder="Chọn ngày áp dụng bảo hành"
            valueFormat="YYYY-MM-DDTHH:mm:ss[Z]"
            showTime
          />
        </FormItem>

        <FormItem
          label="Tên hợp đồng"
          name="ten"
          :rules="[{ required: true, message: 'Vui lòng nhập tên' }]"
        >
          <Input v-model:value="formState.ten" placeholder="Nhập tên hợp đồng" :maxlength="400" />
        </FormItem>

        <Row :gutter="16">
          <Col :span="12">
            <FormItem
              label="Cơ sở"
              name="coSoId"
              :rules="[{ required: true, message: 'Vui lòng chọn cơ sở' }]"
              :label-col="{ span: 12 }"
              :wrapper-col="{ span: 12 }"
            >
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
            <FormItem
              label="Ngành hàng"
              name="nghanhHangId"
              :label-col="{ span: 12 }"
              :wrapper-col="{ span: 12 }"
            >
              <Select
                v-model:value="formState.nghanhHangId"
                placeholder="Ngành hàng hiện tại"
                show-search
                disabled
                :filter-option="filterOption"
                :options="nghanhHangOptions"
              />
            </FormItem>
          </Col>
        </Row>

        <Row :gutter="16">
          <Col :span="12">
            <FormItem
              label="Loại hệ thống"
              name="loaiHeThong"
              :rules="[{ required: true, message: 'Vui lòng chọn loại hệ thống' }]"
              :label-col="{ span: 12 }"
              :wrapper-col="{ span: 12 }"
            >
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
            <FormItem
              label="Loại pha"
              name="loaiPha"
              :rules="[{ required: true, message: 'Vui lòng chọn loại pha' }]"
              :label-col="{ span: 12 }"
              :wrapper-col="{ span: 12 }"
            >
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
            <FormItem
              label="Sản lượng TT (kW)"
              name="sanLuongToiThieu"
              :label-col="{ span: 12 }"
              :wrapper-col="{ span: 12 }"
            >
              <InputNumber
                v-model:value="formState.sanLuongToiThieu"
                :min="0"
                style="width: 100%"
              />
            </FormItem>
          </Col>
          <Col :span="12">
            <FormItem
              label="Sản lượng TĐ (kW)"
              name="sanLuongToiDa"
              :label-col="{ span: 12 }"
              :wrapper-col="{ span: 12 }"
            >
              <InputNumber v-model:value="formState.sanLuongToiDa" :min="0" style="width: 100%" />
            </FormItem>
          </Col>
        </Row>

        <FormItem label="Mô tả" name="moTa">
          <Textarea v-model:value="formState.moTa" placeholder="Nhập mô tả" :rows="3" />
        </FormItem>

        <Divider orientation="center">Thông tin khách hàng</Divider>

        <Row :gutter="16">
          <Col :span="12">
            <FormItem
              label="Họ và tên"
              name="hoVaTenKhachHang"
              :rules="[{ required: true, message: 'Vui lòng nhập họ tên' }]"
              :label-col="{ span: 12 }"
              :wrapper-col="{ span: 12 }"
            >
              <Input v-model:value="formState.hoVaTenKhachHang" placeholder="Nhập họ tên" />
            </FormItem>
          </Col>
          <Col :span="12">
            <FormItem
              label="Số điện thoại"
              name="sdtKhachHang"
              :rules="[{ required: true, message: 'Vui lòng nhập số điện thoại' }]"
              :label-col="{ span: 12 }"
              :wrapper-col="{ span: 12 }"
            >
              <Input v-model:value="formState.sdtKhachHang" placeholder="Nhập SĐT" />
            </FormItem>
          </Col>
        </Row>

        <Row :gutter="16">
          <Col :span="12">
            <FormItem
              label="Email"
              name="emailKhachHang"
              :rules="[{ type: 'email', message: 'Email không hợp lệ' }]"
              :label-col="{ span: 12 }"
              :wrapper-col="{ span: 12 }"
            >
              <Input v-model:value="formState.emailKhachHang" placeholder="Nhập email" />
            </FormItem>
          </Col>
          <Col :span="12">
            <FormItem
              label="Người giới thiệu"
              name="nguoiGioiThieuId"
              :rules="[{ required: true, message: 'Vui lòng chọn người giới thiệu' }]"
              :label-col="{ span: 12 }"
              :wrapper-col="{ span: 12 }"
            >
              <Select
                v-model:value="formState.nguoiGioiThieuId"
                placeholder="Chọn người giới thiệu"
                show-search
                :filter-option="filterOption"
                :options="nguoiDungOptions"
              />
            </FormItem>
          </Col>
        </Row>

        <FormItem label="Địa chỉ" name="diaChiKhachHang">
          <Textarea
            v-model:value="formState.diaChiKhachHang"
            placeholder="Nhập địa chỉ"
            :rows="2"
          />
        </FormItem>

        <Divider orientation="center">Vật tư trong hợp đồng</Divider>

        <VatTuGroupSection
          v-for="group in vatTuGroups"
          :key="group.code"
          :title="group.title"
          :items="group.items"
          :options="group.options"
          :vat-tu-data="group.data"
          :co-so-ma="selectedCoSoMa"
          :region="selectedCoSoTen"
          :show-add-button="group.allowMultiple"
          @add="handleAddVatTu(group.code)"
          @remove="(index) => handleRemoveVatTu(group.code, index)"
          @update="(index, value) => handleUpdateVatTu(group.code, index, value)"
        />

        <Divider />
        <Row :gutter="16">
          <Col :span="12">
            <FormItem
              label="Giá khung sắt"
              name="giaKhungSat"
              :label-col="{ span: 12 }"
              :wrapper-col="{ span: 12 }"
            >
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
            <FormItem
              label="Tổng giá"
              name="tongGia"
              :rules="[{ required: true, message: 'Vui lòng nhập tổng giá' }]"
              :label-col="{ span: 12 }"
              :wrapper-col="{ span: 12 }"
            >
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
    </Spin>
  </BasicModal>
</template>

<script lang="ts" setup>
  import { computed, nextTick, reactive, ref, watch, type Ref } from 'vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import {
    Button,
    Col,
    DatePicker,
    Divider,
    Form,
    FormItem,
    Input,
    InputNumber,
    Row,
    Select,
    Textarea,
    Spin,
    message,
  } from 'ant-design-vue';
  import { ceil } from 'lodash-es';
  import VatTuGroupSection from './VatTuGroupSection.vue';
  import {
    filterVatTu,
    getAllCoSo,
    getAllNganhHang,
    getAllNguoiDung,
    updateHopDong,
    type HopDongDto,
    type HopDongUpdateDto,
    type VatTuDto,
    type VatTuHopDongCreatingDto,
    type VatTuHopDongDto,
  } from './hopDong';

  defineOptions({ name: 'UpdateHopDongModal' });

  const emit = defineEmits(['success', 'register']);

  interface HopDongUpdateFormState extends HopDongUpdateDto {
    nghanhHangId?: number;
  }

  interface VatTuGroupConfig {
    code: string;
    title: string;
    dataRef: Ref<VatTuDto[]>;
    listRef: Ref<VatTuHopDongCreatingDto[]>;
    allowMultiple: boolean;
    skipLoad?: boolean;
  }

  const formRef = ref();
  const loading = ref(false);
  const loadingInit = ref(false);
  const isHydrating = ref(false);

  const formState = reactive<HopDongUpdateFormState>({
    id: undefined,
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
    diaChiKhachHang: '',
    tongGia: 0,
    taoLuc: '',
    vatTuHopDongs: [],
  });

  const coSoOptions = ref<Array<{ label: string; value: number; ma: string }>>([]);
  const nghanhHangOptions = ref<Array<{ label: string; value: number }>>([]);
  const nguoiDungOptions = ref<Array<{ label: string; value: number }>>([]);

  const tamPinData = ref<VatTuDto[]>([]);
  const bienTanData = ref<VatTuDto[]>([]);
  const pinLuuTruData = ref<VatTuDto[]>([]);
  const heKhungNhomData = ref<VatTuDto[]>([]);
  const heDayDienData = ref<VatTuDto[]>([]);
  const tuDienData = ref<VatTuDto[]>([]);
  const heTiepDiaData = ref<VatTuDto[]>([]);
  const tronGoiLapDatData = ref<VatTuDto[]>([]);
  const khacData = ref<VatTuDto[]>([]);

  const tamPinList = ref<VatTuHopDongCreatingDto[]>([]);
  const bienTanList = ref<VatTuHopDongCreatingDto[]>([]);
  const pinLuuTruList = ref<VatTuHopDongCreatingDto[]>([]);
  const heKhungNhomList = ref<VatTuHopDongCreatingDto[]>([]);
  const heDayDienList = ref<VatTuHopDongCreatingDto[]>([]);
  const tuDienList = ref<VatTuHopDongCreatingDto[]>([]);
  const heTiepDiaList = ref<VatTuHopDongCreatingDto[]>([]);
  const tronGoiLapDatList = ref<VatTuHopDongCreatingDto[]>([]);
  const khacList = ref<VatTuHopDongCreatingDto[]>([]);

  const vatTuGroupConfigs: VatTuGroupConfig[] = [
    {
      code: 'TAM_PIN',
      title: 'Tấm pin',
      dataRef: tamPinData,
      listRef: tamPinList,
      allowMultiple: false,
    },
    {
      code: 'BIEN_TAN',
      title: 'Biến tần',
      dataRef: bienTanData,
      listRef: bienTanList,
      allowMultiple: true,
    },
    {
      code: 'PIN_LUU_TRU',
      title: 'Pin lưu trữ',
      dataRef: pinLuuTruData,
      listRef: pinLuuTruList,
      allowMultiple: true,
    },
    {
      code: 'HE_KHUNG_NHOM',
      title: 'Hệ khung nhôm',
      dataRef: heKhungNhomData,
      listRef: heKhungNhomList,
      allowMultiple: true,
    },
    {
      code: 'HE_DAY_DIEN',
      title: 'Hệ dây điện',
      dataRef: heDayDienData,
      listRef: heDayDienList,
      allowMultiple: true,
    },
    {
      code: 'TU_DIEN',
      title: 'Tủ điện',
      dataRef: tuDienData,
      listRef: tuDienList,
      allowMultiple: true,
    },
    {
      code: 'HE_TIEP_DIA',
      title: 'Hệ tiếp địa',
      dataRef: heTiepDiaData,
      listRef: heTiepDiaList,
      allowMultiple: true,
    },
    {
      code: 'TRON_GOI_LAP_DAT',
      title: 'Trọn gói lắp đặt',
      dataRef: tronGoiLapDatData,
      listRef: tronGoiLapDatList,
      allowMultiple: true,
    },
    {
      code: 'KHAC',
      title: 'Vật tư khác',
      dataRef: khacData,
      listRef: khacList,
      allowMultiple: true,
      skipLoad: true,
    },
  ];

  const selectedCoSoMa = computed(() => {
    const coSo = coSoOptions.value.find((c) => c.value === formState.coSoId);
    return coSo?.ma || 'HN';
  });

  const selectedCoSoTen = computed(() => {
    const coSo = coSoOptions.value.find((c) => c.value === formState.coSoId);
    return coSo?.label || 'Miền Bắc';
  });

  const vatTuGroups = computed(() =>
    vatTuGroupConfigs
      .filter((group) => group.code !== 'KHAC' || group.listRef.value.length > 0)
      .map((group) => ({
        code: group.code,
        title: group.title,
        items: group.listRef.value,
        options: group.dataRef.value.map((v) => ({ label: v.ten, value: v.id })),
        data: group.dataRef.value,
        allowMultiple: group.allowMultiple,
      })),
  );

  watch(
    () => formState.coSoId,
    (newCoSoId, oldCoSoId) => {
      if (isHydrating.value || !newCoSoId || !oldCoSoId || newCoSoId === oldCoSoId) {
        return;
      }

      updateAllPricesByCoSo();
    },
  );

  const [registerModal, { closeModal }] = useModalInner(async (data) => {
    isHydrating.value = true;
    loadingInit.value = true; // 👈 thêm
    resetForm();

    try {
      // 👈 bọc try/finally
      await Promise.all([loadOptions(), loadAllVatTu()]);
      fillForm(data?.record);
      await nextTick();
    } finally {
      loadingInit.value = false; // 👈 thêm
      isHydrating.value = false;
      formRef.value?.clearValidate?.();
    }
  });
  function resetForm() {
    Object.assign(formState, {
      id: undefined,
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
      diaChiKhachHang: '',
      tongGia: 0,
      taoLuc: '',
      vatTuHopDongs: [],
    });

    clearVatTuLists();
  }

  function clearVatTuLists() {
    vatTuGroupConfigs.forEach((group) => {
      group.listRef.value = [];
      if (group.skipLoad) {
        group.dataRef.value = [];
      }
    });
  }

  async function loadOptions() {
    try {
      const [coSoRes, nghanhHangRes, nguoiDungRes] = await Promise.allSettled([
        getAllCoSo(),
        getAllNganhHang(),
        getAllNguoiDung(),
      ]);

      if (coSoRes.status === 'fulfilled') {
        coSoOptions.value = resolveList(coSoRes.value).map((item: any) => ({
          label: item.ten || item.ma,
          value: item.id,
          ma: item.ma,
        }));
      }

      if (nghanhHangRes.status === 'fulfilled') {
        nghanhHangOptions.value = resolveList(nghanhHangRes.value).map((item: any) => ({
          label: item.ten,
          value: item.id,
        }));
      }

      if (nguoiDungRes.status === 'fulfilled') {
        nguoiDungOptions.value = resolveList(nguoiDungRes.value).map((item: any) => ({
          label: item.hoVaTen || item.email || item.sdt,
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
      const groups = vatTuGroupConfigs.filter((group) => !group.skipLoad);
      const results = await Promise.allSettled(
        groups.map(async (group) => ({
          group,
          response: await filterVatTu(group.code),
        })),
      );

      results.forEach((result) => {
        if (result.status !== 'fulfilled') {
          console.error('Error loading vat tu group:', result.reason);
          return;
        }

        const { group, response } = result.value;
        group.dataRef.value = response?.data?.content || [];
      });
    } catch (error) {
      console.error('Error loading vat tu:', error);
      message.error('Không thể tải danh sách vật tư');
    }
  }

  function fillForm(record?: HopDongDto) {
    if (!record) return;

    ensureCurrentOptions(record);

    Object.assign(formState, {
      id: record.id,
      coSoId: record.coSo?.id,
      nghanhHangId: record.nghanhHang?.id,
      ten: record.ten || '',
      loaiHeThong: record.loaiHeThong || 'On-Grid',
      loaiPha: record.loaiPha || '1 pha',
      sanLuongToiThieu: record.sanLuongToiThieu || 0,
      sanLuongToiDa: record.sanLuongToiDa || 0,
      giaKhungSat: record.giaKhungSat || 0,
      moTa: record.moTa || '',
      nguoiGioiThieuId: record.nguoiGioiThieu?.id || record.khachHang?.nguoiGioiThieu?.id,
      emailKhachHang: record.khachHang?.email || '',
      sdtKhachHang: record.khachHang?.sdt || '',
      hoVaTenKhachHang: record.khachHang?.hoVaTen || '',
      diaChiKhachHang: record.khachHang?.diaChi || '',
      tongGia: record.tongGia || 0,
      taoLuc: toDatePickerValue(record.taoLuc),
      vatTuHopDongs: [],
    });

    fillVatTuLists(record.vatTuHopDongs || []);
  }

  function fillVatTuLists(items: VatTuHopDongDto[]) {
    clearVatTuLists();

    items.forEach((item) => {
      const group = getGroupConfig(normalizeGroupCode(item.vatTu?.nhomVatTu?.ma));

      if (item.vatTu && !group.dataRef.value.some((vatTu) => vatTu.id === item.vatTu.id)) {
        group.dataRef.value.push(item.vatTu);
      }

      group.listRef.value.push({
        vatTuId: item.vatTu?.id,
        moTa: item.moTa || item.vatTu?.moTaBaoGia || '',
        soLuong: item.soLuong || 0,
        giaBan: item.giaHienThi ?? item.giaHeThong ?? 0,
        gm: item.gm ?? item.vatTu?.nhomVatTu?.gm ?? 0,
        thoiGianBaoHanh: item.thoiGianBaoHanh || 0,
        duocBaoHanh: item.duocBaoHanh ?? true,
        trangThai: item.trangThai ?? 1,
      });
    });
  }

  function ensureCurrentOptions(record: HopDongDto) {
    if (record.coSo?.id && !coSoOptions.value.some((item) => item.value === record.coSo.id)) {
      coSoOptions.value.push({
        label: record.coSo.ten || record.coSo.ma,
        value: record.coSo.id,
        ma: record.coSo.ma,
      });
    }

    if (
      record.nghanhHang?.id &&
      !nghanhHangOptions.value.some((item) => item.value === record.nghanhHang.id)
    ) {
      nghanhHangOptions.value.push({
        label: record.nghanhHang.ten || record.nghanhHang.ma,
        value: record.nghanhHang.id,
      });
    }

    const nguoiGioiThieu = record.nguoiGioiThieu || record.khachHang?.nguoiGioiThieu;
    if (
      nguoiGioiThieu?.id &&
      !nguoiDungOptions.value.some((item) => item.value === nguoiGioiThieu.id)
    ) {
      nguoiDungOptions.value.push({
        label: nguoiGioiThieu.hoVaTen || String(nguoiGioiThieu.id),
        value: nguoiGioiThieu.id,
      });
    }
  }

  function resolveList(res: any) {
    const payload = res?.data ?? res;
    if (Array.isArray(payload)) return payload;
    if (payload && Array.isArray(payload.content)) return payload.content;
    return [];
  }

  function normalizeGroupCode(code?: string) {
    const normalizedCode = (code || '').trim().toUpperCase();
    if (vatTuGroupConfigs.some((group) => group.code === normalizedCode)) {
      return normalizedCode;
    }
    return 'KHAC';
  }

  function getGroupConfig(code: string) {
    return (
      vatTuGroupConfigs.find((group) => group.code === code) ||
      vatTuGroupConfigs[vatTuGroupConfigs.length - 1]
    );
  }

  function toDatePickerValue(value?: string) {
    if (!value) return '';

    const date = new Date(value);
    if (Number.isNaN(date.getTime())) return value;

    return date.toISOString().replace(/\.\d{3}Z$/, 'Z');
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

  function handleChangCoSo() {}

  async function handleSoPhaChange() {
    const response = await filterVatTu('TU_DIEN');
    if (response?.data?.content) {
      tuDienData.value = response.data.content.filter((v: VatTuDto) => {
        const soPha = v.duLieuRieng?.so_pha?.giaTri as string;
        return soPha?.includes(formState.loaiPha);
      });

      tuDienList.value.forEach((item) => {
        const selectedVatTu = item.vatTuId
          ? response.data.content.find((vatTu: VatTuDto) => vatTu.id === item.vatTuId)
          : undefined;
        if (selectedVatTu && !tuDienData.value.some((vatTu) => vatTu.id === selectedVatTu.id)) {
          tuDienData.value.push(selectedVatTu);
        }
      });
    }
  }

  function handleAddVatTu(code: string) {
    const group = getGroupConfig(code);
    if (!group || group.dataRef.value.length === 0) {
      message.error('Không có vật tư để thêm');
      return;
    }

    const firstVatTu = group.dataRef.value[0];
    const latestGia = firstVatTu.thongTinGias[firstVatTu.thongTinGias.length - 1];
    const giaInfo = latestGia?.dsGia.find((g: any) => g.maCoSo === selectedCoSoMa.value);

    const newItem: VatTuHopDongCreatingDto = {
      vatTuId: firstVatTu.id,
      moTa: firstVatTu.moTaBaoGia || '',
      soLuong:
        code === 'HE_KHUNG_NHOM'
          ? calculateHeKhungNhomQuantity(firstVatTu.ma, tamPinList.value[0]?.soLuong || 0)
          : 1,
      giaBan: giaInfo?.giaBan || 0,
      gm: firstVatTu.nhomVatTu.gm,
      thoiGianBaoHanh: firstVatTu.thoiGianBaoHanh || 0,
      duocBaoHanh: true,
      trangThai: 1,
    };

    group.listRef.value.push(newItem);
  }

  function handleRemoveVatTu(code: string, index: number) {
    getGroupConfig(code).listRef.value.splice(index, 1);
  }

  function handleUpdateVatTu(code: string, index: number, value: VatTuHopDongCreatingDto) {
    const group = getGroupConfig(code);

    if (code === 'HE_KHUNG_NHOM' && group.listRef.value[index]?.vatTuId !== value.vatTuId) {
      const vatTuFinding = heKhungNhomData.value.find((vatTu) => vatTu.id === value.vatTuId);
      if (vatTuFinding) {
        value.soLuong = calculateHeKhungNhomQuantity(
          vatTuFinding.ma,
          tamPinList.value[0]?.soLuong || 0,
        );
      }
    }

    group.listRef.value[index] = value;

    if (code === 'TAM_PIN') {
      updateHeKhungNhomQuantities();
    }
  }

  function calculateHeKhungNhomQuantity(ma: string, tamPinQty: number): number {
    if (ma.includes('kep_bien')) return ceil(tamPinQty / 5) * 4 + 4;
    if (ma.includes('kep_giua')) return ceil(tamPinQty / 5) * 8 + 4;
    if (ma.includes('full') && ma.includes('rail')) return ceil((tamPinQty * 1.2 * 2) / 4);
    if (ma.includes('thanh_noi')) return ceil((tamPinQty * 1.2 * 2) / 4) * 2 + 4;
    if (ma.includes('chan_l')) return ceil((tamPinQty * 1.2 * 2) / 4) * 6;
    if (ma.includes('kep_tiep_dia')) return ceil(tamPinQty / 5) + 1;
    if (ma.includes('la_tiep_dia')) return (ceil(tamPinQty / 5) * 8 + 4) / 2;
    if (ma.includes('kep_day_dien')) return tamPinQty * 4;
    if (ma.includes('mini') && ma.includes('rail')) return ceil(tamPinQty / 5) * 12 + 8;
    return 1;
  }

  function updateHeKhungNhomQuantities() {
    if (tamPinList.value.length === 0) return;

    heKhungNhomList.value.forEach((item, index) => {
      const vatTu = heKhungNhomData.value.find((v) => v.id === item.vatTuId);
      if (!vatTu) return;

      heKhungNhomList.value[index].soLuong = calculateHeKhungNhomQuantity(
        vatTu.ma,
        tamPinList.value[0].soLuong,
      );
    });
  }

  function updateAllPricesByCoSo() {
    vatTuGroupConfigs.forEach((group) => {
      group.listRef.value.forEach((item, index) => {
        const vatTu = group.dataRef.value.find((v) => v.id === item.vatTuId);
        if (!vatTu) return;

        const latestGia = vatTu.thongTinGias[vatTu.thongTinGias.length - 1];
        const giaInfo = latestGia?.dsGia.find((g: any) => g.maCoSo === selectedCoSoMa.value);
        if (giaInfo) {
          group.listRef.value[index].giaBan = giaInfo.giaBan || 0;
        }
      });
    });

    message.success('Đã cập nhật giá theo cơ sở mới');
  }

  function getAllVatTuItems() {
    return vatTuGroupConfigs.flatMap((group) => group.listRef.value);
  }

  function tinhTongGia() {
    const total = getAllVatTuItems().reduce((sum, item) => {
      return sum + (item.giaBan || 0) * (item.soLuong || 0);
    }, formState.giaKhungSat || 0);

    formState.tongGia = total;
    message.success('Đã tính tổng giá');
  }

  async function handleSubmit() {
    try {
      await formRef.value?.validate();
      loading.value = true;

      const allVatTu = getAllVatTuItems();
      if (allVatTu.length === 0) {
        message.error('Vui lòng thêm ít nhất một vật tư');
        return;
      }

      if (!formState.id) {
        message.error('Không tìm thấy hợp đồng cần cập nhật');
        return;
      }

      const submitData: HopDongUpdateDto = {
        id: formState.id,
        coSoId: formState.coSoId,
        ten: formState.ten,
        loaiHeThong: formState.loaiHeThong,
        loaiPha: formState.loaiPha,
        sanLuongToiThieu: formState.sanLuongToiThieu || 0,
        sanLuongToiDa: formState.sanLuongToiDa || 0,
        giaKhungSat: formState.giaKhungSat || 0,
        moTa: formState.moTa || '',
        nguoiGioiThieuId: formState.nguoiGioiThieuId,
        emailKhachHang: formState.emailKhachHang || '',
        sdtKhachHang: formState.sdtKhachHang || '',
        hoVaTenKhachHang: formState.hoVaTenKhachHang || '',
        diaChiKhachHang: formState.diaChiKhachHang || '',
        tongGia: formState.tongGia || 0,
        taoLuc: formState.taoLuc,
        vatTuHopDongs: allVatTu.map((item) => ({
          vatTuId: item.vatTuId,
          moTa: item.moTa || '',
          soLuong: item.soLuong || 0,
          giaBan: item.giaBan || 0,
          gm: item.gm || 0,
          thoiGianBaoHanh: item.thoiGianBaoHanh || 0,
          duocBaoHanh: item.duocBaoHanh ?? true,
          trangThai: item.trangThai ?? 1,
        })),
      };

      const result = await updateHopDong(submitData);

      if (result.status === 200) {
        message.success('Cập nhật thành công');
        closeModal();
        emit('success');
      } else {
        message.error(result.message || 'Có lỗi xảy ra');
      }
    } catch (error: any) {
      console.error('Update error:', error);
      if (error.errorFields) {
        message.error('Vui lòng kiểm tra lại thông tin');
      } else {
        message.error(error?.message || 'Có lỗi xảy ra khi cập nhật dữ liệu');
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
