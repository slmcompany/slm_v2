// src/views/demo/system/bai-viet/baiViet.ts

import { realHttp } from '@/utils/http/axios';

enum Api {
  Filter = '/bai-viet/filter',
  GetById = '/bai-viet/get-by-id/',
  Delete = '/bai-viet/delete',
}

export interface FilterCriteria {
  fieldName: string;
  operation:
    | 'EQUALS'
    | 'LESS_THAN'
    | 'LESS_THAN_OR_EQUAL'
    | 'GREATER_THAN'
    | 'GREATER_THAN_OR_EQUAL'
    | 'LIKE'
    | 'ILIKE'
    | 'IN'
    | 'NOT_IN';
  value: any;
  logicType?: 'AND' | 'OR';
}

export interface SortCriteria {
  fieldName: string;
  direction: 'ASC' | 'DESC';
}

export interface BaseFilterRequest {
  filters: FilterCriteria[];
  sorts: SortCriteria[];
  page: number;
  size: number;
}

export interface TepTinDto {
  id: number;
  tenTepGoc: string;
  tenTaiLen: string;
  tenLuuTru: string;
  duongDan: string;
  loaiTepTin: string;
  duoiTep: string;
  kichCo: number;
  moTa: string;
  taoLuc: string;
  suaLuc: string;
  trangThai: number;
}

export interface NguoiDungDto {
  id: number;
  ten: string;
  tenDangNhap: string;
  email: string;
}

export interface BaiVietDto {
  id: number;
  loaiBaiViet: string;
  tieuDe: string;
  duongDanYoutube: string;
  anhBia: TepTinDto | null;
  anhNgoai: TepTinDto | null;
  noiDung: TepTinDto | null;
  taoBoi: NguoiDungDto | null;
  lienQuan: string;
  taoLuc: string;
  suaLuc: string;
  trangThai: number;
}

export interface PageResponse<T> {
  content: T[];
  pageable: {
    pageNumber: number;
    pageSize: number;
    offset: number;
  };
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}

export interface ResponseData<T> {
  status: number;
  data: T;
  message: string;
  timestamp: string;
  error?: any;
}

export function convertToFilterRequest(params: any): BaseFilterRequest {
  const filters: FilterCriteria[] = [];
  const sorts: SortCriteria[] = [];

  if (params.tieuDe) {
    filters.push({
      fieldName: 'tieuDe',
      operation: 'ILIKE',
      value: params.tieuDe,
    });
  }

  if (params.loaiBaiViet) {
    filters.push({
      fieldName: 'loaiBaiViet',
      operation: 'EQUALS',
      value: params.loaiBaiViet,
    });
  }

  if (params.trangThai !== undefined && params.trangThai !== '') {
    filters.push({
      fieldName: 'trangThai',
      operation: 'EQUALS',
      value: params.trangThai,
    });
  }

  if (params.sortField) {
    sorts.push({
      fieldName: params.sortField,
      direction: params.sortOrder === 'ascend' ? 'ASC' : 'DESC',
    });
  } else {
    sorts.push({
      fieldName: 'id',
      direction: 'DESC',
    });
  }

  return {
    filters,
    sorts,
    page: params.page ? params.page - 1 : 0,
    size: params.pageSize || 20,
  };
}

export function filterBaiViet(params: any) {
  const filterRequest = convertToFilterRequest(params);
  return realHttp
    .post<ResponseData<PageResponse<BaiVietDto>>>(
      {
        url: Api.Filter,
        data: filterRequest,
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => {
      return res as ResponseData<PageResponse<BaiVietDto>>;
    });
}

export function getBaiVietById(id: number) {
  return realHttp
    .get<ResponseData<BaiVietDto>>(
      {
        url: `${Api.GetById}${id}`,
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => res as ResponseData<BaiVietDto>);
}

// Placeholder - API chưa có
export function deleteBaiViet(id: number) {
  return realHttp
    .delete<ResponseData<null>>(
      {
        url: `${Api.Delete}/${id}`,
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => res as ResponseData<String>);
  // return Promise.resolve({
  //   status: 501,
  //   message: 'Chức năng này hiện tại chưa được hỗ trợ',
  //   data: null,
  // });
}