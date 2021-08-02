package utn.isi.dan.pedido.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCTO", schema = "MS_PEDIDOS")
public class Producto {

	@Id
	@Column(name = "ID_PROD")
	private Integer id;
	
	@Column(name = "DESC_PROD")
	private String descripcion;
	
	@Column(name = "PREC_PROD")
	private Double precio;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
}
