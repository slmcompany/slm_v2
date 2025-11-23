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
            <div
              v-if="fileList.length < 8"
              style="cursor: pointer"
            >
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
  import { ref } from 'vue';
  import { Upload } from 'ant-design-vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { BasicForm, useForm } from '@/components/Form';
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

  const updateFormSchema = [
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
      if (Array.isArray(data.record.anhVatTus) && data.record.anhVatTus.length > 0) {
        fileList.value = data.record.anhVatTus.map((a: any, i: number) => {
          const duongDan = a.tepTin?.duongDan || a.tepTin?.url || '';
          return {
            uid: String(a.id ?? `exist-${i}`),
            name: a.tepTin?.tenTepGoc || a.tepTin?.tenLuuTru || `img-${i}`,
            status: 'done',
            url: duongDan,
            thumbUrl: duongDan,
            type: a.tepTin?.loaiTepTin || 'image/jpeg',
            isExisting: true,
          };
        });
      }

      // Set giá trị form
      setFieldsValue({
        ten: data.record.ten,
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

  async function handleSubmit() {
    try {
      const values = await validate();
      setModalProps({ confirmLoading: true });

      const updateData = {
        id: recordId.value!,
        ten: values.ten,
        trangThai: values.trangThai,
      };

      // Chỉ lấy các file mới (không phải existing)
      const imageFiles = fileList.value
        .filter(f => !f.isExisting && f.originFileObj)
        .map((f) => f.originFileObj)
        .filter(Boolean);

      const result = await updateVatTu(updateData, sheetFile.value, imageFiles);

      if (result.status === 200 || result.status === 201) {
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