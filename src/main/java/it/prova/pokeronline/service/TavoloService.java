package it.prova.pokeronline.service;

import java.util.List;

import it.prova.pokeronline.model.Tavolo;

public interface TavoloService {

	Tavolo inserisciNuovo(Tavolo buildTavoloModel);

	List<Tavolo> listAllElementsEager();

	Tavolo cercaPerDenominazione(String string);

	List<Tavolo> cercaPerProprietario(Long id);

	Tavolo caricaSingoloElementoEager(long id);

	Tavolo aggiorna(Tavolo buildTavoloModel);

	void rimuovi(Tavolo tavolo);

}
