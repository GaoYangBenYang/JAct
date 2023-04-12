<!-- 7. 搜索结果组件： -->
<template>
    <div class="search-result">
      <h2>搜索结果：{{ keyword }}</h2>
      <ul>
        <li v-for="post in filteredPosts" :key="post.id">
          <router-link :to="`/posts/${post.id}`">{{ post.title }}</router-link>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  export default {
    name: "SearchResult",
    computed: {
      keyword() {
        // 从路由参数中获取搜索关键字
        return this.$route.query.keyword;
      },
      filteredPosts() {
        // 根据搜索关键字过滤文章列表
        return this.$store.state.posts.filter((post) =>
          post.title.toLowerCase().includes(this.keyword.toLowerCase())
        );
      },
    },
  };
  </script>
  
  <style>
  .search-result {
    margin: 40px 0;
  }
  
  .search-result h2 {
    font-size: 24px;
    margin-bottom: 20px;
  }
  
  .search-result ul {
    list-style: none;
    margin: 0;
    padding: 0;
  }
  
  .search-result li {
    font-size: 16px;
    margin-bottom: 5px;
  }
  
  .search-result a {
    color: #1890ff;
    text-decoration: none;
  }
  
  .search-result a:hover {
    text-decoration: underline;
  }
  </style>