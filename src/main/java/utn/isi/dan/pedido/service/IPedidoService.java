package utn.isi.dan.pedido.service;

import java.util.List;
import java.util.Optional;


import utn.isi.dan.pedido.domain.DetallePedido;
import utn.isi.dan.pedido.domain.Pedido;

public interface IPedidoService {
	
	public Pedido crearPedido(Pedido p);
	
	
	public List<Pedido> consultarPedidos();
	
	Optional<Pedido> buscarPedidoById(Integer id);
	
	public void eliminarPedidobyId(Integer id);
	
	public void actualizarPedido(Pedido p) ;
	
	public Pedido agregarDetallePedido(Integer idPedido , DetallePedido detalle);


	Optional<Pedido> pedidoPorIdObra(Integer idObra);


	Optional<DetallePedido> buscarDetalle(Integer idPedido, Integer idDetalle);


	void eliminarDetalle(Integer idPedido, Integer idDetalle);



		
		

}
