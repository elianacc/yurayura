import request from '@api/baseRequest'

// 分页查询系统角色
function getSysRolePage (params, success, warn) {
  request.post('/api/sys/role/getPage', JSON.stringify(params), success, warn)
}

// 添加系统角色
function insertSysRole (params, success, warn) {
  request.post('/api/sys/role/insert', JSON.stringify(params), success, warn)
}

// 修改系统角色
function updateSysRole (params, success, warn) {
  request.put('/api/sys/role/update', JSON.stringify(params), success, warn)
}

// 查询所有系统角色
function getRoleAll (success) {
  request.get('/api/sys/role/getAll', null, success)
}

export {
  getSysRolePage,
  insertSysRole,
  updateSysRole,
  getRoleAll
}