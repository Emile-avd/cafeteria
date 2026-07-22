package vista;

public class detalleVenta {
	private int id_detalle;
	private int id_venta;
	private int producto_id;
	private String nombre_producto;
	private int cantidad;
	private double precio_unitario;

	public detalleVenta(int producto_id, String nombre_producto, int cantidad, double precio_unitario) {
		this.producto_id = producto_id;
		this.nombre_producto = nombre_producto;
		this.cantidad = cantidad;
		this.precio_unitario = precio_unitario;
	}

	public int getId_detalle() {
		return id_detalle;
	}

	public void setId_detalle(int id_detalle) {
		this.id_detalle = id_detalle;
	}

	public int getId_venta() {
		return id_venta;
	}

	public void setId_venta(int id_venta) {
		this.id_venta = id_venta;
	}

	public int getProducto_id() {
		return producto_id;
	}

	public void setProducto_id(int producto_id) {
		this.producto_id = producto_id;
	}

	public String getNombre_producto() {
		return nombre_producto;
	}

	public void setNombre_producto(String nombre_producto) {
		this.nombre_producto = nombre_producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio_unitario() {
		return precio_unitario;
	}

	public void setPrecio_unitario(double precio_unitario) {
		this.precio_unitario = precio_unitario;
	}

	public double getSubtotal() {
		return this.cantidad * this.precio_unitario;
	}
}
