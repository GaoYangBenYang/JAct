<template>
  <div class="login-container">
    <div class="login-form">
      <h1 class="form-title">Welcome to Problem Focus!</h1>
      <form>
        <div class="form-group">
          <label for="phone-number">电话号码:</label>
          <input type="tel" name="tel" id="tel" v-model="tel" required>
        </div>
        <div class="form-group">
          <label for="email">邮箱:</label>
          <input type="email" name="email" id="email" v-model="email" required>
        </div>
        <div class="form-group">
          <label for="password">密码:</label>
          <input type="password" name="password" id="password" v-model="password" required>
        </div>
        <div class="form-group">
          <label for="confirm-password">确认密码:</label>
          <input type="password" name="confirm-password" id="confirm-password" v-model="confirmPassword" required>
        </div>
        <button type="submit" class="btn-login" @click="login">注册</button>
      </form>
    </div>
  </div>
</template>
<script>
export default {
  name:"LoginView",
  data() {
      return {
      tel:'',
      email: '',
      password: '',
      confirmPassword:''
      }
  },
  methods:{
    login() {
      // TODO: 实现登录逻辑
    },
  }
}
</script>
<style scoped>
.login-container {
display: flex;
justify-content: center;
align-items: center;
height: 100vh;
background-image: linear-gradient(to right bottom, #bc95c6, #a2a4d8, #88b1dd, #79bcd8, #7dc4cc);
}

.login-form {
background-color: #fff;
border-radius: 5px;
box-shadow: 0 0 10px #ccc;
padding: 20px;
width: 360px;
}

.form-title {
font-size: 24px;
margin-bottom: 20px;
text-align: center;
}

.form-group {
margin-bottom: 20px;
}

.registration{
  margin-top: 10px;
  margin-bottom: 10px;
  float: left;
  color: rgb(20, 78, 238);
}

.forget-the-password{
  margin-top: 10px;
  margin-bottom: 10px;
  float: right;
  color: rgb(20, 78, 238);
}

label {
display: block;
margin-bottom: 5px;
}

input[type="tel"],
input[type="email"],
input[type="password"] {
width: 95%;
padding: 10px;
border-radius: 5px;
border: 1px solid #ccc;
}

button[type="submit"] {
display: block;
width: 100%;
padding: 10px;
border-radius: 5px;
border: none;
background-color: #007aff;
color: #fff;
font-size: 18px;
cursor: pointer;
}

button[type="submit"]:hover {
background-color: #0062cc;
}
</style>