package com.example.demo.pokemonservice;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = evolutionDeserialize.class)
public class EvolutionChain {

	
	private List<Pokemon> evolution_chain;
	private int chain_id;
	
	public EvolutionChain() {
		this.evolution_chain = new ArrayList<>();
		this.chain_id = 0;
	}
	
	public List<Pokemon> getEvolution_chain() {
		return evolution_chain;
	}
	public void setEvolution_chain(List<Pokemon> evolution_chain) {
		this.evolution_chain = evolution_chain;
	}
	public int getChain_id() {
		return chain_id;
	}
	public void setChain_id(int chain_id) {
		this.chain_id = chain_id;
	}
	
	public void insertEvolution(Pokemon p) {
		this.evolution_chain.add(p);
	}
	
}
