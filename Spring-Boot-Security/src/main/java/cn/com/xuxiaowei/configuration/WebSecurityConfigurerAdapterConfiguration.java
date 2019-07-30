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

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security 配置
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@Configuration
public class WebSecurityConfigurerAdapterConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication().withUser("xuxiaowwei").password("123").roles("USER");

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

    }

}
