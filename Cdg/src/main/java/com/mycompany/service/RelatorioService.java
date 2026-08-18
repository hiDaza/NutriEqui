package com.mycompany.service;

import com.mycompany.domain.DiagnosticoNutricional;
import com.mycompany.domain.Equino;
import com.mycompany.domain.Propriedade;
import com.mycompany.domain.RelatorioVisita;
import com.mycompany.repository.EquinoRepository;
import com.mycompany.repository.PropriedadeRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RelatorioService {

    private final EquinoRepository equinoRepository;
    private final AvaliacaoService avaliacaoService;
    private final PropriedadeRepository propriedadeRepository;

    public RelatorioService() {
        this.equinoRepository = new EquinoRepository();
        this.avaliacaoService = new AvaliacaoService();
        this.propriedadeRepository = new PropriedadeRepository();
    }


    public RelatorioVisita gerarRelatorioLoteCompleto() {
        List<Equino> equinos = equinoRepository.listarTodos();
        return gerarRelatorio(equinos, "Todas as propriedades");
    }


    public RelatorioVisita gerarRelatorioPorPropriedade(String nomePropriedade) {
        if (nomePropriedade == null || nomePropriedade.trim().isEmpty()) {
            return gerarRelatorioLoteCompleto();
        }

        List<Equino> equinos = equinoRepository.buscarPorPropriedadeNome(nomePropriedade.trim());
        return gerarRelatorio(equinos, nomePropriedade);
    }


    private RelatorioVisita gerarRelatorio(List<Equino> equinos, String nomePropriedade) {
        if (equinos == null || equinos.isEmpty()) {
            return null;
        }

        List<DiagnosticoNutricional> diagnosticos = new ArrayList<>();
        int adequados = 0;
        int deficit = 0;
        int excesso = 0;
        double custoDiarioTotal = 0.0;

        for (Equino eq : equinos) {
            DiagnosticoNutricional diag = avaliacaoService.avaliarEquino(eq);
            diagnosticos.add(diag);

            custoDiarioTotal += diag.getCustoDiario();

            String status = diag.getClassificacao().toUpperCase();
            if (status.contains("ADEQUADO")) {
                adequados++;
            } else if (status.contains("DÉFICIT") || status.contains("DEFICIT")) {
                deficit++;
            } else if (status.contains("EXCESSO")) {
                excesso++;
            }
        }

        double custoMensalTotal = custoDiarioTotal * 30;

        return new RelatorioVisita(
            LocalDate.now(),
            equinos.size(),
            adequados,
            deficit,
            excesso,
            custoDiarioTotal,
            custoMensalTotal,
            diagnosticos,
            nomePropriedade
        );
    }
}