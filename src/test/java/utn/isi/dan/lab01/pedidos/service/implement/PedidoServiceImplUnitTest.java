package utn.isi.dan.lab01.pedidos.service.implement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import utn.isi.dan.lpedido.service.ClienteService;
import utn.isi.dan.lpedido.service.MaterialService;
import utn.isi.dan.lpedido.service.PedidoService;
import utn.isi.dan.pedido.domain.DetallePedido;
import utn.isi.dan.pedido.domain.Obra;
import utn.isi.dan.pedido.domain.Pedido;
import utn.isi.dan.pedido.domain.Producto;
import utn.isi.dan.pedido.repository.PedidoRepository;

@SpringBootTest
public class PedidoServiceImplUnitTest {
	
	@Autowired
	PedidoService pedidoService;
	
	@MockBean
	PedidoRepository pedidoRepo;
	
	@MockBean
	ClienteService clienteService;

	@MockBean
	MaterialService materialService;
	
	Pedido unPedido;
	
	@BeforeEach
	void setUpTests() throws Exception {
		unPedido = new Pedido();
		
		Obra obra = new Obra();
		
		DetallePedido det1 = new DetallePedido(new Producto(), 5, 40.0);
		DetallePedido det2 = new DetallePedido(new Producto(), 10, 80.0);
		DetallePedido det3 = new DetallePedido(new Producto(), 2, 450.0);

		unPedido.setDetalle(new ArrayList<DetallePedido>());
		unPedido.getDetalle().add(det1);
		unPedido.getDetalle().add(det2);
		unPedido.getDetalle().add(det3);
		
		unPedido.setObra(obra);
	}
	
	@Test
	void testCrearPedidoConStockSinDeuda() {
		
		//Producto con stock
		when(materialService.stockDisponible(any(Producto.class))).thenReturn(20);
		
		//Cliente sin deuda
		when(clienteService.deudaCliente(any(Obra.class))).thenReturn(0.0);
		
		//Cliente con saldo negativo maximo de 10000
		when(clienteService.maximoSaldoNegativo(any(Obra.class))).thenReturn(10000.0);
		
		//Cliente en situacion crediticia 1.
		when(clienteService.situacionCrediticiaBCRA(any(Obra.class))).thenReturn(1);
		
		//retornar el pedido luego de guardalo
		when(pedidoRepo.save(any(Pedido.class))).thenReturn(unPedido);
		
		
		Pedido pedidoResultado = pedidoService.crearPedido(unPedido);
		assertThat(pedidoResultado.getEstado().getId().equals(1));
		verify(pedidoRepo,times(1)).save(unPedido);
	}
	
	@Test
	void TestCrearPedidoSinStock() {
		
		//producto sin stock
		when(materialService.stockDisponible(any(Producto.class))).thenReturn(3);
		
		//Cliente sin deuda
		when(clienteService.deudaCliente(any(Obra.class))).thenReturn(0.0);
		
		//Cliente con saldo negativo maximo de 10000
		when(clienteService.maximoSaldoNegativo(any(Obra.class))).thenReturn(10000.0);
		
		//Cliente en situacion crediticia 1.
		when(clienteService.situacionCrediticiaBCRA(any(Obra.class))).thenReturn(1);
		
		//retornar el pedido luego de guardalo
		when(pedidoRepo.save(any(Pedido.class))).thenReturn(unPedido);
		
		Pedido pedidoResultado = pedidoService.crearPedido(unPedido);
		
		assertThat(pedidoResultado.getEstado().getId().equals(2));
		verify(pedidoRepo,times(1)).save(unPedido);
	}
	
	
	

}
