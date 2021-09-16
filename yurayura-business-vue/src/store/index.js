import Vue from 'vue'
import Vuex from 'vuex'
import createVuexAlong from 'vuex-along'

import manager from './manager'
import menutab from './menutab'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    manager,
    menutab
  },
  plugins: [createVuexAlong({
    name: 'yurayura-business-vuex-along',
    local: {
      list: ['menutab'],
      isFilter: true
    },
    session: {
      list: ['menutab']
    }
  })]
})