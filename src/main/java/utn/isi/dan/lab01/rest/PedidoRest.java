package utn.isi.dan.lab01.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

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
import utn.isi.dan.lab01.domain.DetallePedido;
import utn.isi.dan.lab01.domain.Pedido;

@RestController
@RequestMapping("/api/pedido")
@Api(value = "PedidoRest")
public class PedidoRest {
	
	
	private static final List<Pedido> listaPedidos = new ArrayList<>();
	private static Integer ID_GEN = 1;
	
	@PostMapping
	@ApiOperation(value = "Crea un pedido")
	public ResponseEntity<Pedido> crear(@RequestBody Pedido nuevoPedido){
		
		
		nuevoPedido.setId(ID_GEN++);
		listaPedidos.add(nuevoPedido);
		
		return ResponseEntity.ok(nuevoPedido);
	}
	
	@PostMapping(path = "/{idPedido}/detalle")
	@ApiOperation(value = "Agrega un pedido detalle a un pedido existente")
	public ResponseEntity<DetallePedido> crearDetalle(
			@PathVariable Integer idPedido,
			@RequestBody DetallePedido detalle
			){
		
        OptionalInt indexOpt =   IntStream.range(0, listaPedidos.size())
        .filter(i -> listaPedidos.get(i).getId().equals(idPedido))
        .findFirst();

        if(indexOpt.isPresent()){
        	listaPedidos.get(indexOpt.getAsInt()).getDetalle().add(detalle);
        	
            return ResponseEntity.ok().build();
            
            
        } else {
            return ResponseEntity.notFound().build();
        }
	}
	
	
	
	
	
	@PutMapping(path = "/{id}")
	@ApiOperation(value = " Actualiza los datos de un pedido existente")
	public ResponseEntity<Pedido>actualizar(@RequestBody Pedido nuevo, @PathVariable Integer id){
		
		OptionalInt indexOpt = IntStream.range(0, listaPedidos.size())
				.filter(i -> listaPedidos.get(i).getId().equals(id)         )
				.findFirst();
		
		if(indexOpt.isPresent()) {
			listaPedidos.set(indexOpt.getAsInt(),nuevo);
			return ResponseEntity.ok(nuevo);
		}else {
			return ResponseEntity.notFound().build();
		}		
	}
	
	
    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Elimina un pedido existente.")
    public ResponseEntity<Pedido> borrar(@PathVariable Integer id){
        OptionalInt indexOpt =   IntStream.range(0, listaPedidos.size())
        .filter(i -> listaPedidos.get(i).getId().equals(id))
        .findFirst();

        if(indexOpt.isPresent()){
        	listaPedidos.remove(indexOpt.getAsInt());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca un Pedido por id.")
    public ResponseEntity<Pedido> pedidoPorId(@PathVariable Integer id){

        Optional<Pedido> c =  listaPedidos
                .stream()
                .filter(unPed -> unPed.getId().equals(id))
                .findFirst();
        return ResponseEntity.of(c);
    }
    
    @GetMapping
    @ApiOperation(value = "Devuelve la lista de pedidos ( Por id de obra.")
    public ResponseEntity<List<Pedido>> todos(
    		@RequestParam(required = false) Integer idObra
    		
    		){   	
    	
    	if(idObra != null && !idObra.equals(0)) {
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
			 return ResponseEntity.ok(listaPedidos);
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
