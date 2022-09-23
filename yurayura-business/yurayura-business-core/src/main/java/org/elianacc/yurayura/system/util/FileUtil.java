package org.elianacc.yurayura.system.util;

import org.elianacc.yurayura.enumerate.ImgUploadResultEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件操作 util
 *
 * @author ELiaNaCc
 * @since 2018-8-23
 */
@Component
public class FileUtil {

    private FileUtil(){}

    private static String uploadPath;

    @Value("${spring.servlet.multipart.location}")
    public void setUploadPath(String uploadPath) {
        FileUtil.uploadPath = uploadPath;
    }

    /**
     * 图片上传
     * 返回 "0":图片为空，"1":图片格式必须是.gif,jpeg,jpg,png中的一种，"2":图片不能超过限制，否则返回新的图片上传路径（虚拟）
     *
     * @param file 图片文件
	 * @param limit 限制大小（kb）
	 * @param category 类别 （决定上传位置）
     * @return java.lang.String
     */
    public static String imageUpload(MultipartFile file, Integer limit, String category)
            throws IllegalStateException, IOException {
        // 返回结果
        String res;
        // 上传文件不为null
        if (!(file == null)) {
            // 获取上传文件名（带后缀）
            String fileName = file.getOriginalFilename();
            // 获取上传文件后缀名
            String fileEndName = fileName != null ? fileName.substring(fileName.lastIndexOf(".")) : null;
            // 上传文件格式存在且为图片格式
            if ((fileEndName != null && fileEndName.equals(".jpg")) || (fileEndName != null && fileEndName.equals(".JPG"))
                    || (fileEndName != null && fileEndName.equals(".png")) || (fileEndName != null && fileEndName.equals(".PNG"))
                    || (fileEndName != null && fileEndName.equals(".gif")) || (fileEndName != null && fileEndName.equals(".GIF"))
                    || (fileEndName != null && fileEndName.equals(".jpeg")) || (fileEndName != null && fileEndName.equals(".JPEG"))) {
                if (file.getSize() >= limit * 1024) { // 上传文件超过限制
                    res = ImgUploadResultEnum.SIZEBEYOND.getResult();
                } else {
                    // 新文件名
                    String fileNewName;
                    // 上传文件路径
                    String path = uploadPath + "/" + category;
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
        File file = new File(uploadPath.substring(0,28) + dPath);
        if (file.isFile() && file.exists()) { // 上传文件路径不为空
            file.delete(); // 删除文件
        }
    }

}
