import { realHttp } from '@/utils/http/axios';
import { c } from 'node_modules/vite/dist/node/types.d-aGj9QkWt';

enum Api {
  Filter = '/hop-dong/filter',
  Create = '/hop-dong/create',
  Delete = '/hop-dong/delete/',
  GetAllCoSo = '/co-so/all',
  GetAllNganhHang = '/nganh-hang/all',
  GetAllNguoiDung = '/nguoi-dung/all',
  FilterVatTu = '/vat-tu/filter',
}

// ============= Types =============
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

export interface GiaInfo {
  maCoSo: string;
  tenCoSo: string;
  giaNhap: number | null;
  giaBan: number | null;
}

export interface ThongTinGiaDto {
  id: number;
  dsGia: GiaInfo[];
  taoLuc: string;
  trangThai: number;
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

export interface VatTuDto {
  id: number;
  ma: string;
  ten: string;
  donVi: string;
  moTaBaoGia: string;
  moTaHopDong: string;
  duLieuRieng: Record<string, any>;
  thongTinGias: ThongTinGiaDto[];
  nhomVatTu: {
    id: number;
    ma: string;
    ten: string;
    gm: number;
  };
}

export interface VatTuHopDongCreatingDto {
  vatTuId: number | undefined;
  moTa: string;
  soLuong: number;
  giaBan: number;
  gm: number;
  thoiGianBaoHanh: number;
  duocBaoHanh: boolean;
  trangThai: number;
}

export interface HopDongCreateDto {
  coSoId: number | undefined;
  nghanhHangId: number | undefined;
  ten: string;
  loaiHeThong: string;
  loaiPha: string;
  sanLuongToiThieu: number;
  sanLuongToiDa: number;
  giaKhungSat: number;
  moTa: string;
  nguoiGioiThieuId: number | undefined;
  emailKhachHang: string;
  sdtKhachHang: string;
  hoVaTenKhachHang: string;
  gioiTinhKhachHang: boolean;
  sinhNhatKhachHang: string | null;
  diaChiKhachHang: string;
  tongGia: number;
  taoLuc: string;
  vatTuHopDongs: VatTuHopDongCreatingDto[];
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

export interface KhachHangDto{
  id: number;
  email: string;
  sdt: string;
  hoVaTen: string;
  gioiTinh: boolean;
  sinhNhat: string;
  diaChi: string;
  daBanDuocHang: boolean;
  taoLuc: string;
  trangThai: string;
}

export interface NguoiDungDto{
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

export interface VatTuHopDongDto{
  id: number;
  vatTu: VatTuDto;
  moTa: string;
  soLuong: number;
  gm: number;
  giaHeThong: number;
  giaHienThi: number;
  thoiGianBaoHanh: number;
  baoHanhBatDau: number;
  baoHanhKetThuc: number;
  duocBaoHanh: boolean;
  taoLuc: string;
  trangThai: number;
}

export interface HopDongDto {
  id: number;
  coSo: { id: number; ma: string; ten: string };
  nghanhHang: { id: number; ma: string; ten: string };
  ten: string;
  loaiHeThong: string;
  loaiPha: string;
  sanLuongToiThieu: number;
  sanLuongToiDa: number;
  giaKhungSat: number;
  moTa: string;
  nguoiGioiThieu: { id: number; hoVaTen: string };
  khachHang: { id: number; hoVaTen: string; email: string; sdt: string };
  tongGia: number;
  taoLuc: string;
  trangThai: number;
  vatTuHopDongs: VatTuHopDongDto[];
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
}

export interface ResponseData<T> {
  status: number;
  data: T;
  message: string;
}

// ============= API Functions =============
export function convertToFilterRequest(params: any): BaseFilterRequest {
  const filters: FilterCriteria[] = [];
  
  if (params.ten) {
    filters.push({ fieldName: 'ten', operation: 'LIKE', value: params.ten });
  }
  if (params.coSoId) {
    filters.push({ fieldName: 'coSo.id', operation: 'EQUALS', value: Number(params.coSoId) });
  }
  if (params.nghanhHangId) {
    filters.push({ fieldName: 'nghanhHang.id', operation: 'EQUALS', value: Number(params.nghanhHangId) });
  }

  return {
    filters,
    sorts: [],
    page: params.page - 1 || 0,
    size: params.size || 20,
  };
}

export function filterHopDong(params: any) {
  return realHttp.post<ResponseData<PageResponse<HopDongDto>>>(
    { url: Api.Filter, data: convertToFilterRequest(params) },
    { isTransformResponse: false }
  ).then((res: any) => res as ResponseData<PageResponse<HopDongDto>>);
}

export function getAllCoSo() {
  return realHttp.get<ResponseData<CoSoDto[]>>(
    { url: Api.GetAllCoSo },
    { isTransformResponse: false }
  ).then((res: any) => res as ResponseData<CoSoDto[]>);
}

export function getAllNganhHang() {
  return realHttp.get<ResponseData<NganhHangDto[]>>(
    { url: Api.GetAllNganhHang },
    { isTransformResponse: false }
  ).then((res: any) => res as ResponseData<NganhHangDto[]>);
}

export function getAllNguoiDung() {
  return realHttp.get<ResponseData<NguoiDungDto[]>>(
    { url: Api.GetAllNguoiDung },
    { isTransformResponse: false }
  ).then((res: any) => res as ResponseData<NguoiDungDto[]>);
}

export function filterVatTu(maNhomVatTu: string | null) {
  const filters: FilterCriteria[] = [];
  if (maNhomVatTu) {
    filters.push({ fieldName: 'nhomVatTu.ma', operation: 'EQUALS', value: maNhomVatTu });
  }

  return realHttp.post<ResponseData<PageResponse<VatTuDto>>>(
    { url: Api.FilterVatTu, data: { filters, sorts: [], page: 0, size: 1000 } },
    { isTransformResponse: false }
  ).then((res: any) => res as ResponseData<PageResponse<VatTuDto>>);
}

export function createHopDong(data: HopDongCreateDto) {
  return realHttp.post<ResponseData<HopDongDto>>(
    { url: Api.Create, data },
    { isTransformResponse: false }
  ).then((res: any) => res as ResponseData<HopDongDto>);
}

export function deleteHopDong(id: number) {
  return realHttp.delete<ResponseData<HopDongDto>>(
    { url: Api.Delete + id },
    { isTransformResponse: false }
  ).then((res: any) => res as ResponseData<HopDongDto>);
}