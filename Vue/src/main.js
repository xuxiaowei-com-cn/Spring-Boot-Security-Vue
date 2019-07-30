import Vue from 'vue'
import App from './App.vue'
import router from './router' // 导入路由配置文件
import store from './store' // 导入储存配置文件
import Axios from 'axios' // 导入 Axios 配置文件
import '@/plugins/element' // 导入 element-ui 配置文件
import VueResource from 'vue-resource' // 导入 VueResource 依赖
import VueCookies from 'vue-cookies' // 导入 VueCookies 依赖
import JsEncrypt from 'jsencrypt/bin/jsencrypt.min' // 导入 JsEncrypt 依赖

Vue.use(VueResource); // 启用 VueResource
Vue.use(VueCookies); // 启用 VueCookies

Vue.config.productionTip = false;

Vue.prototype.$axios = Axios; // 启用 Axios 依赖
Vue.prototype.$jsEncrypt = JsEncrypt; // 启用 JsEncrypt 依赖

router.beforeEach((to, from, next) => {

    /**
     * 获取 CSRF Cookie
     */
    Axios.get("/_csrf.do").then(function (resource) {
        // eslint-disable-next-line no-console
        console.log("获取 CSRF 成功", resource);
        // eslint-disable-next-line no-console
        console.log("CSRF Cookie Headers", resource.config.headers);
        // eslint-disable-next-line no-console
        console.log("如果浏览器的 Cookie 中（并非上面 Headers 中）没有 CSRF Cookie，说明 API CSRF 策略未配置成功");
        // eslint-disable-next-line no-console
        console.log("如果上面 Headers 中有 CSRF Cookie，说明 API CSRF 配置了 HttpOnly = false，安全性无法保障")
    }).catch(function (resource) {
        // eslint-disable-next-line no-console
        console.log("获取 CSRF 异常", Promise.reject(resource));
    });

    /* 根据路由设置 title，路由发生改变修改页面的 title */
    if (to.meta.title) {
        document.title = to.meta.title
    }

    /* 路由授权拦截 */
    if (to.meta.requireAuth) { // 判断页面是否需要授权

        /**
         * 判断进入授权页面时是否已登录
         * 如果已登录，应该进行适时鉴权
         *
         * 如：登录是否过期，权限是否改变，注意鉴权的时机
         */
        if (store.state.isLocalLogin) { // 本地储存
            next(); // 如果已登录，直接进入
        } else if (store.state.isSessionLogin) { // 会话储存
            next(); // 如果已登录，直接进入
        } else {
            next({
                path: '/login', // 入授权页面时未登录，跳转到登录页面
                query: {redirect: to.fullPath}  // 将跳转的路由path作为参数，登录成功后跳转到该路由
            })
        }
    } else {
        next();
    }
});

new Vue({
    router, // 启用路由配置文件
    store, // 启用储存配置文件
    render: h => h(App),
}).$mount('#app');
