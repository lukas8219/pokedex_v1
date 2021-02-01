package com.example.demo.pokemonservice;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonProperty;

@Service
public class PokemonList implements PokeListInterface{
	
	/*
	 * Classe que implementar interface da Pokedex
	 * Utilizada para armazenar linhas evolutivas baixadas e pokemons.
	 */
	private HashMap<Integer, Pokemon> pokedex;
	private HashMap<Integer, EvolutionChain> evolution_pokedex;
	private int size;
	
	public PokemonList() {
		this.pokedex = new HashMap<>();
		this.size = 0;
		this.setEvolution_pokedex(new HashMap<>());
	}
	
	public void insertPokemon(Pokemon poke, int id) {
		this.pokedex.put(id, poke);
		this.size++;
	}
	
	@JsonProperty("size")
	public int getSize() {
		return size;
	}
	
	@JsonProperty("pokedex")
	public HashMap<Integer, Pokemon> getPokedex() {
		return this.pokedex;
	}

	public HashMap<Integer, EvolutionChain> getEvolution_pokedex() {
		return evolution_pokedex;
	}

	public void setEvolution_pokedex(HashMap<Integer, EvolutionChain> evolution_pokedex) {
		this.evolution_pokedex = evolution_pokedex;
	}
	
	public void insertEvolutionChain(int idx, EvolutionChain chain) {
		this.evolution_pokedex.put(idx, chain);
	}
}
