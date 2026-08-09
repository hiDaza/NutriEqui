package com.mycompany.ui;

import com.mycompany.controller.AvaliacaoController;
import com.mycompany.domain.AvaliacaoHistorico;
import com.mycompany.domain.Equino;
import com.mycompany.repository.EquinoRepository;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class HistoricoAvaliacoesPanel extends JPanel {

    private final AvaliacaoController avaliacaoController;
    private final EquinoRepository equinoRepository;
    private final DateTimeFormatter dateFormatter;

    private JComboBox<String> cbEquinos;
    private JTextArea txtHistorico;
    private JLabel lblMensagem;

    public HistoricoAvaliacoesPanel() {
        this.avaliacaoController = new AvaliacaoController();
        this.equinoRepository = new EquinoRepository();
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
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

        JLabel titulo = new JLabel("Histórico de Avaliações");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 60, 90));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titulo, gbc);

        JLabel subtitulo = new JLabel("Selecione o equino para visualizar avaliações anteriores");
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
        estilizarCombo(cbEquinos);
        add(cbEquinos, gbc);

        gbc.gridy++;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JButton btnBuscar = new JButton("Buscar Histórico");
        estilizarBotao(btnBuscar);
        btnBuscar.addActionListener(e -> buscarHistorico());
        add(btnBuscar, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        txtHistorico = new JTextArea();
        txtHistorico.setEditable(false);
        txtHistorico.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtHistorico.setBackground(Color.WHITE);
        txtHistorico.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        JScrollPane scrollHistorico = new JScrollPane(txtHistorico);
        scrollHistorico.setPreferredSize(new Dimension(520, 240));
        add(scrollHistorico, gbc);

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
        for (Equino equino : equinos) {
            cbEquinos.addItem(equino.getNome());
        }
    }

    private void buscarHistorico() {
        lblMensagem.setText("");
        txtHistorico.setText("");
        String nomeEquino = (String) cbEquinos.getSelectedItem();

        if (nomeEquino == null || nomeEquino.isEmpty()) {
            exibirMensagem("Selecione um equino.", Color.RED);
            return;
        }

        List<AvaliacaoHistorico> historico = avaliacaoController.buscarHistoricoPorEquino(nomeEquino);
        if (historico.isEmpty()) {
            txtHistorico.setText("Nenhuma avaliação registrada para este equino.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (AvaliacaoHistorico item : historico) {
            sb.append("Data: ").append(item.getDataAvaliacao().format(dateFormatter)).append("\n");
            sb.append("Peso registrado: ").append(String.format("%.2f", item.getPesoRegistrado())).append(" kg\n");
            sb.append("Escore corporal: ").append(item.getEscoreCorporal()).append("\n");
            sb.append("Categoria na época: ").append(item.getCategoriaNaEpoca()).append("\n");
            sb.append("Dieta consumida: ").append(item.getDietaConsumida()).append("\n");
            sb.append("Saldo energético: ").append(String.format("%.2f", item.getSaldoEnergetico())).append(" Mcal/dia\n");
            sb.append("Conduta/Recomendação: ").append(item.getRecomendacao()).append("\n");
            sb.append("------------------------------------------------------------\n");
        }

        txtHistorico.setText(sb.toString());
        exibirMensagem("Histórico carregado com sucesso!", new Color(0, 150, 136));
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
    }

    private void exibirMensagem(String texto, Color cor) {
        lblMensagem.setText(texto);
        lblMensagem.setForeground(cor);
    }
}
