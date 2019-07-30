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
package cn.com.xuxiaowei.configuration;

import cn.com.xuxiaowei.filter.CsrfBeforeFilter;
import cn.com.xuxiaowei.handler.LoginAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.LazyCsrfTokenRepository;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Spring Security 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
public class WebSecurityConfigurerAdapterConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * CSRF Cookie Name
     */
    public static final String CSRF_COOKIE_NAME = "demo-csrf-cookie";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                // 配置密码编辑器
                .passwordEncoder(new CustomizePasswordEncoder())
                // 设置用户名和密码以及权限
                .withUser("xuxiaowei").password("123").roles("USER");

    }

    /**
     * 静态资源不拦截（当配置域名需要权限时）
     */
    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/js/**");
        web.ignoring().antMatchers("/img/**");
        web.ignoring().antMatchers("/favicon.ico");

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login.do")
                .failureForwardUrl("/login/fail.do")
                .defaultSuccessUrl("/login/success.do")
                .permitAll();

        http.logout()
                .logoutUrl("/logout.do")
                .logoutSuccessUrl("/logout/success.do")
                .permitAll();

        http.rememberMe()
                .tokenValiditySeconds(60 * 60)
                .rememberMeParameter("remember-me")
                .key("hjkkKJJHJ*(&&(*980");

        // 测试权限
        // 至少要设置一个，防止报错
        http.authorizeRequests().antMatchers("/test/user.do").hasRole("USER");

        // CSRF 策略
        // 默认为懒加载
        http.csrf().csrfTokenRepository(cookieCsrfTokenRepository());

        // CSRF 策略 运行前 Filter
        http.addFilterBefore(new CsrfBeforeFilter(cookieCsrfTokenRepository()), CsrfFilter.class);

        // 用于处理成功用户身份验证的策略。
        http.formLogin().successHandler(loginAuthenticationSuccessHandler());

    }

    /**
     * CSRF 策略
     * <p>
     * 默认为懒加载 {@link LazyCsrfTokenRepository}，会出现浏览器首次打开时，提交不了数据
     * <p>
     * 此 Cookie 默认为 HttpOnly = true（不可读取），
     * 请勿修改为 HttpOnly = false（可读取），否则安全性无法保障
     * <p>
     * 由于此 Cookie 无法读取，
     * 提交数据时在{@link CsrfFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}中验证 CSRF 时获取不到，
     * 获取方式为：<code>String actualToken = request.getHeader(csrfToken.getHeaderName());</code>，即：从 Headers 中获取，
     * 所以需要使用 {@link CsrfBeforeFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}，
     * 将前端传来的 Cookie 中的 CSRF 放入 Headers 中
     *
     * <code>cookieCsrfTokenRepository.setCookieHttpOnly(false);</code>
     * <p>
     * 注：上述报错红，原因为该方法不是公共的，并非错误
     */
    private CookieCsrfTokenRepository cookieCsrfTokenRepository() {
        CookieCsrfTokenRepository cookieCsrfTokenRepository = new CookieCsrfTokenRepository();
        cookieCsrfTokenRepository.setCookieName(CSRF_COOKIE_NAME);
        return cookieCsrfTokenRepository;
    }

    /**
     * 用于处理成功用户身份验证的策略。
     */
    @Bean
    LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler() {
        return new LoginAuthenticationSuccessHandler();
    }

}
