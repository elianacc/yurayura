<template>
  <div class="container ps-0">

    <!-- 置顶导航 -->
    <nav class="navbar navbar-dark fixed-top bg-twitter-nav py-0 px-3">
      <span class="navbar-brand">
        <img src="~@assets/xiaomai.jpg"
             width="40"
             height="40"
             class="rounded" />
        <span class="ms-3"
              v-show="!sideMenuIsCollapse">{{$store.getters['manager/managerName']}}</span>
        <span :class="sideMenuIsCollapse? 'ms-3' : 'ms-uncollapse-span'"
              class="collapse-span"
              @click="sideMenuIsCollapse = !sideMenuIsCollapse">
          <i class="el-icon-s-fold font-size-28"
             v-show="!sideMenuIsCollapse"></i>
          <i class="el-icon-s-unfold font-size-28"
             v-show="sideMenuIsCollapse"></i>
        </span>
      </span>
      <form class="d-flex">
        <button class="btn btn-danger ms-2"
                type="button"
                @click="logoutManager">
          <i class="fa fa-sign-out me-2"></i>注销
        </button>
      </form>
    </nav>

    <!-- 侧边导航、标签导航、主内容row -->
    <div class="row r1">
      <!-- 侧边导航col -->
      <div :class="sideMenuIsCollapse? 'col-sidemenu-collapse': 'col-sidemenu-uncollapse'"
           class="c1">
        <el-menu :default-active="sideMenuDftActive"
                 :default-openeds="sideMenu.map(menu => menu.menuName)"
                 background-color="#192734"
                 text-color="#adb5bd"
                 active-text-color="#409eff"
                 class="el-menu-vertical"
                 :collapse="sideMenuIsCollapse"
                 router
                 style="height: 100vh;">
          <el-scrollbar style="height: 100%;">
            <div style="height: 1200px;"
                 class="menu-container">
              <el-menu-item index="/business/index">
                <i class="el-icon-s-home"></i>
                <span slot="title">首页</span>
              </el-menu-item>
              <el-submenu :index="submenu.menuName"
                          v-for="submenu in sideMenu"
                          :key="submenu.menuName">
                <template slot="title">
                  <i :class="submenu.menuIconClass"></i>
                  <span slot="title">{{submenu.menuTitle}}</span>
                </template>

                <el-menu-item :index="item.menuIndex"
                              v-for="item in submenu.menuSubList"
                              :key="item.menuName">
                  <i :class="item.menuIconClass"
                     class="me-2"></i>{{item.menuTitle}}
                </el-menu-item>

              </el-submenu>
            </div>
          </el-scrollbar>
        </el-menu>
      </div>
      <!-- 标签导航和主内容col -->
      <div :class="sideMenuIsCollapse? 'col-tabandmain-collapse': 'col-tabandmain-uncollapse'"
           class="c2 pt-2 ps-0"
           style="height: 100vh;">

        <!-- 标签导航 -->
        <el-tabs v-model="editableTabsValue"
                 type="card"
                 closable
                 @tab-remove="removeTab"
                 @tab-click="tabClick">
          <el-tab-pane v-for="item in editableTabs"
                       :key="item.name"
                       :name="item.name">
            <span slot="label"><i class="fa fa-tag fa-lg me-2"></i>{{item.title}}</span>
          </el-tab-pane>
        </el-tabs>

        <el-scrollbar style="height: 100%;">
          <!-- 主内容 -->
          <router-view class="pb-12"></router-view>
          <!-- 返回顶部 -->
          <el-backtop target=".r1 .c2 .el-scrollbar__wrap"
                      :bottom="20">
            <div class="back-top rounded">
              <i class="el-icon-arrow-up"></i>
            </div>
          </el-backtop>
        </el-scrollbar>

      </div>
    </div>

  </div>
</template>

<script>
import { getSysSideMenu, getSysMenuSubByIndex } from '@api/sysMenu'
import { sysManagerLogout, getCurrentSysManagerMsg } from '@api/sysManager'
import { getSysDictAll } from '@api/sysDict'

export default {
  name: 'Business',
  data () {
    return {
      sideMenuIsCollapse: false,
      sideMenuDftActive: this.$store.getters['menutab/sideMenuDftActive'],
      sideMenu: [],
      editableTabsValue: this.$store.getters['menutab/editableTabsValue'],
      editableTabs: this.$store.getters['menutab/editableTabs']
    }
  },
  methods: {
    getSideMenu () {
      getSysSideMenu(success => {
        this.sideMenu = success.data
      })
    },
    logoutManager () {
      this.$confirm('确定要注销吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        sysManagerLogout(() => {
          this.$store.commit('manager/CLEAR_MANAGER_MSG')
          this.$router.push('/manager_login')
        })
      })
    },
    removeTab (targetName) {
      let tabs = [...this.editableTabs]
      let activeName = this.editableTabsValue
      if (activeName === targetName) {
        tabs.forEach((tab, index) => {
          if (tab.name === targetName) {
            let nextTab = tabs[index + 1] || tabs[index - 1]
            if (nextTab) {
              this.$router.push(nextTab.index)
            } else {
              this.$router.push('/business')
            }
          }
        })
      }
      this.editableTabs = tabs.filter(tab => tab.name !== targetName)
    },
    addTab (tabTitle, tabName, tabIndex) {
      let newTabName = tabName
      if (this.editableTabs.findIndex(tab => tab.name === newTabName) === -1) {
        this.editableTabs.push({
          title: tabTitle,
          name: newTabName,
          index: tabIndex
        })
      }
      this.sideMenuDftActive = tabIndex
      this.editableTabsValue = newTabName
    },
    tabClick (target) {
      let nowTab = this.editableTabs.find(tab => tab.name === target.name)
      this.$router.push(nowTab.index)
    },
    getCurrentManagerMsg () {
      getCurrentSysManagerMsg(success => {
        this.$store.commit('manager/SET_MANAGER_MSG', success.data)
      })
    },
    getAllDict () {
      getSysDictAll(success => {
        this.$store.commit('dict/SET_DICT_LIST', success.data)
      }, warn => {
        this.$alert(warn.msg, '提示', {
          confirmButtonText: '确定'
        })
      })
    }
  },
  watch: {
    $route: {
      deep: true,
      handler (to) {
        this.getCurrentManagerMsg()
        if (to.name === 'Business') {
          this.sideMenuDftActive = ''
          this.editableTabsValue = ''
        } else {
          if (to.name === 'BusinessIndex') {
            this.addTab('首页', 'index', '/business/index')
          }
          if (to.name !== 'BusinessIndex') {
            let index = to.path.charAt(to.path.length - 1) === '/' ? to.path.substring(0, to.path.length - 1) : to.path
            getSysMenuSubByIndex(index, success => {
              this.getAllDict()
              let nowItem = success.data
              this.addTab(nowItem.menuTitle, nowItem.menuName, nowItem.menuIndex)
            }, warn => {
              this.$message.error(warn.msg)
            })
          }
        }
      },
      immediate: true
    },
    editableTabs: {
      deep: true,
      handler (tabs) {
        this.$store.commit('menutab/SET_EDITABLE_TABS', tabs)
      }
    },
    editableTabsValue (val) {
      this.$store.commit('menutab/SET_EDITABLE_TABS_VALUE', val)
    },
    sideMenuDftActive (val) {
      this.$store.commit('menutab/SET_SIDE_MENU_DFT_ACTIVE', val)
    }
  },
  mounted () {
    this.getSideMenu()
  }
}
</script>

<style scoped>
@media (min-width: 1px) {
  .container {
    width: 1366px;
  }
  .r1 .col-sidemenu-collapse {
    flex: 0 0 8.333333%;
    max-width: 8.333333%;
    position: relative;
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
  }
  .r1 .col-sidemenu-uncollapse {
    flex: 0 0 16.77%;
    max-width: 16.77%;
    position: relative;
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
  }
  .r1 .col-tabandmain-collapse {
    flex: 0 0 91.666667%;
    max-width: 91.666667%;
    position: relative;
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
  }
  .r1 .col-tabandmain-uncollapse {
    flex: 0 0 83.23%;
    max-width: 83.23%;
    position: relative;
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
  }
}
@media (min-width: 1367px) {
  .container {
    width: 1440px;
  }
  .r1 .col-sidemenu-collapse {
    flex: 0 0 8.333333%;
    max-width: 8.333333%;
    position: relative;
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
  }
  .r1 .col-sidemenu-uncollapse {
    flex: 0 0 15.95%;
    max-width: 15.95%;
    position: relative;
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
  }
  .r1 .col-tabandmain-collapse {
    flex: 0 0 91.666667%;
    max-width: 91.666667%;
    position: relative;
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
  }
  .r1 .col-tabandmain-uncollapse {
    flex: 0 0 84.05%;
    max-width: 84.05%;
    position: relative;
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
  }
}
@media (min-width: 1441px) {
  .container {
    width: 1680px;
  }
  .r1 .col-sidemenu-collapse {
    flex: 0 0 8.333333%;
    max-width: 8.333333%;
    position: relative;
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
  }
  .r1 .col-sidemenu-uncollapse {
    flex: 0 0 13.7%;
    max-width: 13.7%;
    position: relative;
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
  }
  .r1 .col-tabandmain-collapse {
    flex: 0 0 91.666667%;
    max-width: 91.666667%;
    position: relative;
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
  }
  .r1 .col-tabandmain-uncollapse {
    flex: 0 0 86.3%;
    max-width: 86.3%;
    position: relative;
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
  }
}
@media (min-width: 1681px) {
  .container {
    width: 1920px;
  }
  .r1 .col-sidemenu-collapse {
    flex: 0 0 8.333333%;
    max-width: 8.333333%;
    position: relative;
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
  }
  .r1 .col-sidemenu-uncollapse {
    flex: 0 0 12%;
    max-width: 12%;
    position: relative;
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
  }
  .r1 .col-tabandmain-collapse {
    flex: 0 0 91.666667%;
    max-width: 91.666667%;
    position: relative;
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
  }
  .r1 .col-tabandmain-uncollapse {
    flex: 0 0 88%;
    max-width: 88%;
    position: relative;
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
  }
}

/* 置顶导航-侧边导航展开图标 */
nav .collapse-span {
  cursor: pointer;
}
nav .ms-uncollapse-span {
  margin-left: 5.5rem;
}

.r1 {
  margin-top: 50px;
}

/* el侧边导航重写（右白边清除） */
.r1 .c1 /deep/ .el-menu {
  border-right: 0rem;
}
/* el侧边导航宽高 */
.r1 .c1 .el-menu-vertical {
  width: 65px;
}
.r1 .c1 .el-menu-vertical:not(.el-menu--collapse) {
  width: 210px;
}
.r1 .c1 .el-menu-vertical:not(.el-menu--collapse) .menu-container {
  background-image: url("~@assets/miku-sidemenu.png");
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-position: 0px 630px;
  background-size: 210px 300px;
}

/* el侧边导航折叠时隐藏文字 */
.r1 .c1 .el-menu--collapse .el-submenu__title span {
  display: none;
}
/* el侧边导航折叠时隐藏 > */
.r1 .c1 .el-menu--collapse .el-submenu__title .el-submenu__icon-arrow {
  display: none;
}

/* el标签页（选项卡）重写 */
.r1 .c2 /deep/ .el-tabs--card > .el-tabs__header .el-tabs__nav {
  border: none;
}
.r1 .c2 /deep/ .el-tabs--card > .el-tabs__header .el-tabs__item {
  border-left: none;
}
.r1 .c2 /deep/ .el-tabs--card > .el-tabs__header {
  border-bottom: none;
  border-radius: 0.5rem;
  background-color: #192734;
}
.r1 .c2 /deep/ .el-tabs--card > .el-tabs__header .el-tabs__item.is-active {
  border-bottom-color: #409eff;
}
.r1 .c2 /deep/ .el-tabs__item .el-icon-close:hover {
  background-color: #10171e;
}
.r1 .c2 /deep/ .el-tabs__item {
  color: #adb5bd;
}
.r1 .c2 /deep/ .el-tabs__item:hover {
  color: #409eff;
  cursor: pointer;
}
.r1 .c2 /deep/ .el-tabs__item.is-active {
  color: #409eff;
}

/* 主内容底部留白 */
.r1 .c2 .pb-12 {
  padding-bottom: 12rem;
}

/* 返回顶部 */
.r1 .c2 .back-top {
  height: 100%;
  width: 100%;
  background-color: #192734;
  box-shadow: 0 0 6px rgba(0, 0, 0, 0.12);
  text-align: center;
  line-height: 40px;
  color: #ffffff;
}
</style>
