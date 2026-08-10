package com.mycompany.ui;

import com.mycompany.controller.AvaliacaoController;
import com.mycompany.domain.DiagnosticoNutricional;
import com.mycompany.domain.Equino;
import com.mycompany.domain.Consumo;
import com.mycompany.repository.EquinoRepository;
import com.mycompany.repository.ConsumoRepository;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class avaliarEquinoPanel extends JPanel {

    private final AvaliacaoController avaliacaoController;
    private final EquinoRepository equinoRepository;
    private final ConsumoRepository consumoRepository;

    private JComboBox<String> cbEquinos;
    private JButton btnAvaliar;
    private JTextArea txtResultado;
    private JTextArea txtResumoDieta;
    private JTextArea txtRecomendacao;
    private JTextArea txtAlertas;
    private JLabel lblMensagem;

    public avaliarEquinoPanel() {
        this.avaliacaoController = new AvaliacaoController();
        this.equinoRepository = new EquinoRepository();
        this.consumoRepository = new ConsumoRepository();
        initComponents();
        carregarEquinos();
    }

    private void initComponents() {
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(30, 30, 30, 30));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Avaliar Balanço Energético");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 60, 90));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titulo, gbc);

        JLabel subtitulo = new JLabel("Selecione um equino para calcular o balanço energético");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(100, 120, 140));
        gbc.gridy++;
        add(subtitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        gbc.gridx = 0;
        JLabel lblEquino = new JLabel("Equino");
        lblEquino.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblEquino.setForeground(new Color(40, 60, 80));
        add(lblEquino, gbc);

        gbc.gridx = 1;
        cbEquinos = new JComboBox<>();
        cbEquinos.setToolTipText("Selecione um cavalo cadastrado");
        estilizarCombo(cbEquinos);
        cbEquinos.addActionListener(e -> atualizarResumoDieta());
        add(cbEquinos, gbc);

        //resumo da dieta
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JLabel lblResumo = new JLabel("Dieta atual do equino:");
        lblResumo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblResumo.setForeground(new Color(40, 60, 80));
        add(lblResumo, gbc);

        gbc.gridy++;
        txtResumoDieta = new JTextArea(3, 30);
        txtResumoDieta.setEditable(false);
        txtResumoDieta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtResumoDieta.setBackground(new Color(240, 245, 250));
        txtResumoDieta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        JScrollPane scrollResumo = new JScrollPane(txtResumoDieta);
        scrollResumo.setPreferredSize(new Dimension(500, 60));
        add(scrollResumo, gbc);

        gbc.gridy++;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        btnAvaliar = new JButton("Avaliar");
        estilizarBotao(btnAvaliar);
        btnAvaliar.addActionListener(e -> avaliar());
        add(btnAvaliar, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JPanel panelResultados = new JPanel(new GridLayout(3, 1, 5, 5));
        panelResultados.setBackground(new Color(245, 247, 250));

        //area de números (ED, saldo, etc.)
        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtResultado.setBackground(Color.WHITE);
        txtResultado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panelResultados.add(new JScrollPane(txtResultado));

        //area de recomendação
        txtRecomendacao = new JTextArea();
        txtRecomendacao.setEditable(false);
        txtRecomendacao.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtRecomendacao.setBackground(new Color(255, 248, 230));
        txtRecomendacao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panelResultados.add(new JScrollPane(txtRecomendacao));

        //area de alertas
        txtAlertas = new JTextArea();
        txtAlertas.setEditable(false);
        txtAlertas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtAlertas.setForeground(new Color(180, 40, 40));
        txtAlertas.setBackground(new Color(255, 240, 240));
        txtAlertas.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panelResultados.add(new JScrollPane(txtAlertas));

        add(panelResultados, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        lblMensagem = new JLabel(" ");
        lblMensagem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMensagem.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensagem.setBorder(new EmptyBorder(10, 0, 0, 0));
        add(lblMensagem, gbc);
    }

    public void carregarEquinos() {
        List<Equino> equinos = equinoRepository.listarTodos();
        cbEquinos.removeAllItems();
        for (Equino e : equinos) {
            cbEquinos.addItem(e.getNome());
        }
        if (cbEquinos.getItemCount() > 0) {
            cbEquinos.setSelectedIndex(0);
            atualizarResumoDieta();
        }
    }

    public void atualizarResumoDieta() {
        String nome = (String) cbEquinos.getSelectedItem();
        if (nome != null) {
            Equino e = equinoRepository.buscarPorNome(nome);
            if (e != null) {
                List<Consumo> consumos = consumoRepository.buscarPorEquino(e);
                StringBuilder sb = new StringBuilder();
                if (consumos.isEmpty()) {
                    sb.append("Nenhum consumo registrado para este equino.");
                } else {
                    for (Consumo c : consumos) {
                        sb.append(c.getAlimento().getNome())
                          .append(": ")
                          .append(c.getQuantidadeKgPorDia())
                          .append(" kg/dia\n");
                    }
                }
                txtResumoDieta.setText(sb.toString());
            }
        }
    }

    private void avaliar() {
        lblMensagem.setText("");
        txtResultado.setText("");
        txtRecomendacao.setText("");
        txtAlertas.setText("");
        String nome = (String) cbEquinos.getSelectedItem();

        if (nome == null || nome.isEmpty()) {
            exibirMensagem("Selecione um equino.", Color.RED);
            return;
        }

        DiagnosticoNutricional diag = avaliacaoController.avaliarEquino(nome);

        if (diag == null) {
            exibirMensagem("Equino não encontrado.", Color.RED);
            return;
        }

        // area de resultados numéricos
        StringBuilder sb = new StringBuilder();
        sb.append("Cavalo: ").append(diag.getEquino().getNome()).append("\n");
        sb.append("Peso: ").append(diag.getEquino().getPeso()).append(" kg\n");
        sb.append("Categoria: ").append(diag.getEquino().getCategoria()).append("\n");
        sb.append("ED Exigida: ").append(String.format("%.2f", diag.getEdExigida())).append(" Mcal/dia\n");
        sb.append("ED Fornecida: ").append(String.format("%.2f", diag.getEdFornecida())).append(" Mcal/dia\n");
        sb.append("Saldo: ").append(String.format("%.2f", diag.getSaldo())).append(" Mcal/dia\n");
        sb.append("Classificação: ").append(diag.getClassificacao()).append("\n");
        sb.append("Custo Diário: R$ ").append(String.format("%.2f", diag.getCustoDiario()));
        sb.append("\nCusto Mensal: R$ ").append(String.format("%.2f", diag.getCustoMensal()));
        txtResultado.setText(sb.toString());

        //area de recomendação
        txtRecomendacao.setText(diag.getRecomendacao());

        // area de alertas
        StringBuilder sbAlertas = new StringBuilder();
        if (diag.getAlertas().isEmpty()) {
            sbAlertas.append("Nenhum alerta de segurança emitido. Dieta adequada.");
        } else {
            for (String alerta : diag.getAlertas()) {
                sbAlertas.append("• ").append(alerta).append("\n");
            }
        }
        txtAlertas.setText(sbAlertas.toString());

       
        if (diag.getClassificacao().contains("DÉFICIT")) {
            txtResultado.setForeground(Color.RED);
        } else if (diag.getClassificacao().contains("EXCESSO")) {
            txtResultado.setForeground(new Color(200, 100, 0));
        } else {
            txtResultado.setForeground(new Color(0, 150, 136));
        }

        exibirMensagem("Avaliação concluída!", new Color(0, 150, 136));
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
        new Timer(5000, e -> lblMensagem.setText(" ")).start();
    }
}