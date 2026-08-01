/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ui;

/**
 *
 * @author daza
 */
import com.mycompany.controller.EquinoController;
import com.mycompany.domain.CategoriaFisiologica;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CadastrarEquinoPanel extends JPanel {

    private final EquinoController equinoController;

    private JTextField txtNome;
    private JTextField txtPeso;
    private JComboBox<CategoriaFisiologica> cbCategoria;
    private JButton btnCadastrar;
    private JLabel lblMensagem;

    public CadastrarEquinoPanel() {
        this.equinoController = new EquinoController();
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
        JLabel titulo = new JLabel("🐴 Cadastrar Equino");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 60, 90));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titulo, gbc);

        // Sub
        JLabel subtitulo = new JLabel("Cadastre um novo cavalo no sistema");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(100, 120, 140));
        gbc.gridy++;
        add(subtitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // Nome
        gbc.gridx = 0;
        JLabel lblNome = new JLabel("Nome do Equino");
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNome.setForeground(new Color(40, 60, 80));
        add(lblNome, gbc);

        gbc.gridx = 1;
        txtNome = new JTextField();
        txtNome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNome.setPreferredSize(new Dimension(250, 35));
        txtNome.setBackground(Color.WHITE);
        txtNome.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        add(txtNome, gbc);

        // Peso
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblPeso = new JLabel("Peso (kg)");
        lblPeso.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPeso.setForeground(new Color(40, 60, 80));
        add(lblPeso, gbc);

        gbc.gridx = 1;
        txtPeso = new JTextField();
        txtPeso.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPeso.setPreferredSize(new Dimension(150, 35));
        txtPeso.setBackground(Color.WHITE);
        txtPeso.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        add(txtPeso, gbc);

        // Categoria
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblCategoria = new JLabel("Categoria Fisiológica");
        lblCategoria.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblCategoria.setForeground(new Color(40, 60, 80));
        add(lblCategoria, gbc);

        gbc.gridx = 1;
        cbCategoria = new JComboBox<>(CategoriaFisiologica.values());
        cbCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbCategoria.setBackground(Color.WHITE);
        cbCategoria.setPreferredSize(new Dimension(250, 35));
        cbCategoria.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        add(cbCategoria, gbc);

        // Botão
        gbc.gridy++;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        btnCadastrar = new JButton("✔ Cadastrar Equino");
        btnCadastrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCadastrar.setBackground(new Color(0, 150, 136));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCadastrar.addActionListener(e -> cadastrar());
        add(btnCadastrar, gbc);

        // Mensagem
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

    private void cadastrar() {
        lblMensagem.setText(" ");
        String nome = txtNome.getText().trim();
        String pesoStr = txtPeso.getText().trim();

        if (nome.isEmpty()) {
            exibirMensagem("Informe o nome do equino.", Color.RED);
            return;
        }
        if (pesoStr.isEmpty()) {
            exibirMensagem("Informe o peso.", Color.RED);
            return;
        }

        double peso;
        try {
            peso = Double.parseDouble(pesoStr);
        } catch (NumberFormatException e) {
            exibirMensagem("Peso inválido (use números).", Color.RED);
            return;
        }

        if (peso <= 0) {
            exibirMensagem("Peso deve ser maior que zero.", Color.RED);
            return;
        }

        int escore = 3; // Valor padrão TROCAR DEPOIS CONFORME EVOLUIR OS CONTROLLERS
        CategoriaFisiologica categoria = (CategoriaFisiologica) cbCategoria.getSelectedItem();

        String resultado = equinoController.cadastrarEquino(nome, peso, escore, categoria);

        if (resultado.startsWith("Erro")) {
            exibirMensagem(" " + resultado, Color.RED);
        } else {
            exibirMensagem("✅ " + resultado, new Color(0, 150, 136));
            txtNome.setText("");
            txtPeso.setText("");
            cbCategoria.setSelectedIndex(0);
        }
    }

    private void exibirMensagem(String texto, Color cor) {
        lblMensagem.setText(texto);
        lblMensagem.setForeground(cor);
    }
}
