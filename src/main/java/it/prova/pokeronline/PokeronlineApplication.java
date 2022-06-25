package it.prova.pokeronline;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.RuoloService;
import it.prova.pokeronline.service.UtenteService;

@SpringBootApplication
public class PokeronlineApplication implements CommandLineRunner {
	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;

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

		if (utenteServiceInstance.findByUsername("admin") == null) {
			Utente admin = new Utente("admin", "admin", "Mario", "Rossi");
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
		if (utenteServiceInstance.findByUsername("special") == null) {
			Utente specialUser = new Utente("special", "special", "Giovanni", "Bianchi");
			specialUser.getRuoli().add(
					ruoloServiceInstance.cercaPerDescrizioneECodice("Special Player User", Ruolo.ROLE_SPECIAL_PLAYER));

			utenteServiceInstance.inserisciNuovo(specialUser);
			utenteServiceInstance.changeUserAbilitation(specialUser.getId());
		}
	}

}
