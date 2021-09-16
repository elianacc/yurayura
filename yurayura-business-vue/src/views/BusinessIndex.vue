<template>
  <div>
    <div class="jumbotron">
      <h1 class="display-4 text-center text-white">Hello, {{$store.getters['manager/managerName']}}!</h1>
      <p class="lead text-center text-white">欢迎进入Yura~Yura~后端管理页面</p>
      <hr class="my-4 bg-secondary">
      <p class="text-center text-white">YuraYura是一个兴趣使然的项目</p>
      <p class="lead github-btn-p text-center">
        <a class="btn btn-primary btn-lg"
           ref="getMoreLink"
           :href="githubUrl"
           data-bs-toggle="tooltip"
           data-bs-placement="right"
           title="点击跳转到本项目前端Vue工程所在GitHub仓库"
           target="_blank"><i class="fa fa-github fa-lg me-2"></i>了解更多</a>
      </p>
    </div>
  </div>
</template>

<script>
import bootstrap from 'bootstrap/dist/js/bootstrap.bundle.min'

export default {
  name: 'BusinessIndex',
  data () {
    return {
      githubUrl: 'https://github.com/elianacc/yurayura-business-vue'
    }
  },
  mounted () {
    new bootstrap.Tooltip(this.$refs.getMoreLink)
  }
}
</script>

<style scoped>
.jumbotron {
  padding: 4rem 2rem;
  margin-bottom: 2rem;
  border-radius: 0.3rem;
  background-color: #15202b;
}

.github-btn-p {
  position: relative;
  top: 2rem;
}
</style>
