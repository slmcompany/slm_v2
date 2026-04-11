<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    title="Chỉnh sửa vật tư"
    :width="900"
    @ok="handleSubmit"
  >
    <BasicForm @register="registerForm">
      <template #sheetFile="{ model }">
        <div class="sheet-container">
          <Upload
            :file-list="sheetFileList"
            :before-upload="beforeUploadSheet"
            @remove="handleRemoveSheet"
            accept=".pdf"
            :max-count="1"
          >
            <a-button>
              <template #icon>
                <UploadOutlined />
              </template>
              Tải PDF lên
            </a-button>
          </Upload>
          <div class="upload-hint">Chỉ chấp nhận file PDF, tối đa 10MB</div>
        </div>
      </template>

      <template #dsGia="{ model, field }">
        <div class="ds-gia-container" v-if="field === 'dsGia'">

          <!-- Lịch sử giá cũ -->
          <div v-if="thongTinGiasHistory.length > 0" class="gia-history-section">
            <div class="section-label">
              <HistoryOutlined style="margin-right: 6px" />
              Lịch sử thông tin giá
            </div>

            <div
              v-for="(record, rIdx) in thongTinGiasHistory"
              :key="`history-${rIdx}`"
              class="gia-history-record"
            >
              <div class="gia-history-record-header">
                <span class="record-index">Lần cập nhật {{ thongTinGiasHistory.length - rIdx }}</span>
                <span class="record-date">
                  <CalendarOutlined style="margin-right: 4px" />
                  {{ formatDate(record.taoLuc) }}
                </span>
              </div>

              <div
                v-for="(gia, gIdx) in record.dsGia"
                :key="`history-gia-${rIdx}-${gIdx}`"
                class="gia-history-item"
              >
                <Row :gutter="12">
                  <Col :span="6">
                    <div class="gia-field">
                      <span class="gia-field-label">Mã cơ sở</span>
                      <span class="gia-field-value tag">{{ gia.maCoSo }}</span>
                    </div>
                  </Col>
                  <Col :span="6">
                    <div class="gia-field">
                      <span class="gia-field-label">Tên cơ sở</span>
                      <span class="gia-field-value">{{ gia.tenCoSo }}</span>
                    </div>
                  </Col>
                  <Col :span="6">
                    <div class="gia-field">
                      <span class="gia-field-label">Giá nhập</span>
                      <span class="gia-field-value price">{{ formatPrice(gia.giaNhap) }}</span>
                    </div>
                  </Col>
                  <Col :span="6">
                    <div class="gia-field">
                      <span class="gia-field-label">Giá bán</span>
                      <span class="gia-field-value price">{{ formatPrice(gia.giaBan) }}</span>
                    </div>
                  </Col>
                </Row>
              </div>
            </div>
          </div>

          <Divider v-if="thongTinGiasHistory.length > 0" style="margin: 16px 0">
            <span style=" color: #666;font-size: 13px">Thêm thông tin giá mới</span>
          </Divider>

          <!-- Form thêm giá mới -->
          <Button type="dashed" block @click="handleAddGia" style="margin-bottom: 16px">
            <template #icon>
              <PlusOutlined />
            </template>
            Thêm thông tin giá
          </Button>

          <Empty
            v-if="dsGiaList.length === 0"
            description="Chưa có thông tin giá mới"
            :image-style="{ height: '60px' }"
          />

          <div v-for="(item, index) in dsGiaList" :key="`gia-item-${index}`" class="gia-item">
            <div
              style="
                display: flex;
                align-items: center;
                justify-content: space-between;
                margin-bottom: 8px;
              "
            >
              <span style="font-size: 14px; font-weight: 500">Thông tin giá {{ index + 1 }}</span>
              <Button type="primary" danger size="small" @click="handleRemoveGia(index)">
                <template #icon>
                  <DeleteOutlined />
                </template>
                Xóa
              </Button>
            </div>

            <Card size="small" :bordered="true">
              <Row :gutter="16">
                <Col :span="8">
                  <FormItem label="Mã cơ sở" :required="true">
                    <Select
                      :value="item.maCoSo"
                      @change="
                        (val) => {
                          item.maCoSo = val;
                          handleCoSoChange(item, val);
                        }
                      "
                      placeholder="Chọn cơ sở"
                      style="width: 100%"
                    >
                      <SelectOption value="HN">HN - Hà Nội</SelectOption>
                      <SelectOption value="HCM">HCM - Hồ Chí Minh</SelectOption>
                    </Select>
                  </FormItem>
                </Col>
                <Col :span="8">
                  <FormItem label="Tên cơ sở">
                    <Input v-model:value="item.tenCoSo" placeholder="Tên cơ sở" disabled />
                  </FormItem>
                </Col>
                <Col :span="8">
                  <FormItem label="Giá nhập" :required="true">
                    <InputNumber
                      v-model:value="item.giaNhap"
                      placeholder="Nhập giá nhập"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
                <Col :span="8">
                  <FormItem label="Giá bán" :required="true">
                    <InputNumber
                      v-model:value="item.giaBan"
                      placeholder="Nhập giá bán"
                      :min="0"
                      :formatter="(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
                      :parser="(value) => value.replace(/\$\s?|(,*)/g, '')"
                      style="width: 100%"
                    />
                  </FormItem>
                </Col>
              </Row>
            </Card>
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
            <div v-if="fileList.length < 8" style="cursor: pointer">
              <PlusOutlined />
              <div style="margin-top: 8px">Tải ảnh lên</div>
            </div>
          </Upload>

          <div class="upload-hint">Tối đa 8 ảnh, định dạng: JPG, PNG, GIF</div>
        </div>
      </template>
    </BasicForm>
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref, onUnmounted } from 'vue';
  import {
    Upload,
    InputNumber,
    Empty,
    Select,
    SelectOption,
    Card,
    Row,
    Col,
    Input,
    Button,
    FormItem,
    Divider,
  } from 'ant-design-vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { BasicForm, useForm, FormSchema } from '@/components/Form';
  import { PlusOutlined, UploadOutlined, DeleteOutlined, HistoryOutlined, CalendarOutlined } from '@ant-design/icons-vue';
  import { updateVatTu, GiaInfo } from './vatTu';
  import { message } from 'ant-design-vue';
  import type { UploadProps } from 'ant-design-vue';

  defineOptions({ name: 'UpdateVatTuModal' });

  const emit = defineEmits(['success', 'register']);

  const recordId = ref<number>();
  const fileList = ref<any[]>([]);
  const sheetFileList = ref<any[]>([]);
  const sheetFile = ref<File | null>(null);
  const antUpload = ref<any>(null);
  const blobUrls = ref<string[]>([]);
  const dsGiaList = ref<GiaInfo[]>([]);
  const thongTinGiasHistory = ref<any[]>([]);

  const updateFormSchema: FormSchema[] = [
    {
      field: 'ten',
      label: 'Tên vật tư',
      component: 'Input',
      required: true,
      componentProps: {
        placeholder: 'Nhập tên vật tư',
        maxlength: 400,
      },
    },
    {
      field: 'thoiGianBaoHanh',
      label: 'Bảo hành (tháng)',
      component: 'InputNumber',
      required: true,
      componentProps: {
        placeholder: 'Nhập thời gian bảo hành',
        max: 360,
        min: 0,
        step: 1,
      },
    },
    {
      field: 'gm',
      label: 'GM (%)',
      component: 'InputNumber',
      required: true,
      componentProps: {
        placeholder: 'Nhập GM',
        max: 100,
        min: 0,
        step: 0.01,
      },
    },
    {
      field: 'trangThai',
      label: 'Trạng thái',
      component: 'RadioButtonGroup',
      defaultValue: 1,
      componentProps: {
        options: [
          { label: 'Kích hoạt', value: 1 },
          { label: 'Vô hiệu', value: 0 },
        ],
      },
      required: true,
    },
    {
      field: 'dsGia',
      label: 'Thông tin giá',
      slot: 'dsGia',
      colProps: { span: 24 },
    },
    {
      field: 'sheetFile',
      label: 'Tải PDF Sheet',
      slot: 'sheetFile',
      colProps: { span: 24 },
    },
    {
      field: 'files',
      label: 'Hình ảnh',
      slot: 'files',
      colProps: { span: 24 },
    },
  ];

  const [registerForm, { resetFields, setFieldsValue, validate }] = useForm({
    labelWidth: 140,
    schemas: updateFormSchema,
    showActionButtonGroup: false,
    baseColProps: { span: 24 },
  });

  const [registerModal, { setModalProps, closeModal }] = useModalInner(async (data) => {
    resetFields();
    setModalProps({ confirmLoading: false });
    fileList.value = [];
    sheetFileList.value = [];
    sheetFile.value = null;
    dsGiaList.value = [];
    thongTinGiasHistory.value = [];

    if (data?.record) {
      recordId.value = data.record.id;

      // Revoke blob URLs cũ
      blobUrls.value.forEach((url) => {
        try {
          URL.revokeObjectURL(url);
        } catch (e) {
          console.error('Error revoking blob URL:', e);
        }
      });
      blobUrls.value = [];

      // Load toàn bộ lịch sử thông tin giá (mới nhất lên đầu)
      if (Array.isArray(data.record.thongTinGias) && data.record.thongTinGias.length > 0) {
        thongTinGiasHistory.value = [...data.record.thongTinGias].reverse();
      }

      // Load ảnh hiện có
      if (Array.isArray(data.record.anhVatTus) && data.record.anhVatTus.length > 0) {
        const promises = data.record.anhVatTus.map(async (a: any, i: number) => {
          const duongDan = a.tepTin?.duongDan || a.tepTin?.url || '';
          const fileName = a.tepTin?.tenTepGoc || a.tepTin?.tenLuuTru || `img-${i}`;

          try {
            const response = await fetch(duongDan);
            const blob = await response.blob();
            const blobUrl = URL.createObjectURL(blob);
            blobUrls.value.push(blobUrl);

            return {
              uid: String(a.id ?? `exist-${i}`),
              name: fileName,
              status: 'done',
              url: duongDan,
              thumbUrl: blobUrl,
              type: blob.type || a.tepTin?.loaiTepTin || 'image/jpeg',
              isExisting: true,
            };
          } catch (error) {
            console.error('Failed to load image:', duongDan, error);
            return {
              uid: String(a.id ?? `exist-${i}`),
              name: fileName,
              status: 'done',
              url: duongDan,
              thumbUrl: duongDan,
              type: a.tepTin?.loaiTepTin || 'image/jpeg',
              isExisting: true,
            };
          }
        });

        try {
          const loadedFiles = await Promise.all(promises);
          fileList.value = loadedFiles;
        } catch (error) {
          console.error('Error loading images:', error);
          const loadedFiles = await Promise.allSettled(promises).then((results) =>
            results
              .filter((r) => r.status === 'fulfilled')
              .map((r: any) => r.value)
          );
          fileList.value = loadedFiles;
        }
      }

      // Set giá trị form
      setFieldsValue({
        ten: data.record.ten,
        thoiGianBaoHanh: data.record.thoiGianBaoHanh,
        gm: data.record.gm,
        trangThai: data.record.trangThai,
      });
    }
  });

  // ---- Quản lý dsGia ----
  function handleAddGia() {
    dsGiaList.value.push({
      maCoSo: '',
      tenCoSo: '',
      giaNhap: 0,
      giaBan: 0,
      giaNhapRaw: null,
      giaBanRaw: null,
    });
  }

  function handleRemoveGia(index: number) {
    dsGiaList.value.splice(index, 1);
  }

  function handleCoSoChange(item: any, value: string) {
    const coSoMap: Record<string, string> = {
      HN: 'Hà Nội',
      HCM: 'Hồ Chí Minh',
    };
    item.tenCoSo = coSoMap[value] || '';
  }

  function formatDate(isoString: string): string {
    if (!isoString) return '—';
    try {
      const d = new Date(isoString);
      const pad = (n: number) => String(n).padStart(2, '0');
      return `${pad(d.getDate())}/${pad(d.getMonth() + 1)}/${d.getFullYear()} ${pad(d.getHours())}:${pad(d.getMinutes())}`;
    } catch {
      return isoString;
    }
  }

  function formatPrice(value: number | null | undefined): string {
    if (value === null || value === undefined) return '—';
    return Number(value).toLocaleString('vi-VN') + ' ₫';
  }

  // ---- Upload Sheet PDF ----
  const beforeUploadSheet: UploadProps['beforeUpload'] = (file) => {
    const isPDF = file.type === 'application/pdf';
    if (!isPDF) {
      message.error('Chỉ chấp nhận file PDF!');
      return false;
    }
    const isLt10M = file.size / 1024 / 1024 < 10;
    if (!isLt10M) {
      message.error('Kích thước file phải nhỏ hơn 10MB!');
      return false;
    }

    sheetFile.value = file as File;
    sheetFileList.value = [
      {
        uid: file.uid,
        name: file.name,
        status: 'done',
        originFileObj: file,
      },
    ];
    return false;
  };

  function handleRemoveSheet() {
    sheetFile.value = null;
    sheetFileList.value = [];
  }

  // ---- Upload ảnh ----
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
            isExisting: f.isExisting,
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

  function handleRemoveFile(file: any) {
    try {
      fileList.value = fileList.value.filter(
        (f: any) => !(f.uid === file.uid || f.name === file.name),
      );

      const thumbUrl = file.thumbUrl;
      if (thumbUrl && thumbUrl.startsWith && thumbUrl.startsWith('blob:')) {
        try {
          URL.revokeObjectURL(thumbUrl);
          blobUrls.value = blobUrls.value.filter((url) => url !== thumbUrl);
        } catch (e) {
          console.error('Error revoking blob URL:', e);
        }
      }
    } catch (err) {
      console.error('handleRemoveFile error', err);
    }
  }

  // ---- Submit ----
  async function handleSubmit() {
    try {
      const values = await validate();
      setModalProps({ confirmLoading: true });

      // Validate dsGia
      for (let i = 0; i < dsGiaList.value.length; i++) {
        const gia = dsGiaList.value[i] as any;
        if (!gia.maCoSo || !gia.tenCoSo) {
          message.error(`Vui lòng nhập Mã/Tên cơ sở cho thông tin giá ${i + 1}`);
          setModalProps({ confirmLoading: false });
          return;
        }
        if (
          gia.giaNhap === null ||
          gia.giaNhap === undefined ||
          gia.giaBan === null ||
          gia.giaBan === undefined
        ) {
          message.error(`Vui lòng nhập đầy đủ Giá nhập và Giá bán cho thông tin giá ${i + 1}`);
          setModalProps({ confirmLoading: false });
          return;
        }
      }

      const dsGiaPayload =
        dsGiaList.value.length > 0
          ? dsGiaList.value.map((g: any) => ({
              maCoSo: g.maCoSo,
              tenCoSo: g.tenCoSo,
              giaNhap: Number(g.giaNhap ?? 0),
              giaBan: Number(g.giaBan ?? 0),
            }))
          : undefined;

      const updateData = {
        id: recordId.value!,
        ten: values.ten,
        thoiGianBaoHanh: values.thoiGianBaoHanh,
        gm: values.gm,
        trangThai: values.trangThai,
        dsGia: dsGiaPayload,
      };

      // Lấy tất cả file (cả file cũ + file mới)
      const imageFiles: File[] = [];

      // Xử lý file mới
      const newFiles = fileList.value
        .filter((f) => !f.isExisting && f.originFileObj)
        .map((f) => f.originFileObj)
        .filter(Boolean);
      imageFiles.push(...newFiles);

      // Xử lý file cũ - fetch URL và convert thành File
      const existingFiles = fileList.value.filter((f) => f.isExisting && f.url);
      for (const existingFile of existingFiles) {
        try {
          const response = await fetch(existingFile.url);
          const blob = await response.blob();
          const file = new File([blob], existingFile.name, { type: blob.type });
          imageFiles.push(file);
        } catch (error) {
          console.error('Failed to fetch existing image:', existingFile.url, error);
        }
      }

      const result = await updateVatTu(updateData, sheetFile.value, imageFiles);

      if (result.status === 200 || result.status === 201) {
        blobUrls.value.forEach((url) => {
          try {
            URL.revokeObjectURL(url);
          } catch (e) {
            console.error('Error revoking blob URL:', e);
          }
        });
        blobUrls.value = [];

        message.success('Cập nhật thành công');
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

  onUnmounted(() => {
    blobUrls.value.forEach((url) => {
      try {
        URL.revokeObjectURL(url);
      } catch (e) {
        console.error('Error revoking blob URL on unmount:', e);
      }
    });
    blobUrls.value = [];
  });
</script>

<style lang="less" scoped>
  .sheet-container {
    .upload-hint {
      margin-top: 8px;
      color: #999;
      font-size: 12px;
    }
  }

  .ds-gia-container {
    .gia-history-section {
      margin-bottom: 4px;

      .section-label {
        display: flex;
        align-items: center;
        margin-bottom: 12px;
        color: #595959;
        font-size: 13px;
        font-weight: 600;
      }

      .gia-history-record {
        margin-bottom: 12px;
        overflow: hidden;
        border: 1px solid #e8e8e8;
        border-radius: 8px;
        background: #fafafa;

        .gia-history-record-header {
          display: flex;
          align-items: center;
          justify-content: space-between;
          padding: 8px 14px;
          border-bottom: 1px solid #e8e8e8;
          background: #f0f0f0;

          .record-index {
            color: #262626;
            font-size: 13px;
            font-weight: 600;
          }

          .record-date {
            display: flex;
            align-items: center;
            color: #8c8c8c;
            font-size: 12px;
          }
        }

        .gia-history-item {
          padding: 10px 14px;
          border-bottom: 1px dashed #ebebeb;

          &:last-child {
            border-bottom: none;
          }

          .gia-field {
            display: flex;
            flex-direction: column;
            gap: 2px;

            .gia-field-label {
              color: #8c8c8c;
              font-size: 11px;
              letter-spacing: 0.3px;
              text-transform: uppercase;
            }

            .gia-field-value {
              color: #262626;
              font-size: 13px;
              font-weight: 500;

              &.tag {
                display: inline-block;
                width: fit-content;
                padding: 1px 8px;
                border-radius: 4px;
                background: #e6f4ff;
                color: #1677ff;
                font-size: 12px;
              }

              &.price {
                color: #d46b08;
                font-variant-numeric: tabular-nums;
              }
            }
          }
        }
      }
    }

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