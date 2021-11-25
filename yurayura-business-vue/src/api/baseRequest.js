import axios from 'axios'
import qs from 'qs'
import router from '../router'
import { MessageBox, Notification } from 'element-ui'

// 解决和后端session不同步的问题，让axios请求带上cookie
axios.defaults.withCredentials = true
// axios默认添加ajax请求头标识
axios.defaults.headers = { 'X-Requested-With': 'XMLHttpRequest', 'Content-Type': 'application/json;charset=utf-8' }

function apiAxios (method, url, params, success, warn, header) {
  axios({
    method: method,
    url: url,
    data: method === 'POST' || method === 'PUT' ? params : null,
    params: method === 'GET' || method === 'DELETE' ? params : null,
    paramsSerializer: params => {
      // 对get,delete请求进行序列化，解决get,delete无法传递数组参数的问题
      if (method === 'GET' || method === 'DELETE') {
        return qs.stringify(params, { arrayFormat: 'repeat' })
      }
    },
    headers: header
  }).then(res => {
    if (res.data.code === 200) {
      success(res.data)
    } else if (res.data.code === 102) {
      warn(res.data)
    } else if (res.data.code === 401 || res.data.code === 405) {
      MessageBox.alert(res.data.msg, '提示', {
        confirmButtonText: '确定'
      }).then(() => {
        if (res.data.code === 401) {
          router.push('/manager_login')
        } else if (res.data.code === 405 && method === 'GET') {
          router.go(-1)
        }
      })
    } else if (res.data.code === 500) {
      Notification.error({
        title: '错误',
        message: res.data.msg,
        duration: 0
      })
    }
  }).catch(err => {
    Notification.error({
      title: '错误',
      message: `axios请求错误:${err.message}`,
      duration: 0
    })
  })
}

export default {
  get (url, params, success, warn, header) {
    return apiAxios('GET', url, params, success, warn, header)
  },
  post (url, params, success, warn, header) {
    return apiAxios('POST', url, params, success, warn, header)
  },
  put (url, params, success, warn, header) {
    return apiAxios('PUT', url, params, success, warn, header)
  },
  delete (url, params, success, warn, header) {
    return apiAxios('DELETE', url, params, success, warn, header)
  }
}