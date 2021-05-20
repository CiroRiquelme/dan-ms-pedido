package utn.isi.dan.pedido.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DETALLE_PEDIDO", schema = "MS_PEDIDOS")
public class DetallePedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_DET_PED")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "ID_PROD")
	private Producto producto;
	
	@Column(name = "CAN_DET_PED", nullable = false)
	private Integer cantidad;
		
	@Column(name = "PREC_DET_PED", nullable = false)
	private Double precio;
	
	public DetallePedido(){
		
	}
	
	
	public DetallePedido(Producto producto, Integer cantidad, Double precio) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
		this.precio = precio;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
}
