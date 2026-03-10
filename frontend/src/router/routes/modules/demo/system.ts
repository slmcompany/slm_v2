import type { AppRouteModule } from '@/router/types';

import { LAYOUT } from '@/router/constant';
import { t } from '@/hooks/web/useI18n';

const system: AppRouteModule = {
  path: '/system',
  name: 'System',
  component: LAYOUT,
  redirect: '/system/account',
  meta: {
    orderNo: 2000,
    icon: 'ion:settings-outline',
    title: t('routes.demo.system.moduleName'),
  },
  children: [
    // {
    //   path: 'account',
    //   name: 'AccountManagement',
    //   meta: {
    //     title: t('routes.demo.system.account'),
    //     ignoreKeepAlive: false,
    //   },
    //   component: () => import('@/views/demo/system/account/index.vue'),
    // },
    // {
    //   path: 'vxeTableAccount',
    //   name: 'VxeTableAccountManagement',
    //   meta: {
    //     title: t('routes.demo.system.vxeTableAccount'),
    //     ignoreKeepAlive: false,
    //   },
    //   component: () => import('@/views/demo/system/vxe-account/index.vue'),
    // },
    // {
    //   path: 'account_detail/:id',
    //   name: 'AccountDetail',
    //   meta: {
    //     hideMenu: true,
    //     title: t('routes.demo.system.account_detail'),
    //     ignoreKeepAlive: true,
    //     showMenu: false,
    //     currentActiveMenu: '/system/account',
    //   },
    //   component: () => import('@/views/demo/system/account/AccountDetail.vue'),
    // },
    // {
    //   path: 'role',
    //   name: 'RoleManagement',
    //   meta: {
    //     title: t('routes.demo.system.role'),
    //     ignoreKeepAlive: true,
    //   },
    //   component: () => import('@/views/demo/system/role/index.vue'),
    // },

    // {
    //   path: 'menu',
    //   name: 'MenuManagement',
    //   meta: {
    //     title: t('routes.demo.system.menu'),
    //     ignoreKeepAlive: true,
    //   },
    //   component: () => import('@/views/demo/system/menu/index.vue'),
    // },
    // {
    //   path: 'dept',
    //   name: 'DeptManagement',
    //   meta: {
    //     title: t('routes.demo.system.dept'),
    //     ignoreKeepAlive: true,
    //   },
    //   component: () => import('@/views/demo/system/dept/index.vue'),
    // },
    // {
    //   path: 'changePassword',
    //   name: 'ChangePassword',
    //   meta: {
    //     title: t('routes.demo.system.password'),
    //     ignoreKeepAlive: true,
    //   },
    //   component: () => import('@/views/demo/system/password/index.vue'),
    // },
    {
      path: 'nhom-vat-tu',
      name: 'NhomVatTuManagement',
      meta: {
        title: t('routes.demo.system.nhomVatTu'),
        ignoreKeepAlive: false,
      },
      component: () => import('@/views/demo/system/nhom-vat-tu/index.vue'),
    },
    {
      path: 'vat-tu',
      name: 'VatTuManagement',
      meta: {
        title: t('routes.demo.system.vatTu'),
        ignoreKeepAlive: false,
      },
      component: () => import('@/views/demo/system/vat-tu/index.vue'),
    },
    {
      path: 'nhom-tron-goi',
      name: 'NhomTronGoiManagement',
      meta: {
        title: t('routes.demo.system.nhomTronGoi'),
        ignoreKeepAlive: false,
      },
      component: () => import('@/views/demo/system/nhom-tron-goi/index.vue'),
    },
    {
      path: 'tron-goi',
      name: 'TronGoiManagement',
      meta: {
        title: t('routes.demo.system.tronGoi'),
        ignoreKeepAlive: false,
      },
      component: () => import('@/views/demo/system/tron-goi/index.vue'),
    },
    {
      path:'quan-ly-bai-viet',
      name:'QuanLyBaiViet',
      meta:{
        title:t('routes.demo.system.quanLyBaiViet'),
        ignoreKeepAlive:false,
      },
      component:()=>import('@/views/demo/system/quan-ly-bai-viet/index.vue'),
    },
    {
      path: 'bai-viet',
      name: 'BaiVietManagement',
      meta: {
        title: t('routes.demo.system.baiViet'),
        ignoreKeepAlive: false,
      },
      component: () => import('@/views/demo/system/bai-viet/index.vue'),
    },
    {
      path: 'sua-bai-viet',
      name: 'SuaBaiVietManagement',
      meta: {
        title: t('routes.demo.system.suaBaiViet'),
        ignoreKeepAlive: false,
      },
      component: () => import('@/views/demo/system/sua-bai-viet/index.vue'),
    },
    {
      path: 'hop-dong',
      name: 'HopDongManagement',
      meta: {
        title: t('routes.demo.system.hopDong'),
        ignoreKeepAlive: false,
      },
      component: () => import('@/views/demo/system/hop-dong/index.vue'),
    },
    {
      path: 'quang-cao',
      name: 'QuangCaoManagement',
      meta: {
        title: t('routes.demo.system.quangCao'),
        ignoreKeepAlive: false,
      },
      component: () => import('@/views/demo/system/quang-cao/index.vue'),
    },
    {
      path: 'nguoi-dung',
      name: 'NguoiDungManagement',
      meta: {
        title: t('routes.demo.system.nguoiDung'),
        ignoreKeepAlive: false,
      },
      component: () => import('@/views/demo/system/nguoi-dung/index.vue'),
    },
    {
      path: 'mien',
      name: 'MienManagement',
      meta: {
        title: t('routes.demo.system.mien'),
        ignoreKeepAlive: false,
      },
      component: () => import('@/views/demo/system/mien/index.vue'),

    }
  ],
};

export default system;
