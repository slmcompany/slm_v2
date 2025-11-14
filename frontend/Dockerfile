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

# nginx 部署
FROM nginx:1.23.3-alpine as production-stage
# Sửa đường dẫn copy - bỏ /dist ở cuối
COPY --from=build-stage /app/dist /usr/share/nginx/html
COPY --from=build-stage /app/nginx.conf /etc/nginx/nginx.conf
EXPOSE 80

CMD sed -i "s|__vg_base_url|$VG_BASE_URL|g" /usr/share/nginx/html/assets/entry/index-*.js /usr/share/nginx/html/_app.config.js && \
    nginx -g 'daemon off;'
RUN echo "🎉 架 🎉 设 🎉 成 🎉 功 🎉"