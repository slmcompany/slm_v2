<template>
  <div class="bai-viet-form">
    <!-- Loại bài viết -->
    <FormItem
      label="Loại bài viết"
      name="loaiBaiViet"
    >
      <Select name="" v-model:value="loaiBaiViet">
        <SelectOption value="MEGA_STORY">MEGA STORY</SelectOption>
        <SelectOption value="HOI_DAP">HỎI ĐÁP</SelectOption>
        <SelectOption value="HUONG_DAN_BAO_HANH">HƯỚNG DẪN BẢO HÀNH</SelectOption>
      </Select>
    </FormItem>

    <!-- Tiêu đề -->
    <FormItem
      label="Tiêu đề"
      name="tieuDe"
    >
      <Textarea
        v-model:value="tieuDe"
        placeholder="Nhập tiêu đề bài viết"
        :maxlength="400"
        :auto-size="{ minRows: 2, maxRows: 4 }"
      />
    </FormItem>

    <!-- Đường dẫn youtube -->
    <FormItem
      label="Đường dẫn Youtube"
      name="duongDanYoutube"
    >
      <Textarea
        v-model:value="duongDanYoutube"
        placeholder="Nhập đường dẫn Youtube"
        :maxlength="1000"
        :auto-size="{ minRows: 2, maxRows: 4 }"
      />
    </FormItem>

    <!-- Ảnh bìa -->
    <FormItem label="Ảnh bìa" name="anhBia">
      <div class="upload-container">
        <Upload
          ref="antUploadBia"
          :file-list="fileListBia"
          list-type="picture-card"
          :before-upload="beforeUploadBia"
          @remove="handleRemoveFileBia"
          @change="onUploadChangeBia"
          accept="image/*"
          :show-upload-list="true"
          :max-count="1"
        >
          <div v-if="fileListBia.length < 1" style="cursor: pointer">
            <PlusOutlined />
            <div style="margin-top: 8px">Tải ảnh bìa</div>
          </div>
        </Upload>
        <div class="upload-hint">Tối đa 1 ảnh, định dạng: JPG, PNG, GIF (Tối đa 5MB)</div>
      </div>
    </FormItem>

    <!-- Ảnh ngoài -->
    <FormItem label="Ảnh ngoài" name="anhNgoai">
      <div class="upload-container">
        <Upload
          ref="antUploadNgoai"
          :file-list="fileListNgoai"
          list-type="picture-card"
          :before-upload="beforeUploadNgoai"
          @remove="handleRemoveFileNgoai"
          @change="onUploadChangeNgoai"
          accept="image/*"
          :show-upload-list="true"
          :max-count="1"
        >
          <div v-if="fileListNgoai.length < 1" style="cursor: pointer">
            <PlusOutlined />
            <div style="margin-top: 8px">Tải ảnh ngoài</div>
          </div>
        </Upload>
        <div class="upload-hint">Tối đa 1 ảnh, định dạng: JPG, PNG, GIF (Tối đa 5MB)</div>
      </div>
    </FormItem>

    <!-- Nội dung -->
    <FormItem label="Nội dung" name="noiDung">
      <div style="border: 1px solid #d9d9d9; border-radius: 4px">
        <Toolbar
          style="border-bottom: 1px solid #d9d9d9"
          :editor="editorRef"
          :defaultConfig="toolbarConfig"
          mode="default"
        />
        <Editor
          style="height: 700px; overflow-y: hidden"
          v-model="valueHtml"
          :defaultConfig="editorConfig"
          mode="default"
          @onCreated="handleCreated"
          @customPaste="handleCustomPaste"
        />
      </div>
    </FormItem>

    <!-- Nút submit -->
    <div style="display: flex; gap: 12px; justify-content: flex-end; margin-top: 24px">
      <Button @click="handleReset">Làm mới</Button>
      <Button type="primary" :loading="submitting" @click="handleSubmit">
        Đăng bài viết
      </Button>
    </div>
  </div>
</template>

<script setup lang="ts">
  import '@wangeditor/editor/dist/css/style.css';

  import { ref, shallowRef, onBeforeUnmount } from 'vue';
  import { Editor, Toolbar } from '@wangeditor/editor-for-vue';
  import { FormItem, Input, Textarea, Upload, Button, message, Select, SelectOption } from 'ant-design-vue';
  import type { UploadProps } from 'ant-design-vue';
  import { PlusOutlined } from '@ant-design/icons-vue';
  import { realHttp } from '@/utils/http/axios';

  // ============ STATES ============
  const editorRef = shallowRef();
  const loaiBaiViet = ref('MEGA_STORY');
  const tieuDe = ref('');
  const duongDanYoutube = ref('')
  
  // Ảnh bìa
  const selectedFileBia = ref<File | null>(null);
  const fileListBia = ref<any[]>([]);
  const antUploadBia = ref<any>(null);
  
  // Ảnh ngoài
  const selectedFileNgoai = ref<File | null>(null);
  const fileListNgoai = ref<any[]>([]);
  const antUploadNgoai = ref<any>(null);
  
  const valueHtml = ref('');
  const submitting = ref(false);

  // ============ EDITOR CONFIG ============
  const toolbarConfig = {
    toolbarKeys: [
      'headerSelect',
      'bold',
      'italic',
      'underline',
      '|',
      'color',
      'bgColor',
      '|',
      'fontSize',
      'fontFamily',
      '|',
      'bulletedList',
      'numberedList',
      '|',
      'justifyLeft',
      'justifyCenter',
      'justifyRight',
      '|',
      'insertLink',
      'uploadImage',
      'insertTable',
      '|',
      'undo',
      'redo',
    ],
  };

  const editorConfig = {
    placeholder: 'Nhập nội dung bài viết của bạn...',
    MENU_CONF: {
      uploadImage: {
        fieldName: 'file',
        
        customBrowseAndUpload(insertFn: any) {
          const input = document.createElement('input');
          input.type = 'file';
          input.accept = 'image/*';
          input.multiple = true;
          
          input.onchange = (e: any) => {
            const files = e.target.files;
            if (!files || files.length === 0) return;
            
            Array.from(files).forEach((file: any) => {
              processImageFile(file, insertFn);
            });
          };
          
          input.click();
        },
        
        customPaste: (editor: any, event: ClipboardEvent) => {
          const items = event.clipboardData?.items;
          if (!items) return false;
          
          let hasImage = false;
          
          for (let i = 0; i < items.length; i++) {
            const item = items[i];
            
            if (item.type.indexOf('image') !== -1) {
              hasImage = true;
              event.preventDefault();
              
              const file = item.getAsFile();
              if (file) {
                processImageFile(file, (url: string, alt: string, href: string) => {
                  editor.dangerouslyInsertHtml(`<img src="${url}" alt="${alt}" style="max-width: 100%;" />`);
                });
              }
            }
          }
          
          return hasImage;
        },
        
        customDrop: (editor: any, event: DragEvent) => {
          const files = event.dataTransfer?.files;
          if (!files || files.length === 0) return false;
          
          let hasImage = false;
          
          Array.from(files).forEach((file: any) => {
            if (file.type.startsWith('image/')) {
              hasImage = true;
              event.preventDefault();
              
              processImageFile(file, (url: string, alt: string, href: string) => {
                editor.dangerouslyInsertHtml(`<img src="${url}" alt="${alt}" style="max-width: 100%;" />`);
              });
            }
          });
          
          return hasImage;
        },
        
        onInsertedImage(imageNode: any) {
          console.log('Đã chèn ảnh:', imageNode);
        },
        
        onBeforeUpload(file: File) {
          return true;
        },
      },
    },
  };

  function processImageFile(file: File, insertFn: any) {
    const isImage = file.type.startsWith('image/');
    if (!isImage) {
      message.error(`${file.name}: Chỉ chấp nhận file ảnh!`);
      return;
    }
    
    const isLt5M = file.size / 1024 / 1024 < 5;
    if (!isLt5M) {
      message.error(`${file.name}: Kích thước ảnh phải nhỏ hơn 5MB!`);
      return;
    }
    
    const reader = new FileReader();
    reader.onload = (event: any) => {
      const base64String = event.target.result;
      const alt = file.name || 'image';
      const href = base64String;
      
      insertFn(base64String, alt, href);
      message.success(`Đã chèn ảnh: ${file.name || 'image'}`);
    };
    
    reader.onerror = () => {
      message.error(`Lỗi khi đọc file: ${file.name}`);
    };
    
    reader.readAsDataURL(file);
  }

  // ============ LIFECYCLE ============
  onBeforeUnmount(() => {
    const editor = editorRef.value;
    if (editor == null) return;
    editor.destroy();
  });

  // ============ EDITOR HANDLERS ============
  const handleCreated = (editor: any) => {
    editorRef.value = editor;
    
    const i18nConfig = {
      zh: {
        textPlaceholder: 'Nhập nội dung...',
        header: {
          text: 'Tiêu đề',
        },
        image: {
          netImage: 'Ảnh từ mạng',
          delete: 'Xóa ảnh',
          edit: 'Chỉnh sửa ảnh',
          viewLink: 'Xem liên kết',
          src: 'Đường dẫn ảnh',
          desc: 'Mô tả',
          link: 'Liên kết',
        },
        link: {
          insert: 'Chèn liên kết',
          text: 'Văn bản liên kết',
          url: 'URL',
          unLink: 'Gỡ liên kết',
          edit: 'Sửa liên kết',
          view: 'Xem liên kết',
        },
      },
    };
    
    if (editor.i18nChangeLanguage) {
      editor.i18nChangeLanguage('zh');
    }
  };

  const handleCustomPaste = (editor: any, event: ClipboardEvent, callback: any) => {
    const items = event.clipboardData?.items;
    if (!items) {
      callback(true);
      return;
    }

    let hasImage = false;

    for (let i = 0; i < items.length; i++) {
      const item = items[i];

      if (item.type.indexOf('image') !== -1) {
        hasImage = true;
        const file = item.getAsFile();

        if (file) {
          const isLt5M = file.size / 1024 / 1024 < 5;
          if (!isLt5M) {
            message.error('Kích thước ảnh phải nhỏ hơn 5MB!');
            event.preventDefault();
            callback(false);
            return;
          }

          const reader = new FileReader();
          reader.onload = (e: any) => {
            const base64String = e.target.result;
            
            editor.dangerouslyInsertHtml(
              `<img src="${base64String}" alt="pasted-image" style="max-width: 100%;" />`
            );
            
            message.success('Đã dán ảnh từ clipboard!');
          };

          reader.onerror = () => {
            message.error('Lỗi khi đọc ảnh từ clipboard!');
          };

          reader.readAsDataURL(file);
        }

        event.preventDefault();
        callback(false);
        return;
      }
    }

    callback(true);
  };

  // ============ UPLOAD HANDLERS - ẢNH BÌA ============
  const beforeUploadBia: UploadProps['beforeUpload'] = (file) => {
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
      selectedFileBia.value = file as File;

      const url = URL.createObjectURL(file as File);
      fileListBia.value = [
        {
          uid: file.uid ?? `${Date.now()}`,
          name: file.name,
          status: 'done',
          originFileObj: file,
          url,
          thumbUrl: url,
        },
      ];
    } catch (e) {
      console.error('beforeUploadBia error', e);
    }

    return false;
  };

  function handleRemoveFileBia() {
    try {
      const url = fileListBia.value[0]?.url || fileListBia.value[0]?.thumbUrl;
      if (url && url.startsWith('blob:')) {
        URL.revokeObjectURL(url);
      }
      selectedFileBia.value = null;
      fileListBia.value = [];
    } catch (err) {
      console.error('handleRemoveFileBia error', err);
    }
  }

  function onUploadChangeBia(e: any) {
    try {
      const fl = (e && e.fileList) || [];
      if (fl.length > 0) {
        const f = fl[0];
        const origin = f.originFile || f.originFileObj;

        if (origin) {
          selectedFileBia.value = origin;
        }

        const url = origin ? URL.createObjectURL(origin) : f.url || f.thumbUrl;
        fileListBia.value = [
          {
            uid: f.uid,
            name: f.name,
            status: f.status || 'done',
            url,
            thumbUrl: url,
            originFileObj: origin,
          },
        ];
      } else {
        selectedFileBia.value = null;
        fileListBia.value = [];
      }
    } catch (err) {
      console.error('onUploadChangeBia error', err);
    }
  }

  // ============ UPLOAD HANDLERS - ẢNH NGOÀI ============
  const beforeUploadNgoai: UploadProps['beforeUpload'] = (file) => {
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
      selectedFileNgoai.value = file as File;

      const url = URL.createObjectURL(file as File);
      fileListNgoai.value = [
        {
          uid: file.uid ?? `${Date.now()}`,
          name: file.name,
          status: 'done',
          originFileObj: file,
          url,
          thumbUrl: url,
        },
      ];
    } catch (e) {
      console.error('beforeUploadNgoai error', e);
    }

    return false;
  };

  function handleRemoveFileNgoai() {
    try {
      const url = fileListNgoai.value[0]?.url || fileListNgoai.value[0]?.thumbUrl;
      if (url && url.startsWith('blob:')) {
        URL.revokeObjectURL(url);
      }
      selectedFileNgoai.value = null;
      fileListNgoai.value = [];
    } catch (err) {
      console.error('handleRemoveFileNgoai error', err);
    }
  }

  function onUploadChangeNgoai(e: any) {
    try {
      const fl = (e && e.fileList) || [];
      if (fl.length > 0) {
        const f = fl[0];
        const origin = f.originFile || f.originFileObj;

        if (origin) {
          selectedFileNgoai.value = origin;
        }

        const url = origin ? URL.createObjectURL(origin) : f.url || f.thumbUrl;
        fileListNgoai.value = [
          {
            uid: f.uid,
            name: f.name,
            status: f.status || 'done',
            url,
            thumbUrl: url,
            originFileObj: origin,
          },
        ];
      } else {
        selectedFileNgoai.value = null;
        fileListNgoai.value = [];
      }
    } catch (err) {
      console.error('onUploadChangeNgoai error', err);
    }
  }

  // ============ FORM HANDLERS ============
  function convertHtmlToTxtFile(html: string, fileName: string): File {
    const BOM = '\uFEFF';
    const content = BOM + html;
    
    const blob = new Blob([content], { 
      type: 'text/plain;charset=utf-8' 
    });
    
    return new File([blob], fileName, { 
      type: 'text/plain;charset=utf-8' 
    });
  }

  function validateForm(): boolean {
    if (!loaiBaiViet.value.trim()) {
      message.error('Vui lòng nhập loại bài viết!');
      return false;
    }

    if (!tieuDe.value.trim()) {
      message.error('Vui lòng nhập tiêu đề!');
      return false;
    }

    if (!selectedFileBia.value) {
      message.error('Vui lòng chọn ảnh bìa!');
      return false;
    }

    if (!selectedFileNgoai.value) {
      message.error('Vui lòng chọn ảnh ngoài!');
      return false;
    }

    if (!valueHtml.value || valueHtml.value === '<p></p>' || valueHtml.value.trim() === '') {
      message.error('Vui lòng nhập nội dung!');
      return false;
    }

    return true;
  }

  async function handleSubmit() {
    if (!validateForm()) return;

    submitting.value = true;

    try {
      const formData = new FormData();

      // 1. DTO
      const dto = {
        loaiBaiViet: loaiBaiViet.value,
        tieuDe: tieuDe.value,
        duongDanYoutube: duongDanYoutube.value,
        taoBoi: null,
        lienQuan: '',
        trangThai: 1,
      };

      const dtoBlob = new Blob([JSON.stringify(dto)], { type: 'application/json' });
      formData.append('dto', dtoBlob);

      // 2. Ảnh bìa
      formData.append('anh_bia', selectedFileBia.value!);

      // 3. Ảnh ngoài
      formData.append('anh_ngoai', selectedFileNgoai.value!);

      // 4. Nội dung (file txt)
      const noiDungFile = convertHtmlToTxtFile(valueHtml.value, `${tieuDe.value}_noi_dung.txt`);
      formData.append('noi_dung', noiDungFile);

      let host = realHttp.getAxios().defaults.baseURL as string

      const response = await fetch(host+'/basic-api/bai-viet/create', {
        method: 'POST',
        body: formData,
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Submit thất bại');
      }

      const result = await response.json();
      message.success('Tạo bài viết thành công!');
      console.log('Response:', result);

      handleReset();

      return result;
    } catch (error: any) {
      message.error(error.message || 'Lỗi khi tạo bài viết!');
      console.error('Submit error:', error);
    } finally {
      submitting.value = false;
    }
  }

  function handleReset() {
    loaiBaiViet.value = 'MEGA_STORY';
    tieuDe.value = '';
    valueHtml.value = '';
    handleRemoveFileBia();
    handleRemoveFileNgoai();

    const editor = editorRef.value;
    if (editor) {
      editor.clear();
    }

    message.info('Đã làm mới form');
  }

  // ============ EXPOSE ============
  defineExpose({
    handleSubmit,
    handleReset,
    loaiBaiViet,
    tieuDe,
    valueHtml,
    selectedFileBia,
    selectedFileNgoai,
  });
</script>

<style lang="less" scoped>
  .bai-viet-form {
    max-width: 1200px;
    margin: 0 auto;
    padding: 24px;
    border-radius: 8px;
  }

  .upload-container {
    display: flex;
    flex-direction: column;
    gap: 8px;
  }

  .upload-hint {
    color: #999;
    font-size: 12px;
  }

  :deep(.w-e-text-placeholder) {
    top: 10px;
    left: 10px;
  }

  :deep(.ant-form-item) {
    margin-bottom: 24px;
  }
</style>