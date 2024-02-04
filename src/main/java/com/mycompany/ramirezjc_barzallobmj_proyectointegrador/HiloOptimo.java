/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ramirezjc_barzallobmj_proyectointegrador;

import javax.swing.table.DefaultTableModel;
import static com.mycompany.ramirezjc_barzallobmj_proyectointegrador.Algoritmos.jTable2;
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
            int rows = jTable2.getRowCount();
            if (rows > 1) {
                String[] lastRowData = new String[jTable2.getColumnCount()];
                for (int i = 0; i < jTable2.getColumnCount(); i++) {
                    if (rows - 1 >= 0) {
                        lastRowData[i] = jTable2.getValueAt(rows - 1, i) == null ? "-1" : jTable2.getValueAt(rows - 1, i).toString();
                    } else {
                        lastRowData[i] = "-1";
                    }
                }
                Map<String, Integer> mapaRef = new LinkedHashMap<>();
                for (String s : lastRowData) {
                    System.out.println(s);
                    mapaRef.put(s, -1);
                }

                System.out.println("");
                int j = 0;
                for (int i = countWhile; i < referencias.length; i++) {
                    for (Map.Entry<String, Integer> entry : mapaRef.entrySet()) {
                        String clave = entry.getKey();
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
            } else {
                fila[countWhile] = String.valueOf(referencias[countWhile]);
                model.addRow(fila);
                jTable2.setModel(model);
            }

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }

            countWhile++;
        }
    }

    private static int indiceMaximo(Map<String, Integer> mapa) {
        int indice = 0;
        int indiceMax = -1;
        int valorMaximo = Integer.MIN_VALUE;
        for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
            String clave = entry.getKey();
            if (mapa.get(clave) > valorMaximo) {
                valorMaximo = mapa.get(clave);
                indiceMax = indice;
            }
            indice++;
        }
        return indiceMax;
    }

}
