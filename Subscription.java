package my.game.app;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Subscription {
	
	@Id
	@SequenceGenerator(
			name = "subscription_id_sequence",
			sequenceName = "subscription_id_sequence",
			allocationSize = 1
			)
	@GeneratedValue(
			generator = "subscription_id_sequence",
			strategy = GenerationType.AUTO
			)
	private Integer subscriptionId;
	private String type;
	private Integer cost;
	private Float discount;
	private Integer topUpLimit;
	private Integer purchaseLimit;
	
	public Subscription() {
		
	}
	
	public Integer getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(Integer gameServiceId) {
		this.subscriptionId = gameServiceId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
	public Float getDiscount() {
		return discount;
	}
	public void setDiscount(Float discount) {
		this.discount = discount;
	}
	public Integer getTopUpLimit() {
		return topUpLimit;
	}
	public void setTopUpLimit(Integer topUpLimit) {
		this.topUpLimit = topUpLimit;
	}
	public Integer getPurchaseLimit() {
		return purchaseLimit;
	}
	public void setPurchaseLimit(Integer purchaseLimit) {
		this.purchaseLimit = purchaseLimit;
	}
	
	
}
