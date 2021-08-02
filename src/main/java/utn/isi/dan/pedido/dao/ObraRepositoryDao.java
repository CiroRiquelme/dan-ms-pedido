package utn.isi.dan.pedido.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import utn.isi.dan.pedido.domain.Obra;

@Repository
@Transactional(readOnly = true)
public interface ObraRepositoryDao extends JpaRepository<Obra, Integer>{
	
	Optional<Obra> findById(Integer id);

}
