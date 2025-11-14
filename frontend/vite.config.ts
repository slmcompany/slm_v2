import { defineApplicationConfig } from '@vben/vite-config';

export default defineApplicationConfig({
  overrides: {
    optimizeDeps: {
      include: [
        'echarts/core',
        'echarts/charts',
        'echarts/components',
        'echarts/renderers',
        'qrcode',
        '@iconify/iconify',
        'ant-design-vue/es/locale/zh_CN',
        'ant-design-vue/es/locale/en_US',
        'vxe-table',
      ],
    },
    server: {
      proxy: {
        '/basic-api': {
          target: 'http://localhost:3000',
          changeOrigin: true,
          ws: true,
          rewrite: (path) => path.replace(new RegExp(`^/basic-api`), ''),
        },
        '/upload': {
          target: 'http://localhost:3300/upload',
          changeOrigin: true,
          ws: true,
          rewrite: (path) => path.replace(new RegExp(`^/upload`), ''),
        },
      },
      open: true,
      warmup: {
        clientFiles: ['./index.html', './src/{views,components}/*'],
      },
    },
    // THÊM PHẦN NÀY
    build: {
      // Tắt strict mode
      rollupOptions: {
        // Tắt validate external
        external: [],
        output: {
          // Tắt strict checks
          exports: 'auto',
          interop: 'auto',
        },
        // Shimming cho missing default exports
        shimMissingExports: true,
      },
      // Cho phép commonjs interop
      commonjsOptions: {
        transformMixedEsModules: true,
        strictRequires: false,
        defaultIsModuleExports: true,
        esmExternals: true,
      },
    },
  },
});