package com.example.demo.userService;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.tomcat.util.json.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import com.example.demo.pokemonservice.Pokemon;
import com.example.demo.pokemonservice.PokemonList;
import com.example.demo.pokemonservice.PokemonListPag;

class userServiceTest {

	//test userService
	//vou implementar os mesmo testes utilizados na conexão com a API externa, porém usando endereço local.
	
	RestTemplate test_template = new RestTemplate();
	
	@Test
	public void testNumberOne() throws MalformedURLException, ParseException, IOException {

		Pokemon poke = test_template.getForObject("http://localhost:8080/userService/pokemon/1", Pokemon.class);
		assertEquals("bulbasaur", poke.getName());
		
		poke = test_template.getForObject("http://localhost:8080/userService/pokemon/3", Pokemon.class);
		assertEquals("venusaur", poke.getName());
		
		assertTrue(poke.getEvolutions().toString().contains("bulbasaur"));
	}
	
	//Testing EEVEE for Multiple evolutions. #133
	//Tem 8 possiveis evoluções.
	@Test
	public void testNumberTwo() {
		Pokemon poke = test_template.getForObject("http://localhost:8080/userService/pokemon/133", Pokemon.class);
		assertEquals(8, poke.getEvolutions().size());
	}
	
	//Teste da Lista Paginada de Pokemons.
	@Test
	public void testNumberThree() {
		PokemonListPag pl = test_template.getForObject("http://localhost:8080/userService/pokemon/pokedex/?from=1&to=5", PokemonListPag.class);
		
		assertTrue(pl.getListSize() == 5); //testando se a lista mostra 5 pokemons.
		pl = test_template.getForObject("http://localhost:8080/userService/pokemon/pokedex/?from=6&to=10", PokemonListPag.class);
		pl.setPathFrom("6");
		pl.setPathTo("10"); //preciso setar isso, para ela me retornar uma pokedex paginada.
		
		//todos maps tem valores de level minimo, nome e url.
		for(int i : pl.getPokedex().keySet()) {
			Pokemon pk = pl.getPokedex().get(i);
			pk.getEvolutions().toString().contains("min_level_to_evolve");
			pk.getEvolutions().toString().contains("name");
			pk.getEvolutions().toString().contains("url");
		}
	}

}
