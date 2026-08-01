/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ui;

/**
 *
 * @author daza
 */
import com.mycompany.controller.AlimentoController;
import com.mycompany.controller.EquinoController;
import com.mycompany.domain.CategoriaFisiologica;
import com.mycompany.domain.TipoAlimento;
import com.mycompany.ui.MainFrame;

/**
 * Classe de teste que pré-popula o banco de dados com dados de exemplo
 * e então abre a interface principal para teste visual completo.
 */
public class UIFlowTestLauncher {

    public static void main(String[] args) {
        System.out.println("=== PRÉ-CADASTRO DE DADOS DE TESTE ===");

        // Controllers para cadastrar os dados
        EquinoController equinoController = new EquinoController();
        AlimentoController alimentoController = new AlimentoController();

        //Cadastrar cavalo "Alazao" se ja existir vai retornar erro, então quando rodar usar o create do persistence
        //caso contrario vai ficar salvo no banco o alazao
        String resultadoEquino = equinoController.cadastrarEquino(
                "Alazao",
                520.0,
                3,
                CategoriaFisiologica.ATLETA_MODERADO
        );
        System.out.println("Cavalo 'Alazao': " + resultadoEquino);

        //Cadastrar Feno Poderoso  classificacao volumoso
        String resultadoFeno = alimentoController.cadastrarAlimento(
                "Feno Poderoso",
                TipoAlimento.VOLUMOSO,
                2.1
        );
        System.out.println("Alimento 'Feno Poderoso': " + resultadoFeno);

        // 3. Cadastrar Ração Power do tipo ração
        String resultadoRacao = alimentoController.cadastrarAlimento(
                "Ração Power",
                TipoAlimento.RACAO,
                3.4
        );
        System.out.println("Alimento 'Ração Power': " + resultadoRacao);

        //Cadastrar Suplemento do tipo suplement :)
        String resultadoSuplemento = alimentoController.cadastrarAlimento(
                "Suplemento MONSTRAO",
                TipoAlimento.SUPLEMENTO,
                0.0 // Suplemento não energético, ED 0
        );
        System.out.println("Alimento 'Suplemento MONSTRAO': " + resultadoSuplemento);

        System.out.println("\n✅ Dados de teste carregados!");

        // sleep para conseguir ver os dados
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        MainFrame.main(args);
    }
}
