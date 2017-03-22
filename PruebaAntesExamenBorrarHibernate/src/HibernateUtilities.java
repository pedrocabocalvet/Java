import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/* Clase Singleton que prepara el SessionFactory y permite que otras clase lo cojan y lo puedan utilizar*/

public class HibernateUtilities {
	
	// atributo privado y static del SessionFactory 
	private static SessionFactory sessionFactory = buildSessionFactory();
	
	// aqui construimos el sessionFactory y lo devolvemos
	private static SessionFactory buildSessionFactory(){
		try{
// nos creamos un objeto StandardServiceRegistry q es donde cargaremos el xml de la configuracion de hibernate
StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
// necesitamos crearnos un metadata para poder construir el sessionFactory, para construirlo usaremos el StandardServiceRegistry
Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
			// construimos e SessionFactory y lo devolvemos
			return metadata.getSessionFactoryBuilder().build();
		}catch(HibernateException e){
			System.out.println("Problema creando SessionFactory "+e);
		}
		
		return sessionFactory;
	}
	
	// metodo publico para poder coger y usar el SessionFactory

	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}
