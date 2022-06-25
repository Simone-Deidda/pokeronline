package it.prova.pokeronline.service;

import java.util.List;

import it.prova.pokeronline.model.Utente;

public interface UtenteService {
	public Utente findByUsername(String username);
	
	public Utente inserisciNuovo(Utente utenteInstance);
	
	public void changeUserAbilitation(Long utenteInstanceId);

	Utente caricaSingoloUtente(Long id);

	public List<Utente> caricaListaUtenti();

	public Utente aggiorna(Utente buildUtenteModel);

	public void rimuovi(Utente utente);

}
