package com.example.demo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
		ResponseEntity<String> response = teste.getForEntity(pokemonApiURL, String.class);
		LinkedHashMap<String, Object> parsedJSON = new JSONParser(response.getBody()).parseObject();
		String speciesURL = ((LinkedHashMap<String, Object>) parsedJSON.get("species")).get("url").toString();
		ArrayList<Object> typesJSON = (ArrayList<Object>) parsedJSON.get("types");
		String[] types = new String[typesJSON.size()];
		
		for(int i=0; i<typesJSON.size(); i++) {
			LinkedHashMap<String, Object> currType = (LinkedHashMap<String, Object>) typesJSON.get(i);
			String TYPE = (String) ((LinkedHashMap<String,Object>) currType.get("type")).get("name");
			types[i] = TYPE;
		}
		
		Pokemon currentPokemon = new Pokemon((String) parsedJSON.get("name"), id, "http://localhost:8080/userService/pokemon/"+id,
				types);
		
		EvolutionChain currentPokemonChain = null;
		String evoID = getEvoIDfromSpecies(speciesURL);
		
		if(pokedex.getEvolution_pokedex().containsKey(Integer.parseInt(evoID))) {
			currentPokemonChain = pokedex.getEvolution_pokedex().get(Integer.parseInt(evoID));
		} else {
			currentPokemonChain = getEvolutionChain(speciesURL);
			int chain_id = currentPokemonChain.getId();
			pokedex.insertEvolutionChain(chain_id, currentPokemonChain);
		}

		//if theres already this evo, return the string and add to poke, if not so..
		
		Chain evo_chain = currentPokemonChain.getChain();
		
		while(evo_chain != null) {
			String name = evo_chain.getSpecies().get("name");
			String url = evo_chain.getSpecies().get("url");
			int min_level = 0;
			if(evo_chain.getEvolution_details().length > 0) {
				LinkedHashMap<String, Object> details = (LinkedHashMap<String, Object>) evo_chain.getEvolution_details()[0];
				if(details.get("min_level") != null) {
					min_level = (Integer) details.get("min_level");
				}
			}
			
			if(! name.equals(currentPokemon.getName())){ //if its not the current pokemon.
				HashMap<String, String> currentInfo = new HashMap<>();
				currentInfo.put("name", name);
				currentInfo.put("min_level_to_evolve", String.valueOf(min_level));
				url = IDParser.parseSpecie(url);
				currentInfo.put("url", "http://localhost:8080/userService/pokemon/"+url);
				
				currentPokemon.insertEvolution(currentInfo);
			}
			
			if(evo_chain.getEvolves_to().length > 1) { // processo todos , fora o 0, e depois redefino pro 0.
				for(int i=1; i<evo_chain.getEvolves_to().length; i++) {
					Chain ch = evo_chain.getEvolves_to()[i];
					String forName = ch.getSpecies().get("name");
					String forUrl = ch.getSpecies().get("url");
					int forMin_level = 0;
					if(ch.getEvolution_details().length > 0) {
						LinkedHashMap<String, Object> details = (LinkedHashMap<String, Object>) ch.getEvolution_details()[0];
						if(details.get("min_level") != null) {
							min_level = (Integer) details.get("min_level");
						}
					}
					if(! forName.equals(currentPokemon.getName())){ //if its not the current pokemon.
						HashMap<String, String> currentInfo = new HashMap<>();
						currentInfo.put("name", forName);
						currentInfo.put("min_level_to_evolve", String.valueOf(forMin_level));
						forUrl = IDParser.parseSpecie(forUrl);
						currentInfo.put("url", "http://localhost:8080/userService/pokemon/"+forUrl);
						
						currentPokemon.insertEvolution(currentInfo);
					}
					
				}
				evo_chain = evo_chain.getEvolves_to()[0];
			} else if(evo_chain.getEvolves_to().length == 1){
				evo_chain = evo_chain.getEvolves_to()[0];
			} else {
				evo_chain = null;
			}
			
		}
		
		return currentPokemon;
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
		final String evolutionURL = getEvolutionChainURL(speciesURL);
		ObjectMapper mp = new ObjectMapper();
		mp.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		return mp.readValue(new URL(evolutionURL), EvolutionChain.class);
	}
	
	/*
	 * Helper Function que retorna ID da linha evolutiva, apartir da URL da espécie.
	 */
	private String getEvoIDfromSpecies(String speciesURL) throws ParseException {
		String pokemonApiURL = speciesURL;
		ResponseEntity<String> response = teste.getForEntity(pokemonApiURL, String.class);
		LinkedHashMap<String, Object> parsedJSON = new JSONParser(response.getBody()).parseObject();
		return IDParser.parseEvolution(((LinkedHashMap<String, String>) parsedJSON.get("evolution_chain")).get("url"));
	}
	
	private String getEvolutionChainURL(String speciesURL) throws ParseException, MalformedURLException, IOException {
		String pokemonApiURL = speciesURL;
		ResponseEntity<String> response = teste.getForEntity(pokemonApiURL, String.class);
		LinkedHashMap<String, Object> parsedJSON = new JSONParser(response.getBody()).parseObject();
		return ((LinkedHashMap<String, String>) parsedJSON.get("evolution_chain")).get("url");
	}
}
