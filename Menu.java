package vista;

import util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Menu extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private productosCRUD productoCRUD;
    private java.util.Map<Integer, Integer> filaIdMap;
    private PanelBotones panelBotones;
    private final boolean esAdmin;

    public Menu() {

        setTitle("MenÃº");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        esAdmin = SessionManager.esAdministrador();

        productoCRUD = new productosCRUD();
        filaIdMap = new java.util.HashMap<>();

        // PANEL IZQUIERDO

        panelBotones = new PanelBotones(PanelBotones.MENU);
        
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

        // PANEL DERECHO

        JPanel panelDerecho =
                new JPanel(
                        new BorderLayout()
                );

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

        cargarProductosEnTabla();
        setVisible(true);
    }

    private JPanel crearContenido() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Colores.FONDO);

        // PANEL SUPERIOR CON BOTÃ“N AGREGAR

        JPanel panelBotonAgregar = new JPanel();
        panelBotonAgregar.setBackground(Colores.FONDO);
        panelBotonAgregar.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelBotonAgregar.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        if (esAdmin) {
            JButton btnAgregar = new BotonRedondo("+ AGREGAR PRODUCTO", 20);
            btnAgregar.setBackground(new Color(36, 26, 20));
            btnAgregar.setForeground(Color.WHITE);
            btnAgregar.addActionListener(e -> {
                new AgregarProductoDialog(Menu.this, productoCRUD, () -> {
                    modelo.setRowCount(0);
                    filaIdMap.clear();
                    cargarProductosEnTabla();
                }).setVisible(true);
            });
            panelBotonAgregar.add(btnAgregar);
        } else {
            JLabel lblSoloConsulta = new JLabel("Modo cajero: solo consulta de productos");
            lblSoloConsulta.setFont(new Font("SansSerif", Font.BOLD, 13));
            lblSoloConsulta.setForeground(new Color(120, 90, 60));
            panelBotonAgregar.add(lblSoloConsulta);
        }
        panelPrincipal.add(panelBotonAgregar, BorderLayout.NORTH);

        // PANEL CON TABLA

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Colores.FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));

        String columnas[];
        if (esAdmin) {
            columnas = new String[]{
                    "Imagen",
                    "Producto",
                    "DescripciÃ³n",
                    "Precio",
                    "Editar",
                    "Eliminar"
            };
        } else {
            columnas = new String[]{
                    "Imagen",
                    "Producto",
                    "DescripciÃ³n",
                    "Precio"
            };
        }

        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (!esAdmin) {
                    return false;
                }
                return column == 4 || column == 5;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) {
                    return Icon.class;
                }
                return String.class;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(120);
        tabla.setFont(Fuentes.TEXTO);
        tabla.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 22));
        tabla.getTableHeader().setBackground(new Color(231, 220, 205));
        tabla.setGridColor(new Color(220, 220, 220));
        tabla.setSelectionBackground(new Color(240, 235, 228));
        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);

        // CENTRAR TEXTO
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 1; i < 4; i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centro);
        }

        if (esAdmin) {
            // BOTÃ“N EDITAR
            tabla.getColumnModel().getColumn(4).setCellRenderer(new BotonTablaRenderer("Editar"));
            tabla.getColumnModel().getColumn(4).setCellEditor(new BotonTablaEditor("Editar"));

            // BOTÃ“N ELIMINAR
            tabla.getColumnModel().getColumn(5).setCellRenderer(new BotonTablaRenderer("Eliminar"));
            tabla.getColumnModel().getColumn(5).setCellEditor(new BotonTablaEditor("Eliminar"));
        }

        // TAMAÃ‘O DE COLUMNAS
        tabla.getColumnModel().getColumn(0).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(180);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(180);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(90);
        if (esAdmin) {
            tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(100);
        }

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(225, 225, 225)));

        panel.add(scroll, BorderLayout.CENTER);
        panelPrincipal.add(panel, BorderLayout.CENTER);

        return panelPrincipal;
    }

    private void cargarProductosEnTabla() {
        try {
            String sql = "SELECT * FROM producto";
            Connection conn = conexionDb.getConexion();
            if (conn == null) return;

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            int fila = 0;
            while (rs.next()) {
                int idProducto = rs.getInt("id_producto");
                String nombre = rs.getString("nombre_producto");
                double precio = rs.getDouble("precio");

                if (esAdmin) {
                    modelo.addRow(new Object[]{
                        redimensionar("img/latte.png"),
                        nombre,
                        "Estado: " + (rs.getBoolean("estado") ? "Activo" : "Inactivo"),
                        "$" + String.format("%.2f", precio),
                        "Editar",
                        "Eliminar"
                    });
                } else {
                    modelo.addRow(new Object[]{
                        redimensionar("img/latte.png"),
                        nombre,
                        "Estado: " + (rs.getBoolean("estado") ? "Activo" : "Inactivo"),
                        "$" + String.format("%.2f", precio)
                    });
                }

                filaIdMap.put(fila, idProducto);
                fila++;
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Error al cargar productos: " + e.getMessage());
        }
    }

    private ImageIcon redimensionar(String ruta) {
        ImageIcon icono = new ImageIcon(ruta);
        Image img = icono.getImage().getScaledInstance(85, 85, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // RENDERIZADOR DE BOTONES
    class BotonTablaRenderer extends JButton implements javax.swing.table.TableCellRenderer {

        public BotonTablaRenderer(String texto) {
            setText(texto);
            setFont(new Font("SansSerif", Font.BOLD, 14));
            setFocusPainted(false);
            setBorderPainted(false);
            setBackground(new Color(36, 26, 20));
            setForeground(Color.WHITE);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
        ) {
            return this;
        }
    }

    // EDITOR DE BOTONES
    class BotonTablaEditor extends DefaultCellEditor {

        private JButton boton;
        private String texto;

        public BotonTablaEditor(String texto) {
            super(new JCheckBox());
            this.texto = texto;

            boton = new JButton(texto);
            boton.setFont(new Font("SansSerif", Font.BOLD, 14));
            boton.setFocusPainted(false);
            boton.setBorderPainted(false);
            boton.setBackground(new Color(36, 26, 20));
            boton.setForeground(Color.WHITE);

            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = tabla.getSelectedRow();
                    Integer idProducto = filaIdMap.get(row);

                    if (texto.equals("Editar") && idProducto != null) {
                        producto prod = productoCRUD.obtenerProductoPorId(idProducto);
                        new EditarProductoDialog(Menu.this, productoCRUD, prod, () -> {
                            modelo.setRowCount(0);
                            filaIdMap.clear();
                            cargarProductosEnTabla();
                        }).setVisible(true);
                    } else if (texto.equals("Eliminar") && idProducto != null) {
                        int respuesta = JOptionPane.showConfirmDialog(
                                Menu.this,
                                "Â¿EstÃ¡s seguro de que deseas eliminar el producto?",
                                "Eliminar producto",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (respuesta == JOptionPane.YES_OPTION) {
                            productoCRUD.eliminarProducto(idProducto);
                            JOptionPane.showMessageDialog(Menu.this, "Producto eliminado correctamente.");
                            modelo.setRowCount(0);
                            filaIdMap.clear();
                            cargarProductosEnTabla();
                        }
                    }

                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable table, Object value, boolean isSelected, int row, int column
        ) {
            return boton;
        }

        @Override
        public Object getCellEditorValue() {
            return texto;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Menu();
        });
    }
}

