package utn.isi.dan.lab01.service;

import utn.isi.dan.lab01.domain.Obra;

public interface ClienteService {

	public Double deudaCliente(Obra id);
	public Double maximoSaldoNegativo(Obra id);
	public Integer situacionCrediticiaBCRA(Obra id);
}
