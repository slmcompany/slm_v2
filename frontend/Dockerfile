FROM node:18-alpine as build-stage
WORKDIR /app
COPY . ./
RUN npm config set registry https://registry.npmmirror.com
ENV NODE_OPTIONS=--max-old-space-size=16384

RUN npm install pnpm -g && \
    pnpm install --no-frozen-lockfile && \
    pnpm build

FROM nginx:1.23.3-alpine as production-stage
WORKDIR /usr/share/nginx/html

# Copy dist files
COPY --from=build-stage /app/dist ./

# Copy nginx.conf vào đúng vị trí (nginx.conf chính)
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]