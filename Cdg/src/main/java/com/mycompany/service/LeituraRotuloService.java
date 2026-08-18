/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

/**
 *
 * @author daza
 */
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class LeituraRotuloService {

    private static final String UPLOAD_DIR = "uploads/rotulos/";

    public LeituraRotuloService() {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public ResultadoLeituraRotulo processarArquivo(File file, String nomeOriginal) throws IOException {
        String nomeUnico = System.currentTimeMillis() + "_" + nomeOriginal;
        Path destino = Paths.get(UPLOAD_DIR + nomeUnico);
        Files.copy(file.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

        String textoExtraido = extrairTexto(destino.toFile());

        return new ResultadoLeituraRotulo(destino.toString(), textoExtraido);
    }

    private String extrairTexto(File arquivo) throws IOException {
        String nome = arquivo.getName().toLowerCase();

        if (nome.endsWith(".pdf")) {
            return extrairTextoPDF(arquivo);
        } else {
            return "Formato de arquivo não suportado. Atualmente, apenas PDFs com texto pesquisável são suportados. " +
                   "Para imagens, converta para PDF com OCR (ex: Google Drive, Adobe Scan) e envie o PDF gerado.";
        }
    }


    private String extrairTextoPDF(File pdfFile) throws IOException {
        try (PDDocument document = Loader.loadPDF(pdfFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    
    //futuramente colocar imagem, é mais complicado pq depende da instalação da extensão em diferentes ambientes :(
    /*
    private String extrairTextoImagem(File imageFile) throws IOException {
        try {
            net.sourceforge.tess4j.Tesseract tesseract = new net.sourceforge.tess4j.Tesseract();
            tesseract.setDatapath("/usr/share/tessdata/");
            tesseract.setLanguage("por");
            BufferedImage image = ImageIO.read(imageFile);
            return tesseract.doOCR(image);
        } catch (Exception e) {
            return "Erro ao extrair texto da imagem: " + e.getMessage();
        }
    }
    */

    public static class ResultadoLeituraRotulo {
        private final String caminhoArquivo;
        private final String textoExtraido;

        public ResultadoLeituraRotulo(String caminhoArquivo, String textoExtraido) {
            this.caminhoArquivo = caminhoArquivo;
            this.textoExtraido = textoExtraido;
        }

        public String getCaminhoArquivo() { return caminhoArquivo; }
        public String getTextoExtraido() { return textoExtraido; }
    }
}