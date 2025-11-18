import { BasicColumn, FormSchema } from '@/components/Table';
import { h } from 'vue';
import { Tag } from 'ant-design-vue';

export const columns: BasicColumn[] = [
  {
    title: 'Ngành hàng',
    dataIndex: ['nganhHang', 'ten'],
    width: 180,
  },
  {
    title: 'Tên',
    dataIndex: 'ten',
    width: 250,
  },
  {
    title: 'Thương hiệu tấm pin',
    dataIndex: ['thuongHieuTamPin', 'ten'],
    width: 200,
  },
  {
    title: 'Thương hiệu inverter',
    dataIndex: ['thuongHieuInverter', 'ten'],
    width: 200,
  },
  {
    title: 'Thương hiệu pin lưu trữ',
    dataIndex: ['thuongHieuPinLuuTru', 'ten'],
    width: 200,
  },
  {
    title: 'Trạng thái',
    dataIndex: 'trangThai',
    width: 120,
    customRender: ({ record }) => {
      const status = record.trangThai === 1;
      return h(Tag, { color: status ? 'success' : 'error' }, () =>
        status ? 'Kích hoạt' : 'Vô hiệu',
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
    field: 'ten',
    label: 'Tên nhóm trọn gói',
    component: 'Input',
    componentProps: {
      placeholder: 'Nhập tên nhóm trọn gói',
    },
    colProps: { span: 8 },
  },
  {
    field: 'nganhHangId',
    label: 'Ngành hàng',
    component: 'Select',
    componentProps: {
      placeholder: 'Chọn ngành hàng',
      options: [], // Sẽ được cập nhật từ loadOptions
      showSearch: true,
      allowClear: true,
      filterOption: (input: string, option: any) => {
        return option?.label?.toLowerCase().indexOf(input.toLowerCase()) >= 0;
      },
    },
    colProps: { span: 8 },
  },
];

export const formSchema: FormSchema[] = [
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
        return option?.label?.toLowerCase().indexOf(input.toLowerCase()) >= 0;
      },
    },
  },
  {
    field: 'ten',
    label: 'Tên nhóm trọn gói',
    component: 'Input',
    required: true,
    componentProps: {
      placeholder: 'Nhập tên nhóm trọn gói',
    },
  },
  {
    field: 'thuongHieuTamPinId',
    label: 'Thương hiệu tấm pin',
    component: 'Select',
    required: true,
    componentProps: {
      placeholder: 'Chọn thương hiệu tấm pin',
      options: [],
      showSearch: true,
      filterOption: (input: string, option: any) => {
        return option?.label?.toLowerCase().indexOf(input.toLowerCase()) >= 0;
      },
    },
  },
  {
    field: 'thuongHieuInverterId',
    label: 'Thương hiệu inverter',
    component: 'Select',
    required: true,
    componentProps: {
      placeholder: 'Chọn thương hiệu inverter',
      options: [],
      showSearch: true,
      filterOption: (input: string, option: any) => {
        return option?.label?.toLowerCase().indexOf(input.toLowerCase()) >= 0;
      },
    },
  },
  {
    field: 'thuongHieuPinLuuTruId',
    label: 'Thương hiệu pin lưu trữ',
    component: 'Select',
    required: false,
    componentProps: {
      placeholder: 'Chọn thương hiệu pin lưu trữ',
      options: [],
      showSearch: true,
      filterOption: (input: string, option: any) => {
        return option?.label?.toLowerCase().indexOf(input.toLowerCase()) >= 0;
      },
    },
  },
  {
    field: 'trangThai',
    label: 'Trạng thái',
    component: 'RadioButtonGroup',
    defaultValue: 1,
    componentProps: {
      options: [
        { label: 'Kích hoạt', value: 1 },
        { label: 'Vô hiệu', value: 0 },
      ],
    },
  },
];