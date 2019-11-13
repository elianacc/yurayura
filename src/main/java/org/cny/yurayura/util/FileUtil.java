package org.cny.yurayura.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件操作工具类
 *
 * @author CNY
 * @date 2018年8月23日 20:37
 */
public class FileUtil {

    /**
     * 图片上传
     * 返回 "0":图片为空，"1":图片格式必须是.gif,jpeg,jpg,png中的一种，"2":图片不能超过100KB，否则返回新的图片名
     *
     * @param request
     * @param file
     * @return java.lang.String
     */
    public static String imageUpload(HttpServletRequest request, MultipartFile file)
            throws IllegalStateException, IOException {
        // 返回结果
        String res;
        // 如果文件不为空，写入上传路径
        if (!(file == null)) {
            // 上传文件名
            String fileName = file.getOriginalFilename();
            // 获取文件后缀名
            String fileEndName = fileName != null ? fileName.substring(fileName.lastIndexOf(".")) : null;
            if ((fileEndName != null && fileEndName.equals(".jpg")) || (fileEndName != null && fileEndName.equals(".JPG")) || (fileEndName != null && fileEndName.equals(".png"))
                    || (fileEndName != null && fileEndName.equals(".PNG")) || (fileEndName != null && fileEndName.equals(".gif")) || (fileEndName != null && fileEndName.equals(".GIF"))
                    || (fileEndName != null && fileEndName.equals("jpeg")) || (fileEndName != null && fileEndName.equals("JPEG"))) {
                if (file.getSize() >= 102400) {
                    res = "2";
                } else {
                    // 新文件名
                    String fileNewName;
                    // 上传文件路径
                    String path = request.getServletContext().getRealPath("/images");
                    // 生成不重复的32位新文件名
                    fileNewName = UUID.randomUUID().toString().replace("-", "") + fileEndName;
                    File filePath = new File(path, fileNewName);
                    // 判断路径是否存在，如果不存在就创建一个
                    if (!filePath.getParentFile().exists()) {
                        filePath.getParentFile().mkdirs();
                    }
                    // 将上传文件保存到一个目标文件当中
                    file.transferTo(new File(path + File.separator + fileNewName));
                    res = fileNewName;
                }

            } else {
                res = "1";
            }

        } else {
            res = "0";
        }
        return res;
    }

    /**
     * 文件删除
     *
     * @param request
     * @param dPath
     * @return void
     */
    public static void fileDelete(HttpServletRequest request, String dPath) {
        // 上传文件路径
        String path = request.getServletContext().getRealPath("/images");
        File file = new File(path + File.separator + dPath.substring(7));
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }

}
