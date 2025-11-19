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

    <!-- Ảnh bìa -->
    <FormItem label="Ảnh bìa" name="anhBia">
      <div class="upload-container">
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
          <div v-if="fileList.length < 1" style="cursor: pointer">
            <PlusOutlined />
            <div style="margin-top: 8px">Tải ảnh bìa</div>
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

  // ============ STATES ============
  const editorRef = shallowRef();
  const loaiBaiViet = ref('MEGA_STORY');
  const tieuDe = ref('');
  const selectedFile = ref<File | null>(null);
  const fileList = ref<any[]>([]);
  const valueHtml = ref('');
  const submitting = ref(false);
  const antUpload = ref<any>(null);

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
      'uploadImage', // Thay đổi từ 'insertImage' sang 'uploadImage'
      'insertTable',
      '|',
      'undo',
      'redo',
    ],
  };

  const editorConfig = {
    placeholder: 'Nhập nội dung bài viết của bạn...',
    MENU_CONF: {
      // Cấu hình upload ảnh
      uploadImage: {
        // Tùy chỉnh text hiển thị
        fieldName: 'file',
        
        // Chọn ảnh từ máy và convert sang base64
        customBrowseAndUpload(insertFn: any) {
          // Tạo input file ẩn
          const input = document.createElement('input');
          input.type = 'file';
          input.accept = 'image/*';
          input.multiple = true; // Cho phép chọn nhiều ảnh
          
          input.onchange = (e: any) => {
            const files = e.target.files;
            if (!files || files.length === 0) return;
            
            // Xử lý từng file
            Array.from(files).forEach((file: any) => {
              processImageFile(file, insertFn);
            });
          };
          
          input.click();
        },
        
        // Xử lý khi paste ảnh từ clipboard
        customPaste: (editor: any, event: ClipboardEvent) => {
          const items = event.clipboardData?.items;
          if (!items) return false;
          
          let hasImage = false;
          
          for (let i = 0; i < items.length; i++) {
            const item = items[i];
            
            // Kiểm tra nếu là ảnh
            if (item.type.indexOf('image') !== -1) {
              hasImage = true;
              event.preventDefault(); // Ngăn paste mặc định
              
              const file = item.getAsFile();
              if (file) {
                processImageFile(file, (url: string, alt: string, href: string) => {
                  editor.dangerouslyInsertHtml(`<img src="${url}" alt="${alt}" style="max-width: 100%;" />`);
                });
              }
            }
          }
          
          return hasImage; // true = đã xử lý, false = để editor xử lý mặc định
        },
        
        // Xử lý khi kéo thả ảnh vào editor
        customDrop: (editor: any, event: DragEvent) => {
          const files = event.dataTransfer?.files;
          if (!files || files.length === 0) return false;
          
          let hasImage = false;
          
          Array.from(files).forEach((file: any) => {
            if (file.type.startsWith('image/')) {
              hasImage = true;
              event.preventDefault(); // Ngăn drop mặc định
              
              processImageFile(file, (url: string, alt: string, href: string) => {
                editor.dangerouslyInsertHtml(`<img src="${url}" alt="${alt}" style="max-width: 100%;" />`);
              });
            }
          });
          
          return hasImage;
        },
        
        // Xử lý khi chèn ảnh
        onInsertedImage(imageNode: any) {
          console.log('Đã chèn ảnh:', imageNode);
        },
        
        // Kiểm tra file trước khi upload
        onBeforeUpload(file: File) {
          return true;
        },
      },
    },
  };

  // Hàm xử lý ảnh chung (dùng cho upload, paste, drag-drop)
  function processImageFile(file: File, insertFn: any) {
    // Kiểm tra file
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
    
    // Convert sang base64
    const reader = new FileReader();
    reader.onload = (event: any) => {
      const base64String = event.target.result;
      const alt = file.name || 'image';
      const href = base64String;
      
      // Chèn ảnh base64 vào editor
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
    
    // Cấu hình ngôn ngữ tiếng Việt cho editor
    const i18nConfig = {
      zh: {
        // Thay đổi text tiếng Trung sang tiếng Việt
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
    
    // Áp dụng config (nếu editor có hỗ trợ)
    if (editor.i18nChangeLanguage) {
      editor.i18nChangeLanguage('zh'); // Giữ 'zh' nhưng nội dung đã đổi ở trên
    }
  };

  // Xử lý paste ảnh từ clipboard
  const handleCustomPaste = (editor: any, event: ClipboardEvent, callback: any) => {
    // Lấy dữ liệu từ clipboard
    const items = event.clipboardData?.items;
    if (!items) {
      callback(true); // Tiếp tục paste mặc định
      return;
    }

    let hasImage = false;

    // Duyệt qua các items trong clipboard
    for (let i = 0; i < items.length; i++) {
      const item = items[i];

      // Kiểm tra nếu là ảnh
      if (item.type.indexOf('image') !== -1) {
        hasImage = true;
        const file = item.getAsFile();

        if (file) {
          // Kiểm tra kích thước
          const isLt5M = file.size / 1024 / 1024 < 5;
          if (!isLt5M) {
            message.error('Kích thước ảnh phải nhỏ hơn 5MB!');
            event.preventDefault();
            callback(false);
            return;
          }

          // Convert sang base64 và chèn vào editor
          const reader = new FileReader();
          reader.onload = (e: any) => {
            const base64String = e.target.result;
            
            // Chèn ảnh vào editor
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

        // Ngăn paste mặc định
        event.preventDefault();
        callback(false);
        return;
      }
    }

    // Nếu không có ảnh, tiếp tục paste mặc định
    callback(true);
  };

  // ============ UPLOAD HANDLERS ============
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
      selectedFile.value = file as File;

      const url = URL.createObjectURL(file as File);
      fileList.value = [
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
      console.error('beforeUpload error', e);
    }

    return false;
  };

  function handleRemoveFile() {
    try {
      const url = fileList.value[0]?.url || fileList.value[0]?.thumbUrl;
      if (url && url.startsWith('blob:')) {
        URL.revokeObjectURL(url);
      }
      selectedFile.value = null;
      fileList.value = [];
    } catch (err) {
      console.error('handleRemoveFile error', err);
    }
  }

  function onUploadChange(e: any) {
    try {
      const fl = (e && e.fileList) || [];
      if (fl.length > 0) {
        const f = fl[0];
        const origin = f.originFile || f.originFileObj;

        if (origin) {
          selectedFile.value = origin;
        }

        const url = origin ? URL.createObjectURL(origin) : f.url || f.thumbUrl;
        fileList.value = [
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
        selectedFile.value = null;
        fileList.value = [];
      }
    } catch (err) {
      console.error('onUploadChange error', err);
    }
  }

  // ============ FORM HANDLERS ============
  function convertHtmlToTxtFile(html: string, fileName: string): File {
    // Thêm UTF-8 BOM để đảm bảo encoding đúng
    const BOM = '\uFEFF';
    const content = BOM + html;
    
    // Tạo Blob với charset UTF-8
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

    if (!selectedFile.value) {
      message.error('Vui lòng chọn ảnh bìa!');
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
        taoBoi: null,
        lienQuan: '',
        trangThai: 1,
      };

      const dtoBlob = new Blob([JSON.stringify(dto)], { type: 'application/json' });
      formData.append('dto', dtoBlob);

      // 2. Ảnh bìa
      formData.append('anh_bia', selectedFile.value!);

      // 3. Nội dung (file txt)
      const noiDungFile = convertHtmlToTxtFile(valueHtml.value, `${tieuDe.value}_noi_dung.txt`);
      formData.append('noi_dung', noiDungFile);

      // Gửi request - THAY URL NÀY BẰNG API THỰC TẾ CỦA BẠN
      // let host = 'http://localhost:8080'
      let host = 'http://103.161.16.66/api'

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

      // Reset form sau khi thành công
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
    handleRemoveFile();

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
    selectedFile,
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
