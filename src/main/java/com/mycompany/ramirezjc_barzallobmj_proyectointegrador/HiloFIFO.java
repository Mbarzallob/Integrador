
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ramirezjc_barzallobmj_proyectointegrador;

import static com.mycompany.ramirezjc_barzallobmj_proyectointegrador.Algoritmos.jTable1;
import static com.mycompany.ramirezjc_barzallobmj_proyectointegrador.Algoritmos.jTable4;
import static com.mycompany.ramirezjc_barzallobmj_proyectointegrador.Algoritmos.fallosFifo;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mbarz
 */
public class HiloFIFO extends Thread {

    int marcos;
    int[] referencias;
    int milisegundos;

    public HiloFIFO(int marcos, int[] referencias, int segundos) {
        this.marcos = marcos;
        this.referencias = referencias;
        this.milisegundos = segundos * 1000;
    }

    @Override
    public void run() {
        int count = 0;
        int countWhile = 0;
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        DefaultTableModel model2 = (DefaultTableModel) jTable4.getModel();
        String[] fila = new String[marcos];
        while (countWhile < referencias.length) {
            int rows = jTable1.getRowCount();
            if (rows > 0) {
                String[] lastRowData = new String[jTable1.getColumnCount()];
                for (int i = 0; i < jTable1.getColumnCount(); i++) {
                    lastRowData[i] = jTable1.getValueAt(rows - 1, i) == null ? "-1" : jTable1.getValueAt(rows - 1, i).toString();
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
                    model2.setRowCount(0);
                    model2.addRow(fila);
                    jTable4.setModel(model2);
                    int lastRow = jTable1.getRowCount() - 1;
                    int columnToColor = count;
                    jTable1.getColumnModel().getColumn(columnToColor).setCellRenderer(new ColorCellRenderer(lastRow, columnToColor));

                    for (int i = 0; i < jTable4.getColumnCount(); i++) {
                        jTable4.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer());
                    }
                    jTable4.getColumnModel().getColumn(columnToColor).setCellRenderer(new ColorCellRenderer(0, columnToColor));

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
                model2.setRowCount(0);
                model2.addRow(fila);
                jTable4.setModel(model2);
                int lastRow = jTable1.getRowCount() - 1;
                int columnToColor = count;
                jTable1.getColumnModel().getColumn(columnToColor).setCellRenderer(new ColorCellRenderer(lastRow, columnToColor));
                
                for (int i = 0; i < jTable4.getColumnCount(); i++) {
                    jTable4.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer());
                }
                jTable4.getColumnModel().getColumn(count).setCellRenderer(new ColorCellRenderer(0, count));

                count++;
            }
            try {
                Thread.sleep(milisegundos);
            } catch (Exception e) {
                System.out.println(e);
            }
            countWhile++;
        }
        fallosFifo.setText(String.valueOf(jTable1.getRowCount()));
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
