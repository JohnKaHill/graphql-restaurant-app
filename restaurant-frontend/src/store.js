import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex, axios)

export default new Vuex.Store({
  state: {
    orders: [],
    messages: [
      'Hello Peter',
      'Restaurant Mark',
      'Karl!',
      'Jimmy',
      'Jonnie',
    ],
    filterOptions: ["Active",
                   "Closed",
                   "All"],
    selectedFilterOption: "All"
  },
  getters: {
    filteredMessages(state) {
      if( state.selectedFilterOption == "Active") {
        return state.messages.filter( message => {
          return message.includes('a');
        });
      } else if( state.selectedFilterOption == "Closed") {
        return state.messages.filter( message => {
          return message.includes('e');
        });
      } else {
        return state.messages;
      }
    },
    filteredOrders(state) {
      return state.orders
    }
  },
  mutations: {
    SET_ORDERS (state, orders)  {
      state.orders = orders
    },
    SET_MESSAGES (state, messages) {
      state.messages = messages
    },
    SET_SELECTEDFILTEROPTION (state, selectedFilterOption) {
      state.selectedFilterOption = selectedFilterOption
    }
  },
  actions: {
    setMessage(state, messages) {
      state.commit('SET_MESSAGES', messages);
    },
    setFilterOption(state, selectedFilterOption) {
      state.commit('SET_SELECTEDFILTEROPTION', selectedFilterOption);
    },
    findAllOrders () {
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
          console.log("Orders: ")
          this.commit('SET_ORDERS', orders.data)

        })
        .catch( error => {
          console.log(error);
        })      
    },

  }
})