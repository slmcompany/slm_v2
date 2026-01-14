// src/views/demo/system/bai-viet/baiViet.data.ts

import { BasicColumn, FormSchema } from '@/components/Table';
import { h } from 'vue';
import { Tag } from 'ant-design-vue';

export const columns: BasicColumn[] = [
  {
    title: 'Tiêu đề',
    dataIndex: 'tieuDe',
    width: 300,
  },
  {
    title: 'Loại bài viết',
    dataIndex: 'loaiBaiViet',
    width: 180,
    customRender: ({ record }) => {
      const loaiMap = {
        MEGA_STORY: { text: 'MEGA STORY', color: 'blue' },
        HOI_DAP: { text: 'HỎI ĐÁP', color: 'green' },
        HUONG_DAN_BAO_HANH: { text: 'HƯỚNG DẪN BẢO HÀNH', color: 'orange' },
      };
      const loai = loaiMap[record.loaiBaiViet] || { text: record.loaiBaiViet, color: 'default' };
      return h(Tag, { color: loai.color }, () => loai.text);
    },
  },
  {
    title: 'Ảnh bìa',
    dataIndex: ['anhBia', 'duongDan'],
    width: 100,
    customRender: ({ record }) => {
      if (!record.anhBia?.duongDan) {
        return h('span', { style: { color: '#999' } }, '-');
      }
      return h('img', {
        src: record.anhBia.duongDan,
        style: {
          width: '60px',
          height: '40px',
          objectFit: 'cover',
          borderRadius: '4px',
        },
      });
    },
  },
  {
    title: 'Người tạo',
    dataIndex: ['taoBoi', 'ten'],
    width: 150,
    customRender: ({ record }) => {
      if (!record.taoBoi?.ten) {
        return h('span', { style: { color: '#999' } }, '-');
      }
      return record.taoBoi.hoVaTen;
    },
  },
  {
    title: 'Trạng thái',
    dataIndex: 'trangThai',
    width: 120,
    customRender: ({ record }) => {
      const status = record.trangThai === 1;
      return h(Tag, { color: status ? 'success' : 'error' }, () =>
        status ? 'Hiển thị' : 'Ẩn',
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
      placeholder: 'Nhập tiêu đề bài viết',
    },
    colProps: { span: 8 },
  },
  {
    field: 'loaiBaiViet',
    label: 'Loại bài viết',
    component: 'Select',
    componentProps: {
      placeholder: 'Chọn loại bài viết',
      allowClear: true,
      options: [
        { label: 'MEGA STORY', value: 'MEGA_STORY' },
        { label: 'HỎI ĐÁP', value: 'HOI_DAP' },
        { label: 'HƯỚNG DẪN BẢO HÀNH', value: 'HUONG_DAN_BAO_HANH' },
      ],
    },
    colProps: { span: 8 },
  },
  {
    field: 'trangThai',
    label: 'Trạng thái',
    component: 'Select',
    componentProps: {
      placeholder: 'Chọn trạng thái',
      allowClear: true,
      options: [
        { label: 'Hiển thị', value: 1 },
        { label: 'Ẩn', value: 0 },
      ],
    },
    colProps: { span: 8 },
  },
];