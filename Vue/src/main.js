import Vue from 'vue'
import App from './App.vue'
import router from './router' // 导入路由配置文件
import store from './store' // 导入储存配置文件

Vue.config.productionTip = false;

router.beforeEach((to, from, next) => {
    /* 根据路由设置 title，路由发生改变修改页面的 title */
    if (to.meta.title) {
        document.title = to.meta.title
    }
    next();
});

new Vue({
    router, // 启用路由配置文件
    store, // 启用储存配置文件
    render: h => h(App),
}).$mount('#app');
