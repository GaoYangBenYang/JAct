<!-- 6.搜索功能组件： -->
<template>
    <div class="search">
      <input type="text" v-model="keyword" placeholder="搜索文章..." />
      <button @click="search">搜索</button>
    </div>
  </template>
  
  <script>
  export default {
    name: "Search",
    data() {
      return {
        keyword: "",
      };
    },
    methods: {
      search() {
        // 跳转到搜索结果页面，并将搜索关键字作为参数传递给组件
        this.$router.push({ path: "/search", query: { keyword: this.keyword } });
      },
    },
  };
  </script>
  
  <style>
  .search {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 40px;
  }
  
  .search input {
    width: 300px;
    height: 36px;
    padding: 0 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 16px;
    outline: none;
  }
  
  .search button {
    height: 36px;
    padding: 0 20px;
    margin-left: 10px;
    background-color: #1890ff;
    color: #fff;
    border: none;
    border-radius: 4px;
    font-size: 16px;
    cursor: pointer;
    outline: none;
  }
  
  .search button:hover {
  background-color: #40a9ff;
  }
  </style>