package utn.isi.dan.pedido.service.implement;

import org.springframework.stereotype.Service;

import utn.isi.dan.pedido.domain.Obra;
import utn.isi.dan.pedido.service.IClienteService;


@Service
public class ClienteServiceImpl implements IClienteService{

	@Override
	public Double saldoCliente(Obra obra) {
		return  1000.00;
	}

	@Override
	public Double maximoSaldoNegativo(Obra obra) {
		// TODO Auto-generated method stub		
		return 500.00;
	}

	@Override
	public Integer situacionCrediticiaBCRA(Obra id) {
		// TODO Auto-generated method stub
		return null;
	}

}
