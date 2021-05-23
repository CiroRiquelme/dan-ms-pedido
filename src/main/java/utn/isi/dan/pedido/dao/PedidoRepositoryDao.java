package utn.isi.dan.pedido.dao;




import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import utn.isi.dan.pedido.domain.Obra;
import utn.isi.dan.pedido.domain.Pedido;

@Repository
public interface PedidoRepositoryDao extends JpaRepository<Pedido, Integer> {
	
	Optional<Pedido>  findByObra(Obra obra);
	
}
