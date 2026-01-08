<template>
  <div class="bai-viet-update-page">
    <h2 style="margin-bottom: 24px">Cập nhật bài viết</h2>

    <!-- Phần tìm kiếm và chọn bài viết -->
    <div class="search-section">
      <FormItem label="Tìm kiếm bài viết">
        <div style="display: flex; gap: 12px">
          <Input
            v-model:value="searchKeyword"
            placeholder="Nhập tiêu đề bài viết..."
            style="flex: 1"
            @pressEnter="handleSearch"
          />
          <Button type="primary" @click="handleSearch" :loading="searching">
            Tìm kiếm
          </Button>
        </div>
      </FormItem>

      <FormItem label="Chọn bài viết cần sửa">
        <Select
          v-model:value="selectedBaiVietId"
          placeholder="Chọn bài viết..."
          show-search
          :filter-option="false"
          @search="handleSelectSearch"
          @change="handleSelectChange"
          :loading="loadingSelect"
          style="width: 100%"
        >
          <SelectOption
            v-for="item in baiVietList"
            :key="item.id"
            :value="item.id"
          >
            {{ item.tieuDe }} ({{ item.loaiBaiViet }})
          </SelectOption>
        </Select>
      </FormItem>

      <div v-if="selectedBaiViet" class="selected-info">
        <div><strong>ID:</strong> {{ selectedBaiViet.id }}</div>
        <div><strong>Loại:</strong> {{ selectedBaiViet.loaiBaiViet }}</div>
        <div><strong>Trạng thái:</strong> {{ selectedBaiViet.trangThai === 1 ? 'Hiển thị' : 'Ẩn' }}</div>
      </div>
    </div>

    <Divider />

    <!-- Form cập nhật -->
    <div v-if="selectedBaiViet" class="bai-viet-form">
      <!-- Loại bài viết -->
      <FormItem label="Loại bài viết" name="loaiBaiViet">
        <Select v-model:value="loaiBaiViet">
          <SelectOption value="MEGA_STORY">MEGA STORY</SelectOption>
          <SelectOption value="HOI_DAP">HỎI ĐÁP</SelectOption>
          <SelectOption value="HUONG_DAN_BAO_HANH">HƯỚNG DẪN BẢO HÀNH</SelectOption>
        </Select>
      </FormItem>

      <!-- Tiêu đề -->
      <FormItem label="Tiêu đề" name="tieuDe">
        <Textarea
          v-model:value="tieuDe"
          placeholder="Nhập tiêu đề bài viết"
          :maxlength="400"
          :auto-size="{ minRows: 2, maxRows: 4 }"
        />
      </FormItem>

      <!-- Đường dẫn youtube -->
      <FormItem label="Đường dẫn Youtube" name="duongDanYoutube">
        <Textarea
          v-model:value="duongDanYoutube"
          placeholder="Nhập đường dẫn Youtube"
          :maxlength="1000"
          :auto-size="{ minRows: 2, maxRows: 4 }"
        />
      </FormItem>

      <!-- Ảnh bìa hiện tại -->
      <FormItem label="Ảnh bìa hiện tại">
        <div v-if="currentAnhBiaUrl" class="current-image">
          <img :src="currentAnhBiaUrl" alt="Ảnh bìa hiện tại" style="max-width: 200px; border-radius: 4px" />
          <div style="margin-top: 8px; color: #999; font-size: 12px">
            (Để trống nếu không muốn thay đổi)
          </div>
        </div>
      </FormItem>

      <!-- Ảnh bìa mới -->
      <FormItem label="Ảnh bìa mới (tùy chọn)" name="anhBia">
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
              <div style="margin-top: 8px">Tải ảnh mới</div>
            </div>
          </Upload>
          <div class="upload-hint">
            Tối đa 1 ảnh, định dạng: JPG, PNG, GIF (Tối đa 5MB)
          </div>
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

      <!-- Trạng thái -->
      <FormItem label="Trạng thái">
        <Select v-model:value="trangThai">
          <SelectOption :value="1">Hiển thị</SelectOption>
          <SelectOption :value="0">Ẩn</SelectOption>
        </Select>
      </FormItem>

      <!-- Nút submit -->
      <div style="display: flex; gap: 12px; justify-content: flex-end; margin-top: 24px">
        <Button @click="handleCancel">Hủy</Button>
        <Button type="primary" :loading="submitting" @click="handleUpdate">
          Cập nhật bài viết
        </Button>
      </div>
    </div>

    <div v-else class="empty-state">
      <p style=" padding: 40px 0; color: #999;text-align: center">
        Vui lòng tìm kiếm và chọn bài viết cần cập nhật
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
  import '@wangeditor/editor/dist/css/style.css';

  import { ref, shallowRef, onBeforeUnmount } from 'vue';
  import { Editor, Toolbar } from '@wangeditor/editor-for-vue';
  import {
    FormItem,
    Input,
    Textarea,
    Upload,
    Button,
    message,
    Select,
    SelectOption,
    Divider,
  } from 'ant-design-vue';
  import type { UploadProps } from 'ant-design-vue';
  import { PlusOutlined } from '@ant-design/icons-vue';
  import { realHttp } from '@/utils/http/axios';

  // ============ STATES ============
  const editorRef = shallowRef();
  
  // Tìm kiếm và chọn bài viết
  const searchKeyword = ref('');
  const searching = ref(false);
  const loadingSelect = ref(false);
  const baiVietList = ref<any[]>([]);
  const selectedBaiVietId = ref<number | undefined>(undefined);
  const selectedBaiViet = ref<any>(null);
  const currentAnhBiaUrl = ref('');

  // Form data
  const loaiBaiViet = ref('MEGA_STORY');
  const tieuDe = ref('');
  const duongDanYoutube = ref('');
  const selectedFile = ref<File | null>(null);
  const fileList = ref<any[]>([]);
  const valueHtml = ref('');
  const trangThai = ref(1);
  const submitting = ref(false);
  const antUpload = ref<any>(null);

  // ============ API HELPERS ============
  const API_BASE = (realHttp.getAxios().defaults.baseURL || 'http://localhost:8080') + '/basic-api/bai-viet';

  async function fetchBaiVietList(keyword: string = '') {
    loadingSelect.value = true;
    try {
      const filterRequest = {
        filters: keyword
          ? [
              {
                fieldName: 'tieuDe',
                operation: 'ILIKE',
                value: keyword,
                logicType: 'AND',
              },
            ]
          : [],
        sorts: [
          {
            fieldName: 'id',
            ascending: false,
          },
        ],
        page: 0,
        size: 50,
      };

      const response = await fetch(`${API_BASE}/filter`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(filterRequest),
      });

      if (!response.ok) throw new Error('Lỗi khi tải danh sách bài viết');

      const result = await response.json();
      baiVietList.value = result.data?.content || [];
    } catch (error: any) {
      message.error(error.message || 'Lỗi khi tải danh sách bài viết');
      baiVietList.value = [];
    } finally {
      loadingSelect.value = false;
    }
  }

  async function fetchBaiVietDetail(id: number) {
    try {
      const response = await fetch(`${API_BASE}/get-by-id/${id}`);
      if (!response.ok) throw new Error('Không tìm thấy bài viết');

      const result = await response.json();
      return result.data;
    } catch (error: any) {
      message.error(error.message || 'Lỗi khi tải chi tiết bài viết');
      return null;
    }
  }

  // ============ SEARCH HANDLERS ============
  async function handleSearch() {
    searching.value = true;
    await fetchBaiVietList(searchKeyword.value);
    searching.value = false;
  }

  async function handleSelectSearch(value: string) {
    await fetchBaiVietList(value);
  }

  async function handleSelectChange(value: number) {
    if (!value) {
      selectedBaiViet.value = null;
      return;
    }

    const detail = await fetchBaiVietDetail(value);
    if (!detail) return;

    selectedBaiViet.value = detail;
    
    // Populate form với dữ liệu hiện tại
    loaiBaiViet.value = detail.loaiBaiViet || 'MEGA_STORY';
    tieuDe.value = detail.tieuDe || '';
    duongDanYoutube.value = detail.duongDanYoutube || '';
    trangThai.value = detail.trangThai ?? 1;
    
    // Load nội dung từ file txt nếu có
    if (detail.noiDungUrl) {
      try {
        const contentResponse = await fetch(detail.noiDungUrl);
        const content = await contentResponse.text();
        valueHtml.value = content;
        
        if (editorRef.value) {
          editorRef.value.setHtml(content);
        }
      } catch (error) {
        console.error('Lỗi khi load nội dung:', error);
      }
    }

    // Set ảnh bìa hiện tại
    currentAnhBiaUrl.value = detail.anhBiaUrl || '';
    
    // Reset file mới
    handleRemoveFile();

    message.success('Đã tải thông tin bài viết');
  }

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
    const BOM = '\uFEFF';
    const content = BOM + html;
    const blob = new Blob([content], {
      type: 'text/plain;charset=utf-8',
    });
    return new File([blob], fileName, {
      type: 'text/plain;charset=utf-8',
    });
  }

  function validateForm(): boolean {
    if (!selectedBaiViet.value) {
      message.error('Vui lòng chọn bài viết cần cập nhật!');
      return false;
    }

    if (!loaiBaiViet.value.trim()) {
      message.error('Vui lòng chọn loại bài viết!');
      return false;
    }

    if (!tieuDe.value.trim()) {
      message.error('Vui lòng nhập tiêu đề!');
      return false;
    }

    if (!valueHtml.value || valueHtml.value === '<p></p>' || valueHtml.value.trim() === '') {
      message.error('Vui lòng nhập nội dung!');
      return false;
    }

    return true;
  }

  async function handleUpdate() {
    if (!validateForm()) return;

    submitting.value = true;

    try {
      const formData = new FormData();

      // 1. DTO
      const dto = {
        id: selectedBaiViet.value.id,
        loaiBaiViet: loaiBaiViet.value,
        tieuDe: tieuDe.value,
        duongDanYoutube: duongDanYoutube.value,
        lienQuan: selectedBaiViet.value.lienQuan || '',
        trangThai: trangThai.value,
      };

      const dtoBlob = new Blob([JSON.stringify(dto)], { type: 'application/json' });
      formData.append('dto', dtoBlob);

      // 2. Ảnh bìa (nếu có thay đổi)
      if (selectedFile.value) {
        formData.append('anh_bia', selectedFile.value);
      } else {
        // Nếu không có file mới, tạo một blob rỗng
        const emptyBlob = new Blob([''], { type: 'application/octet-stream' });
        formData.append('anh_bia', emptyBlob, 'empty');
      }

      // 3. Nội dung (file txt)
      const noiDungFile = convertHtmlToTxtFile(valueHtml.value, `${tieuDe.value}_noi_dung.txt`);
      formData.append('noi_dung', noiDungFile);

      const response = await fetch(`${API_BASE}/update`, {
        method: 'PUT',
        body: formData,
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Cập nhật thất bại');
      }

      const result = await response.json();
      message.success('Cập nhật bài viết thành công!');
      console.log('Response:', result);

      // Refresh chi tiết bài viết
      await handleSelectChange(selectedBaiVietId.value!);

      return result;
    } catch (error: any) {
      message.error(error.message || 'Lỗi khi cập nhật bài viết!');
      console.error('Update error:', error);
    } finally {
      submitting.value = false;
    }
  }

  function handleCancel() {
    selectedBaiVietId.value = undefined;
    selectedBaiViet.value = null;
    currentAnhBiaUrl.value = '';
    loaiBaiViet.value = 'MEGA_STORY';
    tieuDe.value = '';
    duongDanYoutube.value = '';
    valueHtml.value = '';
    trangThai.value = 1;
    handleRemoveFile();

    const editor = editorRef.value;
    if (editor) {
      editor.clear();
    }

    message.info('Đã hủy chỉnh sửa');
  }

  // Load danh sách ban đầu
  fetchBaiVietList();
</script>

<style lang="less" scoped>
  .bai-viet-update-page {
    max-width: 1200px;
    margin: 0 auto;
    padding: 24px;
  }

  .search-section {
    margin-bottom: 24px;
    padding: 24px;
    border-radius: 8px;
    background: #fafafa;
  }

  .selected-info {
    margin-top: 16px;
    padding: 12px;
    border: 1px solid #91d5ff;
    border-radius: 4px;
    background: #e6f7ff;

    div {
      margin-bottom: 4px;

      &:last-child {
        margin-bottom: 0;
      }
    }
  }

  .bai-viet-form {
    padding: 24px;
    border-radius: 8px;
    background: white;
  }

  .current-image {
    img {
      display: block;
      border: 1px solid #d9d9d9;
    }
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

  .empty-state {
    display: flex;
    align-items: center;
    justify-content: center;
    min-height: 200px;
    border-radius: 8px;
    background: #fafafa;
  }

  :deep(.w-e-text-placeholder) {
    top: 10px;
    left: 10px;
  }

  :deep(.ant-form-item) {
    margin-bottom: 24px;
  }
</style>