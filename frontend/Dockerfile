# node 构建
FROM node:18-alpine as build-stage
MAINTAINER Adoin 'adoin@qq.com'
WORKDIR /app
COPY . ./
RUN npm config set registry https://registry.npmmirror.com
ENV NODE_OPTIONS=--max-old-space-size=16384
RUN npm install pnpm -g && \
    pnpm install --no-frozen-lockfile && \
    cross-env VITE_USE_MOCK=true pnpm build
RUN echo "🎉 编 🎉 译 🎉 成 🎉 功 🎉"

# Stage production - chỉ giữ dist
FROM node:18-alpine as production-stage
WORKDIR /output
COPY --from=build-stage /app/dist ./

# Script để copy và thay thế env variables
CMD sh -c '\
    if [ -d /shared ]; then \
        echo "Copying dist to shared volume..."; \
        cp -r /output/* /shared/; \
        sed -i "s|__vg_base_url|${VG_BASE_URL:-http://localhost}|g" /shared/assets/entry/index-*.js /shared/_app.config.js 2>/dev/null || true; \
        echo "Frontend files ready!"; \
    fi; \
    tail -f /dev/null'