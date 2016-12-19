import java.awt.EventQueue;
// clase para inicializar la aplicacion 
// ejercicio 3 del examen de juanmi


public class inicio {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame frame = new frame();
					frame.setVisible(true);
					frame.setTitle("Libreria");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
