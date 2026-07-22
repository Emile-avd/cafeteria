package vista;

import util.*;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AgregarProductoDialog extends JDialog {

    private CampoTexto txtNombre;
    private CampoTexto txtPrecio;
    private JCheckBox chkEstado;
    private productosCRUD productoCRUD;
    private Runnable onProductoAgregado;

    public AgregarProductoDialog(JFrame parent, productosCRUD productoCRUD, Runnable onProductoAgregado) {
        super(parent, "AGREGAR NUEVO PRODUCTO", true);

        this.productoCRUD = productoCRUD;
        this.onProductoAgregado = onProductoAgregado;

        setSize(550, 450);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(Colores.FONDO);

        // PANEL PRINCIPAL

        JPanel panelContenido = new JPanel();
        panelContenido.setBackground(Colores.FONDO);
        panelContenido.setLayout(new GridBagLayout());
        panelContenido.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // TÃTULO DEL FORMULARIO

        JLabel lblTitulo = new JLabel("Formulario de Nuevo Producto");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(36, 26, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 10, 30, 10);
        panelContenido.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(15, 10, 15, 10);

        // ETIQUETA Y CAMPO NOMBRE

        JLabel lblNombre = new JLabel("Nombre del Producto *");
        lblNombre.setFont(Fuentes.TEXTO);
        lblNombre.setForeground(Colores.TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.4;
        panelContenido.add(lblNombre, gbc);

        txtNombre = new CampoTexto(30);
        txtNombre.setFont(Fuentes.TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        panelContenido.add(txtNombre, gbc);

        gbc.gridwidth = 1;

        // ETIQUETA Y CAMPO PRECIO

        JLabel lblPrecio = new JLabel("Precio ($) *");
        lblPrecio.setFont(Fuentes.TEXTO);
        lblPrecio.setForeground(Colores.TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.4;
        panelContenido.add(lblPrecio, gbc);

        txtPrecio = new CampoTexto(30);
        txtPrecio.setFont(Fuentes.TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        panelContenido.add(txtPrecio, gbc);

        gbc.gridwidth = 1;

        // CHECKBOX ESTADO

        chkEstado = new JCheckBox("Producto Activo");
        chkEstado.setSelected(true);
        chkEstado.setFont(Fuentes.TEXTO);
        chkEstado.setBackground(Colores.FONDO);
        chkEstado.setForeground(Colores.TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 10, 15, 10);
        panelContenido.add(chkEstado, gbc);

        // ETIQUETA DE CAMPOS REQUERIDOS

        JLabel lblRequeridos = new JLabel("* Campos requeridos");
        lblRequeridos.setFont(new Font("Arial", Font.ITALIC, 11));
        lblRequeridos.setForeground(new Color(150, 150, 150));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 10, 15, 10);
        panelContenido.add(lblRequeridos, gbc);

        // PANEL DE BOTONES

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Colores.FONDO);
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnGuardar = new BotonRedondo("GUARDAR", 20);
        btnGuardar.setBackground(new Color(36, 26, 20));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setPreferredSize(new Dimension(120, 40));
        btnGuardar.addActionListener(e -> guardarProducto());

        JButton btnCancelar = new BotonRedondo("CANCELAR", 20);
        btnCancelar.setBackground(new Color(150, 150, 150));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setPreferredSize(new Dimension(120, 40));
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelContenido, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void guardarProducto() {
        if (validarCampos()) {
            try {
                String nombre = txtNombre.getText().trim();
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                boolean estado = chkEstado.isSelected();

                // Verificar que no exista un producto con el mismo nombre
                if (productoExiste(nombre)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Ya existe un producto con el nombre \"" + nombre + "\"",
                            "Producto duplicado",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                // Crear el objeto producto y agregarlo usando el CRUD
                producto nuevoProducto = new producto(nombre, precio, estado);
                productoCRUD.insertarProducto(nuevoProducto);

                JOptionPane.showMessageDialog(
                        this,
                        "Producto \"" + nombre + "\" agregado correctamente",
                        "Ã‰xito",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Limpiar campos
                limpiarCampos();

                // Ejecutar callback para actualizar tabla
                if (onProductoAgregado != null) {
                    onProductoAgregado.run();
                }

                dispose();

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "El precio debe ser un nÃºmero vÃ¡lido (ej: 25.50)",
                        "Formato de precio invÃ¡lido",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private boolean validarCampos() {
        String nombre = txtNombre.getText().trim();
        String precio = txtPrecio.getText().trim();

        // Validar nombre
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "El nombre del producto es requerido",
                    "Campo vacÃ­o",
                    JOptionPane.WARNING_MESSAGE
            );
            txtNombre.requestFocus();
            return false;
        }

        if (nombre.length() < 3) {
            JOptionPane.showMessageDialog(
                    this,
                    "El nombre del producto debe tener al menos 3 caracteres",
                    "Nombre muy corto",
                    JOptionPane.WARNING_MESSAGE
            );
            txtNombre.requestFocus();
            return false;
        }

        // Validar precio
        if (precio.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "El precio es requerido",
                    "Campo vacÃ­o",
                    JOptionPane.WARNING_MESSAGE
            );
            txtPrecio.requestFocus();
            return false;
        }

        try {
            double precioDouble = Double.parseDouble(precio);
            if (precioDouble <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "El precio debe ser mayor a 0",
                        "Precio invÃ¡lido",
                        JOptionPane.WARNING_MESSAGE
                );
                txtPrecio.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "El precio debe ser un nÃºmero vÃ¡lido (ej: 25.50)",
                    "Formato invÃ¡lido",
                    JOptionPane.WARNING_MESSAGE
            );
            txtPrecio.requestFocus();
            return false;
        }

        return true;
    }

    private boolean productoExiste(String nombre) {
        try {
            String sql = "SELECT COUNT(*) FROM producto WHERE LOWER(nombre_producto) = LOWER(?)";
            Connection conn = conexionDb.getConexion();
            if (conn == null) return false;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            boolean existe = false;
            if (rs.next()) {
                int count = rs.getInt(1);
                existe = count > 0;
            }

            rs.close();
            stmt.close();
            conn.close();

            return existe;

        } catch (Exception e) {
            System.out.println("Error al verificar producto: " + e.getMessage());
        }

        return false;
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtPrecio.setText("");
        chkEstado.setSelected(true);
    }
}

