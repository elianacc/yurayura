package pers.elianacc.yurayura.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 用户 feign client
 *
 * @author ELiaNaCc
 * @since 2022-10-14
 */
@FeignClient(value = "yurayura-cloud-user")
public interface UserFeignClient {



}
