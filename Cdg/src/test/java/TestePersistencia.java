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

/**
 * Classe de teste para validar a persistência e a lógica de negócio
 * do sistema NutriEqui Campo, utilizando os controllers específicos.
 * 
 * Executa o fluxo completo: cadastro de equino, cadastro de alimento,
 * registro de consumo e avaliação energética.
 */
public class TestePersistencia {

    public static void main(String[] args) {
        // Inicializa os controllers específicos
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

        // Tentar cadastrar o mesmo equino novamente (deve falhar)
        String resultadoEquinoDuplicado = equinoController.cadastrarEquino(
                "Alazao", 
                520.0, 
                3, 
                CategoriaFisiologica.ATLETA_MODERADO
        );
        System.out.println("   -> Tentativa duplicada: " + resultadoEquinoDuplicado);

        // 2. TESTE DE CADASTRO DE ALIMENTO 
        System.out.println("\n2. Teste de cadastro de alimento:");
        String resultadoAlimento = alimentoController.cadastrarAlimento(
                "Feno Poderoso",
                TipoAlimento.VOLUMOSO,
                2.1
        );
        System.out.println("   -> " + resultadoAlimento);

        // Tentar cadastrar o mesmo alimento novamente (deve falhar)
        String resultadoAlimentoDuplicado = alimentoController.cadastrarAlimento(
                "Feno Poderoso",
                TipoAlimento.VOLUMOSO,
                2.1
        );
        System.out.println("   -> Tentativa duplicada: " + resultadoAlimentoDuplicado);

        // Cadastrar um segundo alimento (ração) para testar múltiplos itens
        String resultadoAlimento2 = alimentoController.cadastrarAlimento(
                "Raçao MUITO PODEROSA",
                TipoAlimento.RACAO,
                3.4
        );
        System.out.println("   -> " + resultadoAlimento2);

        // 3. TESTE DE REGISTRO DE CONSUMO 
        System.out.println("\n3. Teste de registro de consumo:");

        // 3.1. Registro válido
        String resultadoConsumo = consumoController.registrarConsumo(
                "Alazao",
                "Feno Poderoso",
                10.0
        );
        System.out.println("   -> Registro válido: " + resultadoConsumo);

        // 3.2. Tentar registrar com equino inexistente (deve falhar)
        String resultadoConsumoEquinoInvalido = consumoController.registrarConsumo(
                "CORCEL",
                "Feno Poderoso",
                10.0
        );
        System.out.println("   -> Equino inexistente: " + resultadoConsumoEquinoInvalido);

        // 3.3. Tentar registrar com alimento inexistente (deve falhar)
        String resultadoConsumoAlimentoInvalido = consumoController.registrarConsumo(
                "Alazao",
                "RACAO DIFERENTE",
                10.0
        );
        System.out.println("   -> Alimento inexistente: " + resultadoConsumoAlimentoInvalido);

        // 3.4. Tentar registrar com quantidade inválida (<=0) (deve falhar)
        String resultadoConsumoQuantidadeInvalida = consumoController.registrarConsumo(
                "Alazao",
                "Feno Poderoso",
                0.0
        );
        System.out.println("   -> Quantidade inválida (0): " + resultadoConsumoQuantidadeInvalida);

        // 3.5. Registrar um segundo consumo (ração) para o mesmo equino
        String resultadoConsumo2 = consumoController.registrarConsumo(
                "Alazao",
                "Raçao MUITO PODEROSA",
                2.5
        );
        System.out.println("   -> Segundo consumo (ração): " + resultadoConsumo2);

        // 4. TESTE DE AVALIAÇÃO ENERGÉTICA
        System.out.println("\n4. Teste de avaliação energética:");

        // 4.1. Avaliação para equino existente
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

        // 4.2. Avaliação para equino inexistente (deve retornar null)
        DiagnosticoNutricional diagInexistente = avaliacaoController.avaliarEquino("CORCEL");
        if (diagInexistente == null) {
            System.out.println("   -> Avaliação para cavalo inexistente: retornou null (correto).");
        }

        // ========== 5. FINALIZAÇÃO ==========
        System.out.println("\n=== TESTE CONCLUÍDO COM SUCESSO! ===");
        System.out.println("Consulte o banco de dados MySQL para verificar os registros.");
        
        JpaUtil.close();
    }
}