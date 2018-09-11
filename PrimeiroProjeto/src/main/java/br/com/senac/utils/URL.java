package br.com.senac.utils;

import java.util.ArrayList;
import java.util.List;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class URL {

	
	public static List<Integer> decodeIntList(String s) {
		String[] vet = s.split(",");
		List<Integer> list = new ArrayList<>();
		for (int i=0; i<vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		return list;
		// também posso fazer assim
		//return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
	
	
	
	// tratamento de espaco na string
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} 
		catch (UnsupportedEncodingException e) {
			return "";
		}
	} 
	
}
