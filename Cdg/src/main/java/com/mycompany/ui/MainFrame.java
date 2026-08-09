/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ui;

/**
 *
 * @author daza
 */

import com.mycompany.repository.JpaUtil;
import java.awt.*;
import javax.swing.*;
import java.util.concurrent.ExecutionException;

public class MainFrame extends JFrame {
    private RegistrarConsumoPanel registrarConsumoPanel;
    private AvaliarEquinoPanel avaliarEquinoPanel;
    private HistoricoAvaliacoesPanel historicoAvaliacoesPanel;

    public MainFrame() {
        
        setTitle("🌾NutriEqui Campo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 650);
        setLocationRelativeTo(null);

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        
        CadastrarEquinoPanel cadastrarEquinoPanel = new CadastrarEquinoPanel();
        CadastrarAlimentoPanel cadastrarAlimentoPanel = new CadastrarAlimentoPanel();
        registrarConsumoPanel = new RegistrarConsumoPanel();
        avaliarEquinoPanel = new AvaliarEquinoPanel();
        historicoAvaliacoesPanel = new HistoricoAvaliacoesPanel();

        cadastrarEquinoPanel.setMainFrame(this);
        cadastrarAlimentoPanel.setMainFrame(this);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));

        tabbedPane.addTab("🐴Cadastrar Equino",  cadastrarEquinoPanel);
        tabbedPane.addTab("🌿Cadastrar Alimento",  cadastrarAlimentoPanel);
        tabbedPane.addTab("Registrar Consumo",  registrarConsumoPanel);
        tabbedPane.addTab("Avaliar Balanço",  avaliarEquinoPanel);
        tabbedPane.addTab("Histórico", historicoAvaliacoesPanel);

        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        JLabel footer = new JLabel("NutriEqui Campo v1.0 - MVP", SwingConstants.CENTER);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footer.setForeground(Color.GRAY);
        footer.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        getContentPane().add(footer, BorderLayout.SOUTH);
    }
    
    
     //meotdo  para atualização de dados
    public void atualizarDados() {
        SwingUtilities.invokeLater(() -> {
            if (registrarConsumoPanel != null) {
                registrarConsumoPanel.carregarDados();
                System.out.println("RegistrarConsumoPanel recarregado!"); // verificacao do erro
            }
            if (avaliarEquinoPanel != null) {
                avaliarEquinoPanel.carregarEquinos();
                avaliarEquinoPanel.atualizarResumoDieta();
                System.out.println("AvaliarEquinoPanel recarregado!"); // verificacao do erro 
            }
            if (historicoAvaliacoesPanel != null) {
                historicoAvaliacoesPanel.carregarEquinos();
                System.out.println("HistoricoAvaliacoesPanel recarregado!");
            }
        });
    }

    public static void main(String[] args) {
        // splash de carregamento 
        JWindow splash = new JWindow();
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel labelTitulo = new JLabel("🌾NutriEqui Campo", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        labelTitulo.setForeground(new Color(30, 60, 90));
        panel.add(labelTitulo, BorderLayout.NORTH);

        // Barra de progresso indeterminada
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setForeground(new Color(0, 150, 136));
        progressBar.setPreferredSize(new Dimension(250, 20));
        panel.add(progressBar, BorderLayout.CENTER);

        // Texto de status
        JLabel labelStatus = new JLabel("Carregando sistema e conectando ao banco de dados...", SwingConstants.CENTER);
        labelStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        labelStatus.setForeground(Color.GRAY);
        panel.add(labelStatus, BorderLayout.SOUTH);

        splash.getContentPane().add(panel);
        splash.setSize(350, 160);
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);

        //força iniciar o hibernate quand o progrmaa abre para nao precisar esperar ao realizar alguma ação de cadastro inicial
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                JpaUtil.getEntityManager().close();
                return null;
            }

            @Override
            protected void done() {
                // Fecha a tela de carregamento
                splash.dispose();


                SwingUtilities.invokeLater(() -> {
                    new MainFrame().setVisible(true);
                });

                try {
                    get(); 
                } catch (InterruptedException | ExecutionException e) {
                    JOptionPane.showMessageDialog(null,
                        "Erro ao conectar ao banco de dados.\nVerifique o MySQL e tente novamente.",
                        "Erro de Conexão",
                        JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }
}
