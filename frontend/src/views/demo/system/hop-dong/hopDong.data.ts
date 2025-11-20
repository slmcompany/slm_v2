import { BasicColumn, FormSchema } from '@/components/Table';
import { h } from 'vue';
import { Tag } from 'ant-design-vue';

export const columns: BasicColumn[] = [
  {
    title: 'Tên hợp đồng',
    dataIndex: 'ten',
    width: 250,
  },
  {
    title: 'Cơ sở',
    dataIndex: ['coSo', 'ten'],
    width: 150,
  },
  {
    title: 'Ngành hàng',
    dataIndex: ['nghanhHang', 'ten'],
    width: 150,
  },
  {
    title: 'Khách hàng',
    dataIndex: ['khachHang', 'hoVaTen'],
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
    title: 'Tổng giá',
    dataIndex: 'tongGia',
    width: 150,
    customRender: ({ record }) => {
      if (!record.tongGia && record.tongGia !== 0) return '-';
      try {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(record.tongGia);
      } catch {
        return String(record.tongGia);
      }
    },
  },
  {
    title: 'Trạng thái',
    dataIndex: 'trangThai',
    width: 120,
    customRender: ({ record }) => {
      const status = record.trangThai === 1;
      return h(Tag, { color: status ? 'success' : 'error' }, () => status ? 'Kích hoạt' : 'Vô hiệu');
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
    label: 'Tên hợp đồng',
    component: 'Input',
    componentProps: { placeholder: 'Nhập tên hợp đồng' },
    colProps: { span: 6 },
  },
  {
    field: 'coSoId',
    label: 'Cơ sở',
    component: 'Select',
    componentProps: {
      placeholder: 'Chọn cơ sở',
      options: [],
      showSearch: true,
      filterOption: (input: string, option: any) => 
        option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0,
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
      filterOption: (input: string, option: any) => 
        option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0,
    },
    colProps: { span: 6 },
  },
];

export const vatTuColumns = [
  { title: 'Tên vật tư', key: 'ten', dataIndex: ['vatTu', 'ten'] },
  { title: 'Số lượng', key: 'soLuong', dataIndex: 'soLuong', width: 100 },
  { title: 'Giá', key: 'giaHienThi', dataIndex: 'giaHienThi', width: 150 },
  { title: 'GM (%)', key: 'gm', dataIndex: 'gm', width: 80 },
  { title: 'Bảo hành (tháng)', key: 'thoiGianBaoHanh', dataIndex: 'thoiGianBaoHanh', width: 120 },
  { 
    title: 'Được BH', 
    key: 'duocBaoHanh', 
    dataIndex: 'duocBaoHanh', 
    width: 100 
  },
  { title: 'Mô tả', key: 'moTa', dataIndex: 'moTa' },
];