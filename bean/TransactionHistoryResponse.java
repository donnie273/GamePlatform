package my.game.app.bean;

import java.util.List;

import my.game.app.TransactionHistory;

public class TransactionHistoryResponse {
	
	private List<TransactionHistory> transactionHistory;
	private Float totalPurchaseAmount;
	private Float totalDiscountAmount;
	private Float totalDiscountedAmount;
	private String download;
	
	public List<TransactionHistory> getTransactionHistory() {
		return transactionHistory;
	}
	public void setTransactionHistory(List<TransactionHistory> transactionHistory) {
		this.transactionHistory = transactionHistory;
	}
	public Float getTotalPurchaseAmount() {
		return totalPurchaseAmount;
	}
	public void setTotalPurchaseAmount(Float totalPurchaseAmount) {
		this.totalPurchaseAmount = totalPurchaseAmount;
	}
	public Float getTotalDiscountAmount() {
		return totalDiscountAmount;
	}
	public void setTotalDiscountAmount(Float totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}
	public Float getTotalDiscountedAmount() {
		return totalDiscountedAmount;
	}
	public void setTotalDiscountedAmount(Float totalDiscountedAmount) {
		this.totalDiscountedAmount = totalDiscountedAmount;
	}
	public String getDownload() {
		return download;
	}
	public void setDownload(String download) {
		this.download = download;
	}
	
}
