package utn.isi.dan.pedido.service.implement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import utn.isi.dan.pedido.PedidosApplicationTests;
import utn.isi.dan.pedido.dao.PedidoRepositoryDao;
import utn.isi.dan.pedido.domain.DetallePedido;
import utn.isi.dan.pedido.domain.Obra;
import utn.isi.dan.pedido.domain.Pedido;
import utn.isi.dan.pedido.domain.Producto;
import utn.isi.dan.pedido.service.IClienteService;
import utn.isi.dan.pedido.service.IMaterialService;
import utn.isi.dan.pedido.service.IPedidoService;

@SpringBootTest(classes = PedidosApplicationTests.class)
public class PedidoServiceImplUnitTest {
	
	@Autowired
	IPedidoService pedidoService;
	
	@MockBean
	PedidoRepositoryDao pedidoRepository;
	
	@MockBean
	IClienteService clienteService;

	@MockBean
	IMaterialService materialService;
	
	Pedido unPedido;
	
	@BeforeEach
	void setUpTests() throws Exception {
		unPedido = new Pedido();
		
		Obra obra = new Obra();
		obra.setId(1);
		
		DetallePedido det1 = new DetallePedido(new Producto(), 3, 100.0);
		DetallePedido det2 = new DetallePedido(new Producto(), 2, 50.0);
		DetallePedido det3 = new DetallePedido(new Producto(), 1, 100.0);

		unPedido.setDetalle(new ArrayList<DetallePedido>());
		unPedido.addDetalle(det1);
		unPedido.addDetalle(det2);
		unPedido.addDetalle(det3);
		
		unPedido.setObra(obra);
		
	}
	
    @Test
    public void testCrearPedidoOK() {
    	
    	unPedido.getObra().setId(3);

        when(materialService.stockDisponible(any(Producto.class))).thenReturn(5);
        when(clienteService.deudaCliente(any(Obra.class))).thenReturn(1000.00);
        when(clienteService.maximoSaldoNegativo(any(Obra.class))).thenReturn(1000.0);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(unPedido);

        Pedido resultado = pedidoService.crearPedido(unPedido);

        assertTrue(resultado.getEstado().getId().equals(1));
        verify(pedidoRepository, times(1)).save(unPedido);
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
		when(pedidoRepository.save(any(Pedido.class))).thenReturn(unPedido);
		
		
		Pedido pedidoResultado = pedidoService.crearPedido(unPedido);
		assertThat(pedidoResultado.getEstado().getId().equals(1));
		verify(pedidoRepository,times(1)).save(unPedido);
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
		when(pedidoRepository.save(any(Pedido.class))).thenReturn(unPedido);
		
		Pedido pedidoResultado = pedidoService.crearPedido(unPedido);
		
		assertThat(pedidoResultado.getEstado().getId().equals(2));
		verify(pedidoRepository,times(1)).save(unPedido);
	}
	
	
	

}
