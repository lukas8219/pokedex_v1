package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.pokemonservice.Chain;
import com.example.demo.pokemonservice.EvolutionChain;
import com.example.demo.pokemonservice.IDParser;
import com.example.demo.pokemonservice.PokeListInterface;
import com.example.demo.pokemonservice.Pokemon;
import com.example.demo.pokemonservice.PokemonList;
import com.example.demo.pokemonservice.PokemonListPag;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;

@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/apidatabase/pokemon/")
public class PokemonController {
	
	@Autowired
	private RestTemplate teste;
	@Autowired
	private PokemonList pokedex;
	
	/*
	 * Função que mapeia um link para conexão com API externa do Pokemon. Chama função getPokemonData.
	 * Utiliza de Cache para apenas poder se chamada uma vez. Unica função com acesso ao PokemonData.
	 * É chamada no getPokemonList, para gerar cachê e mapear na PokeDex.
	 */
	
	@GetMapping(path = "{id}")
	@Cacheable("{id}")
	public Pokemon addPokemonToPokedex(@PathVariable("id") String id) throws ParseException, MalformedURLException, IOException {
		Pokemon pokemon = getPokemonData(id);
		pokedex.insertPokemon(pokemon, Integer.parseInt(id));
		return pokemon;
	}
	
	/*
	 * Função, um pouco além do tamanho que eu queria, porém que mapeia todos os valores para uma classe pokemon
	 * e adiciona as linhas evolutivas no mesmo. Ela verifica se já existe um registro na Pokedex, antes de chamar
	 * a função que buscaria a linha evolutiva na API.
	 */
 	private Pokemon getPokemonData(String id) throws ParseException, MalformedURLException, IOException {
		
		final String pokemonApiURL = "https://pokeapi.co/api/v2/pokemon/"+id;
		
		return teste.getForObject(pokemonApiURL, Pokemon.class);
	}
	
 	/*
 	 * Função que mapeia Request com chaves From e To, as quais retornam JSON paginado utilizando
 	 * a pokedex. Funciona bem até 10~15 valores; Depois começa ficar muito lento.
 	 */
	@GetMapping("pokedex")
	public PokeListInterface getPokemonListFrom(@RequestParam(value = "from", required = false) String pathFrom, @RequestParam(value = "to", required = false) String pathTo) throws ParseException {
		
		if(pathTo == null || pathFrom == null) {
			return pokedex;
		} else {
			int to = Integer.parseInt(pathTo);
			int from = Integer.parseInt(pathFrom);
						
			for(int i=from; i<=to; i++) {
				Pokemon currentPokemon = teste.getForObject("http://localhost:8080/apidatabase/pokemon/"+i, Pokemon.class);
			}
			PokemonListPag temporaryPag = new PokemonListPag(pokedex, pathFrom, pathTo);
			return temporaryPag;
		}
	   }

	/*
	 * Helper Function para mapear a linha evolutiva apartir da URL da Espécie.
	 */
	private EvolutionChain getEvolutionChain(String speciesURL) throws MalformedURLException, ParseException, IOException {
		
		EvolutionChain evo = new ObjectMapper()
				.readValue(new File("data/evo1.json"), EvolutionChain.class);
		
		return evo;
	}
	


}
