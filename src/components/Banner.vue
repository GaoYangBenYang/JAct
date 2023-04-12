<!-- 2.首页 Banner 组件： -->
<template>
    <div class="banner">
      <h1>{{ title }}</h1>
      <p>{{ description }}</p>
      <router-link to="/articles/latest">{{ latestArticle }}</router-link>
    </div>
  </template>
  
  <script>
  export default {
    name: "Banner",
    data() {
      return {
        title: "我的博客",
        description: "记录生活、分享经验、交流技术",
        latestArticle: "最新文章",
      };
    },
  };
  </script>
  
  <style>
  .banner {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 300px;
    background-image: url(https://picsum.photos/1200/300);
    background-size: cover;
    color: #fff;
  }
  
  .banner h1 {
    font-size: 48px;
    margin-bottom: 20px;
  }
  
  .banner p {
    font-size: 24px;
    margin-bottom: 40px;
  }
  
  .banner a {
    font-size: 18px;
    color: #fff;
    text-decoration: none;
  }
  
  .banner a:hover {
    color: #1890ff;
  }
  </style>