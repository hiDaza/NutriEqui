/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author daza
 */

import com.mycompany.controller.NutriEquiController;
import com.mycompany.domain.*;

public class TestePersistencia {

    public static void main(String[] args) {
        System.out.println("=== TESTE DE PERSISTÊNCIA - NUTRIEQUI CAMPO ===\n");

        NutriEquiController controller = new NutriEquiController();

        // 1. Cadastrar um cavalo
        System.out.println("1. Cadastrando cavalo 'Alazao'...");
        String resultadoCavalo = controller.cadastrarEquino(
                "Alazao", 
                520.0, 
                3, 
                CategoriaFisiologica.ATLETA_MODERADO
        );
        System.out.println("   -> " + resultadoCavalo);

        // 2. Cadastrar um alimento
        System.out.println("\n2. Cadastrando alimento 'Feno PODEROSO'...");
        String resultadoAlimento = controller.cadastrarAlimento(
                "Feno Poderoso",
                TipoAlimento.VOLUMOSO,
                2.1
        );
        System.out.println("   -> " + resultadoAlimento);

        // 3. Registrar consumo para o cavalo
        System.out.println("\n3. Registrando consumo de 10 kg de Feno Poderoso para Alazao...");
        String resultadoConsumo = controller.registrarConsumo(
                "Alazao",
                "Feno Poderoso",
                10.0
        );
        System.out.println("   -> " + resultadoConsumo);

        // 4. Avaliar o balanço energético
        System.out.println("\n4. Avaliando o balanço energético de Alazao...");
        DiagnosticoNutricional diag = controller.avaliarEquino("Alazao");
        if (diag == null) {
            System.out.println("   -> Erro: Cavalo não encontrado!");
            return;
        }

        // Exibe o diagnóstico formatado
        System.out.println("\n===== DIAGNÓSTICO NUTRICIONAL =====");
        System.out.printf("Cavalo: %s\n", diag.getEquino().getNome());
        System.out.printf("Peso: %.1f kg\n", diag.getEquino().getPeso());
        System.out.printf("Categoria: %s\n", diag.getEquino().getCategoria());
        System.out.printf("ED Exigida: %.2f Mcal/dia\n", diag.getEdExigida());
        System.out.printf("ED Fornecida: %.2f Mcal/dia\n", diag.getEdFornecida());
        System.out.printf("Saldo: %.2f Mcal/dia\n", diag.getSaldo());
        System.out.println("Classificação: " + diag.getClassificacao());
        System.out.println("Recomendação: " + diag.getRecomendacao());
        System.out.println("====================================\n");

        // 5. Verificar se os dados foram realmente persistidos (opcional)
        System.out.println("5. Verificando se os dados foram salvos no banco...");
        System.out.println("   -> Consulte o MySQL para confirmar as tabelas 'equino', 'alimento' e 'consumo'.");
        System.out.println("   -> Tabela equino deve conter o cavalo 'Spirit'.");
        System.out.println("   -> Tabela alimento deve conter 'Feno Tifton'.");
        System.out.println("   -> Tabela consumo deve ter o registro de 10 kg.");

        System.out.println("\n=== TESTE CONCLUÍDO COM SUCESSO! ===");
    }
}
