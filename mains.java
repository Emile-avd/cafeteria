import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class mains {
    private static final Scanner scanner = new Scanner(System.in);
    private static final historial historialVentas = new historial();
    private static final VentasManager ventasManager = new VentasManager(historialVentas);

    public static void main(String[] args) {
        try {
            var conexion = conexionDb.getConexion();

            if (conexion != null && !conexion.isClosed()) {
                System.out.println("Conexion exitosa a la base de datos");

                System.out.println("Bienvenido al sistema de gestion de la cafeteria");
                System.out.println("Inicia sesion con tu nombre y contraseña");
                System.out.println("Ingresa tu nombre: ");
                String nombre = scanner.nextLine();
                System.out.println("Ingresa tu contraseña: ");
                String passw = scanner.nextLine();
                
                boolean loginValido = false;
                if (!nombre.equals("") && !passw.equals("")) {
                    try {
                        PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM usuario WHERE nombre_usuario = ? AND passw = ?");
                        stmt.setString(1, nombre);
                        stmt.setString(2, passw);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            System.out.println("Login exitoso. Bienvenido " + nombre);
                            loginValido = true;
                            mostrarMenuAdministrador();
                        } else {
                            System.out.println("Usuario o contraseña incorrectos");
                        }
                        rs.close();
                        stmt.close();
                    } catch (Exception e) {
                        System.out.println("Error al iniciar sesion: " + e.getMessage());
                    }
                }
                
                if (!loginValido) {
                    System.out.println("No puedes acceder al sistema sin login valido");
                    conexion.close();
                    return;
                }
                conexion.close();
            } else {
                System.out.println("No se pudo realizar la conexion");
            }

        } catch (Exception e) {
            System.out.println("Error al verificar la conexion" + e.getMessage());
        }
    }

    private static void mostrarMenuAdministrador() {
        int opcion;
        do {
            System.out.println("Elige una opcion:");
            System.out.println("1. Gestion de usuarios");
            System.out.println("2. Gestion de productos");
            System.out.println("3. Gestion de ventas");
            System.out.println("4. Salir");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    gestionUsuarios();
                    break;
                case 2:
                    gestionProductos();
                    break;
                case 3:
                    gestionVentas();
                    break;
                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        } while (opcion != 4);
    }

    private static void mostrarMenuCajero() {
        System.out.println("Elige una opcion:");
        System.out.println("1. Gestion de productos");
        System.out.println("2. Salir");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        if (opcion == 1) {
            gestionUsuarios();
        } else if (opcion == 2) {
            gestionProductos();
        } else {
            System.out.println("Opcion invalida");
        }
    }

    private static String pedirRolUsuario() {
        while (true) {
            System.out.println("Selecciona el rol:");
            System.out.println("1. Administrador");
            System.out.println("2. Cajero");
            int opcionRol = scanner.nextInt();
            scanner.nextLine();

            switch (opcionRol) {
                case 1:
                    return "administrador";
                case 2:
                    return "cajero";
                default:
                    System.out.println("Opcion invalida. Intenta de nuevo.");
                    break;
            }
        }
    }

    private static void gestionUsuarios() {
        usuarioCRUD usuarioCRUD = new usuarioCRUD();
        int opcionUsuario = 0;

        do {
            System.out.println("Elige una opcion:");
            System.out.println("1. Insertar usuario");
            System.out.println("2. Eliminar usuario");
            System.out.println("3. Actualizar usuario");
            System.out.println("4. Consultar usuarios");
            System.out.println("5. Salir");
            opcionUsuario = scanner.nextInt();
            scanner.nextLine();

            switch (opcionUsuario) {
                case 1:
                    System.out.println("Ingresa el nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.println("Ingresa el email: ");
                    String email = scanner.nextLine();
                    System.out.println("Ingresa la contraseña: ");
                    String pass = scanner.nextLine();
                    String rol = pedirRolUsuario();
                    usuario nuevoUsuario = new usuario(nombre, email, pass, rol);
                    usuarioCRUD.insertarUsuario(nuevoUsuario);
                    break;
                case 2:
                    usuarioCRUD.listarUsuarios();
                    System.out.println("Ingresa el ID del usuario a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    scanner.nextLine();
                    usuarioCRUD.eliminarUsuario(idEliminar);
                    break;
                case 3:
                    usuarioCRUD.listarUsuarios();
                    System.out.println("Ingresa el ID del usuario a actualizar: ");
                    int idActualizar = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Ingresa el nuevo nombre: ");
                    String nombreActualizado = scanner.nextLine();
                    System.out.println("Ingresa el nuevo email: ");
                    String emailActualizado = scanner.nextLine();
                    System.out.println("Ingresa la nueva contraseña: ");
                    String passActualizado = scanner.nextLine();
                    String rolActualizado = pedirRolUsuario();
                    usuarioCRUD.actualizarUsuario(idActualizar, nombreActualizado, emailActualizado, passActualizado, rolActualizado);
                    break;
                case 4:
                    System.out.println("Consultando usuarios...");
                    usuarioCRUD.listarUsuarios();
                    break;
                case 5:
                    System.out.println("Saliendo del programa");
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        } while (opcionUsuario != 5);
    }

    private static void gestionProductos() {
        productosCRUD operaciones = new productosCRUD();
        int opcionProducto = 0;

        do {
            System.out.println("Elige una opcion:");
            System.out.println("1. Insertar producto");
            System.out.println("2. Eliminar producto");
            System.out.println("3. Actualizar producto");
            System.out.println("4. Consultar productos");
            System.out.println("5. Salir");
            opcionProducto = scanner.nextInt();
            scanner.nextLine();

            switch (opcionProducto) {
                case 1:
                    System.out.println("Ingresa el nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.println("Ingresa el precio: ");
                    double precio = scanner.nextDouble();
                    scanner.nextLine();
                    producto nuevoProducto = new producto(nombre, precio, true);
                    operaciones.insertarProducto(nuevoProducto);
                    break;
                case 2:
                    operaciones.listarProductos();
                    System.out.println("Ingresa el ID del producto a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    scanner.nextLine();
                    operaciones.eliminarProducto(idEliminar);
                    break;
                case 3:
                    operaciones.listarProductos();
                    System.out.println("Ingresa el ID del producto a actualizar: ");
                    int idActualizar = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Ingresa el nuevo nombre: ");
                    String nombreActualizado = scanner.nextLine();
                    System.out.println("Ingresa el nuevo precio: ");
                    double precioActualizado = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Ingresa el nuevo estado: ");
                    boolean estadoActualizado = scanner.nextBoolean();
                    scanner.nextLine();
                    operaciones.actualizarProducto(idActualizar, nombreActualizado, precioActualizado, estadoActualizado);
                    break;
                case 4:
                    System.out.println("Consultando productos...");
                    operaciones.listarProductos();
                    break;
                case 5:
                    System.out.println("Saliendo del programa");
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        } while (opcionProducto != 5);
    }

    private static void gestionVentas() {
        int opcionVenta = 0;

        do {
            System.out.println("Elige una opcion de ventas:");
            System.out.println("1. Hacer venta");
            System.out.println("2. Ver historial de ventas");
            System.out.println("3. Salir");
            opcionVenta = scanner.nextInt();
            scanner.nextLine();

            switch (opcionVenta) {
                case 1:
                    hacerVenta();
                    break;
                case 2:
                    historialVentas.mostrarHistorial();
                    break;
                case 3:
                    System.out.println("Regresando al menu anterior");
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        } while (opcionVenta != 3);
    }

    private static void hacerVenta() {
        venta pedido = ventasManager.crearVentaPendiente();
        producto prod;
        productosCRUD prodCrud = new productosCRUD();
        int opcion = 0;

        do {
            System.out.println("==== Menu de venta ====");
            System.out.println("1. Agregar producto");
            System.out.println("2. Ver pedido actual");
            System.out.println("3. Marcar pedido en proceso");
            System.out.println("4. Cambiar cantidad de un producto");
            System.out.println("5. Eliminar producto del pedido");
            System.out.println("6. Finalizar venta");
            System.out.println("7. Cancelar orden");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("Productos disponibles:");
                    prodCrud.listarProductos();
                    System.out.println("Ingresa el ID del producto:");
                    int idProd = scanner.nextInt();
                    scanner.nextLine();
                    prod = prodCrud.obtenerProductoPorId(idProd);
                    if (prod == null) {
                        System.out.println("Producto no encontrado");
                        break;
                    }
                    System.out.println("Ingresa la cantidad:");
                    int cantidad = scanner.nextInt();
                    scanner.nextLine();
                    if (cantidad <= 0) {
                        System.out.println("La cantidad debe ser mayor a cero");
                        break;
                    }
                    detalleVenta detalle = new detalleVenta(idProd, prod.getNombre_producto(), cantidad, prod.getPrecio());
                    ventasManager.agregarItem(pedido, detalle);
                    System.out.println("Producto agregado al pedido");
                    break;
                case 2:
                    System.out.println("Pedido actual:");
                    System.out.println("Total: " + pedido.getVen_total());
                    System.out.println("Cantidad de productos: " + pedido.getVen_cantidad_productos());
                    if (pedido.getItems().isEmpty()) {
                        System.out.println("No hay productos en el pedido");
                    } else {
                        for (int i = 0; i < pedido.getItems().size(); i++) {
                            detalleVenta item = pedido.getItems().get(i);
                            System.out.println((i + 1) + ". " + item.getNombre_producto() + " - Cant: " + item.getCantidad() + " - P.Unit: " + item.getPrecio_unitario() + " - Subtotal: " + item.getSubtotal());
                        }
                    }
                    break;
                case 3:
                    ventasManager.moverAProceso(pedido);
                    System.out.println("El pedido ahora está en proceso.");
                    break;
                case 4:
                    if (pedido.getItems().isEmpty()) {
                        System.out.println("El pedido no tiene productos para modificar");
                        break;
                    }
                    System.out.println("Ingresa el numero del producto a modificar:");
                    int indexModificar = scanner.nextInt() - 1;
                    scanner.nextLine();
                    System.out.println("Ingresa la nueva cantidad:");
                    int nuevaCantidad = scanner.nextInt();
                    scanner.nextLine();
                    ventasManager.actualizarCantidad(pedido, indexModificar, nuevaCantidad);
                    System.out.println("Cantidad actualizada");
                    break;
                case 5:
                    if (pedido.getItems().isEmpty()) {
                        System.out.println("El pedido no tiene productos para eliminar");
                        break;
                    }
                    System.out.println("Ingresa el numero del producto a eliminar:");
                    int indexEliminar = scanner.nextInt() - 1;
                    scanner.nextLine();
                    ventasManager.eliminarItem(pedido, indexEliminar);
                    System.out.println("Producto eliminado del pedido");
                    break;
                case 6:
                    if (pedido.getItems().isEmpty()) {
                        System.out.println("No se puede finalizar un pedido vacio");
                        break;
                    }
                    ventasManager.finalizarVenta(pedido);
                    System.out.println("Venta finalizada y añadida al historial");
                    opcion = 7; // salir del menu de venta
                    break;
                case 7:
                    ventasManager.cancelarOrden(pedido);
                    System.out.println("Orden cancelada y registrada en historial");
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        } while (opcion != 7);
    }
}