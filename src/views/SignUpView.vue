<template>
  <div>
    <h2>注册</h2>
    <form @submit.prevent="handleSubmit">
      <div>
        <label for="username">用户名：</label>
        <input type="text" id="username" v-model="username" />
      </div>
      <div>
        <label for="password">密码：</label>
        <input type="password" id="password" v-model="password" />
      </div>
      <button type="submit">注册</button>
    </form>
    <p>已经有账号了？<a href="#" @click="handleChangeView('login')">立即登录</a></p>
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
      // 处理注册逻辑
    },
    handleChangeView(view) {
      this.$emit('changeView', view);
    },
  },
};
</script>