package run.dampharm.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@ConfigurationProperties(prefix = "dampharm.mail")
@Configuration("smtpProperties")
@Data
public class SmtpConfig {
	private String host;
	private String username;
	private String password;
	
}
