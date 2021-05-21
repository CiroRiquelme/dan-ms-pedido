package utn.isi.dan.pedido.service.implement;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utn.isi.dan.pedido.dao.DetallePedidoRepositoryDao;
import utn.isi.dan.pedido.dao.PedidoRepositoryDao;
import utn.isi.dan.pedido.domain.DetallePedido;
import utn.isi.dan.pedido.domain.EstadoPedido;
import utn.isi.dan.pedido.domain.Obra;
import utn.isi.dan.pedido.domain.Pedido;
import utn.isi.dan.pedido.domain.Producto;
import utn.isi.dan.pedido.exception.NotFoundException;
import utn.isi.dan.pedido.repository.PedidoRepository;
import utn.isi.dan.pedido.service.IClienteService;
import utn.isi.dan.pedido.service.IMaterialService;
import utn.isi.dan.pedido.service.IPedidoService;


@Service
public class PedidoServiceImpl implements IPedidoService{
	
	@Autowired
	IMaterialService materialSrv;
	
	@Autowired
	IClienteService clienteSrv;
	
	@Autowired
	PedidoRepositoryDao pedidoRepository;
	
	@Autowired
    DetallePedidoRepositoryDao detallePedidoRepository;

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
				throw new RuntimeException("El cliente NO posee aprobacion crediticia.");
			}
		}else {
			p.setEstado(new EstadoPedido(2,"PENDIENTE"));
		}		
		
		return this.pedidoRepository.save(p);
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
        return pedidoRepository.findAll();
	}


	@Override
	public Optional<Pedido> buscarPedidoById(Integer id) {
        return pedidoRepository.findById(id);		
	}
	
	@Override
    public Optional<Pedido> pedidoPorIdObra(Integer idObra) {
        Obra obra = new Obra();
        obra.setId(idObra);
        
        Optional<Pedido> pedido= pedidoRepository.findByObra(obra);
        
        
        if(pedido.isPresent()) {
        	return pedido;
        }else {
			throw new NotFoundException("Pedido con idobra: " + obra.getId() + "inexistente");

        }
    }
	
    
	@Override
    public Optional<DetallePedido> buscarDetalle(Integer idPedido, Integer idDetalle) {

        Optional<Pedido> pedido = pedidoRepository.findById(idPedido);

        if (pedido.isPresent()) {

        	
        	Optional<DetallePedido> det = pedido.get()
                .getDetalle()
                .stream()
                .filter(dp -> dp.getId().equals(idDetalle))
                .findFirst();
        	
        	if(det.isPresent()) {
        		return det;
        	}else {
    			throw new NotFoundException("Detalle pedido inexistente. IdPedido: " + idPedido + " IdDetalle: " + idDetalle);

        	}

        } else {          
			throw new NotFoundException("Pedido inexistente. Id: " + idPedido);
        }
    }

	
	public void eliminarPedidobyId(Integer id){
		
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
        } else {
			throw new NotFoundException("Pedido inexistente. Id: " + id);
        }
	}
	
	public void actualizarPedido(Pedido p) {
		
        if (pedidoRepository.existsById(p.getId())) {
            pedidoRepository.save(p);
        } else {
			throw new NotFoundException("Pedido inexistente. Id: " + p.getId());
        }
	}
	
	public Pedido agregarDetallePedido(Integer idPedido , DetallePedido detalle) {
		
		if(this.pedidoRepository.existsById(idPedido)) {
			Pedido p = this.pedidoRepository.findById(idPedido).get();
			p.getDetalle().add(detalle);
            return pedidoRepository.save(p);
		}else {
			throw new NotFoundException("Pedido inexistente. Id: " + idPedido);
		}		
	}
	
	@Override
    public void eliminarDetalle(Integer idPedido, Integer idDetalle) {
        
        Optional<Pedido> pedido = pedidoRepository.findById(idPedido); 

        if (pedido.isPresent()) {

            if (detallePedidoRepository.existsById(idDetalle)) {

                detallePedidoRepository.deleteById(idDetalle); 

            } else {
                throw new NotFoundException("Detalle inexistente. Id: " + idPedido);
            }
        } else {
            throw new NotFoundException("Pedido inexistente. Id: " + idPedido);
        }        
    }


}
