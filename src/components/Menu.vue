<!-- 1.顶部菜单栏组件： -->
<template>
    <div class="menu">
      <router-link to="/">主页</router-link>
      <router-link to="/articles">文章</router-link>
      <router-link to="/tags">标签</router-link>
      <router-link to="/categories">分类</router-link>
      <router-link to="/about">关于</router-link>
    </div>
  </template>
  
  <script>
  export default {
    name: "Menu",
  };
  </script>
  
  <style>
  .menu {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 50px;
    background-color: #f5f5f5;
  }
  
  .menu a {
    margin: 0 20px;
    font-size: 16px;
    color: #333;
    text-decoration: none;
  }
  
  .menu a:hover {
    color: #1890ff;
  }
  </style>