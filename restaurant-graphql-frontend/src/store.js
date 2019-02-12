import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex, axios)

export default new Vuex.Store({
  state: {
    orders: [],
    message: ['Hello Peter',
              'Restaurant Mark',
              'Karl!',
              'Jimmy',
              'Jonnie']
  },
  mutations: {
    SET_ORDERS (state, orders)  {
      state.orders = orders
    },
    SET_MESSAGE (state, message) {
      state.message = message
    }
  },
  actions: {
    setMessage(state, message) {
      state.dispatch('SET_MESSAGE', message);
    },
    findAllOrders () {
      // eslint-disable-next-line
      console.log("Orders:1 ")
      axios
        .post('localhost:4000/graphql', {
          query : `
            query{
              findAllOrders{
                orderId
                dateCreated
                dateEdited
                tableNumber
                datePaid
                paymentMethod
                totalAmount
                drinks{
                  name
                  price
                }
              }
            }
          `
        })
        .then( orders => {
          // eslint-disable-next-line
          console.log("Orders: ")
          this.commit('SET_ORDERS', orders.data)

        })
        .catch( error => {
          // eslint-disable-next-line
          console.log(error);
        })      
    },

  }
})