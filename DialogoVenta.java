package vista;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import util.*;

public class DialogoVenta extends JDialog {

    private JTable tablaVenta;
    private DefaultTableModel modeloVenta;
    private JTextField txtNombreProducto;
    private JSpinner spinnerCantidad;
    private JLabel lblTotal;
    private double totalVenta = 0;
    private List<detalleVenta> items = new ArrayList<>();

    public DialogoVenta(JFrame parent) {
        super(parent, "Nueva Venta", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // PANEL SUPERIOR - AGREGAR PRODUCTOS
        JPanel panelAgregar = new JPanel(new GridLayout(3, 2, 10, 10));
        panelAgregar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelAgregar.setBackground(Colores.FONDO);

        // NOMBRE PRODUCTO
        JLabel lblProducto = new JLabel("Producto:");
        lblProducto.setForeground(Colores.TEXTO);
        panelAgregar.add(lblProducto);

        txtNombreProducto = new JTextField();
        txtNombreProducto.setBackground(Colores.CAMPO);
        panelAgregar.add(txtNombreProducto);

        // CANTIDAD
        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setForeground(Colores.TEXTO);
        panelAgregar.add(lblCantidad);

        spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        panelAgregar.add(spinnerCantidad);

        // BOTÃ“N AGREGAR
        JButton btnAgregar = new BotonRedondo("AGREGAR", 20);
        btnAgregar.addActionListener(e -> agregarProducto());
        panelAgregar.add(btnAgregar);

        add(panelAgregar, BorderLayout.NORTH);

        // TABLA CENTRAL
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panelTabla.setBackground(Colores.FONDO);

        String[] columnas = {"Producto", "Cantidad", "Precio Unit.", "Subtotal"};
        modeloVenta = new DefaultTableModel(columnas, 0);
        tablaVenta = new JTable(modeloVenta);
        tablaVenta.setBackground(Colores.CAMPO);
        tablaVenta.setForeground(Colores.TEXTO);
        tablaVenta.getTableHeader().setBackground(new Color(36, 26, 20));
        tablaVenta.getTableHeader().setForeground(Color.WHITE);
        tablaVenta.setRowHeight(20);

        JScrollPane scroll = new JScrollPane(tablaVenta);
        panelTabla.add(scroll, BorderLayout.CENTER);

        add(panelTabla, BorderLayout.CENTER);

        // PANEL INFERIOR - TOTAL Y BOTONES
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelInferior.setBackground(Colores.FONDO);

        JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelTotal.setBackground(Colores.FONDO);
        JLabel lblTotalLabel = new JLabel("Total a Pagar: ");
        lblTotalLabel.setForeground(Colores.TEXTO);
        lblTotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotal = new JLabel("$0.00");
        lblTotal.setForeground(Colores.BOTON_SELECT);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        panelTotal.add(lblTotalLabel);
        panelTotal.add(lblTotal);

        panelInferior.add(panelTotal, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 10, 0));
        panelBotones.setBackground(Colores.FONDO);

        JButton btnCancelar = new BotonRedondo("CANCELAR", 20);
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnCancelar);

        JButton btnFinalizar = new BotonRedondo("FINALIZAR", 20);
        btnFinalizar.addActionListener(e -> finalizarVenta());
        panelBotones.add(btnFinalizar);

        panelInferior.add(panelBotones, BorderLayout.CENTER);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void agregarProducto() {
        String nombreProducto = txtNombreProducto.getText().trim();
        int cantidad = (int) spinnerCantidad.getValue();

        if (nombreProducto.isEmpty() || cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "Ingresa un producto vÃ¡lido y cantidad mayor a 0", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = conexionDb.getConexion();
            String sql = "SELECT id_producto, precio FROM producto WHERE nombre_producto = ? AND estado = true";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombreProducto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idProducto = rs.getInt("id_producto");
                double precioUnitario = rs.getDouble("precio");
                double subtotal = precioUnitario * cantidad;

                // Agregar a tabla
                modeloVenta.addRow(new Object[]{
                    nombreProducto,
                    cantidad,
                    "$" + String.format("%.2f", precioUnitario),
                    "$" + String.format("%.2f", subtotal)
                });

                // Agregar a items
                detalleVenta detalle = new detalleVenta(idProducto, nombreProducto, cantidad, precioUnitario);
                items.add(detalle);

                // Actualizar total
                totalVenta += subtotal;
                lblTotal.setText("$" + String.format("%.2f", totalVenta));

                // Limpiar campos
                txtNombreProducto.setText("");
                spinnerCantidad.setValue(1);
                txtNombreProducto.requestFocus();

            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado o no disponible", "Error", JOptionPane.WARNING_MESSAGE);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al buscar producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void finalizarVenta() {
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Agrega al menos un producto a la venta", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = conexionDb.getConexion();

            // Crear venta
            String sqlVenta = "INSERT INTO venta(ven_total, ven_cantidad_productos, ven_fecha, ven_estado) VALUES(?, ?, NOW(), true)";
            PreparedStatement stmtVenta = conn.prepareStatement(sqlVenta, PreparedStatement.RETURN_GENERATED_KEYS);
            stmtVenta.setDouble(1, totalVenta);
            stmtVenta.setInt(2, items.size());
            stmtVenta.executeUpdate();

            ResultSet rsKeys = stmtVenta.getGeneratedKeys();
            int idVenta = 0;
            if (rsKeys.next()) {
                idVenta = rsKeys.getInt(1);
            }
            rsKeys.close();
            stmtVenta.close();

            // Guardar detalles
            String sqlDetalle = "INSERT INTO detalleVenta(id_venta, producto_id, cantidad, precio_unitario) VALUES(?, ?, ?, ?)";
            PreparedStatement stmtDetalle = conn.prepareStatement(sqlDetalle);

            for (detalleVenta item : items) {
                stmtDetalle.setInt(1, idVenta);
                stmtDetalle.setInt(2, item.getProducto_id());
                stmtDetalle.setInt(3, item.getCantidad());
                stmtDetalle.setDouble(4, item.getPrecio_unitario());
                stmtDetalle.addBatch();
            }
            stmtDetalle.executeBatch();
            stmtDetalle.close();

            conn.close();

                JOptionPane.showMessageDialog(this,
                    "Venta registrada exitosamente\nTotal: $" + String.format("%.2f", totalVenta),
                    "Ã‰xito",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar la venta:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

