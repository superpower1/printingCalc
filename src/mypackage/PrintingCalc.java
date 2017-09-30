package mypackage;

public class PrintingCalc {
	
	private int blackSinglePrice;
	private int blackDoublePrice;
	private int colorSinglePrice;
	private int colorDoublePrice;
	
	PrintingCalc(int blackSinglePrice, int blackDoublePrice, int colorSinglePrice, int colorDoublePrice) {
		this.blackSinglePrice = blackSinglePrice;
		this.blackDoublePrice = blackDoublePrice;
		this.colorSinglePrice = colorSinglePrice;
		this.colorDoublePrice = colorDoublePrice;
	}
	
	private void calc(int totalPage, int colorPage, boolean isDouble) {
		
		
	}
	
	public void readFile(String filename) {
		
	}
	

	public static void main(String[] args) {
		PrintingCalc A4 = new PrintingCalc(15, 10, 25, 20);
		

	}

}
