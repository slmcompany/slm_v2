<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    :title="getTitle"
    :width="800"
    @ok="handleSubmit"
  >
    <BasicForm @register="registerForm">
      <template #anhQuangCao="{ model }">
        <div class="files-container">
          <Upload
            ref="antUpload"
            :file-list="fileList"
            list-type="picture-card"
            :before-upload="beforeUpload"
            @remove="handleRemoveFile"
            @change="onUploadChange"
            :multiple="false"
            accept="image/*"
            :show-upload-list="true"
          >
            <div
              v-if="fileList.length < 1"
              @click.stop.prevent="triggerNativeFileInput"
              style="cursor: pointer"
            >
              <PlusOutlined />
              <div style="margin-top: 8px">Tải ảnh lên</div>
            </div>
          </Upload>

          <input
            ref="nativeFileInput"
            type="file"
            style="display: none"
            accept="image/*"
            @change="onNativeFiles"
          />

          <div class="upload-hint">Chỉ tải lên 1 ảnh, định dạng: JPG, PNG, GIF</div>
        </div>
      </template>
    </BasicForm>
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref, computed, unref } from 'vue';
  import { Upload } from 'ant-design-vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { BasicForm, useForm } from '@/components/Form';
  import { PlusOutlined } from '@ant-design/icons-vue';
  import { formSchema } from './quangCao.data';
  import { createQuangCao, updateQuangCao } from './quangCao';
  import { message } from 'ant-design-vue';
  import type { UploadProps } from 'ant-design-vue';

  defineOptions({ name: 'QuangCaoModal' });

  const props = defineProps<{
    nganhHangOptions: Array<{ label: string; value: number }>;
  }>();

  const emit = defineEmits(['success', 'register']);

  const isUpdate = ref(false);
  const recordId = ref<number>();
  const fileList = ref<any[]>([]);
  const originalImageUrl = ref<string | null>(null); // Lưu URL ảnh gốc
  const hasImageChanged = ref(false); // Track xem user có đổi ảnh không

  const nativeFileInput = ref<HTMLInputElement | null>(null);
  const antUpload = ref<any>(null);

  const [registerForm, { resetFields, setFieldsValue, validate, updateSchema }] = useForm({
    labelWidth: 140,
    schemas: formSchema,
    showActionButtonGroup: false,
    baseColProps: { span: 24 },
  });

  const [registerModal, { setModalProps, closeModal }] = useModalInner(async (data) => {
    resetFields();
    setModalProps({ confirmLoading: false });
    isUpdate.value = !!data?.isUpdate;
    fileList.value = [];
    originalImageUrl.value = null;
    hasImageChanged.value = false;

    updateSchema([
      {
        field: 'nganhHangId',
        componentProps: {
          options: props.nganhHangOptions,
        },
      },
    ]);

    if (unref(isUpdate)) {
      recordId.value = data.record.id;
      
      // Set form values
      setFieldsValue({
        tieuDe: data.record.tieuDe,
        nganhHangId: data.record.nganhHang?.id,
        viTri: data.record.viTri,
        hoatDong: data.record.hoatDong,
        trangThai: data.record.trangThai,
      });

      // Load existing image
      if (data.record.tepTin?.duongDan) {
        originalImageUrl.value = data.record.tepTin.duongDan;
        fileList.value = [
          {
            uid: String(data.record.tepTin.id || '-1'),
            name: data.record.tepTin.tenTepGoc || 'image',
            status: 'done',
            url: data.record.tepTin.duongDan,
            thumbUrl: data.record.tepTin.duongDan,
            isExisting: true,
          },
        ];
      }
    }
  });

  const getTitle = computed(() => (!unref(isUpdate) ? 'Tạo quảng cáo' : 'Chỉnh sửa quảng cáo'));

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

    const f = files[0];
    
    // Validate
    const isImage = f.type && f.type.startsWith('image/');
    if (!isImage) {
      message.error('Chỉ chấp nhận file ảnh (JPG, PNG, GIF)!');
      input.value = '';
      return;
    }
    
    const isLt5M = f.size / 1024 / 1024 < 5;
    if (!isLt5M) {
      message.error('Kích thước ảnh phải nhỏ hơn 5MB!');
      input.value = '';
      return;
    }

    // Add to fileList
    try {
      const url = URL.createObjectURL(f);
      const newFile = {
        uid: `${Date.now()}_${Math.random().toString(36).slice(2, 8)}`,
        name: f.name,
        status: 'done',
        originFileObj: f,
        url,
        thumbUrl: url,
      };
      fileList.value = [newFile];
      hasImageChanged.value = true; // Đánh dấu đã đổi ảnh
      console.log('Native file added:', newFile);
      message.success('Đã tải ảnh lên thành công!');
    } catch (err) {
      console.error('onNativeFiles error', err);
      message.error('Có lỗi khi tải ảnh');
    }

    input.value = '';
  }

  const beforeUpload: UploadProps['beforeUpload'] = (file) => {
    // Validate image type
    const isImage = file.type && file.type.startsWith('image/');
    if (!isImage) {
      message.error('Chỉ chấp nhận file ảnh (JPG, PNG, GIF)!');
      return Upload.LIST_IGNORE;
    }

    // Validate file size
    const isLt5M = file.size / 1024 / 1024 < 5;
    if (!isLt5M) {
      message.error('Kích thước ảnh phải nhỏ hơn 5MB!');
      return Upload.LIST_IGNORE;
    }

    // Create preview URL
    try {
      const url = URL.createObjectURL(file as File);
      const newFile = {
        uid: file.uid || `${Date.now()}_${Math.random().toString(36).slice(2)}`,
        name: file.name,
        status: 'done',
        originFileObj: file,
        url,
        thumbUrl: url,
      };
      fileList.value = [newFile];
      hasImageChanged.value = true; // Đánh dấu đã đổi ảnh
      console.log('File added to list:', newFile);
    } catch (e) {
      console.error('Error creating preview:', e);
      message.error('Không thể tạo preview cho ảnh');
      return Upload.LIST_IGNORE;
    }

    // Prevent auto upload
    return false;
  };

  function onUploadChange(e: any) {
    try {
      const fl = (e && e.fileList) || [];
      fileList.value = fl.slice(0, 1).map((f: any) => {
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
      console.log('=== START SUBMIT ===');
      console.log('isUpdate:', unref(isUpdate));
      console.log('fileList:', fileList.value);
      console.log('hasImageChanged:', hasImageChanged.value);
      
      // Validate form
      console.log('Validating form...');
      const values = await validate();
      console.log('Form values:', values);
      
      setModalProps({ confirmLoading: true });

      if (unref(isUpdate)) {
        // UPDATE MODE
        let file: File | null = null;

        // Nếu user đổi ảnh, lấy file mới
        if (hasImageChanged.value && fileList.value.length > 0) {
          file = fileList.value[0].originFileObj;
          if (!file) {
            message.error('Không tìm thấy file ảnh mới!');
            setModalProps({ confirmLoading: false });
            return;
          }
          console.log('New file to upload:', file.name, file.type, file.size);
        } else {
          console.log('No image change, keeping existing image');
        }

        const updateData = {
          id: recordId.value!,
          nganhHangId: values.nganhHangId,
          tieuDe: values.tieuDe,
          viTri: values.viTri,
          hoatDong: values.hoatDong ?? true,
          trangThai: values.trangThai ?? 1,
        };

        console.log('Update data:', updateData);
        const result = await updateQuangCao(updateData, file);
        console.log('API result:', result);

        if (result.status === 200 || result.status === 201) {
          message.success('Cập nhật thành công');
          closeModal();
          emit('success');
        } else {
          console.log('API returned non-200 status:', result);
          message.error(result.message || 'Có lỗi xảy ra');
        }
      } else {
        // CREATE MODE
        if (fileList.value.length === 0) {
          console.log('ERROR: No files');
          message.error('Vui lòng tải lên ảnh quảng cáo!');
          setModalProps({ confirmLoading: false });
          return;
        }

        const file = fileList.value[0].originFileObj;
        console.log('File object:', file);
        
        if (!file) {
          console.log('ERROR: No originFileObj');
          message.error('Không tìm thấy file ảnh hợp lệ!');
          setModalProps({ confirmLoading: false });
          return;
        }

        const submitData = {
          nganhHangId: values.nganhHangId,
          tieuDe: values.tieuDe,
          viTri: values.viTri,
        };

        console.log('Submit data:', submitData);
        console.log('File to upload:', file.name, file.type, file.size);

        const result = await createQuangCao(submitData, file);
        console.log('API result:', result);

        if (result.status === 200 || result.status === 201) {
          message.success('Tạo mới thành công');
          closeModal();
          emit('success');
        } else {
          console.log('API returned non-200 status:', result);
          message.error(result.message || 'Có lỗi xảy ra');
        }
      }
    } catch (error: any) {
      console.error('=== SUBMIT ERROR ===');
      console.error('Error object:', error);
      console.error('Error message:', error?.message);
      console.error('Error stack:', error?.stack);
      
      if (error?.errorFields) {
        console.log('Form validation errors:', error.errorFields);
        message.error('Vui lòng điền đầy đủ thông tin bắt buộc');
      } else {
        message.error(error?.message || 'Có lỗi xảy ra khi lưu dữ liệu');
      }
    } finally {
      setModalProps({ confirmLoading: false });
    }
  }

  function handleRemoveFile(file: any) {
    try {
      fileList.value = [];
      hasImageChanged.value = true; // Đánh dấu đã xóa ảnh
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
  .files-container {
    .upload-hint {
      margin-top: 8px;
      color: #999;
      font-size: 12px;
    }
  }
</style>