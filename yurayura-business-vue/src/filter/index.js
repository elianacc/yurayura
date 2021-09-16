export default {
  cmStatusFilter (status, currentEpisodes, arrCmStatus) {
    let res = ''
    if (parseInt(status) === 0) {
      res = `已完结：全${currentEpisodes}话`
    } else {
      arrCmStatus.forEach(cmStatus => {
        if (parseInt(status) === parseInt(cmStatus.dictVal)) {
          res = `${cmStatus.dictName}：更新至第${currentEpisodes}话`
        }
      })
    }
    return res
  },
  cmLabelFilter (value) {
    let arrCmLabel = value.split(',')
    arrCmLabel = arrCmLabel.filter(label => label !== '')
    return arrCmLabel.toString()
  },
  userStatusFilter (value) {
    let userStatus
    switch (value) {
      case 0:
        userStatus = '正常'
        break
      case -3:
        userStatus = '小黑屋3天'
        break
      case -7:
        userStatus = '小黑屋一周'
        break
      case -30:
        userStatus = '小黑屋一月'
        break
      case -365:
        userStatus = '小黑屋一年'
        break
      case -999:
        userStatus = '小黑屋永久'
        break
    }
    return userStatus
  },
  permissionBelongFilter (permissionBelongSubmenuName, menuSubs) {
    let permissionBelong
    menuSubs.forEach(menuSub => {
      if (menuSub.menuName === permissionBelongSubmenuName) {
        permissionBelong = menuSub.menuTitle
      }
    })
    return permissionBelong
  }
}