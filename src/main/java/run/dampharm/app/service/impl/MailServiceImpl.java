package run.dampharm.app.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import run.dampharm.app.model.Mail;
import run.dampharm.app.model.Mail.EmailAttachment;
import run.dampharm.app.service.MailService;

@Slf4j
@Service("mailService")
public class MailServiceImpl implements MailService {

	@Autowired
	@Qualifier("gmail")
	private JavaMailSender mailSender;

	@Async
	public void sendEmail(Mail mail) {
		try {
			MimeMessagePreparator preparator = mimeMessage -> {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true);
				message.setTo(mail.getMailTo().split("[,;]"));
				message.setFrom(mail.getMailFrom(), mail.getSenderName());
				message.setSubject(mail.getMailSubject());
				if (StringUtils.isNotBlank(mail.getMailCc()))
					message.setCc(mail.getMailCc().split("[;,]"));
				if (StringUtils.isNotBlank(mail.getMailBcc()))
					message.setBcc(mail.getMailBcc().split("[;,]"));
				message.setText(mail.getMailContent(), false);

				for (EmailAttachment attachment : mail.getAttachments()) {
					message.addAttachment(attachment.getName(), new ByteArrayResource(attachment.getContent()),
							attachment.getContentType());
				}

			};
			mailSender.send(preparator);
			log.info("Email sent successfully To {},{} with Subject {}", mail.getMailTo(), mail.getMailCc(),
					mail.getMailSubject());
		} catch (Exception ex) {
			log.error("Error during sending mail to customer:{} :Error:{}", mail.getMailTo(), ex.getMessage());
		}
	}

}