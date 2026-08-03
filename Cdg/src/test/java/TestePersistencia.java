/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author daza
 */

import com.mycompany.controller.EquinoController;
import com.mycompany.controller.AlimentoController;
import com.mycompany.controller.ConsumoController;
import com.mycompany.controller.AvaliacaoController;
import com.mycompany.domain.*;
import com.mycompany.repository.JpaUtil;

public class TestePersistencia {

    public static void main(String[] args) {
        EquinoController equinoController = new EquinoController();
        AlimentoController alimentoController = new AlimentoController();
        ConsumoController consumoController = new ConsumoController();
        AvaliacaoController avaliacaoController = new AvaliacaoController();

        System.out.println("=== TESTE COMPLETO - NUTRIEQUI CAMPO ===\n");

        // 1. TESTE DE CADASTRO DE EQUINO
        System.out.println("1. Teste de cadastro de equino:");
        String resultadoEquino = equinoController.cadastrarEquino(
                "Alazao",
                520.0,
                3,
                CategoriaFisiologica.ATLETA_MODERADO
        );
        System.out.println("   -> " + resultadoEquino);

        String resultadoEquinoDuplicado = equinoController.cadastrarEquino(
                "Alazao",
                520.0,
                3,
                CategoriaFisiologica.ATLETA_MODERADO
        );
        System.out.println("   -> Tentativa duplicada: " + resultadoEquinoDuplicado);

        // 2. TESTE DE CADASTRO DE ALIMENTO (usando métodos específicos)
        System.out.println("\n2. Teste de cadastro de alimento:");

        // Cadastrar Feno Poderoso (VOLUMOSO)
        String resultadoFeno = alimentoController.cadastrarVolumoso(
                TipoVolumoso.TIFTON,
                CategoriaVolumoso.B,
                90.0, 8.0, 65.0, 35.0, 2.1, "Sudeste"
        );
        System.out.println("   -> " + resultadoFeno);

        // Tentar cadastrar o mesmo volumoso (deve falhar)
        String resultadoFenoDuplicado = alimentoController.cadastrarVolumoso(
                TipoVolumoso.TIFTON,
                CategoriaVolumoso.B,
                90.0, 8.0, 65.0, 35.0, 2.1, "Sudeste"
        );
        System.out.println("   -> Tentativa duplicada (volumoso): " + resultadoFenoDuplicado);

        // Cadastrar Ração MUITO PODEROSA (RACAO)
        String resultadoRacao = alimentoController.cadastrarRacao(
                "Raçao MUITO PODEROSA",
                "Fábrica Z",
                CategoriaRacao.ATLETA,
                12.0, 16.0, 6.0, 10.0, 8.0, 18.0, 7.0, 0.9, 0.6, 0.4, 3.4
        );
        System.out.println("   -> " + resultadoRacao);

        // 3. TESTE DE REGISTRO DE CONSUMO
        System.out.println("\n3. Teste de registro de consumo:");

        // 3.1. Registro válido
        String resultadoConsumo = consumoController.registrarConsumo(
                "Alazao",
                "Feno Poderoso",   // o nome deve ser o nome gerado pelo cadastrarVolumoso
                10.0
        );
        System.out.println("   -> Registro válido: " + resultadoConsumo);

        // 3.2. Equino inexistente
        String resultadoConsumoEquinoInvalido = consumoController.registrarConsumo(
                "CORCEL",
                "Feno Poderoso",
                10.0
        );
        System.out.println("   -> Equino inexistente: " + resultadoConsumoEquinoInvalido);

        // 3.3. Alimento inexistente
        String resultadoConsumoAlimentoInvalido = consumoController.registrarConsumo(
                "Alazao",
                "RACAO DIFERENTE",
                10.0
        );
        System.out.println("   -> Alimento inexistente: " + resultadoConsumoAlimentoInvalido);

        // 3.4. Quantidade inválida
        String resultadoConsumoQuantidadeInvalida = consumoController.registrarConsumo(
                "Alazao",
                "Feno Poderoso",
                0.0
        );
        System.out.println("   -> Quantidade inválida (0): " + resultadoConsumoQuantidadeInvalida);

        // 3.5. Segundo consumo (ração)
        String resultadoConsumo2 = consumoController.registrarConsumo(
                "Alazao",
                "Raçao MUITO PODEROSA",
                2.5
        );
        System.out.println("   -> Segundo consumo (ração): " + resultadoConsumo2);

        // 4. TESTE DE AVALIAÇÃO ENERGÉTICA
        System.out.println("\n4. Teste de avaliação energética:");

        DiagnosticoNutricional diag = avaliacaoController.avaliarEquino("Alazao");
        if (diag != null) {
            System.out.println("   -> Diagnóstico para Alazao:");
            System.out.println("      - ED Exigida: " + String.format("%.2f", diag.getEdExigida()) + " Mcal/dia");
            System.out.println("      - ED Fornecida: " + String.format("%.2f", diag.getEdFornecida()) + " Mcal/dia");
            System.out.println("      - Saldo: " + String.format("%.2f", diag.getSaldo()) + " Mcal/dia");
            System.out.println("      - Classificação: " + diag.getClassificacao());
            System.out.println("      - Recomendação: " + diag.getRecomendacao());
        } else {
            System.out.println("   -> Erro: Equino não encontrado.");
        }

        DiagnosticoNutricional diagInexistente = avaliacaoController.avaliarEquino("CORCEL");
        if (diagInexistente == null) {
            System.out.println("   -> Avaliação para cavalo inexistente: retornou null (correto).");
        }

        System.out.println("\n=== TESTE CONCLUÍDO COM SUCESSO! ===");
        System.out.println("Consulte o banco de dados MySQL para verificar os registros.");
        
        JpaUtil.close();
    }
}