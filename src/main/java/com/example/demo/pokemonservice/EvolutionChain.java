package com.example.demo.pokemonservice;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EvolutionChain {

	private Object baby_trigger_item;
	private Chain chain;
	private int id;
	
	public EvolutionChain(@JsonProperty("baby_trigger_item") Object item, @JsonProperty("chain") Chain chain
			,@JsonProperty("id") int id) {
		this.baby_trigger_item = item;
		this.chain = chain;
		this.id = id;
	}
	
	@JsonProperty("baby_trigger_item")
	public Object getBaby_trigger_item() {
		return baby_trigger_item;
	}

	public void setBaby_trigger_item(Object baby_trigger_item) {
		this.baby_trigger_item = baby_trigger_item;
	}
	@JsonProperty("chain")
	public Chain getChain() {
		return chain;
	}

	public void setChain(Chain chain) {
		this.chain = chain;
	}
	
	@JsonProperty("id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
