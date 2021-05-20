package utn.isi.dan.pedido.service.implement;

import org.springframework.stereotype.Service;

import utn.isi.dan.pedido.domain.Producto;
import utn.isi.dan.pedido.service.IMaterialService;


@Service
public class MaterialServiceImpl implements IMaterialService {

	@Override
	public Integer stockDisponible(Producto m) {

		return 100;
		
	}

}
