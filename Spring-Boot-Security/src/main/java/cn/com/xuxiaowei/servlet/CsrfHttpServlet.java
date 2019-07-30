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
package cn.com.xuxiaowei.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CSRF HttpServlet
 *
 * @author xuxiaowei
 * @since 0.0.1
 */
public class CsrfHttpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 响应类型
        resp.setContentType("text/json;charset=UTF-8");

        // 响应内容
        resp.getWriter().println("");

        // 响应状态
        resp.setStatus(HttpServletResponse.SC_OK);

        // 强制将缓冲区中的任何内容写入客户端。
        // 对此方法的调用会自动提交响应，这意味着将写入状态代码和标头。
        resp.flushBuffer();
    }

}
