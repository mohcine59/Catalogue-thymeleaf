package org.sid.web;

import java.util.List;

import javax.validation.Valid;

import org.sid.dao.ProduitRepository;
import org.sid.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProduitController {

	@Autowired
	private ProduitRepository produitRepository;
	
	//On définit le @RequestParam si le nom du param est different que celui definit dans l'url
	@RequestMapping(value = "/index")
	public String index(Model model, 
			@RequestParam(name="page", defaultValue="0") int page, 
			@RequestParam(name="nb", defaultValue="5") int size, 
			@RequestParam(name="mc", defaultValue="") String mc) {
		
		//Page<Produit> pageProduits = produitRepository.findAll(new PageRequest(page, size));
		Page<Produit> pageProduits = produitRepository.chercher("%"+mc+"%", new PageRequest(page, size));
		model.addAttribute("listProduits", pageProduits.getContent());
		int[] pages = new int[pageProduits.getTotalPages()];
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", page);
		model.addAttribute("mc", mc);
		return "produits";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(Long id, String mc, int page){
		produitRepository.delete(id);
		
		return "redirect:/index?page="+page+"&mc="+mc;
	}
	
	@RequestMapping(value="/form", method=RequestMethod.GET)
	public String formProduit(Model model){
		model.addAttribute("produit", new Produit());
		return "formProduit";
	}
	
	@RequestMapping(value="/addOrUpdate", method=RequestMethod.POST)
	public String addProduit(Model model, @Valid Produit produit, BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			return "formProduit";
		}
		produitRepository.save(produit);
		
		return "confirmation";
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String edit(Long id, Model model){
		Produit p = produitRepository.findOne(id);
		model.addAttribute("produit", p);
		produitRepository.delete(id);
		
		return "formProduit";
	}
}
