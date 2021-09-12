package run.dampharm.app.pdf;

import java.util.Map;

import lombok.Data;

@Data
public class CTemplate {
	private String templateName;
	private String templateContent;
	private Map<String,Object> templateModel;
	private String templateRendered;

}
