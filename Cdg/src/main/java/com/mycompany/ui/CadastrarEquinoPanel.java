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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;

public class CadastrarEquinoPanel extends JPanel {

    private final EquinoController equinoController;
    private MainFrame mainFrame;

    private JTextField txtNome;
    private JTextField txtPeso;
    private JSlider sliderScore;
    private JLabel lblScoreValor;
    private JComboBox<CategoriaFisiologica> cbCategoria;
    private JButton btnCadastrar;
    private JLabel lblMensagem;

    
    public void setMainFrame(MainFrame mainFrame){
        this.mainFrame = mainFrame;
    }
    
    
    public CadastrarEquinoPanel() {
        this.equinoController = new EquinoController(); //iniciando controlador aqui e nenhum lugar mais
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

        JLabel subtitulo = new JLabel("Preencha os dados do cavalo para cadastrá-lo no sistema");
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
        txtNome.setToolTipText("Digite o nome do cavalo");
        estilizarCampo(txtNome);
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
        txtPeso.setToolTipText("Digite o peso estimado em quilogramas");
        estilizarCampo(txtPeso);
        txtPeso.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                validarPeso();
            }
        });
        add(txtPeso, gbc);

        // score Corporal agora com slider
        //
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblScore = new JLabel("Score Corporal (1-9)");
        lblScore.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblScore.setForeground(new Color(40, 60, 80));
        add(lblScore, gbc);

        gbc.gridx = 1;
        JPanel panelSlider = new JPanel(new BorderLayout(10, 0));
        panelSlider.setOpaque(false);
        sliderScore = new JSlider(1, 9, 5);
        sliderScore.setMajorTickSpacing(2);
        sliderScore.setMinorTickSpacing(1);
        sliderScore.setPaintTicks(true);
        sliderScore.setPaintLabels(true);
        sliderScore.setSnapToTicks(true);
        sliderScore.setToolTipText("1 = Muito magro, 9 = Muito obeso");
        sliderScore.addChangeListener((ChangeEvent e) -> {
            lblScoreValor.setText(String.valueOf(sliderScore.getValue()));
        });
        panelSlider.add(sliderScore, BorderLayout.CENTER);
        lblScoreValor = new JLabel("5");
        lblScoreValor.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblScoreValor.setForeground(new Color(0, 150, 136));
        panelSlider.add(lblScoreValor, BorderLayout.EAST);
        add(panelSlider, gbc);

        // Categoria
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblCategoria = new JLabel("Categoria Fisiológica");
        lblCategoria.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblCategoria.setForeground(new Color(40, 60, 80));
        add(lblCategoria, gbc);

        gbc.gridx = 1;
        cbCategoria = new JComboBox<>(CategoriaFisiologica.values());
        cbCategoria.setToolTipText("Selecione a categoria que melhor descreve o cavalo");
        estilizarCombo(cbCategoria);
        add(cbCategoria, gbc);

        // Botão
        gbc.gridy++;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        btnCadastrar = new JButton("Cadastrar Equino");
        estilizarBotao(btnCadastrar);
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

    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setPreferredSize(new Dimension(250, 35));
        campo.setBackground(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private void estilizarCombo(JComboBox<?> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(Color.WHITE);
        combo.setPreferredSize(new Dimension(250, 35));
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private void estilizarBotao(JButton botao) {
        botao.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botao.setBackground(new Color(0, 150, 136));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void validarPeso() {
        String pesoStr = txtPeso.getText().trim();
        if (!pesoStr.isEmpty()) {
            try {
                double peso = Double.parseDouble(pesoStr);
                if (peso <= 0) {
                    txtPeso.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.RED, 1, true),
                            BorderFactory.createEmptyBorder(5, 10, 5, 10)
                    ));
                } else {
                    txtPeso.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(0, 150, 136), 1, true),
                            BorderFactory.createEmptyBorder(5, 10, 5, 10)
                    ));
                }
            } catch (NumberFormatException e) {
                txtPeso.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.RED, 1, true),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }
        }
    }

    private void cadastrar() {
        lblMensagem.setText("");
        String nome = txtNome.getText().trim();
        String pesoStr = txtPeso.getText().trim();

        if (nome.isEmpty()) {
            exibirMensagem("Informe o nome do equino.", Color.RED);
            txtNome.requestFocus();
            return;
        }
        if (pesoStr.isEmpty()) {
            exibirMensagem("Informe o peso.", Color.RED);
            txtPeso.requestFocus();
            return;
        }

        double peso;
        try {
            peso = Double.parseDouble(pesoStr);
        } catch (NumberFormatException e) {
            exibirMensagem("Peso inválido!. Use números).", Color.RED);
            txtPeso.requestFocus();
            return;
        }

        if (peso <= 0) {
            exibirMensagem("Peso deve ser maior que zero.", Color.RED);
            txtPeso.requestFocus();
            return;
        }

        int score = sliderScore.getValue();
        CategoriaFisiologica categoria = (CategoriaFisiologica) cbCategoria.getSelectedItem();

        String resultado = equinoController.cadastrarEquino(nome, peso, score, categoria);

        if (resultado.startsWith("Erro")) {
            exibirMensagem("" + resultado, Color.RED);
        } else {
            exibirMensagem("✅ " + resultado, new Color(0, 150, 136));
            // Limpa campos
            txtNome.setText("");
            txtPeso.setText("");
            sliderScore.setValue(5);
            cbCategoria.setSelectedIndex(0);
            if(mainFrame != null){
                mainFrame.atualizarDados();
            }
            // Restaura borda padrão
            txtPeso.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        }
    }

    private void exibirMensagem(String texto, Color cor) {
        lblMensagem.setText(texto);
        lblMensagem.setForeground(cor);
    }
}