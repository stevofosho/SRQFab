package com.srqfabrications.products;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.*; 


public class Product2 {

   public static int skuDynamic = 0;
   private static int BUFFER_LIMIT = 4000;

   public static void main(String[] args) {

		try(BufferedReader br = new BufferedReader(new FileReader("src/main/java/variations.txt"))) {
		    //First, determine number of lines in the file so we will know how many vectors we need to instantiate
		    int lines = 0;
		    br.mark(BUFFER_LIMIT);
		    while (br.readLine() != null) lines++;
		    Vector<String> categories = new Vector<String>();
		    //Vector of String vectors
		    Vector<Vector<String>> sets = new Vector<Vector<String>>();
		    
		    
		    //Reset Reader back to beginning of file and populate each Vector 
		    br.reset();
		    String line = br.readLine();
		    while (line != null) {
		    	//Add the category that is prefixed at each line
		    	categories.add(line.substring(0, line.indexOf(":")));
		    	System.out.println(line.substring(0, line.indexOf(":")));
		    	
		    	//Get the options after the prefix
		    	String option = line.substring(line.indexOf(":") + 1);
		    	System.out.println(option);
		    	Vector<String> vec = new Vector<String>();
		    	while(option.indexOf("|") != -1) {
		    		//option.substring(0, option.indexOf("|"))
		    		vec.add(option.substring(0, option.indexOf("|")));
		    		option= option.substring(option.indexOf("|") + 1);
		    	}
		    	sets.add(vec);
		    	line = br.readLine();
		    }
		    
		    System.out.println(lines);
		    for(int i = 0; i < sets.size(); i++) {
		    	for(int j = 0; j < sets.elementAt(i).size(); j++)
		    		System.out.println(sets.elementAt(i).elementAt(j));
		    }
		    
		    System.out.println();
		    System.out.println();
		    System.out.println("**Starting Recursive Function**");
		    
		    //combine(sets, 0, "");
		    writeToFile(sets, categories);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   

//      String[][] sets={
//              {"Base-", "Raptor-"},
//              {"None-", "White Toyota Lettering-", "Stock Emblem-", "4Runner-"},
//              {"None-", "Raptor Lights-", "Large Raptor Lights-"},
//              {"Black", "Chrome", "TRD", "Custom TRD", "Color Match"},
//      };
//      combine(sets,0,"");
  }
  
   public static void combine(Vector<Vector<String>> vec, int index, String upperText, FileWriter writer)
   {
       if(index==vec.size())return;
       for(String i : vec.elementAt(index)){
	       combine(vec, index + 1, upperText + i + "-", writer);
	       if(index == vec.size() - 1) {
	    	   Product2.skuDynamic++;
	    	   String[] parts = (upperText + i).split("-");
	    	   for(int j = 0; j < parts.length; j++){System.out.println(parts[j]);}
	    	   try {
	    		   writer.write("SKU 96807-" + Product2.skuDynamic + ",");
		    	   for(int j = 0; j < parts.length; j++){
		    		   if(j != parts.length - 1)
		    			   writer.write(parts[j] + ",");
		    		   else
		    			   writer.write(parts[j] + "\n");
		    		   }
		    	   System.out.println("SKU 96807-" + Product2.skuDynamic);
		    	   //writer.write("SKU 96807-" + Product2.skuDynamic + "\n");
	    	   } catch(Exception e) {
	    		   e.printStackTrace();
	    	   }
	       }

    	   }
   }
   
   public static void writeToFile(Vector<Vector<String>> vec, Vector<String> categories) {
	   try {
		      FileWriter myWriter = new FileWriter("filename1.csv");
		      myWriter.write("Sku,");
		      for(String s : categories) {
		    	  myWriter.write(s);
		    	  if(s.equals(categories.lastElement()))
		    		  myWriter.write("\n");
		    	  else
		    		  myWriter.write(",");
		    	  }
		      combine(vec, 0, "", myWriter);
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
   }
   
//  public static void combine(String[][] list,int index,String upperText)
//  {
//      if(index==list.length)return;
//      for(String i:list[index]){
//          combine(list,index+1,upperText+i);
//          if(index==list.length-1){
//             Product2.skuDynamic++;
//             String[] parts = upperText.split("-");
//             int partsLength = parts.length;
//             for(int j = 0; j < partsLength; j++){System.out.println(parts[j]);}
//             System.out.println(i);
//              System.out.println("SKU 96807-" + Product2.skuDynamic);
//          }
//      }
//  }
   
   
}