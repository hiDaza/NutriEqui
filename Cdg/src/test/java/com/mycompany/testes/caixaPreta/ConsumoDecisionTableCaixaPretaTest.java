/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testes.caixaPreta;

/**
 *
 * @author daza
 */
import com.mycompany.controller.ConsumoController;
import com.mycompany.domain.Alimento;
import com.mycompany.domain.CategoriaFisiologica;
import com.mycompany.domain.Equino;
import com.mycompany.domain.TipoAlimento;
import com.mycompany.repository.AlimentoRepository;
import com.mycompany.repository.ConsumoRepository;
import com.mycompany.repository.EquinoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedConstruction;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.withSettings;

public class ConsumoDecisionTableCaixaPretaTest {

    @ParameterizedTest(name = "Cenário [{index}]: Equino=''{0}'', Suplemento=''{1}'', Dose={2} -> Esperado=''{3}''")
    @DisplayName("Matriz de Decisão - Registro de Consumo de Suplemento")
    @CsvSource({
        // tudo válido
        "'Bravio',      'Mineral Equino',   1.5,  'Suplemento registrado com sucesso!'",
        
        //Equino inexistent
        "'Inexistente', 'Mineral Equino',   1.5,  'Erro: Equino não encontrado.'",
        
        //Suplemento inexistente
        "'Bravio',      'Inexistente',      1.5,  'Erro: Suplemento não encontrado.'",
        
        //Dose zero
        "'Bravio',      'Mineral Equino',   0.0,  'Erro: Dose diária inválida. Deve ser maior que zero.'",
        
        //Dose negativa
        "'Bravio',      'Mineral Equino',  -1.0,  'Erro: Dose diária inválida. Deve ser maior que zero.'"
    })
    void testRegistrarSuplemento_MatrizDecisao(String nomeEquino, String nomeSuplemento, double doseDiaria, String resultadoEsperado) {

        Alimento suplementoValido = new Alimento();
        suplementoValido.setNome(nomeSuplemento);
        suplementoValido.setTipo(TipoAlimento.SUPLEMENTO);

        Answer<Object> respostaEquino = invocation -> {
            Object[] args = invocation.getArguments();
            if (args.length > 0 && args[0] != null && "Inexistente".equalsIgnoreCase(args[0].toString())) {
                return null;
            }
            return new Equino(nomeEquino, 400.0, 5, CategoriaFisiologica.MANTENCAO);
        };

        Answer<Object> respostaAlimento = invocation -> {
            Object[] args = invocation.getArguments();
            if (args.length > 0 && args[0] != null && "Inexistente".equalsIgnoreCase(args[0].toString())) {
                return null;
            }
            return suplementoValido;
        };

        Answer<Object> respostaConsumo = invocation -> null;

        try (MockedConstruction<EquinoRepository> mockEq = mockConstruction(EquinoRepository.class, withSettings().defaultAnswer(respostaEquino));
             MockedConstruction<AlimentoRepository> mockAli = mockConstruction(AlimentoRepository.class, withSettings().defaultAnswer(respostaAlimento));
             MockedConstruction<ConsumoRepository> mockCon = mockConstruction(ConsumoRepository.class, withSettings().defaultAnswer(respostaConsumo))) {

            ConsumoController consumoController = new ConsumoController();
            
            String resultado = consumoController.registrarSuplemento(nomeEquino, nomeSuplemento, doseDiaria,true);

            assertEquals(resultadoEsperado, resultado);
        }
    }
}