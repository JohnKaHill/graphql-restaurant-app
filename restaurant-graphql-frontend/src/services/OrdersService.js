import Api from '@/services/Api'

export default {
  getOrders (params) {
    return Api().post('/'), {
      query: `
        findAllOrders
        {
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
      `
    }
  }
}
