package it.prova.pokeronline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.RuoloService;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;

@SpringBootApplication
public class PokeronlineApplication implements CommandLineRunner {
	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;
	@Autowired
	private TavoloService tavoloServiceInstance;

	public static void main(String[] args) {
		SpringApplication.run(PokeronlineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", Ruolo.ROLE_ADMIN));
		}
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Player User", Ruolo.ROLE_PLAYER) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Player User", Ruolo.ROLE_PLAYER));
		}
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Special Player User", Ruolo.ROLE_SPECIAL_PLAYER) == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Special Player User", Ruolo.ROLE_SPECIAL_PLAYER));
		}

		Utente admin = utenteServiceInstance.findByUsername("admin");
		if (admin == null) {
			admin = new Utente("admin", "admin", "Mario", "Rossi");
			admin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", Ruolo.ROLE_ADMIN));

			utenteServiceInstance.inserisciNuovo(admin);
			utenteServiceInstance.changeUserAbilitation(admin.getId());
		}
		if (utenteServiceInstance.findByUsername("player") == null) {
			Utente playerUser = new Utente("player", "player", "Antonio", "Verdi");
			playerUser.getRuoli()
					.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Player User", Ruolo.ROLE_PLAYER));

			utenteServiceInstance.inserisciNuovo(playerUser);
			utenteServiceInstance.changeUserAbilitation(playerUser.getId());
		}
		Utente specialUser = utenteServiceInstance.findByUsername("special");
		if (specialUser == null) {
			specialUser = new Utente("special", "special", "Giovanni", "Bianchi");
			specialUser.getRuoli().add(
					ruoloServiceInstance.cercaPerDescrizioneECodice("Special Player User", Ruolo.ROLE_SPECIAL_PLAYER));

			utenteServiceInstance.inserisciNuovo(specialUser);
			utenteServiceInstance.changeUserAbilitation(specialUser.getId());
		}

		if (tavoloServiceInstance.cercaPerDenominazione("Tavolo1") == null) {
			Tavolo tavoloAdmin = new Tavolo("Tavolo1", 0, 0);
			tavoloAdmin.setProprietarioTavolo(admin);
			tavoloServiceInstance.inserisciNuovo(tavoloAdmin);
		}
		if (tavoloServiceInstance.cercaPerDenominazione("Tavolo2") == null) {
			Tavolo tavoloSpecial = new Tavolo("Tavolo2", 0, 0);
			tavoloSpecial.setProprietarioTavolo(specialUser);
			tavoloServiceInstance.inserisciNuovo(tavoloSpecial);
		}
	}

}
