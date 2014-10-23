package boot.utils.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import boot.utils.exceptions.BadRequestException;

@Aspect
@Component
@Order(1)
public class BindingResultErrorAspect {
	
	private static Log logger = LogFactory.getLog(BindingResultErrorAspect.class);

	@Before("@annotation(boot.utils.annotations.BindingResultError)")
	public void before(JoinPoint joinPoint) throws Exception {
		Object[] args = joinPoint.getArgs();

		for (Object object : args) {
			if (object instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) object;
				
				if (bindingResult.hasErrors()) {
					logger.info("BindingResult: " + bindingResult.getAllErrors());
					throw new BadRequestException(bindingResult.getAllErrors());
				}
			}
		}
	}
}
