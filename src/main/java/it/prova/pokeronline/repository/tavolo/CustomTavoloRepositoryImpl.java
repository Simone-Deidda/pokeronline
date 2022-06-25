package it.prova.pokeronline.repository.tavolo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public class CustomTavoloRepositoryImpl implements CustomTavoloRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Tavolo> findByExample(Tavolo example, Utente special) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder(
				"select t from Tavolo t join fetch t.proprietarioTavolo p where t.id = t.id ");

		if (StringUtils.isNotEmpty(example.getDenominazione())) {
			whereClauses.add(" t.denominazione  like :denominazione ");
			paramaterMap.put("denominazione", "%" + example.getDenominazione() + "%");
		}
		if (example.getCifraMinima() != null && example.getCifraMinima() > 0) {
			whereClauses.add(" t.cifraMinima > :cifraMinima ");
			paramaterMap.put("cifraMinima", example.getCifraMinima());
		}
		if (example.getEsperienzaMinima() != null && example.getEsperienzaMinima() > 0) {
			whereClauses.add(" t.esperienzaMinima > :esperienzaMinima ");
			paramaterMap.put("esperienzaMinima", example.getEsperienzaMinima());
		}
		if (example.getDataCreazione() != null) {
			whereClauses.add("t.dataCreazione >= :dataCreazione ");
			paramaterMap.put("dataCreazione", example.getDataCreazione());
		}
		if (special != null && special.getId() != null && special.getId() > 0) {
			whereClauses.add("p.id = :proprietario_id ");
			paramaterMap.put("proprietario_id", example.getProprietarioTavolo().getId());
		} else {
			if (example.getProprietarioTavolo() != null && example.getProprietarioTavolo().getId() != null
					&& example.getProprietarioTavolo().getId() > 0) {
				whereClauses.add("p.id = :proprietario_id ");
				paramaterMap.put("proprietario_id", example.getProprietarioTavolo().getId());
			}

		}

		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Tavolo> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tavolo.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}

}
