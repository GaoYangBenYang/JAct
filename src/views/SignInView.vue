<template>
  <div class="login-container">
    <div class="login-form">
      <h1 class="form-title">Welcome to Problem Focus!</h1>
      <form>
        <div class="form-group">
          <label for="email">电话号码/邮箱：</label>
          <input type="email" name="email" id="email" v-model="email" required>
        </div>
        <div class="form-group">
          <label for="password">密码：</label>
          <input type="password" name="password" id="password" v-model="password" required>
          <div class="registration">没有账号？
            <a href="" >立即注册</a>
          </div>
          <div class="forget-password">
             <a href="">忘记密码</a> 
          </div>
        </div>
        <button type="submit" class="btn-login" @click="login">登录</button>
      </form>
    </div>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  name:"LoginView",
  data() {
      return {
      email: '',
      password: '',
      }
  },
  methods:{
    login() {
      // TODO: 实现登录逻辑
      axios.post('http://localhost:8080/v1/user', {
        email: 'myemail',
        password: 'mypassword'
      }, {
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        }
      })
      .then(response => {
        console.log(response)
      })
      .catch(error => {
        console.log(error)
      })
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
}

.forget-password{
  margin-top: 10px;
  margin-bottom: 10px;
  float: right;
}

a {
  text-decoration: none;
  color: rgb(20, 78, 238);
}

label {
display: block;
margin-bottom: 5px;
}

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