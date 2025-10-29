package co.edu.unbosque.proyectoia.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "resultadoia")
public class ResultadoIa {
	
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private Long idArchivo;
	private Long idAnalizadorIa;
	private Double porcentaje;

	public ResultadoIa() {
		// TODO Auto-generated constructor stub
	}

}
