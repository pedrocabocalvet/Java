import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class Consultas {
	
	// metodo que hace un select en la bbdd para conseguir el usuario que se pasa por parametro y comprueba su contraseña
	public boolean AutentificarUsuarioContraseña(String usuario, String contraseña) {
		boolean respuesta = false;
		ResultSet rs = null;
		Statement cmd = null;

		String contraseñaBBDD = "";
		String usuarioBBDD = "";
		
		

		ConexionBaseDatos con = ConexionBaseDatos.getInstancia();
		
		

		try {
			if (con != null) {
				cmd = ConexionBaseDatos.con.createStatement();

				rs = cmd.executeQuery("SELECT nombre, contrasena FROM EMPLEADO WHERE NOMBRE = '" + usuario + "';");
			} else {
				String mensaje = "Error no existe la conexion con la base de datos";
				String titulo = "Error.Algo no ha ido bien";
				JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		try {

			while (rs.next()) {
				usuarioBBDD = rs.getString("nombre");
				contraseñaBBDD = rs.getString("contrasena");

			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (contraseñaBBDD.equals(contraseña))
			respuesta = true;

		else
			respuesta = false;

		return respuesta;

	}
	
	
	//Metodo para insertar un empleado en la base de datos
	public void insertarEmpleado(String nom, String ima, String contrasena, String cargo) {

		Statement cmd = null;
		

		try {
			if (ConexionBaseDatos.con != null) {
				cmd = ConexionBaseDatos.con.createStatement();
				cmd.executeUpdate("insert into `empleado` (`nombre`, `imagen`, `contrasena`, `cargo`) values ( '"+nom+"', "+ima+", '"+contrasena+"', '"+cargo+"' );");
			} else {
				String mensaje = "Error no existe la conexión con la base de datos";
				String titulo = "Error.Algo no ha ido bien";
				JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void borrarArticuloAlmacen(String cod){
		
		Statement cmd = null;
				
		try {
			if(ConexionBaseDatos.con!=null){
				cmd = ConexionBaseDatos.con.createStatement();
				cmd.executeUpdate("DELETE FROM `articulo` WHERE `cod`="+cod+";");//hay que solucionar error. no podemos borrar articulos que esten en ventas
			}else{
				String mensaje="Error no existe la conexión con la base de datos";
				String titulo="Error.Algo no ha ido bien";
				JOptionPane.showMessageDialog(null,mensaje,titulo,JOptionPane.ERROR_MESSAGE);
			}	
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	// metodo para actualizar la bbdd
	public void updateArticulo(String cod, String nom, Double pc, Double venta, Integer disp, Integer min, Integer cat){
		
		Statement cmd = null;
		
		try {
			if(ConexionBaseDatos.con!=null){
				cmd = ConexionBaseDatos.con.createStatement();
				cmd.executeUpdate("UPDATE `articulo` SET `precio_compra` = '"+pc+"', `pvp` = '"+venta+"', `disponibles` = "+disp+", `cant_minima` = "+min+", `cod_categoria` = "+cat+" WHERE `cod`="+cod+";");
			}else{
				String mensaje="Error no existe la conexión con la base de datos";
				String titulo="Error.Algo no ha ido bien";
				JOptionPane.showMessageDialog(null,mensaje,titulo,JOptionPane.ERROR_MESSAGE);
			}	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
