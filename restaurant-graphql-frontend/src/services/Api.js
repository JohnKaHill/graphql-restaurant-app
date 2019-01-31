import axios from 'axios'

export default() => {
  return axios.create({
    baseURL: `http://localhost:8088/`,
    withCredentials: false,
    headers: {
      'Accept': 'application/json',
      'Content-type': 'application/json'
    }
  })
}
