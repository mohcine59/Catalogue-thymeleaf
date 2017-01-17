package org.sid;

import org.sid.dao.ProduitRepository;
import org.sid.entities.Produit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CatalogueApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(CatalogueApplication.class, args);
		ProduitRepository produitRepository = ctx.getBean(ProduitRepository.class);
		produitRepository.save(new Produit("PC11", 400, 5));
		produitRepository.save(new Produit("PC22", 500, 3));
		produitRepository.save(new Produit("PC33", 600, 12));
		produitRepository.save(new Produit("PC44", 700, 15));
		
		produitRepository.findAll().forEach(p->System.out.println(p.getDesignation()));
	}
}
