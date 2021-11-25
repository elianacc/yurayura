import request from '@api/baseRequest'
import qs from 'qs'

// 查询权限授权树
function getSysPermissionAuthorTree (success) {
  request.get('/api/sys/permission/getPermissionAuthorTree', null, success)
}

// 分页查询系统权限
function getSysPermissionPage (params, success, warn) {
  request.get('/api/sys/permission/getPage', params, success, warn)
}

// 添加系统权限
function insertSysPermission (params, success, warn) {
  request.post('/api/sys/permission/insert', qs.stringify(params), success, warn, {
    'Content-type': 'application/x-www-form-urlencoded;charset=utf-8'
  })
}

// 修改系统权限
function updateSysPermission (params, success, warn) {
  request.put('/api/sys/permission/update', qs.stringify(params), success, warn, {
    'Content-type': 'application/x-www-form-urlencoded;charset=utf-8'
  })
}

export { getSysPermissionAuthorTree, getSysPermissionPage, insertSysPermission, updateSysPermission }