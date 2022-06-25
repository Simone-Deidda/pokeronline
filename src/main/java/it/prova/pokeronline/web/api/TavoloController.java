package it.prova.pokeronline.web.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.exceptions.IdNotNullForInsertException;
import it.prova.pokeronline.exceptions.ProprietarioTavoloNonInseritoException;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;

@RestController
@RequestMapping("api/tavolo")
public class TavoloController {
	@Autowired
	private TavoloService tavoloService;
	@Autowired
	private UtenteService utenteService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TavoloDTO createNew( @RequestBody TavoloDTO tavoloInput) {
		if (tavoloInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");
		if (tavoloInput.getIdProprietario() == null || tavoloInput.getIdProprietario() < 1) {
			throw new ProprietarioTavoloNonInseritoException("Non è stato inserito il Proprietario del Tavolo.");
		}

		Tavolo tavoloInserito = tavoloService.inserisciNuovo(tavoloInput.buildTavoloModel());
		return TavoloDTO.buildTavoloDTOFromModel(tavoloInserito);
	}
}
