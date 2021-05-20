package utn.isi.dan.pedido.service;

import utn.isi.dan.pedido.domain.Obra;

public interface IClienteService {

	public Double deudaCliente(Obra id);
	public Double maximoSaldoNegativo(Obra id);
	public Integer situacionCrediticiaBCRA(Obra id);
}
