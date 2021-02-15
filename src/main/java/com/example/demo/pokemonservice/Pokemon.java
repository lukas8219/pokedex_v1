package com.example.demo.pokemonservice;
import java.util.ArrayList;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/*
 * Classe utilizada para mapear JSON da APi do Pokemon em objeto Java
 * para ser manipulado como uma Bean.
 */

@JsonDeserialize(using = pokemonDeserializer.class)
public class Pokemon {
	
	private String name;
	private String id;
	private String url;
	private ArrayList<HashMap<String, String>> evolutions;
	private String species_id;
	private ArrayList<String> types;
	private HashMap<String, Integer> baseStatus;
	
	public Pokemon(@JsonProperty("name") String name, @JsonProperty("id") String id,
			@JsonProperty("url") String url, @JsonProperty("types") ArrayList<String >types) {
		this.name = name;
		this.id = id;
		this.url = url;
		this.types = types;
		this.evolutions = new ArrayList<>();
	}
	
	
	public Pokemon(String name2, int id2, HashMap<String, Integer> baseStatus, ArrayList<String> types) {
		this.name = name2;
		this.id = String.valueOf(id2);
		this.baseStatus = baseStatus;
		this.types = types;
	}


	public String toString() {
		return this.name;
	}
	
	@JsonProperty("name")
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("id")
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("url")
	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}
	
	@JsonProperty("types")
	public ArrayList<String> getTypes() {
		return types;
	}


	public void setTypes(ArrayList<String> types) {
		this.types = types;
	}

	@JsonProperty("evolutions")
	public ArrayList<HashMap<String, String>> getEvolutions() {
		return evolutions;
	}


	public void setEvolutions(ArrayList<HashMap<String, String>> map) {
		this.evolutions = map;
	}
	
	public void insertEvolution(HashMap<String, String> temp) {
		this.evolutions.add(temp);
	}

	
	@JsonProperty("base_status")
	public HashMap<String, Integer> getBaseStatus() {
		return baseStatus;
	}


	public void setBaseStatus(HashMap<String, Integer> baseStatus) {
		this.baseStatus = baseStatus;
	}
}
