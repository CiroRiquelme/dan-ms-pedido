package utn.isi.dan.pedido.service;

import java.util.List;
import java.util.Optional;


import utn.isi.dan.pedido.domain.DetallePedido;
import utn.isi.dan.pedido.domain.EstadoPedido;
import utn.isi.dan.pedido.domain.Pedido;

public interface IPedidoService {
	
	public Pedido crearPedido(Pedido p);
	
	public Pedido aceptarPedido(Pedido p);
	
	public Optional<Pedido> actualizarEstadoPedido(Integer IdPedido , EstadoPedido nuevoEstado); 
	
	
	public List<Pedido> consultarPedidos();
	
	public Optional<Pedido> buscarPedidoById(Integer id);
	
	public void eliminarPedidobyId(Integer id);
	
	public void actualizarPedido(Pedido p) ;
	
	public Pedido agregarDetallePedido(Integer idPedido , DetallePedido detalle);


	public List<Pedido> buscarPedidoByObraId(Integer idObra);
	public List<Pedido> buscarPedidoByEstadoPedido(Integer idEstadoPedido);



	public Optional<DetallePedido> buscarDetalle(Integer idPedido, Integer idDetalle);


	void eliminarDetalle(Integer idPedido, Integer idDetalle);
    public DetallePedido actualizarDetalle(Integer idPedido, DetallePedido detalle);



		
		

}
