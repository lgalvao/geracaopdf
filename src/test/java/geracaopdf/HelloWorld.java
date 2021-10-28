package geracaopdf;

import com.github.javafaker.Faker;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest
public class HelloWorld {
    @Test
    public void hello() {
        try (Document documento = new Document()) {
            final PdfWriter pdfWriter = PdfWriter.getInstance(documento, new FileOutputStream("HelloWorld.pdf"));
            documento.open();
            pdfWriter.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
            documento.add(new Paragraph("Hello World"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void aleatorioSimples() {
        final int NUM_LINHAS = 40;
        try (Document documento = new Document()) {
            final PdfWriter pdfWriter = PdfWriter.getInstance(documento, new FileOutputStream("AleatorioSimples.pdf"));
            documento.open();
            pdfWriter.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
            for (int i = 1; i <= NUM_LINHAS; i++) {
                documento.add(new Paragraph(faker.lorem().paragraph()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void textoComplexo() {
        Document document = new Document();
        Font fonteHelvetica10 = FontFactory.getFont(FontFactory.HELVETICA, 10);
        Font fonteHelvetica10Bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD,10);
        Font fonteHelvetica18 = FontFactory.getFont(FontFactory.TIMES, 18);
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Paragraphs.pdf"));
            document.open();
            Paragraph p1 = new Paragraph(new Chunk("This is my first paragraph. ", fonteHelvetica10));
            p1.add("The leading of this paragraph is calculated automagically. ");
            p1.add("The default leading is 1.5 times the fontsize. ");
            p1.add(new Chunk("You can add chunks "));
            p1.add(new Phrase("or you can add phrases. "));
            p1.add(new Phrase("Unless you change the leading with the method setLeading, " +
                    "the leading doesn't change if you add text with another leading. This can lead to some problems.",
                    fonteHelvetica18));
            document.add(p1);
            Paragraph p2 = new Paragraph(new Phrase("This is my second paragraph. ", FontFactory.getFont(FontFactory.HELVETICA, 12)));
            p2.add("As you can see, it started on a new line.");
            document.add(p2);
            Paragraph p3 = new Paragraph("This is my third paragraph.", fonteHelvetica10Bold);
            document.add(p3);
        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }
        document.close();
    }

    @Autowired
    Faker faker;

}
