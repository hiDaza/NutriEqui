/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

/**
 *
 * @author daza
 */
import com.mycompany.domain.CalculoEnergetico;
import com.mycompany.domain.CategoriaRacao;
import com.mycompany.domain.CategoriaVolumoso;
import com.mycompany.domain.TipoAlimento;
import com.mycompany.domain.TipoVolumoso;
import com.mycompany.domain.UnidadeSuplemento;
import com.mycompany.domain.dto.AlimentoDTO;
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
    
    
    public AlimentoDTO parseTextoParaAlimento(String texto) {
        AlimentoDTO dto = new AlimentoDTO();
        String[] linhas = texto.split("\\r?\\n");
        for (String linha : linhas) {
            linha = linha.trim();
            if (linha.isEmpty()) continue;
            String[] partes = linha.split(":", 2);
            if (partes.length < 2) continue;
            String chave = partes[0].trim().toUpperCase();
            String valor = partes[1].trim();
            if (valor.isEmpty()) continue;

            switch (chave) {
                case "TIPO":
                    try { dto.setTipo(TipoAlimento.valueOf(valor.toUpperCase())); } catch (Exception e) {}
                    break;

                case "NOME":
                    dto.setNome(valor);
                    break;
                case "FABRICANTE":
                    dto.setFabricante(valor);
                    break;
                case "PRECO":
                    dto.setPrecoPorKg(parseDouble(valor));
                    break;

                case "CATEGORIA":
                    try { dto.setCategoriaRacao(CategoriaRacao.valueOf(valor.toUpperCase())); } catch (Exception e) {}
                    break;
                case "UMIDADE": dto.setUmidade(parseDouble(valor)); break;
                case "PROTEINA_BRUTA": dto.setProteinaBruta(parseDouble(valor)); break;
                case "EXTRATO_ETERE": dto.setExtratoEtereo(parseDouble(valor)); break;
                case "FIBRA_BRUTA": dto.setFibraBruta(parseDouble(valor)); break;
                case "FDA": dto.setFda(parseDouble(valor)); break;
                case "FDN": dto.setFdn(parseDouble(valor)); break;
                case "MATERIA_MINERAL": dto.setMateriaMineralRacao(parseDouble(valor)); break;
                case "CALCIO": dto.setCalcioRacao(parseDouble(valor)); break;
                case "FOSFORO": dto.setFosforoRacao(parseDouble(valor)); break;
                case "SODIO": dto.setSodioRacao(parseDouble(valor)); break;
                case "ED_DECLARADA": dto.setEdDec(parseDouble(valor)); break;

                case "TIPO_VOLUMOSO":
                    try { dto.setTipoVolumoso(TipoVolumoso.valueOf(valor.toUpperCase())); } catch (Exception e) {}
                    break;
                case "CATEGORIA_VOLUMOSO":
                    try { dto.setCategoriaVolumoso(CategoriaVolumoso.valueOf(valor.toUpperCase())); } catch (Exception e) {}
                    break;
                case "MATERIA_SECA": dto.setMateriaSeca(parseDouble(valor)); break;
                case "PROTEINA_VOLUMOSO": dto.setProteinaVolumoso(parseDouble(valor)); break;
                case "FDN_VOLUMOSO": dto.setFdnVolumoso(parseDouble(valor)); break;
                case "FDA_VOLUMOSO": dto.setFdaVolumoso(parseDouble(valor)); break;
                case "ED_VOLUMOSO": dto.setEdVolumoso(parseDouble(valor)); break;
                case "REGIAO": dto.setRegiao(valor); break;

                case "NOME_COMERCIAL":          
                    dto.setNomeComercialSuplemento(valor);
                    break;
                case "FABRICANTE_SUPLEMENTO":
                    dto.setFabricanteSuplemento(valor);
                    break;
                case "CATEGORIA_SUPLEMENTO":
                    dto.setCategoriaSuplemento(valor);
                    break;
                case "UNIDADE":
                    try { dto.setUnidadeRotulo(UnidadeSuplemento.valueOf(valor.toUpperCase())); } catch (Exception e) {}
                    break;
                case "DOSE_RECOMENDADA": dto.setDoseRecomendada(parseDouble(valor)); break;
                case "DOSE_USADA": dto.setDoseUsada(parseDouble(valor)); break;
                case "ENERGIA": dto.setEnergiaSuplemento(parseDouble(valor)); break;
                case "PROTEINA": dto.setProteinaSuplemento(parseDouble(valor)); break;
                case "GORDURA": dto.setGordura(parseDouble(valor)); break;
                case "CALCIO_SUPLEMENTO": dto.setCalcioSuplemento(parseDouble(valor)); break;
                case "FOSFORO_SUPLEMENTO": dto.setFosforoSuplemento(parseDouble(valor)); break;
                case "SODIO_SUPLEMENTO": dto.setSodioSuplemento(parseDouble(valor)); break;
                case "POTASSIO": dto.setPotassio(parseDouble(valor)); break;
                case "MAGNESIO": dto.setMagnesio(parseDouble(valor)); break;
                case "SELENIO": dto.setSelenio(parseDouble(valor)); break;
                case "VITAMINA_E": dto.setVitaminaE(parseDouble(valor)); break;
                case "BIOTINA": dto.setBiotina(parseDouble(valor)); break;
                case "CALCULO_ENERGETICO":
                    try { dto.setCalculoEnergetico(CalculoEnergetico.valueOf(valor.toUpperCase())); } catch (Exception e) {}
                    break;

                default:
            }
        }
        return dto;
    }

    private Double parseDouble(String valor) {
        if (valor == null || valor.trim().isEmpty()) return null;
        try {
            return Double.parseDouble(valor.trim().replace(",", "."));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    
    
}