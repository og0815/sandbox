package com.example.init;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.entity.Property;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Singleton
@Startup
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @PersistenceContext(unitName = "defaultPU")
    private EntityManager em;

    private final Random rnd = new Random();

    @PostConstruct
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void init() {
        log.info("Starting DataInitializer...");
        
        // Check if database is empty
        Long count = em.createQuery("SELECT COUNT(p) FROM Property p", Long.class)
                .getSingleResult();
        
        if (count > 0) {
            log.info("Database already contains {} Property records. Skipping initialization.", count);
            return;
        }
        
        log.info("Database is empty. Initializing with 10 random Property records...");
        
        // create 10 random Property entities
        for (int i = 1; i <= 10; i++) {
            Property p = new Property();
            long pk = 140L + i; // simple unique PK_IDObject values
            p.setPK_IDObject(pk);
            p.setAppelation("Auto-generated " + i);
            p.setObjectGroup("Group" + (rnd.nextInt(5) + 1));
            p.setStreet(i + " Example Street");
            p.setZip(1000 + rnd.nextInt(9000));
            p.setCity("City" + (rnd.nextInt(20) + 1));
            p.setLatitude(47.0 + rnd.nextDouble());
            p.setLongitude(8.0 + rnd.nextDouble());
            p.setLine("L" + (rnd.nextInt(10) + 1));
            p.setLineSection("S" + (rnd.nextInt(4) + 1));
            p.setDriveTimeToCentralStation(rnd.nextInt(60));
            p.setCapacityMax(10 + rnd.nextInt(90));
            p.setCapacityHandicapped(rnd.nextInt(10));
            p.setCapacityRent(rnd.nextInt(20));

            em.persist(p);
            log.debug("Created Property with PK_IDObject: {}", pk);
        }
        
        log.info("Successfully initialized database with 10 Property records.");
    }
}
