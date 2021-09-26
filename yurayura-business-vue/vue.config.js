// vue.config.js
const path = require('path')
const IS_PROD = process.env.NODE_ENV === 'production'
const resolve = (dir) => path.join(__dirname, dir)
module.exports = {
  publicPath: IS_PROD ? './' : '/',  // 公共路径
  indexPath: 'index.html', // 相对于打包路径index.html的路径
  outputDir: 'dist', // '生产环境构建文件的目录
  assetsDir: '', // 相对于outputDir的静态资源(js、css、img、fonts)目录
  lintOnSave: false, // 是否在开发环境下通过 eslint-loader 在每次保存时 lint 代码
  runtimeCompiler: false, // 是否使用包含运行时编译器的 Vue 构建版本
  transpileDependencies: ['bootstrap'], // 使用babel转译node_modules中的依赖（解决Edge,IE下不兼容某些安装依赖的问题）
  productionSourceMap: !IS_PROD, // 生产环境的 source map
  parallel: require('os').cpus().length > 1, // 是否为 Babel 或 TypeScript 使用 thread-loader。该选项在系统的 CPU 有多于一个内核时自动启用，仅作用于生产构建
  chainWebpack: config => {
    config.resolve.symlinks(true) // 修复热更新失效
    // 如果使用多页面打包，使用vue inspect --plugins查看html是否在结果数组中
    config.plugin('html').tap(args => {
      // 修复 Lazy loading routes Error
      args[0].chunksSortMode = 'none'
      return args
    })
    config.resolve.alias // 添加别名
      .set('@', resolve('src'))
      .set('@assets', resolve('src/assets'))
      .set('@api', resolve('src/api'))
      .set('@components', resolve('src/components'))
      .set('@views', resolve('src/views'))
      .set('@utils', resolve('src/utils'))
      .set('@css', resolve('public/css'))
  },
  css: {
    extract: IS_PROD,
    requireModuleExtension: true // 去掉文件名中的 .module
  },
  devServer: {
    overlay: { // 让浏览器 overlay 同时显示警告和错误
      warnings: true,
      errors: true
    },
    host: '127.0.0.1',
    port: 2233, // 端口号
    https: false, // https:{type:Boolean}
    open: true, // 配置自动启动浏览器
    hotOnly: true, // 热更新
    proxy: { // 配置多个代理
      '/images': {
        target: 'http://127.0.0.1:2333/images',
        changeOrigin: true,
        secure: false,
        pathRewrite: {
          '^/images': ''
        }
      },
      '/upload': {
        target: 'http://127.0.0.1:2333/upload',
        changeOrigin: true,
        secure: false,
        pathRewrite: {
          '^/upload': ''
        }
      },
      '/api': {
        target: 'http://127.0.0.1:2333/api',
        changeOrigin: true,
        secure: false,
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
}
