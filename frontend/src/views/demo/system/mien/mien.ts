import { realHttp } from '@/utils/http/axios';

enum Api {
  Filter = '/mien/filter',
  GetById = '/mien/get-by-id/',
  GetAll = '/mien/all',
  Create = '/mien/create',
  Update = '/mien/update',
  Delete = '/mien/delete/',
  GetAllCoSo = '/co-so/all',
}

// ─── Base filter types ────────────────────────────────────────────────────────

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

// ─── Entity DTOs ──────────────────────────────────────────────────────────────

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

export interface CoSoDto {
  id: number;
  ma: string;
  ten: string;
  sdt: string;
  dcVanPhong: string;
  dcKho: string;
  taoLuc: string;
  trangThai: number;
}

export interface ThongTinTenMienDto {
  id: number;
  sdt: string;
  email: string;
  taoLuc: string;
  trangThai: number;
}

export interface MienDto {
  id: number;
  tenMien: string;
  coSo: CoSoDto;
  tepTin: TepTinDto;
  taoLuc: string;
  trangThai: number;
  thongTinTenMiens: ThongTinTenMienDto[];
}

export interface ThongTinMienCreatingDto {
  sdt: string;
  email: string;
}

export interface ThongTinMienUpdatingDto {
  id: number;
  sdt: string;
  email: string;
}

export interface MienCreatingDto {
  tenMien: string;
  coSoId?: number;
  thongTinMiens?: ThongTinMienCreatingDto[];
}

export interface MienUpdatingDto {
  id: number;
  tenMien: string;
  coSoId?: number;
  thongTinMiens?: ThongTinMienUpdatingDto[];
}

// ─── Response wrappers ────────────────────────────────────────────────────────

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

// ─── Helper ───────────────────────────────────────────────────────────────────

export function convertToFilterRequest(params: any): BaseFilterRequest {
  const filters: FilterCriteria[] = [];
  const sorts: SortCriteria[] = [];

  if (params.tenMien) {
    filters.push({
      fieldName: 'tenMien',
      operation: 'LIKE',
      value: params.tenMien,
    });
  }

  if (params.coSoId !== undefined && params.coSoId !== '') {
    filters.push({
      fieldName: 'coSo.id',
      operation: 'EQUALS',
      value: params.coSoId,
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
    sorts.push({ fieldName: 'id', direction: 'DESC' });
  }

  return {
    filters,
    sorts,
    page: params.page ? params.page - 1 : 0,
    size: params.pageSize || 20,
  };
}

// ─── API calls ────────────────────────────────────────────────────────────────

export function filterMien(params: any) {
  const filterRequest = convertToFilterRequest(params);
  return realHttp
    .post<ResponseData<PageResponse<MienDto>>>(
      { url: Api.Filter, data: filterRequest },
      { isTransformResponse: false },
    )
    .then((res: any) => res as ResponseData<PageResponse<MienDto>>);
}

export function getMienById(id: number) {
  return realHttp
    .get<ResponseData<MienDto>>(
      { url: `${Api.GetById}${id}` },
      { isTransformResponse: false },
    )
    .then((res: any) => res as ResponseData<MienDto>);
}

export function getAllMien() {
  return realHttp
    .get<ResponseData<MienDto[]>>(
      { url: Api.GetAll },
      { isTransformResponse: false },
    )
    .then((res: any) => res as ResponseData<MienDto[]>);
}

export function createMien(data: MienCreatingDto, file: File) {
  const formData = new FormData();
  const jsonBlob = new Blob([JSON.stringify(data)], { type: 'application/json' });
  formData.append('dto', jsonBlob);
  formData.append('file', file);

  return realHttp
    .post<ResponseData<MienDto>>(
      {
        url: Api.Create,
        data: formData,
        headers: { 'Content-Type': 'multipart/form-data' },
      },
      { isTransformResponse: false },
    )
    .then((res: any) => res as ResponseData<MienDto>);
}

export function updateMien(data: MienUpdatingDto, file?: File | null) {
  const formData = new FormData();
  const jsonBlob = new Blob([JSON.stringify(data)], { type: 'application/json' });
  formData.append('dto', jsonBlob);
  if (file) {
    formData.append('file', file);
  }

  return realHttp
    .put<ResponseData<string>>(
      {
        url: Api.Update,
        data: formData,
        headers: { 'Content-Type': 'multipart/form-data' },
      },
      { isTransformResponse: false },
    )
    .then((res: any) => res as ResponseData<string>);
}

export function deleteMien(id: number) {
  return realHttp
    .delete<ResponseData<void>>(
      { url: `${Api.Delete}${id}` },
      { isTransformResponse: false },
    )
    .then((res: any) => res as ResponseData<void>);
}

export function getAllCoSo() {
  return realHttp
    .get<ResponseData<CoSoDto[]>>(
      { url: Api.GetAllCoSo },
      { isTransformResponse: false },
    )
    .then((res: any) => res as ResponseData<CoSoDto[]>);
}