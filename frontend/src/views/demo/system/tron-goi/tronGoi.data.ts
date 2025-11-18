import { BasicColumn, FormSchema } from '@/components/Table';
import { h } from 'vue';
import { Tag, Tooltip } from 'ant-design-vue';

export const columns: BasicColumn[] = [
  {
    title: 'Tên trọn gói',
    dataIndex: 'ten',
    width: 250,
  },
  {
    title: 'Cơ sở',
    dataIndex: ['coSo', 'ten'],
    width: 150,
  },
  {
    title: 'Nhóm trọn gói',
    dataIndex: ['nhomTronGoi', 'ten'],
    width: 180,
  },
  {
    title: 'Loại hệ thống',
    dataIndex: 'loaiHeThong',
    width: 120,
  },
  {
    title: 'Loại pha',
    dataIndex: 'loaiPha',
    width: 100,
  },
  {
    title: 'Sản lượng',
    dataIndex: 'sanLuongToiThieu',
    width: 150,
    customRender: ({ record }) => {
      if (!record.sanLuongToiThieu && !record.sanLuongToiDa) return '-';
      return `${record.sanLuongToiThieu || 0} - ${record.sanLuongToiDa || 0} kW`;
    },
  },
  {
    title: 'Tổng giá',
    dataIndex: 'tongGia',
    width: 150,
    customRender: ({ record }) => {
      if (!record.tongGia && record.tongGia !== 0) return '-';
      try {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(record.tongGia);
      } catch (err) {
        return String(record.tongGia);
      }
    },
  },
  {
    title: 'Bán chạy',
    dataIndex: 'banChay',
    width: 100,
    customRender: ({ record }) => {
      return h(
        Tag,
        { color: record.banChay ? 'success' : 'default' },
        () => record.banChay ? 'Có' : 'Không'
      );
    },
  },
  {
    title: 'Trạng thái',
    dataIndex: 'trangThai',
    width: 120,
    customRender: ({ record }) => {
      const status = record.trangThai === 1;
      return h(
        Tag,
        { color: status ? 'success' : 'error' },
        () => status ? 'Kích hoạt' : 'Vô hiệu'
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
    label: 'Tên trọn gói',
    component: 'Input',
    componentProps: {
      placeholder: 'Nhập tên trọn gói',
    },
    colProps: { span: 6 },
  },
  {
    field: 'nhomTronGoiId',
    label: 'Nhóm trọn gói',
    component: 'Select',
    componentProps: {
      placeholder: 'Chọn nhóm trọn gói',
      options: [],
      showSearch: true,
      filterOption: (input: string, option: any) => {
        return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
      },
    },
    colProps: { span: 6 },
  },
  {
    field: 'loaiHeThong',
    label: 'Loại hệ thống',
    component: 'Select',
    componentProps: {
      placeholder: 'Chọn loại hệ thống',
      allowClear: true,
      options: [
        { label: 'On-Grid', value: 'On-Grid' },
        { label: 'Hy-Brid', value: 'Hy-Brid' },
      ],
    },
    colProps: { span: 6 },
  },
  {
    field: 'loaiPha',
    label: 'Loại pha',
    component: 'Select',
    componentProps: {
      placeholder: 'Chọn loại pha',
      allowClear: true,
      options: [
        { label: '1 pha', value: '1 pha' },
        { label: '3 pha', value: '3 pha' },
      ],
    },
    colProps: { span: 6 },
  },
];

export const formSchema: FormSchema[] = [
  {
    field: 'ten',
    label: 'Tên trọn gói',
    component: 'Input',
    required: true,
    componentProps: {
      placeholder: 'Nhập tên trọn gói',
      maxlength: 400,
    },
  },
  {
    field: 'nhomTronGoiId',
    label: 'Nhóm trọn gói',
    component: 'Select',
    required: true,
    componentProps: {
      placeholder: 'Chọn nhóm trọn gói',
      options: [],
      showSearch: true,
      filterOption: (input: string, option: any) => {
        return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
      },
    },
  },
  {
    field: 'loaiHeThong',
    label: 'Loại hệ thống',
    component: 'Select',
    required: true,
    componentProps: {
      placeholder: 'Chọn loại hệ thống',
      options: [
        { label: 'On-Grid', value: 'On-Grid' },
        { label: 'Hy-Brid', value: 'Hy-Brid' },
      ],
    },
  },
  {
    field: 'loaiPha',
    label: 'Loại pha',
    component: 'Select',
    required: true,
    componentProps: {
      placeholder: 'Chọn loại pha',
      options: [
        { label: '1 pha', value: '1 pha' },
        { label: '3 pha', value: '3 pha' },
      ],
    },
  },
  {
    field: 'moTa',
    label: 'Mô tả',
    component: 'InputTextArea',
    componentProps: {
      placeholder: 'Nhập mô tả',
      rows: 3,
    },
  },
  
  {
    field: 'banChay',
    label: 'Bán chạy',
    component: 'RadioButtonGroup',
    defaultValue: false,
    componentProps: {
      options: [
        { label: 'Có', value: true },
        { label: 'Không', value: false },
      ],
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
    required: true,
  },
  {
    field: 'tronGoiCoSos',
    label: 'Thông tin cơ sở',
    slot: 'tronGoiCoSos',
    colProps: { span: 24 },
  } as FormSchema,
  {
    field: 'vatTuTronGois',
    label: 'Vật tư trong gói',
    slot: 'vatTuTronGois',
    colProps: { span: 24 },
  } as FormSchema,
  {
    field: 'tongGia',
    label: 'Tổng giá',
    component: 'InputNumber',
    required: true,
    componentProps: {
      placeholder: 'Nhập tổng giá',
      min: 0,
      formatter: (value: any) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ','),
      parser: (value: any) => value.replace(/\$\s?|(,*)/g, ''),
      style: { width: '100%' },
    },
  },
  {
    field: 'file',
    label: 'Hình ảnh',
    component: 'Input',
    slot: 'file',
    colProps: { span: 24 },
  } as FormSchema,
];