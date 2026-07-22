package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import util.BotonRedondo;
import util.Colores;

public class TicketPreviewDialog extends JDialog {

    private final JPanel panelTicket;

    public TicketPreviewDialog(Window owner, int idVenta, Date fechaVenta, List<detalleVenta> items, double totalVenta) {
        super(owner, "Vista previa del ticket", ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));
        getContentPane().setBackground(Colores.FONDO);

        panelTicket = construirPanelTicket(idVenta, fechaVenta, items, totalVenta);
        JScrollPane scroll = new JScrollPane(panelTicket);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotones.setBackground(Colores.FONDO);

        BotonRedondo btnImprimir = new BotonRedondo("IMPRIMIR / GUARDAR PDF", 20);
        btnImprimir.addActionListener(e -> imprimirTicket());

        BotonRedondo btnCerrar = new BotonRedondo("CERRAR", 20);
        btnCerrar.addActionListener(e -> dispose());

        panelBotones.add(btnImprimir);
        panelBotones.add(btnCerrar);
        add(panelBotones, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(640, 680));
        pack();
        setLocationRelativeTo(owner);
    }

    private JPanel construirPanelTicket(int idVenta, Date fechaVenta, List<detalleVenta> items, double totalVenta) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));

        JLabel titulo = new JLabel("CAFETERIA AROMA");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(58, 38, 27));

        JLabel subtitulo = new JLabel("Ticket de venta");
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitulo.setForeground(new Color(90, 90, 90));

        String fechaTexto = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format((fechaVenta != null) ? fechaVenta : new Date());
        JLabel meta = new JLabel("Ticket #" + ((idVenta > 0) ? idVenta : "N/A") + "  |  " + fechaTexto, SwingConstants.CENTER);
        meta.setAlignmentX(Component.CENTER_ALIGNMENT);
        meta.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        meta.setForeground(new Color(110, 110, 110));

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(4));
        panel.add(subtitulo);
        panel.add(Box.createVerticalStrut(6));
        panel.add(meta);
        panel.add(Box.createVerticalStrut(16));

        String[] columnas = {"Producto", "Cant", "P.Unit", "Subtotal"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        int cantidadProductos = 0;
        if (items != null) {
            for (detalleVenta item : items) {
                if (item == null) {
                    continue;
                }
                cantidadProductos += item.getCantidad();
                modelo.addRow(new Object[] {
                        item.getNombre_producto(),
                        item.getCantidad(),
                        "$" + String.format("%.2f", item.getPrecio_unitario()),
                        "$" + String.format("%.2f", item.getSubtotal())
                });
            }
        }

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(24);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(new Color(245, 236, 225));
        tabla.getTableHeader().setForeground(new Color(48, 34, 23));
        tabla.setBackground(Color.WHITE);
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(235, 235, 235), 1));
        scrollTabla.setPreferredSize(new Dimension(580, 300));
        scrollTabla.setMaximumSize(new Dimension(Integer.MAX_VALUE, 320));

        panel.add(scrollTabla);
        panel.add(Box.createVerticalStrut(14));

        JPanel resumen = new JPanel(new BorderLayout());
        resumen.setBackground(new Color(250, 247, 241));
        resumen.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel lblCantidad = new JLabel("Productos vendidos: " + cantidadProductos);
        lblCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JLabel lblTotal = new JLabel("TOTAL: $" + String.format("%.2f", totalVenta), SwingConstants.RIGHT);
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotal.setForeground(new Color(87, 48, 24));

        resumen.add(lblCantidad, BorderLayout.WEST);
        resumen.add(lblTotal, BorderLayout.EAST);

        panel.add(resumen);
        panel.add(Box.createVerticalStrut(10));

        JLabel gracias = new JLabel("Gracias por tu compra", SwingConstants.CENTER);
        gracias.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        gracias.setForeground(new Color(100, 100, 100));
        gracias.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(gracias);
        return panel;
    }

    private void imprimirTicket() {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setJobName("Ticket de venta");

        printerJob.setPrintable(new Printable() {
            @Override
            public int print(java.awt.Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return NO_SUCH_PAGE;
                }

                java.awt.Graphics2D g2d = (java.awt.Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                double widthScale = pageFormat.getImageableWidth() / panelTicket.getWidth();
                double heightScale = pageFormat.getImageableHeight() / panelTicket.getHeight();
                double scale = Math.min(widthScale, heightScale);

                g2d.scale(scale, scale);
                panelTicket.printAll(g2d);
                return PAGE_EXISTS;
            }
        });

        if (printerJob.printDialog()) {
            try {
                printerJob.print();
                JOptionPane.showMessageDialog(this,
                        "Listo. Si elegiste Microsoft Print to PDF, podras guardar el ticket como archivo PDF.",
                        "Impresion enviada",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this,
                        "No se pudo imprimir el ticket: " + ex.getMessage(),
                        "Error de impresion",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

