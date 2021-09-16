<template>
  <div v-if="containerShow">
    <!-- 数据筛选表单row -->
    <div class="row mt-4 r1">

      <div class="col-2">
      </div>

      <div class="col-10 c2">
        <el-form inline
                 :model="selectForm"
                 ref="selectForm"
                 label-suffix=":"
                 size="small"
                 class="float-end"
                 @submit.native.prevent="selectContent">
          <el-form-item label="用户名昵称"
                        prop="userNameKeyword"
                        label-width="5.5rem">
            <el-input v-model.trim="selectForm.userNameKeyword"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="性别"
                        prop="userSex"
                        label-width="3rem">
            <el-select v-model="selectForm.userSex"
                       clearable
                       placeholder="请选择">
              <el-option value="1"
                         label="男">
              </el-option>
              <el-option value="0"
                         label="女">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="手机号"
                        prop="userPhoneNumber"
                        label-width="4rem">
            <el-input v-model.trim="selectForm.userPhoneNumber"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="状态"
                        prop="userStatus"
                        label-width="3rem">
            <el-select v-model="selectForm.userStatus"
                       clearable
                       placeholder="请选择">
              <el-option v-for="item in userStatusDict"
                         :key="item.id"
                         :value="item.dictVal"
                         :label="item.dictName">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <div class="btn-group">
              <button class="btn btn-primary font-size-14"
                      type="submit">
                <i class="fa fa-filter me-2"></i>查询
              </button>
              <button class="btn btn-primary font-size-14"
                      type="button"
                      @click="clearSelectContent">
                <i class="fa fa-refresh me-2"></i>刷新
              </button>
            </div>
          </el-form-item>
        </el-form>
      </div>

    </div>

    <!-- 数据表格row -->
    <div class="row r2">
      <div class="col-12 c1">
        <el-table :data="pageInfo.list">
          <el-table-column label="头像"
                           width="100">
            <template slot-scope="scope">
              <img :src="scope.row.userAvatarUrl"
                   class="rounded"
                   width="100"
                   height="100" />
            </template>
          </el-table-column>
          <el-table-column label="用户名"
                           width="120"
                           prop="userName">
          </el-table-column>
          <el-table-column label="昵称"
                           width="120"
                           prop="userNickname">
          </el-table-column>
          <el-table-column label="性别"
                           width="100">
            <template slot-scope="scope">
              <span v-if="scope.row.userSex === 1">男</span>
              <span v-else>女</span>
            </template>
          </el-table-column>
          <el-table-column label="生日"
                           width="150"
                           prop="userBirthday">
          </el-table-column>
          <el-table-column label="所在省市"
                           width="150">
            <template slot-scope="scope">
              {{scope.row.userProvince}} {{scope.row.userCity}}
            </template>
          </el-table-column>
          <el-table-column label="邮箱"
                           width="150"
                           prop="userEmail">
          </el-table-column>
          <el-table-column label="手机号"
                           width="200"
                           prop="userPhoneNumber">
          </el-table-column>
          <el-table-column label="状态"
                           width="150">
            <template slot-scope="scope">
              {{scope.row.userStatus | userStatusFilter}}
            </template>
          </el-table-column>
          <el-table-column label="注册时间"
                           width="200"
                           prop="userRegTime">
          </el-table-column>
          <el-table-column label="操作"
                           width="250">
            <template slot-scope="scope">
              <div class="btn-group shadow">
                <button type="button"
                        class="btn btn-info btn-twitter font-size-14 text-white"
                        v-if="$store.getters['manager/managerPermission'].includes(`${$route.query.menuName}_update`)"
                        @click="updateStatusDialogOpen(scope.row.id)">
                  <i class="fa fa-pencil-square-o me-2"></i>调整状态
                </button>
                <button type="button"
                        class="btn btn-warning btn-twitter font-size-14 text-white"
                        v-if="$store.getters['manager/managerPermission'].includes(`${$route.query.menuName}_update`)"
                        @click="resetAvatar(scope.row.id)">
                  <i class="fa fa-retweet me-2"></i>重置头像
                </button>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!-- 数据表格信息及分页component -->
    <business-pagination :pageInfo="pageInfo"
                         @currentPageChangeImpl="currentPageChangeImpl"></business-pagination>

    <!-- 调整状态对话框 -->
    <div class="up-status-dialog">
      <el-dialog :title="updateStatusDialogTitle"
                 :visible.sync="updateStatusDialogVisible"
                 @close="updateStatusDialogClose"
                 v-dialogDrag>
        <el-form :model="updateStatusDialogForm"
                 label-suffix=":"
                 size="small">
          <el-form-item label="状态"
                        label-width="10rem">
            <el-select v-model="updateStatusDialogForm.userStatus"
                       placeholder="请选择"
                       class="w-75">
              <el-option v-for="item in userStatusDict"
                         :key="item.id"
                         :value="item.dictVal"
                         :label="item.dictName">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer"
             class="dialog-footer">
          <el-button type="danger"
                     icon="el-icon-close"
                     @click="updateStatusDialogVisible = false">取 消</el-button>
          <el-button type="primary"
                     icon="el-icon-check"
                     @click="submitContent">确 定</el-button>
        </div>
      </el-dialog>
    </div>

  </div>
</template>

<script>
import BusinessPagination from '@components/BusinessPagination.vue'

export default {
  name: 'BusinessUserInfo',
  components: {
    BusinessPagination
  },
  data () {
    return {
      containerShow: true,
      selectForm: {
        userNameKeyword: '',
        userSex: '',
        userPhoneNumber: '',
        userStatus: ''
      },
      searchContent: {},
      pageInfo: {},
      currentPageNum: 1,
      updateStatusDialogTitle: '',
      updateStatusDialogVisible: false,
      updateStatusDialogForm: {
        id: 0,
        userStatus: '0'
      },
      userStatusDict: []
    }
  },
  methods: {
    async getSysDict () {
      this.userStatusDict = await this.$sysDict.get('userStatus')
    },
    getPage () {
      let sendData = { ...this.searchContent }
      sendData.pageNum = this.currentPageNum
      sendData.pageSize = 10
      this.$api.get(this.$apiUrl.USER_GETPAGE, sendData, res => {
        if (res.code === 200) {
          this.pageInfo = res.data
        } else if (res.code === 102) {
          this.pageInfo = {}
        } else if (res.code === 401 || res.code === 405) {
          this.$alert(res.msg, '提示', {
            confirmButtonText: '确定'
          }).then(() => {
            if (res.code === 401) {
              this.$router.push('/manager_login')
            } else {
              this.containerShow = false
            }
          })
        } else if (res.code === 500) {
          this.$notify.error({
            title: '错误',
            message: res.msg,
            duration: 0
          })
        }
      })
    },
    selectContent () {
      this.searchContent = { ...this.selectForm }
      this.currentPageNum = 1
      this.getPage()
    },
    clearSelectContent () {
      this.$refs.selectForm.resetFields()
      this.selectContent()
    },
    currentPageChangeImpl (val) {
      this.currentPageNum = val
      this.getPage()
    },
    updateStatusDialogOpen (id) {
      this.updateStatusDialogTitle = '『调整状态窗口』'
      this.updateStatusDialogOpenAndSetVal(id)
    },
    updateStatusDialogOpenAndSetVal (id) {
      let currentUser = this.pageInfo.list.find(user => user.id === id)
      this.updateStatusDialogForm.id = currentUser.id
      this.updateStatusDialogForm.userStatus = currentUser.userStatus.toString()
      this.updateStatusDialogVisible = true
    },
    submitContent () {
      this.$api.put(this.$apiUrl.USER_UPDATESTATUS, this.$qs.stringify(this.updateStatusDialogForm), res => {
        if (res.code === 200) {
          this.$message.success(res.msg)
          this.updateStatusDialogVisible = false
        } else if (res.code === 102) {
          this.$message.error(res.msg)
        } else if (res.code === 401 || res.code === 405) {
          this.$alert(res.msg, '提示', {
            confirmButtonText: '确定'
          }).then(() => {
            if (res.code === 401) {
              this.$router.push('/manager_login')
            }
          })
        } else if (res.code === 500) {
          this.$notify.error({
            title: '错误',
            message: res.msg,
            duration: 0
          })
        }
      }, {
        'Content-type': 'application/x-www-form-urlencoded;charset=utf-8'
      })
    },
    resetAvatar (id) {
      this.$confirm('确定要重置头像吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$api.put(this.$apiUrl.USER_UPDATEAVATARDEFAULT, this.$qs.stringify({ id }), res => {
          if (res.code === 200) {
            this.$message.success(res.msg)
            this.getPage()
          } else if (res.code === 102) {
            this.$message.error(res.msg)
          } else if (res.code === 401 || res.code === 405) {
            this.$alert(res.msg, '提示', {
              confirmButtonText: '确定'
            }).then(() => {
              if (res.code === 401) {
                this.$router.push('/manager_login')
              }
            })
          } else if (res.code === 500) {
            this.$notify.error({
              title: '错误',
              message: res.msg,
              duration: 0
            })
          }
        }, {
          'Content-type': 'application/x-www-form-urlencoded;charset=utf-8'
        })
      })
    },
    updateStatusDialogClose () {
      this.getPage()
      this.updateStatusDialogForm = {
        id: 0,
        userStatus: '0'
      }
    }
  },
  mounted () {
    this.getSysDict()
    this.getPage()
  }
}
</script>

<style scoped>
/* 查询表单 */
/* el表单标签重写（颜色修改） */
.r1 .c2 /deep/ .el-form-item__label {
  color: #f8f9fa;
}

/* el表格重写 */
.r2 .c1 /deep/ .el-table,
.r2 .c1 /deep/ .el-table__expanded-cell {
  background-color: #15202b;
}
.r2 .c1 /deep/ .el-table th,
.r2 .c1 /deep/ .el-table tr {
  background-color: #15202b;
}
.r2 .c1 /deep/ .el-table--enable-row-hover .el-table__body tr:hover > td {
  background-color: #111a22;
}
.r2 .c1 /deep/ .el-table td,
.r2 .c1 /deep/ .el-table th.is-leaf {
  border-bottom: 1px solid #15202b;
}
.r2 .c1 /deep/ .el-table::before {
  background: #15202b;
}
.r2 .c1 /deep/ .el-table {
  color: #f8f9fa;
  border: 1px solid #38444d;
  border-radius: 1rem;
  padding-bottom: 1px;
}
.r2 .c1 /deep/ .el-table td,
.r2 .c1 /deep/ .el-table th {
  text-align: center;
}
.r2 .c1 /deep/ .el-table thead {
  color: #f8f9fa;
}

/* el对话框重写 */
.up-status-dialog /deep/ .el-dialog,
.up-status-dialog /deep/ .el-pager li {
  background: #192734;
  border-radius: 0.5rem;
}
.up-status-dialog /deep/ .el-dialog__title {
  color: #f8f9fa;
}

/* data-dialog表单 */
/* el表单标签重写（颜色修改） */
.up-status-dialog /deep/ .el-form-item__label {
  color: #f8f9fa;
}
</style>
