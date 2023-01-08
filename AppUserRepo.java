package my.game.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Integer>{
	
	@Query("select u from AppUser u where u.username = ?1")
	public AppUser findUserByUsername(String username);
}
