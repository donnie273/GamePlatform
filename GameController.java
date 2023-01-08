package my.game.app;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import my.game.app.bean.MessageResponse;
import my.game.app.bean.PurchaseResponse;
import my.game.app.bean.TopupResponse;
import my.game.app.bean.TransactionHistoryResponse;
import my.game.app.exception.BalanceInsufficientException;
import my.game.app.exception.DataNotFoundException;
import my.game.app.exception.FailToCreateUserException;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class GameController {

	@Autowired
	private GameServiceImpl gameServiceImpl;

	//AppUser API
	@PostMapping(path = "/appuser/save", consumes = "application/x-www-form-urlencoded")
	public ResponseEntity<?> createAppUser(AppUser appUser) {
		ResponseEntity<?> resp;
		try {
			resp =  ResponseEntity.ok().body(gameServiceImpl.createAppUser(appUser));
		}catch(FailToCreateUserException ex) {
			resp = ((BodyBuilder) ResponseEntity.notFound().header("messages", ex.getMessage())).body(null);
		}
		return resp;
	}
	
	@GetMapping("/appuser/{userId}") 
	public ResponseEntity<AppUser> getAppUser(@PathVariable Integer userId) { 
		ResponseEntity<AppUser> resp;
		try {
			resp =  ResponseEntity.ok().body(gameServiceImpl.getAppUser(userId));
		}catch(DataNotFoundException ex) {
			resp = ((BodyBuilder) ResponseEntity.notFound().header("messages", ex.getMessage())).body(null);
		}
		return resp;
	}
	
	@GetMapping("/appusers")
	public ResponseEntity<List<AppUser>> getAppUsers(){
		ResponseEntity<List<AppUser>> resp;
		try {
			resp =  ResponseEntity.ok().body(gameServiceImpl.getAppUsers());
		}catch(DataNotFoundException ex) {
			resp = ((BodyBuilder) ResponseEntity.notFound().header("messages", ex.getMessage())).body(null);
		}
		return resp;
	}
	 
	@PostMapping(path = "/userlogin", consumes = "application/x-www-form-urlencoded")
	public ResponseEntity<?> login
		(LoginRequestBody loginRequestBody){
		AppUser appUser = gameServiceImpl.login(loginRequestBody.getUsername(), String.valueOf(loginRequestBody.getPassword()));
		if(appUser == null)
			return ResponseEntity.badRequest().header("message", "incorrect username or password").build();
		return ResponseEntity.ok().body(appUser);
	}
	
	@PostMapping(path = "/updatePassword", consumes = "application/x-www-form-urlencoded")
	public ResponseEntity<?> updatePassword(@RequestParam Integer userId, 
			updatePasswordRequestBody updatePasswordRequestBody) {
		MessageResponse resp = gameServiceImpl.updatePassword(userId, updatePasswordRequestBody.getPassword());
		return ResponseEntity.ok().body(resp);
	}
	
	@GetMapping("/topup")
	public ResponseEntity<?> topUp(@RequestParam Integer userId, @RequestParam Integer amount) {
		TopupResponse resp = gameServiceImpl.topUp(userId, amount);
		return ResponseEntity.ok().body(resp);
	}
	
	@GetMapping("/purchase/{itemId}")
	public ResponseEntity<?> purchase(@PathVariable Integer itemId, @RequestParam Integer userId) {
		PurchaseResponse resp = new PurchaseResponse();
		
		try {
			resp = gameServiceImpl.purchase(userId, itemId);
		}catch(BalanceInsufficientException ex) {
			return ResponseEntity.badRequest().header("message", ex.getMessage()).build();
		}
		return ResponseEntity.ok().body(resp);
	}
	
	@GetMapping("/subscribe/{subscriptionId}")
	public ResponseEntity<?> subscribeService(@RequestParam Integer userId, 
			@PathVariable Integer subscriptionId) {
		MessageResponse resp = new MessageResponse();
		try {
			resp = gameServiceImpl.subscribe(userId, subscriptionId);
			return ResponseEntity.ok().body(resp);
		}catch (BalanceInsufficientException ex) {
			resp.setMessage("Insufficient Balance");
			return ResponseEntity.badRequest().body(resp);
		}
	}
	
	@GetMapping("/subscriptions") 
	public ResponseEntity<List<Subscription>> getSubscriptions() { 
		return ResponseEntity.ok().body(gameServiceImpl.getSubscriptions()); 
	}
	
	@GetMapping("/subscription/{subscriptionId}")
	public ResponseEntity<Subscription> getSubscription(@PathVariable Integer subscriptionId) { 
		return ResponseEntity.ok().body(gameServiceImpl.getSubscription(subscriptionId)); 
	}
	
	@PostMapping("/subscription/save")
	public ResponseEntity<?> createGameService(@RequestBody Subscription subscription) {
		gameServiceImpl.createSubscription(subscription);
		return ResponseEntity.created(null).build();
	}
	
	@GetMapping("/items") 
	public ResponseEntity<List<Item>> getItems() { 
		return ResponseEntity.ok().body(gameServiceImpl.getItems()); 
	}
	
	@GetMapping("/item/{itemId}") 
	public ResponseEntity<Item> getItem(@PathVariable Integer itemId) { 
		return ResponseEntity.ok().body(gameServiceImpl.getItem(itemId)); 
	}
	
	@PostMapping("/item/save")
	public ResponseEntity<?> addItem(@RequestBody Item item) {
		gameServiceImpl.addItem(item);
		return ResponseEntity.created(null).build();
	}
	
	@GetMapping("/history")
	public ResponseEntity<?> getTransactionHistory(@ RequestParam Integer userId,
			@RequestParam String fromDate, @RequestParam String toDate) {
		TransactionHistoryResponse resp = new TransactionHistoryResponse();
		try {
			resp = gameServiceImpl.getTransactionHistory(userId, fromDate, toDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().body(resp);
	}


}

class LoginRequestBody{
	public String username;
	public String password;
	
	public LoginRequestBody() {
	}
	public LoginRequestBody(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}

class updatePasswordRequestBody{
	private String password;
	
	public updatePasswordRequestBody() {
	}
	public updatePasswordRequestBody(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
