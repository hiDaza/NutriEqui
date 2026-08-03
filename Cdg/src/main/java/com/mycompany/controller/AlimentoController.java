package com.mycompany.controller;

import com.mycompany.domain.*;
import com.mycompany.repository.AlimentoRepository;

public class AlimentoController {

    private AlimentoRepository alimentoRepository;

    public AlimentoController() {
        this.alimentoRepository = new AlimentoRepository();
    }

    public String cadastrarRacao(String nome, String fabricante, CategoriaRacao categoria,
                                 double umidade, double proteinaBruta, double extratoEtereo,
                                 double fibraBruta, double fda, double fdn,
                                 double materiaMineralRacao, double calcioRacao, double fosforoRacao,
                                 double sodioRacao, Double edDec) {
        
        if (alimentoRepository.buscarRacaoPorNome(nome) != null) {
            return "Erro: Já existe uma ração com este nome.";
        }

        Alimento racao = new Alimento(nome, fabricante, categoria);
        racao.setUmidade(umidade);
        racao.setProteinaBruta(proteinaBruta);
        racao.setExtratoEtereo(extratoEtereo);
        racao.setFibraBruta(fibraBruta);
        racao.setFda(fda);
        racao.setFdn(fdn);
        racao.setMateriaMineralRacao(materiaMineralRacao);
        racao.setCalcioRacao(calcioRacao);
        racao.setFosforoRacao(fosforoRacao);
        racao.setSodioRacao(sodioRacao);
        racao.setEdDec(edDec);

        // Calcula ED estimada
        double edEstimada = calcularEdRacao(proteinaBruta, extratoEtereo, fibraBruta, fdn);
        racao.setEdEst(edEstimada);

        alimentoRepository.salvar(racao);
        return "Ração cadastrada com sucesso! ID: " + racao.getId() + " | ED Estimada: " + String.format("%.2f", edEstimada) + " Mcal/kg";
    }

    public String cadastrarVolumoso(TipoVolumoso tipo, CategoriaVolumoso categoria,
                                    double materiaSeca, double proteinaVolumoso,
                                    double fdnVolumoso, double fdaVolumoso,
                                    double edVolumoso, String regiao) {
        
        String nome = tipo.getDescricao() + " - Categoria " + categoria.getDescricao();
        if (alimentoRepository.buscarVolumosoPorNomeETipo(nome, tipo) != null) {
            return "Erro: Já existe um volumoso com estas características.";
        }

        Alimento volumoso = new Alimento(nome, tipo, categoria);
        volumoso.setMateriaSeca(materiaSeca);
        volumoso.setProteinaVolumoso(proteinaVolumoso);
        volumoso.setFdnVolumoso(fdnVolumoso);
        volumoso.setFdaVolumoso(fdaVolumoso);
        volumoso.setEdVolumoso(edVolumoso);
        volumoso.setRegiao(regiao);

        alimentoRepository.salvar(volumoso);
        return "Volumoso cadastrado com sucesso! ID: " + volumoso.getId();
    }

    public String cadastrarSuplemento(String nome, String fabricante, String categoria,
                                      UnidadeSuplemento unidade, double doseRecomendada,
                                      double doseUsada, Double energia, Double proteina,
                                      Double gordura, Double calcio, Double fosforo,
                                      Double sodio, Double potassio, Double magnesio,
                                      Double selenio, Double vitaminaE, Double biotina,
                                      CalculoEnergetico calculoEnergetico) {
        
        if (alimentoRepository.buscarSupplementoPorNome(nome) != null) {
            return "Erro: Já existe um suplemento com este nome.";
        }

        Alimento suplemento = new Alimento();
        suplemento.setTipo(TipoAlimento.SUPLEMENTO);
        suplemento.setNomeComercialSuplemento(nome);
        suplemento.setFabricanteSuplemento(fabricante);
        suplemento.setCategoriaSuplemento(categoria);
        suplemento.setUnidadeRotulo(unidade);
        suplemento.setDoseRecomendada(doseRecomendada);
        suplemento.setDoseUsada(doseUsada);

        // Campos opcionais
        if (energia != null) suplemento.setEnergiaSuplemento(energia);
        if (proteina != null) suplemento.setProteinaSuplemento(proteina);
        if (gordura != null) suplemento.setGordura(gordura);
        if (calcio != null) suplemento.setCalcioSuplemento(calcio);
        if (fosforo != null) suplemento.setFosforoSuplemento(fosforo);
        if (sodio != null) suplemento.setSodioSuplemento(sodio);
        if (potassio != null) suplemento.setPotassio(potassio);
        if (magnesio != null) suplemento.setMagnesio(magnesio);
        if (selenio != null) suplemento.setSelenio(selenio);
        if (vitaminaE != null) suplemento.setVitaminaE(vitaminaE);
        if (biotina != null) suplemento.setBiotina(biotina);

        suplemento.setEntraCalculoEnergetico(calculoEnergetico);

        alimentoRepository.salvar(suplemento);
        return "Suplemento cadastrado com sucesso! ID: " + suplemento.getId();
    }

    private double calcularEdRacao(double proteinaBruta, double extratoEtereo,
                                    double fibraBruta, double fdn) {
        // Fórmula simplificada para calcular ED estimada
        // ED (Mcal/kg) = 2.58 + 0.0169*PB + 0.0329*EE - 0.0108*FB - 0.0068*FDA
        // Adaptada para equinos
        double ed = 2.58 + (0.0169 * proteinaBruta) + (0.0329 * extratoEtereo) 
                    - (0.0108 * fibraBruta) - (0.0068 * fdn);
        return Math.max(0, ed); // Garante valor positivo
    }
}
