package utn.isi.dan.pedido.dao;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import utn.isi.dan.pedido.domain.Pedido;

@Repository
public interface PedidoRepositoryDao extends JpaRepository<Pedido, Integer> {

	List<Pedido> findByObraId(Integer idObra);
	
	List<Pedido> findByEstadoId(Integer idEstadoPedido);

	
	
	
	
}
