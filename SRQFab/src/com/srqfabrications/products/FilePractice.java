package com.srqfabrications.products;

import java.io.File;

public class FilePractice {

	private static final String INPUT_FILEPATH = "inputFiles";
	private static final String PROCESSED_FILEPATH = "inputFiles/processed";
	private static final String OUTPUT_FILEPATH = "outputFiles";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File dir = new File(INPUT_FILEPATH);
		File[] directoryListing = dir.listFiles();
		String name = directoryListing[0].getName();
		
	}

}
