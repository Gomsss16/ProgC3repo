package co.edu.unbosque.proyectoia.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "archivo")
public class Archivo {
	
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private String nombre;
	private String tipo; // txt, docx, img, video
	private String urlRuta;
	private LocalDate fechaSubido; 

	public Archivo() {
		// TODO Auto-generated constructor stub
	}

}
