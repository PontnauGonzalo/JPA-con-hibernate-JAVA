package ar.com.alura.tienda.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtils {

    private static EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("tienda"); 
    																				  // 'tienda' depende de persistence.xml
    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }
}