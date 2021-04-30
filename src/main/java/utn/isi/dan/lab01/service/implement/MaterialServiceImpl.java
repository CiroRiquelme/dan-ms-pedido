package utn.isi.dan.lab01.service.implement;

import org.springframework.stereotype.Service;

import utn.isi.dan.lab01.domain.Producto;
import utn.isi.dan.lab01.service.MaterialService;


@Service
public class MaterialServiceImpl implements MaterialService {

	@Override
	public Integer stockDisponible(Producto m) {

		return 100;
		
	}

}
