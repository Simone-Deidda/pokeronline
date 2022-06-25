package it.prova.pokeronline.repository.untete;

import java.util.List;

import it.prova.pokeronline.model.Utente;

public interface CustomUtenteRepository {
	List<Utente> findByExample(Utente example);
}
