import java.sql.Date;

import org.hibernate.Session;

public class Inicio {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("HELLO WORLD");
		
		Session session = HibernateUtilities.getSessionFactory().openSession();
	
		session.beginTransaction();
		
		Empresa empresa = new Empresa();
		//empresa.setCif("A343242");
		empresa.setDireccion("C/PALOTES n15");
		empresa.setEmpleados(101);
		empresa.setNombre("scotex21");
	
		Item item = new Item();
		item.setNombre("palo2");
		item.setCantidad(20012);
		
		
		java.util.Date utilDate = new java.util.Date(); //fecha actual
		long lnMilisegundos = utilDate.getTime();
		
		Date fecha = new Date(lnMilisegundos);
		Pedido pedido = new Pedido();
		pedido.setFecha(fecha);
		
		// salvamos el objeto en la session
		session.save(empresa);
		session.save(item);
		session.save(pedido);
		// comiteamos la transaccion
	//	session.getTransaction().commit();
	
	//	session.close();
		
	
	//	session.beginTransaction();
		Empresa empresaRecuperada = session.get(Empresa.class,1);
		Item itemRecuperado = session.get(Item.class, 1);
		Pedido pedidoRecuperado = session.get(Pedido.class, 1);
		
		System.out.println("He recuperado la empresa "+empresaRecuperada.getNombre());
		System.out.println("He recuperado el item "+itemRecuperado.getNombre());
		System.out.println("He recuperado el pedido de la fecha "+pedidoRecuperado.getFecha());
		
		session.getTransaction().commit();
		session.close();
		
		
		
		// cerramos la session statica
		HibernateUtilities.getSessionFactory().close();
	}

}
