package com.policia.negocio.modelo.Libros;


import java.io.Serializable;

public class LibrosResultEntry  implements Serializable {


	public LibrosResultEntry() {
	}

	public Boolean Vigente_Libro;
	public Integer ID_Libro;
	public String NombreLibroESP;
	public String NombreLibroENG;
	public Integer Id_Nivel_Libro;
	public String Fecha_Libro;
}
