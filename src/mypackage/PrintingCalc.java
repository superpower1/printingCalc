/*
* Reads print jobs from a csv file and calculate the cost of each job
* as well as the total cost and print out the job details to the console
* 
* Jobs in the csv file should be: number, number, true/false
* The first number indicates the total page to print
* The second number indicates how many pages will be printed in color
* The third parameter indicates that whether printing is double sided
* (true -> double sided, false -> single sided)
* 
* @author Haofu Zhu
* @version 1.0
*/

package mypackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class PrintingCalc {
	
	private int blackSinglePrice;
	private int blackDoublePrice;
	private int colorSinglePrice;
	private int colorDoublePrice;
	
	private int totalCost;
	
	private ArrayList<PrintJob> allRows;
	
	private ArrayList<Integer> eachJobCost;
	
	PrintingCalc(int blackSinglePrice, int blackDoublePrice, int colorSinglePrice, int colorDoublePrice) {
		this.blackSinglePrice = blackSinglePrice;
		this.blackDoublePrice = blackDoublePrice;
		this.colorSinglePrice = colorSinglePrice;
		this.colorDoublePrice = colorDoublePrice;
		allRows = new ArrayList<PrintJob>();
		eachJobCost = new ArrayList<Integer>();
		totalCost = 0;
	}
	
	int getTotalCost() {
		return totalCost;
	}
	
	/**
	* Calculates each job's cost using ArrayList allRows and store results in ArrayList eachJobCost
	* Add up all costs and save to totalCost
	*/
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
	
	/**
	* Prints out the job details and job cost for each job to the console
	*/
	private void printCost() {

		String leftAlignFormat = "| %-6d | %-10d | %-10d | %-13s | %-8s |%n";

		System.out.format("+--------+------------+------------+---------------+----------+%n");
		System.out.format("| Job ID | Total Page | Color Page | Double/Single |   Cost   |%n");
		System.out.format("+--------+------------+------------+---------------+----------+%n");
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
		System.out.format("+--------+------------+------------+---------------+----------+%n");
		
		System.out.println("Total Cost: $" + String.format("%.2f", totalCost/100.00));
		
	}
	
	/**
	* Reads in the csv file and check if data are valid for calculating
	* 
	* @param file the file directory
	*/
	public void readFile(String file){
		
		Boolean success = true;
			
		BufferedReader reader;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = "";
			int lineNum = 0;
			
	        try {
				while((line = reader.readLine())!= null){
					lineNum++;
					
					String[] row = new String[3];
					row = line.split(",");
					
					/*
					 * Check the number of parameters
					 * For cases that have more than three parameters in a line, 
					 * only the first three parameters are valid
					 */
					if(row.length < 3) {
						System.out.println("Missing parameters in line "+ lineNum +" !");
						System.out.println("Please use: number, number, true/false");
						success = false;
						break;
					}

					int totalPage;
					int colorPage;
					Boolean isDouble;
					
					try{
						totalPage = Integer.parseInt(row[0].trim());
						if(totalPage<0 || totalPage>2000) {
							throw new RuntimeException("Number of pages should be between 0 and 2000");
						}
					}
					catch(RuntimeException e){
						System.out.println("Invalid number of total pages in line "+ lineNum +" !");
						System.out.println(e.getMessage());
						success = false;
						break;
					}
					
					try{				
						colorPage = Integer.parseInt(row[1].trim());
						if(colorPage > totalPage) {
							throw new RuntimeException("Number of Color pages should be less than total pages !");
						}
					}
					catch(RuntimeException e){
						System.out.println("Invalid number of color pages in line "+ lineNum +" !");
						System.out.println(e.getMessage());
						success = false;
						break;
					}
					
					
					// Use Regular Expression to Test whether the third parameter is true/false
					String pattern = "^(true)|(false)$";					 
				    boolean isMatch = Pattern.matches(pattern, row[2].trim());
					
					if (isMatch){
						isDouble = Boolean.parseBoolean(row[2].trim());
					}
					else {
						System.out.println("Invalid expression in line "+ lineNum +" !");
						System.out.println("Please use true or false to indicate whether printing is double sided!");
						success = false;
						break;
					}
					
					PrintJob pj = new PrintJob(totalPage, colorPage, isDouble);			
					
					allRows.add(pj);
				}
		        
			} catch (IOException e) {
				System.out.println(e.getMessage());
				success = false;
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			success = false;
		}
			 	
		if(success) {
			calc();
			printCost();
		}
		
		
	}
	

	public static void main(String[] args) {
		
		for(int i=1; i<=11; i++) {
			PrintingCalc A4 = new PrintingCalc(15, 10, 25, 20);
			System.out.println("Test case "+i);
			A4.readFile("src\\testdata\\mytest"+ i +".csv");
			System.out.println("\n");
		}
		
		PrintingCalc A4 = new PrintingCalc(15, 10, 25, 20);
		A4.readFile("src\\testdata\\printJobs.csv");
		
	}

}
