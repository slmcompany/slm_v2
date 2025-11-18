import { BasicColumn, FormSchema } from '@/components/Table';
import { h } from 'vue';
import { Tag, Tooltip } from 'ant-design-vue';

export const columns: BasicColumn[] = [
  {
    title: 'Tên vật tư',
    dataIndex: 'ten',
    width: 250,
  },
  {
    title: 'Mã vât tư',
    dataIndex: 'ma',
    width: 250,
    customRender: ({ record }) => {
      if (!record.ma) return '-';
      return String(record.ma);
    },
  },
  {
    title: 'Nhóm vật tư',
    dataIndex: ['nhomVatTu', 'ten'],
    width: 180,
  },
  {
    title: 'Thương hiệu',
    dataIndex: ['thuongHieu', 'ten'],
    width: 150,
  },
  {
    title: 'Đơn vị',
    dataIndex: 'donVi',
    width: 100,
  },
  {
    title: 'Dữ liệu riêng',
    dataIndex: 'duLieuRieng',
    width: 180,
    customRender: ({ record }) => {
      if (!record.duLieuRieng || Object.keys(record.duLieuRieng).length === 0) {
        return h('span', { style: { color: '#999' } }, '-');
      }
      
      const duLieuList = Object.values(record.duLieuRieng);
      
      const tooltipContent = h('div', { style: { maxWidth: '300px' } }, 
        duLieuList.map((item: any, index: number) => {
          let text = item.ten;
          if (item.giaTri) text += `: ${item.giaTri}`;
          if (item.donVi) text += ` (${item.donVi})`;
          return h('div', { 
            key: index,
            style: { 
              marginBottom: index < duLieuList.length - 1 ? '8px' : '0',
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
            { default: () => `${duLieuList.length} thuộc tính` }
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
    label: 'Tên vật tư',
    component: 'Input',
    componentProps: {
      placeholder: 'Nhập tên vật tư',
    },
    colProps: { span: 6 },
  },
  {
    field: 'nhomVatTuId',
    label: 'Nhóm vật tư',
    component: 'Select',
    componentProps: {
      placeholder: 'Chọn nhóm vật tư',
      options: [],
      showSearch: true,
      filterOption: (input: string, option: any) => {
        return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
      },
    },
    colProps: { span: 6 },
  },
  {
    field: 'thuongHieuId',
    label: 'Thương hiệu',
    component: 'Select',
    componentProps: {
      placeholder: 'Chọn thương hiệu',
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
];

export const formSchema: FormSchema[] = [
  {
    field: 'ten',
    label: 'Tên vật tư',
    component: 'Input',
    required: true,
    componentProps: {
      placeholder: 'Nhập tên vật tư',
      maxlength: 400,
    },
  },
  {
    field: 'nhomVatTuId',
    label: 'Nhóm vật tư',
    component: 'Select',
    required: true,
    componentProps: {
      placeholder: 'Chọn nhóm vật tư',
      options: [],
      showSearch: true,
      filterOption: (input: string, option: any) => {
        return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
      },
    },
  },
  {
    field: 'thuongHieuId',
    label: 'Thương hiệu',
    component: 'Select',
    componentProps: {
      placeholder: 'Chọn thương hiệu',
      options: [],
      showSearch: true,
      filterOption: (input: string, option: any) => {
        return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
      },
    },
  },
  {
    field: 'donVi',
    label: 'Đơn vị',
    component: 'Input',
    componentProps: {
      placeholder: 'VD: cái, kg, m2',
      maxlength: 20,
    },
  },
  {
    field: 'sheetLink',
    label: 'Sheet Link',
    component: 'Input',
    componentProps: {
      placeholder: 'Nhập link Google Sheet',
      maxlength: 1000,
    },
  },
  {
    field: 'moTaBaoGia',
    label: 'Mô tả báo giá',
    component: 'InputTextArea',
    componentProps: {
      placeholder: 'Nhập mô tả báo giá',
      rows: 3,
    },
  },
  {
    field: 'moTaHopDong',
    label: 'Mô tả hợp đồng',
    component: 'InputTextArea',
    componentProps: {
      placeholder: 'Nhập mô tả hợp đồng',
      rows: 3,
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
  // Dữ liệu riêng - dùng slot
  {
    field: 'duLieuRieng',
    label: 'Dữ liệu riêng',
    slot: 'duLieuRieng',
    colProps: { span: 24 },
  } as FormSchema,
  // Thông tin giá - dùng slot  
  {
    field: 'dsGia',
    label: 'Thông tin giá',
    slot: 'dsGia',
    colProps: { span: 24 },
  } as FormSchema,
  // Hình ảnh - dùng slot
  {
    field: 'files',
    label: 'Hình ảnh',
    slot: 'files',
    colProps: { span: 24 },
  } as FormSchema,
];