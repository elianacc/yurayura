<template>
  <div>
    <el-select v-model="selectedVal"
               clearable
               :placeholder="placeholder"
               :multiple="isMultiple"
               :filterable="isMultiple"
               :default-first-option="isMultiple"
               :collapse-tags="isMultiple"
               :allow-create="allowCreate"
               :disabled="disabled"
               :class="customClass">
      <el-option v-for="item in dictListByCode"
                 :key="item.id"
                 :value="item.dictVal"
                 :label="item.dictName">
      </el-option>
    </el-select>
  </div>
</template>

<script>
export default {
  name: 'SysDictSelect',
  props: {
    value: { type: [String, Array] },
    dictCode: String,
    placeholder: { type: String, default: '请选择' },
    isMultiple: { type: Boolean, default: false },
    allowCreate: { type: Boolean, default: false },
    disabled: { type: Boolean, default: false },
    customClass: { type: String, default: '' }
  },
  data () {
    return {
      selectedVal: this.value
    }
  },
  watch: {
    value (val) {
      this.selectedVal = val
    },
    selectedVal (val) {
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