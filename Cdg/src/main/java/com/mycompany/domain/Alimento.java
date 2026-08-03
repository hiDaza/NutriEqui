/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoAlimento tipo;

    @OneToMany(mappedBy = "alimento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consumo> consumos = new ArrayList<>();

    // Campos comuns
    private String nome; // Nome comercial
    private String fabricante;

    // ===== RAÇÃO =====
    @Enumerated(EnumType.STRING)
    private CategoriaRacao categoriaRacao;

    private Double umidade; // %
    private Double proteinaBruta; // %
    private Double extratoEtereo; // %
    private Double fibraBruta; // %
    private Double fda; // %
    private Double fdn; // %
    private Double materiaMineralRacao; // %
    private Double calcioRacao; // % ou g/kg
    private Double fosforoRacao; // % ou g/kg
    private Double sodioRacao; // % ou g/kg
    private Double edDec; // Energia Digestível declarada - Mcal/kg (opcional)
    private Double edEst; // Energia Digestível estimada - calculada pelo sistema

    // ===== VOLUMOSO =====
    @Enumerated(EnumType.STRING)
    private TipoVolumoso tipoVolumoso;

    @Enumerated(EnumType.STRING)
    private CategoriaVolumoso categoriaVolumoso;

    private Double materiaSeca; // %
    private Double proteinaVolumoso; // %
    private Double fdnVolumoso; // %
    private Double fdaVolumoso; // %
    private Double edVolumoso; // Mcal/kg
    private String regiao;

    // ===== SUPLEMENTO =====
    private String nomeComercialSuplemento;
    private String fabricanteSuplemento;
    private String categoriaSuplemento;
    
    private Double doseRecomendada; // g/dia, mL/dia, scoop
    private Double doseUsada;
    @Enumerated(EnumType.STRING)
    private UnidadeSuplemento unidadeRotulo;

    private Double energiaSuplemento; // Opcional
    private Double proteinaSuplemento; // Opcional
    private Double gordura; // Opcional
    private Double calcioSuplemento; // Opcional
    private Double fosforoSuplemento; // Opcional
    private Double sodioSuplemento; // Opcional
    private Double potassio; // Opcional
    private Double magnesio; // Opcional
    private Double selenio; // Opcional
    private Double vitaminaE; // Opcional
    private Double biotina; // Opcional

    @Enumerated(EnumType.STRING)
    private CalculoEnergetico entraCalculoEnergetico;

    public Alimento() {}

    // Construtor para Ração
    public Alimento(String nomeComercial, String fabricante, CategoriaRacao categoriaRacao) {
        this.tipo = TipoAlimento.RACAO;
        this.nome = nomeComercial;
        this.fabricante = fabricante;
        this.categoriaRacao = categoriaRacao;
    }

    // Construtor para Volumoso
    public Alimento(String nome, TipoVolumoso tipoVolumoso, CategoriaVolumoso categoriaVolumoso) {
        this.tipo = TipoAlimento.VOLUMOSO;
        this.nome = nome;
        this.tipoVolumoso = tipoVolumoso;
        this.categoriaVolumoso = categoriaVolumoso;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TipoAlimento getTipo() { return tipo; }
    public void setTipo(TipoAlimento tipo) { this.tipo = tipo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }

    // Ração
    public CategoriaRacao getCategoriaRacao() { return categoriaRacao; }
    public void setCategoriaRacao(CategoriaRacao categoriaRacao) { this.categoriaRacao = categoriaRacao; }
    public Double getUmidade() { return umidade; }
    public void setUmidade(Double umidade) { this.umidade = umidade; }
    public Double getProteinaBruta() { return proteinaBruta; }
    public void setProteinaBruta(Double proteinaBruta) { this.proteinaBruta = proteinaBruta; }
    public Double getExtratoEtereo() { return extratoEtereo; }
    public void setExtratoEtereo(Double extratoEtereo) { this.extratoEtereo = extratoEtereo; }
    public Double getFibraBruta() { return fibraBruta; }
    public void setFibraBruta(Double fibraBruta) { this.fibraBruta = fibraBruta; }
    public Double getFda() { return fda; }
    public void setFda(Double fda) { this.fda = fda; }
    public Double getFdn() { return fdn; }
    public void setFdn(Double fdn) { this.fdn = fdn; }
    public Double getMateriaMineralRacao() { return materiaMineralRacao; }
    public void setMateriaMineralRacao(Double materiaMineralRacao) { this.materiaMineralRacao = materiaMineralRacao; }
    public Double getCalcioRacao() { return calcioRacao; }
    public void setCalcioRacao(Double calcioRacao) { this.calcioRacao = calcioRacao; }
    public Double getFosforoRacao() { return fosforoRacao; }
    public void setFosforoRacao(Double fosforoRacao) { this.fosforoRacao = fosforoRacao; }
    public Double getSodioRacao() { return sodioRacao; }
    public void setSodioRacao(Double sodioRacao) { this.sodioRacao = sodioRacao; }
    public Double getEdDec() { return edDec; }
    public void setEdDec(Double edDec) { this.edDec = edDec; }
    public Double getEdEst() { return edEst; }
    public void setEdEst(Double edEst) { this.edEst = edEst; }

    // Volumoso
    public TipoVolumoso getTipoVolumoso() { return tipoVolumoso; }
    public void setTipoVolumoso(TipoVolumoso tipoVolumoso) { this.tipoVolumoso = tipoVolumoso; }
    public CategoriaVolumoso getCategoriaVolumoso() { return categoriaVolumoso; }
    public void setCategoriaVolumoso(CategoriaVolumoso categoriaVolumoso) { this.categoriaVolumoso = categoriaVolumoso; }
    public Double getMateriaSeca() { return materiaSeca; }
    public void setMateriaSeca(Double materiaSeca) { this.materiaSeca = materiaSeca; }
    public Double getProteinaVolumoso() { return proteinaVolumoso; }
    public void setProteinaVolumoso(Double proteinaVolumoso) { this.proteinaVolumoso = proteinaVolumoso; }
    public Double getFdnVolumoso() { return fdnVolumoso; }
    public void setFdnVolumoso(Double fdnVolumoso) { this.fdnVolumoso = fdnVolumoso; }
    public Double getFdaVolumoso() { return fdaVolumoso; }
    public void setFdaVolumoso(Double fdaVolumoso) { this.fdaVolumoso = fdaVolumoso; }
    public Double getEdVolumoso() { return edVolumoso; }
    public void setEdVolumoso(Double edVolumoso) { this.edVolumoso = edVolumoso; }
    public String getRegiao() { return regiao; }
    public void setRegiao(String regiao) { this.regiao = regiao; }

    // Suplemento
    public String getNomeComercialSuplemento() { return nomeComercialSuplemento; }
    public void setNomeComercialSuplemento(String nomeComercialSuplemento) { this.nomeComercialSuplemento = nomeComercialSuplemento; }
    public String getFabricanteSuplemento() { return fabricanteSuplemento; }
    public void setFabricanteSuplemento(String fabricanteSuplemento) { this.fabricanteSuplemento = fabricanteSuplemento; }
    public String getCategoriaSuplemento() { return categoriaSuplemento; }
    public void setCategoriaSuplemento(String categoriaSuplemento) { this.categoriaSuplemento = categoriaSuplemento; }
    public Double getDoseRecomendada() { return doseRecomendada; }
    public void setDoseRecomendada(Double doseRecomendada) { this.doseRecomendada = doseRecomendada; }
    public Double getDoseUsada() { return doseUsada; }
    public void setDoseUsada(Double doseUsada) { this.doseUsada = doseUsada; }
    public UnidadeSuplemento getUnidadeRotulo() { return unidadeRotulo; }
    public void setUnidadeRotulo(UnidadeSuplemento unidadeRotulo) { this.unidadeRotulo = unidadeRotulo; }
    public Double getEnergiaSuplemento() { return energiaSuplemento; }
    public void setEnergiaSuplemento(Double energiaSuplemento) { this.energiaSuplemento = energiaSuplemento; }
    public Double getProteinaSuplemento() { return proteinaSuplemento; }
    public void setProteinaSuplemento(Double proteinaSuplemento) { this.proteinaSuplemento = proteinaSuplemento; }
    public Double getGordura() { return gordura; }
    public void setGordura(Double gordura) { this.gordura = gordura; }
    public Double getCalcioSuplemento() { return calcioSuplemento; }
    public void setCalcioSuplemento(Double calcioSuplemento) { this.calcioSuplemento = calcioSuplemento; }
    public Double getFosforoSuplemento() { return fosforoSuplemento; }
    public void setFosforoSuplemento(Double fosforoSuplemento) { this.fosforoSuplemento = fosforoSuplemento; }
    public Double getSodioSuplemento() { return sodioSuplemento; }
    public void setSodioSuplemento(Double sodioSuplemento) { this.sodioSuplemento = sodioSuplemento; }
    public Double getPotassio() { return potassio; }
    public void setPotassio(Double potassio) { this.potassio = potassio; }
    public Double getMagnesio() { return magnesio; }
    public void setMagnesio(Double magnesio) { this.magnesio = magnesio; }
    public Double getSelenio() { return selenio; }
    public void setSelenio(Double selenio) { this.selenio = selenio; }
    public Double getVitaminaE() { return vitaminaE; }
    public void setVitaminaE(Double vitaminaE) { this.vitaminaE = vitaminaE; }
    public Double getBiotina() { return biotina; }
    public void setBiotina(Double biotina) { this.biotina = biotina; }
    public CalculoEnergetico getEntraCalculoEnergetico() { return entraCalculoEnergetico; }
    public void setEntraCalculoEnergetico(CalculoEnergetico entraCalculoEnergetico) { this.entraCalculoEnergetico = entraCalculoEnergetico; }

    public List<Consumo> getConsumos() { return consumos; }
    public void setConsumos(List<Consumo> consumos) { this.consumos = consumos; }

    public double getEnergiaDigestivel() {
        if (tipo == TipoAlimento.RACAO) {
            return edEst != null ? edEst : (edDec != null ? edDec : 0.0);
        } else if (tipo == TipoAlimento.VOLUMOSO) {
            return edVolumoso != null ? edVolumoso : 0.0;
        } else if (tipo == TipoAlimento.SUPLEMENTO) {
            return energiaSuplemento != null ? (energiaSuplemento / 1000.0) : 0.0;
        }
        return 0.0;
    }
}