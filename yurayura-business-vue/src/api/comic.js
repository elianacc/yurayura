import request from '@api/baseRequest'

// 分页查询番剧
function getComicPage (params, success) {
  request.get('/api/comic/getPage', params, success)
}

// 添加番剧
function insertComic (params, success) {
  let sendData = new FormData()
  Object.keys(params).forEach(key => {
    sendData.append(key, params[key])
  })
  request.post('/api/comic/insert', sendData, success)
}

// 修改番剧
function updateComic (params, success) {
  let sendData = new FormData()
  Object.keys(params).forEach(key => {
    sendData.append(key, params[key])
  })
  request.put('/api/comic/update', sendData, success)
}

// 批量删除番剧（根据id组）
function deleteComicBatchByIds (params, success) {
  request.delete('/api/comic/deleteBatchByIds', { ids: params }, success)
}

export { getComicPage, insertComic, updateComic, deleteComicBatchByIds }