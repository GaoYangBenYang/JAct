<template>
  <div class="bg-gradient-to-r from-blue-500 to-purple-500 bg-opacity-50 min-h-screen flex items-center justify-center">
    <div class="w-full max-w-md bg-white p-8 rounded-md">
      <div class="flex items-center justify-center mb-6">
<!--        <img src="logo.png" alt="网站Logo" class="w-12 h-12">-->
      </div>
      <h1 class="text-2xl font-bold mb-6">欢迎登录</h1>
      <button class="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-4 rounded-full mb-4 w-full">
        登录
      </button>
      <button class="bg-gray-300 hover:bg-gray-400 text-gray-800 font-semibold py-2 px-4 rounded-full mb-4 w-full">
        注册
      </button>
      <button class="text-blue-500 hover:text-blue-600 font-semibold mb-4 w-full">
        忘记密码
      </button>
      <div class="flex items-center justify-between mb-4">
        <label for="remember" class="text-gray-700">记住密码</label>
        <input type="checkbox" id="remember" class="ml-2">
      </div>
      <button class="bg-green-500 hover:bg-green-600 text-white font-semibold py-2 px-4 rounded-full mb-4 w-full">
        微信第三方登录
      </button>
      <div class="text-sm text-gray-500">
        © 1998 - 2023 Tencent Inc. All Rights Reserved
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoginPage',
};
</script>

<style>

</style>