package com.mycompany.ui;

import com.mycompany.controller.PropriedadeController;
import com.mycompany.controller.RelatorioController;
import com.mycompany.domain.Propriedade;
import com.mycompany.domain.RelatorioVisita;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.*;

public class RelatorioVisitaPanel extends JPanel {

    private final RelatorioController relatorioController;
    private final PropriedadeController propriedadeController;

    private JComboBox<String> cbPropriedades;
    private JTextArea txtRelatorio;
    private JButton btnGerar;
    private JButton btnSalvarTXT;

    public RelatorioVisitaPanel() {
        this.relatorioController = new RelatorioController();
        this.propriedadeController = new PropriedadeController();
        initComponents();
        carregarPropriedades();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Painel superior com combo e botões
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        // Combo de propriedades
        JLabel lblPropriedade = new JLabel("Propriedade:");
        lblPropriedade.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        topPanel.add(lblPropriedade);

        cbPropriedades = new JComboBox<>();
        cbPropriedades.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbPropriedades.setPreferredSize(new Dimension(200, 30));
        topPanel.add(cbPropriedades);

        // Botão Gerar
        btnGerar = new JButton("Gerar Relatório de Visita Técnica");
        btnGerar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGerar.setBackground(new Color(0, 122, 255));
        btnGerar.setForeground(Color.WHITE);
        btnGerar.setFocusPainted(false);
        btnGerar.addActionListener(e -> carregarRelatorio());
        topPanel.add(btnGerar);

        // Botão Salvar TXT
        btnSalvarTXT = new JButton("Salvar em .TXT");
        btnSalvarTXT.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnSalvarTXT.addActionListener(e -> exportarTXT());
        topPanel.add(btnSalvarTXT);

        add(topPanel, BorderLayout.NORTH);

        // Área de texto do relatório
        txtRelatorio = new JTextArea();
        txtRelatorio.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtRelatorio.setEditable(false);
        txtRelatorio.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(txtRelatorio);
        scroll.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        add(scroll, BorderLayout.CENTER);
    }

    private void carregarPropriedades() {
        cbPropriedades.removeAllItems();
        cbPropriedades.addItem("Todas");

        List<Propriedade> propriedades = propriedadeController.listarPropriedades();
        if (propriedades != null) {
            for (Propriedade p : propriedades) {
                if (p.getNome() != null && !p.getNome().trim().isEmpty()) {
                    cbPropriedades.addItem(p.getNome().trim());
                }
            }
        }

        // Se houver apenas "Todas", desabilita o combo (opcional)
        if (cbPropriedades.getItemCount() == 1) {
            cbPropriedades.setEnabled(false);
        } else {
            cbPropriedades.setEnabled(true);
            cbPropriedades.setSelectedIndex(0);
        }
    }

    public void carregarRelatorio() {
        String selected = (String) cbPropriedades.getSelectedItem();
        String nomePropriedade = (selected == null || selected.equals("Todas")) ? null : selected;

        RelatorioVisita relatorio;
        if (nomePropriedade == null) {
            relatorio = relatorioController.gerarRelatorioGeral();
        } else {
            relatorio = relatorioController.gerarRelatorioPorPropriedade(nomePropriedade);
        }

        if (relatorio == null) {
            txtRelatorio.setText("Nenhuma avaliação ou equino encontrado para a propriedade selecionada.");
        } else {
            txtRelatorio.setText(relatorio.gerarTextoFormatado());
            txtRelatorio.setCaretPosition(0);
        }
    }

    private void exportarTXT() {
        String texto = txtRelatorio.getText().trim();
        if (texto.isEmpty() || texto.startsWith("Nenhuma avaliação")) {
            JOptionPane.showMessageDialog(this,
                "Gere o relatório com dados válidos antes de salvar!",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("Relatorio_Visita_NutriEqui.txt"));
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(fileToSave)) {
                writer.write(texto);
                JOptionPane.showMessageDialog(this,
                    "Relatório salvo em: " + fileToSave.getAbsolutePath(),
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                    "Erro ao salvar arquivo: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void recarregarPropriedades() {
        carregarPropriedades();
    }
}