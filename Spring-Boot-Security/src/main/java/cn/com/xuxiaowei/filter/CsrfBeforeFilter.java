/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.com.xuxiaowei.filter;

import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * CSRF 策略 运行前 Filter
 * <p>
 * 由于此 Cookie 无法读取，
 * 提交数据时在{@link CsrfFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}中验证 CSRF 时获取不到，
 * 获取方式为：<code>String actualToken = request.getHeader(csrfToken.getHeaderName());</code>，即：从 Headers 中获取，
 * 所以需要使用 {@link #doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}，
 * 将前端传来的 Cookie 中的 CSRF 放入 Headers 中
 * <p>
 * 注：上述报错红，原因为该方法不是公共的，并非错误
 *
 * @author xuxiaowei
 * @see CsrfFilter
 * @since 0.0.1
 */
public class CsrfBeforeFilter extends OncePerRequestFilter {

    private final CsrfTokenRepository tokenRepository;

    public CsrfBeforeFilter(CsrfTokenRepository csrfTokenRepository) {
        Assert.notNull(csrfTokenRepository, "csrfTokenRepository 不能为 null");
        this.tokenRepository = csrfTokenRepository;
    }

    /**
     * 提交数据时在{@link CsrfFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}中验证 CSRF 时获取不到，
     * 获取方式为：<code>String actualToken = request.getHeader(csrfToken.getHeaderName());</code>，即：从 Headers 中获取，
     * 所以需要使用此方法，
     * 将前端传来的 Cookie 中的 CSRF 放入 Headers 中
     * <p>
     * 注：上述、下面报错红，原因为该方法不是公共的，并非错误
     *
     * @see CsrfFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
     */
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        filterChain.doFilter(request, response);
    }

    /**
     * 自定义请求
     * <p>
     * 为了简化代码，此内部类仅重写了后面要使用的方法，有兴趣的同学可以尝试重写其他方法
     * <p>
     * 使用 static 节省资源
     *
     * @author xuxiaowei
     * @since 0.0.1
     */
    private static class CustomizeRequest extends HttpServletRequestWrapper {

        private Map<String, String> headerMap;

        /**
         * 构造一个包装给定请求的请求对象。
         *
         * @param request HttpServletRequest
         * @throws IllegalArgumentException 如果 request 为 null时，抛出此异常
         */
        private CustomizeRequest(HttpServletRequest request) {
            super(request);

            headerMap = new HashMap<>();

            // 将原始 Headers 放入新 Headers（Map）中
            Enumeration enumeration = request.getHeaderNames();
            while (enumeration.hasMoreElements()) {
                String name = (String) enumeration.nextElement();
                headerMap.put(name, request.getHeader(name));
            }
        }

        /**
         * 由于{@link CsrfFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}中使用了{@link HttpServletRequest#getHeader(String)}，
         * 所以此方法必须重写
         * <p>
         * 注：上述报错红，原因为该方法不是公共的，并非错误
         */
        @Override
        public String getHeader(String name) {
            return headerMap.get(name);
        }

        /**
         * 此方法用于添加 Header
         *
         * @param name  需要添加的 Header name
         * @param value 需要添加的 Header value
         */
        private void addHeader(String name, String value) {
            headerMap.put(name, value);
        }

    }

}
