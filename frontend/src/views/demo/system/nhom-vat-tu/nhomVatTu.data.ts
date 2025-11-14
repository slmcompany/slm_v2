import { BasicColumn, FormSchema } from '@/components/Table';
import { h } from 'vue';
import { Tag, Tooltip } from 'ant-design-vue';

export const columns: BasicColumn[] = [
  {
    title: 'Mã nhóm',
    dataIndex: 'ma',
    width: 120,
  },
  {
    title: 'Tên nhóm vật tư',
    dataIndex: 'ten',
    width: 250,
  },
  {
    title: 'Ngành hàng',
    dataIndex: ['nghanhHang', 'ten'],
    width: 180,
  },
  {
    title: 'GM (%)',
    dataIndex: 'gm',
    width: 100,
    customRender: ({ record }) => {
      return record.gm ? `${record.gm}%` : '-';
    },
  },
  {
    title: 'Vật tư chính',
    dataIndex: 'vatTuChinh',
    width: 120,
    customRender: ({ record }) => {
      return h(
        Tag,
        { color: record.vatTuChinh ? 'green' : 'default' },
        () => record.vatTuChinh ? 'Có' : 'Không'
      );
    },
  },
  {
    title: 'Thuộc tính riêng',
    dataIndex: 'thuocTinhRieng',
    width: 200,
    customRender: ({ record }) => {
      if (!record.thuocTinhRieng || Object.keys(record.thuocTinhRieng).length === 0) {
        return h('span', { style: { color: '#999' } }, '-');
      }
      
      const thuocTinhList = Object.values(record.thuocTinhRieng);
      
      // Tạo tooltip content với HTML formatting
      const tooltipContent = h('div', { style: { maxWidth: '300px' } }, 
        thuocTinhList.map((item: any, index: number) => {
          let text = item.ten;
          if (item.giaTri) text += `: ${item.giaTri}`;
          if (item.donVi) text += ` (${item.donVi})`;
          return h('div', { 
            key: index,
            style: { 
              marginBottom: index < thuocTinhList.length - 1 ? '8px' : '0',
              lineHeight: '1.5'
            } 
          }, text);
        })
      );
      
      return h(
        Tooltip,
        { 
          placement: 'topLeft',
          overlayInnerStyle: { maxWidth: '350px' }
        },
        {
          title: () => tooltipContent,
          default: () => h(
            Tag, 
            { color: 'blue', style: { cursor: 'pointer' } }, 
            { default: () => `${thuocTinhList.length} thuộc tính` }
          )
        }
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
    label: 'Tên nhóm vật tư',
    component: 'Input',
    componentProps: {
      placeholder: 'Nhập tên nhóm vật tư',
    },
    colProps: { span: 6 },
  },
  {
    field: 'ma',
    label: 'Mã nhóm',
    component: 'Input',
    componentProps: {
      placeholder: 'Nhập mã nhóm',
    },
    colProps: { span: 6 },
  },
  {
    field: 'nghanhHangId',
    label: 'Ngành hàng',
    component: 'Select',
    componentProps: {
      placeholder: 'Chọn ngành hàng',
      options: [],
      showSearch: true,
      filterOption: (input: string, option: any) => {
        return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
      },
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
  {
    field: 'vatTuChinh',
    label: 'Vật tư chính',
    component: 'Select',
    componentProps: {
      placeholder: 'Chọn loại',
      allowClear: true,
      options: [
        { label: 'Có', value: 1 },
        { label: 'Không', value: 0 },
      ],
    },
    colProps: { span: 6 },
  },
  {
    field: 'gmMin',
    label: 'GM tối thiểu (%)',
    component: 'InputNumber',
    componentProps: {
      placeholder: 'Nhập GM tối thiểu',
      style: { width: '100%' },
      min: 0,
      max: 100,
    },
    colProps: { span: 6 },
  },
  {
    field: 'gmMax',
    label: 'GM tối đa (%)',
    component: 'InputNumber',
    componentProps: {
      placeholder: 'Nhập GM tối đa',
      style: { width: '100%' },
      min: 0,
      max: 100,
    },
    colProps: { span: 6 },
  },
];

export const formSchema: FormSchema[] = [
  {
    field: 'ma',
    label: 'Mã nhóm',
    component: 'Input',
    required: true,
    componentProps: {
      placeholder: 'Nhập mã nhóm vật tư',
      maxlength: 50,
    },
  },
  {
    field: 'ten',
    label: 'Tên nhóm vật tư',
    component: 'Input',
    required: true,
    componentProps: {
      placeholder: 'Nhập tên nhóm vật tư',
      maxlength: 400,
    },
  },
  {
    field: 'nghanhHangId',
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
    field: 'gm',
    label: 'GM (%)',
    component: 'InputNumber',
    componentProps: {
      placeholder: 'Nhập tỷ lệ GM',
      style: { width: '100%' },
      min: 0,
      max: 100,
      step: 0.1,
      precision: 2,
    },
  },
  {
    field: 'vatTuChinh',
    label: 'Vật tư chính',
    component: 'RadioButtonGroup',
    defaultValue: 0,
    componentProps: {
      options: [
        { label: 'Có', value: 1 },
        { label: 'Không', value: 0 },
      ],
    },
    required: true,
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
    field: 'thuocTinhRieng',
    label: 'Thuộc tính riêng',
    component: 'Input',
    slot: 'thuocTinhRieng',
    colProps: { span: 24 },
  } as FormSchema,
];