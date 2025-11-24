import { realHttp } from '@/utils/http/axios';

enum Api {
  Filter = '/vat-tu/filter',
  GetById = '/vat-tu/',
  Create = '/vat-tu/create',
  Update = '/vat-tu/update',
  Delete = '/vat-tu/',
  GetAllNhomVatTu = '/nhom-vat-tu/filter',
  GetAllThuongHieu = '/thuong-hieu/all',
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

export interface ThuocTinh {
  ten: string;
  donVi: string;
  giaTri: any;
}

export interface GiaInfo {
  maCoSo: string;
  tenCoSo: string;
  giaNhap: number | undefined | null;
  giaBan: number | undefined | null;
  giaNhapRaw: string | null;
  giaBanRaw: string | null;
}
export interface GiaCreatingDto {
  maCoSo: string;
  tenCoSo: string;
  giaNhap: number | null;
  giaBan: number | null;
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

export interface AnhVatTuDto {
  id: number;
  tepTin: TepTinDto;
  anhChinh: boolean;
  trangThai: number;
}

export interface ThongTinGiaDto {
  id: number;
  dsGia: GiaInfo[];
  taoLuc: string;
  trangThai: number;
}

export interface NhomVatTuDto {
  id: number;
  ma: string;
  ten: string;
  thuocTinhRieng: Record<string, ThuocTinh>;
}

export interface ThuongHieuDto {
  id: number;
  ten: string;
  tenQuocTe: string;
}

export interface NhaCungCapDto {
  id: number;
  ten: string;
  tenQuocTe: string;
}

export interface VatTuDto {
  id: number;
  ma: string;
  nhomVatTu: NhomVatTuDto;
  thuongHieu: ThuongHieuDto;
  nhaCungCap: NhaCungCapDto;
  ten: string;
  sheetLink: string;
  donVi: string;
  moTaBaoGia: string;
  moTaHopDong: string;
  duLieuRieng: Record<string, ThuocTinh>;
  taoLuc: string;
  trangThai: number;
  anhVatTus: AnhVatTuDto[];
  thongTinGias: ThongTinGiaDto[];
}

export interface VatTuCreateDto {
  nhomVatTuId: number;
  thuongHieuId?: number;
  ten: string;
  sheetLink?: string;
  donVi?: string;
  moTaBaoGia?: string;
  moTaHopDong?: string;
  duLieuRieng?: Record<string, ThuocTinh>;
  trangThai: number;
  dsGia?: GiaCreatingDto[];
}

export interface VatTuUpdateDto {
  id: number;
  ten: string;
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

  if (params.ten) {
    filters.push({
      fieldName: 'ten',
      operation: 'LIKE',
      value: params.ten,
    });
  }

  if (params.nhomVatTuId) {
    filters.push({
      fieldName: 'nhomVatTu.id',
      operation: 'EQUALS',
      value: params.nhomVatTuId,
    });
  }

  if (params.thuongHieuId) {
    filters.push({
      fieldName: 'thuongHieu.id',
      operation: 'EQUALS',
      value: params.thuongHieuId,
    });
  }

  if (params.nhaCungCapId) {
    filters.push({
      fieldName: 'nhaCungCap.id',
      operation: 'EQUALS',
      value: params.nhaCungCapId,
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

export function filterVatTu(params: any) {
  const filterRequest = convertToFilterRequest(params);
  return realHttp
    .post<ResponseData<PageResponse<VatTuDto>>>(
      {
        url: Api.Filter,
        data: filterRequest,
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => {
      return res as ResponseData<PageResponse<VatTuDto>>;
    });
}

export function getVatTuById(id: number) {
  return realHttp
    .get<ResponseData<VatTuDto>>(
      {
        url: `${Api.GetById}${id}`,
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => res.data as ResponseData<VatTuDto>);
}

export function createVatTu(data: VatTuCreateDto, files: File[]) {
  const formData = new FormData();

  const jsonBlob = new Blob([JSON.stringify(data)], { type: 'application/json' });
  formData.append('dto', jsonBlob);

  files.forEach((file) => {
    formData.append('files', file);
  });

  return realHttp
    .post<ResponseData<VatTuDto>>(
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
    .then((res: any) => res as ResponseData<VatTuDto>);
}

export function updateVatTu(data: VatTuUpdateDto, sheetFile: File | null, imageFiles: File[]) {
  const formData = new FormData();

  const jsonBlob = new Blob([JSON.stringify(data)], { type: 'application/json' });
  formData.append('dto', jsonBlob);

  // Thêm file PDF sheet nếu có
  if (sheetFile) {
    formData.append('sheet', sheetFile);
  }

  // Thêm các file ảnh
  imageFiles.forEach((file) => {
    formData.append('files', file);
  });

  return realHttp
    .put<ResponseData<VatTuDto>>(
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
    .then((res: any) => res as ResponseData<VatTuDto>);
}

export function deleteVatTu(id: number) {
  return realHttp
    .delete<ResponseData<VatTuDto>>(
      {
        url: `${Api.Delete}${id}`,
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => res as ResponseData<void>);
}

export function getAllNhomVatTu() {
  return realHttp
    .post<ResponseData<PageResponse<NhomVatTuDto>>>(
      {
        url: Api.GetAllNhomVatTu,
        data: {
          filters: [],
          sorts: [{ fieldName: 'id', direction: 'DESC' }],
          page: 0,
          size: 1000,
        },
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => res as ResponseData<PageResponse<NhomVatTuDto>>);
}

export function getAllThuongHieu() {
  return realHttp.get<ResponseData<ThuongHieuDto[]>>(
    {
      url: Api.GetAllThuongHieu,
    },
    {
      isTransformResponse: false,
    }
  ).then((res: any) => res as ResponseData<ThuongHieuDto[]>);
}