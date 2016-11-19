import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class FramePrincipal extends JFrame implements ActionListener {

	private JPanel contentPane;
	JLabel lblTiempo;
	contarTiempo runnable;
	JButton btnPausar;
	boolean nuevoThread;

	public FramePrincipal() {

		nuevoThread = true;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblTiempo = new JLabel("0");
		lblTiempo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTiempo.setForeground(Color.BLACK);
		lblTiempo.setBackground(Color.WHITE);
		lblTiempo.setFont(new Font("Tahoma", Font.PLAIN, 50));
		lblTiempo.setBounds(167, 76, 97, 61);
		
		contentPane.add(lblTiempo);
		
		btnPausar = new JButton("Pausar");
		btnPausar.addActionListener(this);
		btnPausar.setBounds(28, 209, 89, 23);
		btnPausar.setActionCommand("Pausar");
		contentPane.add(btnPausar);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.setBounds(167, 209, 89, 23);
		btnPlay.addActionListener(this);
		contentPane.add(btnPlay);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(this);
		btnStop.setBounds(308, 209, 89, 23);
		contentPane.add(btnStop);
		
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		switch (e.getActionCommand()) {
			case "Play":
				if(nuevoThread){
					runnable = new contarTiempo(lblTiempo);
				}
				
				if(!runnable.isAlive()){
					nuevoThread = false;
					runnable.start();
				}
			break;

			case "Stop":
				btnPausar.setText("Pausar");
				btnPausar.setActionCommand("Pausar");
				runnable.parar();

				nuevoThread = true;
			break;
				
			case "Pausar":
				System.out.println("pausado");
				btnPausar.setText("Resume");
				btnPausar.setActionCommand("Resume");
				runnable.pausar();
			break;
			
			case "Resume":
				System.out.println("resume");
				btnPausar.setText("Pausar");
				btnPausar.setActionCommand("Pausar");
				runnable.activar();
			break;
		}
		
	}
}
