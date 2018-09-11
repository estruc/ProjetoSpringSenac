package br.com.senac.inicilizacao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.senac.dominio.Aluno;
import br.com.senac.dominio.Categoria;
import br.com.senac.dominio.Cidade;
import br.com.senac.dominio.Curso;
import br.com.senac.dominio.Endereco;
import br.com.senac.dominio.Estado;
import br.com.senac.dominio.ItemPedido;
import br.com.senac.dominio.Pagamento;
import br.com.senac.dominio.PagamentoComBoleto;
import br.com.senac.dominio.PagamentoComCartao;
import br.com.senac.dominio.Pedido;
import br.com.senac.dominio.enums.Perfil;
import br.com.senac.dominio.enums.StatusPagamento;
import br.com.senac.repositorio.AlunoRepositorio;
import br.com.senac.repositorio.CategoriaRepositorio;
import br.com.senac.repositorio.CidadeRepositorio;
import br.com.senac.repositorio.CursoRepositorio;
import br.com.senac.repositorio.EnderecoRepositorio;
import br.com.senac.repositorio.EstadoRepositorio;
import br.com.senac.repositorio.ItemPedidoRepositorio;
import br.com.senac.repositorio.PagamentoRepositorio;
import br.com.senac.repositorio.PedidoRepositorio;
import br.com.senac.servico.S3Service;

@Component
public class Init implements ApplicationListener<ContextRefreshedEvent> {

	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	AlunoRepositorio alunoRepositorio;
	
	@Autowired
	EstadoRepositorio estadoRepositorio;
	
	@Autowired
	CidadeRepositorio cidadeRepositorio;
	
	
	@Autowired
	EnderecoRepositorio enderecoRepositorio;
	
	
	@Autowired
	PedidoRepositorio  pedidoRepositorio;
	
	@Autowired
	PagamentoRepositorio  pagamentoRepositorio;

	@Autowired
	CategoriaRepositorio categoriaRepositorio;
	
	@Autowired
	CursoRepositorio cursoRepositorio;
	
	
	@Autowired
	ItemPedidoRepositorio itemPedidoRepositorio;
	
	@Autowired
	private S3Service s3Service;
	
	
	
	public void onApplicationEvent(ContextRefreshedEvent event)   {
		
		Aluno aluno1 = new Aluno();
		aluno1.setNome("Lucas");
		aluno1.setEmail("lucas@gmail.com");
		aluno1.setSenha(pe.encode("123456"));
		
		Aluno aluno2 = new Aluno();
		aluno2.setNome("Joao");
		aluno2.setEmail("joao@gmail.com");
		aluno2.setSenha(pe.encode("123456"));
		
		aluno2.addPerfil(Perfil.MONITOR);
		
					
		Estado estado1 = new Estado();
		estado1.setNome("Rio de Janeiro");
		
		Estado estado2 = new Estado();
		estado2.setNome("São Paulo");
		
		Cidade cidade1 = new Cidade();
		cidade1.setNome("Angra dos Reis");
		cidade1.setEstado(estado1);
		
		Cidade cidade2 = new Cidade();
		cidade2.setNome("Cabo Frio");
		cidade2.setEstado(estado1);
		
		Cidade cidade3 = new Cidade();
		cidade3.setNome("Mogi das Cruzes");
		cidade3.setEstado(estado2);
					
		estadoRepositorio.saveAll(Arrays.asList(estado1,estado2));
		
		cidadeRepositorio.saveAll(Arrays.asList(cidade1,cidade2,cidade3));
		
		Endereco end1 = new Endereco();
		end1.setRua("Rua dos Andradas");
		end1.setNumero("20");
		end1.setBairro("Centro");
		end1.setComplemento("Bloco B");
		end1.setCep("22341-175");
		end1.setCidade(cidade1);
		end1.setAluno(aluno1);
		
		Endereco end2 = new Endereco();
		end2.setRua("Rua dos Marrecos");
		end2.setNumero("68");
		end2.setBairro("Laje");
		end2.setComplemento("Funos");
		end2.setCep("21572-201");
		end2.setCidade(cidade2);
		end2.setAluno(aluno1);
		
		Endereco end3 = new Endereco();
		end3.setRua("Rua dos Andradas");
		end3.setNumero("80");
		end3.setBairro("Centro");
		end3.setComplemento("Casa a");
		end3.setCep("21572-342");
		end3.setCidade(cidade2);
		end3.setAluno(aluno2);
		
		aluno1.getTelefones().addAll(Arrays.asList("232323243","232323234"));
		aluno2.getTelefones().addAll(Arrays.asList("454545454","354345345"));
						
		alunoRepositorio.save(aluno1);
		alunoRepositorio.save(aluno2);
		
		enderecoRepositorio.saveAll(Arrays.asList(end1,end2,end3));
		
		// Criando categoria
		
		Categoria categoria1 = new Categoria(null, "Java");
		
		Categoria categoria2 = new Categoria(null, "Mobile");
		
		Categoria categoria3 = new Categoria(null, "Desktop");
		Categoria categoria4 = new Categoria(null, "Game");
		Categoria categoria5 = new Categoria(null, "Design");
		Categoria categoria6 = new Categoria(null, "UX");
		Categoria categoria7 = new Categoria(null, "IOS");
		Categoria categoria8 = new Categoria(null, "Android");
		
		
		categoriaRepositorio.saveAll(Arrays.asList(categoria1,categoria2,categoria3,categoria4,categoria5,categoria6,categoria7,categoria8));
		
		// criando curso
		

		Curso curso1 = new Curso(null,"Java", "Java para Iniciante", 200.00);
		
		Curso curso2 = new Curso(null,"Java II", "Java Intermediãrio", 400.00);
		
		
		curso1.setCategorias(Arrays.asList(categoria1,categoria2));
		
		curso2.setCategorias(Arrays.asList(categoria1));
		
		cursoRepositorio.saveAll(Arrays.asList(curso1,curso2));
		
		
		
		// Criando o pedido
		Pedido ped1 = new Pedido();
		ped1.setAluno(aluno1);
		ped1.setEnderecoDeEntrega(end1);
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
	
		try {
			//fez o pedidio nesta data
			ped1.setDataPedido(sdf.parse("27/06/2018 09:08"));
		

		     Pagamento pag1 = new PagamentoComBoleto(null,StatusPagamento.CANCELADO,ped1,sdf.parse("30/06/2018 00:00"),sdf.parse("29/06/2018 00:00"));
		     
		     
		   //  Pagamento pag2 = new PagamentoComCartao(null, StatusPagamento.PENDENTE, ped1, 3);
			
				ped1.setPagamento(pag1);
				
				// salvado o pedido
				pedidoRepositorio.save(ped1);
				
				//salvando pagamento
				pagamentoRepositorio.save(pag1);
				
				
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//criando os itens
		ItemPedido item1 = new ItemPedido(ped1, curso1, 0.00,1, 200.00);
		ItemPedido item2 = new ItemPedido(ped1, curso2, 10.00,1, 390.00);
		
		
		//criando a lista de itens para um pedido
		Set<ItemPedido> listaItens1 = new HashSet();
		listaItens1.add(item1);
		listaItens1.add(item2);
		ped1.setItens(listaItens1);
		
		
		//indicando qual lista de Itens o curso está 
		
		curso1.setItens(listaItens1);
		curso2.setItens(listaItens1);
		
		
		itemPedidoRepositorio.saveAll(Arrays.asList(item1,item2));
		
		s3Service.uploadFile("D:\\projetos\\senac\\javaweb2\\img\\html5.png");
		
		
	}
	
}
