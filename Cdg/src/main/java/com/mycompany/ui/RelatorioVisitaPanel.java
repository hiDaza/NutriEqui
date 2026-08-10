package com.mycompany.ui;

import com.mycompany.controller.RelatorioController;
import com.mycompany.domain.RelatorioVisita;
import java.awt.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import javax.swing.*;

public class RelatorioVisitaPanel extends JPanel {

    private final RelatorioController relatorioController;
    private JTextArea txtRelatorio;
    private JButton btnGerar;
    //private JButton btnExportarPDF;
    private JButton btnSalvarTXT;

    public RelatorioVisitaPanel() {
        this.relatorioController = new RelatorioController();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        btnGerar = new JButton("Gerar Relatório de Visita Técnica diário");
        btnGerar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGerar.setBackground(new Color(0, 122, 255));
        btnGerar.setForeground(Color.WHITE);
        btnGerar.addActionListener(e -> carregarRelatorio());

     // por enquanto nao temos a funcionalidade   btnExportarPDF = new JButton("📄 Exportar PDF / Imprimir");
     //   btnExportarPDF.setFont(new Font("Segoe UI", Font.BOLD, 13));
    //    btnExportarPDF.setBackground(new Color(40, 167, 69));
    //    btnExportarPDF.setForeground(Color.WHITE);
    //    btnExportarPDF.addActionListener(e -> exportarPDF());

        btnSalvarTXT = new JButton("💾 Salvar em .TXT");
        btnSalvarTXT.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnSalvarTXT.addActionListener(e -> exportarTXT());

        topPanel.add(btnGerar);
      //  topPanel.add(btnExportarPDF);
        topPanel.add(btnSalvarTXT);

        txtRelatorio = new JTextArea();
        txtRelatorio.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtRelatorio.setEditable(false);
        txtRelatorio.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(txtRelatorio);

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    public void carregarRelatorio() {
        RelatorioVisita relatorio = relatorioController.gerarRelatorioGeral();
        if (relatorio == null) {
            txtRelatorio.setText("Nenhuma avaliação ou equino encontrado no banco para gerar o relatório.");
        } else {
            txtRelatorio.setText(relatorio.gerarTextoFormatado());
            txtRelatorio.setCaretPosition(0);
        }
    }

    private void exportarPDF() {
        if (txtRelatorio.getText().trim().isEmpty() || txtRelatorio.getText().startsWith("LOTE⚠️")) {
            JOptionPane.showMessageDialog(this, "Gere o relatório com dados válidos antes de exportar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            MessageFormat header = new MessageFormat("NutriEqui Campo - Relatório de Visita Técnica");
            MessageFormat footer = new MessageFormat("Página {0}");
            
            boolean concluido = txtRelatorio.print(header, footer, true, null, null, true);
            
            if (concluido) {
                JOptionPane.showMessageDialog(this, "Relatório exportado/impresso com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar PDF/Impressão: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportarTXT() {
        if (txtRelatorio.getText().trim().isEmpty() || txtRelatorio.getText().startsWith("⚠️")) {
            JOptionPane.showMessageDialog(this, "Gere o relatório com dados válidos antes de salvar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("Relatorio_Visita_NutriEqui.txt"));
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(fileToSave)) {
                writer.write(txtRelatorio.getText());
                JOptionPane.showMessageDialog(this, "Relatório salvo em: " + fileToSave.getAbsolutePath(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}