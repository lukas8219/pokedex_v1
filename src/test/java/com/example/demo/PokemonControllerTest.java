package com.example.demo;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import com.example.demo.pokemonservice.Pokemon;
import com.example.demo.pokemonservice.PokemonListPag;

import org.apache.tomcat.util.json.ParseException;
import org.json.*;

class PokemonControllerTest {

	RestTemplate test_template = new RestTemplate();
	
	//Test getPokemonData
	//Teste conectando diretamente com apidatabase.
	@Test
	public void testNumberOne() throws MalformedURLException, ParseException, IOException {

		Pokemon poke = test_template.getForObject("http://localhost:8080/apidatabase/pokemon/1", Pokemon.class);
		assertEquals("bulbasaur", poke.getName());
		
		poke = test_template.getForObject("http://localhost:8080/apidatabase/pokemon/3", Pokemon.class);
		assertEquals("venusaur", poke.getName());
		
		assertTrue(poke.getEvolutions().toString().contains("bulbasaur"));
	}
	
	//Testing EEVEE for Multiple evolutions. #133
	//Tem 8 possiveis evoluções.
	@Test
	public void testNumberTwo() {
		Pokemon poke = test_template.getForObject("http://localhost:8080/apidatabase/pokemon/133", Pokemon.class);
		assertEquals(8, poke.getEvolutions().size());
	}
	
	//Teste da Lista Paginada de Pokemons.
	@Test
	public void testNumberThree() {
		PokemonListPag pl = test_template.getForObject("http://localhost:8080/apidatabase/pokemon/pokedex/?from=1&to5", PokemonListPag.class);
		System.out.println(pl.getListSize());
		assertTrue(pl.getListSize() == 5); //testando se a lista mostra 5 pokemons.
		pl = test_template.getForObject("http://localhost:8080/apidatabase/pokemon/pokedex/?from=6&to10", PokemonListPag.class);
		
		assertTrue(pl.getPokedex().size() == 10); //Testando o tamanho da pokedex depois de chamar 2 vezes a lista.
		
		pl = test_template.getForObject("http://localhost:8080/apidatabase/pokemon/pokedex/?from=1&to10", PokemonListPag.class);
		
		//testando se o tamanho da lista confere, e apos segunda chamada, não alteramos o tamanho da pokedex
		//visto que são os mesmos valores.
		assertTrue(pl.getListSize() == 10 && pl.getPokedex().size() == 10);
	}
}
