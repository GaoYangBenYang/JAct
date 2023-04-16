<template>

    <!-- 导航条 -->
    <nav class="navbar">
      <ul class="nav_left">
        <li><a href="http://localhost:5173/"><img src="./assets/logo.png" alt="Logo">Problem Focus</a></li>
        <li><a href="#">首页</a></li>
        <li><a href="#">分类</a></li>
      </ul>  
      <ul class="nav_right">
  
        <li>
            <form class="navbar-search" method="get" role="search">
                <input class="search_input" placeholder="search" type="text" tabindex="3">
                <button type="submit" id="search_button">
                    <img src="./assets/logo.png" alt="搜索">
                </button>
            </form>
        </li>
        <li id="navbar_login_status" class="navbar-list">
            <a class="navbar-user-info navbar-blog" href="https://i.cnblogs.com/EditPosts.aspx?opt=1" alt="写随笔" title="写随笔" style="display: none;">
                <img id="new_post_icon" class="navbar-icon" src="/images/aggsite/newpost.svg" alt="写随笔">
            </a>
            <a id="navblog-myblog-icon" class="navbar-user-info navbar-blog" href="https://passport.cnblogs.com/GetBlogApplyStatus.aspx" alt="我的博客" title="我的博客" style="display: none;">
                <img id="myblog_icon" class="navbar-icon" src="/images/aggsite/myblog.svg" alt="我的博客">
            </a>
            <a class="navbar-user-info navbar-message navbar-icon-wrapper" href="https://msg.cnblogs.com/" alt="短消息" title="短消息" style="display: none;">
                <img id="msg_icon" class="navbar-icon" src="/images/aggsite/message.svg?v=J0WS2P2iPgaIVgXxcAhliw4AFZIpaTWxtdoNAv9eiCA" alt="短消息">
                <span id="msg_count" style="display: none"></span>
            </a>
            <a id="navbar_lite_mode_indicator" data-current-page="aggsite" style="display: none" href="javascript:void(0)" alt="简洁模式" title="简洁模式启用，您在访问他人博客时会使用简洁款皮肤展示">
                <img class="navbar-icon" src="/images/aggsite/lite-mode-on.svg" alt="简洁模式">
            </a>
            <div id="user_info" class="navbar-user-info dropdown" style="display: none;">
                <a class="dropdown-button" href="https://home.cnblogs.com/">
                    <img id="user_icon" class="navbar-avatar" src="/images/aggsite/avatar-default.svg" alt="用户头像">
                </a>
                <div class="dropdown-menu" id="navbar_menu">
                    <a id="navblog-myblog-text" href="https://passport.cnblogs.com/GetBlogApplyStatus.aspx">我的博客</a>
                    <a href="https://home.cnblogs.com/">我的园子</a>
                    <a href="https://account.cnblogs.com/settings/account">账号设置</a>
                    <a href="javascript:void(0)" id="navbar_lite_mode_toggle" title="简洁模式会使用简洁款皮肤显示所有博客">简洁模式 <img id="navbar_lite_mode_on" src="/images/lite-mode-check.svg" class="hide"><span id="navbar_lite_mode_spinner" class="hide">...</span></a>
                    <a href="javascript:void(0)" onclick="account.logout();">退出登录</a>
                </div>
            </div>
            <a class="navbar-anonymous" href="http://localhost:5173/signup" style="display: inline;">注册</a>
            <a class="navbar-anonymous" href="http://localhost:5173/signin?" style="display: inline;">登录</a>
        </li>
      </ul>
    </nav>
    <!-- 主页内容 -->
    <div class="content">
  
      <div class="content_letf">
          asd
      </div>
      <div class="content_center">
          qwe
      </div>
      <div class="content_right">
          zxc
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
  </template>
  
  <script>
  export default {
    name:"HomeView",
    methods:{
      ToSignUp(){
          this.$router.replace("/signup")
      }
    },
  }
  </script>
  
  <style scoped> 
  /* 导航栏样式 */
  .navbar { 
    height: 38px;
    border: none;
    background-color: #444;
    display: flex;
      /* 对齐方式 */
    justify-content: space-between; 
      /* 子元素的排列方式 */
    flex-direction: row;
      /* 纵向对齐方式 */
    align-items: center;
    font-size: 15px;
    padding: 8px 40px; 
  } 
  .nav_left,.nav_right {
    margin: 0;
    padding: 0;
    display: flex;
    flex-direction: row;
    align-items: center;
    list-style: none;
  }
  li{
      padding-right: 40px;
  }
  img {
    height: 30px;
  }
  a{
    list-style: none;
    color: rgb(235, 229, 229);
  }
  /* 内容布局 */
  .content{
    text-align: center;
    /* 横向弹性布局 */
    display: flex;
    /* 使用justify-content属性，可设置子元素的对齐方式
    flex-start：开头对齐
    flex-end：结尾对齐
    center：居中
    space-between：平均分布，但两边不留控件
    space-around：平均分布，两边留空间
    */
    justify-content:space-around;
    padding: 0px 40px;
  }
  .content_letf{
    width: 10%;
    background-color: aqua;
  }
  .content_center{
    width: 70%;
    background-color: rgb(42, 122, 122);
  }
  .content_right{
    width: 20%;
    background-color: rgb(15, 78, 78);
  }
  
  
  
  /* 底部信息样式 */ 
  .footer { 
    text-align: center; 
    padding: 20px 40px; 
    background-color: #eee; 
  } 
  .footer-links { 
    display: flex; 
    justify-content: center; 
    gap: 20px; 
    margin-bottom: 10px; } 
  .footer-links a { 
    color: #333; 
    text-decoration: none; 
    font-weight: bold; 
  }
  .footer p { 
    font-size: 15px; 
    color: #666; 
  } 
   </style>