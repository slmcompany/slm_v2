<template>
  <BasicModal
    v-bind="$attrs"
    @register="registerModal"
    title="Tạo tài khoản mới"
    :width="600"
    @ok="handleSubmit"
  >
    <BasicForm @register="registerForm" />
  </BasicModal>
</template>

<script lang="ts" setup>
  import { ref } from 'vue';
  import { BasicModal, useModalInner } from '@/components/Modal';
  import { BasicForm, useForm } from '@/components/Form';
  import { formSchema } from './nguoiDung.data';
  import { createAccount } from './nguoiDung';
  import type { CreateAccountRequest } from './nguoiDung';
  import { message } from 'ant-design-vue';

  defineOptions({ name: 'NguoiDungModal' });

  const emit = defineEmits(['success', 'register']);

  const [registerForm, { resetFields, validate }] = useForm({
    labelWidth: 120,
    schemas: formSchema,
    showActionButtonGroup: false,
    baseColProps: { span: 24 },
  });

  const [registerModal, { setModalProps, closeModal }] = useModalInner(async () => {
    resetFields();
    setModalProps({ confirmLoading: false });
  });

  async function handleSubmit() {
    try {
      const values = await validate();
      setModalProps({ confirmLoading: true });

      const requestData: CreateAccountRequest = {
        hoVaTen: values.hoVaTen,
        email: values.email,
        sdt: values.sdt,
        matKhau: values.matKhau,
        maCoSo: values.maCoSo,
      };

      const result = await createAccount(requestData);

      if (result.status === 200 || result.status === 201) {
        message.success('Tạo tài khoản thành công');
        closeModal();
        emit('success');
      } else {
        message.error(result.message || 'Có lỗi xảy ra');
      }
    } catch (error: any) {
      console.error('Create account error:', error);
      if (error?.errorFields) {
        message.error('Vui lòng điền đầy đủ thông tin bắt buộc');
      } else {
        message.error(error?.message || 'Có lỗi xảy ra khi tạo tài khoản');
      }
    } finally {
      setModalProps({ confirmLoading: false });
    }
  }
</script>

<style lang="less" scoped></style>
