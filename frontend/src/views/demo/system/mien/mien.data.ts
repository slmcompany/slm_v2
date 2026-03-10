import { BasicColumn, FormSchema } from '@/components/Table';
import { h } from 'vue';
import { Tag } from 'ant-design-vue';

export const columns: BasicColumn[] = [
  {
    title: 'Tên miền',
    dataIndex: 'tenMien',
    width: 250,
  },
  {
    title: 'Cơ sở',
    dataIndex: ['coSo', 'ten'],
    width: 180,
    customRender: ({ record }) => record.coSo?.ten || '-',
  },
  {
    title: 'Mã cơ sở',
    dataIndex: ['coSo', 'ma'],
    width: 120,
    customRender: ({ record }) => record.coSo?.ma || '-',
  },
  {
    title: 'Hình ảnh',
    dataIndex: 'tepTin',
    width: 100,
    customRender: ({ record }) => {
      if (!record.tepTin?.duongDan) {
        return h('span', { style: { color: '#999' } }, 'Chưa có');
      }
      return h('img', {
        src: record.tepTin.duongDan,
        style: {
          width: '40px',
          height: '40px',
          objectFit: 'cover',
          borderRadius: '4px',
          border: '1px solid #f0f0f0',
        },
      });
    },
  },
  {
    title: 'Thông tin liên hệ',
    dataIndex: 'thongTinTenMiens',
    width: 180,
    customRender: ({ record }) => {
      const list: any[] = record.thongTinTenMiens || [];
      if (!list.length) return h('span', { style: { color: '#999' } }, '-');
      return h(
        Tag,
        { color: 'blue', style: { cursor: 'pointer' } },
        { default: () => `${list.length} liên hệ` },
      );
    },
  },
  {
    title: 'Trạng thái',
    dataIndex: 'trangThai',
    width: 120,
    customRender: ({ record }) => {
      const active = record.trangThai === 1;
      return h(Tag, { color: active ? 'success' : 'error' }, () =>
        active ? 'Kích hoạt' : 'Vô hiệu',
      );
    },
  },
  {
    title: 'Ngày tạo',
    dataIndex: 'taoLuc',
    width: 180,
    customRender: ({ record }) => {
      if (!record.taoLuc) return '-';
      return new Date(record.taoLuc).toLocaleString('vi-VN');
    },
  },
];

export const searchFormSchema: FormSchema[] = [
  {
    field: 'tenMien',
    label: 'Tên miền',
    component: 'Input',
    componentProps: { placeholder: 'Nhập tên miền' },
    colProps: { span: 6 },
  },
  {
    field: 'coSoId',
    label: 'Cơ sở',
    component: 'Select',
    componentProps: {
      placeholder: 'Chọn cơ sở',
      options: [],
      allowClear: true,
      showSearch: true,
      filterOption: (input: string, option: any) =>
        option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0,
    },
    colProps: { span: 6 },
  },
  {
    field: 'trangThai',
    label: 'Trạng thái',
    component: 'Select',
    componentProps: {
      placeholder: 'Chọn trạng thái',
      allowClear: true,
      options: [
        { label: 'Kích hoạt', value: 1 },
        { label: 'Vô hiệu', value: 0 },
      ],
    },
    colProps: { span: 6 },
  },
];

export const formSchema: FormSchema[] = [
  {
    field: 'tenMien',
    label: 'Tên miền',
    component: 'Input',
    required: true,
    componentProps: {
      placeholder: 'Nhập tên miền (VD: example.com)',
      maxlength: 100,
    },
  },
  {
    field: 'coSoId',
    label: 'Cơ sở',
    component: 'Select',
    componentProps: {
      placeholder: 'Chọn cơ sở',
      options: [],
      allowClear: true,
      showSearch: true,
      filterOption: (input: string, option: any) =>
        option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0,
    },
  },
  {
    field: 'trangThai',
    label: 'Trạng thái',
    component: 'RadioButtonGroup',
    defaultValue: 1,
    required: true,
    componentProps: {
      options: [
        { label: 'Kích hoạt', value: 1 },
        { label: 'Vô hiệu', value: 0 },
      ],
    },
  },
  {
    field: 'file',
    label: 'Hình ảnh / Logo',
    slot: 'file',
    colProps: { span: 24 },
  } as FormSchema,
  {
    field: 'thongTinMiens',
    label: 'Thông tin tên miền',
    slot: 'thongTinMiens',
    colProps: { span: 24 },
  } as FormSchema,
];