package my.game.app;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class AppUser{
	
	@Id
	@SequenceGenerator(
			name = "appUser_id_sequence",
			sequenceName = "appUser_id_sequence",
			allocationSize = 1
			)
	@GeneratedValue(
			generator = "appUser_id_sequence",
			strategy = GenerationType.AUTO
			)
	private Integer userId;
	private String username;
	private String password;
	private String fullname;
	private Float balance;
	private Integer subscriptionId;
	
	public AppUser(String username, String password, String fullname) {
		this.username = username;
		this.password = password;
		this.fullname = fullname;
	}
	
	public AppUser() {
		
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public Float getBalance() {
		return balance;
	}
	public void setBalance(Float balance) {
		this.balance = balance;
	}
	public Integer getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(Integer subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
}
