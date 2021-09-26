export default {
  namespaced: true,
  actions: {
    resetMenuAndTab (context) {
      context.commit('CLEAR_SIDE_MENU_DFT_ACTIVE')
      context.commit('CLEAR_EDITABLE_TABS')
      context.commit('CLEAR_EDITABLE_TABS_VALUE')
    }
  },
  mutations: {
    SET_SIDE_MENU_DFT_ACTIVE (state, value) {
      state.sideMenuDftActive = value
    },
    SET_EDITABLE_TABS (state, value) {
      state.editableTabs = [...value]
    },
    SET_EDITABLE_TABS_VALUE (state, value) {
      state.editableTabsValue = value
    },
    CLEAR_SIDE_MENU_DFT_ACTIVE (state) {
      state.sideMenuDftActive = ''
    },
    CLEAR_EDITABLE_TABS (state) {
      state.editableTabs = []
    },
    CLEAR_EDITABLE_TABS_VALUE (state) {
      state.editableTabsValue = ''
    }
  },
  state: {
    sideMenuDftActive: '',
    editableTabs: [],
    editableTabsValue: ''
  },
  getters: {
    sideMenuDftActive (state) {
      return state.sideMenuDftActive
    },
    editableTabs (state) {
      return state.editableTabs
    },
    editableTabsValue (state) {
      return state.editableTabsValue
    }
  }
}