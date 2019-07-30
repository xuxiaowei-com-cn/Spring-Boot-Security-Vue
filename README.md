# Spring-Boot-Security-Vue
 Spring Boot Security Vue 前后端分离

## Vue

- 提交数据时携带 Cookie

## Spring Boot Security CSRF

- 访问任意 API 均能获取到 SCRF Cookie

- SCRF Cookie HttpOnly = true，不可读取

- 接收数据时，使用 CsrfFilter.class FilterBefore，在 CsrfFilter 接收数据前，将 HttpServletRequest 中的 CSRF Cookie 放入 Header 中，
    使得 CsrfFilter.class 能正常接收到 CSRF Header