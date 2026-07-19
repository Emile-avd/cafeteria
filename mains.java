import java.util.Scanner;

public class mains {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            var conexion = conexionDb.getConexion();

            if (conexion != null && !conexion.isClosed()) {
                System.out.println("Conexion exitosa a la base de datos");

                System.out.println("Bienvenido al sistema de gestion de la cafeteria");
                System.out.println("Inicia sesion");
                System.out.println("Email: ");
                String email = scanner.nextLine();
                System.out.println("Contraseña: ");
                String password = scanner.nextLine();

                inicioSesion sesion = new inicioSesion();
                usuarioCRUD usuarioCRUD = new usuarioCRUD();

                if (!sesion.iniciarSesion(email, password, usuarioCRUD)) {
                    System.out.println("Email o contraseña incorrectos");
                    conexion.close();
                    return;
                }

                System.out.println("Bienvenido " + sesion.getUsuarioActual().getnombre_usuario());
                System.out.println("Rol: " + sesion.getUsuarioActual().getRol());
                System.out.println("Elige una opcion:");
                System.out.println("1. Gestion de usuarios");
                System.out.println("2. Gestion de productos");
                int opcion = scanner.nextInt();
                scanner.nextLine();

                if (opcion == 1) {
                    gestionUsuarios(usuarioCRUD);
                } else if (opcion == 2) {
                    gestionProductos();
                } else {
                    System.out.println("Opcion invalida");
                }

                conexion.close();
            } else {
                System.out.println("No se pudo realizar la conexion");
            }

        } catch (Exception e) {
            System.out.println("Error al verificar la conexion" + e.getMessage());
        }
    }

    private static void gestionUsuarios(usuarioCRUD usuarioCRUD) {
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
                    String rol = pedirRol();
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
                    String rolActualizado = pedirRol();
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

    private static String pedirRol() {
        System.out.println("Seleccione el rol:");
        System.out.println("1. Administrador");
        System.out.println("2. Cajero");
        int opcionRol = scanner.nextInt();
        scanner.nextLine();

        if (opcionRol == 1) {
            return "Administrador";
        }

        return "Cajero";
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
}