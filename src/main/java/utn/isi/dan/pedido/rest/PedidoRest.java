package utn.isi.dan.pedido.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import utn.isi.dan.pedido.domain.DetallePedido;
import utn.isi.dan.pedido.domain.EstadoPedido;
import utn.isi.dan.pedido.domain.Pedido;
import utn.isi.dan.pedido.exception.BadRequestException;
import utn.isi.dan.pedido.service.IPedidoService;

@RestController
@RequestMapping("/api/pedido")
@Api(value = "PedidoRest")
public class PedidoRest {

	@Autowired
	IPedidoService pedidoService;

	@PostMapping
	@ApiOperation(value = "Crea un nuevo pedido")
	public ResponseEntity<?> crearPedido(@RequestBody Pedido nuevoPedido) {

		if (nuevoPedido.getObra() == null || nuevoPedido.getObra().getId() == null) {
			throw new BadRequestException("El Pedido Debe contener una obra.");
		}
		if (nuevoPedido.getDetalle() == null || nuevoPedido.getDetalle().isEmpty()) {
			throw new BadRequestException("El Pedido debe contener al menos un detalle.");
		} else {
			boolean detallesValidos = nuevoPedido.getDetalle().stream().allMatch(dp -> validarDetallePedido(dp));
			if (!detallesValidos) {
				 throw new BadRequestException("El Pedido debe tener detalles validos , revise datos de producto, cantidad y precio.");
			}
		}

		Pedido pedidoCreado = pedidoService.aceptarPedido(nuevoPedido);


		return ResponseEntity.ok(pedidoCreado);
	}

	boolean validarDetallePedido(DetallePedido dp) {
		if (dp.getProducto() == null || dp.getProducto().getId() == null) {
			return false;
		}
		if (dp.getCantidad() == null || dp.getCantidad() < 1) {
			return false;
		}

		if (dp.getPrecio() == null) {
			return false;
		}
		return true;
	}

	@PostMapping(path = "/{idPedido}/detalle")
	@ApiOperation(value = "Agrega un pedido detalle a un pedido existente")
	public ResponseEntity<Pedido> crearDetalle(@PathVariable Integer idPedido, @RequestBody DetallePedido detalle) {
		Pedido body = pedidoService.agregarDetallePedido(idPedido, detalle);
		return ResponseEntity.ok(body);
	}

	@PutMapping(path = "/{id}")
	@ApiOperation(value = " Actualiza los datos de un pedido existente")
	public ResponseEntity<?> actualizaRPedido(@RequestBody Pedido nuevo, @PathVariable Integer id) {

		if (nuevo.getId() == null) {
			throw new BadRequestException("El Pedido Debe contener un id.");
		}
			pedidoService.actualizarPedido(nuevo);


		return ResponseEntity.ok().build();
	}

	@PatchMapping(path = "/{id}")
	@ApiOperation(value = "Actualizar estado pedido")
	public ResponseEntity<?> actualizarEstadoPedido(@RequestBody EstadoPedido nuevoEstado, @PathVariable Integer id) {

		pedidoService.actualizarEstadoPedido(id, nuevoEstado);
			return ResponseEntity.ok().build();
	}

	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Elimina un pedido existente.")
	public ResponseEntity<?> eliminarPedido(@PathVariable Integer id) {

	
			pedidoService.eliminarPedidobyId(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Busca un Pedido por id.")
	public ResponseEntity<?> pedidoPorId(@PathVariable Integer id) {

		Optional<Pedido> pedido = pedidoService.buscarPedidoById(id);
		return ResponseEntity.of(pedido);		
	}

	@GetMapping
	@ApiOperation(value = "Devuelve todos los pedidos.")
	public ResponseEntity<List<Pedido>> consultaPedidos() {
		return ResponseEntity.ok(pedidoService.consultarPedidos());
	}

	@GetMapping(path = "/obra/{idObra}")
	@ApiOperation(value = "Busca un pedido por id de obra")
	public ResponseEntity<?> pedidoPorIdObra(@PathVariable Integer idObra) {

		return ResponseEntity.ok(this.pedidoService.buscarPedidoByObraId(idObra));

	}

	@GetMapping(path = "/estado/{idEstadoPedido}")
	@ApiOperation(value = "Busca un pedido por id de estadoPedido")
	public ResponseEntity<?> pedidoPorIdEstadoPedido(@PathVariable Integer idEstadoPedido) {

		return ResponseEntity.ok(this.pedidoService.buscarPedidoByEstadoPedido(idEstadoPedido));

	}

	@GetMapping(path = "/{idPedido}/detalle/{idDetalle}")
	@ApiOperation(value = "Busca detalle de un pedido por id.")
	public ResponseEntity<?> buscarDetallePedido(@PathVariable Integer idPedido, @PathVariable Integer idDetalle) {

		return ResponseEntity.ok(pedidoService.buscarDetalle(idPedido, idDetalle));

	}

	@DeleteMapping(path = "/{idPedido}/detalle/{idDetalle}")
	@ApiOperation(value = "Elimina un detalle de un pedido existente.")
	public ResponseEntity<?> eliminarDetallePedido(@PathVariable Integer idPedido, @PathVariable Integer idDetalle) {

		pedidoService.eliminarDetalle(idPedido, idDetalle);

		return ResponseEntity.ok().build();

	}

	@PatchMapping(path = "/{idPedido}/detalle/{idDetalle}")
	@ApiOperation(value = "Actualiza un detalle de un pedido existente.")
	public ResponseEntity<?> actualizarDetallePedido(@PathVariable Integer idPedido, @PathVariable Integer idDetalle,
			@RequestBody DetallePedido detalleNuevo) {


		pedidoService.actualizarDetalle(idPedido, detalleNuevo);

		return ResponseEntity.ok().build();

	}

}
