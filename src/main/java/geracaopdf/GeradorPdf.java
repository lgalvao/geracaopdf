package geracaopdf;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

@Slf4j
public class GeradorPdf {
    public static final int NEGRITO = Font.BOLD;
    public static final int ITALICO = Font.ITALIC;

    public static final String ALINHAR_CENTRO = ElementTags.ALIGN_CENTER;
    public static final String ALINHAR_DIREITA = ElementTags.ALIGN_RIGHT;
    public static final String ALINHAR_ESQUERDA = ElementTags.ALIGN_LEFT;
    public static final String ALINHAR_JUSTIFICADO = ElementTags.ALIGN_JUSTIFIED;

    final Font FONTE_PADRAO = FontFactory.getFont(FontFactory.TIMES_ROMAN);
    final Font FONTE_PADRAO_NEGRITO = FontFactory.getFont(FontFactory.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD);
    final Font FONTE_PADRAO_ITALICO = FontFactory.getFont(FontFactory.TIMES_ROMAN, Font.DEFAULTSIZE, Font.ITALIC);

    final String nome;
    final Document documento = new Document();

    PdfWriter pdfWriter;

    public GeradorPdf(String nome) {
        this.nome = nome;
        try {
            pdfWriter = PdfWriter.getInstance(documento, new FileOutputStream(nome + ".pdf"));
            pdfWriter.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
            documento.setPageSize(PageSize.A4);
            documento.setMargins(60,60,60,60);
        } catch (FileNotFoundException e) {
            log.error("Impossível escrever pdf", e);
        }
    }

    public void gravar() {
        documento.close();
    }

    public void cabecalho() {
        try {
            if (!documento.isOpen()) documento.open();
            Image image = Image.getInstance("brasao.png");
            image.scalePercent(50);
            image.setAlignment(Image.MIDDLE);
            documento.add(image);

            final Font fonteCabecalho = FontFactory.getFont(FontFactory.TIMES, 9);

            Paragraph tre = new Paragraph(
                    "Tribunal Regional Eleitoral de Pernambuco",
                    fonteCabecalho);
            tre.setAlignment(ALINHAR_CENTRO);

            Paragraph endereco = new Paragraph(
                    "Av. Gov. Agamenon Magalhães, 1.160, Graças, CEP 52010-904, Recife-PE",
                    fonteCabecalho);
            endereco.setAlignment(ALINHAR_CENTRO);

            documento.add(tre);
            documento.add(endereco);
        } catch (IOException e) {
            log.warn("Imagem não encontrada", e);
        }
    }

    public void rodape() {
        if (!documento.isOpen()) documento.open();

        final Font fonteRodape = FontFactory.getFont(FontFactory.TIMES, 9);
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"));

        String unidadeTxt = "SGP/COPES/SEMARE";
        String sistemaTxt = "Sistema de Inscrições de Juízes em Rodízios Eleitorais";
        String geradoEmTxt = String.format("Gerado em %s, às %s", data, hora);

        Paragraph unidade = new Paragraph(unidadeTxt, fonteRodape);
        unidade.setAlignment(ALINHAR_CENTRO);

        Paragraph sistema = new Paragraph(sistemaTxt, fonteRodape);
        sistema.setAlignment(ALINHAR_CENTRO);

        Paragraph geradoEm = new Paragraph(geradoEmTxt, fonteRodape);
        geradoEm.setAlignment(ALINHAR_CENTRO);

        linhasEmBranco(3);
        documento.add(geradoEm);
        documento.add(sistema);
        documento.add(unidade);
    }

    public void titulo(String conteudo) {
        if (!documento.isOpen()) documento.open();

        Paragraph titulo = new Paragraph(conteudo, FONTE_PADRAO_NEGRITO);
        titulo.setAlignment(ALINHAR_CENTRO);
        linhasEmBranco(7);
        documento.add(titulo);
        linhasEmBranco(2);
    }

    public void paragrafo(String conteudo) {
        if (!documento.isOpen()) documento.open();

        Paragraph paragraph = new Paragraph(conteudo, FONTE_PADRAO);
        paragraph.setAlignment(ALINHAR_JUSTIFICADO);
        documento.add(paragraph);
        documento.add(Chunk.NEWLINE);
    }

    public void linhasEmBranco(int numero) {
        IntStream.of(1, numero).forEach(linha -> documento.add(Chunk.NEWLINE));
    }

    public void paragrafo(String conteudo, String alinhamento) {
        if (!documento.isOpen()) documento.open();
        Paragraph paragraph = new Paragraph(conteudo, FONTE_PADRAO);
        paragraph.setAlignment(alinhamento);
        documento.add(paragraph);
        linhasEmBranco(1);
    }

    public void frase(String conteudo) {
        if (!documento.isOpen()) documento.open();
        documento.add(new Phrase(conteudo + " ", FONTE_PADRAO));
    }

    public void frase(String conteudo, int formato) {
        Font fonte = switch (formato) {
            case Font.BOLD -> FONTE_PADRAO_NEGRITO;
            case Font.ITALIC -> FONTE_PADRAO_ITALICO;
            default -> FONTE_PADRAO;
        };
        if (!documento.isOpen()) documento.open();
        documento.add(new Phrase(conteudo + " ", fonte));
    }
}
