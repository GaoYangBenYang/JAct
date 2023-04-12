<!-- 3.文章列表组件： -->
<template>
<div class="article-list">
    <h2>最新文章</h2>
    <ul>
      <li v-for="article in articles" :key="article.id">
        <router-link :to="`/articles/${article.id}`">{{ article.title }}</router-link>
        <span>{{ article.author }}</span>
        <span>{{ article.date }}</span>
        <span>{{ article.views }} 浏览</span>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "ArticleList",
  data() {
    return {
      articles: [
        {
          id: 1,
          title: "Vue.js 快速入门",
          author: "Tom",
          date: "2022-01-01",
          views: 100,
        },
        {
          id: 2,
          title: "React.js 实战教程",
          author: "Jerry",
          date: "2022-01-02",
          views: 200,
        },
        {
          id: 3,
          title: "Node.js 全栈开发",
          author: "Lucy",
          date: "2022-01-03",
          views: 300,
        },
      ],
    };
  },
};
</script>

<style>
.article-list {
  margin: 40px 0;
}

.article-list h2 {
  font-size: 24px;
  margin-bottom: 20px;
}

.article-list ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

.article-list li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
  padding: 10px;
  background-color: #f5f5f5;
}

.article-list li:hover {
  background-color: #e6f7ff;
}

.article-list li span {
  font-size: 14px;
  color: #999;
}
</style>