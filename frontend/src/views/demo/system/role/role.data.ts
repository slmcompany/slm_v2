import { BasicColumn, FormSchema } from '@/components/Table';
import { h } from 'vue';
import { Switch } from 'ant-design-vue';
import { setRoleStatus } from '@/api/demo/system';
import { useMessage } from '@/hooks/web/useMessage';

type CheckedType = boolean | string | number;

export const columns: BasicColumn[] = [
  {
    title: 'Tên vai trò',
    dataIndex: 'roleName',
    width: 200,
  },
  {
    title: 'Giá trị vai trò',
    dataIndex: 'roleValue',
    width: 180,
  },
  {
    title: 'Thứ tự',
    dataIndex: 'orderNo',
    width: 50,
  },
  {
    title: 'Trạng thái',
    dataIndex: 'status',
    width: 120,
    customRender: ({ record }) => {
      if (!Reflect.has(record, 'pendingStatus')) {
        record.pendingStatus = false;
      }
      return h(Switch, {
        checked: record.status === '1',
        checkedChildren: 'Kích hoạt',
        unCheckedChildren: 'Vô hiệu',
        loading: record.pendingStatus,
        onChange(checked: CheckedType) {
          record.pendingStatus = true;
          const newStatus = checked ? '1' : '0';
          const { createMessage } = useMessage();
          setRoleStatus(record.id, newStatus)
            .then(() => {
              record.status = newStatus;
              createMessage.success('Cập nhật trạng thái vai trò thành công');
            })
            .catch(() => {
              createMessage.error('Cập nhật trạng thái vai trò thất bại');
            })
            .finally(() => {
              record.pendingStatus = false;
            });
        },
      });
    },
  },
  {
    title: 'Ngày tạo',
    dataIndex: 'createTime',
    width: 180,
  },
  {
    title: 'Ghi chú',
    dataIndex: 'remark',
  },
];

export const searchFormSchema: FormSchema[] = [
  {
    field: 'roleName',
    label: 'Tên vai trò',
    component: 'Input',
    colProps: { span: 8 },
  },
  {
    field: 'status',
    label: 'Trạng thái',
    component: 'Select',
    componentProps: {
      options: [
        { label: 'Kích hoạt', value: '1' },
        { label: 'Vô hiệu', value: '0' },
      ],
    },
    colProps: { span: 8 },
  },
];

export const formSchema: FormSchema[] = [
  {
    field: 'roleName',
    label: 'Tên vai trò',
    required: true,
    component: 'Input',
  },
  {
    field: 'roleValue',
    label: 'Giá trị vai trò',
    required: true,
    component: 'Input',
  },
  {
    field: 'status',
    label: 'Trạng thái',
    component: 'RadioButtonGroup',
    defaultValue: '0',
    componentProps: {
      options: [
        { label: 'Kích hoạt', value: '1' },
        { label: 'Vô hiệu', value: '0' },
      ],
    },
  },
  {
    label: 'Ghi chú',
    field: 'remark',
    component: 'InputTextArea',
  },
  {
    label: 'Phân quyền menu',
    field: 'menu',
    slot: 'menu',
  },
];
