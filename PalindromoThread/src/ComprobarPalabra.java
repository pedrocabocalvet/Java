
public class ComprobarPalabra implements Runnable {
	
	String palabra;
	
	public ComprobarPalabra(String p){
		
		palabra = p;
	}
	
	public void run(){
		if(comprobarPalabra())
			System.out.println(palabra+" es palindromo");
		else
			System.out.println(palabra+" no es palindromo");
	}
	
	public boolean comprobarPalabra(){
		
		
		int ultimaPosicion = palabra.length() - 1;
		
		for(int x = 0; x < palabra.length(); x++){
			
			if(palabra.charAt(x) != palabra.charAt(ultimaPosicion)){
				return false;
			}
			ultimaPosicion--;
		}
		
		
		
		return true;
	}
	

}
