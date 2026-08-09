package com.mycompany.service;

import com.mycompany.domain.Alimento;
import com.mycompany.domain.Consumo;
import com.mycompany.domain.Equino;
import com.mycompany.domain.TipoAlimento;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AvaliacaoServiceTest {

    @Test
    void deveIgnorarSuplementosQueNaoEntramNoCalculoEnergetico() {
        AvaliacaoService avaliacaoService = new AvaliacaoService();

        Equino equino = new Equino();
        equino.setNome("Rex");

        Alimento racao = new Alimento();
        racao.setTipo(TipoAlimento.RACAO);
        racao.setEdDec(10.0);

        Alimento suplemento = new Alimento();
        suplemento.setTipo(TipoAlimento.SUPLEMENTO);
        suplemento.setEnergiaSuplemento(5000.0);

        Consumo consumoRacao = new Consumo(equino, racao, 0.5);
        consumoRacao.setIncluiNoCalculoEnergetico(true);

        Consumo consumoSuplemento = new Consumo(equino, suplemento, 0.2);
        consumoSuplemento.setIncluiNoCalculoEnergetico(false);

        double total = avaliacaoService.calcularFornecimento(List.of(consumoRacao, consumoSuplemento));

        assertEquals(5.0, total, 0.0001);
    }
}
