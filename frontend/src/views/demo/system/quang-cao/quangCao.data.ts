import { BasicColumn, FormSchema } from '@/components/Table';
import { h } from 'vue';
import { Tag, Image } from 'ant-design-vue';

export const columns: BasicColumn[] = [
  {
    title: 'Ảnh quảng cáo',
    dataIndex: ['tepTin', 'duongDan'],
    width: 150,
    customRender: ({ record }) => {
      const duongDan = record.tepTin?.duongDan;
      return h(Image, {
        src: duongDan || 'https://cdn-media.sforum.vn/storage/app/media/anh-dep-16.jpg',
        width: 100,
        height: 100,
        style: { objectFit: 'cover' },
      });
    },
  },
  {
    title: 'Tiêu đề',
    dataIndex: 'tieuDe',
    width: 250,
  },
  {
    title: 'Vị trí',
    dataIndex: 'viTri',
    width: 180,
  },
  {
    title: 'Ngành hàng',
    dataIndex: ['nganhHang', 'ten'],
    width: 180,
  },
  {
    title: 'Hoạt động',
    dataIndex: 'hoatDong',
    width: 120,
    customRender: ({ record }) => {
      const hoatDong = record.hoatDong === 1 || record.hoatDong === true;
      return h(
        Tag,
        { color: hoatDong ? 'success' : 'error' },
        () => hoatDong ? 'Kích hoạt' : 'Vô hiệu'
      );
    },
  },
  {
    title: 'Trạng thái',
    dataIndex: 'trangThai',
    width: 120,
    customRender: ({ record }) => {
      const trangThai = record.trangThai === 1;
      return h(
        Tag,
        { color: trangThai ? 'success' : 'error' },
        () => trangThai ? 'Hoạt động' : 'Đã xóa'
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
    field: 'tieuDe',
    label: 'Tiêu đề',
    component: 'Input',
    componentProps: {
      placeholder: 'Nhập tiêu đề quảng cáo',
    },
    colProps: { span: 8 },
  },
  {
    field: 'viTri',
    label: 'Vị trí',
    component: 'Input',
    componentProps: {
      placeholder: 'Nhập vị trí',
    },
    colProps: { span: 8 },
  },
];

export const formSchema: FormSchema[] = [
  {
    field: 'tieuDe',
    label: 'Tiêu đề',
    component: 'Input',
    required: true,
    componentProps: {
      placeholder: 'Nhập tiêu đề quảng cáo',
      maxlength: 200,
    },
  },
  {
    field: 'nganhHangId',
    label: 'Ngành hàng',
    component: 'Select',
    required: true,
    componentProps: {
      placeholder: 'Chọn ngành hàng',
      options: [],
      showSearch: true,
      filterOption: (input: string, option: any) => {
        return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
      },
    },
  },
  {
    field: 'viTri',
    label: 'Vị trí',
    component: 'Input',
    required: true,
    componentProps: {
      placeholder: 'Chọn vị trí',
      maxlength: 200,
    },
  },
  {
    field: 'hoatDong',
    label: 'Hoạt động',
    component: 'RadioButtonGroup',
    defaultValue: true,
    componentProps: {
      options: [
        { label: 'Kích hoạt', value: true },
        { label: 'Vô hiệu', value: false },
      ],
    },
    ifShow: ({ values }) => {
      // Chỉ show khi đang update
      return !!values.id;
    },
  },
  {
    field: 'trangThai',
    label: 'Trạng thái',
    component: 'RadioButtonGroup',
    defaultValue: 1,
    componentProps: {
      options: [
        { label: 'Hoạt động', value: 1 },
        { label: 'Đã xóa', value: 0 },
      ],
    },
    ifShow: ({ values }) => {
      // Chỉ show khi đang update
      return !!values.id;
    },
  },
  {
    field: 'anhQuangCao',
    label: 'Ảnh quảng cáo',
    slot: 'anhQuangCao',
    required: false,
    colProps: { span: 24 },
  } as FormSchema,
];