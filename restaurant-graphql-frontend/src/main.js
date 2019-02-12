import Vue from 'vue'
import App from './App.vue'
// import { MdField, MdLayout, MdMenu } from 'vue-material/dist/components'
// import 'vue-material/dist/vue-material.min.css'
//
// Vue.use(MdField)
// Vue.use(MdLayout)
// Vue.use(MdMenu)

// import VueMaterial from 'vue-material'
// import 'vue-material/dist/vue-material.min.css'

// Vue.use(VueMaterial)

import router from './router'
import store from './store'

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
