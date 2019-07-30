/**
 * 储存配置文件
 */
import Vue from 'vue' // Vue，用于启用储存
import Vuex from 'vuex' // 导入储存依赖
import localStorageState from 'vuex-persistedstate' // 导入持久化 Vuex 依赖，本地储存
import sessionStorageState from 'vuex-persistedstate' // 导入持久化 Vuex 依赖，会话储存

Vue.use(Vuex); // Vue 启用依赖

export default new Vuex.Store({
    state: {
        isSessionLogin: null, // 是否会话登录
        isLocalLogin: null, // 是否本地登录
    },
    mutations: {
        /**
         * 改变 state 中的 isLogin
         *
         * @param state
         * @param payload
         */
        changeLogin(state, payload) {
            state.isSessionLogin = payload.isSessionLogin;
            state.isLocalLogin = payload.isLocalLogin;
        },
    },
    actions: {},
    plugins: [
        /**
         * 本地储存
         */
        localStorageState({
            storage: window.localStorage, // 本地储存
            reducer(val) {
                return {
                    // 只储存 state 中的 isLocalLogin
                    isLocalLogin: val.isLocalLogin
                }
            }
        }),
        /**
         * 会话储存
         */
        sessionStorageState({
            storage: window.sessionStorage, // 会话储存
            reducer(val) {
                return {
                    // 只储存 state 中的 isSessionLogin
                    isSessionLogin: val.isSessionLogin
                }
            }
        }),
    ],
})
