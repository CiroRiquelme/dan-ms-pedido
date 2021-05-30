package utn.isi.dan.pedido.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import utn.isi.dan.pedido.domain.DetallePedido;

@Repository
public interface DetallePedidoRepositoryDao extends JpaRepository<DetallePedido, Integer> {

}
