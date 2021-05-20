package utn.isi.dan.pedido.service;

import utn.isi.dan.pedido.domain.Producto;

public interface IMaterialService {	
	
	public Integer stockDisponible(Producto m);
}
