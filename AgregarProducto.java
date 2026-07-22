
package vista;

import util.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class AgregarProducto extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;


    public AgregarProducto() {

        setTitle("AGREGAR PRODUCTO");
        setSize(900,550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        // PANEL IZQUIERDO

        PanelBotones panelBotones = new PanelBotones(PanelBotones.MENU);
        
        // Conectar botones del panel
        panelBotones.getBtnInicio().addActionListener(e -> {
            new Inicio().setVisible(true);
            dispose();
        });
        panelBotones.getBtnVenta().addActionListener(e -> {
            new PuntoVenta().setVisible(true);
            dispose();
        });
        panelBotones.getBtnMenu().addActionListener(e -> {
            new Menu().setVisible(true);
            dispose();
        });
        panelBotones.getBtnHistorial().addActionListener(e -> {
            new HistorialVista().setVisible(true);
            dispose();
        });
        panelBotones.getBtnCerrarSesion().addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });
        
        add(panelBotones, BorderLayout.WEST);


        // PANEL DERECHO

        JPanel panelDerecho = new JPanel(new BorderLayout());

        panelDerecho.setBackground(
                Colores.FONDO
        );


        panelDerecho.add(
                new PanelEncabezado(),
                BorderLayout.NORTH
        );


        panelDerecho.add(
                crearContenido(),
                BorderLayout.CENTER
        );


        add(
                panelDerecho,
                BorderLayout.CENTER
        );


        setVisible(true);

    }



    private JPanel crearContenido() {


        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(
                Colores.FONDO
        );


        panel.setBorder(
            BorderFactory.createEmptyBorder(
                20,20,20,20
            )
        );



        // COLUMNAS DE LA TABLA

        String columnas[] = {

                "Imagen",
                "Producto",
                "DescripciÃ³n",
                "Precio"

        };



        modelo = new DefaultTableModel(columnas,0);



        tabla = new JTable(modelo);


        tabla.setRowHeight(
                100
        );


        tabla.setFont(
                Fuentes.TEXTO
        );


        tabla.getTableHeader().setFont(
            new Font(
                "Times New Roman",
                Font.BOLD,
                20
            )
        );


        tabla.getTableHeader().setBackground(
            new Color(
                231,
                220,
                205
            )
        );


        tabla.setGridColor(
            new Color(
                220,
                220,
                220
            )
        );


        tabla.setShowVerticalLines(false);



        // AGREGAR UNA FILA VACÃA PARA ESCRIBIR

        modelo.addRow(
            new Object[]{

                "Imagen",
                "",
                "",
                ""

            }
        );



        JScrollPane scroll =
                new JScrollPane(
                        tabla
                );


        panel.add(
                scroll,
                BorderLayout.CENTER
        );



        // BOTÃ“N GUARDAR

        BotonRedondo guardar =
                new BotonRedondo(
                        "Guardar",
                        20
                );


        guardar.addActionListener(e -> {


            JOptionPane.showMessageDialog(
                    this,
                    "Producto guardado correctamente"
            );


        });



        JPanel abajo = new JPanel();


        abajo.setBackground(
                Colores.FONDO
        );


        abajo.add(
                guardar
        );



        panel.add(
                abajo,
                BorderLayout.SOUTH
        );



        return panel;

    }




    public static void main(String[] args) {

        new AgregarProducto();

    }

}
