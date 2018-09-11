package br.com.senac.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.senac.dominio.Aluno;
import br.com.senac.repositorio.AlunoRepositorio;
import br.com.senac.security.UsuarioSS;

@Service
public class AlunoDetailsService implements UserDetailsService {

	@Autowired
	private AlunoRepositorio repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Aluno aluno = repo.findByEmail(email);
		if (aluno == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UsuarioSS(aluno.getId(), aluno.getEmail(), aluno.getSenha(), aluno.getPerfis());
	}
}