/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.ramirezjc_barzallobmj_proyectointegrador;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mbarz
 */
public class RamirezJC_BarzallobMJ_ProyectoIntegrador {

    public static void main(String[] args) {
        Map<Object,Integer>mapa = new LinkedHashMap<>();
        mapa.put("si", 0);
        mapa.put("no", 2);
        mapa.put("porque", 1);
        System.out.println(indiceMaximo(mapa));
        
    }
    private static int indiceMaximo(Map<Object, Integer> mapa) {
        int indice = 0;
        int indiceMax = 0;
        int valorMaximo = -1;
        for (Map.Entry<Object, Integer> entry : mapa.entrySet()) {
            Object clave = entry.getKey();
            if (mapa.get(clave) > valorMaximo) {
                valorMaximo = mapa.get(clave);
                indiceMax = indice;
            }
            indice++;
        }
        return indiceMax;
    }
}
