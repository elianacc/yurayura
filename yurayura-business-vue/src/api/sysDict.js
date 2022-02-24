import request from '@api/baseRequest'

// 分页查询系统数据字典
function getSysDictPage (params, success, warn) {
  request.post('/api/sys/dict/getPage', JSON.stringify(params), success, warn)
}

// 添加系统数据字典
function insertSysDict (params, success, warn) {
  request.post('/api/sys/dict/insert', JSON.stringify(params), success, warn)
}

// 修改系统数据字典
function updateSysDict (params, success, warn) {
  request.put('/api/sys/dict/update', JSON.stringify(params), success, warn)
}

// 查询所有系统数据字典
function getSysDictAll (success, warn) {
  request.get('/api/sys/dict/getAll', null, success, warn)
}

export { getSysDictPage, insertSysDict, updateSysDict, getSysDictAll }