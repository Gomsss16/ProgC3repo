package co.edu.unbosque.proyectoia.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "analizadoria")
public class AnalizadorIa {
	
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private String nombre; // Ej: DeepSeek, chatGPT
	private String apiUrl;
	private String apiKey; //si es privada

	public AnalizadorIa() {
		// TODO Auto-generated constructor stub
	}

}
