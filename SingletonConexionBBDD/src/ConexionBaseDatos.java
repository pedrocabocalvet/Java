import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class ConexionBaseDatos {

	static Connection con; //atributo para guardar el objeto conexión
	private static ConexionBaseDatos INSTANCIA=null; //referencia al objeto que vamos a crear
	
	// constructor privado para que solo se pueda llamar desde esta clase no desde fuera
	private ConexionBaseDatos(){
		
		realizarConexion();
	}
	
	//Si no existe, crea una instancia de esta clase
	private synchronized static void creaInstancia(){
		
		if(INSTANCIA==null){
			INSTANCIA = new ConexionBaseDatos();
		}
	}
	// con este metodo prodremos crear desde fuera una instancia de este objeto y cogerla para usarla
	public static ConexionBaseDatos getInstancia(){
		
		if(INSTANCIA==null) creaInstancia();
		return INSTANCIA;
	}
	
	// creado para poder acceder al con desde otro paquete
	public static Connection getCon(){
		return con;
	}
	
	//Metodo para eliminar la instancia de la conexión
	public static void eliminaInstancia(){
		INSTANCIA= null;
		cierraConexion();
	}
	
	// aqui registramos el driver esto siempre es igual
	private void realizarConexion(){
		try{
			
			//registramos el driver
			Class.forName("com.mysql.jdbc.Driver");
			//obtenemos la cadena de conexión
			//Establecemos conexión
			
			//String newConnectionURL="jdbc:mysql://52.25.51.182/tpv2?"+"user=alan&password=jordi";
			String newConnectionURL="jdbc:mysql://localhost/tpv2?"+"user=root&password=";
			con=DriverManager.getConnection(newConnectionURL);
		} catch(Exception e){
			String mensaje="Error intentando abrir la conexion con la bese de datos. Compruebe si tiene el driver JDBC";
			String titulo="Error.Algo no ha ido bien";
			JOptionPane.showMessageDialog(null,mensaje,titulo,JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//Metodo para cerrar la conexión
	private static void cierraConexion(){
		try{
			con.close();
		}catch(Exception e){
			String mensaje="Error intentando cerrar la conexion";
			String titulo="Error.Algo no ha ido bien";
			JOptionPane.showMessageDialog(null,mensaje,titulo,JOptionPane.ERROR_MESSAGE);
		}
	}
}