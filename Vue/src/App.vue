<template>
    <div id="app">

        <!--<img alt="Vue logo" src="./assets/logo.png">-->
        <!--<HelloWorld msg="Welcome to Your Vue.js App"/>-->

        <router-view/><!-- 路由显示 -->

        <Footer/><!-- 脚部 -->

    </div>
</template>

<script>
    // import HelloWorld from './components/HelloWorld.vue'
    import Footer from '@/components/Footer'

    export default {
        name: 'app',
        components: {
            // HelloWorld,
            Footer,
        }
    }
</script>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        margin-top: 60px;
    }

    /* Message 消息提示 宽度 */
    .message-min-w {
        min-width: 300px !important;
    }

</style>
