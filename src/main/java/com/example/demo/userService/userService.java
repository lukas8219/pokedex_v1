package com.example.demo.userService;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.pokemonservice.PokeListInterface;
import com.example.demo.pokemonservice.Pokemon;
import com.example.demo.pokemonservice.PokemonList;
import com.example.demo.pokemonservice.PokemonListPag;

@RestController
@RequestMapping("/userService/")
public class userService {

	//chamar get PokemonData.
	
	/*
	 * Mapeando para utilização com usuário, sem expor contato direto com API.
	 */
	@GetMapping("pokemon/{id}")
	public Pokemon catchPokemon(@PathVariable String id) {
		return new RestTemplate().getForObject("http://localhost:8080/apidatabase/pokemon/"+id, Pokemon.class);
	}
	
	@GetMapping("/pokemon/pokedex/")
	public PokeListInterface getPokemonListFrom(@RequestParam(value = "from", required = false) String pathFrom, @RequestParam(value = "to", required = false) String pathTo) throws ParseException {
		if(pathTo == null || pathFrom == null) {
			PokemonList temp = new RestTemplate().getForObject("http://localhost:8080/apidatabase/pokemon/pokedex/", PokemonList.class);
			return temp;
		} else {
			PokemonListPag temp = new RestTemplate().getForObject("http://localhost:8080/apidatabase/pokemon/pokedex/?from="+pathFrom+"&to="+pathTo, PokemonListPag.class);
			temp.setPathFrom(pathFrom);
			temp.setPathTo(pathTo);
			
			return temp;
		}
	}
}
