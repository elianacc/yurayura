<template>
  <div>
    <el-checkbox-group v-model="checkedVals"
                       :fill="fill">
      <el-checkbox-button v-for="item in dictListByCode"
                          :label="item.dictVal"
                          :key="item.id"><i :class="iconClass"></i>{{item.dictName}}</el-checkbox-button>
    </el-checkbox-group>
  </div>
</template>

<script>
export default {
  name: 'SysDictCheckboxBtnGroup',
  props: {
    value: Array,
    dictCode: String,
    fill: String,
    iconClass: String
  },
  data () {
    return {
      checkedVals: this.value
    }
  },
  watch: {
    value (val) {
      this.checkedVals = val
    },
    checkedVals (val) {
      this.$emit('input', val)
    }
  },
  computed: {
    dictListByCode () {
      let dictListByCode = this.$store.getters['dict/dictList'].filter(dict => dict.dictCode === this.dictCode)
      return dictListByCode
    }
  }
}
</script>

<style scoped>
</style>