package com.pdfbox.printbox;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("printbox")
public class PrintController {
	
	@PostMapping("/preupload")
    public JSONObject handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException{

		JSONObject response = new JSONObject();
		
		response.put("id", 2);
		response.put("file", file.getOriginalFilename());

		JSONArray pagesArray = new JSONArray();
		
		final PDDocument document = PDDocument.load(convert(file));
        PDFRenderer pdfRenderer = new PDFRenderer(document);
            
        BufferedImage bim = null;
            
        //int grayscale=0,colors=0;
        for (int page = 0; page < document.getNumberOfPages(); ++page)
        {
        	bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
        	if (isGrayScale(bim)){
        		pagesArray.add(page, false);
            	//grayscale++;
            }
            else{
            	pagesArray.add(page, true);
            	//colors++;
            }    
        }
        response.put("pages",pagesArray);
        document.close();
            
        return response;
	}
	
	@PostMapping("/postupload")
    public double handleFileUpload(@RequestParam("prefs") String chooses, 
    		@RequestParam("actualPref") String actualPref){
		
		int countCol = StringUtils.countOccurrencesOf(actualPref, "true");
		int countGS = StringUtils.countOccurrencesOf(actualPref, "false");
		
		return (countCol*0.50)+(countGS*0.10);
		
	}
	static boolean isGrayScale(BufferedImage image)
	{
		// Test the type
		if ( image.getType() == BufferedImage.TYPE_BYTE_GRAY ) return true ;
		if ( image.getType() == BufferedImage.TYPE_USHORT_GRAY ) return true ;
		// Test the number of channels / bands
		if ( image.getRaster().getNumBands() == 1 ) return true ; // Single channel => gray scale
		
		// Multi-channels image; then you have to test the color for each pixel.
		for (int y=0 ; y < image.getHeight() ; y++)
			for (int x=0 ; x < image.getWidth() ; x++)
				for (int c=1 ; c < image.getRaster().getNumBands() ; c++)
					if ( image.getRaster().getSample(x, y, c-1) != image.getRaster().getSample(x, y, c) ) return false ;
		
		return true ;
	}
	
	File convert(MultipartFile file) throws IOException
	{    
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	}
		
}
