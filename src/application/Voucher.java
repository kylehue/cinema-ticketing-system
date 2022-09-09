package application;

public class Voucher {
	private String code;
	private double discountRate = 0;
	public Voucher() {
		this.code = Utils.createUID();
		
	}
	
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}
	
	public double getDiscountRate() {
		return discountRate;
	}
	
	public String getCode() {
		return code;
	}
}
