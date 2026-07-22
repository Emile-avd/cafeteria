package vista;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

import util.*;

public class Inicio extends JFrame {

    private JPanel contenido;
    private int totalVentas = 0;
    private double totalIngresos = 0;
    private String productoMasVendido = "N/A";

    public Inicio() {

        // CONFIGURACIÃ“N DE LA VENTANA

        setTitle("EL RINCÃ“N DEL CAFÃ‰");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        // ---------------- PANEL LATERAL ----------------

        PanelBotones panelBotones = new PanelBotones(PanelBotones.INICIO);
        
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
            confirmarCerrarSesion();
        });
        
        add(panelBotones, BorderLayout.WEST);


        // ---------------- PANEL CENTRAL ----------------

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(Colores.FONDO);


        // ---------------- ENCABEZADO ----------------

        PanelEncabezado panelEncabezado = new PanelEncabezado();

        panelCentral.add(panelEncabezado, BorderLayout.NORTH);


        // Cargar datos antes de crear contenido
        cargarDatos();


        // ---------------- CONTENIDO ----------------

        contenido = new JPanel(new BorderLayout());
        contenido.setBackground(Colores.FONDO);

        crearContenido();

        panelCentral.add(contenido, BorderLayout.CENTER);


        // ---------------- AGREGAR PANEL CENTRAL ----------------

        add(panelCentral, BorderLayout.CENTER);

        setVisible(true);
    }


    // ================ CARGAR DATOS DE LA BD ================

    private void cargarDatos() {
        try {
            Connection conn = conexionDb.getConexion();

            // CONTAR TOTAL DE VENTAS
            String sqlVentas = "SELECT COUNT(*) AS total FROM venta";
            Statement stVentas = conn.createStatement();
            ResultSet rsVentas = stVentas.executeQuery(sqlVentas);
            if (rsVentas.next()) {
                totalVentas = rsVentas.getInt("total");
            }
            rsVentas.close();
            stVentas.close();

            // CALCULAR INGRESOS TOTALES
            String sqlIngresos = "SELECT SUM(ven_total) AS ingresos FROM venta";
            Statement stIngresos = conn.createStatement();
            ResultSet rsIngresos = stIngresos.executeQuery(sqlIngresos);
            if (rsIngresos.next()) {
                totalIngresos = rsIngresos.getDouble("ingresos");
                if (totalIngresos == 0 || Double.isNaN(totalIngresos)) {
                    totalIngresos = 0;
                }
            }
            rsIngresos.close();
            stIngresos.close();

            // PRODUCTO MÃS VENDIDO
            String sqlProducto = "SELECT p.nombre_producto, SUM(dv.cantidad) AS total_cantidad " +
                    "FROM detalleVenta dv " +
                    "JOIN producto p ON dv.producto_id = p.id_producto " +
                    "GROUP BY dv.producto_id, p.nombre_producto " +
                    "ORDER BY total_cantidad DESC LIMIT 1";
            Statement stProducto = conn.createStatement();
            ResultSet rsProducto = stProducto.executeQuery(sqlProducto);
            if (rsProducto.next()) {
                String nombre = rsProducto.getString("nombre_producto");
                if (nombre != null && !nombre.isEmpty()) {
                    productoMasVendido = nombre;
                } else {
                    productoMasVendido = "N/A";
                }
            } else {
                productoMasVendido = "N/A";
            }
            rsProducto.close();
            stProducto.close();

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // ================ CREAR CONTENIDO ================

    private void crearContenido() {

        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(Colores.FONDO);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;

        // ================ TARJETAS ================

        JPanel ventas = crearCuadro(
                "Ventas realizadas",
                String.valueOf(totalVentas)
        );

        JPanel ingresos = crearCuadro(
                "Ingresos Totales",
                String.format("$%.2f", totalIngresos)
        );

        JPanel productos = crearCuadro(
                "Producto mÃ¡s vendido",
                productoMasVendido
        );

        gbc.gridy = 0;
        gbc.gridx = 0;
        panelPrincipal.add(ventas, gbc);

        gbc.gridx = 1;
        panelPrincipal.add(ingresos, gbc);

        gbc.gridx = 2;
        panelPrincipal.add(productos, gbc);


        // ================ BIENVENIDA ================

        JPanel bienvenida = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // FONDO DEGRADADO
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(36, 26, 20),
                        getWidth(), getHeight(), Colores.BOTON_MARRON
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(20, 20, getWidth() - 40, getHeight() - 40, 30, 30);

                // BORDE
                g2.setColor(Colores.BOTON_SELECT);
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(20, 20, getWidth() - 40, getHeight() - 40, 30, 30);

                // EMOJI CAFÃ‰
                g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
                g2.setColor(Color.WHITE);
                g2.drawString("â˜•", getWidth() / 2 - 40, getHeight() / 2 + 20);

                // TEXTO BIENVENIDA
                g2.setFont(new Font("Arial", Font.BOLD, 32));
                g2.setColor(Colores.BOTON_SELECT);
                String texto = "Â¡Bienvenido a El RincÃ³n del CafÃ©!";
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(texto)) / 2;
                g2.drawString(texto, x, getHeight() / 2 - 60);

                // SUBTEXTO
                g2.setFont(new Font("Arial", Font.PLAIN, 16));
                g2.setColor(Color.WHITE);
                String subtexto = "Tu lugar para disfrutar del mejor cafÃ©";
                fm = g2.getFontMetrics();
                x = (getWidth() - fm.stringWidth(subtexto)) / 2;
                g2.drawString(subtexto, x, getHeight() / 2 + 60);
            }
        };

        bienvenida.setBackground(Colores.FONDO);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.weighty = 2;

        panelPrincipal.add(bienvenida, gbc);

        contenido.add(panelPrincipal, BorderLayout.CENTER);
    }


    // ================ CREAR TARJETAS ================

    private JPanel crearCuadro(String titulo, String valor) {

        JPanel panel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(Colores.CAMPO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            }
        };

        panel.setPreferredSize(new Dimension(180, 140));
        panel.setMinimumSize(new Dimension(150, 120));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel icono = new JLabel("â˜•", SwingConstants.CENTER);
        icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        lblTitulo.setForeground(Colores.TEXTO);

        JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
        lblValor.setFont(new Font("Arial", Font.BOLD, 24));
        lblValor.setForeground(Colores.TEXTO);

        icono.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblValor.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(15));
        panel.add(icono);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblValor);

        return panel;
    }


    // ================ CONFIRMAR CERRAR SESIÃ“N ================

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

    // ================ MAIN ================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Inicio();
        });
    }

}
