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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

}
