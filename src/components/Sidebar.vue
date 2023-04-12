<!-- 4.文章分类和标签侧边栏组件： -->
<template>
    <div class="sidebar">
      <div class="category">
        <h3>分类</h3>
        <ul>
          <li v-for="category in categories" :key="category.id">
            <router-link :to="`/categories/${category.id}`">{{ category.name }}</router-link>
        </li>
      </ul>
    </div>
    <div class="tag">
      <h3>标签</h3>
      <ul>
        <li v-for="tag in tags" :key="tag.id">
          <router-link :to="`/tags/${tag.id}`">{{ tag.name }}</router-link>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "Sidebar",
  data() {
    return {
      categories: [
        {
          id: 1,
          name: "技术分享",
        },
        {
          id: 2,
          name: "生活感悟",
        },
        {
          id: 3,
          name: "随笔杂谈",
        },
      ],
      tags: [
        {
          id: 1,
          name: "Vue.js",
        },
        {
          id: 2,
          name: "React.js",
        },
        {
          id: 3,
          name: "Node.js",
        },
      ],
    };
  },
};
</script>

<style>
.sidebar {
  display: flex;
  flex-direction: column;
  margin: 40px 0;
}

.category,
.tag {
  margin-bottom: 20px;
}

.category h3,
.tag h3 {
  font-size: 18px;
  margin-bottom: 10px;
}

.category ul,
.tag ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

.category li,
.tag li {
  margin-bottom: 5px;
}

.category a,
.tag a {
  font-size: 14px;
  color: #333;
  text-decoration: none;
}

.category a:hover,
.tag a:hover {
  color: #1890ff;
}
</style>