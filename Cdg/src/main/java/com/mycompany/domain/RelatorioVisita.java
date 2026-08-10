package com.mycompany.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class RelatorioVisita {

    private LocalDate dataVisita;
    private int totalEquinosAvaliados;
    private int quantidadeAdequados;
    private int quantidadeDeficit;
    private int quantidadeExcesso;
    private double custoDiarioTotalLote;
    private double custoMensalTotalLote;
    private List<DiagnosticoNutricional> diagnosticos;

    public RelatorioVisita(LocalDate dataVisita, int totalEquinosAvaliados, int quantidadeAdequados, 
                            int quantidadeDeficit, int quantidadeExcesso, double custoDiarioTotalLote, 
                            double custoMensalTotalLote, List<DiagnosticoNutricional> diagnosticos) {
        this.dataVisita = dataVisita != null ? dataVisita : LocalDate.now();
        this.totalEquinosAvaliados = totalEquinosAvaliados;
        this.quantidadeAdequados = quantidadeAdequados;
        this.quantidadeDeficit = quantidadeDeficit;
        this.quantidadeExcesso = quantidadeExcesso;
        this.custoDiarioTotalLote = custoDiarioTotalLote;
        this.custoMensalTotalLote = custoMensalTotalLote;
        this.diagnosticos = diagnosticos != null ? diagnosticos : Collections.emptyList();
    }

    public String gerarTextoFormatado() {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        sb.append("===============================================================\n");
        sb.append("            RELATÓRIO DE VISITA TÉCNICA - NUTRIEQUI            \n");
        sb.append("===============================================================\n");
        sb.append(String.format("Data da Visita: %s\n", dataVisita.format(fmt)));
        sb.append(String.format("Total de Equinos Avaliados: %d\n", totalEquinosAvaliados));
        sb.append("---------------------------------------------------------------\n");
        sb.append("RESUMO ESTATÍSTICO DO LOTE Diário:\n");
        sb.append(String.format("  - Dietas Adequadas: %d\n", quantidadeAdequados));
        sb.append(String.format("  - Em Déficit Energético: %d\n", quantidadeDeficit));
        sb.append(String.format("  - Em Excesso Energético: %d\n", quantidadeExcesso));
        sb.append("---------------------------------------------------------------\n");
        sb.append("IMPACTO FINANCEIRO DO LOTE Diário:\n");
        sb.append(String.format("  - Custo Diário Total: R$ %.2f\n", custoDiarioTotalLote));
        sb.append(String.format("  - Custo Mensal Projetado: R$ %.2f\n", custoMensalTotalLote));
        if (totalEquinosAvaliados > 0) {
            sb.append(String.format("  - Custo Médio Diário por Animal: R$ %.2f\n", custoDiarioTotalLote / totalEquinosAvaliados));
        }
        sb.append("===============================================================\n\n");

        sb.append("DETALHAMENTO INDIVIDUAL POR EQUINO:\n");
        sb.append("---------------------------------------------------------------\n");

        for (DiagnosticoNutricional d : diagnosticos) {
            Equino eq = d.getEquino();
            sb.append(String.format("%s | Categoria: %s | Peso: %.1f kg\n", eq.getNome(), eq.getCategoria(), eq.getPeso()));
            sb.append(String.format("   Exigência: %.2f Mcal | Fornecido: %.2f Mcal | Saldo: %.2f Mcal\n", d.getEdExigida(), d.getEdFornecida(), d.getSaldo()));
            sb.append(String.format("   Status: %s\n", d.getClassificacao()));
            sb.append(String.format("   Recomendação de Ajuste: %s\n", d.getRecomendacao()));
            sb.append(String.format("   Custo Diário: R$ %.2f | Custo Mensal: R$ %.2f\n", d.getCustoDiario(), d.getCustoMensal()));

            if (!d.getAlertas().isEmpty()) {
                sb.append("    ALERTAS DE SEGURANÇA NUTRICIONAL:\n");
                for (String alt : d.getAlertas()) {
                    sb.append(String.format("     - %s\n", alt));
                }
            } else {
                sb.append("  Nenhum alerta de segurança emitido.\n");
            }
            sb.append("---------------------------------------------------------------\n");
        }

        return sb.toString();
    }

    
    public LocalDate getDataVisita() { return dataVisita; }
    public int getTotalEquinosAvaliados() { return totalEquinosAvaliados; }
    public int getQuantidadeAdequados() { return quantidadeAdequados; }
    public int getQuantidadeDeficit() { return quantidadeDeficit; }
    public int getQuantidadeExcesso() { return quantidadeExcesso; }
    public double getCustoDiarioTotalLote() { return custoDiarioTotalLote; }
    public double getCustoMensalTotalLote() { return custoMensalTotalLote; }
    public List<DiagnosticoNutricional> getDiagnosticos() { return diagnosticos; }
}