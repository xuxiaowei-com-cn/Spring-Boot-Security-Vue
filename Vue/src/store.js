/**
 * 储存配置文件
 */
import Vue from 'vue' // Vue，用于启用储存
import Vuex from 'vuex' // 导入储存依赖
import createPersistedState from 'vuex-persistedstate' // 导入持久化 Vuex 依赖

Vue.use(Vuex); // Vue 启用依赖

export default new Vuex.Store({
    state: {
        isLogin: null, // 是否登录
    },
    mutations: {
        /**
         * 改变 state 中的 isLogin
         *
         * @param state
         * @param payload
         */
        changeLogin(state, payload) {
            state.isLogin = payload.isLogin
        },
    },
    actions: {},
    plugins: [createPersistedState({
        // storage: window.sessionStorage, // 
        storage: window.localStorage, // 本地储存
        reducer(val) {
            return {
                // 只储存 state 中的 isLogin
                isLogin: val.isLogin
            }
        }
    })],
})
