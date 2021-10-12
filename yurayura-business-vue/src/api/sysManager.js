import request from '@api/baseRequest'
import qs from 'qs'

// 系统管理员登入
function sysManagerLogin (params, success) {
  request.post('/api/sys/manager/login', JSON.stringify(params), success)
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
function getSysManagerPage (params, success) {
  request.get('/api/sys/manager/getPage', params, success)
}

// 添加系统管理员
function insertSysManager (params, success) {
  request.post('/api/sys/manager/insert', qs.stringify(params, { arrayFormat: 'repeat' }), success, {
    'Content-type': 'application/x-www-form-urlencoded;charset=utf-8'
  })
}

// 修改系统管理员
function updateSysManager (params, success) {
  request.put('/api/sys/manager/update', qs.stringify(params, { arrayFormat: 'repeat' }), success, {
    'Content-type': 'application/x-www-form-urlencoded;charset=utf-8'
  })
}

// 判断系统管理员认证状态
function judgeManagerAuthen (success) {
  request.get('/api/sys/manager/judgeAuthen', null, success)
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