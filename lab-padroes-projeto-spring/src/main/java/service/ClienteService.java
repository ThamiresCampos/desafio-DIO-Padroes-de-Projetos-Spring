package service;

import model.Cliente;
import model.Endereco;

public interface ClienteService {
	
	
	Iterable<Cliente> buscarTodos();
	
	Cliente buscarPorid(Long id);
	
	void inserir (Cliente cliente);
	
	void atualizar (Long id, Cliente cliente);
	
	void deletar(Long id);

	void inserir(Cliente cliente, Endereco endereco);
	

}
