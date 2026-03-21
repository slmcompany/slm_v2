import { realHttp } from '@/utils/http/axios';

enum Api {
  Filter = '/nguoi-dung/filter',
  GetAll = '/nguoi-dung/all',
  GetById = '/nguoi-dung/',
  CreateAccount = '/nguoi-dung/create-account',
  Update = '/nguoi-dung/update',
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

export interface CoSoDto {
  id: number;
  ten: string;
  diaChi: string;
  sdt: string;
  trangThai: number;
}

export interface KhachHangDto {
  id: number;
  email: string;
  sdt: string;
  hoVaTen: string;
  gioiTinh: boolean;
  sinhNhat: string;
  diaChi: string;
  daBanDuocHang: boolean;
  taoLuc: string;
  trangThai: number;
}

export interface NguoiDungDto {
  id: number;
  coSo: CoSoDto;
  phanQuyen: string;
  email: string;
  sdt: string;
  matKhau: string;
  hoVaTen: string;
  gioiTinh: boolean;
  sinhNhat: string;
  phanTramHoaHong: number;
  tongHoaHong: number;
  diaChi: string;
  nganHang: string;
  maNganHang: string;
  taoLuc: string;
  trangThai: number;
  khachHangs: KhachHangDto[];
}

export interface CreateAccountRequest {
  sdt: string;
  matKhau: string;
  hoVaTen: string;
  email: string;
  maCoSo: string;
}

export interface UpdateNguoiDungRequest {
  id: number;
  phanQuyen?: string;
  email?: string;
  sdt: string;
  hoVaTen: string;
  sinhNhat?: string; // ISO string, maps to Instant on backend
  phanTramHoaHong?: number;
  diaChi?: string;
}

export function convertToFilterRequest(params: any): BaseFilterRequest {
  const filters: FilterCriteria[] = [];
  const sorts: SortCriteria[] = [];

  if (params.hoVaTen) {
    filters.push({ fieldName: 'hoVaTen', operation: 'ILIKE', value: params.hoVaTen });
  }
  if (params.email) {
    filters.push({ fieldName: 'email', operation: 'ILIKE', value: params.email });
  }
  if (params.sdt) {
    filters.push({ fieldName: 'sdt', operation: 'ILIKE', value: params.sdt });
  }
  if (params.phanQuyen) {
    filters.push({ fieldName: 'phanQuyen', operation: 'EQUALS', value: params.phanQuyen });
  }
  if (params.trangThai !== undefined && params.trangThai !== null && params.trangThai !== '') {
    filters.push({ fieldName: 'trangThai', operation: 'EQUALS', value: params.trangThai });
  }

  sorts.push({
    fieldName: params.sortField || 'id',
    direction: params.sortOrder === 'ascend' ? 'ASC' : 'DESC',
  });

  return {
    filters,
    sorts,
    page: params.page ? params.page - 1 : 0,
    size: params.pageSize || 20,
  };
}

export function getAllNguoiDung() {
  return realHttp
    .get<ResponseData<NguoiDungDto[]>>({ url: Api.GetAll }, { isTransformResponse: false })
    .then((res: any) => res as ResponseData<NguoiDungDto[]>);
}

export function filterNguoiDung(params: any) {
  const filterRequest = convertToFilterRequest(params);
  return realHttp
    .post<
      ResponseData<PageResponse<NguoiDungDto>>
    >({ url: Api.Filter, data: filterRequest }, { isTransformResponse: false })
    .then((res: any) => res as ResponseData<PageResponse<NguoiDungDto>>);
}

export function getNguoiDungById(id: number) {
  return realHttp
    .get<ResponseData<NguoiDungDto>>({ url: `${Api.GetById}${id}` }, { isTransformResponse: false })
    .then((res: any) => res.data as ResponseData<NguoiDungDto>);
}

export function createAccount(data: CreateAccountRequest) {
  return realHttp
    .post<
      ResponseData<NguoiDungDto>
    >({ url: Api.CreateAccount, data }, { isTransformResponse: false })
    .then((res: any) => res as ResponseData<NguoiDungDto>);
}

export function updateNguoiDung(data: UpdateNguoiDungRequest) {
  return realHttp
    .put<ResponseData<NguoiDungDto>>({ url: Api.Update, data }, { isTransformResponse: false })
    .then((res: any) => res as ResponseData<NguoiDungDto>);
}
