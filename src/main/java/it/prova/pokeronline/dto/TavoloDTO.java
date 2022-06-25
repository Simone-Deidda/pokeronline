package it.prova.pokeronline.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public class TavoloDTO {
	private Long id;
	@NotBlank(message = "{denominazione.notblank}")
	@Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String denominazione;
	private LocalDate dataCreazione;
	@NotNull(message = "{esperienzaMinima.notnull}")
	@Min(0)
	private Integer esperienzaMinima;
	@NotNull(message = "{cifraMinima.notnull}")
	@Min(0)
	private Integer cifraMinima;

	private SimpleUtenteDTO proprietario;
	private List<SimpleUtenteDTO> giocatori;

	public TavoloDTO() {
	}

	public TavoloDTO(Long id, String denominazione, LocalDate dataCreazione, Integer esperienzaMinima,
			Integer cifraMinima) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
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

	public SimpleUtenteDTO getProprietario() {
		return proprietario;
	}

	public void setProprietario(SimpleUtenteDTO proprietario) {
		this.proprietario = proprietario;
	}

	public List<SimpleUtenteDTO> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(List<SimpleUtenteDTO> giocatori) {
		this.giocatori = giocatori;
	}

	public Tavolo buildTavoloModel() {
		Tavolo result = new Tavolo(this.id, this.denominazione, this.dataCreazione, this.esperienzaMinima,
				this.cifraMinima);

		if (this.proprietario != null && this.proprietario.getId() != null && this.proprietario.getId() > 0) {
			result.setProprietarioTavolo(proprietario.buildUtenteModel());
		}
		if (this.giocatori != null && !this.giocatori.isEmpty()) {
			result.setGiocatori(
					this.giocatori.stream().map(entity -> entity.buildUtenteModel()).collect(Collectors.toSet()));
		}
		return result;
	}

	public static TavoloDTO buildTavoloDTOFromModel(Tavolo tavoloInserito) {
		TavoloDTO result = new TavoloDTO(tavoloInserito.getId(), tavoloInserito.getDenominazione(),
				tavoloInserito.getDataCreazione(), tavoloInserito.getEsperienzaMinima(),
				tavoloInserito.getCifraMinima());

		if (tavoloInserito.getProprietarioTavolo() != null && tavoloInserito.getProprietarioTavolo().getId() != null
				&& tavoloInserito.getProprietarioTavolo().getId() > 0) {
			result.setProprietario(SimpleUtenteDTO.buildSimpleUtenteDTOFromModel(tavoloInserito.getProprietarioTavolo()));
		}
		if (tavoloInserito.getGiocatori() != null && !tavoloInserito.getGiocatori().isEmpty()) {
			result.setGiocatori(SimpleUtenteDTO.createSimpleUtenteDTOListFromModelSet(tavoloInserito.getGiocatori()));
		}
		return result;
	}

	public static List<TavoloDTO> createTavoloDTOListFromModelList(List<Tavolo> listAllElementsEager, boolean b) {
		return listAllElementsEager.stream().map(entity -> TavoloDTO.buildTavoloDTOFromModel(entity))
				.collect(Collectors.toList());
	}

}
