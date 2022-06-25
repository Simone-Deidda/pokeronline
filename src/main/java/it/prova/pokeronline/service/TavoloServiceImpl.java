package it.prova.pokeronline.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.repository.TavoloRepository;

@Service
public class TavoloServiceImpl implements TavoloService {
	@Autowired
	private TavoloRepository tavoloRepository;

	@Override
	public Tavolo inserisciNuovo(Tavolo buildTavoloModel) {
		buildTavoloModel.setDataCreazione(LocalDate.now());
		buildTavoloModel.setGiocatori(null);
		return tavoloRepository.save(buildTavoloModel);
	}
}
