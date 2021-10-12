import Vue from 'vue'
import Vuex from 'vuex'
import createVuexAlong from 'vuex-along'

import manager from './manager'
import menutab from './menutab'
import dict from './dict'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    manager,
    menutab,
    dict
  },
  plugins: [createVuexAlong({
    name: 'yurayura-business-vuex-along',
    local: {
      list: ['menutab', 'dict'],
      isFilter: true
    },
    session: {
      list: ['menutab', 'dict']
    }
  })]
})