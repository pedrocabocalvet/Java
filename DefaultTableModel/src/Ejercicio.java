import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;



import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class Ejercicio extends JFrame implements ActionListener {

	private JPanel contentPane;
	JButton btnAnyadir;
	Connection con;
	Statement cmd;
	ResultSet rs;
	
	/**
	 * Launch the application.
	 */
	//Color color = (Color.blue);	
	
	
	String dadesTaula[][]={{"Manel","Viel","PROGRAMACIÓ","1","DAM"},
		{"Paco","Gomez","Entornos","1","DAM"},
		{"Juanmi","Benavent","LLenguatge de marques","1","DAM"},
		{"Toni","Ruiz","BBDD","1","DAM"}};
		
	String nomsColumnes[]={"Nom","Cognoms","Modul","Curs","Cicle"};
	
	private JTable dades;
	Object[] unafila = new Object[dadesTaula[0].length];
	
	DefaultTableModel dtm;
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejercicio frame = new Ejercicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ejercicio() {
		con = null;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		dtm=new DefaultTableModel();
		dades = new JTable(dtm);
		
		for (int column=0; column < nomsColumnes.length;column++){
			dtm.addColumn(nomsColumnes[column]);
		}
		
		for(int i=0;i<dadesTaula.length;i++){
			for(int x=0;x<dadesTaula[0].length;x++){
				unafila[x]=dadesTaula[i][x];
			}
			dtm.addRow(unafila);
		}
		
		JScrollPane scrollPane = new JScrollPane(dades);
		
		
		
		contentPane.add(scrollPane,BorderLayout.CENTER);
		
		JPanel botonera = new JPanel();
		contentPane.add(botonera, BorderLayout.NORTH);
		
		btnAnyadir = new JButton("añadir");
		btnAnyadir.addActionListener(this);
		botonera.add(btnAnyadir);
		
		JButton btnElimina = new JButton("elimina");
		btnElimina.addActionListener(this);
		botonera.add(btnElimina);
		
		JButton btnModificar = new JButton("modificar");
		btnModificar.addActionListener(this);
		botonera.add(btnModificar);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnAadirColumna = new JButton("A\u00D1ADIR COLUMNA");
		btnAadirColumna.addActionListener(this);
		panel.add(btnAadirColumna);
		
		JButton btnConsulta = new JButton("consulta");
		btnConsulta.addActionListener(this);
		panel.add(btnConsulta);
		
		Integer tolotenria;
		tolotenria = dades.getRowCount();
		
		

		
		
		
	}
	
	// si quieres que conectar con la bbdd tienes que registrar un driver, esto siempre
	// es asi
	
	public void registrarDriver(){
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();

			System.out.println("Registro exitoso");

			} catch (Exception e) {

			System.out.println(e.toString());

			}
	}
	
	// necesitamos un conector para poder conectar a la base de datos que queramos
	public void inicializarConector(){
		
		try {

			con = (Connection) DriverManager.getConnection(

			"jdbc:mysql://localhost/mysql?"

			+ "user=root&password=");
			
			mensajeAdvertencia("Exito en la conexión");

			// Otros y operaciones sobre la base de datos...

			} catch (SQLException ex) {

			// Mantener el control sobre el tipo de error

			System.out.println("SQLException: " + ex.getMessage());

			}
		
	}
	
	
	// Stament es para hacer una instruccion, luego le metes al Statment el string de la consulta
	// y asi le haces la consulta a la bbdd guardada en el conector con, el executeQuery devuelve
	// un puntero que habra que recorrer para ver todo lo que sale en esa consulta esto es un ResultSet
	// lo guardamos en rs
	
	public void efectuarConsulta(){
		
		try {
			
			cmd = (Statement) con.createStatement();
			
			rs=cmd.executeQuery("SELECT Host,User,Password FROM user");
			mensajeAdvertencia("Consulta realizada");

			

			}
		catch(Exception e){
				e.printStackTrace();
		}
		
	}
	
	// el resultset que nos ha devuelto la query en el metodo anterior, es como una matriz sin
	// llegar a serlo que contiene los datos de la consulta, para recorrerlo lo haremos con un next
	// asi pasarmos a una nueva fila, una fila puede tener varios datos que se cogen como vemos,
	// con el metodo rs.getString(numero), cuando quieras pasar de fila es con el next
	
	public void agregarConsulta(){
		
		String datos[];
		datos = new String[3];
		
		try{
			while (rs.next()) {

				datos[0] = rs.getString(1);
	
				datos[1] = rs.getString(2);
				
				datos[2] = rs.getString(3);

				dtm.addRow(datos);

	
			}
			rs.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		
	}
	
	public void mensajeAdvertencia(String mensaje){
		JOptionPane.showMessageDialog(
				   null,
				   mensaje);
		
	}
	
	
	
	

	@Override
	// para que funcione esto hay que acordarse de añadirle los listener a los botones
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getActionCommand()=="añadir"){
			Formulario formulario;

			formulario = new Formulario(dtm, null);
			formulario.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			formulario.setVisible(true);
		}
		if (e.getActionCommand()=="AÑADIR COLUMNA"){
			String seleccion = JOptionPane.showInputDialog(
					   null,
					   "Dime el nombre de la columna");
			if(seleccion != null)
				dtm.addColumn(seleccion);
		}
		
		if (e.getActionCommand()=="consulta"){
			
			registrarDriver();
			inicializarConector();
			efectuarConsulta();
			agregarConsulta();
		}
		
		if (e.getActionCommand()=="elimina"){
			int fila;
			fila = dades.getSelectedRow();
			dtm.removeRow(fila);
		}
		
		if (e.getActionCommand()=="modificar"){
			
			// NO ACABADO MODIFICAR
			int fila;
			
			String array[] = new String[5];
			for(int x = 0; x < 5 ; x++){
				array[x]=dades.getModel().getValueAt(dades.getSelectedRow(), x).toString();
			}
			
			Formulario formulario;
			formulario = new Formulario(dtm, array);
			
			formulario.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			formulario.setVisible(true);
			// String nombre, apellido, modulo, curso, ciclo;
			
			fila = dades.getSelectedRow();
			dtm.removeRow(fila);
			

		}
		
		
	}

}

