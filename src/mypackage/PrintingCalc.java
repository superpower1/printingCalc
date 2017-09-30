package mypackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PrintingCalc {
	
	private int blackSinglePrice;
	private int blackDoublePrice;
	private int colorSinglePrice;
	private int colorDoublePrice;
	
	private int totalCost;
	
	private ArrayList<PrintJob> allRows = new ArrayList<PrintJob>();
	
	private ArrayList<Integer> eachJobCost = new ArrayList<Integer>();
	
	PrintingCalc(int blackSinglePrice, int blackDoublePrice, int colorSinglePrice, int colorDoublePrice) {
		this.blackSinglePrice = blackSinglePrice;
		this.blackDoublePrice = blackDoublePrice;
		this.colorSinglePrice = colorSinglePrice;
		this.colorDoublePrice = colorDoublePrice;
	}
	
	private void calc() {
		for(int i=0; i<allRows.size(); i++) {
			PrintJob job = allRows.get(i);
			int colorPage = job.getColorPage();
			int blackPage = job.getTotalPage() - colorPage;
			int singleJobCost = 0;
			if(job.getIsDouble()) {
				singleJobCost = blackPage*blackDoublePrice + colorPage*colorDoublePrice;
			}
			else {
				singleJobCost = blackPage*blackSinglePrice + colorPage*colorSinglePrice;
			}
			
			eachJobCost.add(singleJobCost);
			
			totalCost += singleJobCost;
		}
		
	}
	
	private void printCost() {

		String leftAlignFormat = "| %-6d | %-10d | %-10d | %-13s | %-6s |%n";

		System.out.format("+--------+------------+------------+---------------+--------+%n");
		System.out.format("| Job ID | Total Page | Color Page | Double/Single |  Cost  |%n");
		System.out.format("+--------+------------+------------+---------------+--------+%n");
		for (int i = 0; i < allRows.size(); i++) {
			PrintJob tmp = allRows.get(i);
			
			String cost = "$"+String.format("%.2f", eachJobCost.get(i)/100.00);
			String DoubleOrSingle;
			if(tmp.getIsDouble()) {
				DoubleOrSingle = "Double";
			}
			else {
				DoubleOrSingle = "Single";
			}
			
		    System.out.printf(leftAlignFormat, i+1, tmp.getTotalPage(), tmp.getColorPage(), DoubleOrSingle, cost);
		}
		System.out.format("+--------+------------+------------+---------------+--------+%n");
		
		System.out.println("Total Cost: $" + String.format("%.2f", totalCost/100.00));
		
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
		
		calc();
        printCost();
	}
	

	public static void main(String[] args) {
		PrintingCalc A4 = new PrintingCalc(15, 10, 25, 20);
		
		A4.readFile("src\\testdata\\printjobs.csv");
		
	}

}
