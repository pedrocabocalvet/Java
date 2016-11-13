import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

// esta clase se encargara de generar un Document, crear la estructura dom, ir anidando todos los elementos y subelementos,
// y por ultimo guardarlo en un file xml

public class Marshaller {
	
	ArrayList <Libro> libros;	// aqui guardaremos los libros que vallamos a convertir en xml
	Document dom = null;		

	public Marshaller(ArrayList <Libro> e){
		
		libros = e;
	}
	
	// en este metodo unicamente inicializaremos el document dom.
	public void crearDocument(){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dom = db.newDocument();
	}
	
	// aqui se crea el arbol
	public void crearArbol(){
		
		// empezamos poniendo el elemnto raiz del dom
		Element docEle = dom.createElement("libros");
		dom.appendChild(docEle);
		
		Iterator it = libros.iterator();
		
		// por cada libro que haya generaremos nuevos elemento libro que anidaremos en el elemento raiz libros
		while(it.hasNext()){
			Libro l = (Libro) it.next();
			Element ele = setLibro(l);	// este metodo se encarga de devolver un elemento de Libro ya creado
			docEle.appendChild(ele);	// aqui lo añadimos al elemento raiz
		}
		
	}
	// aqui transformaremos un objeto Libro en un elemento libro para anidar al principal, con todas sus etiquetas
	public Element setLibro(Libro libro){
		
		Element libroEle = dom.createElement("libro");
		
		Element titulo = dom.createElement("titulo");
		Text textoTitulo= dom.createTextNode(libro.getTitulo());
		
		titulo.appendChild(textoTitulo);
		libroEle.appendChild(titulo);

		Element autor = setAutores(libro.getAutor());	// metodo q devuelve un elemento de los autores
		libroEle.appendChild(autor);

		Element fecha = dom.createElement("fecha");
		Text textoFecha= dom.createTextNode(libro.getFecha());
		
		fecha.appendChild(textoFecha);
		libroEle.appendChild(fecha);
		
		Element editor = dom.createElement("editor");
		Text textoEditor= dom.createTextNode(libro.getEditor());
		
		editor.appendChild(textoEditor);
		libroEle.appendChild(editor);
		
		Element paginas = dom.createElement("paginas");
		Text textoPaginas= dom.createTextNode(Integer.toString(libro.getNumPag()));
		
		paginas.appendChild(textoPaginas);
		libroEle.appendChild(paginas);
		
		return libroEle;
	}
	
	// devuelve un elemento de los autores dado un array de autores
	private Element setAutores(String[] arrayAutores) {
		
		Element autores = dom.createElement("autores");
		
		for(int x = 0; x < arrayAutores.length; x++){
			Element nomAutor = dom.createElement("nombre");
			Text textoNombre = dom.createTextNode(arrayAutores[x]);
			nomAutor.appendChild(textoNombre);
			autores.appendChild(nomAutor);
		}

		return autores;
	}

	//escribe el dom en un file pasado por parametro
	public void escribirAXml(File file){
		
		Transformer trans = null;
		
		try {
			trans = TransformerFactory.newInstance().newTransformer();
			
		
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// estas dos lineas sirven para indentar el xml y q salga mas legible
		trans.setOutputProperty(OutputKeys.INDENT, "yes");
		trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "6");
		
		StreamResult result = new StreamResult(file);
		DOMSource source = new DOMSource(dom);
		try {
			trans.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
