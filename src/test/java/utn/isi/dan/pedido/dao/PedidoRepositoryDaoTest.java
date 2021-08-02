package utn.isi.dan.pedido.dao;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;


import utn.isi.dan.pedido.PedidosApplicationTests;
import utn.isi.dan.pedido.domain.DetallePedido;
import utn.isi.dan.pedido.domain.EstadoPedido;
import utn.isi.dan.pedido.domain.Obra;
import utn.isi.dan.pedido.domain.Pedido;
import utn.isi.dan.pedido.domain.Producto;

@SpringBootTest(classes = PedidosApplicationTests.class)

class PedidoRepositoryDaoTest {

	@Autowired
	public PedidoRepositoryDao pedidoRepository;
	
	@Test
    @Order(1)    
	public void RepositoryNotNull() {
		assertNotNull(pedidoRepository);
	}
	
    @Test
    @Sql("/datos_test.sql")
    @Order(2)    
    public void findAllPedidos() {

        List<Pedido> pedidos = pedidoRepository.findAll();

        assertFalse(pedidos.isEmpty());
        assertThat(pedidos.size(), is(equalTo(3)));
    }
    
    @Test
    @Order(3)    
    public void findById_pedido() {

        Optional<Pedido> pedido = pedidoRepository.findById(1);
        
        assertTrue(pedido.isPresent());
        assertThat(pedido.get().getId(), is(equalTo(1)));

    }
    
    @Test
    public void findByObraId_pedido() {
        
        List<Pedido> pedido = pedidoRepository.findByObraId(1); 
        
        assertThat(pedido.size(), is(equalTo(1))); 
    } 

    
    @Test
    public void savePedido() {
    	
        EstadoPedido estadoPedido = new EstadoPedido(1, "Aceptado");
        
        Obra obra = new Obra();
        obra.setId(1);
        
        Producto producto1 = new Producto();
        Producto producto2 = new Producto();        
        producto1.setId(1);
        producto2.setId(2);
        
        DetallePedido detallePedido1 = new DetallePedido(producto1, 1, 10.0); 
        DetallePedido detallePedido2 = new DetallePedido(producto2, 2, 20.0);
        
        Pedido pedido = new Pedido();
        pedido.setFechaPedido(Instant.now());
        pedido.setEstado(estadoPedido);
        pedido.setObra(obra);
        pedido.setDetalle(new ArrayList<DetallePedido>());
        pedido.addDetalle(detallePedido1);
        pedido.addDetalle(detallePedido2);

        pedidoRepository.save(pedido);

        assertThat(pedidoRepository.count(), is(equalTo(4L)));
    }
	
	


}
