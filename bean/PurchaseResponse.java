package my.game.app.bean;

public class PurchaseResponse {
	
	private Float discountAmount;
	private Float discountedAmount;
	private Float balanceBefore;
	private Float balanceAfter;
	
	public Float getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Float discountAmount) {
		this.discountAmount = discountAmount;
	}
	public Float getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(Float discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
	public Float getBalanceBefore() {
		return balanceBefore;
	}
	public void setBalanceBefore(Float balanceBefore) {
		this.balanceBefore = balanceBefore;
	}
	public Float getBalanceAfter() {
		return balanceAfter;
	}
	public void setBalanceAfter(Float balanceAfter) {
		this.balanceAfter = balanceAfter;
	}
	
}
