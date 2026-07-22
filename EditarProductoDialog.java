package vista;

import util.*;

import javax.swing.*;
import java.awt.*;

public class EditarProductoDialog extends JDialog {

    private CampoTexto txtNombre;
    private CampoTexto txtPrecio;
    private JCheckBox chkEstado;
    private producto productoActual;
    private productosCRUD productoCRUD;
    private Runnable onActualizar;

    public EditarProductoDialog(JFrame parent, productosCRUD productoCRUD, producto producto, Runnable onActualizar) {
        super(parent, "EDITAR PRODUCTO", true);

        this.productoCRUD = productoCRUD;
        this.productoActual = producto;
        this.onActualizar = onActualizar;

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

        JLabel lblTitulo = new JLabel("Editar Producto");
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
        txtNombre.setText(producto.getNombre_producto());
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
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        txtPrecio.setFont(Fuentes.TEXTO);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        panelContenido.add(txtPrecio, gbc);

        gbc.gridwidth = 1;

        // CHECKBOX ESTADO

        chkEstado = new JCheckBox("Producto Activo");
        chkEstado.setSelected(producto.getEstado());
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
        btnGuardar.addActionListener(e -> guardarCambios());

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

    private void guardarCambios() {
        if (validarCampos()) {
            try {
                String nombre = txtNombre.getText().trim();
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                boolean estado = chkEstado.isSelected();

                // Actualizar el objeto producto
                productoActual.setNombre_producto(nombre);
                productoActual.setPrecio(precio);
                productoActual.setEstado(estado);

                // Usar el mÃ©todo CRUD para actualizar
                productoCRUD.actualizarProducto(productoActual);

                JOptionPane.showMessageDialog(
                        this,
                        "Producto actualizado correctamente",
                        "Ã‰xito",
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Ejecutar callback para actualizar tabla
                if (onActualizar != null) {
                    onActualizar.run();
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
}

