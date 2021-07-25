package utn.isi.dan.pedido.service.implement;

import utn.isi.dan.pedido.domain.DetallePedido;

public class DetallePedidoDto {

	private Integer idMaterial;
	
	private String descripcionMaterial;
	
	private Integer cantidadMaterial;
	


	public DetallePedidoDto(DetallePedido detalle) {
		
		this.idMaterial = detalle.getProducto().getId();
		this.descripcionMaterial = detalle.getProducto().getDescripcion();
		this.cantidadMaterial = detalle.getCantidad();
		
	}

	public Integer getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(Integer idMaterial) {
		this.idMaterial = idMaterial;
	}

	public Integer getCantidadMaterial() {
		return cantidadMaterial;
	}

	public void setCantidadMaterial(Integer cantidadMaterial) {
		this.cantidadMaterial = cantidadMaterial;
	}

	@Override
	public String toString() {
		return "DetallePedidoDto [idMaterial=" + idMaterial + ", descripcionMaterial=" + descripcionMaterial
				+ ", cantidadMaterial=" + cantidadMaterial + "]";
	}

	public String getDescripcionMaterial() {
		return descripcionMaterial;
	}

	public void setDescripcionMaterial(String descripcionMaterial) {
		this.descripcionMaterial = descripcionMaterial;
	}
	
	
	
	
	

	
	

}
