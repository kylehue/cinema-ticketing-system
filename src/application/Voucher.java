package application;

public class Voucher {
	private String code;
	public String title;
	private double discountRate = 0;
	public Voucher(String title, double discountRate) {
		this.code = Utils.createUID();
		this.title = title;
		this.discountRate = discountRate;
	}
	
	public double getDiscountRate() {
		return discountRate;
	}
	
	public String getCode() {
		return code;
	}
}
