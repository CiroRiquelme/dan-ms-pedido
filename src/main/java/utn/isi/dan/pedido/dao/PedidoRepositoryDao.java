package utn.isi.dan.pedido.dao;




import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import utn.isi.dan.pedido.domain.Obra;
import utn.isi.dan.pedido.domain.Pedido;

public interface PedidoRepositoryDao extends JpaRepository<Pedido, Integer> {
	
	Optional<Pedido>  findByObra(Obra obra);
	
}
