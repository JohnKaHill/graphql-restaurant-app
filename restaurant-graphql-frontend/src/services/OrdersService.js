import Api from '@/services/Api'

export default {
  getOrders (params) {
    return Api().post('/')
  }
}
