package common.net;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Http协议
 * @author liangyx
 * @date 2014-08-30
 */
@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpProtocol {
	public abstract int value();
}