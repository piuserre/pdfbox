package com.pdfbox.printbox;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.ghost4j.Ghostscript;
import org.ghost4j.GhostscriptException;
import org.ghost4j.analyzer.AnalysisItem;
import org.ghost4j.analyzer.InkAnalyzer;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.document.PSDocument;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PrintboxApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(PrintboxApplication.class, args);
		try {
		 // load PS document
	    PDFDocument document = new PDFDocument();
	    document.load(new File("C:\\PDFBOX\\prova.pdf"));

	    // create analyzer
	    InkAnalyzer analyzer = new InkAnalyzer();

	    // analyze
	    List<AnalysisItem> coverageData = analyzer.analyze(document);

	    // print result
	    for (AnalysisItem analysisItem : coverageData) {
		System.out.println(analysisItem);

	    }

	} catch (Exception e) {
	    System.out.println("ERROR: " + e.getMessage());
	}
	}

}

