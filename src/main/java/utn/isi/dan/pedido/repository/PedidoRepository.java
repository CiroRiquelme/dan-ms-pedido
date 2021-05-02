package utn.isi.dan.pedido.repository;

import org.springframework.stereotype.Repository;

import frsf.isi.dan.InMemoryRepository;
import utn.isi.dan.pedido.domain.Pedido;


@Repository
public class PedidoRepository extends InMemoryRepository<Pedido> {

	@Override
	public Integer getId(Pedido arg0) {
		return arg0.getId();
	}

	@Override
	public void setId(Pedido arg0, Integer arg1) {
		arg0.setId(arg1);		
	}
	
	
	

}