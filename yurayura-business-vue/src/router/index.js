import Vue from 'vue'
import Router from 'vue-router'
import api from '@api/baseRequest'
import apiUrl from '@api/baseUrl'
import ManagerLogin from '@views/ManagerLogin.vue'
import Business from '@views/Business.vue'
import BusinessIndex from '@views/BusinessIndex.vue'
import BusinessSysMenu from '@views/BusinessSysMenu.vue'
import BusinessSysDict from '@views/BusinessSysDict.vue'
import BusinessSysManager from '@views/BusinessSysManager.vue'
import BusinessSysPermission from '@views/BusinessSysPermission.vue'
import BusinessComicInfo from '@views/BusinessComicInfo.vue'
import BusinessUserInfo from '@views/BusinessUserInfo.vue'
import Notfound from '@components/Notfound.vue'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'HomePage',
      component: ManagerLogin
    },
    {
      path: '/manager_login',
      name: 'ManagerLogin',
      component: ManagerLogin
    },
    {
      path: '/business',
      component: Business,
      children: [
        {
          path: '',
          name: 'Business',
          component: BusinessIndex
        },
        {
          path: 'index',
          name: 'BusinessIndex',
          component: BusinessIndex
        },
        {
          path: 'sys_menu',
          name: 'BusinessSysMenu',
          component: BusinessSysMenu
        },
        {
          path: 'sys_dict',
          name: 'BusinessSysDict',
          component: BusinessSysDict
        },
        {
          path: 'sys_manager',
          name: 'BusinessSysManager',
          component: BusinessSysManager
        },
        {
          path: 'sys_permission',
          name: 'BusinessSysPermission',
          component: BusinessSysPermission
        },
        {
          path: 'comic_info',
          name: 'BusinessComicInfo',
          component: BusinessComicInfo
        },
        {
          path: 'user_info',
          name: 'BusinessUserInfo',
          component: BusinessUserInfo
        }
      ]
    },
    {
      path: '*',
      name: 'Notfound',
      component: Notfound
    }
  ],
  scrollBehavior () {
    // 页面刷新回到页面顶部的方法
    return {
      x: 0,
      y: 0
    }
  }
})

// 设置全局前置守卫
router.beforeEach((to, from, next) => {
  if (to.name !== 'ManagerLogin' && to.name !== 'HomePage' && to.name !== 'Notfound') {
    api.get(apiUrl.SYS_MANAGER_JUDGEAUTHEN, null, res => {
      if (res.code === 200) {
        next()
      } else {
        next('/manager_login')
      }
    })
  } else {
    next()
  }
})

// 解决重复访问相同路由时浏览器报错（虽然不影响）
const VueRouterPush = Router.prototype.push
Router.prototype.push = function push (to) {
  return VueRouterPush.call(this, to).catch(err => err)
}

export default router
