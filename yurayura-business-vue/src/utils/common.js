import { Message } from 'element-ui'

function uploadImgIsCorrect (imgFile, sizeLimit) {
  let isCorrect = true
  if (!/.(gif|jpg|jpeg|png|GIF|JPG|JPEG|PNG)$/.test(imgFile.name)) {
    Message.warning('图片格式必须是.gif,jpeg,jpg,png中的一种')
    isCorrect = false
  } else if ((imgFile.size).toFixed(2) >= sizeLimit) {
    Message.warning(`图片不能超过${sizeLimit / 1024}KB`)
    isCorrect = false
  }
  return isCorrect
}

export { uploadImgIsCorrect }