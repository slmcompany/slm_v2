// vite.config.ts
import { defineApplicationConfig } from "file:///C:/Users/admin/Documents/GitHub/test/slm_v2/frontend/internal/vite-config/dist/index.mjs";
var vite_config_default = defineApplicationConfig({
  overrides: {
    optimizeDeps: {
      include: [
        "echarts/core",
        "echarts/charts",
        "echarts/components",
        "echarts/renderers",
        "qrcode",
        "@iconify/iconify",
        "ant-design-vue/es/locale/zh_CN",
        "ant-design-vue/es/locale/en_US",
        "vxe-table"
      ]
    },
    server: {
      proxy: {
        "/basic-api": {
          target: "http://localhost:3000",
          changeOrigin: true,
          ws: true,
          rewrite: (path) => path.replace(new RegExp(`^/basic-api`), "")
        },
        "/upload": {
          target: "http://localhost:3300/upload",
          changeOrigin: true,
          ws: true,
          rewrite: (path) => path.replace(new RegExp(`^/upload`), "")
        }
      },
      open: true,
      warmup: {
        clientFiles: ["./index.html", "./src/{views,components}/*"]
      }
    },
    // THÊM PHẦN NÀY
    build: {
      // Tắt strict mode
      rollupOptions: {
        // Tắt validate external
        external: [],
        output: {
          // Tắt strict checks
          exports: "auto",
          interop: "auto"
        },
        // Shimming cho missing default exports
        shimMissingExports: true
      },
      // Cho phép commonjs interop
      commonjsOptions: {
        transformMixedEsModules: true,
        strictRequires: false,
        defaultIsModuleExports: true,
        esmExternals: true
      }
    }
  }
});
export {
  vite_config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcudHMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJDOlxcXFxVc2Vyc1xcXFxhZG1pblxcXFxEb2N1bWVudHNcXFxcR2l0SHViXFxcXHRlc3RcXFxcc2xtX3YyXFxcXGZyb250ZW5kXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ZpbGVuYW1lID0gXCJDOlxcXFxVc2Vyc1xcXFxhZG1pblxcXFxEb2N1bWVudHNcXFxcR2l0SHViXFxcXHRlc3RcXFxcc2xtX3YyXFxcXGZyb250ZW5kXFxcXHZpdGUuY29uZmlnLnRzXCI7Y29uc3QgX192aXRlX2luamVjdGVkX29yaWdpbmFsX2ltcG9ydF9tZXRhX3VybCA9IFwiZmlsZTovLy9DOi9Vc2Vycy9hZG1pbi9Eb2N1bWVudHMvR2l0SHViL3Rlc3Qvc2xtX3YyL2Zyb250ZW5kL3ZpdGUuY29uZmlnLnRzXCI7aW1wb3J0IHsgZGVmaW5lQXBwbGljYXRpb25Db25maWcgfSBmcm9tICdAdmJlbi92aXRlLWNvbmZpZyc7XG5cbmV4cG9ydCBkZWZhdWx0IGRlZmluZUFwcGxpY2F0aW9uQ29uZmlnKHtcbiAgb3ZlcnJpZGVzOiB7XG4gICAgb3B0aW1pemVEZXBzOiB7XG4gICAgICBpbmNsdWRlOiBbXG4gICAgICAgICdlY2hhcnRzL2NvcmUnLFxuICAgICAgICAnZWNoYXJ0cy9jaGFydHMnLFxuICAgICAgICAnZWNoYXJ0cy9jb21wb25lbnRzJyxcbiAgICAgICAgJ2VjaGFydHMvcmVuZGVyZXJzJyxcbiAgICAgICAgJ3FyY29kZScsXG4gICAgICAgICdAaWNvbmlmeS9pY29uaWZ5JyxcbiAgICAgICAgJ2FudC1kZXNpZ24tdnVlL2VzL2xvY2FsZS96aF9DTicsXG4gICAgICAgICdhbnQtZGVzaWduLXZ1ZS9lcy9sb2NhbGUvZW5fVVMnLFxuICAgICAgICAndnhlLXRhYmxlJyxcbiAgICAgIF0sXG4gICAgfSxcbiAgICBzZXJ2ZXI6IHtcbiAgICAgIHByb3h5OiB7XG4gICAgICAgICcvYmFzaWMtYXBpJzoge1xuICAgICAgICAgIHRhcmdldDogJ2h0dHA6Ly9sb2NhbGhvc3Q6MzAwMCcsXG4gICAgICAgICAgY2hhbmdlT3JpZ2luOiB0cnVlLFxuICAgICAgICAgIHdzOiB0cnVlLFxuICAgICAgICAgIHJld3JpdGU6IChwYXRoKSA9PiBwYXRoLnJlcGxhY2UobmV3IFJlZ0V4cChgXi9iYXNpYy1hcGlgKSwgJycpLFxuICAgICAgICB9LFxuICAgICAgICAnL3VwbG9hZCc6IHtcbiAgICAgICAgICB0YXJnZXQ6ICdodHRwOi8vbG9jYWxob3N0OjMzMDAvdXBsb2FkJyxcbiAgICAgICAgICBjaGFuZ2VPcmlnaW46IHRydWUsXG4gICAgICAgICAgd3M6IHRydWUsXG4gICAgICAgICAgcmV3cml0ZTogKHBhdGgpID0+IHBhdGgucmVwbGFjZShuZXcgUmVnRXhwKGBeL3VwbG9hZGApLCAnJyksXG4gICAgICAgIH0sXG4gICAgICB9LFxuICAgICAgb3BlbjogdHJ1ZSxcbiAgICAgIHdhcm11cDoge1xuICAgICAgICBjbGllbnRGaWxlczogWycuL2luZGV4Lmh0bWwnLCAnLi9zcmMve3ZpZXdzLGNvbXBvbmVudHN9LyonXSxcbiAgICAgIH0sXG4gICAgfSxcbiAgICAvLyBUSFx1MDBDQU0gUEhcdTFFQTZOIE5cdTAwQzBZXG4gICAgYnVpbGQ6IHtcbiAgICAgIC8vIFRcdTFFQUZ0IHN0cmljdCBtb2RlXG4gICAgICByb2xsdXBPcHRpb25zOiB7XG4gICAgICAgIC8vIFRcdTFFQUZ0IHZhbGlkYXRlIGV4dGVybmFsXG4gICAgICAgIGV4dGVybmFsOiBbXSxcbiAgICAgICAgb3V0cHV0OiB7XG4gICAgICAgICAgLy8gVFx1MUVBRnQgc3RyaWN0IGNoZWNrc1xuICAgICAgICAgIGV4cG9ydHM6ICdhdXRvJyxcbiAgICAgICAgICBpbnRlcm9wOiAnYXV0bycsXG4gICAgICAgIH0sXG4gICAgICAgIC8vIFNoaW1taW5nIGNobyBtaXNzaW5nIGRlZmF1bHQgZXhwb3J0c1xuICAgICAgICBzaGltTWlzc2luZ0V4cG9ydHM6IHRydWUsXG4gICAgICB9LFxuICAgICAgLy8gQ2hvIHBoXHUwMEU5cCBjb21tb25qcyBpbnRlcm9wXG4gICAgICBjb21tb25qc09wdGlvbnM6IHtcbiAgICAgICAgdHJhbnNmb3JtTWl4ZWRFc01vZHVsZXM6IHRydWUsXG4gICAgICAgIHN0cmljdFJlcXVpcmVzOiBmYWxzZSxcbiAgICAgICAgZGVmYXVsdElzTW9kdWxlRXhwb3J0czogdHJ1ZSxcbiAgICAgICAgZXNtRXh0ZXJuYWxzOiB0cnVlLFxuICAgICAgfSxcbiAgICB9LFxuICB9LFxufSk7Il0sCiAgIm1hcHBpbmdzIjogIjtBQUE4VixTQUFTLCtCQUErQjtBQUV0WSxJQUFPLHNCQUFRLHdCQUF3QjtBQUFBLEVBQ3JDLFdBQVc7QUFBQSxJQUNULGNBQWM7QUFBQSxNQUNaLFNBQVM7QUFBQSxRQUNQO0FBQUEsUUFDQTtBQUFBLFFBQ0E7QUFBQSxRQUNBO0FBQUEsUUFDQTtBQUFBLFFBQ0E7QUFBQSxRQUNBO0FBQUEsUUFDQTtBQUFBLFFBQ0E7QUFBQSxNQUNGO0FBQUEsSUFDRjtBQUFBLElBQ0EsUUFBUTtBQUFBLE1BQ04sT0FBTztBQUFBLFFBQ0wsY0FBYztBQUFBLFVBQ1osUUFBUTtBQUFBLFVBQ1IsY0FBYztBQUFBLFVBQ2QsSUFBSTtBQUFBLFVBQ0osU0FBUyxDQUFDLFNBQVMsS0FBSyxRQUFRLElBQUksT0FBTyxhQUFhLEdBQUcsRUFBRTtBQUFBLFFBQy9EO0FBQUEsUUFDQSxXQUFXO0FBQUEsVUFDVCxRQUFRO0FBQUEsVUFDUixjQUFjO0FBQUEsVUFDZCxJQUFJO0FBQUEsVUFDSixTQUFTLENBQUMsU0FBUyxLQUFLLFFBQVEsSUFBSSxPQUFPLFVBQVUsR0FBRyxFQUFFO0FBQUEsUUFDNUQ7QUFBQSxNQUNGO0FBQUEsTUFDQSxNQUFNO0FBQUEsTUFDTixRQUFRO0FBQUEsUUFDTixhQUFhLENBQUMsZ0JBQWdCLDRCQUE0QjtBQUFBLE1BQzVEO0FBQUEsSUFDRjtBQUFBO0FBQUEsSUFFQSxPQUFPO0FBQUE7QUFBQSxNQUVMLGVBQWU7QUFBQTtBQUFBLFFBRWIsVUFBVSxDQUFDO0FBQUEsUUFDWCxRQUFRO0FBQUE7QUFBQSxVQUVOLFNBQVM7QUFBQSxVQUNULFNBQVM7QUFBQSxRQUNYO0FBQUE7QUFBQSxRQUVBLG9CQUFvQjtBQUFBLE1BQ3RCO0FBQUE7QUFBQSxNQUVBLGlCQUFpQjtBQUFBLFFBQ2YseUJBQXlCO0FBQUEsUUFDekIsZ0JBQWdCO0FBQUEsUUFDaEIsd0JBQXdCO0FBQUEsUUFDeEIsY0FBYztBQUFBLE1BQ2hCO0FBQUEsSUFDRjtBQUFBLEVBQ0Y7QUFDRixDQUFDOyIsCiAgIm5hbWVzIjogW10KfQo=
