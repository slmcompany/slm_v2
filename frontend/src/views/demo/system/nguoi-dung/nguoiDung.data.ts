import { BasicColumn, FormSchema } from '@/components/Table';
import { h } from 'vue';
import { Tag } from 'ant-design-vue';

export const columns: BasicColumn[] = [
  {
    title: 'Họ và tên',
    dataIndex: 'hoVaTen',
    width: 150,
  },
  {
    title: 'Email',
    dataIndex: 'email',
    width: 200,
  },
  {
    title: 'Số điện thoại',
    dataIndex: 'sdt',
    width: 120,
  },
  {
    title: 'Giới tính',
    dataIndex: 'gioiTinh',
    width: 80,
    customRender: ({ record }) => {
      return record.gioiTinh === true ? 'Nam' : 'Nữ';
    },
  },
  {
    title: 'Phân quyền',
    dataIndex: 'phanQuyen',
    width: 120,
    customRender: ({ record }) => {
      const phanQuyen = record.phanQuyen;
      let color = 'blue';
      let text = 'Khách hàng';
      
      if (phanQuyen === 'ADMIN') {
        color = 'red';
        text = 'Quản trị';
      } else if (phanQuyen === 'SALE') {
        color = 'orange';
        text = 'Bán hàng';
      } else if (phanQuyen === 'AGENT') {
        color = 'green';
        text = 'Đại lý';
      }
      
      return h(Tag, { color }, () => text);
    },
  },
  {
    title: 'Cơ sở',
    dataIndex: ['coSo', 'ten'],
    width: 150,
    customRender: ({ record }) => {
      return record.coSo?.ten || '-';
    },
  },
  {
    title: 'Hoa hồng',
    dataIndex: 'phanTramHoaHong',
    width: 100,
    align: 'right',
    customRender: ({ record }) => {
      return record.phanTramHoaHong ? `${record.phanTramHoaHong}%` : '-';
    },
  },
  {
    title: 'Tổng hoa hồng',
    dataIndex: 'tongHoaHong',
    width: 130,
    align: 'right',
    customRender: ({ record }) => {
      if (!record.tongHoaHong) return '0 đ';
      return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
      }).format(record.tongHoaHong);
    },
  },
  {
    title: 'Trạng thái',
    dataIndex: 'trangThai',
    width: 100,
    customRender: ({ record }) => {
      const trangThai = record.trangThai;
      let color = 'success';
      let text = 'Hoạt động';
      
      if (trangThai === 0) {
        color = 'error';
        text = 'Ngừng hoạt động';
      }
      
      return h(Tag, { color }, () => text);
    },
  },
  {
    title: 'Ngày tạo',
    dataIndex: 'taoLuc',
    width: 150,
    customRender: ({ record }) => {
      return record.taoLuc 
        ? new Date(record.taoLuc).toLocaleString('vi-VN')
        : '-';
    },
  },
];

export const searchFormSchema: FormSchema[] = [
  {
    field: 'hoVaTen',
    label: 'Họ và tên',
    component: 'Input',
    colProps: { span: 6 },
    componentProps: {
      placeholder: 'Nhập họ và tên',
    },
  },
  {
    field: 'email',
    label: 'Email',
    component: 'Input',
    colProps: { span: 6 },
    componentProps: {
      placeholder: 'Nhập email',
    },
  },
  {
    field: 'sdt',
    label: 'Số điện thoại',
    component: 'Input',
    colProps: { span: 6 },
    componentProps: {
      placeholder: 'Nhập số điện thoại',
    },
  },
  {
    field: 'phanQuyen',
    label: 'Phân quyền',
    component: 'Select',
    colProps: { span: 6 },
    componentProps: {
      placeholder: 'Chọn phân quyền',
      options: [
        { label: 'Tất cả', value: '' },
        { label: 'Quản trị', value: 'ADMIN' },
        { label: 'Người bán hàng', value: 'SALE' },
        { label: 'Đại lý', value: 'AGENT' },
        { label: 'Khách hàng', value: 'CUSTOMER' },
      ],
    },
  },
  {
    field: 'trangThai',
    label: 'Trạng thái',
    component: 'Select',
    colProps: { span: 6 },
    componentProps: {
      placeholder: 'Chọn trạng thái',
      options: [
        { label: 'Tất cả', value: '' },
        { label: 'Hoạt động', value: 1 },
        { label: 'Ngừng hoạt động', value: 0 },
      ],
    },
  },
];

// Schema cho form tạo tài khoản
export const formSchema: FormSchema[] = [
  {
    field: 'hoVaTen',
    label: 'Họ và tên',
    component: 'Input',
    required: true,
    componentProps: {
      placeholder: 'Nhập họ và tên',
    },
  },
  {
    field: 'email',
    label: 'Email',
    component: 'Input',
    required: true,
    componentProps: {
      placeholder: 'Nhập email',
    },
    rules: [
      {
        type: 'email',
        message: 'Email không hợp lệ',
      },
    ],
  },
  {
    field: 'sdt',
    label: 'Số điện thoại',
    component: 'Input',
    required: true,
    componentProps: {
      placeholder: 'Nhập số điện thoại',
    },
    rules: [
      {
        pattern: /^[0-9]{10,11}$/,
        message: 'Số điện thoại phải từ 10-11 chữ số',
      },
    ],
  },
  {
    field: 'matKhau',
    label: 'Mật khẩu',
    component: 'InputPassword',
    required: true,
    componentProps: {
      placeholder: 'Nhập mật khẩu',
    },
    rules: [
      {
        min: 6,
        message: 'Mật khẩu phải có ít nhất 6 ký tự',
      },
    ],
  },
  {
    field: 'maCoSo',
    label: 'Mã cơ sở',
    component: 'Select',
    required: true,
    componentProps: {
      placeholder: 'Chọn cơ sở',
      options: [
        { label: 'Hà Nội', value: 'HN' },
        { label: 'Hồ Chí Minh', value: 'HCM' },
      ],
    },
  },
];