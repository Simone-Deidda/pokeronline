package it.prova.pokeronline.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.StatoUtente;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.utente.UtenteRepository;

@Service
public class UtenteServiceImpl implements UtenteService {
	@Autowired
	private UtenteRepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public Utente findByUsername(String username) {
		return repository.findByUsername(username).orElse(null);
	}

	@Override
	@Transactional
	public Utente findByUsernameEAggiornaCredito(String name, Integer creditoInAggiunta) {
		Utente utente = repository.findByUsername(name).orElse(null);
		if (utente == null) {
			return utente;
		}
		utente.setCreditoAccumulato(utente.getCreditoAccumulato() + creditoInAggiunta);
		return utente;
	}

	@Override
	@Transactional(readOnly = true)
	public Utente caricaSingoloUtente(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Utente inserisciNuovo(Utente utenteInstance) {
		utenteInstance.setStato(StatoUtente.CREATO);
		utenteInstance.setPassword(passwordEncoder.encode(utenteInstance.getPassword()));
		utenteInstance.setDataRegistrazione(LocalDate.now());
		utenteInstance.setEsperienzaAccumulata(0);
		utenteInstance.setCreditoAccumulato(0);
		return repository.save(utenteInstance);
	}

	@Override
	@Transactional
	public void changeUserAbilitation(Long utenteInstanceId) {
		Utente utenteInstance = caricaSingoloUtente(utenteInstanceId);
		if (utenteInstance == null)
			throw new RuntimeException("Elemento non trovato.");

		if (utenteInstance.getStato() == null || utenteInstance.getStato().equals(StatoUtente.CREATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
		else if (utenteInstance.getStato().equals(StatoUtente.ATTIVO))
			utenteInstance.setStato(StatoUtente.DISABILITATO);
		else if (utenteInstance.getStato().equals(StatoUtente.DISABILITATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Utente> caricaListaUtenti() {
		return (List<Utente>) repository.findAll();
	}

	@Override
	@Transactional
	public Utente aggiorna(Utente utenteInstance) {
		return repository.save(utenteInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Utente utente) {
		utente.setStato(StatoUtente.DISABILITATO);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Utente> findByExample(Utente example) {
		return repository.findByExample(example);
	}

	@Override
	@Transactional
	public void giocaPartita(Utente utenteLoggato) {
		double segno = Math.random();
		int totale = (int) ((segno >= 0.5) ? segno * 100 : -(segno * 100));

		utenteLoggato.setCreditoAccumulato(
				totale + utenteLoggato.getCreditoAccumulato() < 0 ? 0 : totale + utenteLoggato.getCreditoAccumulato());
		utenteLoggato.setEsperienzaAccumulata(utenteLoggato.getEsperienzaAccumulata() + 1);
		repository.save(utenteLoggato);
	}

}
