<template>
  <div>

    <!-- 操作按钮及数据筛选表单row -->
    <div class="row mt-4 r1">

      <div class="col-2">
        <button type="button"
                class="btn btn-primary font-size-14 me-2"
                v-if="$store.getters['manager/managerPermission'].includes(`${$route.query.menuName}_insert`)"
                @click="insertDialogOpen">
          <i class="fa fa-plus-circle me-2"></i>添加
        </button>
      </div>

      <div class="col-10 c2">
        <el-form inline
                 :model="selectForm"
                 ref="selectForm"
                 label-suffix=":"
                 size="small"
                 class="float-end"
                 @submit.native.prevent="selectContent">
          <el-form-item label="管理员名"
                        prop="managerName"
                        label-width="4.5rem">
            <el-input v-model.trim="selectForm.managerName"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="状态"
                        prop="managerStatus"
                        label-width="3rem">
            <el-select v-model="selectForm.managerStatus"
                       clearable
                       placeholder="请选择">
              <el-option value="1"
                         label="启用">
              </el-option>
              <el-option value="0"
                         label="禁用">
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
          <el-table-column label="管理员名"
                           width="200"
                           prop="managerName">
          </el-table-column>
          <el-table-column label="权限"
                           width="400"
                           prop="permissionNamesStr">
          </el-table-column>
          <el-table-column label="创建时间"
                           width="200"
                           prop="managerCreateTime">
          </el-table-column>
          <el-table-column label="更新时间"
                           width="200"
                           prop="managerUpdateTime">
          </el-table-column>
          <el-table-column label="状态"
                           width="200">
            <template slot-scope="scope">
              <span v-if="scope.row.managerStatus === 1">启用</span>
              <span v-else>禁用</span>
            </template>
          </el-table-column>
          <el-table-column label="操作"
                           width="180">
            <template slot-scope="scope">
              <button type="button"
                      class="btn btn-info btn-twitter font-size-14 text-white shadow"
                      v-if="$store.getters['manager/managerPermission'].includes(`${$route.query.menuName}_update`) && scope.row.managerName !== 'admin'"
                      @click="updateDialogOpen(scope.row.id)">
                <i class="fa fa-pencil-square-o me-2"></i>修改
              </button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!-- 数据表格信息及分页component -->
    <business-pagination :pageInfo="pageInfo"
                         @currentPageChangeImpl="currentPageChangeImpl"></business-pagination>

    <!-- 数据对话框 -->
    <div class="data-dialog">
      <el-dialog :title="dataDialogTitle"
                 :visible.sync="dataDialogVisible"
                 @close="dataDialogClose"
                 v-dialogDrag>
        <el-form :model="dataDialogForm"
                 ref="dataDialogForm"
                 :rules="dataDialogFormRule"
                 hide-required-asterisk
                 inline-message
                 label-suffix=":"
                 size="small">
          <el-form-item label="管理员名"
                        prop="managerName"
                        label-width="10rem">
            <el-input v-model.trim="dataDialogForm.managerName"
                      class="w-75"
                      maxlength="20"
                      show-word-limit
                      :disabled="dataDialogForm.id !== 0"></el-input>
          </el-form-item>
          <el-form-item label="密码"
                        label-width="10rem"
                        prop="managerPassword">
            <el-input v-model.trim="dataDialogForm.managerPassword"
                      show-password
                      class="w-75"
                      :placeholder="dataDialogForm.id !== 0? '修改密码为空则使用此管理员旧密码': ''">
            </el-input>
          </el-form-item>
          <el-form-item label="权限"
                        label-width="10rem">
            <el-tree :data="permissionAuthorTreeList"
                     show-checkbox
                     default-expand-all
                     node-key="id"
                     ref="permissionAuthorTree"
                     highlight-current
                     :props="permissionAuthorTreeProps">
            </el-tree>
          </el-form-item>
          <el-form-item label="状态"
                        prop="managerStatus"
                        label-width="10rem">
            <el-radio-group v-model="dataDialogForm.managerStatus">
              <el-radio :label="1"
                        border>
                启用
              </el-radio>
              <el-radio :label="0"
                        border>
                禁用
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <div slot="footer"
             class="dialog-footer">
          <el-button type="danger"
                     icon="el-icon-close"
                     @click="dataDialogVisible = false">取 消</el-button>
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
import { Base64 } from 'js-base64'

export default {
  name: 'BusinessSysManager',
  components: {
    BusinessPagination
  },
  data () {
    let checkPassword = (rule, value, callback) => {
      if (this.dataDialogForm.id === 0 && !value) {
        return callback(new Error('密码不能为空'))
      }
      callback()
    }
    return {
      selectForm: {
        managerName: '',
        managerStatus: ''
      },
      searchContent: {},
      pageInfo: {},
      currentPageNum: 1,
      dataDialogTitle: '',
      dataDialogVisible: false,
      dataDialogForm: {
        id: 0,
        managerName: '',
        managerPassword: '',
        managerStatus: 1
      },
      dataDialogFormRule: {
        managerName: [{ required: true, message: '管理员名不能为空', trigger: 'blur' }],
        managerPassword: [{ validator: checkPassword, trigger: 'blur' }]
      },
      permissionAuthorTreeList: [],
      permissionAuthorTreeProps: {
        children: 'permissionList',
        label: 'title'
      }
    }
  },
  methods: {
    getPage () {
      let sendData = { ...this.searchContent }
      sendData.pageNum = this.currentPageNum
      sendData.pageSize = 10
      this.$api.get(this.$apiUrl.SYS_MANAGER_GETPAGE, sendData, res => {
        if (res.code === 200) {
          this.pageInfo = res.data
        } else if (res.code === 102) {
          this.pageInfo = {}
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
    insertDialogOpen () {
      this.getPermissionAuthorTree()
      this.dataDialogTitle = '『添加窗口』'
      this.dataDialogVisible = true
    },
    updateDialogOpen (id) {
      this.getPermissionAuthorTree()
      this.dataDialogTitle = '『修改窗口』'
      this.dataDialogOpenAndSetVal(id)
    },
    dataDialogOpenAndSetVal (id) {
      let currentManager = this.pageInfo.list.find(manager => manager.id === id)
      Object.keys(this.dataDialogForm).forEach(key => this.dataDialogForm[key] = currentManager[key])
      this.$nextTick(() => {
        if (currentManager.permissionIdsStr) {
          this.$refs.permissionAuthorTree.setCheckedKeys(currentManager.permissionIdsStr.split(','))
        }
      })
      this.dataDialogVisible = true
    },
    submitContent () {
      this.$refs.dataDialogForm.validate(valid => {
        if (valid) {
          let sendData = { ...this.dataDialogForm }
          if (sendData.managerPassword) {
            sendData.managerPassword = Base64.encode(sendData.managerPassword)
          }
          let checkPermIdArr = this.$refs.permissionAuthorTree.getCheckedKeys().filter(permId => permId % 1 === 0)
          sendData.permissionIdArr = checkPermIdArr
          let submitCallback = res => {
            if (res.code === 200) {
              this.$message.success(res.msg)
              if (this.dataDialogForm.id === 0) {
                this.$refs.selectForm.resetFields()
                this.searchContent = { ...this.selectForm }
                this.currentPageNum = 1
              }
              this.dataDialogVisible = false
            } else if (res.code === 102) {
              this.$message.error(res.msg)
            }
          }
          if (this.dataDialogForm.id === 0) {
            this.$api.post(this.$apiUrl.SYS_MANAGER_INSERT, this.$qs.stringify(sendData, { arrayFormat: 'repeat' }), submitCallback, {
              'Content-type': 'application/x-www-form-urlencoded;charset=utf-8'
            })
          } else {
            this.$api.put(this.$apiUrl.SYS_MANAGER_UPDATE, this.$qs.stringify(sendData, { arrayFormat: 'repeat' }), submitCallback, {
              'Content-type': 'application/x-www-form-urlencoded;charset=utf-8'
            })
          }
        }
      })
    },
    dataDialogClose () {
      this.getPage()
      this.dataDialogForm = {
        id: 0,
        managerName: '',
        managerPassword: '',
        managerStatus: 1
      }
      this.$refs.dataDialogForm.clearValidate()
      this.$refs.permissionAuthorTree.setCheckedKeys([])
    },
    getPermissionAuthorTree () {
      this.$api.get(this.$apiUrl.SYS_PERMISSION_GETPERMISSIONAUTHORTREE, null, res => {
        if (res.code === 200) {
          this.permissionAuthorTreeList = res.data
        }
      })
    }
  },
  mounted () {
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
.data-dialog /deep/ .el-dialog,
.data-dialog /deep/ .el-pager li {
  background: #192734;
  border-radius: 0.5rem;
}
.data-dialog /deep/ .el-dialog__title {
  color: #f8f9fa;
}

/* data-dialog表单 */
/* el表单标签重写（颜色修改） */
.data-dialog /deep/ .el-form-item__label {
  color: #f8f9fa;
}
/* el表单单选重写 */
.data-dialog /deep/ .el-radio {
  color: #f8f9fa;
}
/* el表单树形控件重写 */
.data-dialog /deep/ .el-tree {
  padding-top: 10px;
  padding-bottom: 10px;
}
.data-dialog /deep/ .el-tree,
.data-dialog /deep/ .el-tree-node__content:hover,
.data-dialog
  /deep/
  .el-tree--highlight-current
  .el-tree-node.is-current
  > .el-tree-node__content {
  border-radius: 0.25rem;
}
</style>
