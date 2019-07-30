# Nginx 配置

## 配置 server_names_hash_bucket_size

### 运行 Nginx 时，可能会报以下错误

    - 2019/07/30 19:45:22 [emerg] 10056#6796: could not build server_names_hash, you should increase server_names_hash_bucket_size: 32

### 在 nginx.conf 中 http 的 default_type  application/octet-stream; 下面设置：
    - server_names_hash_bucket_size 64;
- 推荐设置为 64（设置为 32可能会依然报错）

## 配置 server http

~~~
	# http://demo.xuxiaowei.com.cn 配置
	server {
		listen       80;
		server_name  demo.xuxiaowei.com.cn;
 
		# http 重定向到 https
		# rewrite ^(.*)     https://$host$1 permanent;
 
		# Vue
		location / {
            proxy_pass                                  http://127.0.0.1:8001;
            proxy_set_header        Host                $host;
            proxy_set_header        X-Real-IP           $remote_addr;
            proxy_set_header        X-Forwarded-For     $proxy_add_x_forwarded_for;
            proxy_http_version                          1.1;
            proxy_set_header        Upgrade             $http_upgrade;
            proxy_set_header        Connection          'upgrade';
		}
	
		# API
		# 任何以 .do 结尾的请求，都是访问 API
		location ~ \.(do)$ {
            proxy_pass                                  http://127.0.0.1:8002;
            proxy_set_header        Host                $host;
            proxy_set_header        X-Real-IP           $remote_addr;
            proxy_set_header        X-Forwarded-For     $proxy_add_x_forwarded_for;
            proxy_http_version                          1.1;
            proxy_set_header        Upgrade             $http_upgrade;
            proxy_set_header        Connection          'upgrade';
		}
	
		error_page   500 502 503 504  /50x.html;
		location = /50x.html {
			root   html;
		}
	}
~~~

## 配置 server https
~~~
    # https://demo.xuxiaowei.com.cn 配置
    server {
		listen 443 ssl;
		server_name demo.xuxiaowei.com.cn;
 
		ssl_certificate cert/nginx/demo.xuxiaowei.com.cn.pem;
		ssl_certificate_key cert/nginx/demo.xuxiaowei.com.cn.key;
		
		ssl_session_timeout 5m;
		ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:ECDHE:ECDH:AES:HIGH:!NULL:!aNULL:!MD5:!ADH:!RC4;
		ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
		ssl_prefer_server_ciphers on;

		location / {
            proxy_pass                                  https://127.0.0.1:4001;
            proxy_set_header        Host                $host;
            proxy_set_header        X-Real-IP           $remote_addr;
            proxy_set_header        X-Forwarded-For     $proxy_add_x_forwarded_for;
            proxy_http_version                          1.1;
            proxy_set_header        Upgrade             $http_upgrade;
            proxy_set_header        Connection          'upgrade';
		}

		# API
		# 任何以 .do 结尾的请求，都是访问 API
		location ~ \.(do)$ {
            proxy_pass                                  http://127.0.0.1:4002;
            proxy_set_header        Host                $host;
            proxy_set_header        X-Real-IP           $remote_addr;
            proxy_set_header        X-Forwarded-For     $proxy_add_x_forwarded_for;
            proxy_http_version                          1.1;
            proxy_set_header        Upgrade             $http_upgrade;
            proxy_set_header        Connection          'upgrade';
		}
	}
~~~
