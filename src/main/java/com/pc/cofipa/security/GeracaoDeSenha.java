package com.pc.cofipa.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeracaoDeSenha {
	
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("adimcofipa52"));
	}

}
