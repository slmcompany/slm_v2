# node 构建
FROM node:18-alpine as build-stage
MAINTAINER Adoin 'adoin@qq.com'
WORKDIR /app
COPY . ./
RUN npm config set registry https://registry.npmmirror.com
ENV NODE_OPTIONS=--max-old-space-size=16384
RUN npm install pnpm -g && \
    pnpm install --no-frozen-lockfile && \
    pnpm build:docker
RUN echo "🎉 编 🎉 译 🎉 成 🎉 功 🎉"

# Stage cuối chỉ giữ dist
FROM node:18-alpine as production-stage
WORKDIR /app
COPY --from=build-stage /app/dist ./dist

# Thay thế biến môi trường khi container start
CMD sh -c "sed -i 's|__vg_base_url|${VG_BASE_URL}|g' /app/dist/assets/entry/index-*.js /app/dist/_app.config.js 2>/dev/null || true && tail -f /dev/null"