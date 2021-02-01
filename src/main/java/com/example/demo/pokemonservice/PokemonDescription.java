package com.example.demo.pokemonservice;

public class PokemonDescription {
	//only to describe evolutions.
	private int min_level;
	private String name;
	private String url;
	
	public PokemonDescription(String name, int min_level, String url) {
		this.name = name;
		this.min_level = min_level;
		this.url = url;
	}

	public int getMin_level() {
		return min_level;
	}

	public void setMin_level(int min_level) {
		this.min_level = min_level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
