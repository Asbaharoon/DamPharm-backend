package run.dampharm.app.utils;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class ResourceBundleUtility {

	@Autowired
	private ResourceBundleMessageSource messageSource;

	@Autowired
	private HttpServletRequest request;

	public String getMessage(String msgKey) {
		System.out.println(request.getLocale());
		return messageSource.getMessage(msgKey, null, request.getLocale());
	}

	public String getMessage(String msgKey, Locale locale) {
		return messageSource.getMessage(msgKey, null, locale);
	}

	public String getMessage(String msgKey, Object[] params, Locale locale) {
		return messageSource.getMessage(msgKey, params, locale);
	}

}
