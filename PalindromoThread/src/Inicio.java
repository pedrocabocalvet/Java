import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Inicio {
	
	ArrayList <String> palabras;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	//	thread.start();
		
		
		Inicio ini = new Inicio();
		ini.conseguirPalabraFichero(new File("palabras.txt"));
		ThreadPoolExecutor myTPE=(ThreadPoolExecutor)Executors.newFixedThreadPool(4);
		
		for(int x = 0; x < ini.palabras.size(); x++){
			ComprobarPalabra cp = new ComprobarPalabra(ini.palabras.get(x));
			Thread thread = new Thread(cp);
			myTPE.execute(thread);
		}
		myTPE.shutdown();
	}
	
	public Inicio(){
		palabras = new ArrayList<String>();
	}
	
	public void conseguirPalabraFichero(File file){
		FileReader fr = null;
		BufferedReader br = null;
		String palabra;
		
		
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		br = new BufferedReader(fr);
		
		try {
			while((palabra = br.readLine())!= null){
				palabras.add(palabra);
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
		
	}

}
