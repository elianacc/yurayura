import request from '@api/baseRequest'

// 查询权限授权树
function getSysPermissionAuthorTree (success) {
  request.get('/api/sys/permission/getPermissionAuthorTree', null, success)
}

// 分页查询系统权限
function getSysPermissionPage (params, success, warn) {
  request.post('/api/sys/permission/getPage', JSON.stringify(params), success, warn)
}

// 添加系统权限
function insertSysPermission (params, success, warn) {
  request.post('/api/sys/permission/insert', JSON.stringify(params), success, warn)
}

// 修改系统权限
function updateSysPermission (params, success, warn) {
  request.put('/api/sys/permission/update', JSON.stringify(params), success, warn)
}

export { getSysPermissionAuthorTree, getSysPermissionPage, insertSysPermission, updateSysPermission }