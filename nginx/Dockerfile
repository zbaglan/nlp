FROM nexus.sberbank.kz:5001/nginx:1.19.4-alpine

COPY nginx.conf /etc/nginx/nginx.conf
RUN rm -rf /etc/nginx/conf.d/default.conf
COPY ./sites-enabled/local.conf /etc/nginx/templates/default.conf.template
COPY ./nginxconfig.io/general.conf /etc/nginx/nginxconfig.io/general.conf
COPY ./nginxconfig.io/proxy.conf /etc/nginx/nginxconfig.io/proxy.conf
