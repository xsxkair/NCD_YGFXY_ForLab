package com.org.xsx.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;

import com.org.xsx.UI.Erweima.CreateCode;
import com.org.xsx.beans.QRCodeBean;
import com.org.xsx.tools.CRC16;
import com.org.xsx.tools.DescyTool;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class MakeQRCodeService extends Service<Void>{
	@Override
	protected Task<Void> createTask() {
		// TODO Auto-generated method stub
		return new RefreshDataTask();
	}
	
	class RefreshDataTask extends Task<Void> {       

        @Override 
        protected Void call() throws InterruptedException{

        	return MakeQRCode();
        }
        
        
        private Void MakeQRCode(){
        	QRCodeBean myQrCodeBean = QRCodeBean.GetInstance();
        	StringBuffer content = new StringBuffer();
        	
        	//创建文本保存二维码数据
        	File file = new File(myQrCodeBean.getSavedir()+"/"+myQrCodeBean.getPihaohead()+".txt");
        	if (!file.exists()){
        		try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	
        	FileWriter writer = null;
        	
        	try {
				writer = new FileWriter(file);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        	//创建word，保存二维码图片
        	XWPFDocument doc = new XWPFDocument();

            CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();  
            CTPageMar pageMar = sectPr.addNewPgMar();  
            pageMar.setLeft(BigInteger.valueOf(500L));  
            pageMar.setTop(BigInteger.valueOf(500L));  
            pageMar.setRight(BigInteger.valueOf(500L));  
            pageMar.setBottom(BigInteger.valueOf(500L));
            
            XWPFParagraph p = doc.createParagraph();
            CTPPr pPPr = null;  
            if (p.getCTP() != null) {  
                if (p.getCTP().getPPr() != null) {  
                    pPPr = p.getCTP().getPPr();  
                } else {  
                    pPPr = p.getCTP().addNewPPr();  
                }  
            }  
            CTSpacing pSpacing = pPPr.getSpacing() != null ? pPPr.getSpacing() : pPPr.addNewSpacing(); 
            pSpacing.setLine(new BigInteger("350"));
            
            XWPFRun r = p.createRun();
        	
        	for(int i=0; i<myQrCodeBean.getMakenum(); i++){
        		
        		content.delete(0, content.length());
        		
        		content.append(myQrCodeBean.getItemname()+"#"+myQrCodeBean.getTctype()+"#"+myQrCodeBean.getNormal()+"#"+
        				myQrCodeBean.getLow()+"#"+myQrCodeBean.getHigh()+"#"+myQrCodeBean.getDanwei()+"#"+myQrCodeBean.getT_location()+"#"+
        				myQrCodeBean.getBiaoqunum()+"#");
        		
        		if("1" == myQrCodeBean.getBiaoqunum()){
        			content.append(myQrCodeBean.getQu1_a()+"#"+myQrCodeBean.getQu1_b()+"#"+myQrCodeBean.getQu1_c()+"#");
        		}
        		else{
        			content.append(myQrCodeBean.getFenduan()+"#"+myQrCodeBean.getQu1_a()+"#"+myQrCodeBean.getQu1_b()+"#"
        					+myQrCodeBean.getQu1_c()+"#"+myQrCodeBean.getQu2_a()+"#"+myQrCodeBean.getQu2_b()+"#"+myQrCodeBean.getQu2_c()+"#");
        		}
        		content.append(myQrCodeBean.getWaittime()+"#"+myQrCodeBean.getC_location()+"#NCD"+myQrCodeBean.getPihaohead()+String .format("%04d",i)+
        				"#"+myQrCodeBean.getOuttime()+"#");
        		
        		content.append(String.valueOf(CRC16.CalCRC16(content.toString().getBytes(), content.length())));
        		
        		String tempstr = "AB#"+content.length()+"#"+content.toString();
        		
        		String tempstr2 = DescyTool.Des(tempstr);
        		
        		if(tempstr2 != null){
        			CreateCode.CreateAndSaveQRCode(tempstr2, myQrCodeBean.getSavedir()+"/"+myQrCodeBean.getPihaohead()+i+".png");
        			try {
						writer.write(tempstr2+"\n");
						writer.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			
        			try {
						r.addPicture(new FileInputStream(myQrCodeBean.getSavedir()+"/"+myQrCodeBean.getPihaohead()+i+".png"), XWPFDocument.PICTURE_TYPE_PNG, myQrCodeBean.getSavedir()+"/"+myQrCodeBean.getPihaohead()+i+".png", Units.toEMU(43), Units.toEMU(43));
						r.setText(" ");
					} catch (InvalidFormatException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	
        		}
        		
        		updateProgress(i, myQrCodeBean.getMakenum());
        		try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	
        	//由于word最后一行有段落标记，缩进了图片间距，所以多写入一行的空白图片是段落标记到下一行
        	for(int j=0; j<13; j++){
        		try {
					r.addPicture(new FileInputStream("RES/nothing.png"), XWPFDocument.PICTURE_TYPE_PNG, "RES/nothing.png", Units.toEMU(43), Units.toEMU(43));
					r.setText(" ");
				} catch (InvalidFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	
        	FileOutputStream out;
			try {
				out = new FileOutputStream(myQrCodeBean.getSavedir()+"/"+myQrCodeBean.getPihaohead()+".docx");
				doc.write(out);
				out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

            
        	try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
			return null;
        }
   
 
       
    }
}
