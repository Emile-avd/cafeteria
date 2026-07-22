package vista;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

import util.*;

public class HistorialVista extends JFrame {

    private JTable tablaHistorial;
    private DefaultTableModel modelo;
    private JTextField txtBuscar;

    public HistorialVista() {

        setTitle("Historial de ventas");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // PANEL LATERAL
        PanelBotones panelBotones = new PanelBotones(PanelBotones.HISTORIAL);
        
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

        // PANEL PRINCIPAL
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(250, 247, 244));

        // ENCABEZADO
        PanelEncabezado panelEncabezado = new PanelEncabezado();
        panelPrincipal.add(panelEncabezado, BorderLayout.NORTH);

        // CONTENIDO
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(new Color(250, 247, 244));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        // TÃTULO
        JLabel lblTitulo = new JLabel("Historial de ventas");
        lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(45, 30, 25));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelContenido.add(lblTitulo);

        // SEPARACIÃ“N
        panelContenido.add(Box.createVerticalStrut(15));

        // PANEL DE FILTROS
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelFiltros.setBackground(new Color(250, 247, 244));
        panelFiltros.setAlignmentX(Component.LEFT_ALIGNMENT);

        // BUSCADOR
        txtBuscar = new JTextField();
        txtBuscar.setPreferredSize(new Dimension(230, 32));
        txtBuscar.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        txtBuscar.setText("Buscar folio");
        txtBuscar.setForeground(new Color(120, 110, 100));
        txtBuscar.setBackground(new Color(229, 220, 210));
        txtBuscar.setBorder(BorderFactory.createEmptyBorder(3, 15, 3, 10));

        // BOTÃ“N BUSCAR
        BotonRedondo btnBuscar = new BotonRedondo("BUSCAR", 20);
        btnBuscar.setPreferredSize(new Dimension(100, 32));
        btnBuscar.setBackground(new Color(210, 150, 75));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("SansSerif", Font.BOLD, 13));
        
        btnBuscar.addActionListener(e -> buscarPorFolio());

        panelFiltros.add(txtBuscar);
        panelFiltros.add(btnBuscar);
        panelContenido.add(panelFiltros);

        // SEPARACIÃ“N ANTES DE LA TABLA
        panelContenido.add(Box.createVerticalStrut(20));

        // TABLA
        String[] columnas = {"Folio", "Fecha", "Hora", "Total", "Producto"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaHistorial = new JTable(modelo);

        // ESTILO DE LA TABLA
        tablaHistorial.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        tablaHistorial.setRowHeight(30);
        tablaHistorial.setShowGrid(true);
        tablaHistorial.setGridColor(new Color(225, 215, 205));
        tablaHistorial.setBackground(Color.WHITE);
        tablaHistorial.setForeground(Color.BLACK);

        // ENCABEZADO DE LA TABLA
        tablaHistorial.getTableHeader().setFont(new Font("Times New Roman", Font.PLAIN, 18));
        tablaHistorial.getTableHeader().setPreferredSize(new Dimension(0, 45));
        tablaHistorial.getTableHeader().setBackground(new Color(229, 220, 210));

        // CENTRAR TEXTO
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tablaHistorial.getColumnCount(); i++) {
            tablaHistorial.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }

        // SCROLLPANE
        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        panelContenido.add(scrollPane);

        // AGREGAR CONTENIDO
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
        add(panelPrincipal, BorderLayout.CENTER);

        // Cargar ventas
        cargarVentas();

        setVisible(true);
    }

    private void cargarVentas() {
        try {
            modelo.setRowCount(0);

            Connection conn = conexionDb.getConexion();
            String sql = "SELECT v.id_venta, v.ven_fecha, v.ven_total, GROUP_CONCAT(p.nombre_producto SEPARATOR ', ') as productos " +
                    "FROM venta v " +
                    "LEFT JOIN detalleVenta dv ON v.id_venta = dv.id_venta " +
                    "LEFT JOIN producto p ON dv.producto_id = p.id_producto " +
                    "WHERE v.ven_estado = true " +
                    "GROUP BY v.id_venta, v.ven_fecha, v.ven_total " +
                    "ORDER BY v.id_venta DESC";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int folio = rs.getInt("id_venta");
                Timestamp fecha = rs.getTimestamp("ven_fecha");
                double total = rs.getDouble("ven_total");
                String productos = rs.getString("productos");

                // Extraer fecha y hora
                String[] fechaHora = extraerFechaHora(fecha);
                String fechaFormato = fechaHora[0];
                String horaFormato = fechaHora[1];

                modelo.addRow(new Object[]{
                    String.format("%03d", folio),
                    fechaFormato,
                    horaFormato,
                    "$" + String.format("%.2f", total),
                    productos != null ? productos : "Sin productos"
                });
            }

            rs.close();
            st.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar ventas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarPorFolio() {
        String folio = txtBuscar.getText().trim();

        if (folio.isEmpty() || folio.equals("Buscar folio")) {
            cargarVentas();
            return;
        }

        try {
            modelo.setRowCount(0);

            Connection conn = conexionDb.getConexion();
            String sql = "SELECT v.id_venta, v.ven_fecha, v.ven_total, GROUP_CONCAT(p.nombre_producto SEPARATOR ', ') as productos " +
                    "FROM venta v " +
                    "LEFT JOIN detalleVenta dv ON v.id_venta = dv.id_venta " +
                    "LEFT JOIN producto p ON dv.producto_id = p.id_producto " +
                    "WHERE v.ven_estado = true AND v.id_venta = ? " +
                    "GROUP BY v.id_venta, v.ven_fecha, v.ven_total";
            PreparedStatement pst = conn.prepareStatement(sql);

            try {
                pst.setInt(1, Integer.parseInt(folio));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El folio debe ser un nÃºmero", "Error", JOptionPane.WARNING_MESSAGE);
                conn.close();
                return;
            }

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int idVenta = rs.getInt("id_venta");
                Timestamp fecha = rs.getTimestamp("ven_fecha");
                double total = rs.getDouble("ven_total");
                String productos = rs.getString("productos");

                // Extraer fecha y hora
                String[] fechaHora = extraerFechaHora(fecha);
                String fechaFormato = fechaHora[0];
                String horaFormato = fechaHora[1];

                modelo.addRow(new Object[]{
                    String.format("%03d", idVenta),
                    fechaFormato,
                    horaFormato,
                    "$" + String.format("%.2f", total),
                    productos != null ? productos : "Sin productos"
                });
            } else {
                JOptionPane.showMessageDialog(this, "Folio no encontrado", "BÃºsqueda", JOptionPane.INFORMATION_MESSAGE);
            }

            rs.close();
            pst.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error en la bÃºsqueda: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String[] extraerFechaHora(Timestamp timestamp) {
        if (timestamp == null) {
            return new String[]{"--/--/--", "--:-- --"};
        }

        java.text.SimpleDateFormat sdfFecha = new java.text.SimpleDateFormat("dd/MM/yy");
        java.text.SimpleDateFormat sdfHora = new java.text.SimpleDateFormat("hh:mm a");

        String fecha = sdfFecha.format(new java.util.Date(timestamp.getTime()));
        String hora = sdfHora.format(new java.util.Date(timestamp.getTime()));

        return new String[]{fecha, hora};
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

    public static void main(String[] args) {
        new HistorialVista();
    }
}

