import request from '@api/baseRequest'

// 系统管理员登入
function sysManagerLogin (params, success, warn) {
  request.post('/api/sys/manager/login', JSON.stringify(params), success, warn)
}

// 系统管理员注销
function sysManagerLogout (success) {
  request.get('/api/sys/manager/logout', null, success)
}

// 获取当前登入管理员信息
function getCurrentSysManagerMsg (success) {
  request.get('/api/sys/manager/getCurrentManagerMsg', null, success)
}

// 分页查询系统管理员
function getSysManagerPage (params, success, warn) {
  request.post('/api/sys/manager/getPage', JSON.stringify(params), success, warn)
}

// 添加系统管理员
function insertSysManager (params, success, warn) {
  request.post('/api/sys/manager/insert', JSON.stringify(params), success, warn)
}

// 修改系统管理员
function updateSysManager (params, success, warn) {
  request.put('/api/sys/manager/update', JSON.stringify(params), success, warn)
}

// 判断系统管理员认证状态
function judgeManagerAuthen (success, warn) {
  request.get('/api/sys/manager/judgeAuthen', null, success, warn)
}

export {
  sysManagerLogin,
  sysManagerLogout,
  getCurrentSysManagerMsg,
  getSysManagerPage,
  insertSysManager,
  updateSysManager,
  judgeManagerAuthen
}