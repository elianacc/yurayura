package pers.elianacc.yurayura.component;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.elianacc.yurayura.feign.SysFeignClient;
import pers.elianacc.yurayura.vo.ApiResult;

import java.util.ArrayList;
import java.util.List;

/**
 * sa-token自定义权限加载接口实现类 component
 *
 * @author ELiaNaCc
 * @since 2023-08-21
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private SysFeignClient sysFeignClient;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {

        ApiResult<List<String>> apiResult = sysFeignClient.getManagerRolePermission(Integer.parseInt(loginId.toString()));
        if (apiResult.getCode() == ApiResult.SUCCESS_CODE) {
            return apiResult.getData();
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return null;
    }
}
