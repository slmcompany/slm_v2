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
        'vxe-table', // Thêm dòng này
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
    // Thêm build config
    build: {
      rollupOptions: {
        onwarn(warning, warn) {
          // Bỏ qua warning về missing exports từ vxe-table
          if (warning.code === 'MISSING_EXPORT' && warning.message.includes('vxe-table')) {
            return;
          }
          if (warning.code === 'UNRESOLVED_IMPORT' && warning.message.includes('vxe-table')) {
            return;
          }
          warn(warning);
        },
      },
      // Cho phép commonjs interop
      commonjsOptions: {
        transformMixedEsModules: true,
        strictRequires: false,
      },
    },
    // Thêm resolve config
    resolve: {
      extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue'],
    },
  },
});