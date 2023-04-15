package service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import model.Cliente;
import model.ClienteRepository;
import model.Endereco;
import model.EnderecoRepository;
import service.ClienteService;
import service.ViaCepService;

public class ClienteServiceImpl implements ClienteService {

	//Singleton: Injetar os componentes do spring com...
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ViaCepService viaCepService;
	
	
	@Override
	public Iterable<Cliente> buscarTodos() {
		//vai buscar todos os clientes//
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorid(Long id) {
	//vai buscar todos os clientes pelo id//
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.get();
	}

	@Override
	public void inserir(Cliente cliente, Endereco endereco) {
		salvarClienteComCep(cliente, endereco);
		
	}


	@Override
	public void atualizar(Long id, Cliente cliente) {
		//Buscar cliente por ID, caso exista,
		Optional<Cliente> clienteBd = clienteRepository.findById(id);
		if (cliente.isPresent()) {
			salvarClienteComCep(cliente, null);
		}
	}
	
	private void salvarClienteComCep(Cliente cliente, Endereco endereco) {
		//Verificarse o Endereco do cliente já existe (pelo cep),
		String cep = cliente.getEndereco().getCep();
		enderecoRepository.findById(cep).orElseGet(() ->{
			
		//Caso não exista, integrar com ViaCEP e persistir o retorno,
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		cliente.setEndereco(endereco);
		//Inserir Cliente, vinculando o Endereco (novo ou existente)
		clienteRepository.save(cliente);
	}

	@Override
	public void deletar(Long id) {
		//Deletar cliente por ID.
		clienteRepository.deleteById(id);
		
	}

}
