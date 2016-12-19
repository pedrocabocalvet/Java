import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Formulario extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JTextField nombre;
	private JTextField apellido;
	private JTextField modulo;
	private JTextField curso;
	private JTextField ciclo;
	private DefaultTableModel dtm;
	private Object unaFila[];

	public Formulario(DefaultTableModel dtm2, String array[]) {
		dtm=dtm2;
		unaFila = new Object[5];
		setBounds(100, 100, 394, 167);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel label = new JLabel("");
			contentPanel.add(label);
		}
		{
			JLabel lblNombre = new JLabel("NOMBRE");
			contentPanel.add(lblNombre);
		}
		{
			nombre = new JTextField();
			contentPanel.add(nombre);
			nombre.setColumns(10);
		}
		{
			JLabel label = new JLabel("");
			contentPanel.add(label);
		}
		{
			JLabel lblApellidos = new JLabel("Apellido");
			contentPanel.add(lblApellidos);
		}
		{
			apellido = new JTextField();
			contentPanel.add(apellido);
			apellido.setColumns(10);
		}
		{
			JLabel lblModulo = new JLabel("Modulo");
			contentPanel.add(lblModulo);
		}
		{
			modulo = new JTextField();
			contentPanel.add(modulo);
			modulo.setColumns(10);
		}
		{
			JLabel label = new JLabel("    ");
			contentPanel.add(label);
		}
		{
			JLabel lblCurso = new JLabel("Curso");
			contentPanel.add(lblCurso);
		}
		{
			curso = new JTextField();
			contentPanel.add(curso);
			curso.setColumns(10);
		}
		{
			JLabel label = new JLabel("      ");
			contentPanel.add(label);
		}
		{
			JLabel lblCiclo = new JLabel("Ciclo");
			contentPanel.add(lblCiclo);
		}
		{
			ciclo = new JTextField();
			contentPanel.add(ciclo);
			ciclo.setColumns(10);
		}
		{
			JLabel label = new JLabel("");
			contentPanel.add(label);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(this);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(this);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		if(array != null){
			nombre.setText(array[0]);
			apellido.setText(array[1]);
			modulo.setText(array[2]);
			curso.setText(array[3]);
			ciclo.setText(array[4]);
		}
		
		setModal(true);
		
	}
	
	public boolean comprobarCampos(){
		
		boolean ok = true;
		
		if (nombre.getText().isEmpty() || apellido.getText().isEmpty() || modulo.getText().isEmpty() || curso.getText().isEmpty() || ciclo.getText().isEmpty()){
			ok = false;
		}

		
		
		return ok;
		
		
	}
	
	public Object[] asignarCampos(){
		
		Object unaFila[];
		unaFila = new Object[5];
		
		unaFila[0] = nombre.getText();
		unaFila[1] = apellido.getText();
		unaFila[2] = modulo.getText();
		unaFila[3] = curso.getText();
		unaFila[4] = ciclo.getText();
		
		return unaFila;
		
		
	}
	
	public String confeccionarMensaje(){
		String mensaje = "Faltan los datos ";
		
		if (nombre.getText().isEmpty()){
			mensaje = mensaje.concat("Nombre ");
		}
		
		if (apellido.getText().isEmpty()){
			mensaje = mensaje.concat("Apellido ");
		}
		
		if (modulo.getText().isEmpty()){
			mensaje = mensaje.concat("Modulo ");
		}
		
		if (curso.getText().isEmpty()){
			mensaje = mensaje.concat("Curso ");
		}
		
		if (ciclo.getText().isEmpty()){
			mensaje = mensaje.concat("Ciclo");
		}
		
		
		return mensaje;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getActionCommand()=="OK"){
			
			boolean ok = false;
			
			ok = comprobarCampos();
			
			if (ok){
				unaFila = asignarCampos();
				dtm.addRow(unaFila);
				dispose();

			}
			else{
				String mensaje;
				mensaje = confeccionarMensaje();
				JOptionPane.showMessageDialog(
						   null,
						   mensaje);
			}
			
		}
		if(e.getActionCommand()=="Cancel")
			dispose();
		
	}

}
