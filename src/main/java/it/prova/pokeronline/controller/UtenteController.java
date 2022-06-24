package it.prova.pokeronline.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.security.dto.UtenteInfoJWTResponseDTO;
import it.prova.pokeronline.service.UtenteService;

@RestController
@RequestMapping("/api/utente")
public class UtenteController {
	@Autowired
	private UtenteService utenteService;

	@GetMapping(value = "/userInfo")
	public ResponseEntity<UtenteInfoJWTResponseDTO> getUserInfo() {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		Utente utenteLoggato = utenteService.findByUsername(username);
		List<String> ruoli = utenteLoggato.getRuoli().stream().map(item -> item.getCodice())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new UtenteInfoJWTResponseDTO(utenteLoggato.getNome(), utenteLoggato.getCognome(),
				utenteLoggato.getUsername(), utenteLoggato.getDataRegistrazione(),
				utenteLoggato.getEsperienzaAccumulata(), utenteLoggato.getCreditoAccumulato(), ruoli));
	}
}
