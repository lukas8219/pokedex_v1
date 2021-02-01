package com.example.demo.pokemonservice;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;
/*
 *Classe utilizada para mapear os elementos do JSON 
 *para um Objeto Java, para então ser manipulado
 */

public class Chain {

	private Object[] evolution_details;
	private Chain[] evolves_to;
	private boolean is_baby;
	private HashMap<String, String> species;
	
	public Chain(@JsonProperty("evolution_details") Object[] details, @JsonProperty("evolves_to") Chain[] chain
			, @JsonProperty("is_baby") boolean isBaby, @JsonProperty("species") HashMap<String, String> spe) {
		this.evolution_details = details;
		this.evolves_to = chain;
		this.is_baby = isBaby;
		this.species = spe;
	}

	@JsonProperty("evolution_details")
	public Object[] getEvolution_details() {
		return evolution_details;
	}

	public void setEvolution_details(Object[] evolution_details) {
		this.evolution_details = evolution_details;
	}
	@JsonProperty("evolves_to")
	public Chain[] getEvolves_to() {
		return evolves_to;
	}

	public void setEvolves_to(Chain[] evolves_to) {
		this.evolves_to = evolves_to;
	}
	@JsonProperty("is_baby")
	public boolean isIs_baby() {
		return is_baby;
	}

	public void setIs_baby(boolean is_baby) {
		this.is_baby = is_baby;
	}
	@JsonProperty("species")
	public HashMap<String, String> getSpecies() {
		return species;
	}

	public void setSpecies(HashMap<String, String> species) {
		this.species = species;
	}
}

