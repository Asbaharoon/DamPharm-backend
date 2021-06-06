package run.dampharm.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
	}

	/**
	 * For Validate @RequestParams and @PathVariable
	 * 
	 * @return
	 */
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	/**
	 * For internationalization message resources
	 */
	@Bean
	public ResourceBundleMessageSource messageSource() {

		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasenames("error-messages/messages");
		source.setUseCodeAsDefaultMessage(true);
		source.setDefaultEncoding("UTF-8");

		return source;
	}

	/**
	 * For Internazlization Validation error message like javax.validation.NotNull
	 */
	@Bean
	public LocalValidatorFactoryBean getValidator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}

	/**
	 * 
	 * @return
	 */
//	@Bean
//	public Jackson2ObjectMapperBuilder jacksonBuilder() {
//		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
////		builder.featuresToEnable(DeserializationFeature.UNWRAP_ROOT_VALUE); // enables wrapping for root elements
//		builder.featuresToEnable(SerializationFeature.WRAP_ROOT_VALUE);
//		return builder;
//	}
}
