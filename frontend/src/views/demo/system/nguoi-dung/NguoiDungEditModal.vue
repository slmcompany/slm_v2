<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    title="Chỉnh sửa người dùng"
    :width="600"
    @ok="handleSubmit"
  >
    <BasicForm @register="registerForm" />
  </BasicModal>
</template>

<script lang="ts" setup>
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { BasicForm, useForm } from '@/components/Form';
  import { editFormSchema } from './nguoiDung.data';
  import { updateNguoiDung } from './nguoiDung';
  import type { NguoiDungDto } from './nguoiDung';
  import { message } from 'ant-design-vue';

  defineOptions({ name: 'NguoiDungEditModal' });

  const emit = defineEmits(['success', 'register']);

  const [registerForm, { resetFields, setFieldsValue, validate }] = useForm({
    labelWidth: 120,
    schemas: editFormSchema,
    showActionButtonGroup: false,
    baseColProps: { span: 24 },
  });

  const [registerModal, { setModalProps, closeModal }] = useModalInner(
    async (record: NguoiDungDto) => {
      await resetFields();
      setModalProps({ confirmLoading: false });

      await setFieldsValue({
        id: record.id,
        hoVaTen: record.hoVaTen,
        email: record.email,
        sdt: record.sdt,
        phanQuyen: record.phanQuyen,
        sinhNhat: record.sinhNhat ? new Date(record.sinhNhat) : undefined,
        phanTramHoaHong: record.phanTramHoaHong,
        diaChi: record.diaChi,
      });
    },
  );

  async function handleSubmit() {
    try {
      const values = await validate();
      setModalProps({ confirmLoading: true });

      const result = await updateNguoiDung({
        id: values.id,
        hoVaTen: values.hoVaTen,
        email: values.email,
        sdt: values.sdt,
        phanQuyen: values.phanQuyen,
        sinhNhat: values.sinhNhat ? new Date(values.sinhNhat).toISOString() : undefined,
        phanTramHoaHong: values.phanTramHoaHong ? Number(values.phanTramHoaHong) : undefined,
        diaChi: values.diaChi,
      });

      if (result.status === 200 || result.status === 201) {
        message.success('Cập nhật thành công');
        closeModal();
        emit('success');
      } else {
        message.error(result.message || 'Có lỗi xảy ra');
      }
    } catch (error: any) {
      console.error('Update error:', error);
      if (error?.errorFields) {
        message.error('Vui lòng điền đầy đủ thông tin bắt buộc');
      } else {
        message.error(error?.message || 'Có lỗi xảy ra khi cập nhật');
      }
    } finally {
      setModalProps({ confirmLoading: false });
    }
  }
</script>

<style lang="less" scoped></style>