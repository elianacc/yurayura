package pers.elianacc.yurayura.component;

import com.baomidou.lock.LockFailureStrategy;
import org.springframework.stereotype.Component;
import pers.elianacc.yurayura.exception.Lock4jException;

import java.lang.reflect.Method;

/**
 * 自定义获取lock4j锁异常处理 component
 *
 * @author ELiaNaCc
 * @since 2022-03-15
 */
@Component
public class Lock4jFailureStrategy implements LockFailureStrategy {

    @Override
    public void onLockFailure(String key, Method method, Object[] arguments) {
        // 此处可以抛出指定异常，配合全局异常拦截包装统一格式返回给调用端
        throw new Lock4jException("操作过于频繁，请稍后！");
    }
}
