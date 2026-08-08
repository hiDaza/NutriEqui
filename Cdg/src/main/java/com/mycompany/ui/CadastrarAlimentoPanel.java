//daza
//adesao do campo de preço a interface

package com.mycompany.ui;

import com.mycompany.controller.AlimentoController;
import com.mycompany.domain.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class CadastrarAlimentoPanel extends JPanel {

    private final AlimentoController alimentoController;
    private MainFrame mainFrame;
    private TipoAlimento tipoSelecionado = TipoAlimento.RACAO;

    private JComboBox<TipoAlimento> cbTipo;
    private JLabel lblMensagem;
    private JPanel panelCampos;
    private JTextField txtPreco;

    // Mapa para armazenar campos dinâmicos
    private Map<String, JComponent> camposDinamicos = new HashMap<>();

    public CadastrarAlimentoPanel() {
        this.alimentoController = new AlimentoController();
        initComponents();
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    private void initComponents() {
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(30, 30, 30, 30));
        setLayout(new BorderLayout(10, 10));

        // Painel de cabeçalho
        JPanel panelCabecalho = criarCabecalho();
        add(panelCabecalho, BorderLayout.NORTH);

        // Painel de seleção de tipo
        JPanel panelTipo = criarPanelTipo();
        add(panelTipo, BorderLayout.PAGE_START);

        // Painel de campos dinâmicos
        panelCampos = new JPanel();
        panelCampos.setBackground(new Color(245, 247, 250));
        panelCampos.setLayout(new GridBagLayout());
        atualizarCampos();

        JScrollPane scrollPane = new JScrollPane(panelCampos);
        scrollPane.setBackground(new Color(245, 247, 250));
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // Painel de rodapé (com preço e botão)
        JPanel panelRodape = criarPanelRodape();
        add(panelRodape, BorderLayout.SOUTH);
    }

    private JPanel criarCabecalho() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 247, 250));
        panel.setLayout(new BorderLayout());

        JLabel titulo = new JLabel("🌿 Cadastrar Alimento");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 60, 90));
        panel.add(titulo, BorderLayout.WEST);

        JLabel subtitulo = new JLabel("Cadastre um novo alimento (ração, volumoso ou suplemento)");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(100, 120, 140));
        panel.add(subtitulo, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel criarPanelTipo() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 247, 250));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JLabel lbl = new JLabel("Tipo de Alimento:");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(lbl);

        cbTipo = new JComboBox<>(TipoAlimento.values());
        cbTipo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbTipo.setBackground(Color.WHITE);
        cbTipo.setPreferredSize(new Dimension(200, 35));
        cbTipo.addActionListener(e -> {
            tipoSelecionado = (TipoAlimento) cbTipo.getSelectedItem();
            atualizarCampos();
        });
        panel.add(cbTipo);

        return panel;
    }

    private JPanel criarPanelRodape() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 247, 250));
        panel.setLayout(new BorderLayout());

        // Painel superior: Preço + Botão 
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelSuperior.setBackground(new Color(245, 247, 250));

        // Campo de Preço
        JLabel lblPreco = new JLabel("Preço (R$/kg) *");
        lblPreco.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPreco.setForeground(new Color(40, 60, 80));
        panelSuperior.add(lblPreco);

        txtPreco = new JTextField();
        txtPreco.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPreco.setPreferredSize(new Dimension(120, 35));
        txtPreco.setBackground(Color.WHITE);
        txtPreco.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panelSuperior.add(txtPreco);

        // Botão Cadastrar
        JButton btnCadastrar = new JButton("Cadastrar Alimento");
        btnCadastrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCadastrar.setBackground(new Color(0, 150, 136));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCadastrar.addActionListener(e -> cadastrar());
        panelSuperior.add(btnCadastrar);

        panel.add(panelSuperior, BorderLayout.NORTH);

        // Mensagem de feedback
        lblMensagem = new JLabel(" ");
        lblMensagem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMensagem.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensagem.setBorder(new EmptyBorder(10, 0, 0, 0));
        panel.add(lblMensagem, BorderLayout.CENTER);

        return panel;
    }
    private void atualizarCampos() {
        panelCampos.removeAll();
        camposDinamicos.clear();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        if (tipoSelecionado == TipoAlimento.RACAO) {
            adicionarCamposRacao(gbc);
        } else if (tipoSelecionado == TipoAlimento.VOLUMOSO) {
            adicionarCamposVolumoso(gbc);
        } else if (tipoSelecionado == TipoAlimento.SUPLEMENTO) {
            adicionarCamposSuplemento(gbc);
        }

        panelCampos.revalidate();
        panelCampos.repaint();
    }

    private void adicionarCamposRacao(GridBagConstraints gbc) {
        adicionarCampoTexto(gbc, "nomeRacao", "Nome Comercial", true);
        adicionarCampoTexto(gbc, "fabricanteRacao", "Fabricante", true);
        adicionarCampoCombo(gbc, "categoriaRacao", "Categoria", CategoriaRacao.values(), true);

        adicionarCampoNumerico(gbc, "umidade", "Umidade (%)", true);
        adicionarCampoNumerico(gbc, "proteinaBruta", "Proteína Bruta (%)", true);
        adicionarCampoNumerico(gbc, "extratoEtereo", "Extrato Etéreo (%)", true);
        adicionarCampoNumerico(gbc, "fibraBruta", "Fibra Bruta (%)", true);
        adicionarCampoNumerico(gbc, "fda", "FDA (%)", true);
        adicionarCampoNumerico(gbc, "fdn", "FDN (%)", true);
        adicionarCampoNumerico(gbc, "materiaMineralRacao", "Matéria Mineral (%)", true);
        adicionarCampoNumerico(gbc, "calcioRacao", "Cálcio (% ou g/kg)", true);
        adicionarCampoNumerico(gbc, "fosforoRacao", "Fósforo (% ou g/kg)", true);
        adicionarCampoNumerico(gbc, "sodioRacao", "Sódio (% ou g/kg)", true);
        adicionarCampoNumerico(gbc, "edDec", "ED Declarada (Mcal/kg) - Opcional", false);
    }

    private void adicionarCamposVolumoso(GridBagConstraints gbc) {
        adicionarCampoCombo(gbc, "tipoVolumoso", "Tipo", TipoVolumoso.values(), true);
        adicionarCampoCombo(gbc, "categoriaVolumoso", "Categoria", CategoriaVolumoso.values(), true);

        adicionarCampoNumerico(gbc, "materiaSeca", "Matéria Seca (%)", true);
        adicionarCampoNumerico(gbc, "proteinaVolumoso", "Proteína Bruta (%)", true);
        adicionarCampoNumerico(gbc, "fdnVolumoso", "FDN (%)", true);
        adicionarCampoNumerico(gbc, "fdaVolumoso", "FDA (%)", true);
        adicionarCampoNumerico(gbc, "edVolumoso", "Energia Digestível (Mcal/kg)", true);
        adicionarCampoTexto(gbc, "regiao", "Região", false);
    }

    private void adicionarCamposSuplemento(GridBagConstraints gbc) {
        adicionarCampoTexto(gbc, "nomeSuplemento", "Nome Comercial", true);
        adicionarCampoTexto(gbc, "fabricanteSuplemento", "Fabricante", true);
        adicionarCampoTexto(gbc, "categoriaSuplemento", "Categoria", true);

        adicionarCampoNumerico(gbc, "doseRecomendada", "Dose Recomendada (g/dia, mL/dia, scoop)", true);
        adicionarCampoNumerico(gbc, "doseUsada", "Dose Usada", true);
        adicionarCampoCombo(gbc, "unidadeRotulo", "Unidade do Rótulo", UnidadeSuplemento.values(), true);

        // Campos opcionais
        adicionarSeparador(gbc, "--- Nutrientes (Opcionais) ---");
        adicionarCampoNumerico(gbc, "energiaSuplemento", "Energia (kcal)", false);
        adicionarCampoNumerico(gbc, "proteinaSuplemento", "Proteína (%)", false);
        adicionarCampoNumerico(gbc, "gordura", "Gordura (%)", false);
        adicionarCampoNumerico(gbc, "calcioSuplemento", "Cálcio (mg)", false);
        adicionarCampoNumerico(gbc, "fosforoSuplemento", "Fósforo (mg)", false);
        adicionarCampoNumerico(gbc, "sodioSuplemento", "Sódio (mg)", false);
        adicionarCampoNumerico(gbc, "potassio", "Potássio (mg)", false);
        adicionarCampoNumerico(gbc, "magnesio", "Magnésio (mg)", false);
        adicionarCampoNumerico(gbc, "selenio", "Selênio (µg)", false);
        adicionarCampoNumerico(gbc, "vitaminaE", "Vitamina E (UI)", false);
        adicionarCampoNumerico(gbc, "biotina", "Biotina (µg)", false);

        adicionarCampoCombo(gbc, "calculoEnergetico", "Entra no Cálculo Energético", CalculoEnergetico.values(), true);
    }

    private void adicionarCampoTexto(GridBagConstraints gbc, String chave, String label, boolean obrigatorio) {
        gbc.gridx = 0;
        JLabel lbl = new JLabel(label + (obrigatorio ? " *" : ""));
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(new Color(40, 60, 80));
        panelCampos.add(lbl, gbc);

        gbc.gridx = 1;
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setPreferredSize(new Dimension(250, 35));
        txt.setBackground(Color.WHITE);
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panelCampos.add(txt, gbc);
        camposDinamicos.put(chave, txt);

        gbc.gridy++;
    }

    private void adicionarCampoNumerico(GridBagConstraints gbc, String chave, String label, boolean obrigatorio) {
        gbc.gridx = 0;
        JLabel lbl = new JLabel(label + (obrigatorio ? " *" : ""));
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(new Color(40, 60, 80));
        panelCampos.add(lbl, gbc);

        gbc.gridx = 1;
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setPreferredSize(new Dimension(150, 35));
        txt.setBackground(Color.WHITE);
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panelCampos.add(txt, gbc);
        camposDinamicos.put(chave, txt);

        gbc.gridy++;
    }

    private void adicionarCampoCombo(GridBagConstraints gbc, String chave, String label, Object[] opcoes, boolean obrigatorio) {
        gbc.gridx = 0;
        JLabel lbl = new JLabel(label + (obrigatorio ? " *" : ""));
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(new Color(40, 60, 80));
        panelCampos.add(lbl, gbc);

        gbc.gridx = 1;
        JComboBox<?> combo = new JComboBox<>(opcoes);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(Color.WHITE);
        combo.setPreferredSize(new Dimension(250, 35));
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panelCampos.add(combo, gbc);
        camposDinamicos.put(chave, combo);

        gbc.gridy++;
    }

    private void adicionarSeparador(GridBagConstraints gbc, String texto) {
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(100, 120, 140));
        lbl.setBorder(new EmptyBorder(15, 0, 5, 0));
        panelCampos.add(lbl, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;
    }

    private void cadastrar() {
        lblMensagem.setText(" ");
        String resultado = "";

        try {
            if (tipoSelecionado == TipoAlimento.RACAO) {
                resultado = cadastrarRacao();
            } else if (tipoSelecionado == TipoAlimento.VOLUMOSO) {
                resultado = cadastrarVolumoso();
            } else if (tipoSelecionado == TipoAlimento.SUPLEMENTO) {
                resultado = cadastrarSuplemento();
            }

            if (resultado.startsWith("Erro")) {
                exibirMensagem("❌ " + resultado, Color.RED);
            } else {
                exibirMensagem("✅Alimento Cadastrado com Sucesso", new Color(0, 150, 136)); // + resultado  // comentado, pois o usuario nao precisa ter ciência do ID do alimento.  
                limparCampos();                                                            // depoi corrigir para apresentar o tipo do alimento exato cadastrado.
                if (mainFrame != null) {
                    mainFrame.atualizarDados();
                }
            }
        } catch (Exception e) {
            exibirMensagem("❌ Erro: " + e.getMessage(), Color.RED);
        }
    }

    private String cadastrarRacao() {
        String nome = getCampoTexto("nomeRacao");
        String fabricante = getCampoTexto("fabricanteRacao");
        CategoriaRacao categoria = (CategoriaRacao) getCampoCombo("categoriaRacao");

        if (nome.isEmpty()) return "Erro: Informe o nome comercial da ração.";
        if (fabricante.isEmpty()) return "Erro: Informe o fabricante.";
        
        //preco 
        String precoStr = txtPreco.getText().trim();
        if (precoStr.isEmpty()) return "Erro: Informe o preço por kg.";
        double preco;
        try {
            preco = Double.parseDouble(precoStr.replace(",", "."));
        } catch (NumberFormatException e) {
            return "Erro: Preço inválido. Use números (ex: 2.50).";
        }
        if (preco <= 0) return "Erro: Preço deve ser maior que zero.";
        
        
        try {
            double umidade = getCampoNumerico("umidade");
            double proteinaBruta = getCampoNumerico("proteinaBruta");
            double extratoEtereo = getCampoNumerico("extratoEtereo");
            double fibraBruta = getCampoNumerico("fibraBruta");
            double fda = getCampoNumerico("fda");
            double fdn = getCampoNumerico("fdn");
            double materiaMineralRacao = getCampoNumerico("materiaMineralRacao");
            double calcioRacao = getCampoNumerico("calcioRacao");
            double fosforoRacao = getCampoNumerico("fosforoRacao");
            double sodioRacao = getCampoNumerico("sodioRacao");

            Double edDec = getCampoNumericoOpcional("edDec");

            return alimentoController.cadastrarRacao(nome, fabricante, categoria,
                    umidade, proteinaBruta, extratoEtereo, fibraBruta, fda, fdn,
                    materiaMineralRacao, calcioRacao, fosforoRacao, sodioRacao, edDec,preco);
        } catch (NumberFormatException e) {
            return "Erro: Valores inválidos. Use números.";
        }
    }

    private String cadastrarVolumoso() {
        TipoVolumoso tipo = (TipoVolumoso) getCampoCombo("tipoVolumoso");
        CategoriaVolumoso categoria = (CategoriaVolumoso) getCampoCombo("categoriaVolumoso");
        String regiao = getCampoTexto("regiao");
        
        //preco
        String precoStr = txtPreco.getText().trim();
        if (precoStr.isEmpty()) return "Erro: Informe o preço por kg.";
        double preco;
        try {
            preco = Double.parseDouble(precoStr.replace(",", "."));
        } catch (NumberFormatException e) {
            return "Erro: Preço inválido. Use números (ex: 2.50).";
        }
        if (preco <= 0) return "Erro: Preço deve ser maior que zero.";
        
        
        try {
            double materiaSeca = getCampoNumerico("materiaSeca");
            double proteinaVolumoso = getCampoNumerico("proteinaVolumoso");
            double fdnVolumoso = getCampoNumerico("fdnVolumoso");
            double fdaVolumoso = getCampoNumerico("fdaVolumoso");
            double edVolumoso = getCampoNumerico("edVolumoso");

            return alimentoController.cadastrarVolumoso(tipo, categoria, materiaSeca,
                    proteinaVolumoso, fdnVolumoso, fdaVolumoso, edVolumoso, regiao,preco);
        } catch (NumberFormatException e) {
            return "Erro: Valores inválidos. Use números.";
        }
    }

    private String cadastrarSuplemento() {
        String nome = getCampoTexto("nomeSuplemento");
        String fabricante = getCampoTexto("fabricanteSuplemento");
        String categoria = getCampoTexto("categoriaSuplemento");
        UnidadeSuplemento unidade = (UnidadeSuplemento) getCampoCombo("unidadeRotulo");
        CalculoEnergetico calculoEnergetico = (CalculoEnergetico) getCampoCombo("calculoEnergetico");

        if (nome.isEmpty()) return "Erro: Informe o nome comercial do suplemento.";
        if (fabricante.isEmpty()) return "Erro: Informe o fabricante.";
        if (categoria.isEmpty()) return "Erro: Informe a categoria.";

        
        //preco
        String precoStr = txtPreco.getText().trim();
        if (precoStr.isEmpty()) return "Erro: Informe o preço por kg.";
        double preco;
        try {
            preco = Double.parseDouble(precoStr.replace(",", "."));
        } catch (NumberFormatException e) {
            return "Erro: Preço inválido. Use números (ex: 2.50).";
        }
        if (preco <= 0) return "Erro: Preço deve ser maior que zero.";
        
        
        try {
            double doseRecomendada = getCampoNumerico("doseRecomendada");
            double doseUsada = getCampoNumerico("doseUsada");

            Double energia = getCampoNumericoOpcional("energiaSuplemento");
            Double proteina = getCampoNumericoOpcional("proteinaSuplemento");
            Double gordura = getCampoNumericoOpcional("gordura");
            Double calcio = getCampoNumericoOpcional("calcioSuplemento");
            Double fosforo = getCampoNumericoOpcional("fosforoSuplemento");
            Double sodio = getCampoNumericoOpcional("sodioSuplemento");
            Double potassio = getCampoNumericoOpcional("potassio");
            Double magnesio = getCampoNumericoOpcional("magnesio");
            Double selenio = getCampoNumericoOpcional("selenio");
            Double vitaminaE = getCampoNumericoOpcional("vitaminaE");
            Double biotina = getCampoNumericoOpcional("biotina");

            return alimentoController.cadastrarSuplemento(nome, fabricante, categoria, unidade,
                    doseRecomendada, doseUsada, energia, proteina, gordura, calcio, fosforo,
                    sodio, potassio, magnesio, selenio, vitaminaE, biotina, calculoEnergetico,preco);
        } catch (NumberFormatException e) {
            return "Erro: Valores inválidos. Use números.";
        }
    }

    private String getCampoTexto(String chave) {
        JComponent comp = camposDinamicos.get(chave);
        if (comp instanceof JTextField) {
            return ((JTextField) comp).getText().trim();
        }
        return "";
    }

    private double getCampoNumerico(String chave) throws NumberFormatException {
        String valor = getCampoTexto(chave);
        if (valor.isEmpty()) throw new NumberFormatException("Campo obrigatório vazio: " + chave);
        return Double.parseDouble(valor);
    }

    private Double getCampoNumericoOpcional(String chave) {
        String valor = getCampoTexto(chave);
        if (valor.isEmpty()) return null;
        return Double.parseDouble(valor);
    }

    private Object getCampoCombo(String chave) {
        JComponent comp = camposDinamicos.get(chave);
        if (comp instanceof JComboBox<?>) {
            return ((JComboBox<?>) comp).getSelectedItem();
        }
        return null;
    }

    private void limparCampos() {
        camposDinamicos.forEach((chave, comp) -> {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setText("");
            } else if (comp instanceof JComboBox<?>) {
                ((JComboBox<?>) comp).setSelectedIndex(0);
            }
        });
    }

    private void exibirMensagem(String texto, Color cor) {
        lblMensagem.setText(texto);
        lblMensagem.setForeground(cor);
    }
}
