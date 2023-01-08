package my.game.app;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryRepo extends JpaRepository<TransactionHistory, Integer>{
	
	@Query("select th from TransactionHistory th where "
			+ "th.appUserId =?1 and th.transactionDate >= ?2 and th.transactionDate <= ?3")
	public List<TransactionHistory>getTransactionHistoryByDate(Integer userId, Date fromDate, Date toDate);
}
