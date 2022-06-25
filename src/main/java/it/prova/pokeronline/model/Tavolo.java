package it.prova.pokeronline.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tavolo")
public class Tavolo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "denominazione")
	private String denominazione;
	@Column(name = "datacreazione")
	private LocalDate dataCreazione;
	@Column(name = "esperienzaminima")
	private Integer esperienzaMinima;
	@Column(name = "ciframinima")
	private Integer cifraMinima;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utente_id", nullable = false, referencedColumnName = "id")
	private Utente proprietarioTavolo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tavolo")
	private Set<Utente> giocatori = new HashSet<>();

	public Tavolo() {
	}

	public Tavolo(Long id, String denominazione, LocalDate dataCreazione, Integer esperienzaMinima,
			Integer cifraMinima) {
		this.id = id;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.esperienzaMinima = esperienzaMinima;
		this.cifraMinima = cifraMinima;
	}

	public Tavolo(String denominazione, int esperienzaMinima, int cifraMinima) {
		this.denominazione = denominazione;
		this.esperienzaMinima = esperienzaMinima;
		this.cifraMinima = cifraMinima;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public LocalDate getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDate dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Integer getEsperienzaMinima() {
		return esperienzaMinima;
	}

	public void setEsperienzaMinima(Integer esperienzaMinima) {
		this.esperienzaMinima = esperienzaMinima;
	}

	public Integer getCifraMinima() {
		return cifraMinima;
	}

	public void setCifraMinima(Integer cifraMinima) {
		this.cifraMinima = cifraMinima;
	}

	public Utente getProprietarioTavolo() {
		return proprietarioTavolo;
	}

	public void setProprietarioTavolo(Utente proprietarioTavolo) {
		this.proprietarioTavolo = proprietarioTavolo;
	}

	public Set<Utente> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(Set<Utente> giocatori) {
		this.giocatori = giocatori;
	}

}
