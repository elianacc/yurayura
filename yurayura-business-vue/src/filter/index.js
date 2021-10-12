import store from '../store'

export default {
  sysDictFormatFilter (value, code) {
    let arrDictByCode = store.getters['dict/dictList'].filter(dict => dict.dictCode === code)
    let res = ''
    arrDictByCode.forEach(dict => {
      if (value.toString() === dict.dictVal.toString()) {
        res = dict.dictName
      }
    })
    return res
  },
  comicCurrentStatusFilter (status, currentEpisodes) {
    let arrCmStatus = store.getters['dict/dictList'].filter(dict => dict.dictCode === 'comicStatus')
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
  comicLabelShowFilter (value) {
    let arrCmLabel = value.split(',')
    arrCmLabel = arrCmLabel.filter(label => label !== '')
    return arrCmLabel.toString()
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