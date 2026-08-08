/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author daza
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author daza
 */
import com.mycompany.controller.AlimentoController;
import com.mycompany.controller.EquinoController;
import com.mycompany.domain.*;
import com.mycompany.ui.MainFrame;

public class UIFlowTestLauncher {

    public static void main(String[] args) {
        System.out.println("=== PRÉ-CADASTRO DE DADOS DE TESTE ===");

        EquinoController equinoController = new EquinoController();
        AlimentoController alimentoController = new AlimentoController();

        // Cadastrar cavalo "Alazao"
        String resultadoEquino = equinoController.cadastrarEquino(
                "Alazao",
                520.0,
                3,
                CategoriaFisiologica.ATLETA_MODERADO
        );
        System.out.println("Cavalo 'Alazao': " + resultadoEquino);

        // ---------- Cadastrar Feno Poderoso (VOLUMOSO) ----------
        // Usando cadastrarVolumoso com valores típicos para um feno de boa qualidade
        String resultadoFeno = alimentoController.cadastrarVolumoso(
                TipoVolumoso.TIFTON,
                CategoriaVolumoso.B,
                90.0,   // matéria seca (%)
                8.0,    // proteína bruta (%)
                65.0,   // FDN (%)
                35.0,   // FDA (%)
                2.1,    // ED (Mcal/kg)
                "Sudeste",
                50.00   //preco
        );
        System.out.println("Alimento 'Feno Poderoso': " + resultadoFeno);

        // ---------- Cadastrar Ração Power (RACAO) ----------
        String resultadoRacao = alimentoController.cadastrarRacao(
                "Ração Power",
                "Fábrica X",
                CategoriaRacao.ATLETA,
                12.0,   // umidade
                14.0,   // proteína bruta
                5.0,    // extrato etéreo
                12.0,   // fibra bruta
                10.0,   // FDA
                20.0,   // FDN
                8.0,    // matéria mineral
                0.8,    // cálcio
                0.5,    // fósforo
                0.3,    // sódio
                3.4,     // ED declarada (opcional)
                15.00    //preco
        );
        System.out.println("Alimento 'Ração Power': " + resultadoRacao);

        // ---------- Cadastrar Suplemento MONSTRAO (SUPLEMENTO) ----------
        // ED = 0.0, portanto não energético
        String resultadoSuplemento = alimentoController.cadastrarSuplemento(
                "Suplemento MONSTRAO",
                "Fábrica Y",
                "Vitaminas",
                UnidadeSuplemento.POR_DOSE,
                20.0,   // dose recomendada
                20.0,   // dose usada
                null,   // energia (opcional)
                null,   // proteína
                null,   // gordura
                null,   // cálcio
                null,   // fósforo
                null,   // sódio
                null,   // potássio
                null,   // magnésio
                null,   // selênio
                null,   // vitamina E
                null,   // biotina
                CalculoEnergetico.NAO,
                150.00 //preco altao
        );
        System.out.println("Alimento 'Suplemento MONSTRAO': " + resultadoSuplemento);

        System.out.println("\n✅ Dados de teste carregados!");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MainFrame.main(args);
    }
}