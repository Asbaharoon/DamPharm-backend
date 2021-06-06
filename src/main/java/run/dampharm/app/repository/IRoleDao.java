package run.dampharm.app.repository;

import java.util.List;

import org.springframework.core.io.Resource;

import run.dampharm.app.domain.Role;

public interface IRoleDao {
	List<Role> getAllRoles();

	void initSettings(Resource resource);
}
