package Buffered;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ventanaPrincipal extends JFrame implements ActionListener {

	private JPanel contentPane;

	// constructor
	public ventanaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Programón");	// titulo de la ventana
		setVisible(true);		// que sea visible
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JButton btnNewButton1 = new JButton("Comparar dos ficheros de texto");
		btnNewButton1.addActionListener(this);	// le añado el listener
		btnNewButton1.setBounds(104, 11, 215, 23);
		contentPane.add(btnNewButton1);
		
		JButton btnNewButton2 = new JButton("Buscar palabra en fichero");
		btnNewButton2.addActionListener(this);	// le añado el listener
		btnNewButton2.setBounds(104, 45, 215, 23);
		contentPane.add(btnNewButton2);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(this);	// le añado el listener
		btnCerrar.setBounds(104, 213, 215, 23);
		contentPane.add(btnCerrar);
		
		JButton btnNewButton3 = new JButton("Copiar fichero a bytes");
		btnNewButton3.addActionListener(this);
		btnNewButton3.setBounds(104, 113, 215, 23);
		contentPane.add(btnNewButton3);
		
		JButton btnNewButton4 = new JButton("Ordenar palabras");
		btnNewButton4.addActionListener(this);
		btnNewButton4.setBounds(104, 79, 215, 23);
		contentPane.add(btnNewButton4);
		
		JButton btnNewButton5 = new JButton("Rotar o Espejo imagen");
		btnNewButton5.addActionListener(this);
		btnNewButton5.setBounds(104, 147, 215, 23);
		contentPane.add(btnNewButton5);
	}
	
	// metodo que cierra objetos
	public void intentarCerrar(Closeable c){
		
		try {
			c.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getActionCommand()== "Comparar dos ficheros de texto"){
			
			compararContenidos();
			
		}
		
		if(e.getActionCommand()== "Buscar palabra en fichero"){
			
			mensajesBuscarPalabra();
		}
		
		if(e.getActionCommand()== "Ordenar palabras"){
			
			ordenarPalabras();
		}
		
		if(e.getActionCommand()== "Copiar fichero a bytes"){
			
			copiarFicheroABytes();
		}
		
		if(e.getActionCommand()== "Rotar o Espejo imagen"){
			
			cambiarPropiedadesImagen();
		}

		if(e.getActionCommand()== "Cerrar"){
			ventanaConMensaje("Adios");
			this.dispose();
		}
		
	}
	 
	// funcion que le pasamos por parametro un bufferdImage de una imagen y te devuelve el espejo de esa imagen
	// hecho pixel a pixel
	 public BufferedImage espejoImagen(BufferedImage imagenOrigen){
		
		BufferedImage imagenDestino = new BufferedImage(imagenOrigen.getHeight(), imagenOrigen.getWidth(), imagenOrigen.getType());
		
		 
		 for(int y = 0; y < imagenDestino.getHeight() ; y++){
			 for(int x = 0; x < imagenDestino.getWidth(); x++){
				 imagenDestino.setRGB(x, y, imagenOrigen.getRGB(imagenOrigen.getWidth()-1-x, y));				
			 }	
		 }
		 
		 
		 return imagenDestino;
		 
		 
	 }
	 // funcion que le das como parametro un bufferedImage y te lo devuevle girado 90º pixel a pixel
	 
	 public BufferedImage rotarImagen(BufferedImage imagenOrigen){
			
			BufferedImage imagenDestino = new BufferedImage(imagenOrigen.getHeight(), imagenOrigen.getWidth(), imagenOrigen.getType());
			
			 
			 for(int y = 0; y < imagenDestino.getHeight() ; y++){
				 for(int x = 0; x < imagenDestino.getWidth(); x++){
					 imagenDestino.setRGB(imagenOrigen.getHeight() - x - 1, y, imagenOrigen.getRGB(y, x));				
				 }	
			 }
			 

			 return imagenDestino;
			 
			 
		 }
	
	 // aqui gestionaremos la recogida de datos, control de errores, etc. para que dada una foto y un destino dado por
	 // el usuario pueda o hacerle efecto espejo a la foto o pueda girarla 90 º
	private void cambiarPropiedadesImagen() {
		// TODO Auto-generated method stub
		
		String pathFile1 = JOptionPane.showInputDialog("escribe la ruta de la imagen origen");
		String pathFile2 = JOptionPane.showInputDialog("escribe la ruta de la imagen destino");
		
		int seleccion = JOptionPane.showOptionDialog(
				   null,
				   "Que quieres hacer con la imagen", 
				   "Selector de opciones",
				   JOptionPane.YES_NO_CANCEL_OPTION,
				   JOptionPane.QUESTION_MESSAGE,
				   null,    // null para icono por defecto.
				   new Object[] { "Girar 90º", "Hacer espejo"},   // null para YES, NO y CANCEL
				   "opcion 1");
		
		boolean espejo = false;
		// aqui cambio lo que ha puesto el usuario en los botones Girar 90º o hacer espejo a un booleano
		if(seleccion == 0){
			espejo = false;
		}else if(seleccion == 1){
			espejo = true;
		}
		
		if(!pathFile1.isEmpty() && !pathFile2.isEmpty()){
			
			File fileOrigen = new File(pathFile1);
			File fileDestino = new File(pathFile2);
			BufferedImage imagenOrigen = null;
			BufferedImage imagenDestino = null;
			
			
			if(fileOrigen.exists() && !fileDestino.exists()){
				
				try {
					if(fileDestino.createNewFile()){
					
						try {
							imagenOrigen = ImageIO.read(fileOrigen);
							if(!espejo){
								// llamo al metodo para rotar la imagen
								imagenDestino = rotarImagen(imagenOrigen);
							}else if(espejo){
								//llamo al metodo para hacer espejo en la imagen
								imagenDestino = espejoImagen(imagenOrigen);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 // por ultimo cojo el BufferedImage rectificado y lo guardo en el fichero 
						// dado por el usuario como destino
						ImageIO.write(imagenDestino, "jpg", fileDestino);
						
						
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else{
				ventanaConMensaje("El fichero Origen no existe o el fichero destino ya existe");
			}	
		}else{
			ventanaConMensaje("falta una ruta por indicar");
		}
		
		
		
	}

	// funcion que gestionara todo lo necesario para que coja un fichero y ordene alfabeticamente su contenido en orden
	// normal o inverso y lo guarde en un fichero destino, todos los datos seran dados por el usuario.
	private void ordenarPalabras() {
		
		File file1 = null;	// guardaremos el fichero origen
		File file2 = null;	// guardaremos el fichero destino
		FileReader frIn = null;		
		FileWriter fwOut = null;
		BufferedReader brIn = null;
		BufferedWriter bwOut = null;
		boolean ordenNormal = true;		// guardaremos el orden, normal = true o inverso = false
		
		String lineaOriginal;	// la usaremos como comodin para crear un array con las palabras del fichero ordenadas
		
		String pathFile1 = JOptionPane.showInputDialog("escribe la ruta del fichero origen");	// ruta del fichero origen
		
		String pathFile2 = JOptionPane.showInputDialog("escribe la ruta de salida del fichero");	// ruta del fichero destino
		
		// aqui guardaremos en un entero la respuesta del usuario a el orden, normal = 0 y inverso = 1
		int seleccion = JOptionPane.showOptionDialog(
				   null,
				   "Que orden alfabetico quieres", 
				   "Selector de opciones",
				   JOptionPane.YES_NO_CANCEL_OPTION,
				   JOptionPane.QUESTION_MESSAGE,
				   null,    // null para icono por defecto.
				   new Object[] { "Normal", "Inverso"},   // null para YES, NO y CANCEL
				   "opcion 1");
		
		
		// aqui cambiamos el entero sobre el orden a un booleano
		switch (seleccion) {
			case 0:
				ordenNormal = true;
				break;
			case 1:
				ordenNormal = false;
				break;
		}
		
		// capturamos errores de introduccion de datos del usuario, tiene q escribir un origen y un destino, y no dejar
		// en blanco la ruta
		
		if(!pathFile1.isEmpty() && !pathFile2.isEmpty()){
			
			file1 = new File(pathFile1);
			file2 = new File(pathFile2);
		
			// capturamos errores de introduccion de datos del usuario, el origen tiene que existir y el destino no 
			// tiene q existir para q el programa funcione correctamente, si no fuera asi lanzariamos una ventana
			// con el mensaje de error
			
			if(file1.exists() && !file2.exists()){
				
				// necesitamos saber cuantas lineas tiene le fichero origenn para crear un array con el mismo numero
				// de posiciones para que quepan todas las palabras del fichero
				
				int lineas = contarLineas(file1);	
				String arrayPalabras[] = new String[lineas];
				
				try {
					// capturo el posible error al crear el fichero dos
					if(file2.createNewFile()){
						
						// capturo errores al crear todos los elementos que nos hacen falta
						try {
							frIn = new FileReader(file1);
							fwOut = new FileWriter(file2);
							
							brIn = new BufferedReader(frIn);
							bwOut = new BufferedWriter(fwOut);
							
							
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						// capturo posibles excepciones al leer en el fichero origen o escribir en destino
						try {
							
							// guardo el contenido del fichero origen en arrayPalabras[]
							for(int i= 0; i< lineas; i++){							
								arrayPalabras[i] = brIn.readLine();
							}	
							
							// funcion q devuelve el array ordenado alfabeticamente y con el orden que eligamos
							
							arrayPalabras = ordenarArray(arrayPalabras,ordenNormal);
							
							// escribimos el array ya ordenado en destino
							for (int i = 0; i < arrayPalabras.length; i++) {
								bwOut.write(arrayPalabras[i]);
								
								// ponemos un salto de linea para cada palabra exceptuando la ultima palabra q escriba
								if(i != (arrayPalabras.length -1)){
									bwOut.newLine();
								}
								
							}
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							// cerramos todos los elementos que hemos usado para esta aplicacion, capturando sus posibles errores
							intentarCerrar(bwOut);
							intentarCerrar(brIn);
							intentarCerrar(fwOut);
							intentarCerrar(frIn);
							
						}			
						
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}else if(!file1.exists()){
				ventanaConMensaje("El fichero origen no existe");
			}else if(file2.exists()){
				ventanaConMensaje("El fichero salida que quieres crear ya existe");
			}
		
		}else{
			ventanaConMensaje("Falta escribir una ruta");
		}		
	}

	// metodo q le das un array y te lo devuelve ordenado normal o orden inverso dependiendo q le pases 
	// como segundo parametro
	
	private String[] ordenarArray(String[] arrayPalabras, boolean ordenNormal) {
		
		
		 for(int j=0; j<arrayPalabras.length;j++)
		 {
		     for (int i=j+1 ; i<arrayPalabras.length; i++)
		     {
		         if(arrayPalabras[i].compareTo(arrayPalabras[j])<0)
		         {
		             String temp= arrayPalabras[j];
		             arrayPalabras[j]= arrayPalabras[i]; 
		             arrayPalabras[i]=temp;


		         }
		     }

		 }
		 
		 // si pide orden inverso aqui cambiamos el array a orden inverso
		 if(!ordenNormal){
			 
			 String arrayInverso[] = new String[arrayPalabras.length];
			 int contador = 0;
			 
			 for(int x = arrayPalabras.length - 1; x >= 0 ; x--){
				 arrayInverso[contador] = arrayPalabras[x];
				 contador++;
			 }
			
			 arrayPalabras = arrayInverso;
		 }
		
		
		return arrayPalabras;
	}
	

	private void copiarFicheroABytes() {
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		File file1 = null;
		File file2 = null;
		byte[] buffer = new byte[100000];
		
		String pathFile1 = JOptionPane.showInputDialog("escribe la ruta del primer fichero");
		
		String pathFile2 = JOptionPane.showInputDialog("escribe la ruta de salida del fichero");
		
		// controlo que haya escrito alguna ruta en los contentPane
		if(!pathFile1.isEmpty() && !pathFile2.isEmpty()){
			
				file1 = new File(pathFile1);
				file2 = new File(pathFile2);
				
				// aqui controlo que el fichero de lectura exista
				if(file1.exists() ){
					try {
						// aqui capturo posibles fallos que haya al crear los fileInputStream
						fis = new FileInputStream(file1);
						fos = new FileOutputStream(file2);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try {
						// aqui capturo que se puedan crear
						fis.read(buffer);
						fos.write(buffer);
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						
						intentarCerrar(fis);
						intentarCerrar(fos);

					}
				
				}else{
					ventanaConMensaje("El archivo origen no existe");
				}
			
				
			
			
		}else{
			ventanaConMensaje("No has escrito nada en una ruta");
		}
		
		
		
	}

	// metodo que se encarga de coger datos del usuario para comparar dos ficheros y mostrar mensajes, si todo
	// a ido bien o a fallado algo.
	public void compararContenidos(){
		File file1=null;	// crearemos dos file cuando el usuario nos de los string con las rutas
		File file2=null;
		
		
		// primer String con la ruta dada por el usuario
		String pathFile1 = JOptionPane.showInputDialog("escribe la ruta del primer fichero");
		
		// Segundo String con la ruta dada por el usuario
		String pathFile2 = JOptionPane.showInputDialog("escribe la ruta del segundo fichero"); 
				        
		if(pathFile1 != null && pathFile2 != null){

			file1 = new File(pathFile1);
			file2 = new File(pathFile2);
			
			// Controlo que los dos Files existan si no lanzo un mensaje de que no existan
			if(file1.exists() && file2.exists()){
			
				if(this.compararFicheros(file1, file2))	// en este metodo es donde compruebo si son iguales o no
					ventanaConMensaje("Los dos archivos son iguales");					
				else
					ventanaConMensaje("Los dos archivos son distintos");							  
				
			}else
				ventanaConMensaje("Alguno de los dos ficheros no existe");
			
		}else
			ventanaConMensaje("Tienes que poner el nombre de un fichero");
				
	}
	
	// metodo que le das dos fichero y comprueba linea por linea si su contenido es el mismo, devuelve true si son iguales
	// y false si son distintas
	public boolean compararFicheros (File fichero1, File fichero2){
		
		String lineFirstFile;	// guardare la linea del fichero1
		String lineSecondFile;	// guardare la linea del fichero2
		int lineasFicheroUno;	// guardare el numero de lineas del fichero1
		int lineasFicheroDos;	//guardare el numero de lineas del fichero2
		FileReader fr1 = null;
		BufferedReader br1 = null;
		FileReader fr2 = null;
		BufferedReader br2 = null;
		
		// capturo posibles excepciones
		try {
			fr1 = new FileReader(fichero1);
			br1 = new BufferedReader(fr1);
			fr2 = new FileReader(fichero2);
			br2 = new BufferedReader(fr2);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		lineasFicheroUno = contarLineas(fichero1);
		lineasFicheroDos = contarLineas(fichero2);
		
		// si el numero de lineas de los dos ficheros es iguales podrian ser iguales a si que lo compruebo
		if (lineasFicheroUno == lineasFicheroDos) {
			
			try {
				// voy linea por linea en primer fichero hasta q se acabe, si la linea es distinta al del fichero2
				// ya no son iguales asi que salgo con false
				while((lineFirstFile = br1.readLine())!= null){
					
					lineSecondFile = br2.readLine();

					
					if(!lineFirstFile.equals(lineSecondFile)){
						return false;	
					}				
				}	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{	// si el numero de lineas no es igual los ficheros ya no son iguales
			return false;
		}
	
		return true;
	}

	// funcion que sirve para ver cuantas lineas tiene un fichero
	public int contarLineas(File fichero){
		 
		 FileReader fr = null;
		 BufferedReader br = null;
		 int contador = 0;
		 
		 try {
			fr = new FileReader(fichero);
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 try {
			while(br.readLine() != null){
				 contador++;
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		 return contador;
	 }
	
	// recogera informacion del usuario para buscar una palabra en un fichero y la aparicion de esa palabra,
	// luego llamara a un metodo que busca esa palabra en el fichero
	public void mensajesBuscarPalabra(){

		
		// aqui guardaremos la ruta del fichero a buscar
		String pathFile = JOptionPane.showInputDialog("escribe la ruta del fichero a buscar");
		
		// aqui guardaremos la palabra a buscar en el fichero
		String wordSearch = JOptionPane.showInputDialog("escribe la palabra que quieres buscar");
		
		// aqui guardaremos la aparicion a buscar 0 = primera aparicion, 1 = ultima aparicion
		// mas tarde lo reutilizare para saber en que linea esta la palabra en el fichero
		int seleccion = JOptionPane.showOptionDialog(
				   null,
				   "Que aparición de la palabra quieres que busque", 
				   "Selector de opciones",
				   JOptionPane.YES_NO_CANCEL_OPTION,
				   JOptionPane.QUESTION_MESSAGE,
				   null,    // null para icono por defecto.
				   new Object[] { "Primera", "Ultima" },   // null para YES, NO y CANCEL
				   "opcion 1");
		
		// transformamos el int seleccion a un booleano para q se entienda mejor
		boolean primeraAparicion;
		if(seleccion == 0) primeraAparicion = true; else primeraAparicion=false;
		
		File file = new File(pathFile);
		
		// controlo que el fichero escrito por el usuario exista
		if (file.exists()) {
			if(!wordSearch.equals("")){
				// metodo que te devuelve en que linea esta la palabra dentro del file, si no esta devuelve -1
				seleccion = buscarPalabra(file,wordSearch,primeraAparicion);
				
				if(seleccion == -1){
					ventanaConMensaje("La palabra "+wordSearch+" no se encuentra en "+file.getName());
				}else{
					ventanaConMensaje("La palabra "+wordSearch+" se encuentra en "+file.getName()+" en la línea "+seleccion);
				}
			}
			else{
				ventanaConMensaje("No has puesto ninguna palabra");
			}
		}else{
			ventanaConMensaje("El fichero no existe");
		}
		
		
	}
	
	// metodo que buscara una palabra en un fichero en primera o ultima aparacion, la respuesta sera el numero de
	// linea en el que aparece esta palabra y -1 en caso de que no aparezca la palabra
	public int buscarPalabra (File fichero1, String palabra, boolean primera_aparicion){
		
		int respuesta = -1;	// linea donde aparece la palabra, -1 si no aparece
		int contador = 0;	// lo usare para saber en que linea estoy
		String line;		// aqui guardare linea por linea el fichero
		FileReader fr= null;
		BufferedReader br=null;
		
		try {
			fr = new FileReader(fichero1);
			br = new BufferedReader(fr);
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while((line = br.readLine())!=null){
				
				contador++;
				if(line.contentEquals(palabra)){
					respuesta = contador;
					if(primera_aparicion){
						return respuesta;
					}
				}
		
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return respuesta;
	}
	
	// enseña un mensaje con el texto pasado por parametro
	public void ventanaConMensaje(String mensaje){
		JOptionPane.showMessageDialog(
				   null,
				   mensaje);
	}
}
