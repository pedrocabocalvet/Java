
public class MultiplicarUnaFila implements Runnable{
	
	int[][] matriz;
	int[] array;
	
	int resultado;
	int posicion;
	Inicio inicio;
	
	public MultiplicarUnaFila(Inicio ini, int p){
		resultado = 0;
		this.inicio = ini;
		
		this.matriz = ini.matriz;
		this.array = ini.array;
		
		posicion = p;
	}
	


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		for(int x = 0; x < matriz[posicion].length; x++){
			resultado = resultado + (array[x] * matriz[posicion][x]);
		}
		
		inicio.arrayResultado[posicion]=resultado;
		
	}
	


}
