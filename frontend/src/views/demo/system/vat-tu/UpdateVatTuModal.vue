<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    title="Chỉnh sửa vật tư"
    :width="800"
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
  import { Upload } from 'ant-design-vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { BasicForm, useForm , FormSchema} from '@/components/Form';
  import { PlusOutlined, UploadOutlined } from '@ant-design/icons-vue';
  import { updateVatTu } from './vatTu';
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

    if (data?.record) {
      recordId.value = data.record.id;

      // Load ảnh hiện có
      blobUrls.value.forEach((url) => {
        try {
          URL.revokeObjectURL(url);
        } catch (e) {
          console.error('Error revoking blob URL:', e);
        }
      });
      blobUrls.value = [];

      // Load ảnh hiện có
      if (Array.isArray(data.record.anhVatTus) && data.record.anhVatTus.length > 0) {
        // Xử lý từng ảnh
        const promises = data.record.anhVatTus.map(async (a: any, i: number) => {
          const duongDan = a.tepTin?.duongDan || a.tepTin?.url || '';
          const fileName = a.tepTin?.tenTepGoc || a.tepTin?.tenLuuTru || `img-${i}`;

          try {
            // Fetch ảnh và tạo blob URL
            const response = await fetch(duongDan);
            const blob = await response.blob();
            const blobUrl = URL.createObjectURL(blob);
            blobUrls.value.push(blobUrl); // Lưu để revoke sau

            return {
              uid: String(a.id ?? `exist-${i}`),
              name: fileName,
              status: 'done',
              url: duongDan,
              thumbUrl: blobUrl, // Dùng blob URL cho preview
              type: blob.type || a.tepTin?.loaiTepTin || 'image/jpeg',
              isExisting: true,
            };
          } catch (error) {
            console.error('Failed to load image:', duongDan, error);
            // Fallback nếu fetch thất bại
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

        // Đợi tất cả ảnh load xong
        Promise.all(promises).then((loadedFiles) => {
          fileList.value = loadedFiles;
        });
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

  // Upload Sheet PDF
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

  // Upload ảnh
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

      // Revoke blob URL
      const thumbUrl = file.thumbUrl;
      if (thumbUrl && thumbUrl.startsWith && thumbUrl.startsWith('blob:')) {
        try {
          URL.revokeObjectURL(thumbUrl);
          // Xóa khỏi danh sách blob URLs
          blobUrls.value = blobUrls.value.filter((url) => url !== thumbUrl);
        } catch (e) {
          console.error('Error revoking blob URL:', e);
        }
      }
    } catch (err) {
      console.error('handleRemoveFile error', err);
    }
  }

  async function handleSubmit() {
    try {
      const values = await validate();
      setModalProps({ confirmLoading: true });

      const updateData = {
        id: recordId.value!,
        ten: values.ten,
        thoiGianBaoHanh: values.thoiGianBaoHanh,
        gm: values.gm,
        trangThai: values.trangThai,
      };

      // Chỉ lấy các file mới (không phải existing)
      const imageFiles = fileList.value
        .filter((f) => !f.isExisting && f.originFileObj)
        .map((f) => f.originFileObj)
        .filter(Boolean);

      const result = await updateVatTu(updateData, sheetFile.value, imageFiles);

      if (result.status === 200 || result.status === 201) {
        // Revoke tất cả blob URLs
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
    // Cleanup tất cả blob URLs khi component unmount
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

  .files-container {
    .upload-hint {
      margin-top: 8px;
      color: #999;
      font-size: 12px;
    }
  }
</style>
