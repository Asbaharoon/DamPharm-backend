package run.dampharm.app;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.ServletContextAware;

import lombok.extern.slf4j.Slf4j;
import run.dampharm.app.domain.Role;
import run.dampharm.app.repository.IRoleDao;

/**
 * ssatwa
 * 
 */
@Slf4j
@Order(2)
@Component
public class ContextStartup implements ApplicationRunner, ServletContextAware {

	@Autowired
	IRoleDao roleService;

	private ServletContext servletContext;

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		log.info("initialization ...");
		reloadOptions(true);
		log.info("OK, completed");
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Transactional
	public void reloadOptions(boolean startup) {
		List<Role> roles = roleService.getAllRoles();

		log.info("find roles ({})...", roles.size());

		if (startup && CollectionUtils.isEmpty(roles)) {
			try {
				log.info("init roles...");
				Resource resource = new ClassPathResource("/db/data.sql");
				roleService.initSettings(resource);
			} catch (Exception e) {
				log.error("------------------------------------------------------------");
				log.error("-          ERROR: The database is not initialized          -");
				log.error("------------------------------------------------------------");
				log.error(e.getMessage(), e);
				System.exit(1);
			}
		}

	}

	public void initDb(Resource resource) {

	}

}
