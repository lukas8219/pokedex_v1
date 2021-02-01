package com.example.demo.pokemonservice;

public class IDParser {

	/*
	 * @param URL com ID do Pokemon.
	 * 
	 * Retorna apenas ID
	 */
	public static String parsePokemon(String url) {
		StringBuilder sb = new StringBuilder(url);
		return sb.substring(34, sb.length()).toString();
	}
	
	/*
	 * @param URL com ID da Linha evolutiva.
	 * 
	 * Retorna apenas ID
	 */
	public static String parseEvolution(String url) {
		StringBuilder sb = new StringBuilder(url);
		return sb.substring(42, sb.length()-1).toString();
	}
	
	/*
	 * @param URL com ID da Espécie.
	 * 
	 * Retorna apenas ID
	 */
	public static String parseSpecie(String url) {
		StringBuilder sb = new StringBuilder(url);
		return sb.substring(42, sb.length()-1).toString();
	}
}
