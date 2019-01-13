package com.pdfbox.printbox;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrintboxApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(PrintboxApplication.class, args);
		
		//Loading an existing document 
	      File file = new File("C:/PDFBOX/RF.12050809.pdf"); 
	      PDDocument document = PDDocument.load(file); 
	        
	      System.out.println("PDF loaded"); 
	        
	      PDPage page = document.getPage(0);
	      
	      PDPageContentStream stream = new PDPageContentStream(document, page);
	      
	      System.out.println(stream.toString());

//	      //Saving the document 
//	      document.save("C:/PDFBOX/sample.pdf");

	      //Closing the document  
	      document.close(); 
	}

}

