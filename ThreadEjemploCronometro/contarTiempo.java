import javax.swing.JLabel;

public class contarTiempo extends Thread {
	
	JLabel labelTiempo;	// nos guardamos el label del frame para poder ir ajustando el tiempo
	int tiempo;	// aqui iremos sumando los segundos 
	boolean pausado;	// lo usaremos para saber cuando esta pausado el Thread
	
	public contarTiempo(JLabel t){
		this.labelTiempo = t;
		tiempo = 0;	// iniciamos el tiempo a 0 segundos
		pausado = false;
	}

	@Override
	public void run() {	// metodo mas importante de los Threads, esto es lo que se ejecutara en un hilo cuando lo llamemos
						// desde fuera con el metodo start()
		
		while(!this.isInterrupted()){	// sirve para saber cuando lo hemos interrumpido
			if(pausado){
				synchronized (this){	// muy importante, todo lo que esta aqui dentro significa que no puede entrar otro Thread aqui hasta que este salga
					try {
						this.wait();	// sirve para dormir el Thread tiene q estar en un synchronized
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else{
				pasarTiempo();	// metodo que ira sumando segundos
				labelTiempo.setText(""+tiempo);	// ajustamos el label del Frame
			}
			
		}
		
		System.out.println("por fin se paro con un tiempo de "+tiempo+"segundos");
	}

	private void pasarTiempo() {
		// TODO Auto-generated method stub
		
		try {
			synchronized (this) {	// duerme el proceso los milisegundos que le pongamos. tiene q estar en un SYNCHRONIZED
				this.sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
	
		}
		tiempo++;	//cada vez q espere un seg añadiremos a la variable uno, simulando los segundos del cronometro
	}
	
	public void activar(){
		pausado = false;
		synchronized (this){
			this.notifyAll();	// para que el Thread despierte del wait
		}
	}
	
	public void pausar(){
		
		pausado = true;
	}
	
	public void parar(){
		synchronized (this){
			this.notifyAll();	// antes de pararlo tengo que cercinarme que esta en marcha, por eso despierto el wait
			
			this.interrupt();	// para interrumpir el Thread y conseguir q salga del boucle que hay q en el run.
								// tiene q estar en un Synchronized
		}
	}

}
