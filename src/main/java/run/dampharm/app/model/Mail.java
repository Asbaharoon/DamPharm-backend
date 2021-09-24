package run.dampharm.app.model;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Mail {

	private String senderName;

	private String mailFrom;

	private String mailTo;

	private String mailCc;

	private String mailBcc;

	private String mailSubject;

	private String mailContent;

	private String contentType;

	private List<EmailAttachment> attachments = new ArrayList<EmailAttachment>();

	public Mail() {
		contentType = "application/pdf";
	}

	@Data
	public static class EmailAttachment {
		private String name;
		private String contentType;
		private byte[] content;
	}

}