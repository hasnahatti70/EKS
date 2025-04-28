package com.example.formulaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.formulaire.model.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
}