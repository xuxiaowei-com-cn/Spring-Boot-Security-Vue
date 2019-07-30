<template>
    <el-container>
        <el-header>Login</el-header>

        <el-main>
            <el-form ref="form" :model="form" class="form-login">

                <el-form-item prop="username">
                    <el-input placeholder="请输入用户名" prefix-icon="el-icon-user" v-model="form.username" ref="username"
                              maxlength="11"></el-input>
                </el-form-item>

                <el-form-item prop="password">
                    <el-input placeholder="请输入密码" prefix-icon="el-icon-lock" v-model="form.password" show-password
                              maxlength="16"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-checkbox class="rememberMe" v-model="form.rememberMe">记住我</el-checkbox>
                </el-form-item>

                <el-form-item>
                    <el-button class="login" type="primary" @click="login">登录</el-button>
                </el-form-item>

            </el-form>
        </el-main>

        <el-footer></el-footer>
    </el-container>
</template>

<script>
    export default {
        name: "Login",
        data() {
            return {
                form: {
                    username: '',
                    password: '',
                    rememberMe: false,
                },
            }
        },
        methods: {

            /**
             * 登录
             */
            login: function () {
                // let that = this;
                let form = this.form;
                // eslint-disable-next-line no-console
                console.log(form);

                this.$axios.post("/login.do").then(function (resource) {
                    // eslint-disable-next-line no-console
                    console.log("登录结果", resource);
                }).catch(function (resource) {
                    // eslint-disable-next-line no-console
                    console.log("登录异常", Promise.reject(resource));
                })

            },

        },
    }
</script>

<style scoped>

    .form-login {
        max-width: 300px;
        margin: auto auto;
    }

    /* 记住我 */
    .rememberMe {
        float: left;
    }

    /* 登录 */
    .login {
        width: 100%;
    }

</style>