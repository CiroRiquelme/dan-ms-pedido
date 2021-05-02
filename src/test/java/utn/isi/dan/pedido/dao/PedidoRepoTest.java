package utn.isi.dan.pedido.dao;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.jdbc.Sql;

import com.sun.net.httpserver.Authenticator.Result;

import utn.isi.dan.pedido.domain.Obra;
import utn.isi.dan.pedido.domain.Pedido;

@SpringBootTest
@Profile("testing")
class PedidoRepoTest {

	@Autowired
	PedidoRepositoryDao pedidoRepo;
	
	@Test
	@Sql({"/obra.sql","/pedido.sql"})
	void test() {		
		Long cantida = pedidoRepo.count();
		
		assertThat(cantida , is(2L));
	}
	
	
	@Test
	void testBuscarPorObra() {		
		
	
		Obra obra2 = new Obra();
		obra2.setId(1);
		
		List<Pedido> pedidos = pedidoRepo.findByObra(obra2);
		

		System.out.println(pedidos.toString());
		
		assertThat(pedidos , hasSize(2));
	}
	
	


}
