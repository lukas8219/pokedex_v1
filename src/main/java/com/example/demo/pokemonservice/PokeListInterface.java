package com.example.demo.pokemonservice;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface PokeListInterface {
	
	public void insertPokemon(Pokemon poke, int id);
	
	@JsonProperty("size")
	public int getSize();
	
	@JsonProperty("pokedex")
	public HashMap<Integer, Pokemon> getPokedex();
	
}
