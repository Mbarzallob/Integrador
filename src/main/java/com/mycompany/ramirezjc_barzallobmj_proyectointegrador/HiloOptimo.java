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
    int milisegundos;

    public HiloOptimo(int marcos, int[] referencias, int segundos) {
        this.marcos = marcos;
        this.referencias = referencias;
        this.milisegundos = segundos * 1000;
    }

    @Override
    public void run() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        String[] fila = new String[marcos];
        int countWhile = 0;
        while (countWhile < referencias.length) {
            int rows = jTable2.getRowCount();
            if (rows > 0) {
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
                    mapaRef.put(s, -1);
                }
                int k = -1;
                for (String o : lastRowData) {
                    if (o != null) {
                        if (o.equals(String.valueOf(referencias[countWhile]))) {
                            k = 1;
                        }
                    }
                }
                if (k != 1) {
                    int j = 0;
                    for (int i = countWhile; i < referencias.length; i++) {
                        for (Map.Entry<String, Integer> entry : mapaRef.entrySet()) {
                            String clave = entry.getKey();
                            if (clave.equals(String.valueOf(referencias[i]))) {
                                if (mapaRef.get(clave) == -1) {
                                    mapaRef.put(clave, j);
                                }
                            }
                            j++;
                        }
                    }
                    //for (Map.Entry<String, Integer> entry : mapaRef.entrySet()) {
                    //  String clave = entry.getKey();
                    //System.out.println(clave+ String.valueOf(mapaRef.get(clave)));
                    //}
                    //System.out.println("");
                    int indiceUltimo = indiceMaximo(mapaRef);
                    //System.out.println(indiceUltimo);
                    //System.out.println("");
                    if (indiceUltimo != -1) {
                        fila[indiceUltimo] = String.valueOf(referencias[countWhile]);
                        model.addRow(fila);
                        jTable2.setModel(model);
                    }
                }
            } else {
                fila[countWhile] = String.valueOf(referencias[countWhile]);
                model.addRow(fila);
                jTable2.setModel(model);
            }

            try {
                Thread.sleep(milisegundos);
            } catch (Exception e) {
                System.out.println(e);
            }

            countWhile++;
        }
    }

    private  int indiceMaximo(Map<String, Integer> mapa) {
        int indice = 0;
        int indiceMax = -1;
        int valorMaximo = Integer.MIN_VALUE;
        if(mapa.entrySet().size()<marcos){
            return mapa.entrySet().size()-1;
        }
        for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
            String clave = entry.getKey();
            if (clave.equals("-1")) {
                return indice;
            }
            if (mapa.get(clave) == -1) {
                return indice;
            }
            if (mapa.get(clave) > valorMaximo) {
                valorMaximo = mapa.get(clave);
                indiceMax = indice;
            }
            indice++;
        }
        return indiceMax;
    }

}
