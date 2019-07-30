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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * 登录成功后重定向到此方法，GET
     */
    @RequestMapping(value = "/success.do")
    public Map<String, Object> success(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("code", 0);
        map.put("msg", "登录成功");
        return map;
    }

}
