import request from '@api/baseRequest'

// 分页查询用户
function getUserPage (params, success, warn) {
  request.post('/api/user/getPage', JSON.stringify(params), success, warn)
}

// 修改状态（根据id）
function updateUserStatus (params, success, warn) {
  request.put('/api/user/updateStatus', JSON.stringify(params), success, warn)
}

// 重置为默认头像（根据id）
function updateUserAvatarDefault (params, success, warn) {
  request.put('/api/user/updateAvatarDefault', JSON.stringify({ id: params }), success, warn)
}

export { getUserPage, updateUserStatus, updateUserAvatarDefault }