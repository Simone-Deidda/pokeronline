package it.prova.pokeronline.web.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.SimpleUtenteDTO;
import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.exceptions.IdNotNullForInsertException;
import it.prova.pokeronline.exceptions.PresentiGiocatoriException;
import it.prova.pokeronline.exceptions.ProprietarioTavoloNonInseritoException;
import it.prova.pokeronline.exceptions.TavoloNotFoundException;
import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;

@RestController
@RequestMapping("api/tavolo")
public class TavoloController {
	@Autowired
	private TavoloService tavoloService;
	@Autowired
	private UtenteService utenteService;

	@GetMapping
	public List<TavoloDTO> getAll() {
		Utente utenteLoggato = utenteService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		if (utenteLoggato.getRuoli().stream().map(ruolo -> ruolo.getCodice()).collect(Collectors.toList())
				.contains(Ruolo.ROLE_SPECIAL_PLAYER)) {
			return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.cercaPerProprietario(utenteLoggato.getId()),
					true);
		}

		return TavoloDTO.createTavoloDTOListFromModelList(tavoloService.listAllElementsEager(), true);
	}

	@GetMapping("/{id}")
	public TavoloDTO findById(@PathVariable(value = "id", required = true) long id) {
		Tavolo tavolo = tavoloService.caricaSingoloElementoEager(id);

		if (tavolo == null)
			throw new TavoloNotFoundException("Tavolo not found con id: " + id);

		return TavoloDTO.buildTavoloDTOFromModel(tavolo);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TavoloDTO createNew(@Valid @RequestBody TavoloDTO tavoloInput) {
		if (tavoloInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");
		if (tavoloInput.getProprietario() == null || tavoloInput.getProprietario().getId() == null
				|| tavoloInput.getProprietario().getId() < 1) {
			throw new ProprietarioTavoloNonInseritoException("Non è stato inserito il Proprietario del Tavolo.");
		}
		tavoloInput.setProprietario(SimpleUtenteDTO.buildSimpleUtenteDTOFromModel(
										utenteService.caricaSingoloUtente(
													tavoloInput.getProprietario().getId())));
		Tavolo tavoloInserito = tavoloService.inserisciNuovo(tavoloInput.buildTavoloModel());
		return TavoloDTO.buildTavoloDTOFromModel(tavoloInserito);
	}
	
	@PutMapping("/{id}")
	public TavoloDTO update(@Valid @RequestBody TavoloDTO tavoloInput, @PathVariable(required = true) Long id) {
		Tavolo tavolo = tavoloService.caricaSingoloElementoEager(id);

		if (tavolo == null)
			throw new TavoloNotFoundException("Tavolo not found con id: " + id);
		if (tavoloInput.getGiocatori() != null || !tavoloInput.getGiocatori().isEmpty()) {
			throw new PresentiGiocatoriException("Non è possibile modificare Tavolo quando ci sono giocatori collegati.");
		}
		if (tavoloInput.getProprietario() == null || tavoloInput.getProprietario().getId() == null
				|| tavoloInput.getProprietario().getId() < 1) {
			throw new ProprietarioTavoloNonInseritoException("Non è stato inserito il Proprietario del Tavolo.");
		}
		tavoloInput.setProprietario(SimpleUtenteDTO.buildSimpleUtenteDTOFromModel(
										utenteService.caricaSingoloUtente(
													tavoloInput.getProprietario().getId())));

		tavoloInput.setDataCreazione(tavolo.getDataCreazione());
		tavoloInput.setId(id);
		Tavolo tavoloAggiornato = tavoloService.aggiorna(tavoloInput.buildTavoloModel());
		return TavoloDTO.buildTavoloDTOFromModel(tavoloAggiornato);
	}
}
