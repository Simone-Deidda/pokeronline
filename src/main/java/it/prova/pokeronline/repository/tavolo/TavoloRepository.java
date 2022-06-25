package it.prova.pokeronline.repository.tavolo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import it.prova.pokeronline.model.Tavolo;

public interface TavoloRepository extends CrudRepository<Tavolo, Long>, CustomTavoloRepository {

	@EntityGraph(attributePaths = { "giocatori", "proprietarioTavolo" })
	List<Tavolo> findAll();

	Optional<Tavolo> findByDenominazione(String string);

	@EntityGraph(attributePaths = { "giocatori", "proprietarioTavolo" })
	List<Tavolo> findAllByProprietarioTavolo_Id(Long id);

	@EntityGraph(attributePaths = { "giocatori", "proprietarioTavolo" })
	Optional<Tavolo> findFirstByGiocatori_Id(Long id);

	@EntityGraph(attributePaths = { "giocatori", "proprietarioTavolo" })
	List<Tavolo> findByEsperienzaMinimaLessThanEqual(Integer esperienzaAccumulata);

}
