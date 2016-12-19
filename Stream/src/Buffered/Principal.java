package Buffered;

import java.awt.EventQueue;

public class Principal {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ventanaPrincipal frame = new ventanaPrincipal();
					frame.setVisible(true);
					frame.setLocation(500,200);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
