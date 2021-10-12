import request from '@api/baseRequest'

// 分页查询系统数据字典
function getSysDictPage (params, success) {
  request.get('/api/sys/dict/getPage', params, success)
}

// 添加系统数据字典
function insertSysDict (params, success) {
  request.post('/api/sys/dict/insert', JSON.stringify(params), success)
}

// 修改系统数据字典
function updateSysDict (params, success) {
  request.put('/api/sys/dict/update', JSON.stringify(params), success)
}

// 查询所有系统数据字典
function getSysDictAll (success) {
  request.get('/api/sys/dict/getAll', null, success)
}

export { getSysDictPage, insertSysDict, updateSysDict, getSysDictAll }