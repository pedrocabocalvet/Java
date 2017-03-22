import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Inicio {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Document htmlFile = null;
		String titulo;
		
		///////////////////////// ACORDARSE DE AÑADIR LA LIBRERIA JSOUP ///////////////////////////////////////////////////////
		
		ArrayList<String> nombresArray = new ArrayList<String>();
		ArrayList<String> preciosArray = new ArrayList<String>();
		try {
			//htmlFile= Jsoup.parse(new File("pccomponentes.html"),"UTF-8");	// con esta fila lo saco del fichero adjunto
			htmlFile= Jsoup.parse(new URL("https://www.pccomponentes.com/"), 1000);	// con esta linea lo saco de la url puesta
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// aqui saco el titulo de la pagina
		titulo= htmlFile.title();
		
		// aqui saco todos los elementos que tengan esta clase
		Elements nombres = htmlFile.getElementsByClass("GTM-productClick enlace-disimulado");
		Elements precios = htmlFile.getElementsByClass("tarjeta-articulo__precio-actual");
		
		// cojo el texto de cada uno de los elementos
		for(int x = 0; x < nombres.size(); x++){
			nombresArray.add(nombres.get(x).text());
		}
		// cojo el texto de cada uno de los elementos
		for(int x = 0; x < precios.size(); x++){
			preciosArray.add(precios.get(x).text());
		}
		// imprimo el resultado
		for(int x = 0; x < nombresArray.size(); x++){
			System.out.println("Artículo: "+nombresArray.get(x)+" - Precio: "+preciosArray.get(x));
		}
		
		
		
	}

}
