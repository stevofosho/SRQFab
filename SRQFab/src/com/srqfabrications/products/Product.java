package com.srqfabrications.products;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;
import static java.nio.file.StandardCopyOption.*;

public class Product extends BaseProduct {

	private static final String INPUT_FILEPATH = "inputFiles\\";
	private static final String PROCESSED_FILEPATH = "processed\\";
	private static final String OUTPUT_FILEPATH = "outputFiles\\";
	private static String fileName;
	private static String skuNumber;
	private static double basePrice = 0;
	private static int skuDynamic = 0;
	
	
	
	public static void main(String[] args) {
		
		
		//Grab all files in inputFile directory
		File dir = new File(INPUT_FILEPATH);
		File[] files = dir.listFiles();
		
		if(files != null) {
			//Process each file, then move it to our processed directory.
			for(File file : files){
				try {
					processFile(file);
					Path sourcePath = Paths.get(INPUT_FILEPATH + fileName);
				    Path destinationPath = Paths.get(PROCESSED_FILEPATH + fileName);   
				    
				    File processedDir = new File(PROCESSED_FILEPATH);
				    if(!processedDir.exists())
				    	processedDir.mkdir();
				    
				    //Using java.nio (New I/O) library's File.move() method to handle file movement.
					Files.move(sourcePath, destinationPath, REPLACE_EXISTING);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}else {
			
		}
	}
	
	
	//Recursive function that finds each unique variation of the Product that are provided from our input file.
	//Once a unique variation is found, it will append it to a formatted .csv file.
	public static void combine(Vector<Vector<Option>> vec, int index, String upperText, double addOnPrice, FileWriter writer)
	   {
	       if(index==vec.size())return;
	       for(Option i : vec.elementAt(index)){
		       combine(vec, index + 1, upperText + i.getName() + "-", addOnPrice + i.getPrice(), writer);
		       if(index == vec.size() - 1) {
		    	   skuDynamic++;   	
		    	   String[] parts = (upperText + i.getName()).split("-");
		    	   for(int j = 0; j < parts.length; j++){System.out.println(parts[j]);}
		    	   try {
		    		   //writer.write("SKU 96807-" + skuDynamic + ",");
		    		   writer.write(skuNumber + "-" + skuDynamic + ",");
		    		   for(int j = 0; j < parts.length; j++)
		    			   writer.write(parts[j] + ",");
		    		   writer.write(basePrice + addOnPrice + i.getPrice() + "\n");

			    	   System.out.println(skuNumber + "-" + skuDynamic);
		    	   } catch(Exception e) {
		    		   e.printStackTrace();
		    	   }
		       }

	       }
	   }
	   
	
	   //Creates new .csv file using the name of the input file. Calls combine() recursive function to 
	   //append all product variations to this file. 
	   public static void writeToFile(Vector<Vector<Option>> vec, Vector<String> categories) {
		   try {
			      FileWriter myWriter = new FileWriter(OUTPUT_FILEPATH + fileName.substring(0, fileName.indexOf(".")) + ".csv");
			      myWriter.write("Sku,");
			      for(String s : categories) 
			    	  myWriter.write(s + ",");
			      
			      myWriter.write("Price\n");
			      
			      String upperText = "";
			      double addOnPrice = 0;
			      combine(vec, 0, upperText, addOnPrice, myWriter);
			      myWriter.close();
			      System.out.println("Successfully wrote to the file.");
			  
			    } catch (IOException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			    }
	   }
	   
	   
	   //Leverages the java.io library to read text file
	   public static void processFile(File file) {
		   try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			   fileName = file.getName();
			   String line = br.readLine();
			   skuNumber = line.substring(0, line.indexOf("|"));
			   line = line.substring(line.indexOf("|") + 1);
			   basePrice = Double.valueOf(line.substring(0, line.indexOf("|"))); 
			   
			    Vector<String> categories = new Vector<String>();
			    //Vector of Option vectors
			    Vector<Vector<Option>> sets = new Vector<Vector<Option>>();
			    
			    line = br.readLine();
			    while (line != null) {
			    	//Add the category that is prefixed at each line
			    	categories.add(line.substring(0, line.indexOf(":")));
			    	System.out.println(line.substring(0, line.indexOf(":")));
			    	
			    	//Get the options after the prefix
			    	String listOfOptions = line.substring(line.indexOf(":") + 1);
			    	System.out.println(listOfOptions);
			    	Vector<Option> vec = new Vector<Option>();
			    	while(listOfOptions.indexOf("|") != -1) {
			    		String name = listOfOptions.substring(0, listOfOptions.indexOf("+"));
			    		double price = Double.valueOf(listOfOptions.substring(listOfOptions.indexOf("+") + 1, listOfOptions.indexOf("|")));
			    		Option option = new Option(name, price);
			    		vec.add(option);
			    		listOfOptions = listOfOptions.substring(listOfOptions.indexOf("|") + 1);
			    	}
			    	sets.add(vec);
			    	line = br.readLine();
			    }
			    
			    for(int i = 0; i < sets.size(); i++) {
			    	for(int j = 0; j < sets.elementAt(i).size(); j++)
			    		System.out.println(sets.elementAt(i).elementAt(j));
			    }
			    
			    System.out.println();
			    System.out.println();
			    System.out.println("**Starting Recursive Function**");
			    
			    writeToFile(sets, categories);
		   }catch(IOException e) {			   
		   }
	   }
}

