package com.org.xsx.UI.Erweima;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.org.xsx.tools.MatrixToImageWriter;



public class CreateCode {
	
	public static void CreateAndSaveQRCode(String content, String file){
		int width = 300;   
        int height = 300;   
        String format = "png";
        
        Map<EncodeHintType, Object> hints= new HashMap<>();   
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.remove(EncodeHintType.ERROR_CORRECTION);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        
         BitMatrix bitMatrix;
		try {
			bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height,hints);
			File outputFile = new File(file);   
	        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);

		} catch (WriterException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}

}
