package utn.isi.dan.pedido.rest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


import utn.isi.dan.pedido.PedidosApplicationTests;
import utn.isi.dan.pedido.domain.DetallePedido;
import utn.isi.dan.pedido.domain.Obra;
import utn.isi.dan.pedido.domain.Pedido;
import utn.isi.dan.pedido.domain.Producto;

@SpringBootTest(classes = PedidosApplicationTests.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@Disabled
public class PedidoRestTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@LocalServerPort
	String puerto;
	
	Pedido pedido;
	
	String url;
	
    @BeforeEach
    public void setUp() {

        url = "http://localhost:" + puerto + "/api/pedido";

        Obra obra = new Obra();
        obra.setId(1);

        Producto p1 = new Producto();
        Producto p2 = new Producto();
        Producto p3 = new Producto();
        p1.setId(1);
        p2.setId(2);
        p3.setId(3);

        DetallePedido d1 = new DetallePedido(p1, 1, 100.0);
        DetallePedido d2 = new DetallePedido(p2, 2, 50.0);
        DetallePedido d3 = new DetallePedido(p3, 3, 100.0);

        pedido = new Pedido();
        pedido.setFechaPedido(Instant.now());
        pedido.setObra(obra);
        pedido.setDetalle(new ArrayList<DetallePedido>());
        pedido.addDetalle(d1);
        pedido.addDetalle(d2);
        pedido.addDetalle(d3);
    }

    @Test
    public void crear_PedidoOk_Ok() {

        HttpEntity<Pedido> request = new HttpEntity<Pedido>(pedido);
        ResponseEntity<Pedido> response = testRestTemplate.exchange(url, HttpMethod.POST, request, Pedido.class);

        assertFalse(response.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    public void crearPedidoSinObra() {

        pedido.setObra(null);

        HttpEntity<Pedido> request = new HttpEntity<Pedido>(pedido);
        ResponseEntity<Pedido> response = testRestTemplate.exchange(url, HttpMethod.POST, request, Pedido.class);

        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void crearPedidoSinDetalle() {

        pedido.setDetalle(new ArrayList<DetallePedido>());

        HttpEntity<Pedido> request = new HttpEntity<Pedido>(pedido);
        ResponseEntity<Pedido> response = testRestTemplate.exchange(url, HttpMethod.POST, request, Pedido.class);

        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void crearPedidoDetalleVacio() {

        pedido.getDetalle().add(2, new DetallePedido());

        HttpEntity<Pedido> request = new HttpEntity<Pedido>(pedido);
        ResponseEntity<Pedido> response = testRestTemplate.exchange(url, HttpMethod.POST, request, Pedido.class);

        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_GATEWAY));
        
    }

    @Test
    public void CrearPedidoConDetalleConCantidadesInvalidas() {

        DetallePedido d2 = new DetallePedido(new Producto(), -2, 50.0);
        DetallePedido d3 = new DetallePedido(new Producto(), -3, 100.0);
        pedido.getDetalle().add(1, d2);
        pedido.getDetalle().add(2, d3);

        HttpEntity<Pedido> request = new HttpEntity<Pedido>(pedido);
        ResponseEntity<Pedido> response = testRestTemplate.exchange(url, HttpMethod.POST, request, Pedido.class);

        assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }	

}
