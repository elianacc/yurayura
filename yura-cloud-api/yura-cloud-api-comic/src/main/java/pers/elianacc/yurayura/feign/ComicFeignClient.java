package pers.elianacc.yurayura.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 番剧 feign client
 *
 * @author ELiaNaCc
 * @since 2022-10-09
 */
@FeignClient(value = "yura-cloud-comic")
public interface ComicFeignClient {



}
