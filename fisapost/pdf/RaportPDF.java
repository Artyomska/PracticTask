package fisapost.pdf;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import fisapost.entities.Sarcina;

public class RaportPDF 
{
	public static void addPdf(List<Sarcina> sar)
	{
		try 
		{
			Document document = new Document();
			File f1 = new File("D:\\TestTableFile.pdf");
			boolean success = f1.delete();
				if (!success)
				{
			  System.out.println("Fisier inexistent");
				}
			OutputStream outputStream = new FileOutputStream(new File("D:\\TestTableFile.pdf"));
			
            PdfWriter.getInstance(document, outputStream);
            
            document.open();
            
            PdfPTable pdfPTable = new PdfPTable(2);
            
            System.out.println(sar.size());
            for (int z=0; z<sar.size();z++)
            {
            //Create cells
                System.out.println(z);
            	Sarcina s=sar.get(z);
                System.out.println(s);
	            PdfPCell pdfPCell1 = new PdfPCell(new Paragraph(s.getId().toString()));
	            PdfPCell pdfPCell2 = new PdfPCell(new Paragraph(s.getDesc()));

	            pdfPTable.addCell(pdfPCell1);
	            pdfPTable.addCell(pdfPCell2);
	


            }
            
            document.add(pdfPTable);
            document.close();
            outputStream.close();
            System.out.println("Pdf created successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

