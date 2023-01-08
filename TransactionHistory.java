package my.game.app;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class TransactionHistory {
	@Id
	@SequenceGenerator(
			name = "transaction_id_sequence",
			sequenceName = "transaction_id_sequence",
			allocationSize = 1
			)
	@GeneratedValue(
			generator = "transaction_id_sequence",
			strategy = GenerationType.AUTO
			)
	private Integer transactionId;
	private Date transactionDate;
	private Float purchaseAmount;
	private Float discountedAmount;
	private Integer appUserId;
	
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Float getPurchaseAmount() {
		return purchaseAmount;
	}
	public void setPurchaseAmount(Float purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}
	public Float getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(Float discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
	public Integer getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(Integer appUserId) {
		this.appUserId = appUserId;
	}
	
}
