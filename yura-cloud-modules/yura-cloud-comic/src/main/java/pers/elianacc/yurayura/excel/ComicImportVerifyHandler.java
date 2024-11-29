package pers.elianacc.yurayura.excel;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import pers.elianacc.yurayura.bo.ComicImportBO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 番剧导入校检处理器
 *
 * @author ELiaNaCc
 * @since 2024-11-20
 */
public class ComicImportVerifyHandler implements IExcelVerifyHandler<ComicImportBO> {

    @Override
    public ExcelVerifyHandlerResult verifyHandler(ComicImportBO bo) {

        if (StringUtils.isBlank(bo.getComicName())) {
            return new ExcelVerifyHandlerResult(false, "第 " + bo.getRowNum() + " 行番剧名不能为空");
        }
        if (ObjectUtils.isEmpty(bo.getComicScore())) {
            return new ExcelVerifyHandlerResult(false, "第 " + bo.getRowNum() + " 行评分不能为空");
        }
        if (ObjectUtils.isEmpty(bo.getComicStatus())) {
            return new ExcelVerifyHandlerResult(false, "第 " + bo.getRowNum() + " 行状态不能为空");
        }
        if (ObjectUtils.isEmpty(bo.getComicCurrentEpisodes())) {
            return new ExcelVerifyHandlerResult(false, "第 " + bo.getRowNum() + " 行当前话数不能为空");
        }
        if (StringUtils.isBlank(bo.getComicTime())) {
            return new ExcelVerifyHandlerResult(false, "第 " + bo.getRowNum() + " 行放送时间不能为空");
        }
        if (!StringUtils.isBlank(bo.getComicCustomLabel())) {
            if (bo.getComicCustomLabel().split(",").length > 4) {
                return new ExcelVerifyHandlerResult(false, "第 " + bo.getRowNum() + " 行自定义标签不能超过4个");
            }
        }

        // 定义日期格式
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            // 使用 LocalDate 解析字符串
            LocalDate.parse(bo.getComicTime(), dateFormatter);
        } catch (DateTimeParseException e) {
            return new ExcelVerifyHandlerResult(false, "第 " + bo.getRowNum() + " 行放送时间格式不正确");
        }

        return new ExcelVerifyHandlerResult(true);
    }

}
