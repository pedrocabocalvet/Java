import javax.swing.JLabel;

public class contarTiempo extends Thread {
	
	JLabel labelTiempo;
	int tiempo;
	boolean pausado;
	
	public contarTiempo(JLabel t){
		this.labelTiempo = t;
		tiempo = 0;
		pausado = false;
	}

	@Override
	public void run() {
		
		while(!this.isInterrupted()){
			if(pausado){
				synchronized (this){
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else{
				pasarTiempo();
				labelTiempo.setText(""+tiempo);
			}
			
		}
		
		System.out.println("por fin se paro con un tiempo de "+tiempo+"segundos");
	}

	private void pasarTiempo() {
		// TODO Auto-generated method stub
		
		try {
			synchronized (this) {
				this.sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("hola q ase");
		}
		tiempo++;
	}
	
	public void activar(){
		pausado = false;
		synchronized (this){
			this.notifyAll();
		}
	}
	
	public void pausar(){
		
		pausado = true;
	}
	
	public void parar(){
		synchronized (this){
			this.notifyAll();
			this.interrupt();
		}
	}

}
