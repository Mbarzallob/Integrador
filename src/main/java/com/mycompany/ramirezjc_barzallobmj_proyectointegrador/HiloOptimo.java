/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ramirezjc_barzallobmj_proyectointegrador;

import javax.swing.table.DefaultTableModel;
import static com.mycompany.ramirezjc_barzallobmj_proyectointegrador.Algoritmos.jTable2;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author mbarz
 */
public class HiloOptimo extends Thread {

    int marcos;
    int[] referencias;

    public HiloOptimo(int marcos, int[] referencias) {
        this.marcos = marcos;
        this.referencias = referencias;
    }

    @Override
    public void run() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        String[] fila = new String[marcos];
        int countWhile = 0;
        while (countWhile < referencias.length) {
            if (countWhile < marcos) {
                fila[countWhile] = String.valueOf(referencias[countWhile]);
                model.addRow(fila);
                jTable2.setModel(model);
            } else {

                Object[] lastRowData = new Object[jTable2.getColumnCount()];
                for (int i = 0; i < jTable2.getColumnCount(); i++) {
                    lastRowData[i] = jTable2.getValueAt(countWhile - 1, i);
                }
                Map<Object, Integer> mapaRef = new LinkedHashMap<>();
                for (Object object : lastRowData) {
                    mapaRef.put(object, -1);
                }
                int j = 0;
                for (int i = countWhile; i < referencias.length; i++) {
                    for (Map.Entry<Object, Integer> entry : mapaRef.entrySet()) {
                        Object clave = entry.getKey();
                        if (clave.equals(String.valueOf(referencias[i]))) {
                            if (mapaRef.get(clave) != -1) {
                                mapaRef.put(clave, j);
                            }
                        }
                        j++;
                    }
                }
                int indiceUltimo = indiceMaximo(mapaRef);
                if (indiceUltimo != -1) {
                    fila[indiceUltimo] = String.valueOf(referencias[countWhile]);
                    model.addRow(fila);
                    jTable2.setModel(model);
                }
            }

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println(referencias[countWhile]);

            countWhile++;
        }
    }

    private static int indiceMaximo(Map<Object, Integer> mapa) {
        int indice = 0;
        int indiceMax = -1;
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
