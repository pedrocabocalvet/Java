import org.hibernate.Session;

public class Inicio {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Session session = HibernateUtilities.getSessionFactory().openSession();
		
		session.beginTransaction();
		
		
		
		Item item = new Item();
		item.setCantidad(100);
		item.setNombre("palo");
		
		Item item2 = new Item();
		item2.setCantidad(150);
		item2.setNombre("escoba");
		
		Pedido pedido = new Pedido();
		pedido.setFecha("10-01-1982");
		pedido.getItems().add(item);
		pedido.getItems().add(item2);
		
		Pedido pedido2 = new Pedido();
		pedido2.setFecha("10-01-1999");
		pedido2.getItems().add(item);
		pedido2.getItems().add(item2);
		
		Empresa e = new Empresa();
		e.setCif("12345AS");
		//e.setDireccion("Calle garcilaso");
		e.setNombre("Puleva");
		e.setEmpleados(120);
		e.addPedido(pedido);
		e.addPedido(pedido2);
		e.getDireccion().setCalle("Calle bajokon");
		e.getDireccion().setCp(45180);
		e.getDireccion().setPoblacion("lliria");
		
		
		session.save(e);
		//session.save(item);
		//session.save(pedido);
		//session.save(pedido2);
		
		session.getTransaction().commit();
	/*	
		session.beginTransaction();
		
		Empresa empresaRecuperada = session.get(Empresa.class, "12345A");
		Item itemRecuperado = session.get(Item.class,1);
		Pedido pedidoRecuperado = session.get(Pedido.class, 1);
		
		System.out.println("empresa "+empresaRecuperada.getNombre()+" item "+itemRecuperado.getNombre()+" Fecha pedido "+pedidoRecuperado.getFecha());;
		
		*/
		session.close();
		HibernateUtilities.getSessionFactory().close();

		
		

	}

}
