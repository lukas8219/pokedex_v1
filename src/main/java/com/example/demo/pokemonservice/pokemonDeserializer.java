package com.example.demo.pokemonservice;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class pokemonDeserializer extends StdDeserializer<Pokemon>{
	
	public pokemonDeserializer() {
		this(null);
	}
	
	protected pokemonDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Pokemon deserialize(JsonParser parser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		
		JsonNode root = parser.getCodec().readTree(parser);
		
		String name = root.get("name").asText();
		int id = (Integer) root.get("id").numberValue();
		HashMap<String, Integer> BaseStatus = new HashMap<>();
		String[] statusNotation = {"HP","Attack","Defense","Special Attack","Special Defense","Speed"};
		ArrayList<String> types = new ArrayList<>();

		
		JsonNode statsNode = root.get("stats");
		for(int i=0; i<6; i++) {
			int currentStatus = (Integer) statsNode.get(i).get("base_stat").numberValue();
			BaseStatus.put(statusNotation[i], currentStatus);
		}
		
		JsonNode typeNode = root.get("types");
		for(int i=0; i<typeNode.size(); i++) {
			types.add(typeNode.get(i).get("type").get("name").asText());
		}
		
		System.out.println(types);
		return new Pokemon(name, id, BaseStatus, types);
	}

	
}
