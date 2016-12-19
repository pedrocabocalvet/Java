import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class FramePrincipal extends JFrame implements ActionListener{

	private JPanel contentPane;
	JButton btnComparar;
	JButton btnBuscarPalabra;
	private JButton btnCerrar;
	private JButton btnCopiarByte;


	/**
	 * Create the frame.
	 */
	public FramePrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnComparar = new JButton("Comparar Dos ficheros para ver si son iguales");
		btnComparar.addActionListener(this);
		btnComparar.setActionCommand("comparar");
		btnComparar.setBounds(39, 11, 414, 23);
		contentPane.add(btnComparar);
		
		btnBuscarPalabra = new JButton("Buscar palabra en un fichero y su orden de aparicion");
		btnBuscarPalabra.setActionCommand("buscar");
		btnBuscarPalabra.addActionListener(this);
		btnBuscarPalabra.setBounds(39, 45, 414, 23);
		contentPane.add(btnBuscarPalabra);
		
		btnCerrar = new JButton("Cerrar");
		btnCerrar.setActionCommand("cerrar");
		btnCerrar.addActionListener(this);
		btnCerrar.setBounds(39, 327, 414, 23);
		
		contentPane.add(btnCerrar);
		
		btnCopiarByte = new JButton("Copiar fichero byte a byte");
		btnCopiarByte.setActionCommand("copiarByteByte");
		btnCopiarByte.addActionListener(this);
		btnCopiarByte.setBounds(39, 82, 414, 23);
		contentPane.add(btnCopiarByte);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		switch(e.getActionCommand()){
		
		case "comparar" :
			if(compararFicheros())
				mensajeAviso("son iguales");
			else
				mensajeAviso("son distintos");
			break;
			
		case "buscar" :
			gestionarBuscarPalabraEnFichero();
			
			
			break;
			
		case "copiarByteByte" :
			
			File fileOrigen = abrirFichero(true);
			File fileDestino = abrirFichero(false);
			
			copiaBytes(fileOrigen,fileDestino);
			
			
			break;
			
		case "cerrar" :
			dispose();
			break;
		}
		
	}
	
	private void copiaBytes(File fileOrigen, File fileDestino) {
		// TODO Auto-generated method stub
		
		byte[] buffer = new byte[100000];
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		int numBytes = 0;
		
		try {
			
			fis = new FileInputStream(fileOrigen);
			fos = new FileOutputStream(fileDestino);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			 numBytes = fis.read(buffer);
			fos.write(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			cerrarCloseable(fis);
			cerrarCloseable(fos);
		}
		
		
			mensajeAviso("se han leido "+numBytes+" bytes");
		
		
		
	}

	private void cerrarCloseable(Closeable clos) {
		// TODO Auto-generated method stub
		
		try {
			clos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private File abrirFichero(boolean ficheroOrigen) {
		// TODO Auto-generated method stub
		String path=null;
		File file = null;
		
		
		
		if(ficheroOrigen){
			while(file == null){
				path = mensajeInteractuar("Dame la ruta del fichero Origen");
				file = new File(path);
				
				if(!file.exists()){
					mensajeAviso("El fichero Origen no existe");
					file = null;
				}
			}
		}else if(ficheroOrigen == false){
			while(file == null){
				path = mensajeInteractuar("Dame la ruta del fichero Destino");
				file = new File(path);
				
				if(file.exists()){
					mensajeAviso("El fichero Destino ya existe");
					file = null;
				}
			}
			
		}
		
		
		
		return file;
	}

	private void gestionarBuscarPalabraEnFichero() {
		// TODO Auto-generated method stub
		String path = mensajeInteractuar("Dame el nombre del fichero");
		String palabra = mensajeInteractuar("Dame la palabra a buscar");
		
		// 0 primera posicion 1 ultima posicion
		int seleccion = JOptionPane.showOptionDialog(
				   null,
				   "En que posicion quieres buscarla", 
				   "Selector de opciones",
				   JOptionPane.YES_NO_CANCEL_OPTION,
				   JOptionPane.QUESTION_MESSAGE,
				   null,    
				   new Object[] { "Primera posicion", "Ultima posicion"},   
				   "opcion 1");
		
		
		int posicion = buscarPalabraEnFile(path, palabra, seleccion);
		if(posicion==0){
			mensajeAviso("la palabra no se encuentra en el fichero");
		}else
			mensajeAviso("la palabra se encuentra en la posicion "+posicion);
		
		
	}
	
	public int buscarPalabraEnFile(String path, String palabra, int seleccion){
		
		boolean primeraAparicion = true;
		
		if(seleccion == 1) primeraAparicion = false;
		
		String linea;
		int posicion = 0;
		int numLinea = 1;
		
		File file = new File(path);
		
		if(file.exists()){
			FileReader fr = null;
			BufferedReader br;
			
			try {
				fr = new FileReader(file);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			br = new BufferedReader(fr);
			
			try {
				while((linea = br.readLine())!= null){
					
					if(linea.equals(palabra)){
						
						posicion = numLinea;
						if(primeraAparicion){
							return posicion;
						}
					}
					
					numLinea++;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			mensajeAviso("El fichero no existe");
		}
		
		return posicion;
		
	}

	private boolean compararFicheros() {
		// TODO Auto-generated method stub
		boolean respuesta = true;
		String path1 = mensajeInteractuar("Dame el nombre del primer fichero");
		String path2 = mensajeInteractuar("Dame el nombre del segundo fichero");
		
		
		
		String linea;
		
		File file1 = new File(path1);
		File file2 = new File(path2);

		
		
		
		if(file1.exists() && file2.exists()){
			
			int lineas1 = contarLineasFile(file1);
			int lineas2 = contarLineasFile(file2);
			
			if(lineas1 == lineas2){
				FileReader fr1 = null;
				FileReader fr2 = null;
				
				
				try {
					fr1 = new FileReader(file1);
					fr2 = new FileReader(file2);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				BufferedReader br1 = new BufferedReader(fr1);
				BufferedReader br2 = new BufferedReader(fr2);
				
				try {
					while((linea = br1.readLine())!= null){
						
						if(!linea.equals(br2.readLine()))
							respuesta = false;;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					fr1.close();
					fr2.close();
					br1.close();
					br2.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}else {
				respuesta = false;
			}	
			
		}else{
			respuesta = false;
		}
		
		
		return respuesta;
	}

	public void mensajeAviso(String mensaje){
		JOptionPane.showMessageDialog(
				   null,
				   mensaje);
	}
	
	public String mensajeInteractuar(String pregunta){
		String respuesta = JOptionPane.showInputDialog(
				   null,
				   pregunta,
				   JOptionPane.QUESTION_MESSAGE); 
		
		
		return respuesta;
	}
	
	public int contarLineasFile(File file){
		
		int lineas = 0;
		
		
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		
		try {
			while(br.readLine() != null){
				lineas++;
			}
			
			fr.close();
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return lineas;
	}
}
