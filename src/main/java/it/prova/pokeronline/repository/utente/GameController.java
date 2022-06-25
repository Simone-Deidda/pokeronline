package it.prova.pokeronline.repository.utente;


import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.exceptions.TavoloNotFoundException;
import it.prova.pokeronline.exceptions.UtenteNotFoundException;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;

@RestController
@RequestMapping("api/game")
public class GameController {
	@Autowired
	private TavoloService tavoloService;
	@Autowired
	private UtenteService utenteService;
	
	@GetMapping("/buyCredit")
	public UtenteDTO compraCredito(@PathParam(value = "credito") Integer credito) {
		Utente utenteLoggato = utenteService
				.findByUsernameEAggiornaCredito(SecurityContextHolder.getContext().getAuthentication().getName(), credito);
		if (utenteLoggato == null) {
			throw new UtenteNotFoundException();
		}
		
		return UtenteDTO.buildUtenteDTOFromModel(utenteLoggato);
	}
	
	@GetMapping("/{id}")
	public TavoloDTO partecipaAlGioco(@PathVariable(value = "id", required = true) long id) {
		Utente utenteLoggato = utenteService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		Tavolo tavolo = tavoloService.caricaSingoloElementoEagerEAggiongiGiocatore(id, utenteLoggato);
		if (tavolo == null)
			throw new TavoloNotFoundException("Tavolo not found con id: " + id);
		

		return TavoloDTO.buildTavoloDTOFromModel(tavolo);
	}
	
	@GetMapping("/lastGame")
	public TavoloDTO lastGame() {
		Utente utenteLoggato = utenteService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		Tavolo tavolo = tavoloService.cercaUltimaPartita(utenteLoggato);
		if (tavolo == null)
			throw new TavoloNotFoundException("Tavolo not found per Utente con id: " + utenteLoggato.getId());

		return TavoloDTO.buildTavoloDTOFromModel(tavolo);
	}
}
