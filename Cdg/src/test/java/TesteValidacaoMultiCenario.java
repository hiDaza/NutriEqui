

/**
 * Teste automatizado para validar o balanço energético, custos e alertas
 * em múltiplos cenários reais, comparando com valores esperados.
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

/**
 * Teste automatizado para validar o balanço energético, custos e alertas
 * em múltiplos cenários reais, comparando com valores esperados.
 * 
 * Saída profissional, sem emojis e sem logs do Hibernate.
 */
public class TesteValidacaoMultiCenario {

    private static final double TOLERANCIA = 0.01;

    private static final EquinoController equinoController = new EquinoController();
    private static final AlimentoController alimentoController = new AlimentoController();
    private static final ConsumoController consumoController = new ConsumoController();
    private static final AvaliacaoController avaliacaoController = new AvaliacaoController();

    // Lista de cenários
    private static final List<CenarioTeste> cenarios = new ArrayList<>();

    public static void main(String[] args) {
        // Suprimir logs do Hibernate
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Logger.getLogger("org.hibernate.sql").setLevel(Level.SEVERE);
        Logger.getLogger("org.hibernate.type").setLevel(Level.SEVERE);
        Logger.getLogger("org.hibernate.engine").setLevel(Level.SEVERE);
        Logger.getLogger("org.hibernate.tool").setLevel(Level.SEVERE);
        Logger.getLogger("org.hibernate.resource").setLevel(Level.SEVERE);
        Logger.getLogger("jakarta.persistence").setLevel(Level.SEVERE);
        Logger.getLogger("com.zaxxer").setLevel(Level.SEVERE);
        Logger.getLogger("com.mysql").setLevel(Level.SEVERE);

        System.out.println("=== TESTE DE VALIDACAO MULTI-CENARIO ===\n");

        limparBanco();

        boolean alimentosCadastrados = cadastrarAlimentosBase();
        if (!alimentosCadastrados) {
            System.out.println("[ERRO] Falha ao cadastrar alimentos base. Encerrando.");
            JpaUtil.close();
            return;
        }

        definirCenarios();

        int totalCenarios = cenarios.size();
        int sucessos = 0;
        int falhas = 0;

        for (CenarioTeste cenario : cenarios) {
            System.out.println("--- " + cenario.nome + " ---");
            boolean sucesso = executarCenario(cenario);
            if (sucesso) {
                sucessos++;
            } else {
                falhas++;
            }
            System.out.println();
        }

        System.out.println("========================================");
        System.out.println("RESUMO FINAL:");
        System.out.println("  Total de cenarios: " + totalCenarios);
        System.out.println("  Sucessos: " + sucessos);
        System.out.println("  Falhas: " + falhas);
        if (falhas == 0) {
            System.out.println("STATUS: TODOS OS CENARIOS PASSARAM");
        } else {
            System.out.println("STATUS: ALGUM CENARIO FALHOU");
        }
        System.out.println("========================================");

        JpaUtil.close();
    }

    // ============================================================
    // DEFINIÇÃO DOS CENÁRIOS
    // ============================================================
    private static void definirCenarios() {
        cenarios.add(new CenarioTeste(
                "Cenario 1: Atleta Moderado com Feno + Racao + Suplemento",
                "Spirit",
                500.0,
                5,
                CategoriaFisiologica.ATLETA_MODERADO,
                new ConsumoTeste("Feno Tifton 85", 10.0),
                new ConsumoTeste("Racao Atleta 3.4", 3.0),
                new ConsumoTeste("Suplemento Vitaminico", 0.1),
                26.40, 31.20, 4.80, 23.00, 690.00,
                "EXCESSO ENERGÉTICO",
                0
        ));

        cenarios.add(new CenarioTeste(
                "Cenario 2: Manutencao com Feno Fraco + Racao Leve",
                "Pe de Pano",
                450.0,
                4,
                CategoriaFisiologica.MANTENCAO,
                new ConsumoTeste("Feno Fraquinho", 8.0),
                new ConsumoTeste("Racao Leve", 1.5),
                null,
                14.85, 18.15, 3.30, 11.75, 352.50,
                "EXCESSO ENERGÉTICO",
                0
        ));

        cenarios.add(new CenarioTeste(
                "Cenario 3: Egua Gestante Final",
                "Estrela",
                520.0,
                6,
                CategoriaFisiologica.GESTANTE_FINAL,
                new ConsumoTeste("Feno Tifton 85", 12.0),
                new ConsumoTeste("Racao Atleta 3.4", 2.0),
                null,
                20.59, 32.00, 11.41, 21.40, 642.00,
                "EXCESSO ENERGÉTICO",
                1
        ));

        cenarios.add(new CenarioTeste(
                "Cenario 4: Potro em Crescimento (deficit)",
                "Foguinho",
                250.0,
                4,
                CategoriaFisiologica.POTRO_ATE_1_ANO,
                new ConsumoTeste("Feno Fraquinho", 4.0),
                new ConsumoTeste("Racao Leve", 1.0),
                null,
                14.85, 9.70, -5.15, 6.50, 195.00,
                "DÉFICIT ENERGÉTICO",
                1
        ));
    }

    // ============================================================
    // CADASTRO DE ALIMENTOS BASE
    // ============================================================
    private static boolean cadastrarAlimentosBase() {
        System.out.println("Cadastrando alimentos base...");

        // Feno Tifton 85 (volumoso)
        String resultadoFeno = alimentoController.cadastrarVolumoso(
                TipoVolumoso.TIFTON,
                CategoriaVolumoso.B,
                90.0, 8.5, 68.0, 38.0, 2.1, "Sudeste", 1.20
        );
        System.out.println("  " + resultadoFeno);
        if (resultadoFeno.startsWith("Erro")) return false;

        // Feno Fraquinho (volumoso)
        String resultadoFenoFraco = alimentoController.cadastrarVolumoso(
                TipoVolumoso.OUTRO,
                CategoriaVolumoso.C,
                85.0, 5.0, 75.0, 45.0, 1.8, "N/A", 1.00
        );
        System.out.println("  " + resultadoFenoFraco);
        if (resultadoFenoFraco.startsWith("Erro")) return false;

        // Ração Atleta 3.4 (COM ED DECLARADA)
        String resultadoRacao = alimentoController.cadastrarRacao(
                "Racao Atleta 3.4",
                "NutriEqui",
                CategoriaRacao.ATLETA,
                12.0, 14.0, 5.5, 10.0, 9.0, 18.0, 7.0, 0.9, 0.6, 0.3,
                3.4,   
                3.50
        );
        System.out.println("  " + resultadoRacao);
        if (resultadoRacao.startsWith("Erro")) return false;

        // Ração Leve (COM ED DECLARADA)
        String resultadoRacaoLeve = alimentoController.cadastrarRacao(
                "Racao Leve",
                "NutriEqui",
                CategoriaRacao.MANUTENCAO,
                12.0, 12.0, 3.0, 15.0, 12.0, 25.0, 8.0, 0.5, 0.4, 0.2,
                2.5,   
                2.50
        );
        System.out.println("  " + resultadoRacaoLeve);
        if (resultadoRacaoLeve.startsWith("Erro")) return false;

        // Suplemento (sem alterações)
        String resultadoSuplemento = alimentoController.cadastrarSuplemento(
                "Suplemento Vitaminico",
                "NutriVet",
                "Vitaminas",
                UnidadeSuplemento.POR_DOSE,
                100.0, 100.0, null, null, null,
                5000.0, 3000.0, 1000.0, 2000.0, 500.0,
                100.0, 500.0, null,
                CalculoEnergetico.NAO,
                5.00
        );
        System.out.println("  " + resultadoSuplemento);
        if (resultadoSuplemento.startsWith("Erro")) return false;

        System.out.println("Alimentos base cadastrados com sucesso.\n");
        return true;
    }

    // ============================================================
    // EXECUÇÃO DE UM CENÁRIO
    // ============================================================
    private static boolean executarCenario(CenarioTeste cenario) {
        // Cadastrar equino
        String resultadoEquino = equinoController.cadastrarEquino(
                cenario.nomeEquino,
                cenario.peso,
                cenario.score,
                cenario.categoria
        );
        System.out.println("  Equino: " + resultadoEquino);
        if (resultadoEquino.startsWith("Erro")) return false;

        // Registrar consumos
        for (ConsumoTeste ct : cenario.consumos) {
            if (ct == null) continue;
            String nomeAlimento = ct.nomeAlimento;
            // Ajustar nomes para corresponder ao que o sistema gera
            if (ct.nomeAlimento.equals("Feno Tifton 85")) {
                nomeAlimento = "Tifton - Categoria B";
            } else if (ct.nomeAlimento.equals("Feno Fraquinho")) {
                nomeAlimento = "Outro - Categoria C";
            }
            String resultadoConsumo = consumoController.registrarConsumo(
                    cenario.nomeEquino,
                    nomeAlimento,
                    ct.quantidadeKg
            );
            System.out.println("  Consumo (" + ct.nomeAlimento + "): " + resultadoConsumo);
            if (resultadoConsumo.startsWith("Erro")) return false;
        }

        // Avaliar
        DiagnosticoNutricional diag = avaliacaoController.avaliarEquino(cenario.nomeEquino);
        if (diag == null) {
            System.out.println("  [ERRO] Equino nao encontrado.");
            return false;
        }

        // Exibir diagnóstico obtido
        System.out.println("  Diagnostico obtido:");
        System.out.printf("    ED Exigida:     %.2f Mcal/dia\n", diag.getEdExigida());
        System.out.printf("    ED Fornecida:   %.2f Mcal/dia\n", diag.getEdFornecida());
        System.out.printf("    Saldo:          %.2f Mcal/dia\n", diag.getSaldo());
        System.out.println("    Classificacao:  " + diag.getClassificacao());
        System.out.printf("    Custo Diario:   R$ %.2f\n", diag.getCustoDiario());
        System.out.printf("    Custo Mensal:   R$ %.2f\n", diag.getCustoMensal());
        System.out.println("    Alertas:        " + (diag.getAlertas().isEmpty() ? "Nenhum" : diag.getAlertas()));

        // Comparações
        boolean sucesso = true;

        sucesso &= comparar("ED Exigida", diag.getEdExigida(), cenario.edExigidaEsperada);
        sucesso &= comparar("ED Fornecida", diag.getEdFornecida(), cenario.edFornecidaEsperada);
        sucesso &= comparar("Saldo", diag.getSaldo(), cenario.saldoEsperado);
        sucesso &= comparar("Custo Diario", diag.getCustoDiario(), cenario.custoDiarioEsperado);
        sucesso &= comparar("Custo Mensal", diag.getCustoMensal(), cenario.custoMensalEsperado);

        if (!cenario.classificacaoEsperada.equals(diag.getClassificacao())) {
            System.out.printf("    [ERRO] Classificacao esperada: %s | Obtida: %s\n",
                    cenario.classificacaoEsperada, diag.getClassificacao());
            sucesso = false;
        } else {
            System.out.println("    [OK] Classificacao: " + cenario.classificacaoEsperada);
        }

        int alertasObtidos = diag.getAlertas().size();
        if (alertasObtidos != cenario.numeroAlertasEsperado) {
            System.out.printf("    [ERRO] Numero de alertas esperado: %d | Obtido: %d\n",
                    cenario.numeroAlertasEsperado, alertasObtidos);
            sucesso = false;
        } else {
            System.out.println("    [OK] Alertas: " + alertasObtidos);
        }

        return sucesso;
    }

    private static boolean comparar(String nome, double obtido, double esperado) {
        if (Math.abs(obtido - esperado) > TOLERANCIA) {
            System.out.printf("    [ERRO] %s esperado: %.2f | Obtido: %.2f (diferenca: %.2f)\n",
                    nome, esperado, obtido, Math.abs(obtido - esperado));
            return false;
        } else {
            System.out.printf("    [OK] %s: %.2f\n", nome, obtido);
            return true;
        }
    }

    // ============================================================
    // LIMPEZA DO BANCO
    // ============================================================
    private static void limparBanco() {
        try {
            var em = JpaUtil.getEntityManager();
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Consumo").executeUpdate();
            em.createQuery("DELETE FROM Equino").executeUpdate();
            em.createQuery("DELETE FROM Alimento").executeUpdate();
            em.getTransaction().commit();
            em.close();
            System.out.println("Banco limpo para testes.\n");
        } catch (Exception e) {
            System.out.println("Nao foi possivel limpar o banco: " + e.getMessage());
        }
    }

    // ============================================================
    // CLASSES AUXILIARES
    // ============================================================
    static class ConsumoTeste {
        String nomeAlimento;
        double quantidadeKg;

        ConsumoTeste(String nomeAlimento, double quantidadeKg) {
            this.nomeAlimento = nomeAlimento;
            this.quantidadeKg = quantidadeKg;
        }
    }

    static class CenarioTeste {
        String nome;
        String nomeEquino;
        double peso;
        int score;
        CategoriaFisiologica categoria;
        List<ConsumoTeste> consumos;
        double edExigidaEsperada;
        double edFornecidaEsperada;
        double saldoEsperado;
        double custoDiarioEsperado;
        double custoMensalEsperado;
        String classificacaoEsperada;
        int numeroAlertasEsperado;

        CenarioTeste(String nome, String nomeEquino, double peso, int score,
                     CategoriaFisiologica categoria,
                     ConsumoTeste c1, ConsumoTeste c2, ConsumoTeste c3,
                     double edExigidaEsperada, double edFornecidaEsperada,
                     double saldoEsperado, double custoDiarioEsperado,
                     double custoMensalEsperado, String classificacaoEsperada,
                     int numeroAlertasEsperado) {
            this.nome = nome;
            this.nomeEquino = nomeEquino;
            this.peso = peso;
            this.score = score;
            this.categoria = categoria;
            this.consumos = new ArrayList<>();
            if (c1 != null) this.consumos.add(c1);
            if (c2 != null) this.consumos.add(c2);
            if (c3 != null) this.consumos.add(c3);
            this.edExigidaEsperada = edExigidaEsperada;
            this.edFornecidaEsperada = edFornecidaEsperada;
            this.saldoEsperado = saldoEsperado;
            this.custoDiarioEsperado = custoDiarioEsperado;
            this.custoMensalEsperado = custoMensalEsperado;
            this.classificacaoEsperada = classificacaoEsperada;
            this.numeroAlertasEsperado = numeroAlertasEsperado;
        }
    }
}