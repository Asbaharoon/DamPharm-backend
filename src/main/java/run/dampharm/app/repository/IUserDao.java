package run.dampharm.app.repository;

import org.springframework.data.repository.CrudRepository;

import run.dampharm.app.domain.User;

public interface IUserDao extends CrudRepository<User, Long>{

	public User findByUsername(String username);
	
}
