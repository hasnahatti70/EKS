package com.example.formulaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.formulaire.model.Utilisateur;
import com.example.formulaire.repository.UtilisateurRepository;

@Controller
public class FormulaireController {
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping("/")
    public String redirigerVersFormulaire() {
        return "redirect:/formulaire";
    }

    @GetMapping("/formulaire")
    public String afficherFormulaire(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "formulaire";
    }

    @PostMapping("/soumettre")
    public String soumettreFormulaire(@ModelAttribute Utilisateur utilisateur) {
        utilisateurRepository.save(utilisateur);
        return "redirect:/formulaire";
    }
}