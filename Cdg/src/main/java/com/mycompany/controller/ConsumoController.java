/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controller;

/**
 *
 * @author daza
 */

import com.mycompany.service.ConsumoService;

public class ConsumoController {

    private ConsumoService consumoService;

    
        
    public ConsumoController() {
        this.consumoService = new ConsumoService();
    }
        //para injecao
    public ConsumoController(ConsumoService consumoServiceMock) {
        this.consumoService = new ConsumoService();
    }

    

    public String registrarConsumo(String nomeEquino, String nomeAlimento, double quantidadeKg) {
        return consumoService.registrarConsumo(nomeEquino, nomeAlimento, quantidadeKg);
    }

    public String registrarSuplemento(String nomeEquino, String nomeSuplemento, double doseDiaria, boolean incluiNoCalculoEnergetico) {
        return consumoService.registrarSuplemento(nomeEquino, nomeSuplemento, doseDiaria, incluiNoCalculoEnergetico);
    }
}
