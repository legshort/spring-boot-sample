package boot.utils.aop;

import java.util.Arrays;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import boot.persistence.domains.UserDetailsImpl;
import boot.services.CommonService;
import boot.services.UserService;
import boot.utils.StringUtil;
import boot.utils.exceptions.UnauthorizedException;

@Component
@Aspect
@Order(2)
@SuppressWarnings("rawtypes")
public class ResourceOnwerAspect {
	
	private static final String CONTROLLER = "Controller";
	private static final String SERVICE = "Service";
	private static final String ID = "Id";

	private static Log logger = LogFactory.getLog(ResourceOnwerAspect.class);
	
	@Autowired
	private UserService userService;
	
	@Before("@annotation(boot.utils.annotations.ResourceOnwer)")
	public void before(JoinPoint joinPoint) throws Exception {
		String resourceName = StringUtil.substringBefore(joinPoint.getTarget().getClass().getSimpleName(), CONTROLLER).toLowerCase();
		CommonService commonService = (CommonService) PropertyUtils.getProperty(joinPoint.getTarget(), resourceName + SERVICE);
		logger.info("ResourceName: " + resourceName);
		
		String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
		int resourceIdIndex = Arrays.asList(parameterNames).indexOf(resourceName + ID);
		Object[] args = joinPoint.getArgs();
		Long resourceId = Long.parseLong(args[resourceIdIndex].toString());
		logger.info("ResourceId: " + resourceId);
		
		UserDetailsImpl userDetailsImpl = null;
		for (Object object : args) {
			if (object instanceof UserDetailsImpl) {
				userDetailsImpl = (UserDetailsImpl) object;
				break;
			}
		}
		
		logger.info("UserDetail: " + userDetailsImpl.getUser());
		SecurityContextHolder.getContext().getAuthentication();
		boolean resourceOnwer = commonService.checkOwnership(userDetailsImpl.getUser(), resourceId);
		
		if (!resourceOnwer) {
			throw new UnauthorizedException();
		}
	}

}
