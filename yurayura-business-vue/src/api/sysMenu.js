import request from '@api/baseRequest'

// 查询系统侧边菜单
function getSysSideMenu (success) {
  request.get('/api/sys/menu/getSysSideMenu', null, success)
}

// 查询系统子菜单（根据路径）
function getSysMenuSubByIndex (params, success, warn) {
  request.get('/api/sys/menuSub/getByIndex', { index: params }, success, warn)
}

// 查询系统菜单树形列表
function getSysMenuTreeList (success) {
  request.get('/api/sys/menu/getTreeList', null, success)
}

// 添加系统菜单
function insertSysMenu (params, success, warn) {
  request.post('/api/sys/menu/insert', JSON.stringify(params), success, warn)
}

// 修改系统菜单
function updateSysMenu (params, success, warn) {
  request.put('/api/sys/menu/update', JSON.stringify(params), success, warn)
}

// 删除系统菜单（根据id）
function deleteSysMenuById (params, success, warn) {
  request.put('/api/sys/menu/deleteById', JSON.stringify({ id: params }), success, warn)
}

// 添加系统子菜单
function insertSysMenuSub (params, success, warn) {
  request.post('/api/sys/menuSub/insert', JSON.stringify(params), success, warn)
}

// 修改系统子菜单
function updateSysMenuSub (params, success, warn) {
  request.put('/api/sys/menuSub/update', JSON.stringify(params), success, warn)
}

// 删除系统子菜单（根据id）
function deleteSysMenuSubById (params, success, warn) {
  request.put('/api/sys/menuSub/deleteById', JSON.stringify({ id: params }), success, warn)
}

// 查询所有系统子菜单
function getMenuSubAll (success) {
  request.get('/api/sys/menuSub/getAll', null, success)
}

export {
  getSysSideMenu,
  getSysMenuSubByIndex,
  getSysMenuTreeList,
  insertSysMenu,
  updateSysMenu,
  deleteSysMenuById,
  insertSysMenuSub,
  updateSysMenuSub,
  deleteSysMenuSubById,
  getMenuSubAll
}