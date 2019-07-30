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
package cn.com.xuxiaowei.controller;

import cn.com.xuxiaowei.handler.LoginAuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Login RestController
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
@RestController
@RequestMapping("/login")
public class LoginRestController {

    /**
     * 登录失败
     */
    @RequestMapping(value = "/fail.do", method = RequestMethod.POST)
    public Map<String, Object> fail(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("code", 1);
        map.put("msg", "用户名或密码错误！");
        return map;
    }

    /**
     * 登录成功
     * <p>
     * 登录成功后重定向到此方法为GET
     * <p>
     * 使用{@link LoginAuthenticationSuccessHandler}，进行登陆成功后请求转发，
     * 如果有跨域，登录成功后西响应时，可在此类中处理
     * <p>
     * 使用{@link LoginAuthenticationSuccessHandler}登录成功后进行请求转发，可在此方法中获取到用户提交的数据，
     * 如： username、remember-me等（不要在此接收 password）
     *
     * @param rememberMe 记住我，需要在 {@link LoginAuthenticationSuccessHandler} 中使用请求转发才能获得
     */
    @RequestMapping(value = "/success.do", method = RequestMethod.POST)
    public Map<String, Object> success(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam("remember-me") Boolean rememberMe) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("code", 0);
        map.put("msg", "登录成功");
        map.put("rememberMe", rememberMe);
        return map;
    }

}
