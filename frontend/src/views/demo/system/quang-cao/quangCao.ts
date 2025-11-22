import { defHttp } from '@/utils/http/axios';
import { realHttp } from '@/utils/http/axios';

enum Api {
  Filter = '/quang-cao/filter',
  Create = '/quang-cao/create',
  Update = '/quang-cao/update',
  Delete = '/quang-cao/',
  GetById = '/quang-cao/',
  GetAllNganhHang = '/nganh-hang/all'
}

export interface FilterCriteria {
  fieldName: string;
  operation: 'EQUALS' | 'LESS_THAN' | 'LESS_THAN_OR_EQUAL' | 'GREATER_THAN' | 'GREATER_THAN_OR_EQUAL' | 'LIKE' | 'ILIKE' | 'IN' | 'NOT_IN';
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

export interface NganhHangDto {
  id: number;
  ma: string;
  ten: string;
  sdtSale: string;
  sdtTech: string;
  anhNgang: string;
  anhVuong: string;
  trangThai: number;
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

export interface QuangCaoDto {
  id: number;
  nganhHang: NganhHangDto;
  tepTin: TepTinDto;
  tieuDe: string;
  viTri: string;
  hoatDong: boolean;
  taoLuc: string;
  trangThai: number;
}

export interface QuangCaoCreatDto {
  nganhHangId: number;
  tieuDe: string;
  viTri: string;
}

export interface QuangCaoUpdateDto {
  id: number;
  nganhHangId: number;
  tieuDe: string;
  viTri: string;
  hoatDong: boolean;
  trangThai: number;
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

  if(params.viTri) {
    filters.push({
      fieldName: 'viTri',
      operation: 'ILIKE',
      value: params.viTri,
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

export function getAllNganhHang() {
  return realHttp.get<ResponseData<NganhHangDto[]>>(
    {
      url: Api.GetAllNganhHang,
    },
    {
      isTransformResponse: false,
    }
  ).then((res: any) => res as ResponseData<NganhHangDto[]>);
}

export function filterQuangCao(params: any) {
  const filterRequest = convertToFilterRequest(params);
  return realHttp
    .post<ResponseData<PageResponse<QuangCaoDto>>>(
      {
        url: Api.Filter,
        data: filterRequest,
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => {
      return res as ResponseData<PageResponse<QuangCaoDto>>;
    });
}

export function getQuangCaoById(id: number) {
  return realHttp
    .get<ResponseData<QuangCaoDto>>(
      {
        url: `${Api.GetById}${id}`,
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => res.data as ResponseData<QuangCaoDto>);
}

export function createQuangCao(data: QuangCaoCreatDto, file: File){
  const formData = new FormData();

  const jsonBlob = new Blob([JSON.stringify(data)], { type: 'application/json' });
  formData.append('dto', jsonBlob);
  formData.append('anh_bia', file);

  return realHttp
    .post<ResponseData<QuangCaoDto>>(
      {
        url: Api.Create,
        data: formData,
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => res as ResponseData<QuangCaoDto>);
}

export function updateQuangCao(data: QuangCaoUpdateDto, file: File | null){
  const formData = new FormData();

  const jsonBlob = new Blob([JSON.stringify(data)], { type: 'application/json' });
  formData.append('dto', jsonBlob);
  
  if (file) {
    formData.append('anh_bia', file);
  }

  return realHttp
    .put<ResponseData<QuangCaoDto>>(
      {
        url: Api.Update,
        data: formData,
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => res as ResponseData<QuangCaoDto>);
}

export function deleteQuangCao(id: number) {
  return realHttp
    .delete<ResponseData<void>>(
      {
        url: `${Api.Delete}${id}`,
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => res as ResponseData<void>);
}