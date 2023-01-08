package my.game.app;

import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Service;

import my.game.app.bean.MessageResponse;
import my.game.app.bean.PurchaseResponse;
import my.game.app.bean.RegisterUserResponse;
import my.game.app.bean.TopupResponse;
import my.game.app.bean.TransactionHistoryResponse;
import my.game.app.exception.BalanceInsufficientException;
import my.game.app.exception.FailToCreateUserException;

@Service
public interface GameService {
	
	RegisterUserResponse createAppUser(AppUser appUser) throws FailToCreateUserException;
	AppUser getAppUser(Integer userId);
	List<AppUser> getAppUsers();
	AppUser login(String username, String password);
	MessageResponse updatePassword(Integer userId, String password);
	
	TopupResponse topUp(Integer userId, Integer amount);
	MessageResponse subscribe(Integer userId, Integer subscriptionId) throws BalanceInsufficientException;
	PurchaseResponse purchase(Integer userId, Integer itemId) throws BalanceInsufficientException;
	
	TransactionHistoryResponse getTransactionHistory(Integer userId, String fromDate, String toDate) throws ParseException;
	void createTransactionHistory(TransactionHistory transactionHistory);
	
	void createSubscription(Subscription subscription);
	Subscription getSubscription(Integer subscriptionId);
	List<Subscription> getSubscriptions();
	
	void addItem(Item item);
	Item getItem(Integer itemId);
	List<Item> getItems();
	
}
