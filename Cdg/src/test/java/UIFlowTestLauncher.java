import com.mycompany.controller.AlimentoController;
import com.mycompany.controller.ConsumoController;
import com.mycompany.controller.EquinoController;
import com.mycompany.domain.*;
import com.mycompany.ui.MainFrame;

public class UIFlowTestLauncher {

    public static void main(String[] args) {
        System.out.println("PRE-CADASTRO DE DADOS DE TESTE");

        EquinoController equinoController = new EquinoController();
        AlimentoController alimentoController = new AlimentoController();
        ConsumoController consumoController = new ConsumoController();

        //CADASTRO DE EQUINOS (diversas categorias)
        System.out.println("\nCadastrando equinos...");

        String eq1 = equinoController.cadastrarEquino(
                "Alazao", 520.0, 3, CategoriaFisiologica.ATLETA_MODERADO
        );
        System.out.println("  " + eq1);

        String eq2 = equinoController.cadastrarEquino(
                "Pampa", 480.0, 5, CategoriaFisiologica.MANTENCAO
        );
        System.out.println("  " + eq2);

        String eq3 = equinoController.cadastrarEquino(
                "Estrela", 550.0, 6, CategoriaFisiologica.GESTANTE_FINAL
        );
        System.out.println("  " + eq3);

        String eq4 = equinoController.cadastrarEquino(
                "Foguinho", 280.0, 4, CategoriaFisiologica.POTRO_ATE_1_ANO
        );
        System.out.println("  " + eq4);

        String eq5 = equinoController.cadastrarEquino(
                "Trovão", 600.0, 4, CategoriaFisiologica.ATLETA_INTENSO
        );
        System.out.println("  " + eq5);

        //CADASTRO DE ALIMENTOS
        System.out.println("\nCadastrando alimentos...");

        //volumoso 1: Feno Tifton B (qualidade média)
        String vol1 = alimentoController.cadastrarVolumoso(
                TipoVolumoso.TIFTON,
                CategoriaVolumoso.B,
                90.0,   // matéria seca %
                8.5,    // proteína bruta %
                68.0,   // FDN %
                38.0,   // FDA %
                2.1,    // ED Mcal/kg
                "Sudeste",
                1.20    // preço R$/kg
        );
        System.out.println("  " + vol1);

        //Volumoso 2: Coast-cross C (qualidade mais baixa)
        String vol2 = alimentoController.cadastrarVolumoso(
                TipoVolumoso.COAST_CROSS,
                CategoriaVolumoso.C,
                85.0, 6.0, 75.0, 45.0, 1.6,
                "Sul",
                0.90
        );
        System.out.println("  " + vol2);

        //Ração 1: Atleta Premium
        String rac1 = alimentoController.cadastrarRacao(
                "Racao Atleta Premium",
                "NutriEqui",
                CategoriaRacao.ATLETA,
                12.0, 16.0, 6.0, 10.0, 8.0, 18.0, 7.0, 0.9, 0.6, 0.3,
                3.6,    // ED declarada
                3.80    // preço
        );
        System.out.println("  " + rac1);

        //Ração 2: Manutenção Leve
        String rac2 = alimentoController.cadastrarRacao(
                "Racao Manutencao Leve",
                "NutriEqui",
                CategoriaRacao.MANUTENCAO,
                12.0, 12.0, 3.0, 15.0, 12.0, 25.0, 8.0, 0.5, 0.4, 0.2,
                2.4,    // ED declarada
                2.50
        );
        System.out.println("  " + rac2);

        //Suplemento 1: Energético (entra no cálculo)
        String sup1 = alimentoController.cadastrarSuplemento(
                "Suplemento Energético Plus",
                "VetPower",
                "Energéticos",
                UnidadeSuplemento.POR_DOSE,
                100.0, 100.0,
                3.5,    // energia em kcal (será convertido para Mcal)
                null,   // proteína
                10.0,   // gordura
                null,   // cálcio
                null,   // fósforo
                null,   // sódio
                null,   // potássio
                null,   // magnésio
                null,   // selênio
                null,   // vitamina E
                null,   // biotina
                CalculoEnergetico.SIM,
                4.50
        );
        System.out.println("  " + sup1);

        //Suplemento 2: Vitamínico (não entra no cálculo)
        String sup2 = alimentoController.cadastrarSuplemento(
                "Vitaminas Minerais",
                "NutriVet",
                "Vitaminas",
                UnidadeSuplemento.POR_DOSE,
                50.0, 50.0,
                null, null, null,
                5000.0, 3000.0, 1000.0, 2000.0, 500.0,
                100.0, 500.0, null,
                CalculoEnergetico.NAO,
                5.00
        );
        System.out.println("  " + sup2);

        //REGISTRO DE CONSUMOS
        System.out.println("\nRegistrando consumos...");

        // Alazao: dieta atleta (volumoso + ração)
        String c1 = consumoController.registrarConsumo("Alazao", "Tifton - Categoria B", 10.0);
        System.out.println("  Alazao -> Feno: " + c1);
        String c2 = consumoController.registrarConsumo("Alazao", "Racao Atleta Premium", 3.0);
        System.out.println("  Alazao -> Ração: " + c2);

        // Pampa: manutenção (apenas volumoso)
        String c3 = consumoController.registrarConsumo("Pampa", "Coast-cross - Categoria C", 12.0);
        System.out.println("  Pampa -> Feno: " + c3);

        // Estrela: gestante (volumoso + ração + suplemento vitamínico)
        String c4 = consumoController.registrarConsumo("Estrela", "Tifton - Categoria B", 12.0);
        System.out.println("  Estrela -> Feno: " + c4);
        String c5 = consumoController.registrarConsumo("Estrela", "Racao Manutencao Leve", 2.0);
        System.out.println("  Estrela -> Ração: " + c5);
        // Registrar suplemento (usando o método específico)
        String c6 = consumoController.registrarSuplemento("Estrela", "Vitaminas Minerais", 50.0, false);
        System.out.println("  Estrela -> Suplemento: " + c6);

        // Foguinho: potro (volumoso + ração)
        String c7 = consumoController.registrarConsumo("Foguinho", "Coast-cross - Categoria C", 4.0);
        System.out.println("  Foguinho -> Feno: " + c7);
        String c8 = consumoController.registrarConsumo("Foguinho", "Racao Manutencao Leve", 1.0);
        System.out.println("  Foguinho -> Ração: " + c8);

        // Trovão: atleta intenso (apenas ração + suplemento energético)
        String c9 = consumoController.registrarConsumo("Trovão", "Racao Atleta Premium", 4.0);
        System.out.println("  Trovão -> Ração: " + c9);
        // Registrar suplemento energético (entra no cálculo)
        String c10 = consumoController.registrarSuplemento("Trovão", "Suplemento Energético Plus", 100.0, true);
        System.out.println("  Trovão -> Suplemento: " + c10);

        System.out.println("\nDados de teste carregados com sucesso!");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MainFrame.main(args);
    }
}