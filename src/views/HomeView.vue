<template>
  <div class="home-container">
      <!-- 导航栏 -->
      <nav class="navbar">
        <div class="logo">
          <img src="./assets/logo.svg" alt="Logo">
        </div>
        <ul class="nav-links">
          <li><a href="#">首页</a></li>
          <li><a href="#">产品</a></li>
          <li><a href="#">服务</a></li>
          <li><a href="#">关于我们</a></li>
          <li><a href="#">联系我们</a></li>
        </ul>
      </nav>

      <!-- 轮播图 -->
      <div class="carousel">
        <img src="./assets/logo.svg" alt="Slide 1">
        <img src="./assets/logo.svg" alt="Slide 2">
        <img src="./assets/logo.svg" alt="Slide 3">
      </div>

      <!-- 特色服务 -->
      <div class="features">
        <div class="feature">
          <img src="./assets/logo.svg" alt="Feature 1">
          <h3>多样化的产品</h3>
          <p>我们提供多种类型的产品，满足您不同的需求。</p>
        </div>
        <div class="feature">
          <img src="./assets/logo.svg" alt="Feature 2">
          <h3>高效的服务</h3>
          <p>我们的服务团队高效、专业，确保您的问题得到及时解决。</p>
        </div>
        <div class="feature">
          <img src="./assets/logo.svg" alt="Feature 3">
          <h3>安全可靠</h3>
          <p>我们的产品和服务都经过严格的测试和审核，保证安全可靠。</p>
        </div>
      </div>

      <!-- 产品展示 -->
      <div class="products">
        <h2>我们的产品</h2>
        <div class="product">
          <img src="./assets/logo.svg" alt="Product 1">
          <h3>产品名称</h3>
          <p>产品描述</p>
        </div>
        <div class="product">
          <img src="./assets/logo.svg" alt="Product 2">
          <h3>产品名称</h3>
          <p>产品描述</p>
        </div>
        <div class="product">
          <img src="./assets/logo.svg" alt="Product 3">
          <h3>产品名称</h3>
          <p>产品描述</p>
        </div>
      </div>

      <!-- 公司简介 -->
      <div class="about">
        <h2>关于我们</h2>
        <p>公司简介</p>
        <a href="#">了解更多</a>
      </div>

      <!-- 新闻动态 -->
      <div class="news">
    <h2>新闻动态</h2>
    <div class="news-item">
      <h3>新闻标题</h3>
      <p>新闻摘要</p>
      <a href="#">阅读全文</a>
    </div>
    <div class="news-item">
      <h3>新闻标题</h3>
      <p>新闻摘要</p>
      <a href="#">阅读全文</a>
    </div>
    <div class="news-item">
      <h3>新闻标题</h3>
      <p>新闻摘要</p>
      <a href="#">阅读全文</a>
    </div>
    </div>

    <!-- 底部信息 -->
    <footer class="footer">
      <div class="footer-links">
        <a href="#">隐私政策</a>
        <a href="#">使用条款</a>
        <a href="#">版权声明</a>
      </div>
      <p>© 2023 Company Name. [All Rights Reserved]</p>
    </footer>

  </div>

</template>

<script>
export default {
  name:"HomeView",
}
</script>

<style> 
/* 导航栏样式 */
.navbar { display: flex; align-items: center; justify-content: space-between; padding: 20px; } 
.logo img { height: 50px; } 
.nav-links { display: flex; gap: 20px; } 
.nav-links li { list-style: none; }
.nav-links li a { color: #333; text-decoration: none; font-weight: bold; } 
/* 轮播图样式 */ 
.carousel { display: flex; justify-content: center; align-items: center; height: 500px; } 
.carousel img { width: 100%; height: 100%; object-fit: cover; }
/* 特色服务样式 */ 
.features { display: flex; flex-wrap: wrap; justify-content: center; gap: 50px; padding: 50px; } 
.feature { text-align: center; } 
.feature img { width: 150px; height: 150px; margin-bottom: 20px; } 
.feature h3 { font-size: 24px; margin-bottom: 10px; }
.feature p { font-size: 16px; color: #666; } 
/* 产品展示样式 */ 
.products { text-align: center; padding: 50px; } 
.products h2 { font-size: 36px; margin-bottom: 20px; } 
.product { display: inline-block; width: 300px; margin: 20px; } 
.product img { width: 100%; height: 200px; object-fit: cover; margin-bottom: 20px; } 
.product h3 { font-size: 24px; margin-bottom: 10px; } 
.product p { font-size: 16px; color: #666; } 
/* 公司简介样式 */ 
.about { text-align: center; padding: 50px; }
.about h2 { font-size: 36px; margin-bottom: 20px; }
.about p { font-size: 16px; color: #666; margin-bottom: 20px; }
.about a { color: #333; text-decoration: none; font-weight: bold; 
} /* 新闻动态样式 */
.news { text-align: center; padding: 50px; }
.news h2 { font-size: 36px; margin-bottom: 20px; } 
.news-item { text-align: left; margin-bottom: 20px; }
.news-item h3 { font-size: 24px; margin-bottom: 10px; } 
.news-item p { font-size: 16px; color: #666; margin-bottom: 10px; } 
.news-item a { color: #333; text-decoration: none; font-weight: bold; } 
/* 底部信息样式 */ 
.footer { text-align: center; padding: 20px; background-color: #eee; } 
.footer-links { display: flex; justify-content: center; gap: 20px; margin-bottom: 10px; } 
.footer-links a { color: #333; text-decoration: none; font-weight: bold; }
.footer p { font-size: 14px; color: #666; } 
 </style>