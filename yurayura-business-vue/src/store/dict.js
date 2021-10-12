export default {
  namespaced: true,
  actions: {},
  mutations: {
    SET_DICT_LIST (state, value) {
      state.dictList = [...value]
    }
  },
  state: {
    dictList: []
  },
  getters: {
    dictList (state) {
      return state.dictList
    }
  }
}