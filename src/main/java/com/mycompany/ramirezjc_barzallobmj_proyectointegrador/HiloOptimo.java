/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ramirezjc_barzallobmj_proyectointegrador;

import javax.swing.table.DefaultTableModel;
import static com.mycompany.ramirezjc_barzallobmj_proyectointegrador.Algoritmos.jTable2;
import static com.mycompany.ramirezjc_barzallobmj_proyectointegrador.Algoritmos.jTable5;
import static com.mycompany.ramirezjc_barzallobmj_proyectointegrador.Algoritmos.fallosOptimo;

import java.awt.Color;
import java.awt.Component;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

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
        DefaultTableModel model2 = (DefaultTableModel) jTable5.getModel();
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
                    int valorMax = 100;
                    for (Map.Entry<String, Integer> entry : mapaRef.entrySet()) {
                        String clave = entry.getKey();
                        if (mapaRef.get(clave) == -1) {
                            mapaRef.put(clave, valorMax);
                        }
                        valorMax--;
                    }

                    int indiceUltimo = indiceMaximo(mapaRef);
                    if (indiceUltimo != -1) {
                        fila[indiceUltimo] = String.valueOf(referencias[countWhile]);
                        model.addRow(fila);
                        jTable2.setModel(model);
                        model2.setRowCount(0);
                        model2.addRow(fila);
                        jTable5.setModel(model2);

                        int lastRow = jTable2.getRowCount() - 1;
                        int columnToColor = indiceUltimo;
                        jTable2.getColumnModel().getColumn(columnToColor).setCellRenderer(new ColorCellRenderer(lastRow, columnToColor));
                        for (int i = 0; i < jTable5.getColumnCount(); i++) {
                            jTable5.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer());
                        }
                        jTable5.getColumnModel().getColumn(indiceUltimo).setCellRenderer(new ColorCellRenderer(0, indiceUltimo));
                    }
                }
            } else {
                fila[countWhile] = String.valueOf(referencias[countWhile]);
                model.addRow(fila);
                jTable2.setModel(model);
                model2.setRowCount(0);
                model2.addRow(fila);
                jTable5.setModel(model2);
                int lastRow = jTable2.getRowCount() - 1;
                int columnToColor = countWhile;
                jTable2.getColumnModel().getColumn(columnToColor).setCellRenderer(new ColorCellRenderer(lastRow, columnToColor));
                for (int i = 0; i < jTable5.getColumnCount(); i++) {
                    jTable5.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer());
                }
                jTable5.getColumnModel().getColumn(countWhile).setCellRenderer(new ColorCellRenderer(0, countWhile));

            }

            try {
                Thread.sleep(milisegundos);
            } catch (Exception e) {
                System.out.println(e);
            }

            countWhile++;
        }
        fallosOptimo.setText(String.valueOf(jTable2.getRowCount()));
    }

    private int indiceMaximo(Map<String, Integer> mapa) {
        int indice = 0;
        int indiceMax = -1;
        int valorMaximo = Integer.MIN_VALUE;
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

    class ColorCellRenderer extends DefaultTableCellRenderer {

        private int rowToColor;
        private int columnToColor;

        public ColorCellRenderer(int rowToColor, int columnToColor) {
            this.rowToColor = rowToColor;
            this.columnToColor = columnToColor;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (row == rowToColor && column == columnToColor) {
                cell.setBackground(Color.GREEN);
            } else {
                cell.setBackground(table.getBackground());
            }

            return cell;
        }
    }
}
