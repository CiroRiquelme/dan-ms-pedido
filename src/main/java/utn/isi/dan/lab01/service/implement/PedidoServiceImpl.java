package utn.isi.dan.lab01.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utn.isi.dan.lab01.domain.DetallePedido;
import utn.isi.dan.lab01.domain.EstadoPedido;
import utn.isi.dan.lab01.domain.Obra;
import utn.isi.dan.lab01.domain.Pedido;
import utn.isi.dan.lab01.domain.Producto;
import utn.isi.dan.lab01.repository.PedidoRepository;
import utn.isi.dan.lab01.service.ClienteService;
import utn.isi.dan.lab01.service.MaterialService;
import utn.isi.dan.lab01.service.PedidoService;


@Service
public class PedidoServiceImpl implements PedidoService{
	
	@Autowired
	MaterialService materialSrv;
	
	@Autowired
	PedidoRepository repo;
	
	@Autowired
	ClienteService clienteSrv;

	@Override
	public Pedido crearPedido(Pedido p) {


		boolean hayStock = p.getDetalle()
				.stream()
				.allMatch(dp -> verificarStock(dp.getProducto(), dp.getCantidad()));
		
		Double totalOrden = p.getDetalle()
				.stream()
				.mapToDouble(dp -> dp.getCantidad() * dp.getPrecio())
				.sum();
		
		Double saldoCliente = clienteSrv.deudaCliente(p.getObra());
		Double nuevoSaldo = saldoCliente - totalOrden;
		
		
		boolean generaDeuda = nuevoSaldo < 0;
		
		if(hayStock) {
			if(!generaDeuda	|| (generaDeuda && this.esDeBajoRiesgo(p.getObra(), nuevoSaldo)	)) {
				p.setEstado(new EstadoPedido(1,"ACEPTADO"));
			}else {
				throw new RuntimeException("El cliente no tiene aprobacion crediticia.");
			}
		}else {
			p.setEstado(new EstadoPedido(2,"PENDIENTE"));
		}		
		
		return this.repo.save(p);
	}
	
	
	boolean verificarStock(Producto p,Integer cantidad) {
		return materialSrv.stockDisponible(p)>=cantidad;
	}
	
	boolean esDeBajoRiesgo(Obra o,Double saldoNuevo) {
		Double maximoSaldoNegativo = clienteSrv.maximoSaldoNegativo(o);
		Boolean tieneSaldo = Math.abs(saldoNuevo) < maximoSaldoNegativo;
		return tieneSaldo;
	}


	@Override
	public List<Pedido> consultarPedidos() {
		  List<Pedido> list = new ArrayList<Pedido>();
		  this.repo.findAll().forEach(p -> list.add(p));

		return list;
	}


	@Override
	public Optional<Pedido> buscarPedidoById(Integer id) {
		
		
		return this.repo.findById(id);		
		
	}
	
	public boolean eliminarPedidobyId(Integer id){
		
		if(this.repo.existsById(id)) {
			this.repo.deleteById(id);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean actualizarPedido(Pedido p) {
		
		if(this.repo.existsById(p.getId())) {
			this.repo.deleteById(p.getId());
			this.repo.save(p);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean agregarDetallePedido(Integer idPedido , DetallePedido detalle) {
		
		if(this.repo.existsById(idPedido)) {
			Pedido p = this.repo.findById(idPedido).get();
			p.getDetalle().add(detalle);
			this.repo.save(p);
			return true;
		}else {
			return false;
		}
		
		
	}


}
