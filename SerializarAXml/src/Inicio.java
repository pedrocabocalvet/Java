import java.io.File;
import java.util.ArrayList;

// clase principal contiene el main, iniciaremos todo el programa desde aqui

public class Inicio {

	public static void main(String[] args) {
		
		ArrayList <Libro> libros = new ArrayList<Libro>();
		
		String[] nombres = {"pepito","julito"};		
		Libro l1 = new Libro("mi tio teo lee",nombres , "10-01-2014", "Alfaguara", 160);
		String[] nombres2 = {"Joselito","Manolito"};	
		Libro l2 = new Libro("pulgarcito", nombres2, "15-08-2015", "Santillana", 320);
		
		libros.add(l1);
		libros.add(l2);
		
		// aqui haremos la estructura dom, y la guardaremos en un xml
		Marshaller marshaller = new Marshaller(libros);
		marshaller.crearDocument();
		marshaller.crearArbol();
		marshaller.escribirAXml(new File("probeteando.xml"));

	}

}
