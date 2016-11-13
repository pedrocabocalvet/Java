import java.io.Serializable;

import javax.swing.JOptionPane;

// objeto Libro solo contiene los atributos de un libro, sus getters y setters, y un metodo imprimir que imprime 
// en una ventana emergente todos los atributos del objeto

public class Libro implements Serializable {

	
	String titulo = null;
	// pueden ser varios autores
	String[] autor = null;
	String fecha = null;
	String editor = null;
	int numPag = 0;
	
	public Libro (String tit, String[] au, String fe, String ed, int num){
		
		titulo = tit;
		autor = au;
		fecha = fe;
		editor = ed;
		numPag = num;
		
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String[] getAutor() {
		return autor;
	}

	public void setAutor(String[] autor) {
		this.autor = autor;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public int getNumPag() {
		return numPag;
	}

	public void setNumPag(int numPag) {
		this.numPag = numPag;
	}
	
	// imprime en una ventana emergente todos los atributos del libro
	public void imprimir(){
		ventanaConMensaje("el autor se llama "+ autor +" que ha escrito el libro "+titulo+" en la fecha "+fecha+" con el editor "+editor+" y un numero de paginas "+numPag);
	}
	
	// crea una ventana emergente con el String que le pasemos por parametro
	public void ventanaConMensaje(String mensaje){
		JOptionPane.showMessageDialog(
				   null,
				   mensaje);
	}

}
