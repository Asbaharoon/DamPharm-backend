package run.dampharm.app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import run.dampharm.app.model.Mail;
import run.dampharm.app.service.MailService;

@SpringBootApplication
public class DamPharmApplication {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		Mail mail = new Mail();
		mail.setMailFrom("salahsayedatwa@gmail.com");
		mail.setMailTo("salahsayedatwa2@gmail.com");
		mail.setMailSubject("Spring Boot - Email Example");
		mail.setMailContent("Learn How to send Email using Spring Boot!!!\n\nThanks\nwww.technicalkeeda.com");

		ApplicationContext ctx = SpringApplication.run(DamPharmApplication.class, args);
		MailService mailService = (MailService) ctx.getBean("mailService");
		mailService.sendEmail(mail);
	}
}
