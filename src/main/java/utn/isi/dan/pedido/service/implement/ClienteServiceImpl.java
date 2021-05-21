package utn.isi.dan.pedido.service.implement;

import org.springframework.stereotype.Service;

import utn.isi.dan.pedido.domain.Obra;
import utn.isi.dan.pedido.service.IClienteService;


@Service
public class ClienteServiceImpl implements IClienteService{

	@Override
	public Double deudaCliente(Obra obra) {
		// TODO Por ahora siempre devuelve 1000, excepto la obra 3
		
		if(obra.getId().equals(3)) {
			return 0.00;
		}
		return  1000.00;
	}

	@Override
	public Double maximoSaldoNegativo(Obra obra) {
		// TODO Auto-generated method stub
		
		if(obra.getId().equals(3)) {
			return 10000.00;
		}
		
		return 0.00;
	}

	@Override
	public Integer situacionCrediticiaBCRA(Obra id) {
		// TODO Auto-generated method stub
		return null;
	}

}
