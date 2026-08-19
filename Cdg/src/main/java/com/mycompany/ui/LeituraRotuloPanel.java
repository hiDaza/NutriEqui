package com.mycompany.ui;

import com.mycompany.controller.AlimentoController;
import com.mycompany.controller.LeituraRotuloController;
import com.mycompany.domain.TipoAlimento;
import com.mycompany.domain.dto.AlimentoDTO;
import com.mycompany.service.LeituraRotuloService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class LeituraRotuloPanel extends JPanel {

    private final LeituraRotuloController controller;
    private JTextArea txtResultado;
    private JLabel lblCaminhoArquivo;
    private JButton btnSelecionarArquivo;
    private JButton btnProcessar;
    private JButton btnCadastrar;
    private File arquivoSelecionado;
    private MainFrame mainFrame;

    public LeituraRotuloPanel() {
        this.controller = new LeituraRotuloController();
        initComponents();
    }

    public void setMainFrame(MainFrame mainFrame) {
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

        // Título
        JLabel titulo = new JLabel("Leitura de Rótulos (PDF)");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 60, 90));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titulo, gbc);

        JLabel subtitulo = new JLabel("Envie um PDF com texto pesquisável para extrair informações do rótulo.");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(100, 120, 140));
        gbc.gridy++;
        add(subtitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // Botão selecionar arquivo
        gbc.gridx = 0;
        btnSelecionarArquivo = new JButton("Selecionar Rótulo (PDF)");
        btnSelecionarArquivo.addActionListener(e -> selecionarArquivo());
        add(btnSelecionarArquivo, gbc);

        // Label do caminho
        gbc.gridx = 1;
        lblCaminhoArquivo = new JLabel("Nenhum arquivo selecionado");
        lblCaminhoArquivo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblCaminhoArquivo.setForeground(Color.GRAY);
        add(lblCaminhoArquivo, gbc);

        // Painel de botões (Extrair + Cadastrar)
        gbc.gridy++;
        gbc.gridx = 1;
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        panelBotoes.setOpaque(false);

        btnProcessar = new JButton("Extrair Informações");
        btnProcessar.setEnabled(false);
        btnProcessar.addActionListener(e -> processarArquivo());
        panelBotoes.add(btnProcessar);

        btnCadastrar = new JButton("Cadastrar Alimento");
        btnCadastrar.setEnabled(false);
        btnCadastrar.addActionListener(e -> cadastrarAlimentoDoRotulo());
        panelBotoes.add(btnCadastrar);

        add(panelBotoes, gbc);

        // Área de resultado
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        txtResultado = new JTextArea();
        txtResultado.setEditable(true);
        txtResultado.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtResultado.setBackground(Color.WHITE);
        txtResultado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        JScrollPane scroll = new JScrollPane(txtResultado);
        scroll.setPreferredSize(new Dimension(500, 300));
        add(scroll, gbc);

        // Observação
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        JLabel obs = new JLabel(
                "Dica: Para imagens, converta para PDF com OCR usando Google Drive, Adobe Scan ou ferramentas similares. "
                + "O sistema extrairá o texto do PDF gerado."
        );
        obs.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        obs.setForeground(Color.GRAY);
        add(obs, gbc);
    }

    private void selecionarArquivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Selecione o rótulo do alimento (PDF)");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Arquivos PDF", "pdf"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            arquivoSelecionado = fileChooser.getSelectedFile();
            lblCaminhoArquivo.setText(arquivoSelecionado.getAbsolutePath());
            btnProcessar.setEnabled(true);
            txtResultado.setText("Arquivo selecionado: " + arquivoSelecionado.getName()
                    + "\n\nClique em 'Extrair Informações' para processar.");
        }
    }

    private void processarArquivo() {
        if (arquivoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um arquivo primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            txtResultado.setText("Processando... Aguarde.");
            LeituraRotuloService.ResultadoLeituraRotulo resultado =
                    controller.processarArquivo(arquivoSelecionado, arquivoSelecionado.getName());

            StringBuilder sb = new StringBuilder();
            sb.append("===== INFORMACOES EXTRAIDAS DO ROTULO =====\n");
            sb.append("Arquivo: ").append(arquivoSelecionado.getName()).append("\n");
            sb.append("Salvo em: ").append(resultado.getCaminhoArquivo()).append("\n");
            sb.append("------------------------------------------------\n");
            sb.append("TEXTO EXTRAIDO:\n");
            sb.append(resultado.getTextoExtraido());
            sb.append("\n------------------------------------------------\n");
            sb.append("DICA: Copie os valores para o cadastro do alimento.\n");

            txtResultado.setText(sb.toString());
            btnCadastrar.setEnabled(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao processar o arquivo: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            txtResultado.setText("Erro: " + e.getMessage());
        }
    }

    private void cadastrarAlimentoDoRotulo() {
        try {
            AlimentoDTO dto = controller.processarArquivoParaAlimento(arquivoSelecionado, arquivoSelecionado.getName());
            AlimentoController alimentoController = new AlimentoController();
            String resultado = "";

            if (dto.getTipo() == TipoAlimento.RACAO) {
                resultado = alimentoController.cadastrarRacao(
                        dto.getNome(), dto.getFabricante(), dto.getCategoriaRacao(),
                        dto.getUmidade(), dto.getProteinaBruta(), dto.getExtratoEtereo(),
                        dto.getFibraBruta(), dto.getFda(), dto.getFdn(),
                        dto.getMateriaMineralRacao(), dto.getCalcioRacao(), dto.getFosforoRacao(),
                        dto.getSodioRacao(), dto.getEdDec(), dto.getPrecoPorKg()
                );
            } else if (dto.getTipo() == TipoAlimento.VOLUMOSO) {
                resultado = alimentoController.cadastrarVolumoso(
                        dto.getTipoVolumoso(), dto.getCategoriaVolumoso(),
                        dto.getMateriaSeca(), dto.getProteinaVolumoso(),
                        dto.getFdnVolumoso(), dto.getFdaVolumoso(),
                        dto.getEdVolumoso(), dto.getRegiao(), dto.getPrecoPorKg()
                );
            } else if (dto.getTipo() == TipoAlimento.SUPLEMENTO) {
                resultado = alimentoController.cadastrarSuplemento(
                        dto.getNomeComercialSuplemento(), dto.getFabricanteSuplemento(),
                        dto.getCategoriaSuplemento(), dto.getUnidadeRotulo(),
                        dto.getDoseRecomendada(), dto.getDoseUsada(),
                        dto.getEnergiaSuplemento(), dto.getProteinaSuplemento(),
                        dto.getGordura(), dto.getCalcioSuplemento(), dto.getFosforoSuplemento(),
                        dto.getSodioSuplemento(), dto.getPotassio(), dto.getMagnesio(),
                        dto.getSelenio(), dto.getVitaminaE(), dto.getBiotina(),
                        dto.getCalculoEnergetico(), dto.getPrecoPorKg()
                );
            }

            JOptionPane.showMessageDialog(this, resultado, "Cadastro automático", JOptionPane.INFORMATION_MESSAGE); //remover resultad com referencia a banco de dados
            if (mainFrame != null) {
                mainFrame.atualizarDados();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}