/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import entities.Conference;
import entities.Speaker;
import entities.Talk;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        Speaker s1 = new Speaker("Janus","Drummer","male");
        Speaker s2 = new Speaker("Frederik","Gamer","male");

        Talk t1 = new Talk("Drummer",5,"drums");
        Talk t2 = new Talk("Drummer",5,"drums");
        Talk t3 = new Talk("Gamer",5,"World of warcraft");
        Talk t4 = new Talk("Gamer",5,"World of warcraft");

        Conference c1 = new Conference("Blizzcon","USA",5000,"24-04-2024","18:00");
        Conference c2 = new Conference("Charity","Denmark",2500,"31-08-2024","11:00");

        s1.addTalk(t1);
        s1.addTalk(t2);
        s2.addTalk(t3);
        s2.addTalk(t4);

        c1.addTalk(t1);
        c1.addTalk(t3);
        c2.addTalk(t2);
        c2.addTalk(t4);

        em.getTransaction().begin();
        em.persist(c1);
        em.persist(c2);
        em.persist(s1);
        em.persist(s2);
        em.persist(t1);
        em.persist(t2);
        em.persist(t3);
        em.persist(t4);

        em.getTransaction().commit();
    }
    
    public static void main(String[] args) {
        populate();
    }
}
