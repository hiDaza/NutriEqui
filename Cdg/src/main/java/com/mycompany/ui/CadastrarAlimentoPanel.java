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
import com.mycompany.domain.TipoAlimento;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CadastrarAlimentoPanel extends JPanel {

    private final AlimentoController alimentoController;
    private MainFrame mainFrame;

    private JTextField txtNome;
    private JTextField txtED;
    private JComboBox<TipoAlimento> cbTipo;
    private JButton btnCadastrar;
    private JLabel lblMensagem;

    public CadastrarAlimentoPanel() {
        this.alimentoController = new AlimentoController();
        initComponents();
    }
    
    public void setMainFrame(MainFrame mainFrame){
        this.mainFrame = mainFrame;
    }

    private void initComponents() {
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(30, 30, 30, 30));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("🌿Cadastrar Alimento");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 60, 90));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titulo, gbc);

        JLabel subtitulo = new JLabel("Cadastre um novo alimento (volumoso, ração ou suplemento)");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(100, 120, 140));
        gbc.gridy++;
        add(subtitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // Nome
        gbc.gridx = 0;
        JLabel lblNome = new JLabel("Nome do Alimento");
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

        // Tipo
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblTipo = new JLabel("Tipo");
        lblTipo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTipo.setForeground(new Color(40, 60, 80));
        add(lblTipo, gbc);

        gbc.gridx = 1;
        cbTipo = new JComboBox<>(TipoAlimento.values());
        cbTipo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbTipo.setBackground(Color.WHITE);
        cbTipo.setPreferredSize(new Dimension(250, 35));
        cbTipo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        add(cbTipo, gbc);

        // Energia Digestível
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblED = new JLabel("Energia Digestível (Mcal/kg)");
        lblED.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblED.setForeground(new Color(40, 60, 80));
        add(lblED, gbc);

        gbc.gridx = 1;
        txtED = new JTextField();
        txtED.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtED.setPreferredSize(new Dimension(150, 35));
        txtED.setBackground(Color.WHITE);
        txtED.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        add(txtED, gbc);

        // Botão
        gbc.gridy++;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        btnCadastrar = new JButton("Cadastrar Alimento");
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
        
        
        ///////CAMPO FUTURO PARA PREÇO DO PRODUTO
        /*
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel lblPreco = new JLabel("Preço (R$/kg)");
        lblPreco.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPreco.setForeground(new Color(40, 60, 80));
        add(lblPreco, gbc);

        gbc.gridx = 1;
        txtPreco = new JTextField();
        txtPreco.setToolTipText("Digite o preço por quilograma");
        estilizarCampo(txtPreco);
        add(txtPreco, gbc);
        */
        ////////////////
        
        
    }

    private void cadastrar() {
        lblMensagem.setText(" ");
        String nome = txtNome.getText().trim();
        String edStr = txtED.getText().trim();

        if (nome.isEmpty()) {
            exibirMensagem("Informe o nome do alimento.", Color.RED);
            return;
        }
        if (edStr.isEmpty()) {
            exibirMensagem("Informe a Energia Digestível.", Color.RED);
            return;
        }

        double ed;
        try {
            ed = Double.parseDouble(edStr);
        } catch (NumberFormatException e) {
            exibirMensagem("Valor inválido (use números).", Color.RED);
            return;
        }

        if (ed <= 0) {
            exibirMensagem("Energia Digestível deve ser maior que zero.", Color.RED);
            return;
        }

        TipoAlimento tipo = (TipoAlimento) cbTipo.getSelectedItem();
        String resultado = alimentoController.cadastrarAlimento(nome, tipo, ed);

        if (resultado.startsWith("Erro")) {
            exibirMensagem(" " + resultado, Color.RED);
        } else {
            exibirMensagem("✅ " + resultado, new Color(0, 150, 136));
            txtNome.setText("");
            txtED.setText("");
            cbTipo.setSelectedIndex(0);
            
            if(mainFrame != null){
                mainFrame.atualizarDados();
            }
        }
    }

    private void exibirMensagem(String texto, Color cor) {
        lblMensagem.setText(texto);
        lblMensagem.setForeground(cor);
    }
}
