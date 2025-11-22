import { BasicColumn, FormSchema } from '@/components/Table';
import { h } from 'vue';
import { Tag } from 'ant-design-vue';

export const columns: BasicColumn[] = [
  {
    title: 'Tên người dùng',
    dataIndex: 'hoVaTen',
    width: 250,
  },
  {
    title: 'Cơ sở',
    dataIndex: ['coSo', 'ten'],
    width: 250,
  },
  {
    title: 'Quyền người dùng',
    dataIndex: 'phanQuyen',
    width: 250,
  },
  {
    title: 'Số điện thoại',
    dataIndex: 'sdt',
    width: 250,
  },
  {
    title: 'Ngân hàng',
    dataIndex: 'nganHang',
    width: 250,
  },
  {
    title: 'Số ngân hàng',
    dataIndex: 'maNganHang',
    width: 250,
  },
  {
    title: 'Giới tính',
    dataIndex: 'gioiTinh',
    width: 250,
    customRender: ({ record }) => {
      const gioiTinh = record.gioiTinh === 1;
      return h(Tag, { color: gioiTinh ? 'success' : 'error' }, () =>
        gioiTinh ? 'Nam' : 'Nữ',
      );
    },
  },
  {
    title: 'Hoa hồng',
    dataIndex: 'phanTramHoaHong',
    width: 250,
  },
  {
    title: 'Tổng hoa hồng',
    dataIndex: 'tongHoaHong',
    width: 250,
  },
  {
    title: 'Địa chỉ',
    dataIndex: 'tongHoaHong',
    width: 250,
  },
  {
    title: 'Ngày sinh',
    dataIndex: 'sinhNhat',
    width: 180,
    customRender: ({ record }) => {
      if (!record.sinhNhat) return '-';
      return new Date(record.sinhNhat).toLocaleString('vi-VN');
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
