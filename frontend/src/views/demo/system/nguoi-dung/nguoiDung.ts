// import { defHttp } from '@/utils/http/axios';
// import { realHttp } from '@/utils/http/axios';


// enum Api {
//   Filter = '/nguoi-dung/filter'
// }


// export interface FilterCriteria {
//   fieldName: string;
//   operation: 'EQUALS' | 'LESS_THAN' | 'LESS_THAN_OR_EQUAL' | 'GREATER_THAN' | 'GREATER_THAN_OR_EQUAL' | 'LIKE' | 'ILIKE' | 'IN' | 'NOT_IN';
//   value: any;
//   logicType?: 'AND' | 'OR';
// }

// export interface SortCriteria {
//   fieldName: string;
//   direction: 'ASC' | 'DESC';
// }

// export interface BaseFilterRequest {
//   filters: FilterCriteria[];
//   sorts: SortCriteria[];
//   page: number;
//   size: number;
// }

// export interface PageResponse<T> {
//   content: T[];
//   pageable: {
//     pageNumber: number;
//     pageSize: number;
//     offset: number;
//   };
//   totalElements: number;
//   totalPages: number;
//   size: number;
//   number: number;
//   first: boolean;
//   last: boolean;
//   empty: boolean;
// }

// export interface ResponseData<T> {
//   status: number;
//   data: T;
//   message: string;
//   timestamp: string;
//   error?: any;
// }


// export interface CoSoDto {
//   id: number;
//   ma: string;
//   ten: string;
//   dcVanPhong: string;
//   dcKho: string;
//   taoLuc: string;
//   trangThai: number;
// }

// export function convertToFilterRequest(params: any): BaseFilterRequest {
//   const filters: FilterCriteria[] = [];
//   const sorts: SortCriteria[] = [];

//   if (params.ten) {
//     filters.push({
//       fieldName: 'ten',
//       operation: 'ILIKE',
//       value: params.ten,
//     });
//   }

//   if(params.sdt) {
//     filters.push({
//       fieldName: 'sdt',
//       operation: 'ILIKE',
//       value: params.ten,
//     });
//   }
//   return {
//     filters,
//     sorts,
//     page: params.page ? params.page - 1 : 0,
//     size: params.pageSize || 20,
//   };
// }