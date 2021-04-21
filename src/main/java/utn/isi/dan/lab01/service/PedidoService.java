package utn.isi.dan.lab01.service;

import java.util.List;

import utn.isi.dan.lab01.domain.Pedido;

public interface PedidoService {
	
	public Pedido crearPedido(Pedido p);
	
	
	public List<Pedido> consultarPedidos();
		
		

}
