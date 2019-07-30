<template>
    <div>
        <h2>About</h2>
        <div>
            <router-link to="/login">
                <el-button>Login</el-button>
            </router-link>
        </div>
    </div>
</template>

<script>
    export default {
        name: "About",
        /**
         * 创建期间的生命周期函数：
         * 实例刚在内存中被创建出来，此时，还没有初始化好 data 和 methods 属性
         */
        beforeCreate: function () {

            this.$axios("https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.js").then(function (resource) {
                // eslint-disable-next-line no-console
                console.log("测试 axios 成功", resource);
            }).catch(function (resource) {
                // eslint-disable-next-line no-console
                console.log("测试 axios 响应失败", Promise.reject(resource));
            });

            this.$http.get("https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.js").then(function (resource) {
                // eslint-disable-next-line no-console
                console.log("测试 VueResource 成功", resource);
            }, function (resource) {
                // eslint-disable-next-line no-console
                console.log("测试 VueResource 响应失败", Promise.reject(resource));
            });

            this.$cookies.set("new", new Date().toString());
            // eslint-disable-next-line no-console
            console.log("测试 Cookie", this.$cookies.get("new"));

        },
    }
</script>

<style scoped>

</style>