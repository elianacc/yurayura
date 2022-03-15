package org.elianacc.yurayura.system.component;

import com.baomidou.lock.LockFailureStrategy;
import org.elianacc.yurayura.system.exception.RepeatSubmitException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 自定义获取lock4j锁异常处理
 *
 * @author ELiaNaCc
 * @since 2022-03-15
 */
@Component
public class Lock4jFailureStrategy implements LockFailureStrategy {

    @Override
    public void onLockFailure(String key, Method method, Object[] arguments) {
        // 此处可以抛出指定异常，配合全局异常拦截包装统一格式返回给调用端
        throw new RepeatSubmitException("请勿重复提交！");
    }
}
