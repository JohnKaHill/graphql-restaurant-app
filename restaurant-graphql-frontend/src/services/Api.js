import axios from 'axios'

export default() => {
  return axios.create({
    baseURL: `http://localhost:4000/graphql`,
    withCredentials: false,
    headers: {
      'Accept': 'application/json',
      'Content-type': 'application/json'
    }
  })
}
