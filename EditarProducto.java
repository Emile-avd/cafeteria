package vista;

import util.*;

import java.awt.*;
import javax.swing.*;

public class EditarProducto extends JFrame {

    public EditarProducto() {

        setTitle("EDITAR PRODUCTO");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
                new JLabel("Contenido de Editar Producto"),
                BorderLayout.CENTER
        );

        add(
                panelDerecho,
                BorderLayout.CENTER
        );

        setVisible(true);
    }
}

