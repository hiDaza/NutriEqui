/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ui;

/**
 *
 * @author daza
 */

import com.mycompany.controller.ConsumoController;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class RegistrarConsumoPanel extends JPanel {

    private final ConsumoController consumoController;

    private JTextField txtNomeEquino;
    private JTextField txtNomeAlimento;
    private JTextField txtQuantidade;
    private JButton btnRegistrar;
    private JLabel lblMensagem;

    public RegistrarConsumoPanel() {
        this.consumoController = new ConsumoController();
        initComponents();
    }

    private void initComponents() {
        // Estilo geral do painel
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(30, 30, 30, 30));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = new JLabel("🍽️ Registrar Consumo Diário");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 60, 90));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titulo, gbc);

        // Subtítulo
        JLabel subtitulo = new JLabel("Informe os dados do consumo do cavalo");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(100, 120, 140));
        gbc.gridy++;
        add(subtitulo, gbc);

        // Campos
        gbc.gridwidth = 1;
        gbc.gridy++;

        gbc.gridx = 0;
        JLabel lblEquino = new JLabel("Nome do Equino");
        lblEquino.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblEquino.setForeground(new Color(40, 60, 80));
        add(lblEquino, gbc);

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

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblAlimento = new JLabel("Nome do Alimento");
        lblAlimento.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblAlimento.setForeground(new Color(40, 60, 80));
        add(lblAlimento, gbc);

        gbc.gridx = 1;
        txtNomeAlimento = new JTextField();
        txtNomeAlimento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNomeAlimento.setPreferredSize(new Dimension(250, 35));
        txtNomeAlimento.setBackground(Color.WHITE);
        txtNomeAlimento.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        add(txtNomeAlimento, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblQuantidade = new JLabel("Quantidade (kg/dia)");
        lblQuantidade.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblQuantidade.setForeground(new Color(40, 60, 80));
        add(lblQuantidade, gbc);

        gbc.gridx = 1;
        txtQuantidade = new JTextField();
        txtQuantidade.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtQuantidade.setPreferredSize(new Dimension(150, 35));
        txtQuantidade.setBackground(Color.WHITE);
        txtQuantidade.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        add(txtQuantidade, gbc);

        // Botão
        gbc.gridy++;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        btnRegistrar = new JButton("Registrar Consumo");
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrar.setBackground(new Color(0, 150, 136));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrar.addActionListener(e -> registrarConsumo());
        add(btnRegistrar, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        lblMensagem = new JLabel(" ");
        lblMensagem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMensagem.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensagem.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(lblMensagem, gbc);
    }

    private void registrarConsumo() {
        lblMensagem.setText(" ");
        String nomeEquino = txtNomeEquino.getText().trim();
        String nomeAlimento = txtNomeAlimento.getText().trim();
        String quantidadeStr = txtQuantidade.getText().trim();

        if (nomeEquino.isEmpty()) {
            exibirMensagem("Informe o nome do equino.", Color.RED);
            return;
        }
        if (nomeAlimento.isEmpty()) {
            exibirMensagem("Informe o nome do alimento.", Color.RED);
            return;
        }
        if (quantidadeStr.isEmpty()) {
            exibirMensagem("Informe a quantidade.", Color.RED);
            return;
        }

        double quantidade;
        try {
            quantidade = Double.parseDouble(quantidadeStr);
        } catch (NumberFormatException e) {
            exibirMensagem("Quantidade inválida (use números).", Color.RED);
            return;
        }

        String resultado = consumoController.registrarConsumo(nomeEquino, nomeAlimento, quantidade);

        if (resultado.startsWith("Erro")) {
            exibirMensagem(" " + resultado, Color.RED);
        } else {
            exibirMensagem("✅ " + resultado, new Color(0, 150, 136));
            txtNomeEquino.setText("");
            txtNomeAlimento.setText("");
            txtQuantidade.setText("");
        }
    }

    private void exibirMensagem(String texto, Color cor) {
        lblMensagem.setText(texto);
        lblMensagem.setForeground(cor);
    }
}
