package utn.isi.dan.pedido.rest;


import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import utn.isi.dan.pedido.domain.DetallePedido;
import utn.isi.dan.pedido.domain.Pedido;
import utn.isi.dan.pedido.exception.NotFoundException;
import utn.isi.dan.pedido.service.IPedidoService;

@RestController
@RequestMapping("/api/pedido")
@Api(value = "PedidoRest")
public class PedidoRest {
	
	
	@Autowired
	IPedidoService pedidoService;
	
	@PostMapping
	@ApiOperation(value = "Crea un nuevo pedido")
	public ResponseEntity<Pedido> crear(@RequestBody Pedido nuevoPedido){
		
		if(nuevoPedido.getObra()==null) {
			return ResponseEntity.badRequest().body(nuevoPedido);
		}
		if(nuevoPedido.getDetalle()==null || nuevoPedido.getDetalle().isEmpty()) {
			return ResponseEntity.badRequest().body(nuevoPedido);
		}else {
			boolean detallesValidos = nuevoPedido.getDetalle()
					.stream()
					.allMatch(dp -> validarDetallePedido(dp));
			if(!detallesValidos) {
				return ResponseEntity.badRequest().body(nuevoPedido);
			}
		}		
		
		this.pedidoService.crearPedido(nuevoPedido);		
		return ResponseEntity.ok(nuevoPedido);
	}
	
	boolean validarDetallePedido(DetallePedido dp) {		
		if(dp.getProducto()==null) {
			return false;
		}
		if(dp.getCantidad()==null || dp.getCantidad()< 1) {
			return false;
		}	
		
		if(dp.getPrecio()==null) {
			return false;
		}	
		return true;
	}
	
	
	
	@PostMapping(path = "/{idPedido}/detalle")
	@ApiOperation(value = "Agrega un pedido detalle a un pedido existente")
	public ResponseEntity<Pedido> crearDetalle(
			@PathVariable Integer idPedido,
			@RequestBody DetallePedido detalle
			){
        Pedido body = pedidoService.agregarDetallePedido(idPedido, detalle);
        return ResponseEntity.ok(body);	
	}
	
	
	
	
	
	@PutMapping(path = "/{id}")
	@ApiOperation(value = " Actualiza los datos de un pedido existente")
	public ResponseEntity<Pedido>actualizar(@RequestBody Pedido nuevo, @PathVariable Integer id){
		
        pedidoService.actualizarPedido(nuevo);
        return ResponseEntity.ok().build();	
	}
	
	
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Elimina un pedido existente.")
    public ResponseEntity<Pedido> borrar(@PathVariable Integer id){  	
    	
        pedidoService.eliminarPedidobyId(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca un Pedido por id.")
    public ResponseEntity<Pedido> pedidoPorId(@PathVariable Integer id){
    	
        return ResponseEntity.of(this.pedidoService.buscarPedidoById(id));
    }
    
    @GetMapping
    @ApiOperation(value = "Devuelve todos los pedidos.")
    public ResponseEntity<List<Pedido>> todos(){   	
        List<Pedido> body = pedidoService.consultarPedidos();
        return ResponseEntity.ok(body);  	
    }
    
    @GetMapping(path = "/obra/{idObra}")
    @ApiOperation(value = "Busca un pedido por id de obra")
    public ResponseEntity<?> pedidoPorIdObra(@PathVariable Integer idObra) {

        Optional<Pedido> body;
        try {
            body= pedidoService.pedidoPorIdObra(idObra);			
		} catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
            return ResponseEntity.ok(body.get());

    }
    
    @GetMapping(path = "/{idPedido}/detalle/{idDetalle}")
    @ApiOperation(value = "Busca detalle de un pedido por id.")
    public ResponseEntity<?> pedidoPorId(
    		@PathVariable Integer idPedido,
    		@PathVariable Integer idDetalle
    		){
    	
    	 Optional<DetallePedido> body;
    	try {
            body = pedidoService.buscarDetalle(idPedido, idDetalle);       
            
		} catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
    	
        return ResponseEntity.ok(body.get());



    }
    
    
    @DeleteMapping(path = "/{idPedido}/detalle/{idDetalle}")
    @ApiOperation(value = "Elimina un detalle de un pedido existente.")
    public ResponseEntity<DetallePedido> borrar(
    		@PathVariable Integer idPedido,
    		@PathVariable Integer idDetalle
    		){  	
    	
        pedidoService.eliminarDetalle(idPedido, idDetalle);
        return ResponseEntity.ok().build();

    }
    
    

}
