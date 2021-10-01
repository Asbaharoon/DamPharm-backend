package run.dampharm.app.pdf;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Service
public class TemplateRenderService {

	@Autowired
	private Configuration configuration;

	public String getTemplateContent(String templatePath,Map<String, Object> model) throws IOException, TemplateException {
		StringWriter stringWriter = new StringWriter();
		configuration.getTemplate(templatePath).process(model, stringWriter);
		return stringWriter.getBuffer().toString();
	}
}
