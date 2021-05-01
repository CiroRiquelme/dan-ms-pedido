package utn.isi.dan.pedido.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
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
import utn.isi.dan.lpedido.service.PedidoService;
import utn.isi.dan.pedido.domain.DetallePedido;
import utn.isi.dan.pedido.domain.Pedido;

@RestController
@RequestMapping("/api/pedido")
@Api(value = "PedidoRest")
public class PedidoRest {
	
	
	@Autowired
	PedidoService pedidoService;
	
	
	private static final List<Pedido> listaPedidos = new ArrayList<>();
	
	@PostMapping
	@ApiOperation(value = "Crea un pedido")
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
		if(dp.getCantidad()==null) {
			return false;
		}	
		
		if(dp.getPrecio()==null) {
			return false;
		}	
		return true;
	}
	
	
	
	@PostMapping(path = "/{idPedido}/detalle")
	@ApiOperation(value = "Agrega un pedido detalle a un pedido existente")
	public ResponseEntity<DetallePedido> crearDetalle(
			@PathVariable Integer idPedido,
			@RequestBody DetallePedido detalle
			){
		
        if(this.pedidoService.agregarDetallePedido(idPedido,detalle)){     
            return ResponseEntity.ok(detalle);
        } else {
            return ResponseEntity.notFound().build();
        }	
	}
	
	
	
	
	
	@PutMapping(path = "/{id}")
	@ApiOperation(value = " Actualiza los datos de un pedido existente")
	public ResponseEntity<Pedido>actualizar(@RequestBody Pedido nuevo, @PathVariable Integer id){
		
        if(this.pedidoService.actualizarPedido(nuevo)){     
            return ResponseEntity.ok(nuevo);
        } else {
            return ResponseEntity.notFound().build();
        }	
	}
	
	
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Elimina un pedido existente.")
    public ResponseEntity<Pedido> borrar(@PathVariable Integer id){  	
    	


        if(this.pedidoService.eliminarPedidobyId(id)){     
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca un Pedido por id.")
    public ResponseEntity<Pedido> pedidoPorId(@PathVariable Integer id){
    	


//        Optional<Pedido> c =  listaPedidos
//                .stream()
//                .filter(unPed -> unPed.getId().equals(id))
//                .findFirst();
        return ResponseEntity.of(this.pedidoService.buscarPedidoById(id));
    }
    
    @GetMapping
    @ApiOperation(value = "Devuelve la lista de pedidos ( Por id de obra.")
    public ResponseEntity<List<Pedido>> todos(
    		@RequestParam(required = false) Integer idObra
    		
    		){   	
    	
    	if(idObra != null && !idObra.equals(0)) {
    		
    		List<Pedido> listaPedidos = new ArrayList<>();
    		listaPedidos = this.pedidoService.consultarPedidos();
    		
	        Optional<Pedido> c =  listaPedidos
	                .stream()
	                .filter(unPed -> unPed.getObra().getId().equals(idObra))
	                .findFirst();
	        
	        if (c.isPresent()) {
		        List<Pedido> list = new ArrayList<Pedido>();
		        list.add(c.get());
		        return ResponseEntity.ok(list);
	        }else {
	        	return ResponseEntity.notFound().build();
	        }
	        
		}else {
			
			return ResponseEntity.ok(this.pedidoService.consultarPedidos());
			 //return ResponseEntity.ok(listaPedidos);
		}   	
    }
    
    @GetMapping(path = "/{idPedido}/detalle/{idDetalle}")
    @ApiOperation(value = "Busca un detalle Pedido por id.")
    public ResponseEntity<DetallePedido> pedidoPorId(
    		@PathVariable Integer idPedido,
    		@PathVariable Integer idDetalle
    		){

        Optional<Pedido> c =  listaPedidos
                .stream()
                .filter(unPed -> unPed.getId().equals(idPedido))
                .findFirst();
        
        if (c.isPresent()) {
			
        	Optional<DetallePedido> d =
        	c.get().getDetalle()
        	.stream()
        	.filter(unDet -> unDet.getId().equals(idDetalle))
        	.findFirst()
        	;
        	
        	return ResponseEntity.of(d);
		}       
        
        return ResponseEntity.notFound().build();
    }
    
    
    @DeleteMapping(path = "/{idPedido}/detalle/{idDetalle}")
    @ApiOperation(value = "Elimina un detalle de un pedido existente.")
    public ResponseEntity<DetallePedido> borrar(
    		@PathVariable Integer idPedido,
    		@PathVariable Integer idDetalle
    		){  	
    	
        OptionalInt indexOpt =   IntStream.range(0, listaPedidos.size())
        .filter(i -> listaPedidos.get(i).getId().equals(idPedido))
        .findFirst();

        if(indexOpt.isPresent()){

        	List<DetallePedido> listaDetalle = listaPedidos.get(indexOpt.getAsInt()).getDetalle();
        	
        	OptionalInt indexDetalle = IntStream.range(0, listaDetalle.size())
            		.filter(i -> listaDetalle.get(i).getId().equals(idDetalle))
            		.findFirst(); 
        	
        	if (indexDetalle.isPresent()) {
        		listaDetalle.remove(indexDetalle.getAsInt());
                return ResponseEntity.ok().build();
			}else {
				return ResponseEntity.notFound().build();
			}

        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    

}
