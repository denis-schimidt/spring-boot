package com.hackday.elo7.rest.data.jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hackday.elo7.rest.data.jpa.domain.Hotel;
import com.hackday.elo7.rest.data.jpa.repository.HotelRepository;

@RestController
@RequestMapping(path="hoteis", produces="application/json; charset=UTF-8")
public class HotelApiRestController {

	@Autowired
	private HotelRepository repository;

	private static final int TAMANHO_PAGINA = 10;

	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody List<Hotel> listarTodosHoteis(@RequestParam(defaultValue="0",required=false,value="pagina") int pagina,
		@RequestParam(defaultValue="name",required=false,value="campoAOrdenar") String campoAOrdenar,
		@RequestParam(defaultValue="ASC",required=false,value="ordenacao") String ordenacao){

		Pageable paginacao = new PageRequest(pagina, TAMANHO_PAGINA, Direction.fromStringOrNull(ordenacao), campoAOrdenar);

		return repository.findAll(paginacao).getContent();
	}
}
