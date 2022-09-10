package application;

import java.util.ArrayList;

public class Vouchers {
	private static ArrayList<Voucher> vouchers = new ArrayList<Voucher>();
	
	public static Voucher generate(String title, double discountRate) {
		Voucher voucher = new Voucher(title, discountRate);
		vouchers.add(voucher);
		return voucher;
	}
	
	public static ArrayList<Voucher> getVouchers() {
		return vouchers;
	}
}
