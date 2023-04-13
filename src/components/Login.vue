<template>
    <div>
      <h2>登录</h2>
      <form @submit.prevent="handleSubmit">
        <div>
          <label for="username">用户名：</label>
          <input type="text" id="username" v-model="username" />
        </div>
        <div>
          <label for="password">密码：</label>
          <input type="password" id="password" v-model="password" />
        </div>
        <button type="submit">登录</button>
      </form>
      <p>还没有账号？<a href="#" @click="handleChangeView('register')">立即注册</a></p>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        username: '',
        password: '',
      };
    },
    methods: {
      handleSubmit() {
        // 处理登录逻辑
      },
      handleChangeView(view) {
        this.$emit('changeView', view);
      },
    },
  };
  </script>