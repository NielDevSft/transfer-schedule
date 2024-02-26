FROM node:alpine as builder

WORKDIR /app
COPY package.json yarn.lock ./
ENV CI=1
RUN yarn install --frozen-lockfile

COPY . .
RUN yarn build

# Deploy our Angular app to NGINX
FROM nginx:alpine

RUN mkdir /app
COPY --from=builder /app/dist /app
COPY nginx.conf /etc/nginx/nginx.conf