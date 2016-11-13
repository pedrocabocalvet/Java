
public class Inicio {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		parseLibro pl = new parseLibro();
		pl.parsearFicheroXml("biblioteca.xml");
		pl.parseDocument();
		pl.imprimirLibros();
		
	}

}
