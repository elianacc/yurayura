package pers.elianacc.yurayura.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Map;

/**
 * EasyPoi相关 util
 *
 * @author ELiaNaCc
 * @since 2023-03-23
 */
public class EasyPoiUtil {

    /**
     * 保存按模板导出excel
     *
     * @param exportExcelName      导出excel文件名（不含后缀）
     * @param dataSet              导出数据（必须map类型）
     * @param templateExportParams 模板导出参数
     * @param saveDir              导出文件保存路径
     * @return void
     */
    public static void saveExportExcelByTemplate(String exportExcelName, Map<String, Object> dataSet
            , TemplateExportParams templateExportParams, String saveDir) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(templateExportParams, dataSet);
        File savefile = new File(saveDir);
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(saveDir + "/" + exportExcelName + ".xlsx");
        workbook.write(fos);
        fos.close();
    }

    /**
     * 按模板导出excel
     *
     * @param exportExcelName      导出excel文件名（不含后缀）
     * @param dataSet              导出数据（必须map类型）
     * @param templateExportParams 模板导出参数
     * @param response             HttpServletResponse对象
     * @return void
     */
    public static void exportExcelByTemplate(String exportExcelName, Map<String, Object> dataSet
            , TemplateExportParams templateExportParams, HttpServletResponse response) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(templateExportParams, dataSet);

        if (workbook != null) {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition"
                    , "attachment;filename=" + URLEncoder.encode(exportExcelName, "UTF-8") + ".xlsx");
            workbook.write(response.getOutputStream());
            workbook.close();
        }

    }

    /**
     * 保存按注解导出excel
     *
     * @param exportExcelName 导出excel文件名（不含后缀）
	 * @param dataSet 导出数据
	 * @param entityClass 导出实体类型
	 * @param exportParams 导出参数
	 * @param saveDir 导出文件保存路径
     * @return void
     */
    public static void saveExportExcel(String exportExcelName, Collection<?> dataSet, Class<?> entityClass
            , ExportParams exportParams, String saveDir) throws IOException {

        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,
                entityClass, dataSet);
        File savefileParentPath = new File(saveDir);
        if (!savefileParentPath.exists()) {
            savefileParentPath.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(saveDir + "/" + exportExcelName + ".xlsx");
        workbook.write(fos);
        fos.close();

    }

    /**
     * 按注解导出excel
     *
     * @param exportExcelName 导出excel文件名（不含后缀）
	 * @param dataSet 导出数据
	 * @param entityClass 导出实体类型
	 * @param exportParams 导出参数
	 * @param response HttpServletResponse对象
     * @return void
     */
    public static void exportExcel(String exportExcelName, Collection<?> dataSet, Class<?> entityClass
            , ExportParams exportParams, HttpServletResponse response) throws IOException {

        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,
                entityClass, dataSet);

        if (workbook != null) {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition"
                    , "attachment;filename=" + URLEncoder.encode(exportExcelName, "UTF-8") + ".xlsx");
            workbook.write(response.getOutputStream());
            workbook.close();
        }

    }

}
