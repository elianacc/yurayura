import request from '@api/baseRequest'

// 分页查询番剧
function getComicPage (params, success, warn) {
  request.post('/api/comic/getPage', JSON.stringify(params), success, warn)
}

// 添加番剧
function insertComic (params, success, warn) {
  let sendData = new FormData()
  Object.keys(params).forEach(key => {
    sendData.append(key, params[key])
  })
  request.post('/api/comic/insert', sendData, success, warn)
}

// 修改番剧
function updateComic (params, success, warn) {
  let sendData = new FormData()
  Object.keys(params).forEach(key => {
    sendData.append(key, params[key])
  })
  request.put('/api/comic/update', sendData, success, warn)
}

// 批量删除番剧（根据id组）
function deleteComicBatchByIds (params, success, warn) {
  request.put('/api/comic/deleteBatchByIds', JSON.stringify({ ids: params }), success, warn)
}

export { getComicPage, insertComic, updateComic, deleteComicBatchByIds }