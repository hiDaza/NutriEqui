/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ui;

/**
 *
 * @author daza
 */
import com.mycompany.controller.AvaliacaoController;
import com.mycompany.domain.DiagnosticoNutricional;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AvaliarEquinoPanel extends JPanel {

    private final AvaliacaoController avaliacaoController;

    private JTextField txtNomeEquino;
    private JButton btnAvaliar;
    private JTextArea txtResultado;
    private JLabel lblMensagem;

    public AvaliarEquinoPanel() {
        this.avaliacaoController = new AvaliacaoController();
        initComponents();
    }

    private void initComponents() {
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(30, 30, 30, 30));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = new JLabel("Avaliar Balanço Energético");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 60, 90));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titulo, gbc);

        JLabel subtitulo = new JLabel("Calcule o balanço energético de um equino");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(100, 120, 140));
        gbc.gridy++;
        add(subtitulo, gbc);

        // Campo nome
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblNome = new JLabel("Nome do Equino");
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNome.setForeground(new Color(40, 60, 80));
        add(lblNome, gbc);

        gbc.gridx = 1;
        txtNomeEquino = new JTextField();
        txtNomeEquino.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNomeEquino.setPreferredSize(new Dimension(250, 35));
        txtNomeEquino.setBackground(Color.WHITE);
        txtNomeEquino.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        add(txtNomeEquino, gbc);

        // botão
        gbc.gridy++;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        btnAvaliar = new JButton("Avaliar");
        btnAvaliar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAvaliar.setBackground(new Color(0, 150, 136));
        btnAvaliar.setForeground(Color.WHITE);
        btnAvaliar.setFocusPainted(false);
        btnAvaliar.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        btnAvaliar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAvaliar.addActionListener(e -> avaliar());
        add(btnAvaliar, gbc);

        // area de resultado
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtResultado.setBackground(Color.WHITE);
        txtResultado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        JScrollPane scroll = new JScrollPane(txtResultado);
        scroll.setPreferredSize(new Dimension(500, 200));
        add(scroll, gbc);

        // erro equino nao encontrado
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        lblMensagem = new JLabel(" ");
        lblMensagem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMensagem.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensagem.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(lblMensagem, gbc);
    }

    private void avaliar() {
        lblMensagem.setText(" ");
        txtResultado.setText("");
        String nome = txtNomeEquino.getText().trim();

        if (nome.isEmpty()) {
            exibirMensagem("Informe o nome do equino.", Color.RED);
            return;
        }

        DiagnosticoNutricional diag = avaliacaoController.avaliarEquino(nome);

        if (diag == null) {
            exibirMensagem("Equino não encontrado.", Color.RED);
            return;
        }

        // Exibe o diagnóstico completo
        StringBuilder sb = new StringBuilder();
        sb.append("DIAGNÓSTICO NUTRICIONAL\n");
        sb.append("Cavalo: ").append(diag.getEquino().getNome()).append("\n");
        sb.append(String.format("ED Exigida: %.2f Mcal/dia\n", diag.getEdExigida()));
        sb.append(String.format("ED Fornecida: %.2f Mcal/dia\n", diag.getEdFornecida()));
        sb.append(String.format("Saldo: %.2f Mcal/dia\n", diag.getSaldo()));
        sb.append("Classificação: ").append(diag.getClassificacao()).append("\n");
        sb.append("Recomendação: ").append(diag.getRecomendacao());
        txtResultado.setText(sb.toString());
        
        //Classificador possui cores diferentes 
        if (diag.getClassificacao().contains("DÉFICIT")) {
            txtResultado.setForeground(Color.RED);
        } else if (diag.getClassificacao().contains("EXCESSO")) {
            txtResultado.setForeground(new Color(200, 100, 0)); // laranja
        } else {
            txtResultado.setForeground(new Color(0, 150, 136)); // verde
        }

        exibirMensagem("✅Avaliação concluída!", new Color(0, 150, 136));
        txtNomeEquino.setText("");
    }

    private void exibirMensagem(String texto, Color cor) {
        lblMensagem.setText(texto);
        lblMensagem.setForeground(cor);
    }
}
