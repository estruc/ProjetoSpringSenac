package br.com.senac.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.senac.dominio.Curso;
import br.com.senac.servico.CursoService;

@Controller
public class CursoController {

	@Autowired
	private CursoService cursoService;
	
	
	@GetMapping("/buscarCurso")
	public ModelAndView buscaCurso() {
		ModelAndView mv = new ModelAndView("curso/paginaBuscaCurso");
		Curso curso = new Curso();
		List<Curso> lista = new ArrayList();
		mv.addObject("cursos", lista);
		mv.addObject("curso", curso);
		return mv;
	}
	
	@PostMapping("/listarCurso")
	public ModelAndView listarCurso(Curso curso) {
		ModelAndView mv = new ModelAndView("curso/paginaBuscaCurso");
		mv.addObject("cursos", cursoService.buscarPorNome(curso.getNome()));
		return mv;
	}
	
	
}
