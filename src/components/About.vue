<!-- 5.关于页面组件： -->
<template>
    <div class="about">
      <h2>关于我</h2>
      <p>我是一个热爱技术的程序员，喜欢分享经验和交流技术。</p>
      <h2>博客主题</h2>
      <p>这是一个简洁清新的博客主题，专注于内容和阅读体验。</p>
      <h2>技术栈</h2>
      <ul>
        <li>Vue.js</li>
        <li>Node.js</li>
        <li>Express</li>
        <li>MongoDB</li>
        <li>Webpack</li>
      </ul>
    </div>
  </template>
  
  <script>
  export default {
    name: "About",
  };
  </script>
  
  <style>
  .about {
    margin: 40px 0;
  }
  
  .about h2 {
    font-size: 24px;
    margin-bottom: 20px;
  }
  
  .about p {
    font-size: 16px;
    margin-bottom: 10px;
  }
  
  .about ul {
    list-style: none;
    margin: 0;
    padding: 0;
  }
  
  .about li {
    font-size: 16px;
    margin-bottom: 5px;
  }
  </style>