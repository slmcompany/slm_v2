import { realHttp } from '@/utils/http/axios';

enum Api {
  Filter = '/nhom-tron-goi/filter',
  GetById = '/nhom-tron-goi/',
  Create = '/nhom-tron-goi/create',
  Update = '/nhom-tron-goi/update/',
  Delete = '/nhom-tron-goi/delete/',
  GetAllThuongHieu = '/thuong-hieu/all',
  GetAllNganhHang = '/nganh-hang/all',
  FilterVatTu = '/vat-tu/filter',
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

export interface ThuongHieuDto {
  id: number;
  ten: string;
  tenQuocTe: string;
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

export interface NhomTronGoiDto {
  id: number;
  nganhHang: NganhHangDto;
  ten: string;
  thuongHieuTamPin: ThuongHieuDto;
  thuongHieuInverter: ThuongHieuDto;
  thuongHieuPinLuuTru: ThuongHieuDto;
  trangThai: number;
  taoLuc?: string;
}

export interface NhomTronGoiCreateDto {
  nganhHangId: number;
  ten: string;
  thuongHieuTamPinId: number;
  thuongHieuInverterId: number;
  thuongHieuPinLuuTruId: number;
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

// Hàm convert params từ table thành filter request
export function convertToFilterRequest(params: any): BaseFilterRequest {
  const filters: FilterCriteria[] = [];
  const sorts: SortCriteria[] = [];
  
  // Xử lý filter theo tên
  if (params.ten) {
    filters.push({
      fieldName: 'ten',
      operation: 'ILIKE', // Sử dụng ILIKE để tìm kiếm không phân biệt hoa thường
      value: `%${params.ten}%`,
      logicType: 'AND',
    });
  }
  
  // Xử lý filter theo ngành hàng
  if (params.nganhHangId) {
    filters.push({
      fieldName: 'nganhHang.id',
      operation: 'EQUALS',
      value: params.nganhHangId,
      logicType: 'AND',
    });
  }
  
  // Mặc định sort theo ngày tạo giảm dần
  sorts.push({
    fieldName: 'taoLuc',
    direction: 'DESC',
  });
  
  // Xử lý phân trang
  const page = params.page ? params.page - 1 : 0; // Backend thường bắt đầu từ 0
  const size = params.pageSize || params.size || 20;
  
  return {
    filters,
    sorts,
    page,
    size,
  };
}

// API filter nhóm trọn gói
export function nhomTronGoiFilter(params: any) {
  const filterRequest: BaseFilterRequest = convertToFilterRequest(params);
  
  console.log('Filter request:', filterRequest); // Debug log
  
  return realHttp.post<ResponseData<PageResponse<NhomTronGoiDto>>>(
    {
      url: Api.Filter,
      data: filterRequest,
    },
    {
      isTransformResponse: false,
    },
  ).then((res: any) => {
    console.log('Filter response:', res); // Debug log
    return res as ResponseData<PageResponse<NhomTronGoiDto>>;
  });
}

// API lấy chi tiết nhóm trọn gói
export function getNhomTronGoiById(id: number) {
  return realHttp
    .get<ResponseData<NhomTronGoiDto>>(
      {
        url: Api.GetById + id,
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => res as ResponseData<NhomTronGoiDto>);
}

// API tạo mới nhóm trọn gói
export function createNhomTronGoi(data: NhomTronGoiCreateDto) {
  return realHttp
    .post<ResponseData<NhomTronGoiDto>>(
      {
        url: Api.Create,
        data,
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => res as ResponseData<NhomTronGoiDto>);
}

// API lấy tất cả thương hiệu
export function getAllThuongHieu() {
  return realHttp
    .get<ResponseData<ThuongHieuDto[]>>(
      {
        url: Api.GetAllThuongHieu,
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => {
      console.log('Get all thuong hieu response:', res); // Debug log
      return res as ResponseData<ThuongHieuDto[]>;
    });
}

// API lấy tất cả ngành hàng
export function getAllNganhHang() {
  return realHttp
    .get<ResponseData<NganhHangDto[]>>(
      {
        url: Api.GetAllNganhHang,
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => {
      console.log('Get all nganh hang response:', res); // Debug log
      return res as ResponseData<NganhHangDto[]>;
    });
}