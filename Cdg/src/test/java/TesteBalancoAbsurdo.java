/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author daza
 */


import com.mycompany.controller.*;
import com.mycompany.domain.*;
import com.mycompany.repository.JpaUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TesteBalancoAbsurdo {
    private static final List<ResultadoTeste> resultados = new ArrayList<>();

    public static void main(String[] args) {
        
        // Desativa os logs para visualizar resultado final
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Logger.getLogger("org.hibernate.sql").setLevel(Level.SEVERE);
        Logger.getLogger("org.hibernate.type").setLevel(Level.SEVERE);
        Logger.getLogger("org.hibernate.engine").setLevel(Level.SEVERE);
        Logger.getLogger("org.hibernate.tool").setLevel(Level.SEVERE);
        Logger.getLogger("org.hibernate.resource").setLevel(Level.SEVERE);
        Logger.getLogger("jakarta.persistence").setLevel(Level.SEVERE);
        Logger.getLogger("com.zaxxer").setLevel(Level.SEVERE); // caso use HikariCP
        Logger.getLogger("com.mysql").setLevel(Level.SEVERE);
        
        System.out.println("TESTE DE BALANÇO ENERGÉTICO - CENÁRIOS EXTREMOS\n");

        EquinoController equinoController = new EquinoController();
        AlimentoController alimentoController = new AlimentoController();
        ConsumoController consumoController = new ConsumoController();
        AvaliacaoController avaliacaoController = new AvaliacaoController();

        // Limpa o banco para teste // nao usar dependendo do cenario
        limparBanco();

        //Cadastra alimentos com ED variadas
        System.out.println("Cadastrando alimentos...");

        // Feno Muito Poderoso (VOLUMOSO)
        alimentoController.cadastrarVolumoso(
            TipoVolumoso.TIFTON, CategoriaVolumoso.A,
            90.0, 10.0, 55.0, 25.0, 4.5, "Sudeste"
        );

        // Feno Fraquinho (VOLUMOSO)
        alimentoController.cadastrarVolumoso(
            TipoVolumoso.OUTRO, CategoriaVolumoso.C,
            85.0, 5.0, 75.0, 45.0, 0.8, "N/A"
        );

        // Ração Power (RACAO)
        alimentoController.cadastrarRacao(
            "Ração Power", "Fábrica X", CategoriaRacao.ATLETA,
            10.0, 18.0, 8.0, 8.0, 6.0, 15.0, 6.0, 1.0, 0.6, 0.4, 5.0
        );

        // Ração Fraca (RACAO)
        alimentoController.cadastrarRacao(
            "Ração Fraca", "Fábrica Y", CategoriaRacao.MANUTENCAO,
            12.0, 12.0, 3.0, 15.0, 12.0, 25.0, 8.0, 0.5, 0.4, 0.2, 2.0
        );

        //define cenários
        List<Cenario> cenarios = List.of(
            new Cenario("Cavalo Anão", 50, CategoriaFisiologica.MANTENCAO, "Feno Muito Poderoso", 10.0),
            new Cenario("Cavalo Gigante", 2000, CategoriaFisiologica.MANTENCAO, "Feno Fraquinho", 30.0),
            new Cenario("Cavalo Atleta Extremo", 600, CategoriaFisiologica.ATLETA_INTENSO, "Ração Power", 5.0),
            new Cenario("Égua Gestante Obesa", 800, CategoriaFisiologica.GESTANTE_FINAL, "Ração Fraca", 15.0),
            new Cenario("Potro com Pouco Consumo", 250, CategoriaFisiologica.POTRO_ATE_1_ANO, "Feno Fraquinho", 2.0),
            new Cenario("Cavalo com Consumo Zero", 450, CategoriaFisiologica.MANTENCAO, null, 0.0)
        );

        for (Cenario c : cenarios) {
            System.out.println("=== " + c.nome + " ===");
            System.out.println("Peso: " + c.peso + " kg | Categoria: " + c.categoria);

            // Cadastra o equino
            String resultadoEq = equinoController.cadastrarEquino(c.nome, c.peso, 5, c.categoria);
            System.out.println("Cadastro: " + resultadoEq);

            // Se houver alimento e consumo, registra
            if (c.nomeAlimento != null && c.quantidade > 0) {
                String resultadoConsumo = consumoController.registrarConsumo(c.nome, c.nomeAlimento, c.quantidade);
                System.out.println("Consumo: " + resultadoConsumo);
            } else {
                System.out.println("Consumo: Nenhum consumo registrado (dieta vazia)");
            }

            // Avalia
            DiagnosticoNutricional diag = avaliacaoController.avaliarEquino(c.nome);
            if (diag == null) {
                System.out.println("ERRO: Equino não encontrado!\n");
                resultados.add(new ResultadoTeste(c.nome, "ERRO", null, null));
                continue;
            }

            // Exibe diagnóstico resumido
            System.out.println("ED Exigida: " + String.format("%.2f", diag.getEdExigida()) + " Mcal/dia");
            System.out.println("ED Fornecida: " + String.format("%.2f", diag.getEdFornecida()) + " Mcal/dia");
            System.out.println("Saldo: " + String.format("%.2f", diag.getSaldo()) + " Mcal/dia");
            System.out.println("Classificação: " + diag.getClassificacao());
            System.out.println("Recomendação: " + diag.getRecomendacao());

            // Verificações se o resultado faz sentido
            verificarCorretudeResultado(diag);
            
            // Armazena para o relatório final
            resultados.add(new ResultadoTeste(c.nome, diag.getClassificacao(), diag.getRecomendacao(), diag));

            System.out.println();
        }

        //4
        System.out.println("=============================================");
        System.out.println("RESUMO DOS TESTES");
        System.out.println("=============================================");
        System.out.printf("%-25s | %-15s | %s\n", "Cavalo", "Classificação", "Recomendação Principal");
        System.out.println("-------------------------------------------------------------");
        for (ResultadoTeste r : resultados) {
            String rec = r.recomendacao;
            if (rec != null && rec.length() > 30) {
                rec = rec.substring(0, 30) + "...";
            }
            System.out.printf("%-25s | %-15s | %s\n", r.nome, r.classificacao, rec != null ? rec : "N/A");
        }
        System.out.println("=============================================");
        System.out.println("\n✅ Teste concluído!");
        
        JpaUtil.close();
    }

    // Método auxiliar para verificar se a recomendação faz sentido
    private static void verificarCorretudeResultado(DiagnosticoNutricional diag) {
        String rec = diag.getRecomendacao();
        if (rec == null || rec.isEmpty()) return;

        // Extrai números da recomendação
        String[] partes = rec.split(" ");
        for (String p : partes) {
            if (p.contains("kg/dia") || p.contains("kg")) {
                try {
                    String numStr = p.replace(",", ".").replace("kg/dia", "").trim();
                    if (numStr.startsWith("+")) numStr = numStr.substring(1);
                    double kg = Double.parseDouble(numStr);
                    if (kg > 50) {
                        System.out.println("ALERTA: Recomendação sugere adicionar " + kg + " kg/dia - valor muito alto!");
                    } else if (kg < 0.1 && kg > 0) {
                        System.out.println("ALERTA: Recomendação sugere adicionar " + kg + " kg/dia - valor muito baixo (pode ser insignificante).");
                    }
                } catch (NumberFormatException e) {
                }
            }
        }

        // Verifica se há inconsistência entre classificação e recomendação
        if (diag.getClassificacao().contains("EXCESSO") && rec.toLowerCase().contains("adicionar")) {
            System.out.println("ALERTA: Excesso energético, mas recomendação sugere ADICIONAR alimento (inconsistente).");
        }
        if (diag.getClassificacao().contains("DÉFICIT") && rec.toLowerCase().contains("reduza")) {
            System.out.println("ALERTA: Déficit energético, mas recomendação sugere REDUZIR alimento (inconsistente).");
        }
    }

    private static void limparBanco() {
        try {
            var em = JpaUtil.getEntityManager();
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Consumo").executeUpdate();
            em.createQuery("DELETE FROM Equino").executeUpdate();
            em.createQuery("DELETE FROM Alimento").executeUpdate();
            em.getTransaction().commit();
            em.close();
            System.out.println("Banco limpo para testes.");
        } catch (Exception e) {
            System.out.println("Não foi possível limpar o banco: " + e.getMessage());
        }
    }

    // Classe interna para armazenar cada cenário
    static class Cenario {
        String nome;
        double peso;
        CategoriaFisiologica categoria;
        String nomeAlimento;
        double quantidade;

        Cenario(String nome, double peso, CategoriaFisiologica categoria, String nomeAlimento, double quantidade) {
            this.nome = nome;
            this.peso = peso;
            this.categoria = categoria;
            this.nomeAlimento = nomeAlimento;
            this.quantidade = quantidade;
        }
    }

    // Classe interna para guardar os resultados
    static class ResultadoTeste {
        String nome;
        String classificacao;
        String recomendacao;
        DiagnosticoNutricional diagnostico;

        ResultadoTeste(String nome, String classificacao, String recomendacao, DiagnosticoNutricional diagnostico) {
            this.nome = nome;
            this.classificacao = classificacao;
            this.recomendacao = recomendacao;
            this.diagnostico = diagnostico;
        }
    }
}