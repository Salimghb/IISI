package fr.orleans.miage.iisi.web.rest;
import fr.orleans.miage.iisi.domain.Joueur;
import fr.orleans.miage.iisi.service.JoueurService;
import fr.orleans.miage.iisi.web.rest.errors.BadRequestAlertException;
import fr.orleans.miage.iisi.web.rest.util.HeaderUtil;
import fr.orleans.miage.iisi.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Joueur.
 */
@RestController
@RequestMapping("/api")
public class JoueurResource {

    private final Logger log = LoggerFactory.getLogger(JoueurResource.class);

    private static final String ENTITY_NAME = "joueur";

    private final JoueurService joueurService;

    public JoueurResource(JoueurService joueurService) {
        this.joueurService = joueurService;
    }

    /**
     * POST  /joueurs : Create a new joueur.
     *
     * @param joueur the joueur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new joueur, or with status 400 (Bad Request) if the joueur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/joueurs")
    public ResponseEntity<Joueur> createJoueur(@RequestBody Joueur joueur) throws URISyntaxException {
        log.debug("REST request to save Joueur : {}", joueur);
        if (joueur.getId() != null) {
            throw new BadRequestAlertException("A new joueur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Joueur result = joueurService.save(joueur);
        return ResponseEntity.created(new URI("/api/joueurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /joueurs : Updates an existing joueur.
     *
     * @param joueur the joueur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated joueur,
     * or with status 400 (Bad Request) if the joueur is not valid,
     * or with status 500 (Internal Server Error) if the joueur couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/joueurs")
    public ResponseEntity<Joueur> updateJoueur(@RequestBody Joueur joueur) throws URISyntaxException {
        log.debug("REST request to update Joueur : {}", joueur);
        if (joueur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Joueur result = joueurService.save(joueur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, joueur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /joueurs : get all the joueurs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of joueurs in body
     */
    @GetMapping("/joueurs")
    public ResponseEntity<List<Joueur>> getAllJoueurs(Pageable pageable) {
        log.debug("REST request to get a page of Joueurs");
        Page<Joueur> page = joueurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/joueurs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /joueurs/:id : get the "id" joueur.
     *
     * @param id the id of the joueur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the joueur, or with status 404 (Not Found)
     */
    @GetMapping("/joueurs/{id}")
    public ResponseEntity<Joueur> getJoueur(@PathVariable Long id) {
        log.debug("REST request to get Joueur : {}", id);
        Optional<Joueur> joueur = joueurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(joueur);
    }

    /**
     * DELETE  /joueurs/:id : delete the "id" joueur.
     *
     * @param id the id of the joueur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/joueurs/{id}")
    public ResponseEntity<Void> deleteJoueur(@PathVariable Long id) {
        log.debug("REST request to delete Joueur : {}", id);
        joueurService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
