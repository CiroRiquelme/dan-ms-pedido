package utn.isi.dan.pedido.service.implement;

import org.springframework.stereotype.Service;

import utn.isi.dan.lpedido.service.MaterialService;
import utn.isi.dan.pedido.domain.Producto;


@Service
public class MaterialServiceImpl implements MaterialService {

	@Override
	public Integer stockDisponible(Producto m) {

		return 100;
		
	}

}
