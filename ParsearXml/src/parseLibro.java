import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class parseLibro {
	
	private Document dom = null;	// necesitamos un Document para coger el fichero xml
	private ArrayList<Libro> biblioteca = null;	// aqui guardaremos todos los libros que parseemos
	
	// constructor solo iniciaremos el arrayList
	public parseLibro(){
		biblioteca = new ArrayList<Libro>();
	}
	
	// aqui es donde trasformaremos el fichero xml en un Document
	
	public void parsearFicheroXml(String pathFile){
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		
		try {
			db = dbf.newDocumentBuilder();
			
			dom = db.parse(new File(pathFile));
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	// metodo que se encargara de recorrer todo el document en busca de libros y llamar a los distintos metodos
	// que necesitemos para crear un objeto libro y poder añadirlo al arrayList
	
	public void parseDocument() {
		// obtenemos el elemento raíz
		Element docEle = dom.getDocumentElement();

		// obtenemos el nodelist de elementos
		NodeList nl = docEle.getElementsByTagName("libro");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {

				// obtenemos un elemento de la lista (libro)
				Element el = (Element) nl.item(i);
				
				Libro l = getLibro(el);
				biblioteca.add(l);

			}
		}
	}
	
	// metodo que se encarga de llamar a distintas funciones para poder acabar creando un objeto libro y devolverlo
	private Libro getLibro(Element libroEle){

		String editor = getTextValue(libroEle,"editor");
		int paginas = getIntValue(libroEle,"paginas");
		String titulo = getTextValue(libroEle,"titulo");
		String fecha = getAtributeValue(libroEle,"titulo","anyo");
		ArrayList<String> autores = getTextValues(libroEle,"autor");
		
		String arrayAutores[] = new String[autores.size()];
		
		for(int x = 0; x < arrayAutores.length; x++){
			arrayAutores[x] = autores.get(x);
		}
		
		
		Libro retorno = new Libro(titulo,arrayAutores,fecha,editor,paginas);
		
		return retorno;

	}
	
	// dado un elemento un nombre de etiqueta y un nombre de atributo se encarga de devolver el atributo
	// asociado a esa etiqueta dentro del elemento
	
	private String getAtributeValue(Element libroEle, String tagName, String atributeName) {
		String atributo = null;
		
		NodeList nodo = libroEle.getElementsByTagName(tagName);
		
		if(nodo != null && nodo.getLength() > 0) {
			Element el = (Element)nodo.item(0);
			atributo = el.getAttribute(atributeName);
		}	
		
		return atributo;
	}

	// METODO QUE DEVUELVE UN ARRAYLIST DE SUBELEMENTOS CON LA ETIQUETA NOMBRE QUE ESTEN DENTRO DE LA ETIQUETA PASADA POR PARAMETRO DENTRO
	// DEL ELEMENTO PASADO TAMBIEN POR PARAMETRO
	private ArrayList getTextValues(Element ele, String tagName){
		
		ArrayList<String> valoresRetorno = new ArrayList<String>();
		NodeList nodoAutor = ele.getElementsByTagName(tagName);
		
				Element el = (Element)nodoAutor.item(0);
				
				NodeList nodo_n = el.getElementsByTagName("nombre");
				
				for(int i=0;i<nodo_n.getLength();i++){
					
				Element n=(Element)nodo_n.item(i);
				String res = n.getFirstChild().getTextContent();
				valoresRetorno.add(res);
			}

		return valoresRetorno;
	}
	
	// metodo que dado un elemento y un nombre de etiqueta se encarga de devolver el valor para esa etiqueta
	// dentro del elemento
	
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}		
		return textVal;
	}
	
	// apoyandose en getTextValue este metodo te devuelve el valor en Int de una etiqueta que esta dentro de un
	// elemento
	
	private int getIntValue(Element ele, String tagName){
		return Integer.parseInt(this.getTextValue(ele, tagName));
	}
	
	// metodo que se encarga de ir recorriendo el arrayList y imprimiendo uno por uno, una pequeña descripcion
	// del libro
	
	public void imprimirLibros(){
		
		for(int x = 0; x < biblioteca.size(); x++){
			System.out.println("Libro "+(x+1)+":");
			biblioteca.get(x).imprimir();
		}
		
		
	}

}