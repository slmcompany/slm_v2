import { defHttp } from '@/utils/http/axios';
import { realHttp } from '@/utils/http/axios';

enum Api {
  Filter = '/nhom-vat-tu/filter',
  GetById = '/nhom-vat-tu/',
  Create = '/nhom-vat-tu/create',
  Update = '/nhom-vat-tu/update/',
  Delete = '/nhom-vat-tu/delete/',
  GetAllNganhHang = '/nganh-hang/all',
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

export interface ThuocTinh {
  ten: string;
  donVi: string;
  giaTri: any;
}

export interface NghanhHangDto {
  id: number;
  ma: string;
  ten: string;
  sdtSale: string;
  sdtTech: string;
  anhNgang: string;
  anhVuong: string;
  trangThai: number;
}


export interface NhomVatTuDto {
  id: number;
  ma: string;
  nghanhHang: NghanhHangDto;
  ten: string;
  thuocTinhRieng: Record<string, ThuocTinh>;
  gm: number;
  vatTuChinh: boolean;
  taoLuc: string;
  trangThai: number;
}

export interface NhomVatTuCreateDto {
  ma: string;
  nghanhHangId: number;
  ten: string;
  thuocTinhRieng?: Record<string, ThuocTinh>;
  gm?: number;
  vatTuChinh: boolean;
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

/**
 * Chuyển đổi params từ form search sang BaseFilterRequest
 */
export function convertToFilterRequest(params: any): BaseFilterRequest {
  const filters: FilterCriteria[] = [];
  const sorts: SortCriteria[] = [];

  // Xử lý filters
  if (params.ten) {
    filters.push({
      fieldName: 'ten',
      operation: 'ILIKE',
      value: params.ten,
    });
  }

  if (params.ma) {
    filters.push({
      fieldName: 'ma',
      operation: 'ILIKE',
      value: params.ma,
    });
  }

  if (params.nghanhHangId) {
    filters.push({
      fieldName: 'nghanhHang.id',
      operation: 'EQUALS',
      value: params.nghanhHangId,
    });
  }

  if (params.trangThai !== undefined && params.trangThai !== '') {
    filters.push({
      fieldName: 'trangThai',
      operation: 'EQUALS',
      value: params.trangThai,
    });
  }

  if (params.vatTuChinh !== undefined && params.vatTuChinh !== '') {
    filters.push({
      fieldName: 'vatTuChinh',
      operation: 'EQUALS',
      value: params.vatTuChinh === 1,
    });
  }

  if (params.gmMin !== undefined && params.gmMin !== null) {
    filters.push({
      fieldName: 'gm',
      operation: 'GREATER_THAN_OR_EQUAL',
      value: params.gmMin,
    });
  }

  if (params.gmMax !== undefined && params.gmMax !== null) {
    filters.push({
      fieldName: 'gm',
      operation: 'LESS_THAN_OR_EQUAL',
      value: params.gmMax,
    });
  }

  // Xử lý sorting
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

/**
 * Filter danh sách nhóm vật tư
 */
export function filterNhomVatTu(params: any) {
  const filterRequest = convertToFilterRequest(params);
  return realHttp.post<ResponseData<PageResponse<NhomVatTuDto>>>(
    {
      url: Api.Filter,
      data: filterRequest,
    },
    {
      isTransformResponse: false,
    }
  ).then((res: any) => {
    return res as ResponseData<PageResponse<NhomVatTuDto>>;
  });
}

/**
 * Lấy chi tiết nhóm vật tư theo ID
 */
export function getNhomVatTuById(id: number) {
  return realHttp.get<ResponseData<NhomVatTuDto>>(
    {
      url: `${Api.GetById}${id}`,
    },
    {
      isTransformResponse: false,
    }
  ).then((res: any) => res.data as ResponseData<NhomVatTuDto>);
}

/**
 * Tạo mới nhóm vật tư
 */
export function createNhomVatTu(data: NhomVatTuCreateDto) {
  return realHttp.post<ResponseData<NhomVatTuDto>>(
    {
      url: Api.Create,
      data,
    },
    {
      isTransformResponse: false,
    }
  ).then((res: any) => res as ResponseData<NhomVatTuDto>);
}

/**
 * Cập nhật nhóm vật tư
 */
export function updateNhomVatTu(id: number, data: NhomVatTuCreateDto) {
  return realHttp.put<ResponseData<NhomVatTuDto>>(
    {
      url: `${Api.Update}${id}`,
      data,
    },
    {
      isTransformResponse: false,
    }
  ).then((res: any) => res as ResponseData<NhomVatTuDto>);
}

/**
 * Xóa nhóm vật tư
 */
export function deleteNhomVatTu(id: number) {
  return realHttp.delete<ResponseData<void>>(
    {
      url: `${Api.Delete}${id}`,
    },
    {
      isTransformResponse: false,
    }
  ).then((res: any) => res as ResponseData<void>);
}

export function getAllNganhHang() {
  return realHttp.get<ResponseData<NghanhHangDto[]>>(
    {
      url: Api.GetAllNganhHang,
    },
    {
      isTransformResponse: false,
    }
  ).then((res: any) => res as ResponseData<NghanhHangDto[]>);
}