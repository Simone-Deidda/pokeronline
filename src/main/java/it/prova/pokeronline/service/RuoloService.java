package it.prova.pokeronline.service;

import it.prova.pokeronline.model.Ruolo;

public interface RuoloService {
	public void inserisciNuovo(Ruolo ruoloInstance);
	
	public Ruolo cercaPerDescrizioneECodice(String descrizione, String codice);
}
