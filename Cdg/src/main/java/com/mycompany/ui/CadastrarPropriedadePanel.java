/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ui;

/**
 *
 * @author daza
 */
import com.mycompany.controller.PropriedadeController;
import com.mycompany.domain.Propriedade;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CadastrarPropriedadePanel extends JPanel {

    private final PropriedadeController propriedadeController;

    private JTextField txtNome;
    private JTextField txtEndereco;
    private JTextField txtTelefone;
    private JTextField txtResponsavel;
    private JButton btnCadastrar;
    private JLabel lblMensagem;
    private MainFrame mainFrame;

    public CadastrarPropriedadePanel() {
        this.propriedadeController = new PropriedadeController();
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

        JLabel titulo = new JLabel("Cadastrar Propriedade / CT");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 60, 90));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titulo, gbc);

        JLabel subtitulo = new JLabel("Informe os dados da propriedade para identificar o relatório");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(100, 120, 140));
        gbc.gridy++;
        add(subtitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        gbc.gridx = 0;
        add(new JLabel("Nome da Propriedade *"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField();
        estilizarCampo(txtNome);
        add(txtNome, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Endereço"), gbc);
        gbc.gridx = 1;
        txtEndereco = new JTextField();
        estilizarCampo(txtEndereco);
        add(txtEndereco, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Telefone"), gbc);
        gbc.gridx = 1;
        txtTelefone = new JTextField();
        estilizarCampo(txtTelefone);
        add(txtTelefone, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Responsável Técnico"), gbc);
        gbc.gridx = 1;
        txtResponsavel = new JTextField();
        estilizarCampo(txtResponsavel);
        add(txtResponsavel, gbc);

        gbc.gridy++;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        btnCadastrar = new JButton("Cadastrar Propriedade");
        estilizarBotao(btnCadastrar);
        btnCadastrar.addActionListener(e -> cadastrar());
        add(btnCadastrar, gbc);

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

    private void carregarDados() {
        Propriedade p = propriedadeController.buscarPropriedadeAtual();
        if (p != null) {
            txtNome.setText(p.getNome() != null ? p.getNome() : "");
            txtEndereco.setText(p.getEndereco() != null ? p.getEndereco() : "");
            txtTelefone.setText(p.getTelefone() != null ? p.getTelefone() : "");
            txtResponsavel.setText(p.getResponsavel() != null ? p.getResponsavel() : "");
        }
    }

    private void cadastrar() {
        lblMensagem.setText("");
        String nome = txtNome.getText().trim();
        if (nome.isEmpty()) {
            exibirMensagem("Informe o nome da propriedade.", Color.RED);
            return;
        }

        String resultado = propriedadeController.cadastrarPropriedade(
                nome,
                txtEndereco.getText().trim(),
                txtTelefone.getText().trim(),
                txtResponsavel.getText().trim()
        );

        if (resultado.startsWith("Erro")) {
            exibirMensagem("" + resultado, Color.RED);
        } else {
            exibirMensagem("✅" + resultado, new Color(0, 150, 136));
            txtNome.setText("");
            txtEndereco.setText("");
            txtTelefone.setText("");
            txtResponsavel.setText("");
            if(mainFrame != null){
                mainFrame.recarregarPropriedades();
            }
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
    
    
    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
}