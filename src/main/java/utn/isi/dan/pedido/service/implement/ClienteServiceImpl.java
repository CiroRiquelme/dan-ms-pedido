package utn.isi.dan.pedido.service.implement;

import org.springframework.stereotype.Service;

import utn.isi.dan.pedido.domain.Obra;
import utn.isi.dan.pedido.service.ClienteService;


@Service
public class ClienteServiceImpl implements ClienteService{

	@Override
	public Double deudaCliente(Obra id) {
		// TODO Por ahora siempre devuelve 1000
		return  1000.00;
	}

	@Override
	public Double maximoSaldoNegativo(Obra id) {
		// TODO Auto-generated method stub
		return 0.00;
	}

	@Override
	public Integer situacionCrediticiaBCRA(Obra id) {
		// TODO Auto-generated method stub
		return null;
	}

}
