package it.prova.pokeronline.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.repository.TavoloRepository;

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
	public Tavolo aggiorna(Tavolo buildTavoloModel) {
		return tavoloRepository.save(buildTavoloModel);
	}

	@Override
	@Transactional
	public void rimuovi(Tavolo tavolo) {
		tavoloRepository.delete(tavolo);
	}
}
