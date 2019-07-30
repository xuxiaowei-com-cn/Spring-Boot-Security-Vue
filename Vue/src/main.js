import Vue from 'vue'
import App from './App.vue'
import router from './router' // 导入路由配置文件

Vue.config.productionTip = false;

new Vue({
    router, // 启用路由配置文件
    render: h => h(App),
}).$mount('#app');
