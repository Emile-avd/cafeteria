package vista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.*;
import javax.swing.*;
import util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Registro extends JFrame {
    
    

    private PanelRedondo panelRegistro;

    private JLabel lblTitulo;

    private JLabel lblNombre;
    private JLabel lblEmail;
    //private JLabel lblTelefono;
    //private JLabel lblUsuario;
    private JLabel lblPassword;
    private JLabel lblRol;
    //private JLabel lblConfirmar;

    private CampoTexto txtNombre;
    private CampoTexto txtEmail;
    //private CampoTexto txtTelefono;
    //private CampoTexto txtUsuario;

    private PasswordCampo txtPassword;
    private CampoTexto txtRol;
    //private PasswordCampo txtConfirmar;

    private BotonRedondo btnRegistrar;
    private BotonRedondo btnVolver;

    public Registro() {

        iniciarVentana();

        iniciarComponentes();

        agregarComponentes();

    }

    private void iniciarVentana() {

        setTitle("Crear cuenta");

        setSize(
                Constantes.ANCHO_VENTANA,
                Constantes.ALTO_VENTANA);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);


        getContentPane().setBackground(
                Colores.FONDO);

        setLayout(null);

    }

    private void iniciarComponentes() {

        panelRegistro = new PanelRedondo(40);

        panelRegistro.setLayout(null);

        panelRegistro.setBackground(Color.WHITE);

        panelRegistro.setBounds(
                200,
                20,
                500,
                560);

        lblTitulo = new JLabel("Crear cuenta");

        lblTitulo.setFont(Fuentes.TITULO);

        lblTitulo.setForeground(Colores.TEXTO);

        lblTitulo.setHorizontalAlignment(
                SwingConstants.CENTER);

        lblTitulo.setBounds(
                80,
                30,
                340,
                40);

        
        lblNombre = new JLabel("Nombre");
        lblNombre.setFont(Fuentes.TEXTO);
        lblNombre.setForeground(Colores.TEXTO);
        lblNombre.setBounds(
                50,
                120,
                100,
                20);


        txtNombre = new CampoTexto(30);
        txtNombre.setBounds(
                50,
                145,
                400,
                40);


        lblEmail = new JLabel("Email");
        lblEmail.setFont(Fuentes.TEXTO);
        lblEmail.setForeground(Colores.TEXTO);
        lblEmail.setBounds(
                50,
                190,
                150,
                20);


        txtEmail = new CampoTexto(30);
        txtEmail.setBounds(
                50,
                220,
                400,
                40);


        lblPassword = new JLabel("Contrase\u00f1a");

        lblPassword.setFont(Fuentes.TEXTO);

        lblPassword.setForeground(Colores.TEXTO);

        lblPassword.setBounds(
                50,
                260,
                120,
                20);

        txtPassword = new PasswordCampo(30);

        txtPassword.setBounds(
                50,
                290,
                400,
                40);

        lblRol = new JLabel("Rol");
        lblRol.setFont(Fuentes.TEXTO);
        lblRol.setForeground(Colores.TEXTO);
        lblRol.setBounds(
                50,
                340,
                120,
                20);

        txtRol = new CampoTexto(30);
        txtRol.setBounds(
                50,
                370,
                400,
                40);
        txtRol.setText("Seleccionar");
        txtRol.setToolTipText("Escribe: Cajero o Administrador");

        /*lblConfirmar = new JLabel("Confirmar contrase\u00f1a");

        lblConfirmar.setFont(Fuentes.TEXTO);

        lblConfirmar.setForeground(Colores.TEXTO);

        lblConfirmar.setBounds(
                50,
                420,
                180,
                20);

        txtConfirmar = new PasswordCampo(30);

        txtConfirmar.setBounds(
                50,
                445,
                400,
                40);*/

        btnRegistrar = new BotonRedondo(
                "Crear cuenta",
                30);

        btnRegistrar.setBounds(
                50,
                440,
                190,
                40);

        btnVolver = new BotonRedondo(
                "Iniciar sesi\u00f3n",
                30);

        btnVolver.setBounds(
                260,
                440,
                190,
                40);

    }
        private void agregarComponentes() {

        panelRegistro.add(lblTitulo);

        panelRegistro.add(lblNombre);
        panelRegistro.add(txtNombre);

        panelRegistro.add(lblEmail);
        panelRegistro.add(txtEmail);

        //panelRegistro.add(lblUsuario);
        //panelRegistro.add(txtUsuario);

        panelRegistro.add(lblPassword);
        panelRegistro.add(txtPassword);
        panelRegistro.add(lblRol);
        panelRegistro.add(txtRol);

        //panelRegistro.add(lblConfirmar);
        //panelRegistro.add(txtConfirmar);

        panelRegistro.add(btnRegistrar);
        panelRegistro.add(btnVolver);

        add(panelRegistro);

        btnVolver.addActionListener(e -> {

            new Login().setVisible(true);

            dispose();

        });

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String email = txtEmail.getText();
            String passw = new String(txtPassword.getPassword());
            String rol = txtRol.getText();

            // Validar campos
            if (nombre == null || nombre.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    passw == null || passw.isEmpty() ||
                    rol == null || rol.trim().isEmpty() ||
                    rol.equalsIgnoreCase("Seleccionar")) {
                JOptionPane.showMessageDialog(
                        this,
                        "Por favor, complete todos los campos y seleccione un rol vÃ¡lido (Cajero o Administrador).",
                        "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            usuario nuevoUsuario = new usuario(nombre.trim(), email.trim(), passw, rol.trim());
            usuarioCRUD usuarioCRUD = new usuarioCRUD();

            try {
                usuarioCRUD.insertarUsuario(nuevoUsuario);
                JOptionPane.showMessageDialog(this, "Registro realizado correctamente.");
                new Login().setVisible(true);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al registrar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        /*btnVolver.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        new Login().setVisible(true);
        dispose();
    }
});

btnRegistrar.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(
                Registro.this,
                "Registro realizado correctamente.");
    }
}); */

    }

}
