
package vista;

import java.awt.*;
import javax.swing.*;
import util.BotonRedondo;

public class Dashboard extends JFrame {

    private JPanel menu;
    private JPanel contenido;

    public Dashboard() {

        setTitle("El RincÃ³n del CafÃ©");
        setSize(1000,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setLayout(null);


        //---------------- MENU ----------------

        menu = new JPanel();
        menu.setBounds(0,0,200,700);
        menu.setBackground(new Color(37,27,20));
        menu.setLayout(null);


        JLabel logo = new JLabel("LOGO");

        logo.setBounds(60,40,100,40);
        logo.setForeground(new Color(212,153,78));
        logo.setFont(new Font("Arial",Font.BOLD,22));

        menu.add(logo);



        // BOTONES REDONDOS

        BotonRedondo inicio = crearBoton("INICIO");
        inicio.setBounds(20,150,160,45);


        BotonRedondo venta = crearBoton("PUNTO DE VENTA");
        venta.setBounds(20,220,160,45);


        BotonRedondo menuBtn = crearBoton("MENÃš");
        menuBtn.setBounds(20,290,160,45);


        BotonRedondo historial = crearBoton("HISTORIAL");
        historial.setBounds(20,360,160,45);


        BotonRedondo salir = crearBoton("CERRAR SESIÃ“N");
        salir.setBounds(20,560,160,45);



        menu.add(inicio);
        menu.add(venta);
        menu.add(menuBtn);
        menu.add(historial);
        menu.add(salir);

        inicio.addActionListener(e -> {
            new Dashboard().setVisible(true);
            dispose();
        });

        venta.addActionListener(e -> {
            new PuntoVenta().setVisible(true);
            dispose();
        });

        menuBtn.addActionListener(e -> {
            new Menu().setVisible(true);
            dispose();
        });

        historial.addActionListener(e -> {
            new HistorialVista().setVisible(true);
            dispose();
        });

        salir.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });



        //---------------- CONTENIDO ----------------


        contenido = new JPanel();

        contenido.setBounds(200,0,800,700);
        contenido.setLayout(null);
        contenido.setBackground(new Color(245,241,236));



        JLabel titulo = new JLabel("EL RINCÃ“N DEL CAFÃ‰");

        titulo.setBounds(170,30,450,40);
        titulo.setFont(new Font("Serif",Font.BOLD,32));

        contenido.add(titulo);



        JLabel usuario = new JLabel("USUARIO");

        usuario.setBounds(650,30,100,30);
        usuario.setFont(new Font("Arial",Font.BOLD,16));

        contenido.add(usuario);



        //---------------- TARJETA VENTAS ----------------


        JPanel ventas = tarjeta();

        ventas.setBounds(40,120,180,170);



        JLabel v1 = new JLabel("Ventas",SwingConstants.CENTER);
        v1.setBounds(0,35,180,25);


        JLabel v2 = new JLabel("realizadas",SwingConstants.CENTER);
        v2.setBounds(0,65,180,25);


        JLabel v3 = new JLabel("50",SwingConstants.CENTER);
        v3.setBounds(0,120,180,30);
        v3.setFont(new Font("Arial",Font.BOLD,28));



        ventas.add(v1);
        ventas.add(v2);
        ventas.add(v3);

        contenido.add(ventas);



        //---------------- INGRESOS ----------------


        JPanel ingresos = tarjeta();

        ingresos.setBounds(280,120,180,170);



        JLabel i1 = new JLabel("Ingresos",SwingConstants.CENTER);
        i1.setBounds(0,35,180,25);


        JLabel i2 = new JLabel("Totales",SwingConstants.CENTER);
        i2.setBounds(0,65,180,25);


        JLabel i3 = new JLabel("$5,000",SwingConstants.CENTER);
        i3.setBounds(0,120,180,30);
        i3.setFont(new Font("Arial",Font.BOLD,28));



        ingresos.add(i1);
        ingresos.add(i2);
        ingresos.add(i3);


        contenido.add(ingresos);



        //---------------- PRODUCTOS ----------------


        JPanel productos = tarjeta();

        productos.setBounds(520,120,180,170);



        JLabel p1 = new JLabel("Productos",SwingConstants.CENTER);
        p1.setBounds(0,25,180,20);


        JLabel p2 = new JLabel("mÃ¡s",SwingConstants.CENTER);
        p2.setBounds(0,55,180,20);


        JLabel p3 = new JLabel("vendidos",SwingConstants.CENTER);
        p3.setBounds(0,85,180,20);



        productos.add(p1);
        productos.add(p2);
        productos.add(p3);


        contenido.add(productos);



        //---------------- GRAFICA ----------------


        JPanel grafica = new JPanel(){


            @Override
            protected void paintComponent(Graphics g){

                super.paintComponent(g);


                g.setColor(Color.WHITE);

                g.setFont(new Font("Serif",Font.BOLD,26));

                g.drawString(
                    "Ventas de los Ãºltimos 7 dÃ­as",
                    120,
                    40
                );


                g.setColor(new Color(212,153,78));


                g.fillRect(220,80,30,100);

                g.fillRect(270,95,30,85);

                g.fillRect(320,70,30,110);

                g.fillRect(370,55,30,125);

                g.fillRect(420,105,30,75);

                g.fillRect(470,92,30,88);


            }

        };


        grafica.setBounds(40,340,660,220);

        grafica.setBackground(new Color(37,27,20));


        contenido.add(grafica);



        add(menu);
        add(contenido);


        setVisible(true);

    }



    // CREAR BOTÃ“N REDONDO

    private BotonRedondo crearBoton(String texto){

        BotonRedondo b = new BotonRedondo(texto,20);

        return b;

    }



    // TARJETAS

    private JPanel tarjeta(){

        JPanel p = new JPanel();

        p.setLayout(null);

        p.setBackground(new Color(234,226,217));

        p.setBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY)
        );


        return p;

    }



    public static void main(String args[]){

        new Dashboard();

    }

}
