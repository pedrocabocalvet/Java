import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JDialog;

// Esta clase es la ventana principal, gestiona y organiza las acciones que quiera hacer el usuario, guarda libro, 
// y contiene un arrayList para guardar bloques de libros


public class frame extends JFrame implements ActionListener {

	private JPanel contentPane;
	ArrayList<Libro> libros;	// lo usamos para guardar bloques de libros 


	public frame() {
		libros = new ArrayList<Libro>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Guardar Libro individual");
		btnNewButton.addActionListener(this);
		btnNewButton.setBounds(99, 24, 200, 23);
		contentPane.add(btnNewButton);
		
		JButton btnRecuperar = new JButton("Recuperar libro");
		
		btnRecuperar.addActionListener(this);
		btnRecuperar.setBounds(99, 58, 200, 23);
		contentPane.add(btnRecuperar);
		
		JButton btnGuardarListasDe = new JButton("Guardar listas de libros");
		btnGuardarListasDe.addActionListener(this);
		btnGuardarListasDe.setBounds(99, 92, 200, 23);
		contentPane.add(btnGuardarListasDe);
		
		JButton btnModificarLibro = new JButton("Modificar libro");
		btnModificarLibro.addActionListener(this);
		btnModificarLibro.setBounds(99, 126, 200, 23);
		contentPane.add(btnModificarLibro);
		
		JButton btnExamen = new JButton("Pregunta de examen");
		btnExamen.addActionListener(this);
		btnExamen.setBounds(99, 189, 200, 23);
		contentPane.add(btnExamen);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		switch (e.getActionCommand()){
		case "Guardar Libro individual":
			// llamara al jdialog para introducir datos y poder guardar un solo libro
			
				DatosLibro dialog = new DatosLibro(this,"GUARDAR","Cancel",null);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			
			
			break;
		case "Recuperar libro":
			// introduciendo el titulo del libro buscara ese libro y lo mostrara todos sus datos en una ventana
			// capturando y corrigiendo todos los posibles errores
			
			String libRecuperar = JOptionPane.showInputDialog("escribe el nombre del libro a recuperar");
			
			try{
				if(libRecuperar.isEmpty()){
					// capturamos que el usuario no escriba ningun nombre
					ventanaConMensaje("Hace falta un nombre");
				}else{
					File file = new File(libRecuperar);
					if(file.exists()){
						Libro lib = recuperarLibro(file);
						if(lib == null)
							// capturamos posibles fallos cuando se guardo el libro
							ventanaConMensaje("El libro esta vacio");
						else
							lib.imprimir();
					}else{
						// capturamos si el libro no existe
						ventanaConMensaje("El libro no existe");
					}
				}
			}catch(NullPointerException ex){
				// aqui capturamos si el usuario a pulsado cancelar en el JOptionPane
				ventanaConMensaje("cancelado");
			}
			
			break;
		case "Guardar listas de libros":
			// llamamos al JDialog para que introduzca libros a guardar
			
				DatosLibro dialog2 = new DatosLibro(this,"Continuar","Guardar grupo",null);
				dialog2.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog2.setVisible(true);
			
			
			
			break;
		case "Modificar libro":
			// pediremos al usuario el titulo de un libro para buscar ese fichero y pasarselo al JDialog
			// y asi este podra pintar todos los campos del libro, corrigiendo y capturando todas las posibles
			// excepciones
			
			String libModificar = JOptionPane.showInputDialog("escribe el nombre del libro a modificar");
			
			try{
				if(libModificar.isEmpty()){
					// capturamos que el usuario no escriba ningun titulo
					ventanaConMensaje("Hace falta un nombre");
				}else{
					File file = new File(libModificar);
					if(file.exists()){
						Libro lib = recuperarLibro(file);
						if(lib == null)
							// capturamos que el libro se haya guardado mal
							ventanaConMensaje("El libro esta vacio");
						else{
							DatosLibro dialog3 = new DatosLibro(this,"Modificar","Cancel",lib);
							dialog3.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog3.setVisible(true);
						}
							
					}else{
						// capturamos q el usuario no haya escrito un nombre valido o que no exista
						ventanaConMensaje("El libro no existe");
					}
				}
			}catch(NullPointerException ex){
				// capturamos que el usuario cancele el JOptionPane
				ventanaConMensaje("cancelado");
			}
		
			break;
		
			// EXAMEN DE JUANMI
		case "Pregunta de examen":
			int respuesta = 0;
			ArrayList <String> ficherosABuscar = new ArrayList <String>();
			while(respuesta==0){

				String ficheroPath = JOptionPane.showInputDialog("escribe el nombre del fichero a añadir");
				
				File file = new File(ficheroPath);
				
				if(file.exists()){
					ficherosABuscar.add(ficheroPath);
				}
				else{
					ventanaConMensaje("el fichero no existe no sera añadido");
				}
				
				respuesta = JOptionPane.showOptionDialog(
						   null,
						   "Quiere añadir un fichero mas a la busqueda", 
						   "Selector de opciones",
						   JOptionPane.YES_NO_CANCEL_OPTION,
						   JOptionPane.QUESTION_MESSAGE,
						   null,    // null para icono por defecto.
						   new Object[] { "Si", "No", },   // null para YES, NO y CANCEL
						   "opcion 1");
			}
			
			if(ficherosABuscar.size() >0){
				ArrayList <String> añosDePublicacion = new ArrayList <String>();
				
				añosDePublicacion = funcionQueDevuelveAñosDePublicacion(ficherosABuscar);
				
				String mensaje = "";
				for(int y = 0; y < añosDePublicacion.size(); y++){
					mensaje = mensaje + añosDePublicacion.get(y)+" ";
				}
				ventanaConMensaje(mensaje);
			}
			
			break;
		}
		
	}
	
	
	// funcion que pide el examen de 1 evaluacion de acceso a datos
	private ArrayList<String> funcionQueDevuelveAñosDePublicacion(ArrayList<String> ficherosABuscar) {
		// TODO Auto-generated method stub
		ArrayList <String> años = new ArrayList <String>();
		
		for(int x = 0; x < ficherosABuscar.size(); x++){
			
			Libro l = recuperarLibro(new File(ficherosABuscar.get(x)));
			
			años.add(l.getFecha());
		
			
		}
		
		
		
		return años;
	}

	// este metodo lo que hace es a partir de un file que le pasamos por parametro lo buscara, y lo transformara
	// en objeto Libro (deserializandolo) y lo devolvera, capturando y corrigiendo todas las posbles excepciones
	public Libro recuperarLibro(File file){
		
		Libro libro = null;
		
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			libro = (Libro)ois.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
		
		return libro;
	}
	
	
	// este metodo guardara en un Objeto de la clase Libro en un File (serializandolo), ambos se lo pasamos por parametros,
	// guardando y corrigiendo las posibles excepciones
	
	public void guardarLibro(File file, Libro libro){
		FileOutputStream fout = null;
		ObjectOutputStream out = null;
		
		try {
			fout = new FileOutputStream(file);
			out = new ObjectOutputStream(fout);
			out.writeObject(libro);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
			try {
				out.close();
				fout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	// saca una ventana emergente con el mensaje que le pases por parametro
	public void ventanaConMensaje(String mensaje){
		JOptionPane.showMessageDialog(
				   null,
				   mensaje);
	}
	
	// sirve para añadir Objetos de la clase Libro al ArrayList creado al principio, asi podremos guardar bloques
	// de libros mas fácil
	public void añadirLibroArrayList(Libro lib){
		libros.add(lib);
	}
	
	// resetea el ArrayList
	public void resetearArrayList(){
		libros = new ArrayList<Libro>();
	}
	
	// guarda todos los Objetos de la clase Libro que estan en el ArrayList, uno por uno usando un boucle
	public void guardarTodoArrayListLibro(){
		
		Iterator it = libros.iterator();
		
		while(it.hasNext()){
			Libro libro = (Libro)it.next();
			guardarLibro(new File(libro.getTitulo()),libro);
		}
		
	}
}
