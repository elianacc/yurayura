import axios from 'axios'
import apiUrl from '@api/baseUrl'

export default {
  // 字典获取方法
  async get (dictCode) {
    let dictData = ''
    await axios({
      method: 'get',
      url: apiUrl.SYS_DICT_GETBYDICTCODE,
      params: { dictCode },
      responseType: 'json'
    }).then(res => {
      if (res.data.code === 200) {
        dictData = res.data.data
      } else if (res.data.code === 100) {
        console.log(res.data.msg)
      } else if (res.data.code === 500) {
        console.log(`字典编码：${dictCode}对应系统数据字典获取异常`)
      }
    })
    return dictData
  }
}