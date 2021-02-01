package com.example.demo.pokemonservice;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;



/*
 * Class que implementa uma interface da Pokedex.
 * Utilizada para gerar o JSON paginado.
 */

public class PokemonListPag implements PokeListInterface{
	
	private String next, prev;
	private HashMap<Integer, Pokemon> pokedex;
	private int listSize, to, from, totalPokemons;

	
	public PokemonListPag(PokemonList pokedex, String pathFrom, String pathTo) {
		this.totalPokemons = pokedex.getSize();
		this.pokedex = pokedex.getPokedex();
		this.to = Integer.parseInt(pathTo);
		this.from = Integer.parseInt(pathFrom);
		this.listSize = to-from+1;
		setNext();
		setPrev();
	}
	
	public PokemonListPag(@JsonProperty("TotalPokemon") int total, @JsonProperty("next") String next,
			@JsonProperty("size") int list_size, @JsonProperty("pokedex") HashMap<Integer, Pokemon> poke
			, @JsonProperty("prev") String prev) {
		this.next = next;
		this.listSize = list_size;
		this.prev = prev;
		this.pokedex = poke;
		this.totalPokemons = poke.size();
	}
	
	public void setPathFrom(String pathFrom) {
		this.from = Integer.parseInt(pathFrom);
	}
	
	public void setPathTo(String pathTo) {
		this.to = Integer.parseInt(pathTo);
	}
	
	public void setNext() {
		int diff = to - from; // 30
		if(to+diff > 898) {
			this.next = "http://localhost:8080/userService/pokemon/pokedex/?from="+to+"&to="+898;
		} else {
			this.next = "http://localhost:8080/userService/pokemon/pokedex/?from="+(to+1)+"&to="+(to+1+diff); //from=71 to=100
		}
	}
	
	public void setPrev() {
		int diff = to - from; // 30
		if(from-1-diff < 1) {
			this.prev = "http://localhost:8080/userService/?from="+1+"&to="+from;
		} else {
			this.prev = "http://localhost:8080/userService/?from="+(from-1-diff)+"&to="+(+from-1);
		}
	}
	
	@Override
	public void insertPokemon(Pokemon poke, int id) {
		// TODO Auto-generated method stub
		
	}
	
	@JsonProperty("size")
	public int getListSize() {
		return this.listSize;
	}
	
	@Override
	@JsonProperty("TotalPokemon")
	public int getSize() {
		// TODO Auto-generated method stub
		return totalPokemons;
	}

	@Override
	@JsonProperty("pokedex")
	public HashMap<Integer, Pokemon> getPokedex() {
		// TODO Auto-generated method stub
		HashMap<Integer,Pokemon> temporaryPokedex = new HashMap<>();
		
		for(int i=from; i<=to; i++) {
			temporaryPokedex.put(i, pokedex.get(i));
		}
		
		return temporaryPokedex;
	}

	@JsonProperty("next")
	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}
	@JsonProperty("prev")
	public String getPrev() {
		return prev;
	}

	public void setPrev(String prev) {
		this.prev = prev;
	}
}
