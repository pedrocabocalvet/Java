import java.io.Serializable;

import javax.swing.JOptionPane;

// objeto Libro solo contiene los atributos de un libro, sus getters y setters, y un metodo imprimir que imprime 
// en una ventana emergente todos los atributos del objeto

public class Libro implements Serializable {

	
	String titulo = null;
	String autor[] = null;
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
		if(autor.length == 1){
			System.out.println("el autor se llama "+ autor[0] +" que ha escrito el libro "+titulo+" en la fecha "+fecha+" con el editor "+editor+" y un numero de paginas "+numPag);
		}
		else{
			String nombresAutores = "";
			
			for(int x = 0; x < autor.length ; x++){
				nombresAutores = nombresAutores + autor[x];
				
				if(x != (autor.length)-1 && x != (autor.length)-2){
					nombresAutores = nombresAutores + ", ";
				}
				if(x == (autor.length)-2){
					nombresAutores = nombresAutores + " y ";
				}
			}
			nombresAutores = nombresAutores + ".";
			System.out.println("los autores se llaman "+ nombresAutores +" que han escrito el libro "+titulo+" en la fecha "+fecha+" con el editor "+editor+" y un numero de paginas "+numPag);			
		}
	}
	


}
