package mypackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PrintingCalc {
	
	private int blackSinglePrice;
	private int blackDoublePrice;
	private int colorSinglePrice;
	private int colorDoublePrice;
	private ArrayList<PrintJob> allRows = new ArrayList<PrintJob>();
	
	PrintingCalc(int blackSinglePrice, int blackDoublePrice, int colorSinglePrice, int colorDoublePrice) {
		this.blackSinglePrice = blackSinglePrice;
		this.blackDoublePrice = blackDoublePrice;
		this.colorSinglePrice = colorSinglePrice;
		this.colorDoublePrice = colorDoublePrice;
	}
	
	private void calc(int totalPage, int colorPage, boolean isDouble) {
		
		
	}
	
	private void printCost() {
		for(int i=0; i<allRows.size(); i++) {
			PrintJob tmp = allRows.get(i);
			
			System.out.println(
					tmp.getTotalPage() + " " +
					tmp.getColorPage() + " " +
					String.valueOf(tmp.getIsDouble())
					);

		}
		
	}
	
	public void readFile(String file){
			
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = "";
	        try {
				while((line = reader.readLine())!= null){
					String[] row = new String[3];
					row = line.split(",");
					
					PrintJob pj = new PrintJob(
							Integer.parseInt(row[0].trim()), 
							Integer.parseInt(row[1].trim()), 
							Boolean.parseBoolean(row[2].trim())
									);			
					
					allRows.add(pj);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		

        printCost();
	}
	

	public static void main(String[] args) {
		PrintingCalc A4 = new PrintingCalc(15, 10, 25, 20);
		
		A4.readFile("src\\testdata\\printjobs.csv");
		
	}

}
