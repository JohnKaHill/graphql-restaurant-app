import Vue from 'vue'
import Vuex from 'vuex'


Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    orders: [
      {
        orderId: "001",
        dateCreated: "2019-01-01 12:45",
        dateEdited: null,
        tableNumber: 1,
        isOpen: false,
        meals: [
          {
            name: "Pasta",
            description: "From Italy",
            price: 9.99,
            tax: 10,
            dateCreated: "2019-01-01 12:45",
            dateEdited: "2019-01-01 12:45",
            deprecated: false,
            foodType: "main_course",
            containsMeat: false
          },
          {
            name: "Pasito",
            description: "From Spain",
            price: 19.99,
            tax: 10,
            dateCreated: "2019-01-01 13:45",
            dateEdited: null,
            deprecated: false,
            foodType: "main_course",
            containsMeat: false
          }
        ],
        drinks: [
          {
            name: "Coke",
            description: "From USA",
            price: 1.99,
            tax: 19,
            dateCreated: "2019-01-01 13:45",
            dateEdited: null,
            deprecated: false,
            beverageTyp: "softdrink",
            containsAlcohol: false
          },
          {
            name: "Veltins",
            description: "From Germany",
            price: 2.99,
            tax: 7,
            dateCreated: "2019-01-01 13:45",
            dateEdited: null,
            deprecated: false,
            beverageTyp: "beer",
            containsAlcohol: true
          }
        ],
        datePaid: null,
        paymentMethod: null
      },
      {
        orderId: "002",
        dateCreated: "2019-12-01 12:45",
        dateEdited: null,
        tableNumber: 2,
        isOpen: true,
        meals: [
          {
            name: "Pasta2",
            description: "From Italy2",
            price: 9.99,
            tax: 10,
            dateCreated: "2019-12-01 12:45",
            dateEdited: "2019-12-01 12:45",
            deprecated: false,
            foodType: "main_course",
            containsMeat: false
          },
          {
            name: "Pasito2",
            description: "From Spain2",
            price: 199.99,
            tax: 12,
            dateCreated: "2019-12-01 13:45",
            dateEdited: null,
            deprecated: false,
            foodType: "main_course",
            containsMeat: false
          }
        ],
        drinks: [
          {
            name: "Coke2",
            description: "From USA2",
            price: 1.99,
            tax: 19,
            dateCreated: "2019-12-01 13:45",
            dateEdited: null,
            deprecated: false,
            beverageTyp: "softdrink",
            containsAlcohol: false
          },
          {
            name: "Veltins2",
            description: "From Germany2",
            price: 2.99,
            tax: 7,
            dateCreated: "2019-12-01 13:45",
            dateEdited: null,
            deprecated: false,
            beverageTyp: "beer",
            containsAlcohol: true
          }
        ],
        datePaid: null,
        paymentMethod: null
      }
    ]
  },
  mutations: {

  },
  actions: {

  }
})
