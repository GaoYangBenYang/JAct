<!-- 导航栏组件 -->
<template lang="">
    <nav class="navbar">
        <div class="logo">
            <img src="./assets/logo.png" alt="Logo">
        </div>
        <ul class="nav-links">
            <li><a href="#">首页</a></li>
            <li><a href="#">产品</a></li>
            <li><a href="#">服务</a></li>
            <li><a href="#">关于我们</a></li>
            <li><a href="#">联系我们</a></li>
        </ul>
    </nav>
</template>
<script>
export default {
    
}
</script>
<style lang="">
/* 导航栏样式 */
.navbar { display: flex; align-items: center; justify-content: space-between; padding: 20px; } 
.logo img { height: 50px; } 
.nav-links { display: flex; gap: 20px; } 
.nav-links li { list-style: none; }
.nav-links li a { color: #333; text-decoration: none; font-weight: bold; } 
</style>