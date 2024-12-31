package pers.elianacc.yurayura.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pers.elianacc.yurayura.enumerate.ImgUploadResultEnum;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 文件操作 util
 *
 * @author ELiaNaCc
 * @since 2018-8-23
 */
@Component
public class FileUtil {

    private static String uploadPath;

    @Value("${spring.servlet.multipart.location}")
    public void setUploadPath(String uploadPath) {
        FileUtil.uploadPath = uploadPath;
    }

    /**
     * 图片上传
     * 返回 "0":图片为空，"1":图片格式必须是.gif,jpeg,jpg,png中的一种，"2":图片不能超过限制，否则返回新的图片上传路径（虚拟）
     *
     * @param file     图片文件
     * @param limit    限制大小（kb）
     * @param category 类别 （决定上传位置）
     * @return java.lang.String
     */
    public static String imageUpload(MultipartFile file, Integer limit, String category)
            throws IOException {
        // 返回结果
        String res;
        // 上传文件不为null
        if (!(file == null)) {
            // 获取上传文件名（带后缀）
            String fileName = file.getOriginalFilename();
            // 获取上传文件后缀名
            String fileEndName = fileName != null ? fileName.substring(fileName.lastIndexOf(".")) : null;
            // 上传文件格式存在且为图片格式
            if ((fileEndName != null && fileEndName.equals(".jpg"))
                    || (fileEndName != null && fileEndName.equals(".JPG"))
                    || (fileEndName != null && fileEndName.equals(".png"))
                    || (fileEndName != null && fileEndName.equals(".PNG"))
                    || (fileEndName != null && fileEndName.equals(".gif"))
                    || (fileEndName != null && fileEndName.equals(".GIF"))
                    || (fileEndName != null && fileEndName.equals(".jpeg"))
                    || (fileEndName != null && fileEndName.equals(".JPEG"))) {
                if (file.getSize() >= limit * 1024) { // 上传文件超过限制
                    res = ImgUploadResultEnum.SIZEBEYOND.getResult();
                } else {
                    // 新文件名
                    String fileNewName;
                    // 上传文件路径
                    String path = uploadPath + "/upload/" + category;
                    // 生成不重复的32位新文件名
                    fileNewName = UUID.randomUUID().toString().replace("-", "") + fileEndName;
                    File newFile = new File(path, fileNewName);
                    if (!newFile.getParentFile().exists()) { // 上传文件路径是否存在，如果不存在就创建一个
                        newFile.getParentFile().mkdirs();
                    }
                    // 将上传文件保存到一个目标文件当中
                    file.transferTo(newFile);
                    res = "/upload/" + category + "/" + fileNewName; // 返回新文件名
                }
            } else {
                res = ImgUploadResultEnum.FORMATNOTALLOW.getResult();
            }
        } else {
            res = ImgUploadResultEnum.NULL.getResult();
        }
        return res;
    }

    /**
     * 上传文件删除
     *
     * @param dPath 删除路径（即文件上传的虚拟路径）
     * @return void
     */
    public static void fileDelete(String dPath) {
        File file = new File(uploadPath + dPath);
        if (file.isFile() && file.exists()) { // 上传文件路径不为空
            file.delete(); // 删除文件
        }
    }

    /**
     * 下载文件
     *
     * @param fileName 下载文件名
	 * @param inputStream 输入流
	 * @param fileLength 文件长度
     * @return org.springframework.http.ResponseEntity<org.springframework.core.io.InputStreamResource>
     */
    public static ResponseEntity<InputStreamResource> downloadFile(String fileName, InputStream inputStream, long fileLength)
            throws UnsupportedEncodingException {
        if (inputStream == null) {
            return ResponseEntity.noContent().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "UTF-8"));
        headers.setContentLength(fileLength);

        // 使用 InputStreamResource 包装 InputStream
        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok().headers(headers).body(resource);
    }

}
