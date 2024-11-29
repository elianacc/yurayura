package pers.elianacc.yurayura.bo;

import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import lombok.Data;

/**
 * EasyPoi-导入Excel校验 bo
 *
 * @author ELiaNaCc
 * @since 2024-11-20
 */
@Data
public class ExcelImportVerifyBO implements IExcelModel, IExcelDataModel {

    private Integer rowNum;

    private String errorMsg;

    @Override
    public Integer getRowNum() {
        return rowNum;
    }

    @Override
    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
