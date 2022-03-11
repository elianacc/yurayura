<template>
  <div>

    <!-- 操作按钮及数据筛选表单row -->
    <div class="row mt-4 r1">

      <div class="col-2">
        <button type="button"
                class="btn btn-primary font-size-14 me-2"
                v-if="$store.getters['manager/managerPermission'].includes(`${$store.getters['menutab/editableTabsValue']}_insert`)"
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
          <el-form-item label="角色名"
                        prop="roleName"
                        label-width="4.5rem">
            <el-input v-model.trim="selectForm.roleName"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="状态"
                        prop="roleStatus"
                        label-width="3rem">
            <sys-dict-select v-model="selectForm.roleStatus"
                             dictCode="enableStatus">
            </sys-dict-select>
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
          <el-table-column label="角色名"
                           width="200"
                           prop="roleName">
          </el-table-column>
          <el-table-column label="权限"
                           width="400"
                           prop="permissionNamesStr">
          </el-table-column>
          <el-table-column label="创建时间"
                           width="200"
                           prop="roleCreateTime">
          </el-table-column>
          <el-table-column label="更新时间"
                           width="200"
                           prop="roleUpdateTime">
          </el-table-column>
          <el-table-column label="状态"
                           width="200">
            <template slot-scope="scope">
              {{scope.row.roleStatus | sysDictFormatFilter('enableStatus')}}
            </template>
          </el-table-column>
          <el-table-column label="操作"
                           width="180">
            <template slot-scope="scope">
              <button type="button"
                      class="btn btn-info btn-twitter font-size-14 text-white shadow"
                      v-if="$store.getters['manager/managerPermission'].includes(`${$store.getters['menutab/editableTabsValue']}_update`) && scope.row.id !== 1"
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
          <el-form-item label="角色名"
                        prop="roleName"
                        label-width="10rem">
            <el-input v-model.trim="dataDialogForm.roleName"
                      class="w-75"
                      maxlength="20"
                      show-word-limit></el-input>
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
                        prop="roleStatus"
                        label-width="10rem">
            <sys-dict-radio-group v-model="dataDialogForm.roleStatus"
                                  dictCode="enableStatus">
            </sys-dict-radio-group>
          </el-form-item>
          <el-form-item label="tip"
                        label-width="10rem"
                        v-if="dataDialogForm.id !== 0">
            <span class="text-white">角色禁用后，拥有此角色管理员将失去此角色</span>
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
import { getSysRolePage, insertSysRole, updateSysRole } from '@api/sysRole'
import { getSysPermissionAuthorTree } from '@api/sysPermission'

export default {
  name: 'BusinessSysRole',
  components: {
    BusinessPagination
  },
  data () {
    return {
      selectForm: {
        roleName: '',
        roleStatus: ''
      },
      searchContent: {},
      pageInfo: {},
      currentPageNum: 1,
      dataDialogTitle: '',
      dataDialogVisible: false,
      dataDialogForm: {
        id: 0,
        roleName: '',
        roleStatus: 1
      },
      dataDialogFormRule: {
        roleName: [{ required: true, message: '角色名不能为空', trigger: 'blur' }]
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
      getSysRolePage(sendData, success => {
        this.pageInfo = success.data
      }, () => {
        this.pageInfo = {}
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
      let currentRole = this.pageInfo.list.find(role => role.id === id)
      Object.keys(this.dataDialogForm).forEach(key => this.dataDialogForm[key] = currentRole[key])
      this.$nextTick(() => {
        if (currentRole.permissionIdsStr) {
          this.$refs.permissionAuthorTree.setCheckedKeys(currentRole.permissionIdsStr.split(','))
        }
      })
      this.dataDialogVisible = true
    },
    submitContent () {
      this.$refs.dataDialogForm.validate(valid => {
        if (valid) {
          let sendData = { ...this.dataDialogForm }
          let checkPermIdArr = this.$refs.permissionAuthorTree.getCheckedKeys().filter(permId => permId % 1 === 0)
          sendData.permissionIdArr = checkPermIdArr
          let successCallback = success => {
            this.$message.success(success.msg)
            if (this.dataDialogForm.id === 0) {
              this.$refs.selectForm.resetFields()
              this.searchContent = { ...this.selectForm }
              this.currentPageNum = 1
            }
            this.dataDialogVisible = false
          }
          let warnCallback = warn => { this.$message.error(warn.msg) }
          if (this.dataDialogForm.id === 0) {
            insertSysRole(sendData, successCallback, warnCallback)
          } else {
            updateSysRole(sendData, successCallback, warnCallback)
          }
        }
      })
    },
    dataDialogClose () {
      this.getPage()
      this.dataDialogForm = {
        id: 0,
        roleName: '',
        roleStatus: 1
      }
      this.$refs.dataDialogForm.clearValidate()
      this.$refs.permissionAuthorTree.setCheckedKeys([])
    },
    getPermissionAuthorTree () {
      getSysPermissionAuthorTree(success => {
        this.permissionAuthorTreeList = success.data
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