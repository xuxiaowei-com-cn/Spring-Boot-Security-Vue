import Vue from 'vue'
import App from './App.vue'
import router from './router' // 导入路由配置文件
import store from './store' // 导入储存配置文件
import Axios from 'axios' // 导入 Axios 配置文件
import '@/plugins/element' // 导入 element-ui 配置文件
import VueResource from 'vue-resource' // 导入 VueResource 依赖
import VueCookies from 'vue-cookies' // 导入 VueCookies 依赖

Vue.use(VueResource); // 启用 VueResource
Vue.use(VueCookies); // 启用 VueCookies

Vue.config.productionTip = false;

Vue.prototype.$axios = Axios; // 启用 Axios 依赖

router.beforeEach((to, from, next) => {

    /**
     * 获取 CSRF Cookie
     */
    Axios.get("/_csrf.do").then(function (resource) {
        // eslint-disable-next-line no-console
        console.log("获取 CSRF 成功", resource);
        // eslint-disable-next-line no-console
        console.log("CSRF Cookie Headers", resource.config.headers);
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
        if (store.state.isLogin) { // 判断进入授权页面时是否登录
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
