<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    title="Tạo tên miền"
    :width="800"
    @ok="handleSubmit"
  >
    <BasicForm @register="registerForm">
      <!-- ─── Slot: upload file ─────────────────────────────────────── -->
      <template #file>
        <div class="file-container">
          <Upload
            :file-list="fileList"
            list-type="picture-card"
            :before-upload="beforeUpload"
            @remove="handleRemoveFile"
            :max-count="1"
            accept="image/*"
          >
            <div v-if="fileList.length === 0" style="cursor: pointer">
              <PlusOutlined />
              <div style="margin-top: 8px">Tải ảnh lên</div>
            </div>
          </Upload>
          <div class="upload-hint">Định dạng: JPG, PNG, GIF – tối đa 5 MB</div>
        </div>
      </template>

      <!-- ─── Slot: thông tin tên miền ────────────────────────────────── -->
      <template #thongTinMiens>
        <div class="thong-tin-mien-container">

          <!-- Nút thêm -->
          <Button type="dashed" block @click="handleAddThongTin" style="margin-bottom: 16px">
            <template #icon><PlusOutlined /></template>
            Thêm thông tin tên miền
          </Button>

          <Empty
            v-if="thongTinMienList.length === 0"
            description="Chưa có thông tin tên miền"
            :image-style="{ height: '60px' }"
          />

          <!-- Danh sách -->
          <div
            v-for="(item, index) in thongTinMienList"
            :key="`ttm-${index}`"
            class="thong-tin-item"
          >
            <!-- Header row -->
            <div class="thong-tin-item-header">
              <span class="thong-tin-item-title">Thông tin #{{ index + 1 }}</span>
              <Button type="primary" danger size="small" @click="handleRemoveThongTin(index)">
                <template #icon><DeleteOutlined /></template>
                Xóa
              </Button>
            </div>

            <Card size="small" :bordered="true">
              <Row :gutter="16">
                <!-- Tên miền con -->
                <Col :span="24">
                  <FormItem label="Tên miền" :required="true">
                    <Input
                      v-model:value="item.tenMien"
                      placeholder="VD: www.example.com"
                      :maxlength="100"
                    />
                  </FormItem>
                </Col>

                <!-- SĐT -->
                <Col :span="12">
                  <FormItem label="Số điện thoại">
                    <Input
                      v-model:value="item.sdt"
                      placeholder="VD: 0912345678"
                      :maxlength="20"
                    >
                      <template #prefix>
                        <PhoneOutlined style="color: #bfbfbf" />
                      </template>
                    </Input>
                  </FormItem>
                </Col>

                <!-- Email -->
                <Col :span="12">
                  <FormItem label="Email">
                    <Input
                      v-model:value="item.email"
                      placeholder="VD: contact@example.com"
                      :maxlength="100"
                    >
                      <template #prefix>
                        <MailOutlined style="color: #bfbfbf" />
                      </template>
                    </Input>
                  </FormItem>
                </Col>
              </Row>
            </Card>
          </div>
        </div>
      </template>
    </BasicForm>
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref } from 'vue';
  import {
    Upload,
    Button,
    Card,
    Row,
    Col,
    Input,
    FormItem,
    Empty,
    message,
  } from 'ant-design-vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { BasicForm, useForm } from '@/components/Form';
  import {
    PlusOutlined,
    DeleteOutlined,
    PhoneOutlined,
    MailOutlined,
  } from '@ant-design/icons-vue';
  import { formSchema } from './mien.data';
  import { createMien } from './mien';
  import type { ThongTinMienCreatingDto } from './mien';
  import type { UploadProps } from 'ant-design-vue';

  defineOptions({ name: 'MienModal' });

  const props = defineProps<{
    coSoOptions: Array<{ label: string; value: number }>;
  }>();

  const emit = defineEmits(['success', 'register']);

  // ─── State ────────────────────────────────────────────────────────────────
  const fileList = ref<any[]>([]);
  const selectedFile = ref<File | null>(null);
  const thongTinMienList = ref<ThongTinMienCreatingDto[]>([]);

  // ─── Form ─────────────────────────────────────────────────────────────────
  const [registerForm, { resetFields, validate, updateSchema }] = useForm({
    labelWidth: 140,
    schemas: formSchema,
    showActionButtonGroup: false,
    baseColProps: { span: 24 },
  });

  // ─── Modal ────────────────────────────────────────────────────────────────
  const [registerModal, { setModalProps, closeModal }] = useModalInner(async () => {
    resetFields();
    setModalProps({ confirmLoading: false });
    fileList.value = [];
    selectedFile.value = null;
    thongTinMienList.value = [];

    updateSchema([
      {
        field: 'coSoId',
        componentProps: { options: props.coSoOptions },
      },
    ]);
  });

  // ─── Quản lý thongTinMiens ────────────────────────────────────────────────
  function handleAddThongTin() {
    thongTinMienList.value.push({ tenMien: '', sdt: '', email: '' });
  }

  function handleRemoveThongTin(index: number) {
    thongTinMienList.value.splice(index, 1);
  }

  // ─── Upload ───────────────────────────────────────────────────────────────
  const beforeUpload: UploadProps['beforeUpload'] = (file) => {
    const isImage = file.type?.startsWith('image/');
    if (!isImage) {
      message.error('Chỉ chấp nhận file ảnh!');
      return false;
    }
    const isLt5M = file.size / 1024 / 1024 < 5;
    if (!isLt5M) {
      message.error('Kích thước ảnh phải nhỏ hơn 5 MB!');
      return false;
    }
    const url = URL.createObjectURL(file as File);
    selectedFile.value = file as File;
    fileList.value = [
      {
        uid: file.uid,
        name: file.name,
        status: 'done',
        originFileObj: file,
        url,
        thumbUrl: url,
      },
    ];
    return false;
  };

  function handleRemoveFile() {
    if (fileList.value[0]?.url?.startsWith('blob:')) {
      URL.revokeObjectURL(fileList.value[0].url);
    }
    fileList.value = [];
    selectedFile.value = null;
  }

  // ─── Submit ───────────────────────────────────────────────────────────────
  async function handleSubmit() {
    try {
      const values = await validate();
      setModalProps({ confirmLoading: true });

      if (!selectedFile.value) {
        message.error('Vui lòng chọn hình ảnh / logo cho tên miền!');
        setModalProps({ confirmLoading: false });
        return;
      }

      // Validate từng thongTinMien
      for (let i = 0; i < thongTinMienList.value.length; i++) {
        const ttm = thongTinMienList.value[i];
        if (!ttm.tenMien?.trim()) {
          message.error(`Vui lòng nhập tên miền cho thông tin #${i + 1}`);
          setModalProps({ confirmLoading: false });
          return;
        }
        if (ttm.email && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(ttm.email)) {
          message.error(`Email không hợp lệ ở thông tin #${i + 1}`);
          setModalProps({ confirmLoading: false });
          return;
        }
      }

      const dto = {
        tenMien: values.tenMien,
        coSoId: values.coSoId || undefined,
        thongTinMiens:
          thongTinMienList.value.length > 0
            ? thongTinMienList.value.map((ttm) => ({
                tenMien: ttm.tenMien.trim(),
                sdt: ttm.sdt?.trim() || '',
                email: ttm.email?.trim() || '',
              }))
            : undefined,
      };

      const result = await createMien(dto, selectedFile.value);

      if (result.status === 200 || result.status === 201) {
        if (fileList.value[0]?.url?.startsWith('blob:')) {
          URL.revokeObjectURL(fileList.value[0].url);
        }
        message.success('Tạo mới thành công');
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
  .file-container {
    .upload-hint {
      margin-top: 8px;
      color: #999;
      font-size: 12px;
    }
  }

  .thong-tin-mien-container {
    .thong-tin-item {
      margin-bottom: 16px;

      &:last-child {
        margin-bottom: 0;
      }

      .thong-tin-item-header {
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 8px;

        .thong-tin-item-title {
          color: #262626;
          font-size: 14px;
          font-weight: 500;
        }
      }
    }

    :deep(.ant-card-body) {
      padding: 16px;
    }

    :deep(.ant-form-item) {
      margin-bottom: 12px;

      &:last-child {
        margin-bottom: 0;
      }
    }
  }
</style>