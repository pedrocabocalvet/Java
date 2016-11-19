import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

// programa que multiplica una matriz por un array y guarda el resultado en un array de resultados,
// lo hace de tres maneras, la primera sin threads,
// la segunda con un thread por cada linea de la matriz
// y la tercera con un poolExecutor, para ver esta opcion ir al metodo calcularConPoolExecutor() de la clase inicio
// y ver como lo hace



public class Inicio {

	int [][] matriz;
	int [] array;
	int [] arrayResultado;
	static final int TAMANYO = 10000;
	Scanner sc;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Inicio inicio;
		inicio = new Inicio();
		int resultado = 0;
		Scanner sc = new Scanner(System.in);
		String basura;
		
		/*
		System.out.println("imprimo matriz");
		inicio.imprimirMatriz();
		System.out.println("imprimo array");
		inicio.imprimirArray();
		System.out.println();
		System.out.println("imprimo array Resultados");
		inicio.imprimirArrayResultado();
		*/
		
		while(resultado != 9){
			inicio.rellenarArrayResultados();
			resultado = inicio.menu();
			
			long initialTime = System.currentTimeMillis(); 
			
	
			
			switch(resultado){
				case 1:
					inicio.calcularSinThreads();
				break;
				case 2:
					inicio.calcularConThreads();
				break;
				case 3:
					inicio.calcularConPoolExecutor();
				break;
			}
			
			long finalTime = System.currentTimeMillis();
			
			System.out.println("********************************");
			System.out.println("has tardado "+(finalTime-initialTime)+" milisegundos");
			System.out.println("pulsa la tecla 9 para salir");
			resultado = sc.nextInt();
			basura = sc.nextLine();
			
		}
		System.out.println("array resultado");
		inicio.imprimirArrayResultado();
		
	}
	


	public Inicio(){
		matriz = new int[TAMANYO][TAMANYO];
		array = new int[TAMANYO];
		arrayResultado = new int[TAMANYO];
		
		rellenarMatriz();
		rellenarArray();
		
		
		sc = new Scanner(System.in);
		
	}
	
	public void rellenarMatriz(){
		for(int x = 0; x < matriz.length; x++){
			for(int y = 0 ; y < matriz[x].length; y++){
				matriz[x][y]=(int) (Math.random() * 99) + 1;
			}
		}
	}
	
	public void rellenarArray(){
		for(int x = 0; x < array.length; x++){
			array[x]=(int) (Math.random() * 99) + 1;
		}
	}
	
	public void rellenarArrayResultados(){
		for(int x = 0; x < arrayResultado.length; x++){
			arrayResultado[x]=0;
		}
	}
	
	public void imprimirMatriz(){
		for(int x = 0; x < matriz.length; x++){
			for(int y = 0 ; y < matriz[x].length; y++){
				System.out.print(matriz[x][y]+" ");
			}
			System.out.println();
		}
	}
	
	public void imprimirArray(){
		for(int x = 0; x < array.length; x++){
			System.out.print(array[x]+" ");
		}
	}
	
	public void imprimirArrayResultado(){
		for(int x = 0; x < arrayResultado.length; x++){
			System.out.print(arrayResultado[x]+" ");
			System.out.print(",");
		}
	}
	
	private int menu() {
		// TODO Auto-generated method stub
		int resultado;
		String basura;
		System.out.println("**************************");
		System.out.println("1) sin Threads");
		System.out.println("2) threads por cada linea");
		System.out.println("3) poolExecutor");
		
		resultado = sc.nextInt();
		basura = sc.nextLine();
		
		return resultado;
		
	}
	
	public void calcularSinThreads(){
		for(int x = 0 ; x < matriz.length; x++){
			for(int y = 0; y < matriz[x].length; y++){

				arrayResultado[x] = arrayResultado[x] +(matriz[x][y] * array[y]);
			}
		}
	}
	
	public void calcularConThreads(){
		for(int x = 0; x < array.length; x++){
			MultiplicarUnaFila thread = new MultiplicarUnaFila(this, x);
			Thread t = new Thread(thread);
			t.start();
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void calcularConPoolExecutor(){
		ThreadPoolExecutor myTPE=(ThreadPoolExecutor)Executors.newFixedThreadPool(4) ;
		
		for(int x = 0; x < array.length; x++){
			MultiplicarUnaFila thread = new MultiplicarUnaFila(this, x);
			Thread t = new Thread(thread);
			myTPE.execute(t);
		}
		myTPE.shutdown();
	}
}
