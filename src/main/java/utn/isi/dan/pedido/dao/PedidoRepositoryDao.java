package utn.isi.dan.pedido.dao;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import utn.isi.dan.pedido.domain.Obra;
import utn.isi.dan.pedido.domain.Pedido;

public interface PedidoRepositoryDao extends JpaRepository<Pedido, Integer> {
	
	List<Pedido> findByObra(Obra obra);
	
}
