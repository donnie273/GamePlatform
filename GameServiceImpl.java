package my.game.app;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.game.app.bean.MessageResponse;
import my.game.app.bean.PurchaseResponse;
import my.game.app.bean.RegisterUserResponse;
import my.game.app.bean.TopupResponse;
import my.game.app.bean.TransactionHistoryResponse;
import my.game.app.exception.BalanceInsufficientException;
import my.game.app.exception.DataNotFoundException;
import my.game.app.exception.FailToCreateUserException;

@Service
public class GameServiceImpl implements GameService{
	
	@Autowired
	private AppUserRepo appUserRepo;
	@Autowired
	private SubscriptionRepo subscriptionRepo;
	@Autowired
	private ItemRepo itemRepo;
	@Autowired
	private TransactionHistoryRepo transactionHistoryRepo;
	
	private boolean isSuccess;
	
	//AppUser API
	@Override
	public RegisterUserResponse createAppUser(AppUser appUser) throws FailToCreateUserException {
		try {
			appUser.setBalance(Float.valueOf(0));
			appUser.setSubscriptionId(1); //default free subscription plan
			appUserRepo.save(appUser);
			isSuccess = true;
			RegisterUserResponse resp = new RegisterUserResponse();
			resp.setIsSuccess(true);
			
			return resp;
		}catch(Exception ex) {
			throw new FailToCreateUserException("Fail to register user");
		}
	}
	
	@Override
	public List<AppUser> getAppUsers() {
		List<AppUser> appUsers = appUserRepo.findAll();
		
		if(appUsers == null || appUsers.isEmpty()) {
			throw new DataNotFoundException("No user found");
		}
		return appUsers;
	}

	@Override
	public AppUser getAppUser(Integer userId) {
		AppUser appUser = appUserRepo.findById(userId).get();
		
		if(appUser == null) {
			throw new DataNotFoundException("No user found");
		}
		return appUser;
	}

	@Override
	public AppUser login(String username, String password) {
		AppUser appUser = appUserRepo.findUserByUsername(username);
		if(appUser == null)
			throw new DataNotFoundException("No user found");
		if(!password.equals(appUser.getPassword()))
			throw new DataNotFoundException("Incorrect password");
		return appUser;
	}

	@Override
	@Transactional
	public TopupResponse topUp(Integer userId, Integer amount) {
		TopupResponse resp = new TopupResponse();
		AppUser appUser = appUserRepo.findById(userId).get();
		Float balanceAfter = appUser.getBalance() + amount;
		resp.setBalanceBefore(appUser.getBalance());
		appUser.setBalance(balanceAfter);
		resp.setBalanceAfter(balanceAfter);
		
		return resp;
	}

	@Override
	@Transactional
	public MessageResponse subscribe(Integer userId, Integer subscriptionId) throws BalanceInsufficientException {
		MessageResponse resp = new MessageResponse();
		AppUser user = appUserRepo.findById(userId).get();
		Subscription subscription = subscriptionRepo.findById(subscriptionId).get();
		Float deduct = user.getBalance() - subscription.getCost();
		if(deduct < 0) {
			throw new BalanceInsufficientException("Insufficient balance");
		}
		
		user.setSubscriptionId(subscriptionId);
		user.setBalance(deduct);
		resp.setMessage("Subscribed to service");
		return resp;
	}

	@Override
	@Transactional
	public PurchaseResponse purchase(Integer userId, Integer itemId) throws BalanceInsufficientException {
		PurchaseResponse purchaseResponse = new PurchaseResponse();
		AppUser user = appUserRepo.findById(userId).get();
		Subscription gameService = subscriptionRepo.findById(user.getSubscriptionId()).get();
		Item item = itemRepo.findById(itemId).get();
		Float discountAmount = item.getCost()*gameService.getDiscount();
		Float discountedAmount = item.getCost()*(1 - gameService.getDiscount());
		Float balanceBefore = user.getBalance();
		Float balanceAfter = balanceBefore - discountedAmount;
		user.setBalance(balanceAfter);
		
		if(balanceAfter < 0)
			throw new BalanceInsufficientException("Insufficient Balance");
		
		purchaseResponse.setDiscountAmount(discountAmount);
		purchaseResponse.setDiscountedAmount(discountedAmount);
		purchaseResponse.setBalanceBefore(balanceBefore);
		purchaseResponse.setBalanceAfter(balanceAfter);
		
		TransactionHistory history = new TransactionHistory();
		history.setTransactionDate(new Date());
		history.setPurchaseAmount(item.getCost());
		history.setDiscountedAmount(item.getCost()*(1 - gameService.getDiscount()));
		history.setAppUserId(userId);
		createTransactionHistory(history);
		return purchaseResponse;
	}

	@Override
	public TransactionHistoryResponse getTransactionHistory(Integer userId, String fromDateStr, String toDateStr) throws ParseException {
		float totalPurchaseAmount = 0;
		float totalDiscountedAmount = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date fromDate = sdf.parse(fromDateStr);
		Date toDate = sdf.parse(toDateStr);
		
		List<TransactionHistory> transactionHistory = transactionHistoryRepo.getTransactionHistoryByDate(userId, fromDate, toDate);
		TransactionHistoryResponse resp = new TransactionHistoryResponse();
		resp.setTransactionHistory(transactionHistory);
		for(TransactionHistory history:transactionHistory) {
			totalPurchaseAmount += history.getPurchaseAmount();
			totalDiscountedAmount += history.getDiscountedAmount();
		}
		resp.setTotalPurchaseAmount(Float.valueOf(totalPurchaseAmount));
		resp.setTotalDiscountAmount(Float.valueOf(totalPurchaseAmount - totalDiscountedAmount));
		resp.setTotalDiscountedAmount(Float.valueOf(totalDiscountedAmount));
		resp.setDownload(covertToBase64(transactionHistory));
		
		return resp;
	}

	@Override
	@Transactional
	public MessageResponse updatePassword(Integer userId, String password) {
		AppUser user = appUserRepo.findById(userId).get();
		user.setPassword(password);
		MessageResponse resp = new MessageResponse();
		resp.setMessage("Password updated");
		return resp;
	}

	@Override
	public void createSubscription(Subscription subscription) {
		subscriptionRepo.save(subscription);
		
	}

	@Override
	public Subscription getSubscription(Integer subscriptionId) {
		// TODO Auto-generated method stub
		return subscriptionRepo.findById(subscriptionId).get();
	}
	
	@Override
	public List<Subscription> getSubscriptions() {
		return subscriptionRepo.findAll();
	}

	@Override
	public void addItem(Item item) {
		itemRepo.save(item);
	}
	
	@Override
	public Item getItem(Integer itemId) {
		return itemRepo.findById(itemId).get();
	}

	public List<Item> getItems() {
		return itemRepo.findAll();
	}

	@Override
	public void createTransactionHistory(TransactionHistory transactionHistory) {
		transactionHistoryRepo.save(transactionHistory);
	}
	
	public String covertToBase64(List<TransactionHistory> transactionHistory) {
		String Base64Str;
		StringBuffer result = new StringBuffer("Transaction History\n");
		
		if(transactionHistory != null && !transactionHistory.isEmpty()) {
			for(TransactionHistory th: transactionHistory) {
				result.append(String.format("%s | %s | %s | %s \n", th.getTransactionId(),
						th.getTransactionDate(), th.getPurchaseAmount(), th.getDiscountedAmount()));
			}
		}else {
			result.append("No record found");
		}

		byte[] fileBytes = result.toString().getBytes();
		Base64Str = Base64.getEncoder().encodeToString(fileBytes);
		
		return Base64Str;
	}
	
	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
}
