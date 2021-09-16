<template>
  <div>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
html {
  cursor: pointer;
}
/* 全局背景及禁用y轴滚动设置 */
body {
  cursor: default;
  background: #15202b !important;
  overflow-y: hidden;
}
/* 默认浏览器滚动条修改（Chrome内核有效） */
::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}
::-webkit-scrollbar-thumb {
  position: relative;
  display: block;
  cursor: pointer;
  border-radius: 1rem;
  background-color: rgba(144, 147, 153, 0.3);
  transition: 0.3s background-color;
}
::-webkit-scrollbar-thumb:hover {
  background-color: rgba(144, 147, 153, 0.5);
}
</style>
