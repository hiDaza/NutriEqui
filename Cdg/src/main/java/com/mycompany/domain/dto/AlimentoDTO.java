/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.domain.dto;

/**
 *
 * @author daza
 */

import com.mycompany.domain.CalculoEnergetico;
import com.mycompany.domain.CategoriaRacao;
import com.mycompany.domain.CategoriaVolumoso;
import com.mycompany.domain.TipoAlimento;
import com.mycompany.domain.TipoVolumoso;
import com.mycompany.domain.UnidadeSuplemento;

public class AlimentoDTO {
    private TipoAlimento tipo;
    // Comuns
    private String nome;
    private String fabricante;
    private Double precoPorKg;

    // Ração
    private CategoriaRacao categoriaRacao;
    private Double umidade;
    private Double proteinaBruta;
    private Double extratoEtereo;
    private Double fibraBruta;
    private Double fda;
    private Double fdn;
    private Double materiaMineralRacao;
    private Double calcioRacao;
    private Double fosforoRacao;
    private Double sodioRacao;
    private Double edDec;

    // Volumoso
    private TipoVolumoso tipoVolumoso;
    private CategoriaVolumoso categoriaVolumoso;
    private Double materiaSeca;
    private Double proteinaVolumoso;
    private Double fdnVolumoso;
    private Double fdaVolumoso;
    private Double edVolumoso;
    private String regiao;

    // Suplemento
    private String nomeComercialSuplemento;
    private String fabricanteSuplemento;
    private String categoriaSuplemento;
    private Double doseRecomendada;
    private Double doseUsada;
    private UnidadeSuplemento unidadeRotulo;
    private Double energiaSuplemento;
    private Double proteinaSuplemento;
    private Double gordura;
    private Double calcioSuplemento;
    private Double fosforoSuplemento;
    private Double sodioSuplemento;
    private Double potassio;
    private Double magnesio;
    private Double selenio;
    private Double vitaminaE;
    private Double biotina;
    private CalculoEnergetico calculoEnergetico;
    
    
    public TipoAlimento getTipo() {
        return tipo;
    }

    public void setTipo(TipoAlimento tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Double getPrecoPorKg() {
        return precoPorKg;
    }

    public void setPrecoPorKg(Double precoPorKg) {
        this.precoPorKg = precoPorKg;
    }

    // Ração
    public CategoriaRacao getCategoriaRacao() {
        return categoriaRacao;
    }

    public void setCategoriaRacao(CategoriaRacao categoriaRacao) {
        this.categoriaRacao = categoriaRacao;
    }

    public Double getUmidade() {
        return umidade;
    }

    public void setUmidade(Double umidade) {
        this.umidade = umidade;
    }

    public Double getProteinaBruta() {
        return proteinaBruta;
    }

    public void setProteinaBruta(Double proteinaBruta) {
        this.proteinaBruta = proteinaBruta;
    }

    public Double getExtratoEtereo() {
        return extratoEtereo;
    }

    public void setExtratoEtereo(Double extratoEtereo) {
        this.extratoEtereo = extratoEtereo;
    }

    public Double getFibraBruta() {
        return fibraBruta;
    }

    public void setFibraBruta(Double fibraBruta) {
        this.fibraBruta = fibraBruta;
    }

    public Double getFda() {
        return fda;
    }

    public void setFda(Double fda) {
        this.fda = fda;
    }

    public Double getFdn() {
        return fdn;
    }

    public void setFdn(Double fdn) {
        this.fdn = fdn;
    }

    public Double getMateriaMineralRacao() {
        return materiaMineralRacao;
    }

    public void setMateriaMineralRacao(Double materiaMineralRacao) {
        this.materiaMineralRacao = materiaMineralRacao;
    }

    public Double getCalcioRacao() {
        return calcioRacao;
    }

    public void setCalcioRacao(Double calcioRacao) {
        this.calcioRacao = calcioRacao;
    }

    public Double getFosforoRacao() {
        return fosforoRacao;
    }

    public void setFosforoRacao(Double fosforoRacao) {
        this.fosforoRacao = fosforoRacao;
    }

    public Double getSodioRacao() {
        return sodioRacao;
    }

    public void setSodioRacao(Double sodioRacao) {
        this.sodioRacao = sodioRacao;
    }

    public Double getEdDec() {
        return edDec;
    }

    public void setEdDec(Double edDec) {
        this.edDec = edDec;
    }

    // Volumoso
    public TipoVolumoso getTipoVolumoso() {
        return tipoVolumoso;
    }

    public void setTipoVolumoso(TipoVolumoso tipoVolumoso) {
        this.tipoVolumoso = tipoVolumoso;
    }

    public CategoriaVolumoso getCategoriaVolumoso() {
        return categoriaVolumoso;
    }

    public void setCategoriaVolumoso(CategoriaVolumoso categoriaVolumoso) {
        this.categoriaVolumoso = categoriaVolumoso;
    }

    public Double getMateriaSeca() {
        return materiaSeca;
    }

    public void setMateriaSeca(Double materiaSeca) {
        this.materiaSeca = materiaSeca;
    }

    public Double getProteinaVolumoso() {
        return proteinaVolumoso;
    }

    public void setProteinaVolumoso(Double proteinaVolumoso) {
        this.proteinaVolumoso = proteinaVolumoso;
    }

    public Double getFdnVolumoso() {
        return fdnVolumoso;
    }

    public void setFdnVolumoso(Double fdnVolumoso) {
        this.fdnVolumoso = fdnVolumoso;
    }

    public Double getFdaVolumoso() {
        return fdaVolumoso;
    }

    public void setFdaVolumoso(Double fdaVolumoso) {
        this.fdaVolumoso = fdaVolumoso;
    }

    public Double getEdVolumoso() {
        return edVolumoso;
    }

    public void setEdVolumoso(Double edVolumoso) {
        this.edVolumoso = edVolumoso;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    // Suplemento
    public String getNomeComercialSuplemento() {
        return nomeComercialSuplemento;
    }

    public void setNomeComercialSuplemento(String nomeComercialSuplemento) {
        this.nomeComercialSuplemento = nomeComercialSuplemento;
    }

    public String getFabricanteSuplemento() {
        return fabricanteSuplemento;
    }

    public void setFabricanteSuplemento(String fabricanteSuplemento) {
        this.fabricanteSuplemento = fabricanteSuplemento;
    }

    public String getCategoriaSuplemento() {
        return categoriaSuplemento;
    }

    public void setCategoriaSuplemento(String categoriaSuplemento) {
        this.categoriaSuplemento = categoriaSuplemento;
    }

    public Double getDoseRecomendada() {
        return doseRecomendada;
    }

    public void setDoseRecomendada(Double doseRecomendada) {
        this.doseRecomendada = doseRecomendada;
    }

    public Double getDoseUsada() {
        return doseUsada;
    }

    public void setDoseUsada(Double doseUsada) {
        this.doseUsada = doseUsada;
    }

    public UnidadeSuplemento getUnidadeRotulo() {
        return unidadeRotulo;
    }

    public void setUnidadeRotulo(UnidadeSuplemento unidadeRotulo) {
        this.unidadeRotulo = unidadeRotulo;
    }

    public Double getEnergiaSuplemento() {
        return energiaSuplemento;
    }

    public void setEnergiaSuplemento(Double energiaSuplemento) {
        this.energiaSuplemento = energiaSuplemento;
    }

    public Double getProteinaSuplemento() {
        return proteinaSuplemento;
    }

    public void setProteinaSuplemento(Double proteinaSuplemento) {
        this.proteinaSuplemento = proteinaSuplemento;
    }

    public Double getGordura() {
        return gordura;
    }

    public void setGordura(Double gordura) {
        this.gordura = gordura;
    }

    public Double getCalcioSuplemento() {
        return calcioSuplemento;
    }

    public void setCalcioSuplemento(Double calcioSuplemento) {
        this.calcioSuplemento = calcioSuplemento;
    }

    public Double getFosforoSuplemento() {
        return fosforoSuplemento;
    }

    public void setFosforoSuplemento(Double fosforoSuplemento) {
        this.fosforoSuplemento = fosforoSuplemento;
    }

    public Double getSodioSuplemento() {
        return sodioSuplemento;
    }

    public void setSodioSuplemento(Double sodioSuplemento) {
        this.sodioSuplemento = sodioSuplemento;
    }

    public Double getPotassio() {
        return potassio;
    }

    public void setPotassio(Double potassio) {
        this.potassio = potassio;
    }

    public Double getMagnesio() {
        return magnesio;
    }

    public void setMagnesio(Double magnesio) {
        this.magnesio = magnesio;
    }

    public Double getSelenio() {
        return selenio;
    }

    public void setSelenio(Double selenio) {
        this.selenio = selenio;
    }

    public Double getVitaminaE() {
        return vitaminaE;
    }

    public void setVitaminaE(Double vitaminaE) {
        this.vitaminaE = vitaminaE;
    }

    public Double getBiotina() {
        return biotina;
    }

    public void setBiotina(Double biotina) {
        this.biotina = biotina;
    }

    public CalculoEnergetico getCalculoEnergetico() {
        return calculoEnergetico;
    }

    public void setCalculoEnergetico(CalculoEnergetico calculoEnergetico) {
        this.calculoEnergetico = calculoEnergetico;
    }
    
}