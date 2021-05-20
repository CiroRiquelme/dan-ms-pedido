package utn.isi.dan.pedido.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import utn.isi.dan.pedido.domain.Obra;

@Repository
public interface ObraRepositoryDao extends JpaRepository<Obra, Integer>{

}
