package my.game.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepo 
	extends JpaRepository<Subscription, Integer>{

}
