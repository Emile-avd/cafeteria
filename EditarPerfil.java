package vista;

import util.*;
import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class EditarPerfil extends JFrame {

    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JPasswordField txtPassword;
    private JLabel lblRol;
    private usuario usuarioActual;

    public EditarPerfil() {

        setTitle("Editar Perfil");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Obtener usuario actual
        usuarioActual = SessionManager.getUsuarioActual();
        if (usuarioActual == null) {
            JOptionPane.showMessageDialog(this, "No hay usuario autenticado", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // PANEL LATERAL
        PanelBotones panelBotones = new PanelBotones(PanelBotones.MENU);
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
        panelPrincipal.setBackground(Colores.FONDO);

        // ENCABEZADO
        PanelEncabezado panelEncabezado = new PanelEncabezado();
        panelPrincipal.add(panelEncabezado, BorderLayout.NORTH);

        // CONTENIDO
        JPanel panelContenido = crearContenido();
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);

        add(panelPrincipal, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel crearContenido() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO);

        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setPreferredSize(new Dimension(700, 450));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(20, 20, 20, 20);

        fondo.add(panel, gbc);

        // TÃTULO
        JLabel lblTitulo = new JLabel("MI PERFIL");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setBounds(20, 10, 300, 30);
        panel.add(lblTitulo);

        // NOMBRE
        JLabel lblNombre = new JLabel("NOMBRE:");
        lblNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        lblNombre.setBounds(50, 70, 100, 25);
        panel.add(lblNombre);

        txtNombre = new JTextField(usuarioActual.getnombre_usuario());
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNombre.setBounds(200, 70, 300, 30);
        panel.add(txtNombre);

        // CORREO
        JLabel lblCorreo = new JLabel("CORREO:");
        lblCorreo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblCorreo.setBounds(50, 130, 100, 25);
        panel.add(lblCorreo);

        txtCorreo = new JTextField(usuarioActual.getEmail());
        txtCorreo.setFont(new Font("Arial", Font.PLAIN, 14));
        txtCorreo.setBounds(200, 130, 300, 30);
        panel.add(txtCorreo);

        // CONTRASEÃ‘A
        JLabel lblPassword = new JLabel("CONTRASEÃ‘A:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPassword.setBounds(50, 190, 100, 25);
        panel.add(lblPassword);

        txtPassword = new JPasswordField(usuarioActual.getPassw());
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPassword.setBounds(200, 190, 300, 30);
        panel.add(txtPassword);

        // ROL
        JLabel lblRolLabel = new JLabel("ROL:");
        lblRolLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        lblRolLabel.setBounds(50, 250, 100, 25);
        panel.add(lblRolLabel);

        lblRol = new JLabel(usuarioActual.getRol());
        lblRol.setFont(new Font("Arial", Font.PLAIN, 14));
        lblRol.setBounds(200, 250, 300, 25);
        panel.add(lblRol);

        // BOTÃ“N GUARDAR
        BotonRedondo btnGuardar = new BotonRedondo("GUARDAR", 20);
        btnGuardar.setBounds(200, 310, 120, 40);
        btnGuardar.addActionListener(e -> guardarCambios());
        panel.add(btnGuardar);

        // BOTÃ“N CANCELAR
        BotonRedondo btnCancelar = new BotonRedondo("CANCELAR", 20);
        btnCancelar.setBounds(350, 310, 120, 40);
        btnCancelar.addActionListener(e -> dispose());
        panel.add(btnCancelar);

        return fondo;
    }

    private void guardarCambios() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor completa todos los campos", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = conexionDb.getConexion();
            String sql = "UPDATE usuario SET nombre_usuario = ?, email = ?, contraseÃ±a = ? WHERE id_usuario = ?";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, nombre);
            pst.setString(2, correo);
            pst.setString(3, password);
            pst.setInt(4, usuarioActual.getId());

            int resultado = pst.executeUpdate();

            if (resultado > 0) {
                // Actualizar el usuario en SessionManager
                usuarioActual.setnombre_usuario(nombre);
                usuarioActual.setEmail(correo);
                usuarioActual.setPassw(password);
                SessionManager.setUsuarioActual(usuarioActual);

                JOptionPane.showMessageDialog(this, "Perfil actualizado correctamente", "Ã‰xito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el perfil", "Error", JOptionPane.ERROR_MESSAGE);
            }

            pst.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar cambios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            SessionManager.cerrarSesion();
            new Login().setVisible(true);
            dispose();
        }
    }
}

