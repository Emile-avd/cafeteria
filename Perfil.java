package vista;

import util.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Perfil extends JFrame {

    public Perfil() {

        setTitle("Mi Perfil");
        setSize(900,550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

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
            new Login().setVisible(true);
            dispose();
        });

        add(panelBotones, BorderLayout.WEST);

        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(Colores.FONDO);

        panelDerecho.add(new PanelEncabezado("Andrea PeÃ±a"), BorderLayout.NORTH);
        panelDerecho.add(crearContenido(), BorderLayout.CENTER);

        add(panelDerecho, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel crearContenido() {

    JPanel fondo = new JPanel(new GridBagLayout());
    fondo.setBackground(Colores.FONDO);

    JPanel panel = new JPanel(null);
    panel.setBackground(Color.WHITE);
    panel.setBorder(new LineBorder(new Color(225, 225, 225)));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.insets = new Insets(20, 20, 20, 20);

    fondo.add(panel, gbc);

    // TÃTULO

    JPanel titulo = new JPanel();
    titulo.setBounds(0, 0, 620, 40);
    titulo.setBackground(Colores.FONDO);

    JLabel lblTitulo = new JLabel("MI PERFIL");
    lblTitulo.setFont(Fuentes.TEXTO);

    titulo.add(lblTitulo);
    panel.add(titulo);

    // FOTO

    ImageIcon icon = new ImageIcon("img/perfil.jpg");
    Image img = icon.getImage().getScaledInstance(160,190,Image.SCALE_SMOOTH);

    JLabel foto = new JLabel(new ImageIcon(img));
    foto.setBounds(30, 70, 160, 190);

    panel.add(foto);


    // NOMBRE

    JLabel l1 = new JLabel("NOMBRE:");
    l1.setFont(Fuentes.TEXTO);
    l1.setBounds(230, 70, 140, 30);

    JLabel d1 = new JLabel("Andrea PeÃ±a");
    d1.setFont(Fuentes.TEXTO);
    d1.setBounds(390, 70, 220, 30);

    // CONTRASEÃ‘A

    JLabel l2 = new JLabel("CONTRASEÃ‘A:");
    l2.setFont(Fuentes.TEXTO);
    l2.setBounds(230, 125, 180, 30);

    JLabel d2 = new JLabel("********");
    d2.setFont(Fuentes.TEXTO);
    d2.setBounds(430, 125, 120, 30);

    // CORREO

    JLabel l3 = new JLabel("CORREO:");
    l3.setFont(Fuentes.TEXTO);
    l3.setBounds(230, 180, 150, 30);

    JLabel d3 = new JLabel("andreapena@gmail.com");
    d3.setFont(Fuentes.TEXTO);
    d3.setBounds(380, 180, 240, 30);

    // ROL

    JLabel l4 = new JLabel("ROL:");
    l4.setFont(Fuentes.TEXTO);
    l4.setBounds(230, 235, 120, 30);

    JLabel d4 = new JLabel("Administrador");
    d4.setFont(Fuentes.TEXTO);
    d4.setBounds(380, 235, 180, 30);

    // AGREGAR COMPONENTES

    panel.add(l1);
    panel.add(d1);

    panel.add(l2);
    panel.add(d2);

    panel.add(l3);
    panel.add(d3);

    panel.add(l4);
    panel.add(d4);

    // BOTÃ“N

    BotonRedondo editar = new BotonRedondo("Editar perfil", 20);
    editar.setBounds(220, 330, 180, 42);

    panel.add(editar);

    return fondo;
}
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new Perfil();

        });

    }

}
