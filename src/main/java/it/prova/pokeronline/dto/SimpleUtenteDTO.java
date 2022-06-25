package it.prova.pokeronline.dto;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Utente;

public class SimpleUtenteDTO {
	private Long id;
	private String username;
	private String nome;
	private String cognome;

	public SimpleUtenteDTO() {
	}

	public SimpleUtenteDTO(Long id, String username, String nome, String cognome) {
		super();
		this.id = id;
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public static SimpleUtenteDTO buildSimpleUtenteDTOFromModel(Utente utenteModel) {
		return new SimpleUtenteDTO(utenteModel.getId(), utenteModel.getUsername(), utenteModel.getNome(),
				utenteModel.getCognome());

	}

	public Utente buildUtenteModel() {
		return new Utente(this.id, this.username, this.nome, this.cognome);

	}

	public static List<SimpleUtenteDTO> createSimpleUtenteDTOListFromModelSet(Set<Utente> giocatori) {
		return giocatori.stream().map(entity -> SimpleUtenteDTO.buildSimpleUtenteDTOFromModel(entity)).collect(Collectors.toList());
	}
}
