<template>
  <div class="login-container">
    <div class="login-form">
      <h1 class="form-title">登录</h1>
      <form>
        <div class="form-group">
          <label for="email">电话号码/邮箱：</label>
          <input type="email" name="email" id="email" v-model="email" required>
        </div>
        <div class="form-group">
          <label for="password">密码：</label>
          <input type="password" name="password" id="password" v-model="password" required>
        </div>
        <button type="submit" class="btn-login">登录</button>
      </form>
    </div>
  </div>
</template>
<script>
export default {
  name:"LoginView",
  data() {
      return {
      email: '',
      password: '',
      }
  },
}
</script>
<style>
.login-container {
display: flex;
justify-content: center;
align-items: center;
height: 100vh;
background-color: #f7f7f7;
}

.login-form {
background-color: #fff;
border-radius: 5px;
box-shadow: 0 0 10px #ccc;
padding: 20px;
width: 400px;
}

.form-title {
font-size: 24px;
margin-bottom: 20px;
text-align: center;
}

.form-group {
margin-bottom: 20px;
}

label {
display: block;
margin-bottom: 5px;
}

input[type="email"],
input[type="password"] {
width: 100%;
padding: 15px;
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