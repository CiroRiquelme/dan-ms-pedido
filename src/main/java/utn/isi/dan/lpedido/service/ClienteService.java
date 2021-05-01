package utn.isi.dan.lpedido.service;

import utn.isi.dan.pedido.domain.Obra;

public interface ClienteService {

	public Double deudaCliente(Obra id);
	public Double maximoSaldoNegativo(Obra id);
	public Integer situacionCrediticiaBCRA(Obra id);
}
