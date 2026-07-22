package vista;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import util.*;

public class PuntoVenta extends JFrame {

    private JTable tablaProductos;
    private DefaultTableModel modeloProductos;
    private JPanel contenido;
    private BotonRedondo btnHacerVenta;

    public PuntoVenta() {

        setTitle("PUNTO DE VENTA");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // =================================
        // PANEL LATERAL
        // =================================

        PanelBotones panelBotones = new PanelBotones(PanelBotones.VENTA);
        
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
            confirmarCerrarSesion();
        });

        add(panelBotones, BorderLayout.WEST);

        // =================================
        // PANEL PRINCIPAL
        // =================================

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Colores.FONDO);

        // ENCABEZADO
        PanelEncabezado panelEncabezado = new PanelEncabezado();
        panelPrincipal.add(panelEncabezado, BorderLayout.NORTH);

        // CONTENIDO PRINCIPAL
        contenido = new JPanel(new BorderLayout());
        contenido.setBackground(Colores.FONDO);

        crearContenido();

        panelPrincipal.add(contenido, BorderLayout.CENTER);

        add(panelPrincipal, BorderLayout.CENTER);

        setVisible(true);
    }

    private void crearContenido() {

        // PANEL IZQUIERDO - TABLA DE PRODUCTOS
        JPanel panelIzq = new JPanel(new BorderLayout());
        panelIzq.setBackground(Colores.FONDO);
        panelIzq.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));

        JLabel lblProductos = new JLabel("Productos Disponibles");
        lblProductos.setFont(new Font("Arial", Font.BOLD, 14));
        lblProductos.setForeground(Colores.TEXTO);
        panelIzq.add(lblProductos, BorderLayout.NORTH);

        // TABLA
        String[] columnas = {"Producto", "Precio", "Estado"};
        modeloProductos = new DefaultTableModel(columnas, 0);
        tablaProductos = new JTable(modeloProductos);
        tablaProductos.setBackground(Colores.CAMPO);
        tablaProductos.setForeground(Colores.TEXTO);
        tablaProductos.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaProductos.getTableHeader().setBackground(new Color(36, 26, 20));
        tablaProductos.getTableHeader().setForeground(Color.WHITE);
        tablaProductos.setRowHeight(25);

        JScrollPane scrollProductos = new JScrollPane(tablaProductos);
        scrollProductos.setBackground(Colores.CAMPO);
        panelIzq.add(scrollProductos, BorderLayout.CENTER);

        cargarProductos();

        // PANEL DERECHO - BOTÃ“N VENTA
        JPanel panelDer = new JPanel(new BorderLayout());
        panelDer.setBackground(Colores.FONDO);
        panelDer.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));

        btnHacerVenta = new BotonRedondo("HACER VENTA", 20);
        btnHacerVenta.setPreferredSize(new Dimension(150, 150));
        btnHacerVenta.setBackground(new Color(36, 26, 20));
        btnHacerVenta.setForeground(Colores.BOTON_SELECT);
        btnHacerVenta.setFont(new Font("Arial", Font.BOLD, 14));

        btnHacerVenta.addActionListener(e -> {
            new DialogoVenta(PuntoVenta.this).setVisible(true);
        });

        JPanel panelBtn = new JPanel(new GridBagLayout());
        panelBtn.setBackground(Colores.FONDO);
        panelBtn.add(btnHacerVenta);

        panelDer.add(panelBtn, BorderLayout.CENTER);

        // AGREGAR PANELES
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelIzq, panelDer);
        split.setDividerLocation(450);
        split.setBackground(Colores.FONDO);

        contenido.add(split, BorderLayout.CENTER);
    }

    private void cargarProductos() {
        try {
            Connection conn = conexionDb.getConexion();
            String sql = "SELECT nombre_producto, precio, estado FROM producto WHERE estado = true";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            modeloProductos.setRowCount(0);

            while (rs.next()) {
                String nombre = rs.getString("nombre_producto");
                double precio = rs.getDouble("precio");
                boolean estado = rs.getBoolean("estado");

                modeloProductos.addRow(new Object[]{
                    nombre,
                    "$" + String.format("%.2f", precio),
                    estado ? "Disponible" : "No disponible"
                });
            }

            rs.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void confirmarCerrarSesion() {
        int respuesta = JOptionPane.showConfirmDialog(
            this,
            "Â¿Deseas cerrar sesiÃ³n?",
            "Confirmar cerrar sesiÃ³n",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            util.SessionManager.cerrarSesion();
            new Login().setVisible(true);
            dispose();
        }
    }
}

