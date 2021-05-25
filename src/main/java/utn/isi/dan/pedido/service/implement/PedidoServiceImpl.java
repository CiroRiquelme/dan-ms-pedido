package utn.isi.dan.pedido.service.implement;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utn.isi.dan.pedido.dao.DetallePedidoRepositoryDao;
import utn.isi.dan.pedido.dao.ObraRepositoryDao;
import utn.isi.dan.pedido.dao.PedidoRepositoryDao;
import utn.isi.dan.pedido.dao.ProductoRepositoryDao;
import utn.isi.dan.pedido.domain.DetallePedido;
import utn.isi.dan.pedido.domain.EstadoPedido;
import utn.isi.dan.pedido.domain.Obra;
import utn.isi.dan.pedido.domain.Pedido;
import utn.isi.dan.pedido.domain.Producto;
import utn.isi.dan.pedido.exception.NotFoundException;
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
	
	@Autowired
	ObraRepositoryDao obraRepository;
	
	@Autowired
	ProductoRepositoryDao productoRepository;
	
	public Pedido aceptarPedido(Pedido p) {
		
		if(obraRepository.findById(p.getObra().getId()).isPresent()){
						
		
			if (p.getDetalle().stream().allMatch( dp -> this.productoRepository.existsById(dp.getProducto().getId()))) {
				p.setEstado(new EstadoPedido(1,"NUEVO"));
				return this.pedidoRepository.save(p);
			}else {
				throw new NotFoundException("Id De producto Inexistente.");
			}		

		}else {
			throw new NotFoundException("La obra con id " + p.getObra().getId() + " no esta dada de alta.");
		}		
	}
	
	public Optional<Pedido> actualizarEstadoPedido(Integer IdPedido , EstadoPedido nuevoEstado){
		

		
		
		Optional<Pedido> pedido = this.pedidoRepository.findById(IdPedido);
		
		if (pedido.isPresent()) {		
			
			if(nuevoEstado.getEstado().equals("CONFIRMADO")) {
				confirmarPedido(pedido.get());				
			}			
			this.pedidoRepository.save(pedido.get());
			
			return pedido;
			
		} else {
			return pedido;
		}
		
		
		
	}
	public void confirmarPedido(Pedido p) {
		
		boolean stockDisponible = p.getDetalle()
				.stream()
				.allMatch(dp -> verificarStock(dp.getProducto(), dp.getCantidad()));
		
		Double CostoTotalOrden = p.getDetalle()
				.stream()
				.mapToDouble(dp -> dp.getCantidad() * dp.getPrecio())
				.sum();
		
		Double saldoCliente = clienteSrv.saldoCliente(p.getObra());
		Double nuevoSaldo = saldoCliente - CostoTotalOrden;
		boolean generaDeuda = nuevoSaldo < 0;
		
		if(stockDisponible) {
			if(!generaDeuda || (generaDeuda && this.esDeBajoRiesgo(p.getObra(), nuevoSaldo))) {
				p.setEstado(new EstadoPedido(5,"ACEPTADO"));
			}else {
				p.setEstado(new EstadoPedido(6,"RECHAZADO"));
			}
		}else {
			p.setEstado(new EstadoPedido(3,"PENDIENTE"));
		}		
	}

	@Override
	public Pedido crearPedido(Pedido p) {

		boolean hayStock = p.getDetalle()
				.stream()
				.allMatch(dp -> verificarStock(dp.getProducto(), dp.getCantidad()));
		
		Double totalOrden = p.getDetalle()
				.stream()
				.mapToDouble(dp -> dp.getCantidad() * dp.getPrecio())
				.sum();
		
		Double saldoCliente = clienteSrv.saldoCliente(p.getObra());
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
	
	public List<Pedido> buscarPedidoByObraId(Integer idObra) {       
         
        return pedidoRepository.findByObraId(idObra);
    }
	
	public List<Pedido> buscarPedidoByEstadoPedido(Integer idEstadoPedido){
		
		return pedidoRepository.findByEstadoId(idEstadoPedido);
	}

	
    
	
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
	

    public void eliminarDetalle(Integer idPedido, Integer idDetalle) {
		
        Optional<Pedido> pedido = pedidoRepository.findById(idPedido);

        if (pedido.isPresent()) {
        	
        	Optional<DetallePedido> det = pedido.get()
                .getDetalle()
                .stream()
                .filter(dp -> dp.getId().equals(idDetalle))
                .findFirst();
        	
        	if(det.isPresent()) {       		

        		detallePedidoRepository.delete(det.get());    		

        	}else {
    			throw new NotFoundException("Detalle pedido inexistente. IdPedido: " + idPedido + " IdDetalle: " + idDetalle);

        	}

        } else {          
			throw new NotFoundException("Pedido inexistente. Id: " + idPedido);
        }  
    }

    public DetallePedido actualizarDetalle(Integer idPedido, DetallePedido detalle) {

            Optional<Pedido> pedido = pedidoRepository.findById(idPedido);

            if (pedido.isPresent()) {
            	
            	Optional<DetallePedido> det = pedido.get()
                    .getDetalle()
                    .stream()
                    .filter(dp -> dp.getId().equals(detalle.getId()))
                    .findFirst();
            	
            	if(det.isPresent()) {
            		
            		DetallePedido detalleActual =  detallePedidoRepository.findById(detalle.getId()).get();
            		detalleActual = detalle;
            		detallePedidoRepository.save(detalleActual);    		
            		
            		return detalleActual;
            	}else {
        			throw new NotFoundException("Detalle pedido inexistente. IdPedido: " + idPedido + " IdDetalle: " + detalle.getId());

            	}

            } else {          
    			throw new NotFoundException("Pedido inexistente. Id: " + idPedido);
            }
        
    }	

	


}
