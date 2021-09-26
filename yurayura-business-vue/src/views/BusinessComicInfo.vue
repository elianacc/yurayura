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
        <button type="button"
                class="btn btn-danger font-size-14"
                style="position: relative; top: 0.03125rem;"
                @click="deleteBatch"
                v-if="$store.getters['manager/managerPermission'].includes(`${$route.query.menuName}_deleteBatch`)">
          <i class="fa fa-trash me-2"></i>删除
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
          <el-form-item label="番剧名"
                        prop="comicName"
                        label-width="4rem">
            <el-input v-model.trim="selectForm.comicName"
                      clearable></el-input>
          </el-form-item>
          <el-form-item label="状态"
                        prop="comicStatus"
                        label-width="3rem">
            <el-select v-model="selectForm.comicStatus"
                       clearable
                       placeholder="请选择">
              <el-option v-for="item in comicStatusDict"
                         :key="item.id"
                         :value="item.dictVal"
                         :label="item.dictName">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="上架状态"
                        prop="comicShelfStatus"
                        label-width="5rem">
            <el-select v-model="selectForm.comicShelfStatus"
                       clearable
                       placeholder="请选择">
              <el-option value="1"
                         label="上架">
              </el-option>
              <el-option value="0"
                         label="下架">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="标签"
                        prop="comicTag"
                        label-width="3rem">
            <el-select v-model="selectForm.comicTag"
                       clearable
                       multiple
                       filterable
                       allow-create
                       default-first-option
                       collapse-tags
                       placeholder="请选择或输入标签">
              <el-option v-for="item in comicLabelDict"
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
        <el-table :data="pageInfo.list"
                  @selection-change="tbSelectionChange">
          <el-table-column type="selection"
                           width="55">
          </el-table-column>
          <el-table-column label="图片"
                           width="200">
            <template slot-scope="scope">
              <div class="cmImg-mask">
                <img :src="scope.row.comicImageUrl"
                     class="rounded"
                     width="165"
                     height="217" />
                <a :href="scope.row.comicLink"
                   target="_blank">
                  <div class="cmImg-mask-content"><i class="fa fa-play-circle-o fa-5x"></i><br /><br /><br /></div>
                </a>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="番剧名"
                           width="200">
            <template slot-scope="scope">
              <span>{{scope.row.comicName}}<span v-show="scope.row.comicShelfStatus === 0">(已下架)</span></span>
            </template>
          </el-table-column>
          <el-table-column label="评分"
                           width="100"
                           prop="comicScore">
          </el-table-column>
          <el-table-column label="简介"
                           width="350"
                           prop="comicContent">
          </el-table-column>
          <el-table-column label="状态"
                           width="200">
            <template slot-scope="scope">
              <span>{{scope.row.comicStatus | cmStatusFilter(scope.row.comicCurrentEpisodes, comicStatusDict)}}</span>
            </template>
          </el-table-column>
          <el-table-column label="标签"
                           width="200">
            <template slot-scope="scope">
              <span>{{scope.row.comicLabel | cmLabelFilter}}</span>
            </template>
          </el-table-column>
          <el-table-column label="放送时间"
                           width="200"
                           prop="comicTime">
          </el-table-column>
          <el-table-column label="操作"
                           width="180">
            <template slot-scope="scope">
              <button type="button"
                      class="btn btn-info btn-twitter font-size-14 text-white shadow"
                      v-if="$store.getters['manager/managerPermission'].includes(`${$route.query.menuName}_update`)"
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
          <el-form-item label="番剧名"
                        prop="comicName"
                        label-width="10rem">
            <el-input v-model.trim="dataDialogForm.comicName"
                      class="w-75"
                      maxlength="30"
                      show-word-limit></el-input>
          </el-form-item>
          <el-form-item label="放送时间"
                        prop="comicTime"
                        label-width="10rem">
            <el-date-picker type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="请选择日期"
                            v-model="dataDialogForm.comicTime"></el-date-picker>
          </el-form-item>
          <el-form-item label="评分"
                        prop="comicScore"
                        label-width="10rem">
            <el-input-number :min="1.1"
                             :precision="1"
                             :step="0.1"
                             :max="10"
                             @blur="dataDialogForm.comicScore = dataDialogForm.comicScore || 1.1"
                             v-model="dataDialogForm.comicScore"
                             class="w-50">
            </el-input-number>
          </el-form-item>
          <el-form-item label="简介"
                        prop="comicContent"
                        label-width="10rem">
            <el-input type="textarea"
                      v-model.trim="dataDialogForm.comicContent"
                      class="w-75"
                      rows="3"
                      maxlength="500"
                      show-word-limit></el-input>
          </el-form-item>
          <el-form-item label="状态"
                        prop="comicStatus"
                        label-width="10rem">
            <el-radio-group v-model="dataDialogForm.comicStatus">
              <el-radio :label="0"
                        border>已完结
              </el-radio>
              <el-radio :label="8"
                        border>更新中
              </el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="更新时间"
                        prop="comicUdTime"
                        label-width="10rem"
                        v-show="dataDialogForm.cmUdTimeShow">
            <el-select v-model="dataDialogForm.comicUdTime"
                       clearable
                       placeholder="----请选择更新时间----"
                       class="w-50">
              <el-option v-for="item in comicUpdtTimeDict"
                         :key="item.id"
                         :value="item.dictVal"
                         :label="item.dictName">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="当前话数"
                        prop="comicCurrentEpisodes"
                        label-width="10rem">
            <el-input-number :min="1"
                             :max="10000"
                             @blur="dataDialogForm.comicCurrentEpisodes = dataDialogForm.comicCurrentEpisodes || 1"
                             v-model="dataDialogForm.comicCurrentEpisodes"
                             class="w-50"></el-input-number>
          </el-form-item>
          <el-form-item label="标签"
                        prop="cmTag"
                        label-width="10rem">
            <el-checkbox-group v-model="dataDialogForm.cmTag"
                               fill="#007bff">
              <el-checkbox-button v-for="item in comicLabelDict"
                                  :label="item.dictVal"
                                  :key="item.id"><i class="fa fa-paperclip me-2"></i>{{item.dictName}}</el-checkbox-button>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="自定义标签"
                        label-width="10rem">
            <el-tag v-for="tag in dataDialogForm.customTag"
                    :key="tag"
                    effect="dark"
                    color="#007bff"
                    @close="customTagClose(tag)">
              {{tag}}
            </el-tag>
            <el-input class="input-new-tag"
                      v-focus
                      v-if="dataDialogForm.customTagInputVisible"
                      v-model.trim="dataDialogForm.customTagInput"
                      ref="customTagInput"
                      @keyup.enter.native="customTagInputConfirm"
                      @blur="customTagInputConfirm">
            </el-input>
            <el-button v-else
                       class="button-new-tag"
                       @click="showCustomTagInput">+ 新标签</el-button>
          </el-form-item>
          <el-form-item label="图片"
                        label-width="10rem">
            <el-upload action=""
                       ref="dialogComicImgUpl"
                       list-type="picture-card"
                       :auto-upload="false"
                       :limit="1"
                       :on-exceed="cmImgUplLimitTip"
                       :on-change="cmImgUplChange"
                       :on-remove="cmImgUplDel"
                       class="mb-2">
              <div class="cmImg-mask"
                   v-if="dataDialogForm.cmImgUplUrl"><img :src="dataDialogForm.cmImgUplUrl"
                     width="132"
                     height="174"
                     class="rounded">
                <div class="cmImg-mask-content"><i class="el-icon-upload2 text-light"></i></div>
              </div>
              <i class="el-icon-plus"
                 v-else></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="观看地址"
                        prop="comicLink"
                        label-width="10rem">
            <el-input v-model.trim="dataDialogForm.comicLink"
                      class="w-75"></el-input>
          </el-form-item>
          <el-form-item label="上架状态"
                        prop="comicShelfStatus"
                        label-width="10rem">
            <el-radio-group v-model="dataDialogForm.comicShelfStatus">
              <el-radio :label="1"
                        border>上架</el-radio>
              <el-radio :label="0"
                        border>下架</el-radio>
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

export default {
  name: 'BusinessComicInfo',
  components: {
    BusinessPagination
  },
  data () {
    return {
      selectForm: {
        comicName: '',
        comicStatus: '',
        comicShelfStatus: '',
        comicTag: []
      },
      searchContent: {},
      pageInfo: {},
      currentPageNum: 1,
      multipleSelection: [],
      dataDialogTitle: '',
      dataDialogVisible: false,
      dataDialogForm: {
        id: 0,
        comicName: '',
        comicScore: 1.0,
        comicTime: '',
        comicContent: '',
        comicStatus: 0,
        comicUdTime: '',
        cmUdTimeShow: false,
        comicCurrentEpisodes: 1,
        cmTag: [],
        customTag: [],
        customTagInputVisible: false,
        customTagInput: '',
        cmImgUplUrl: '',
        cmImgUplUrlTmp: '',
        comicImgFile: null,
        comicLink: '',
        comicShelfStatus: 1
      },
      dataDialogFormRule: {
        comicName: [{ required: true, message: '番剧名不能为空', trigger: 'blur' }],
        comicTime: [{ required: true, message: '放送时间不能为空', trigger: 'blur' }]
      },
      comicLabelDict: [],
      comicStatusDict: [],
      comicUpdtTimeDict: []
    }
  },
  methods: {
    async getSysDict () {
      this.comicStatusDict = await this.$sysDict.get('comicStatus')
      this.comicLabelDict = await this.$sysDict.get('comicLabel')
      this.comicUpdtTimeDict = await this.$sysDict.get('comicUpdtTime')
    },
    tbSelectionChange (val) {
      this.multipleSelection = val
    },
    getPage () {
      let sendData = { ...this.searchContent }
      sendData.pageNum = this.currentPageNum
      sendData.pageSize = 10
      this.$api.get(this.$apiUrl.COMIC_GETPAGE, sendData, res => {
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
    deleteBatch () {
      if (this.multipleSelection.length === 0) {
        this.$message.warning('请至少选择一项删除')
      } else {
        this.$confirm('确定要删除选中项吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let sendData = { ids: this.multipleSelection.map(selt => selt.id) }
          this.$api.delete(this.$apiUrl.COMIC_DELETEBATCHBYIDS, sendData, res => {
            if (res.code === 200) {
              this.$message.success(res.msg)
              this.multipleSelection = []
              this.getPage()
            } else if (res.code === 102) {
              this.$message.error(res.msg)
            }
          })
        })
      }
    },
    insertDialogOpen () {
      this.dataDialogTitle = '『添加窗口』'
      this.dataDialogVisible = true
    },
    updateDialogOpen (id) {
      this.dataDialogTitle = '『修改窗口』'
      this.dataDialogOpenAndSetVal(id)
    },
    dataDialogOpenAndSetVal (id) {
      let currentComic = this.pageInfo.list.find(comic => comic.id === id)
      Object.keys(this.dataDialogForm).forEach(key => this.dataDialogForm[key] = currentComic[key])
      this.dataDialogForm.cmImgUplUrl = currentComic.comicImageUrl
      this.dataDialogForm.cmImgUplUrlTmp = currentComic.comicImageUrl
      this.dataDialogForm.comicStatus = currentComic.comicStatus !== 0 ? 8 : 0
      this.dataDialogForm.cmUdTimeShow = currentComic.comicStatus !== 0 ? true : false
      this.dataDialogForm.comicUdTime = currentComic.comicStatus !== 0 && currentComic.comicStatus !== 8 ? currentComic.comicStatus.toString() : ''
      this.diaLogComicLabel = currentComic.comicLabel
      this.dataDialogForm.customTagInputVisible = false
      this.dataDialogForm.customTagInput = ''
      this.dataDialogVisible = true
    },
    customTagClose (tag) {
      this.dataDialogForm.customTag.splice(this.dataDialogForm.customTag.indexOf(tag), 1)
    },
    customTagInputConfirm () {
      let inputValue = this.dataDialogForm.customTagInput
      if (inputValue && this.dataDialogForm.customTag.indexOf(inputValue) === -1 && this.dataDialogForm.cmTag.indexOf(inputValue) === -1) {
        this.dataDialogForm.customTag.push(inputValue)
      } else {
        if (inputValue) {
          this.$message.warning('当前标签已存在')
        }
      }
      this.dataDialogForm.customTagInputVisible = false
      this.dataDialogForm.customTagInput = ''
    },
    showCustomTagInput () {
      if (this.dataDialogForm.customTag.length === 4) {
        this.$message.warning('最多添加4个自定义标签')
      } else {
        this.dataDialogForm.customTagInputVisible = true
      }
    },
    cmImgUplLimitTip () {
      this.$message.warning('只能上传一张图片')
    },
    cmImgUplChange (file) {
      this.$common.imgVerificat(file.raw, 102400, res => {
        if (res) {
          this.$message.warning(res)
          this.$refs.dialogComicImgUpl.clearFiles()
        } else {
          this.dataDialogForm.cmImgUplUrl = ''
          this.dataDialogForm.comicImgFile = file.raw
        }
      })
    },
    cmImgUplDel () {
      this.dataDialogForm.cmImgUplUrl = this.dataDialogForm.cmImgUplUrlTmp
      this.dataDialogForm.comicImgFile = null
    },
    submitContent () {
      this.$refs.dataDialogForm.validate(valid => {
        if (valid) {
          let sendData = new FormData()
          Object.keys(this.dataDialogForm).forEach(key => {
            sendData.append(key, this.dataDialogForm[key])
          })
          let comicLabelArr = [...this.diaLogComicLabel]
          for (let index = 0; index < 4 - this.dataDialogForm.customTag.length; index++) {
            comicLabelArr.push('')
          }
          sendData.append('comicLabel', comicLabelArr)
          if (!this.dataDialogForm.comicImgFile) {
            sendData.delete('comicImgFile')
          }
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
            this.$api.post(this.$apiUrl.COMIC_INSERT, sendData, submitCallback)
          } else {
            this.$api.put(this.$apiUrl.COMIC_UPDATE, sendData, submitCallback)
          }
        }
      })
    },
    dataDialogClose () {
      this.getPage()
      this.dataDialogForm = {
        id: 0,
        comicName: '',
        comicScore: 1.0,
        comicTime: '',
        comicContent: '',
        comicStatus: 0,
        comicUdTime: '',
        cmUdTimeShow: false,
        comicCurrentEpisodes: 1,
        cmTag: [],
        customTag: [],
        customTagInputVisible: false,
        customTagInput: '',
        cmImgUplUrl: '',
        cmImgUplUrlTmp: '',
        comicImgFile: null,
        comicLink: '',
        comicShelfStatus: 1
      }
      this.$refs.dataDialogForm.clearValidate()
      this.$refs.dialogComicImgUpl.clearFiles()
    }
  },
  watch: {
    'dataDialogForm.comicStatus' (val) {
      this.dataDialogForm.cmUdTimeShow = val === 8
      if (val === 0) {
        this.dataDialogForm.comicUdTime = ''
      }
    }
  },
  computed: {
    diaLogComicLabel: {
      get () {
        return this.dataDialogForm.cmTag.concat(this.dataDialogForm.customTag)
      },
      set (value) {
        let labelArr = value.split(',')
        this.dataDialogForm.cmTag = labelArr.slice(0, labelArr.length - 4)
        let customTagArr = labelArr.slice(labelArr.length - 4, labelArr.length)
        this.dataDialogForm.customTag = customTagArr.filter(tag => tag !== '')
      }
    }
  },
  directives: {
    // 注册一个局部的自定义指令 v-focus
    focus: {
      // 指令的定义
      inserted (el) {
        // 聚焦元素
        el.querySelector('input').focus()
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

/* 番剧图片遮罩 */
.r2 .c1 .cmImg-mask {
  width: 165px;
  height: 217px;
  position: relative;
  cursor: pointer;
}
.r2 .c1 .cmImg-mask .cmImg-mask-content {
  width: 165px;
  height: 217px;
  background: black;
  position: absolute;
  bottom: 0px;
  left: 0px;
  display: none;
  font-size: 16px;
  color: white;
  text-align: center;
  padding-top: 68px;
  border-radius: 0.25rem;
}
.r2 .c1 .cmImg-mask:hover .cmImg-mask-content {
  width: 165px;
  height: 217px;
  background: rgba(0, 0, 0, 0.5);
  position: absolute;
  bottom: 0px;
  left: 0px;
  display: block;
  font-size: 16px;
  color: white;
  text-align: center;
  padding-top: 68px;
  border-radius: 0.25rem;
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
/* el表单日期选择重写（宽度修改） */
.data-dialog /deep/ .el-date-editor.el-input,
.data-dialog /deep/ .el-date-editor.el-input__inner {
  width: 75%;
}
/* el表单单选重写 */
.data-dialog /deep/ .el-radio {
  color: #f8f9fa;
}
/* el表单自定义标签 */
.data-dialog .el-tag + .el-tag {
  margin-left: 0.5rem;
}
.data-dialog .input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
.data-dialog .button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
/* el表单上传重写 */
.data-dialog /deep/ .el-upload--picture-card {
  width: 132px;
  height: 174px;
  line-height: 174px;
}
.data-dialog /deep/ .el-upload-list--picture-card .el-upload-list__item {
  width: 132px;
  height: 174px;
}
.data-dialog /deep/ .el-upload--picture-card i {
  font-size: 30px;
}

/* 番剧上传图片遮罩 */
.data-dialog .cmImg-mask {
  width: 134px;
  height: 176px;
  position: relative;
  cursor: pointer;
  bottom: 2px;
  right: 2px;
}
.data-dialog .cmImg-mask .cmImg-mask-content {
  width: 134px;
  height: 176px;
  background: black;
  position: absolute;
  bottom: 1px;
  left: -1px;
  display: none;
  font-size: 16px;
  color: white;
  text-align: center;
  padding-top: 7px;
  border-radius: 0.25rem;
}
.data-dialog .cmImg-mask:hover .cmImg-mask-content {
  width: 134px;
  height: 176px;
  background: rgba(0, 0, 0, 0.5);
  position: absolute;
  bottom: 1px;
  left: -1px;
  display: block;
  font-size: 16px;
  color: white;
  text-align: center;
  padding-top: 7px;
  border-radius: 0.25rem;
}
</style>
