import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

// este JDialog se reutiliza varias veces en el programa, para guardar libro individual, guardar lista de libros
// y modificar, para ello le pasaremos por parametro el texto de los botones. Simplemente servira para que el usuario
// escriba los atributos de un Objeto Libro y se los pasa al metodo frame para que los guarde

public class DatosLibro extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtTitulo;
	private JTextField txtNombre;
	private JTextField txtEditorial;
	private JTextField txtNumPag;
	private JTextField txtFecha;
	private frame ventanaPrincipal = null;	// guardamos el frame principal para poder usar sus metodos
	
	private String libroAntiguoParaModificar; // solo lo usaremos en el caso de que queramos modificar un libro
											  // aqui guardaremos su ruta antigua para destruir el fichero antiguo
											  // y podamos crearlo otra vez con los datos modificados


	// en el constructor recibimos el frame principal, el texto q aparecera en el primer boton y en el segundo,
	// y solo en el caso de que queramos modificar un libro vendra un obejto Libro para poder rellenar los campos
	// con los atributos de ese objeto, si no queremos modificar lib siempre sera null
	public DatosLibro(frame fr, String primerBoton, String segundoBoton, Libro lib) {
		
		ventanaPrincipal = fr;	// guardamos el frame principal
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		txtTitulo = new JTextField();
		txtTitulo.setBounds(35, 50, 86, 20);
		contentPanel.add(txtTitulo);
		txtTitulo.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(164, 50, 86, 20);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtEditorial = new JTextField();
		txtEditorial.setBounds(294, 50, 86, 20);
		contentPanel.add(txtEditorial);
		txtEditorial.setColumns(10);
		
		txtNumPag = new JTextField();
		txtNumPag.setBounds(92, 117, 86, 20);
		contentPanel.add(txtNumPag);
		txtNumPag.setColumns(10);
		
		txtFecha = new JTextField();
		txtFecha.setBounds(236, 117, 86, 20);
		contentPanel.add(txtFecha);
		txtFecha.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(181, 24, 46, 14);
		contentPanel.add(lblNombre);
		
		JLabel titulo = new JLabel("Titulo");
		titulo.setBounds(54, 24, 46, 14);
		contentPanel.add(titulo);
		
		JLabel editorial = new JLabel("Editorial");
		editorial.setBounds(313, 24, 46, 14);
		contentPanel.add(editorial);
		
		JLabel numPag = new JLabel("N\u00BA paginas");
		numPag.setBounds(92, 92, 86, 14);
		contentPanel.add(numPag);
		
		JLabel Fecha = new JLabel("Fecha");
		Fecha.setBounds(253, 92, 46, 14);
		contentPanel.add(Fecha);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(primerBoton);	// aqui ponemos el texto del boton q recibimos por paramtro
				okButton.setActionCommand(primerBoton);
				okButton.addActionListener(this);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton(segundoBoton);	// aqui ponemos el texto del boton q recibimos por paramtro
				cancelButton.setActionCommand(segundoBoton);
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
		
		// si lib no es null significa que queremos modificar un libro, por eso setearemos todos los campos
		// con los atributos del libro a modificar para hacerlo mas facil y mas visual a la hora de modificar
		if(lib != null){
			txtTitulo.setText(lib.getTitulo());
			txtNombre.setText(lib.getAutor());
			txtEditorial.setText(lib.getEditor());
			txtNumPag.setText(""+lib.getNumPag());
			txtFecha.setText(lib.getFecha());
			libroAntiguoParaModificar = txtTitulo.getText();	// cojeremos la ruta antigua del libro para poder borrarla
		}														// y poder crear la nueva
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		switch (e.getActionCommand()) {
		
		case "GUARDAR":
			
			// comprueba q haya datos en todos los campos y luego guarda el libro, capturando y corrigiendo todas las posibles excepciones
			
			if(!txtTitulo.getText().isEmpty() && !txtNombre.getText().isEmpty() && !txtEditorial.getText().isEmpty() && !txtNumPag.getText().isEmpty() && !txtFecha.getText().isEmpty()){
				int numPaginas = Integer.parseInt(txtNumPag.getText().toString());
				Libro lib = new Libro(txtTitulo.getText().toString(),txtNombre.getText().toString(),txtFecha.getText().toString(),txtEditorial.getText().toString(),numPaginas);
				File file = new File(txtTitulo.getText().toString());
				
				if(file.exists()){
					ventanaConMensaje("El fichero ya existe no se va a crear");
				}else{
					ventanaPrincipal.guardarLibro(file, lib);
				}
				dispose();
			}else
				ventanaConMensaje("Faltan algunos datos");
			break;
		case "Cancel":
			// simplemente cierra la ventana
			this.dispose();
			break;
			
		case "Continuar":
			// creara una ventana igual que esta y guardara los datos introducidos en el ArrayList
			// del frame, asi cuando le demos al boton guardar grupo guardara todo el ArrayList en ficheros
			
			DatosLibro dialog2 = new DatosLibro(ventanaPrincipal,"Continuar","Guardar grupo",null);
			dialog2.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog2.setVisible(true);
			if(!txtTitulo.getText().isEmpty() && !txtNombre.getText().isEmpty() && !txtEditorial.getText().isEmpty() && !txtNumPag.getText().isEmpty() && !txtFecha.getText().isEmpty()){
				int numPaginas = Integer.parseInt(txtNumPag.getText().toString());
				Libro lib = new Libro(txtTitulo.getText().toString(),txtNombre.getText().toString(),txtFecha.getText().toString(),txtEditorial.getText().toString(),numPaginas);
				ventanaPrincipal.añadirLibroArrayList(lib);
			}else
				ventanaConMensaje("Faltan algunos datos");
			dispose();
			break;
		
		case "Guardar grupo":
			// comprueba si hay algo escrito para guardarlo en el ArrayList y llama al metodo guardarTodoArrayListLibro
			// del frame que guardara uno por uno todo el ArrayList en ficheros
			if(!txtTitulo.getText().isEmpty() && !txtNombre.getText().isEmpty() && !txtEditorial.getText().isEmpty() && !txtNumPag.getText().isEmpty() && !txtFecha.getText().isEmpty()){
				int numPaginas = Integer.parseInt(txtNumPag.getText().toString());
				Libro lib = new Libro(txtTitulo.getText().toString(),txtNombre.getText().toString(),txtFecha.getText().toString(),txtEditorial.getText().toString(),numPaginas);
				ventanaPrincipal.añadirLibroArrayList(lib);
			}else
				ventanaConMensaje("Faltan algunos datos");
			
			ventanaPrincipal.guardarTodoArrayListLibro();
			dispose();
			break;
			
	case "Modificar":
		
		// comprueba q haya datos en todos los campos y luego guarda el libro, capturando y corrigiendo todas las posibles excepciones
		// antes de guardar destruira el fichero anterior para poder guardar el nuevo (por si se llaman igual)
		
		if(!txtTitulo.getText().isEmpty() && !txtNombre.getText().isEmpty() && !txtEditorial.getText().isEmpty() && !txtNumPag.getText().isEmpty() && !txtFecha.getText().isEmpty()){
			int numPaginas = Integer.parseInt(txtNumPag.getText().toString());
			Libro lib = new Libro(txtTitulo.getText().toString(),txtNombre.getText().toString(),txtFecha.getText().toString(),txtEditorial.getText().toString(),numPaginas);
			File file = new File(txtTitulo.getText().toString());
			File fileAntiguo = new File(libroAntiguoParaModificar);
		
			if(fileAntiguo.delete()){
				
				if(file.exists()){
					ventanaConMensaje("El fichero ya existe no se va a crear");
				}else{
					ventanaPrincipal.guardarLibro(file, lib);
					dispose();
				}
			}else{
				ventanaConMensaje("No se ha podido modificar");
			}
			
			
		}else
			ventanaConMensaje("Faltan algunos datos");
		break;
		}
	}
	// enseña un mensaje con el texto pasado por parametro
		public void ventanaConMensaje(String mensaje){
			JOptionPane.showMessageDialog(
					   null,
					   mensaje);
		}
}
