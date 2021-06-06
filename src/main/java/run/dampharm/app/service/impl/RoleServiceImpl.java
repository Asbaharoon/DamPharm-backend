package run.dampharm.app.service.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import run.dampharm.app.domain.Role;
import run.dampharm.app.repository.IRoleDao;
import run.dampharm.app.repository.RoleRepository;

@Service
public class RoleServiceImpl implements IRoleDao {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	
	public List<Role> getAllRoles(){
		return roleRepository.findAll();
	}

	@Override
	@Transactional
	public void initSettings(Resource resource) {
		Session session = entityManager.unwrap(Session.class);
		session.doWork(connection -> ScriptUtils.executeSqlScript(connection, resource));
	}
}
