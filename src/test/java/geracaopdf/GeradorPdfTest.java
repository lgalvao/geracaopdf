package geracaopdf;

import com.github.javafaker.Faker;
import com.lowagie.text.Chunk;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GeradorPdfTest {
    @Test
    void paragrafo() {
        final GeradorPdf geradorPdf = new GeradorPdf("TesteParagrafos");

        geradorPdf.cabecalho();
        geradorPdf.titulo("REQUERIMENTO");
        geradorPdf.paragrafo(faker.lorem().paragraph(20));
        geradorPdf.paragrafo(faker.lorem().paragraph(10));
        geradorPdf.paragrafo(faker.lorem().paragraph(15));
        geradorPdf.paragrafo(faker.lorem().paragraph(5));
        geradorPdf.rodape();

        geradorPdf.gravar();
    }

    @Test
    void frase() {
        final GeradorPdf geradorPdf = new GeradorPdf("TesteFrases");
        geradorPdf.frase(faker.lorem().sentence(10), GeradorPdf.NEGRITO);
        geradorPdf.frase(faker.lorem().sentence(5));
        geradorPdf.frase(faker.lorem().sentence(20), GeradorPdf.ITALICO);
        geradorPdf.frase(faker.lorem().sentence(30));
        geradorPdf.gravar();
    }

    @Autowired Faker faker;
}
