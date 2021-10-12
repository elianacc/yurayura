import request from '@api/baseRequest'
import qs from 'qs'

// 分页查询用户
function getUserPage (params, success) {
  request.get('/api/user/getPage', params, success)
}

// 修改状态（根据id）
function updateUserStatus (params, success) {
  request.put('/api/user/updateStatus', qs.stringify(params), success, {
    'Content-type': 'application/x-www-form-urlencoded;charset=utf-8'
  })
}

// 重置为默认头像（根据id）
function updateUserAvatarDefault (params, success) {
  request.put('/api/user/updateAvatarDefault', qs.stringify({ id: params }), success, {
    'Content-type': 'application/x-www-form-urlencoded;charset=utf-8'
  })
}

export { getUserPage, updateUserStatus, updateUserAvatarDefault }