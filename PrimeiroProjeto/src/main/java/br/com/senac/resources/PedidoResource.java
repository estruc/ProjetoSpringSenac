package br.com.senac.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.senac.dominio.Pedido;
import br.com.senac.servico.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService service;

	// indica que agora a chamada da url vai incluir um id ex: Pedido/1 ou Pedido/2
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) { // PathVariable indica que o que foi mapeado no value
																	// do RequestMapping vai ser passado para o metodo
		Pedido objPedido = service.buscar(id);
		return ResponseEntity.ok().body(objPedido);
	} // ResponseEntity encapsula varios tratamentos http para o rest e incluir o
		// objeto Pedido que encontramos


	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserir(@RequestBody Pedido obj) {

		obj = service.inserir(obj);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<Pedido>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="dataPedido") String orderBy, 
			@RequestParam(value="direction", defaultValue="DESC") String direction) {
		Page<Pedido> list = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list);
	}
	
	
}
