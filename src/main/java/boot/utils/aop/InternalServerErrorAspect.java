package boot.utils.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import boot.utils.exceptions.InternalServerErrorException;

@Aspect
@Component
@Order(3)
public class InternalServerErrorAspect {

	private static Log logger = LogFactory.getLog(InternalServerErrorAspect.class);
	
	@AfterThrowing(pointcut = "@within(boot.utils.annotations.TryAndCatchInternalServerError)", throwing = "exception")
	public void afterThrowing(Exception exception) {
		logger.info("InternalServerError");
		throw new InternalServerErrorException();
	}
}
