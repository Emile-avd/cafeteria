package vista;

import java.awt.*;
import javax.swing.*;

import util.*;

public class Login extends JFrame {

    private PanelRedondo panelLogin;

    private JLabel lblTitulo;

    private JLabel lblUsuario;
    private JLabel lblPassword;

    private JLabel lblNoCuenta;

    private JLabel lblImagen;

    private CampoTexto txtUsuario;

    private PasswordCampo txtPassword;

    private BotonRedondo btnEntrar;

    private BotonRedondo btnCrearCuenta;

    public Login(){

        iniciarVentana();

        iniciarComponentes();

        agregarComponentes();

    }

    private void iniciarVentana(){

        setTitle(Constantes.NOMBRE_SISTEMA);

        setSize(
                Constantes.ANCHO_VENTANA,
                Constantes.ALTO_VENTANA);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        

        getContentPane().setBackground(
                Colores.FONDO);

        setLayout(null);

    }

    
    private void iniciarComponentes(){

        panelLogin = new PanelRedondo(40);

        panelLogin.setLayout(null);

        panelLogin.setBackground(Color.WHITE);

        panelLogin.setBounds(
                250,
                30,
                400,
                470);

        lblTitulo = new JLabel("Iniciar sesi\u00f3n");

        lblTitulo.setFont(Fuentes.TITULO);

        lblTitulo.setForeground(Colores.TEXTO);

        lblTitulo.setBounds(
                45,
                30,
                310,
                40);

                        lblImagen = new JLabel();

        ImageIcon icono = new ImageIcon("src/img/usuario.png");

        lblImagen.setIcon(icono);

        lblImagen.setHorizontalAlignment(
                SwingConstants.CENTER);

        lblImagen.setBounds(
                125,
                20,
                150,
                110);

        lblUsuario = new JLabel("Usuario");

        lblUsuario.setFont(Fuentes.TEXTO);

        lblUsuario.setForeground(Colores.TEXTO);

        lblUsuario.setBounds(
                45,
                115,
                100,
                20);

        txtUsuario = new CampoTexto(30);

        txtUsuario.setBounds(
                45,
                140,
                310,
                45);

        lblPassword = new JLabel("Contrase\u00f1a");

        lblPassword.setFont(Fuentes.TEXTO);

        lblPassword.setForeground(Colores.TEXTO);

        lblPassword.setBounds(
                45,
                200,
                120,
                20);

        txtPassword = new PasswordCampo(30);

        txtPassword.setBounds(
                45,
                225,
                310,
                45);

        btnEntrar = new BotonRedondo(
                "Entrar",
                30);

        btnEntrar.setBounds(
                45,
                280,
                310,
                45);

        lblNoCuenta = new JLabel(
                "\u00bfNo tienes cuenta?");

        lblNoCuenta.setFont(Fuentes.TEXTO);

        lblNoCuenta.setForeground(Colores.TEXTO);

        lblNoCuenta.setHorizontalAlignment(
                SwingConstants.CENTER);

        lblNoCuenta.setBounds(60, 335, 280, 20);

        btnCrearCuenta = new BotonRedondo(
                "Crear cuenta",
                30);

        btnCrearCuenta.setBounds(
                45,
                365,
                310,
                45);

    }
        private void agregarComponentes() {

        panelLogin.add(lblImagen);

        panelLogin.add(lblTitulo);

        panelLogin.add(lblUsuario);
        panelLogin.add(txtUsuario);

        panelLogin.add(lblPassword);
        panelLogin.add(txtPassword);

        panelLogin.add(btnEntrar);

        panelLogin.add(lblNoCuenta);
        panelLogin.add(btnCrearCuenta);

        add(panelLogin);

        btnCrearCuenta.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                new Registro().setVisible(true);
                dispose();
            }
        });

        btnEntrar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String nombre = txtUsuario.getText();
                String passw = new String(txtPassword.getPassword());

                if (nombre == null || nombre.trim().isEmpty() || passw == null || passw.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            Login.this,
                            "Por favor ingresa usuario y contrase\u00f1a.",
                            "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                usuario usuario = mains.validarCredenciales(nombre, passw);
                if (usuario != null) {
                    util.SessionManager.setUsuarioActual(usuario);
                                        if (util.SessionManager.esAdministrador()) {
                                                new Inicio().setVisible(true);
                                                dispose();
                                        } else if (util.SessionManager.esCajero()) {
                                                new Inicio().setVisible(true);
                                                dispose();
                                        } else {
                                                JOptionPane.showMessageDialog(
                                                                Login.this,
                                                                "El rol del usuario no es valido. Verifica que sea administrador o cajero.",
                                                                "Rol no permitido", JOptionPane.ERROR_MESSAGE);
                                                util.SessionManager.cerrarSesion();
                                        }
                } else {
                    JOptionPane.showMessageDialog(
                            Login.this,
                            "Usuario o contrase\u00f1a incorrectos.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

}
