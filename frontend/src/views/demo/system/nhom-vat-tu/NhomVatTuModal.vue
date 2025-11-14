<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    :title="getTitle"
    :width="900"
    @ok="handleSubmit"
  >
    <BasicForm @register="registerForm">
      <template #thuocTinhRieng="{ model }">
        <div class="thuoc-tinh-rieng-container">
          <a-button 
            type="dashed" 
            block 
            @click="handleAddThuocTinh"
            style="margin-bottom: 16px"
          >
            <template #icon>
              <PlusOutlined />
            </template>
            Thêm thuộc tính
          </a-button>
          
          <div 
            v-for="(item, index) in thuocTinhRiengList" 
            :key="index"
            class="thuoc-tinh-item"
          >
            <div style="display: flex; align-items: center; justify-content: space-between; margin-bottom: 8px;">
              <span style=" font-size: 14px;font-weight: 500;">Thuộc tính {{ index + 1 }}</span>
              <a-button 
                type="primary" 
                danger 
                size="small"
                @click="handleRemoveThuocTinh(index)"
              >
                <template #icon>
                  <DeleteOutlined />
                </template>
                Xóa
              </a-button>
            </div>
            
            <a-card size="small" :bordered="true">
              
              <a-row :gutter="16">
                <a-col :span="6">
                  <a-form-item 
                    label="Key" 
                    :required="true"
                    :validate-status="item.keyError ? 'error' : ''"
                    :help="item.keyError"
                  >
                    <a-input 
                      v-model:value="item.key" 
                      placeholder="VD: dien_ap, loaimai"
                      @blur="validateKey(index)"
                      @input="handleKeyInput(index)"
                    />
                  </a-form-item>
                </a-col>
                <a-col :span="6">
                  <a-form-item label="Tên thuộc tính" :required="true">
                    <a-input 
                      v-model:value="item.ten" 
                      placeholder="VD: Điện áp"
                    />
                  </a-form-item>
                </a-col>
                <a-col :span="6">
                  <a-form-item label="Đơn vị">
                    <a-input 
                      v-model:value="item.donVi" 
                      placeholder="VD: cm, kg, m2"
                    />
                  </a-form-item>
                </a-col>
                <a-col :span="6">
                  <a-form-item label="Giá trị">
                    <a-input 
                      v-model:value="item.giaTri" 
                      placeholder="Giá trị mặc định"
                    />
                  </a-form-item>
                </a-col>
              </a-row>
            </a-card>
          </div>
        </div>
      </template>
    </BasicForm>
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref, computed, unref } from 'vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { BasicForm, useForm } from '@/components/Form';
  import { PlusOutlined, DeleteOutlined } from '@ant-design/icons-vue';
  import { formSchema } from './nhomVatTu.data';
  import { createNhomVatTu, updateNhomVatTu } from './nhomVatTu';
  import { message } from 'ant-design-vue';

  defineOptions({ name: 'NhomVatTuModal' });

  const props = defineProps<{
    nghanhHangOptions: Array<{ label: string; value: number }>;
  }>();

  const emit = defineEmits(['success', 'register']);

  const isUpdate = ref(false);
  const recordId = ref<number>();
  const thuocTinhRiengList = ref<Array<{
    key: string;
    ten: string;
    donVi: string;
    giaTri: any;
    keyError?: string;
  }>>([]);

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
    thuocTinhRiengList.value = [];

    // Cập nhật options cho select nghanhHangId
    updateSchema({
      field: 'nghanhHangId',
      componentProps: {
        options: props.nghanhHangOptions,
      },
    });

    if (unref(isUpdate)) {
      recordId.value = data.record.id;
      
      // Convert thuocTinhRieng from object to array
      if (data.record.thuocTinhRieng && Object.keys(data.record.thuocTinhRieng).length > 0) {
        thuocTinhRiengList.value = Object.entries(data.record.thuocTinhRieng).map(([key, value]: [string, any]) => ({
          key: key,
          ten: value.ten,
          donVi: value.donVi || '',
          giaTri: value.giaTri || null,
          keyError: undefined,
        }));
      }
      
      setFieldsValue({
        ...data.record,
        nghanhHangId: data.record.nghanhHang?.id,
        vatTuChinh: data.record.vatTuChinh ? 1 : 0,
      });
    }
  });

  const getTitle = computed(() => (!unref(isUpdate) ? 'Tạo nhóm vật tư' : 'Chỉnh sửa nhóm vật tư'));

  function handleAddThuocTinh() {
    thuocTinhRiengList.value.push({
      key: '',
      ten: '',
      donVi: '',
      giaTri: null,
      keyError: undefined,
    });
  }

  function handleRemoveThuocTinh(index: number) {
    thuocTinhRiengList.value.splice(index, 1);
  }

  function handleKeyInput(index: number) {
    // Clear error khi người dùng bắt đầu nhập
    thuocTinhRiengList.value[index].keyError = undefined;
  }

  function validateKey(index: number) {
    const item = thuocTinhRiengList.value[index];
    const key = item.key?.trim();

    if (!key) {
      return; // Sẽ validate required khi submit
    }

    // Regex: chỉ cho phép chữ thường, số và dấu gạch dưới, không bắt đầu bằng số
    const validKeyPattern = /^[a-z][a-z0-9_]*$/;

    if (!validKeyPattern.test(key)) {
      item.keyError = 'Key chỉ được chứa chữ thường, số và dấu gạch dưới (_), không có dấu, không bắt đầu bằng số';
      return false;
    }

    // Kiểm tra trùng lặp key
    const duplicateIndex = thuocTinhRiengList.value.findIndex((t, i) => 
      i !== index && t.key?.trim().toLowerCase() === key.toLowerCase()
    );

    if (duplicateIndex !== -1) {
      item.keyError = 'Key này đã được sử dụng';
      return false;
    }

    item.keyError = undefined;
    return true;
  }

  function validateAllKeys(): boolean {
    let hasError = false;

    for (let i = 0; i < thuocTinhRiengList.value.length; i++) {
      const item = thuocTinhRiengList.value[i];
      
      if (!item.key?.trim()) {
        item.keyError = 'Vui lòng nhập key';
        hasError = true;
        continue;
      }

      if (!validateKey(i)) {
        hasError = true;
      }
    }

    return !hasError;
  }

  async function handleSubmit() {
    try {
      const values = await validate();
      setModalProps({ confirmLoading: true });

      // Validate thuocTinhRieng
      for (let i = 0; i < thuocTinhRiengList.value.length; i++) {
        const item = thuocTinhRiengList.value[i];
        
        if (!item.ten?.trim()) {
          message.error(`Vui lòng nhập tên thuộc tính ${i + 1}`);
          setModalProps({ confirmLoading: false });
          return;
        }
      }

      // Validate all keys
      if (!validateAllKeys()) {
        message.error('Vui lòng kiểm tra lại các key thuộc tính');
        setModalProps({ confirmLoading: false });
        return;
      }

      // Transform thuocTinhRieng from array to map using custom keys
      const thuocTinhRiengMap = {};
      if (thuocTinhRiengList.value.length > 0) {
        thuocTinhRiengList.value.forEach((item) => {
          const key = item.key.trim();
          thuocTinhRiengMap[key] = {
            ten: item.ten,
            donVi: item.donVi || '',
            giaTri: item.giaTri || null,
          };
        });
      }

      const submitData = {
        ma: values.ma,
        nghanhHangId: values.nghanhHangId,
        ten: values.ten,
        thuocTinhRieng: thuocTinhRiengMap,
        gm: values.gm,
        vatTuChinh: values.vatTuChinh === 1,
        trangThai: values.trangThai,
      };

      let result;
      if (unref(isUpdate)) {
        result = await updateNhomVatTu(recordId.value!, submitData);
      } else {
        result = await createNhomVatTu(submitData);
      }

      if (result.status === 200 || result.status === 201) {
        message.success(unref(isUpdate) ? 'Cập nhật thành công' : 'Tạo mới thành công');
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
.thuoc-tinh-rieng-container {
  .thuoc-tinh-item {
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
</style>