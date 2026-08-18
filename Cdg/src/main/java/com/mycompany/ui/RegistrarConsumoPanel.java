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
import com.mycompany.domain.Equino;
import com.mycompany.domain.Alimento;
import com.mycompany.repository.EquinoRepository;
import com.mycompany.repository.AlimentoRepository;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class RegistrarConsumoPanel extends JPanel {

    private final ConsumoController consumoController;
    private final EquinoRepository equinoRepository;
    private final AlimentoRepository alimentoRepository;

    private JComboBox<String> cbEquinos;
    private JComboBox<String> cbAlimentos;
    private JTextField txtQuantidade;
    private JButton btnRegistrar;
    private JLabel lblMensagem;
    private JLabel lblInfoEquino;

    public RegistrarConsumoPanel() {
        this.consumoController = new ConsumoController();
        this.equinoRepository = new EquinoRepository();
        this.alimentoRepository = new AlimentoRepository();
        initComponents();
        carregarDados();
    }
    
    


    private void initComponents() {
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(30, 30, 30, 30));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Registrar Consumo Diário");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 60, 90));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titulo, gbc);

        JLabel subtitulo = new JLabel("Selecione o cavalo, o alimento e informe a quantidade consumida por dia");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(100, 120, 140));
        gbc.gridy++;
        add(subtitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // Equino
        gbc.gridx = 0;
        JLabel lblEquino = new JLabel("Equino");
        lblEquino.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblEquino.setForeground(new Color(40, 60, 80));
        add(lblEquino, gbc);

        gbc.gridx = 1;
        cbEquinos = new JComboBox<>();
        cbEquinos.setToolTipText("Selecione um cavalo cadastrado");
        estilizarCombo(cbEquinos);
        cbEquinos.addActionListener(e -> mostrarInfoEquino());
        add(cbEquinos, gbc);

        // Informações do equino
        gbc.gridy++;
        gbc.gridx = 1;
        lblInfoEquino = new JLabel(" ");
        lblInfoEquino.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInfoEquino.setForeground(new Color(100, 120, 140));
        add(lblInfoEquino, gbc);

        // Alimento
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblAlimento = new JLabel("Alimento");
        lblAlimento.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblAlimento.setForeground(new Color(40, 60, 80));
        add(lblAlimento, gbc);

        gbc.gridx = 1;
        cbAlimentos = new JComboBox<>();
        cbAlimentos.setToolTipText("Selecione um alimento cadastrado");
        estilizarCombo(cbAlimentos);
        add(cbAlimentos, gbc);

        // Quantidade
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblQuantidade = new JLabel("Quantidade (kg/dia)");
        lblQuantidade.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblQuantidade.setForeground(new Color(40, 60, 80));
        add(lblQuantidade, gbc);

        gbc.gridx = 1;
        txtQuantidade = new JTextField();
        txtQuantidade.setToolTipText("Digite a quantidade consumida por dia");
        estilizarCampo(txtQuantidade);
        txtQuantidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                validarQuantidade();
            }
        });
        add(txtQuantidade, gbc);

        // Botão
        gbc.gridy++;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        btnRegistrar = new JButton("Registrar Consumo");
        estilizarBotao(btnRegistrar);
        btnRegistrar.addActionListener(e -> registrarConsumo());
        add(btnRegistrar, gbc);

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

    
    public void carregarDados() {
        // Carrega equinos
        List<Equino> equinos = equinoRepository.listarTodos();
        cbEquinos.removeAllItems();
        for (Equino e : equinos) {
            cbEquinos.addItem(e.getNome());
        }
        if (cbEquinos.getItemCount() > 0) {
            cbEquinos.setSelectedIndex(0);
            mostrarInfoEquino();
        }

        // Carrega alimentos
        List<Alimento> alimentos = alimentoRepository.listarTodos();
        cbAlimentos.removeAllItems();
        for (Alimento a : alimentos) {
            cbAlimentos.addItem(a.getNome() + " (" + a.getTipo() + ")");
        }
    }

    private void mostrarInfoEquino() {
        String nome = (String) cbEquinos.getSelectedItem();
        if (nome != null) {
            Equino e = equinoRepository.buscarPorNome(nome);
            if (e != null) {
                lblInfoEquino.setText(String.format("Peso: %.1f kg | Categoria: %s", e.getPeso(), e.getCategoria()));
            } else {
                lblInfoEquino.setText(" ");
            }
        }
    }

    private void validarQuantidade() {
        String texto = txtQuantidade.getText().trim();
        if (!texto.isEmpty()) {
            try { 
                String textoNormalizado = texto.replace(",",".");
                double qtd = Double.parseDouble(textoNormalizado);
                if (qtd <= 0) {
                    txtQuantidade.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.RED, 1, true),
                            BorderFactory.createEmptyBorder(5, 10, 5, 10)
                    ));
                } else {
                    txtQuantidade.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(0, 150, 136), 1, true),
                            BorderFactory.createEmptyBorder(5, 10, 5, 10)
                    ));
                }
            } catch (NumberFormatException e) {
                txtQuantidade.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.RED, 1, true),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }
        }
    }

    private void registrarConsumo() {
        lblMensagem.setText("");
        String nomeEquino = (String) cbEquinos.getSelectedItem();
        String alimentoCombo = (String) cbAlimentos.getSelectedItem();
        String quantidadeStr = txtQuantidade.getText().trim();

        // Extrai o nome do alimento
        String nomeAlimento = alimentoCombo;
        if (alimentoCombo != null && alimentoCombo.contains("(")) {
            nomeAlimento = alimentoCombo.substring(0, alimentoCombo.indexOf("(")).trim();
        }

        if (nomeEquino == null || nomeEquino.isEmpty()) {
            exibirMensagem("Selecione um equino.", Color.RED);
            return;
        }
        if (alimentoCombo == null || alimentoCombo.isEmpty()) {
            exibirMensagem("Selecione um alimento.", Color.RED);
            return;
        }
        if (quantidadeStr.isEmpty()) {
            exibirMensagem("Informe a quantidade.", Color.RED);
            txtQuantidade.requestFocus();
            return;
        }

        double quantidade;
        try {
            quantidade = Double.parseDouble(quantidadeStr.replace(",","."));
        } catch (NumberFormatException e) {
            exibirMensagem("Quantidade inválida! Use números).", Color.RED);
            txtQuantidade.requestFocus();
            return;
        }

        if (quantidade <= 0) {
            exibirMensagem("Quantidade deve ser maior que zero.", Color.RED);
            txtQuantidade.requestFocus();
            return;
        }

        String resultado = consumoController.registrarConsumo(nomeEquino, nomeAlimento, quantidade);

        if (resultado.startsWith("Erro")) {
            exibirMensagem(" " + resultado, Color.RED);
        } else {
            exibirMensagem("✅ " + resultado, new Color(0, 150, 136));
            txtQuantidade.setText("");
            // Restaura borda
            txtQuantidade.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            // Recarrega os dados para refletir novos cadastros
            carregarDados();
        }
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

    private void exibirMensagem(String texto, Color cor) {
        lblMensagem.setText(texto);
        lblMensagem.setForeground(cor);
        new Timer(2000, e -> lblMensagem.setText(" ")).start();
    }
}