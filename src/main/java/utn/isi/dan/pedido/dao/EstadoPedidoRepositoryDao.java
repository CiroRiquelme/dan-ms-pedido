package utn.isi.dan.pedido.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import utn.isi.dan.pedido.domain.EstadoPedido;

@Repository
public interface EstadoPedidoRepositoryDao extends JpaRepository<EstadoPedido, Integer> {
	
	Optional<EstadoPedido> findByEstado(String estado);
	
	
	

}
