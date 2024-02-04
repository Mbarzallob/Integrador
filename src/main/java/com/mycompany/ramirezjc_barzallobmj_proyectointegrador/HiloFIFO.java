/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ramirezjc_barzallobmj_proyectointegrador;

import static com.mycompany.ramirezjc_barzallobmj_proyectointegrador.Algoritmos.jTable1;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mbarz
 */
public class HiloFIFO extends Thread {

    int marcos;
    int[] referencias;

    public HiloFIFO(int marcos, int[] referencias) {
        this.marcos = marcos;
        this.referencias = referencias;
    }

    @Override
    public void run() {
        int count = 0;
        int countWhile = 0;
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        String[] fila = new String[marcos];
        while (countWhile < referencias.length) {
            int rows = jTable1.getRowCount();
            if (rows > 1) {
                String[] lastRowData = new String[jTable1.getColumnCount()];
                for (int i = 0; i < jTable1.getColumnCount(); i++) {
                    if (countWhile - 2 >= 0 ) {
                        lastRowData[i] = jTable1.getValueAt(rows - 1, i) == null ? "-1" : jTable1.getValueAt(rows - 1, i).toString();
                    } else {
                        lastRowData[i] = "-1";
                    }
                }
                int j = -1;
                for (String o : lastRowData) {
                    if (o != null) {
                        if (o.equals(String.valueOf(referencias[countWhile]))) {
                            j = 1;
                        }
                    }
                }
                if (j != 1) {
                    fila[count] = String.valueOf(referencias[countWhile]);
                    model.addRow(fila);
                    jTable1.setModel(model);
                    if (count == marcos - 1) {
                        count = 0;
                    } else {
                        count++;
                    }
                }
            } else {
                fila[count] = String.valueOf(referencias[countWhile]);
                model.addRow(fila);
                jTable1.setModel(model);
                count++;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }
            countWhile++;
        }
    }
}
