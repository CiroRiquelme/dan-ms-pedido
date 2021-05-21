package utn.isi.dan.pedido.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import utn.isi.dan.pedido.domain.DetallePedido;


public interface DetallePedidoRepositoryDao extends JpaRepository<DetallePedido, Integer> {

}
