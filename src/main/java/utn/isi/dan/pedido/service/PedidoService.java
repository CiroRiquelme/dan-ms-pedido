package utn.isi.dan.pedido.service;

import java.util.List;
import java.util.Optional;

import utn.isi.dan.pedido.domain.DetallePedido;
import utn.isi.dan.pedido.domain.Pedido;

public interface PedidoService {
	
	public Pedido crearPedido(Pedido p);
	
	
	public List<Pedido> consultarPedidos();
	
	Optional<Pedido> buscarPedidoById(Integer id);
	
	public boolean eliminarPedidobyId(Integer id);
	
	public boolean actualizarPedido(Pedido p);
	
	public boolean agregarDetallePedido(Integer idPedido , DetallePedido detalle);
		
		

}