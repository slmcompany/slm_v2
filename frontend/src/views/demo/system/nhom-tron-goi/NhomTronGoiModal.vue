<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    :title="getTitle"
    :width="800"
    @ok="handleSubmit"
  >
    <BasicForm @register="registerForm" />
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref, computed, unref } from 'vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { BasicForm, useForm } from '@/components/Form';
  import { message } from 'ant-design-vue';
  import { formSchema } from './nhomTronGoi.data';
  import { createNhomTronGoi, getNhomTronGoiById, getAllThuongHieu } from './nhomTronGoi';
  import type { NhomTronGoiCreateDto, ThuongHieuDto } from './nhomTronGoi';
  import { defHttp, realHttp } from '@/utils/http/axios';

  defineOptions({ name: 'NhomTronGoiModal' });

  const props = defineProps<{
    nganhHangOptions: Array<{ label: string; value: number }>;
  }>();

  const emit = defineEmits(['success', 'register']);

  const isUpdate = ref(false);
  const recordId = ref<number>();
  const thuongHieuOptions = ref<Array<{ label: string; value: number }>>([]);

  const [registerForm, { resetFields, setFieldsValue, validate, updateSchema }] = useForm({
    labelWidth: 160,
    schemas: formSchema,
    showActionButtonGroup: false,
    baseColProps: { span: 24 },
  });

  const [registerModal, { setModalProps, closeModal }] = useModalInner(async (data) => {
    resetFields();
    setModalProps({ confirmLoading: false });
    isUpdate.value = !!data?.isUpdate;

    // Load danh sách thương hiệu
    try {
      const result = await getAllThuongHieu();
      if (result?.data) {
        thuongHieuOptions.value = result.data.map((item: ThuongHieuDto) => ({
          label: item.ten,
          value: item.id,
        }));
      }
    } catch (error) {
      console.error('Load thương hiệu error:', error);
      message.error('Không thể tải danh sách thương hiệu');
    }

    // Cập nhật options cho form
    updateSchema([
      {
        field: 'nganhHangId',
        componentProps: {
          options: props.nganhHangOptions,
        },
      },
      {
        field: 'thuongHieuTamPinId',
        componentProps: {
          options: thuongHieuOptions.value,
        },
      },
      {
        field: 'thuongHieuInverterId',
        componentProps: {
          options: thuongHieuOptions.value,
        },
      },
      {
        field: 'thuongHieuPinLuuTruId',
        componentProps: {
          options: thuongHieuOptions.value,
        },
      },
    ]);

    if (unref(isUpdate)) {
      // Nếu là edit mode, load dữ liệu chi tiết
      const record = data.record;
      recordId.value = record.id;

      // Load chi tiết từ API nếu cần thiết
      try {
        // const detailResult = await getNhomTronGoiById(record.id);
        // const detailData = detailResult?.data || record;
        const detailData = record;

        setFieldsValue({
          nganhHangId: detailData.nganhHang?.id,
          ten: detailData.ten,
          thuongHieuTamPinId: detailData.thuongHieuTamPin?.id,
          thuongHieuInverterId: detailData.thuongHieuInverter?.id,
          thuongHieuPinLuuTruId: detailData.thuongHieuPinLuuTru?.id,
          trangThai: detailData.trangThai ?? 1,
        });
      } catch (error) {
        console.error('Load detail error:', error);
        // Fallback về data truyền vào
        setFieldsValue({
          nganhHangId: record.nganhHang?.id,
          ten: record.ten,
          thuongHieuTamPinId: record.thuongHieuTamPin?.id,
          thuongHieuInverterId: record.thuongHieuInverter?.id,
          thuongHieuPinLuuTruId: record.thuongHieuPinLuuTru?.id,
          trangThai: record.trangThai ?? 1,
        });
      }
    } else {
      // Tạo mới - set giá trị mặc định
      setFieldsValue({
        trangThai: 1,
      });
    }
  });

  const getTitle = computed(() =>
    !unref(isUpdate) ? 'Tạo nhóm trọn gói' : 'Chỉnh sửa nhóm trọn gói',
  );

  async function handleSubmit() {
    try {
      const values = await validate();
      setModalProps({ confirmLoading: true });

      // Validate required fields
      if (!values.nganhHangId) {
        message.error('Vui lòng chọn ngành hàng');
        setModalProps({ confirmLoading: false });
        return;
      }

      if (!values.ten || !values.ten.trim()) {
        message.error('Vui lòng nhập tên nhóm trọn gói');
        setModalProps({ confirmLoading: false });
        return;
      }

      if (!values.thuongHieuTamPinId) {
        message.error('Vui lòng chọn thương hiệu tấm pin');
        setModalProps({ confirmLoading: false });
        return;
      }

      if (!values.thuongHieuInverterId) {
        message.error('Vui lòng chọn thương hiệu inverter');
        setModalProps({ confirmLoading: false });
        return;
      }

      const submitData: NhomTronGoiCreateDto = {
        id: recordId.value,
        nganhHangId: values.nganhHangId,
        ten: values.ten,
        thuongHieuTamPinId: values.thuongHieuTamPinId,
        thuongHieuInverterId: values.thuongHieuInverterId,
        thuongHieuPinLuuTruId: values.thuongHieuPinLuuTruId,
        trangThai: values.trangThai ?? 1,
      };

      let result;
      if (unref(isUpdate)) {
        // Update
        result = await realHttp.put(
          {
            url: `/nhom-tron-goi/update`,
            data: submitData,
          },
          { isTransformResponse: false },
        );
      } else {
        // Create
        console.log('Creating with data:', submitData);
        result = await createNhomTronGoi(submitData);
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
  :deep(.ant-form-item) {
    margin-bottom: 16px;
  }
</style>
