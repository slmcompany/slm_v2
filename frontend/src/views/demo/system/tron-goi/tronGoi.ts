import { realHttp } from '@/utils/http/axios';
import { c } from 'node_modules/vite/dist/node/types.d-aGj9QkWt';
enum Api {
  Filter = '/tron-goi/filter',
  GetById = '/tron-goi/',
  Create = '/tron-goi/create',
  Update = '/tron-goi/update/',
  Delete = '/tron-goi/delete/',
  GetAllCoSo = '/co-so/all',

  GetAllNhomTronGoi = '/nhom-tron-goi/all',
  GetAllThuongHieu = '/thuong-hieu/all',
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

export interface TepTinDto {
  id: number;
  url: string;
  ten: string;
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

export interface VatTuTronGoiDto {
  id: number;
  vatTu: VatTuDto;
  moTa: string;
  soLuong: number;
  gia: number;
  gm: number;
  taoLuc: string;
  thoiGianBaoHanh: number;
  duocBaoHanh: boolean;
  trangThai: number;
}
export interface VatTuTronGoiCreatingDto {
  vatTuId: number;
  moTa: string;
  soLuong: number;
  gia: number;
  gm: number;
  thoiGianBaoHanh: number;
  duocBaoHanh: boolean;
  trangThai: number;
}

export interface CoSoDto {
  id: number;
  ma: string;
  ten: string;
  dcVanPhong: string;
  dcKho: string;
  taoLuc: string;
  trangThai: number;
}

export interface TronGoiDto {
  id: number;
  coSo: CoSoDto;
  nhomTronGoi: NhomTronGoiDto;
  ten: string;
  tepTin: TepTinDto;
  loaiHeThong: string;
  loaiPha: string;
  sanLuongToiThieu: number;
  sanLuongToiDa: number;
  moTa: string;
  taoLuc: string;
  tongGia: number;
  banChay: boolean;
  trangThai: number;
  vatTuTronGois: VatTuTronGoiDto[];
}

export interface ThongTinTronGoiCoSoCreatingDto {
  coSoId: number;
  sanLuongToiThieu: number;
  sanLuongToiDa: number;
}

export interface TronGoiCreateDto {
  tronGoiCoSos: ThongTinTronGoiCoSoCreatingDto[];
  nhomTronGoiId: number;
  ten: string;
  loaiHeThong: string;
  loaiPha: string;
  moTa: string;
  tongGia: number;
  banChay: boolean;
  vatTuTronGois: VatTuTronGoiCreatingDto[];
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

  if (params.nhomTronGoiId) {
    filters.push({
      fieldName: 'nhomTronGoi.id',
      operation: 'EQUALS',
      // đảm bảo kiểu number
      value: Number(params.nhomTronGoiId+''),
    });
  }

  if (params.loaiHeThong) {
    filters.push({
      fieldName: 'loaiHeThong',
      operation: 'EQUALS',
      value: params.loaiHeThong,
    });
  }

  if (params.loaiPha) {
    filters.push({
      fieldName: 'loaiPha',
      operation: 'EQUALS',
      value: params.loaiPha,
    });
  }

  return {
    filters,
    sorts,
    page: params.page - 1 || 0,
    size: params.size || 10,
  };
}

export function filterTronGoi(params: any) {
  const filterRequest: BaseFilterRequest = convertToFilterRequest(params);

  console.log('Filter request:', filterRequest); // Debug log

  return realHttp
    .post<ResponseData<PageResponse<TronGoiDto>>>(
      {
        url: Api.Filter,
        data: filterRequest,
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => {
      console.log('Filter response:', res); // Debug log
      return res as ResponseData<PageResponse<TronGoiDto>>;
    });
}

export function getAllCoSo() {
  return realHttp
    .get<ResponseData<CoSoDto[]>>(
      {
        url: Api.GetAllCoSo,
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => {
      console.log('GetAllCoSo response:', res); // Debug log
      return res as ResponseData<CoSoDto[]>;
    });
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

export function getAllNhomTronGoi() {
  return realHttp
    .get<ResponseData<NhomTronGoiDto[]>>(
      {
        url: Api.GetAllNhomTronGoi,
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => {
      console.log('GetAllNhomTronGoi response:', res); // Debug log
      return res as ResponseData<NhomTronGoiDto[]>;
    });
}

export function filterVatTu(nhomVatTuId: number | null, thuongHieuId: number | null) {
  const filters: FilterCriteria[] = [];
  const sorts: SortCriteria[] = [];
  if (thuongHieuId) {
    filters.push({
      fieldName: 'nhomVatTu.id',
      operation: 'EQUALS',
      value: nhomVatTuId,
    });
  }

  if (nhomVatTuId) {
    filters.push({
      fieldName: 'thuongHieu.id',
      operation: 'EQUALS',
      value: thuongHieuId,
    });
  }
  const filterRequest: BaseFilterRequest = {
    filters,
    sorts,
    page: 0,
    size: 1000,
  };

  console.log('Filter VatTu request:', filterRequest); // Debug log

  return realHttp
    .post<ResponseData<PageResponse<VatTuDto>>>(
      {
        url: Api.FilterVatTu,
        data: filterRequest,
      },
      {
        isTransformResponse: false,
      },
    )
    .then((res: any) => {
      console.log('Filter VatTu response:', res); // Debug log
      return res as ResponseData<PageResponse<VatTuDto>>;
    });
}

export function createTronGoi(data: TronGoiCreateDto, file: File | null) {
  const formData = new FormData();
  formData.append('data', JSON.stringify(data));
  if (file) {
    formData.append('file', file);
  }

  return realHttp
    .post<ResponseData<TronGoiDto>>(
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
    .then((res: any) => {
      console.log('CreateTronGoi response:', res); // Debug log
      return res as ResponseData<TronGoiDto>;
    });
}
