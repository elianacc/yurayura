export default {
  imgVerificat (imgFile, sizeLimit, callback) {
    let res = ''
    if (!/.(gif|jpg|jpeg|png|GIF|JPG|JPEG|PNG)$/.test(imgFile.name)) {
      res = '图片格式必须是.gif,jpeg,jpg,png中的一种'
    } else if ((imgFile.size).toFixed(2) >= sizeLimit) {
      res = `图片不能超过${sizeLimit / 1024}KB`
    }
    callback(res)
  }
}