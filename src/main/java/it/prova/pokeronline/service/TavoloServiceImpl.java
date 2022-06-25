package it.prova.pokeronline.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;

@Service
public class TavoloServiceImpl implements TavoloService {
	@Autowired
	private TavoloRepository tavoloRepository;

	@Override
	@Transactional
	public Tavolo inserisciNuovo(Tavolo buildTavoloModel) {
		buildTavoloModel.setDataCreazione(LocalDate.now());
		buildTavoloModel.setGiocatori(null);
		return tavoloRepository.save(buildTavoloModel);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tavolo> listAllElementsEager() {
		return tavoloRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Tavolo cercaPerDenominazione(String string) {
		return tavoloRepository.findByDenominazione(string).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tavolo> cercaPerProprietario(Long id) {
		return tavoloRepository.findAllByProprietarioTavolo_Id(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Tavolo caricaSingoloElementoEager(long id) {
		return tavoloRepository.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public Tavolo caricaSingoloElementoEagerEAggiongiGiocatore(long id, Utente utenteLoggato) {
		Tavolo tavolo = tavoloRepository.findById(id).orElse(null);
		if (tavolo == null) {
			return tavolo;
		}
		tavolo.setGiocatori(new HashSet<>());
		tavolo.getGiocatori().add(utenteLoggato);
		utenteLoggato.setTavolo(tavolo);
		return tavolo;
	}

	@Override
	@Transactional
	public Tavolo aggiorna(Tavolo buildTavoloModel) {
		return tavoloRepository.save(buildTavoloModel);
	}

	@Override
	@Transactional
	public void rimuovi(Tavolo tavolo) {
		tavoloRepository.delete(tavolo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tavolo> findByExample(Tavolo buildTavoloModel) {
		return tavoloRepository.findByExample(buildTavoloModel);
	}

	@Override
	public Tavolo cercaUltimaPartita(Utente utenteLoggato) {
		return tavoloRepository.findFirstByGiocatori_Id(utenteLoggato.getId()).orElse(null);
	}

	
}
