package br.com.senac.servico;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.senac.dominio.Aluno;
import br.com.senac.dominio.ItemPedido;
import br.com.senac.dominio.PagamentoComBoleto;
import br.com.senac.dominio.Pedido;
import br.com.senac.dominio.enums.StatusPagamento;
import br.com.senac.repositorio.ItemPedidoRepositorio;
import br.com.senac.repositorio.PagamentoRepositorio;
import br.com.senac.repositorio.PedidoRepositorio;
import br.com.senac.security.UsuarioSS;
import br.com.senac.servico.exception.AuthorizationException;
import br.com.senac.servico.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepositorio repoPedido;

	@Autowired
	private PagamentoRepositorio repoPagamento;

	@Autowired
	private CursoService servCurso;
	
	@Autowired
	private AlunoService servAluno;

	@Autowired
	private ItemPedidoRepositorio itemPedidoRepositorio;

	public Pedido buscar(Integer id) {
		Optional<Pedido> objPedido = repoPedido.findById(id);
		return objPedido.orElseThrow(() -> new ObjectNotFoundException(
				"Pedido não encontrada! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	public Pedido inserir(Pedido pedido) {

		pedido.setId(null);
		pedido.setDataPedido(new Date());
		pedido.getPagamento().setStatusPagamento(StatusPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido); // o pagamento precisa saber quem é o pedido

		// se o pagamento do pedido for do tipo boleto preciso definir a data de
		// vencimento
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) pedido.getPagamento();
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, 7);
			pgto.setDataVencimento(cal.getTime());
			pedido.setPagamento(pgto);
		}
		pedido = repoPedido.save(pedido); // inserindo o pedido
		repoPagamento.save(pedido.getPagamento()); // inserido o pagamento

		// para cada item de pedido preciso indicar o curso, o preco e definir o pedido
		// do item de pedido
		for (ItemPedido item : pedido.getItens()) {
			item.setDesconto(0.0);// nao vou atribuir nenhum desconto
			item.setCurso(servCurso.buscar(item.getCurso().getId()));
			item.setValor(item.getCurso().getPreco());
			item.setPedido(pedido);
		}
		itemPedidoRepositorio.saveAll(pedido.getItens());
		return pedido;

	}
	
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UsuarioSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Aluno aluno =  servAluno.buscar(user.getId());
		return repoPedido.findByAluno(aluno, pageRequest);
	}

}
