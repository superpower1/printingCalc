package mypackage;

public class PrintJob {
	private int totalPage;
	private int colorPage;
	private boolean isDouble;
	
	PrintJob(int totalPage, int colorPage, boolean isDouble) {
		this.totalPage = totalPage;
		this.colorPage = colorPage;
		this.isDouble = isDouble;
	}
	
	int getTotalPage() {
		return totalPage;
		
	}
	int getColorPage() {
		return colorPage;
		
	}
	boolean getIsDouble() {
		return isDouble;
		
	}
}
