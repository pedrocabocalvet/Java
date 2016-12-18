import java.util.Scanner;

public class Inicio {
	
	// ejemplo de una conexion a la bbdd y hacer una query, pongo ejemplos de insertar, modificar, eliminar
	// la clase conexionBaseDatos es singleton

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String usuario;
		String contrasena;
		Consultas consultas = new Consultas();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("dame el usuario");
		usuario = sc.nextLine();
		
		System.out.println("dame la contraseña");
		contrasena = sc.nextLine();
		
		if(consultas.AutentificarUsuarioContraseña(usuario, contrasena)){
			System.out.println("usuario correcto");
		}else{
			System.out.println("usuario incorrecto");
		}

	}

}
