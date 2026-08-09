package com.mycompany.ui;

import com.mycompany.controller.ConsumoController;
import com.mycompany.domain.Alimento;
import com.mycompany.domain.Equino;
import com.mycompany.domain.TipoAlimento;
import com.mycompany.repository.AlimentoRepository;
import com.mycompany.repository.EquinoRepository;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class RegistrarSuplementoPanel extends JPanel {

    private final ConsumoController consumoController;
    private final EquinoRepository equinoRepository;
    private final AlimentoRepository alimentoRepository;

    private JComboBox<String> cbEquinos;
    private JComboBox<String> cbSuplementos;
    private JTextField txtDose;
    private JRadioButton rbSim;
    private JRadioButton rbNao;
    private JButton btnRegistrar;
    private JLabel lblMensagem;

    public RegistrarSuplementoPanel() {
        this.consumoController = new ConsumoController();
        this.equinoRepository = new EquinoRepository();
        this.alimentoRepository = new AlimentoRepository();
        initComponents();
        carregarDados();
    }

    @Override
    public void addNotify() {
        super.addNotify();
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

        JLabel titulo = new JLabel("Registrar Suplemento na Dieta");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 60, 90));
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titulo, gbc);

        JLabel subtitulo = new JLabel("Selecione o equino, o suplemento, a dose diária e se ele entra no cálculo energético.");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(100, 120, 140));
        gbc.gridy++;
        add(subtitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        gbc.gridx = 0;
        add(new JLabel("Equino"), gbc);
        gbc.gridx = 1;
        cbEquinos = new JComboBox<>();
        estilizarCombo(cbEquinos);
        add(cbEquinos, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Suplemento"), gbc);
        gbc.gridx = 1;
        cbSuplementos = new JComboBox<>();
        estilizarCombo(cbSuplementos);
        add(cbSuplementos, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Dose diária"), gbc);
        gbc.gridx = 1;
        txtDose = new JTextField();
        estilizarCampo(txtDose);
        add(txtDose, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Entra no cálculo energético?"), gbc);
        gbc.gridx = 1;
        JPanel painelEnergia = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        painelEnergia.setOpaque(false);
        ButtonGroup group = new ButtonGroup();
        rbSim = new JRadioButton("Sim", true);
        rbNao = new JRadioButton("Não");
        group.add(rbSim);
        group.add(rbNao);
        painelEnergia.add(rbSim);
        painelEnergia.add(rbNao);
        add(painelEnergia, gbc);

        gbc.gridy++;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        btnRegistrar = new JButton("Registrar Suplemento");
        estilizarBotao(btnRegistrar);
        btnRegistrar.addActionListener(e -> registrarSuplemento());
        add(btnRegistrar, gbc);

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
        if (cbEquinos == null || cbSuplementos == null) {
            return;
        }

        List<Equino> equinos = equinoRepository.listarTodos();
        cbEquinos.removeAllItems();
        for (Equino equino : equinos) {
            cbEquinos.addItem(equino.getNome());
        }

        List<Alimento> alimentos = alimentoRepository.listarPorTipo(TipoAlimento.SUPLEMENTO);
        cbSuplementos.removeAllItems();
        for (Alimento alimento : alimentos) {
            cbSuplementos.addItem(alimento.getNome());
        }

        if (cbEquinos.getItemCount() > 0) {
            cbEquinos.setSelectedIndex(0);
        }
        if (cbSuplementos.getItemCount() > 0) {
            cbSuplementos.setSelectedIndex(0);
        }
    }

    private void registrarSuplemento() {
        lblMensagem.setText("");
        String nomeEquino = (String) cbEquinos.getSelectedItem();
        String nomeSuplemento = (String) cbSuplementos.getSelectedItem();
        String doseStr = txtDose.getText().trim();

        if (nomeEquino == null || nomeEquino.isEmpty()) {
            exibirMensagem("Selecione um equino.", Color.RED);
            return;
        }
        if (nomeSuplemento == null || nomeSuplemento.isEmpty()) {
            exibirMensagem("Selecione um suplemento cadastrado.", Color.RED);
            return;
        }
        if (doseStr.isEmpty()) {
            exibirMensagem("Informe a dose diária.", Color.RED);
            txtDose.requestFocus();
            return;
        }

        double dose;
        try {
            dose = Double.parseDouble(doseStr.replace(",", "."));
        } catch (NumberFormatException ex) {
            exibirMensagem("Dose inválida! Use números.", Color.RED);
            txtDose.requestFocus();
            return;
        }

        if (dose <= 0) {
            exibirMensagem("Dose deve ser maior que zero.", Color.RED);
            txtDose.requestFocus();
            return;
        }

        boolean incluiNoCalculoEnergetico = rbSim.isSelected();
        String resultado = consumoController.registrarSuplemento(nomeEquino, nomeSuplemento, dose, incluiNoCalculoEnergetico);

        if (resultado.startsWith("Erro")) {
            exibirMensagem(" " + resultado, Color.RED);
        } else {
            exibirMensagem("✅ " + resultado, new Color(0, 150, 136));
            txtDose.setText("");
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
        new Timer(5000, e -> lblMensagem.setText(" ")).start();
    }
}
