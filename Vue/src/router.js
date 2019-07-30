/**
 * 路由配置文件
 */
import Vue from 'vue' // Vue，用于启用路由
import Router from 'vue-router' // 导入路由依赖
import Home from './views/Home.vue' // 页面
import Login from './views/Login.vue'

Vue.use(Router); // Vue 启用路由

export default new Router({ // 创建默认路由
    mode: 'history', // H5 history 路由
    routes: [ // 路由配置
        {
            path: '/', // 路径
            name: 'home', // title
            component: Home, // Vue 页面
            meta: {
                title: 'Home',
                requireAuth: true, // 页面需要授权
            }
        }, {
            path: '/login',
            name: 'login',
            component: Login,
            meta: {
                title: 'Login'
            }
        }, {
            path: '/about',
            name: 'about',
            // route level code-splitting
            // this generates a separate chunk (about.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            // 路由级代码分裂
            // 为此路由生成一个单独的块 (about.[hash].js)
            // 当访问路线时，它是延迟加载的。
            component: () => import(/* webpack 块名称: "about" */ './views/About.vue'),
            meta: {
                title: 'About'
            }
        },
    ]
})
