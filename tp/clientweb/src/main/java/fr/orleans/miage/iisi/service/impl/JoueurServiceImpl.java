package fr.orleans.miage.iisi.service.impl;

import fr.orleans.miage.iisi.service.JoueurService;
import fr.orleans.miage.iisi.domain.Joueur;
import fr.orleans.miage.iisi.repository.JoueurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Joueur.
 */
@Service
@Transactional
public class JoueurServiceImpl implements JoueurService {

    private final Logger log = LoggerFactory.getLogger(JoueurServiceImpl.class);

    private final JoueurRepository joueurRepository;

    public JoueurServiceImpl(JoueurRepository joueurRepository) {
        this.joueurRepository = joueurRepository;
    }

    /**
     * Save a joueur.
     *
     * @param joueur the entity to save
     * @return the persisted entity
     */
    @Override
    public Joueur save(Joueur joueur) {
        log.debug("Request to save Joueur : {}", joueur);
        return joueurRepository.save(joueur);
    }

    /**
     * Get all the joueurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Joueur> findAll(Pageable pageable) {
        log.debug("Request to get all Joueurs");
        return joueurRepository.findAll(pageable);
    }


    /**
     * Get one joueur by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Joueur> findOne(Long id) {
        log.debug("Request to get Joueur : {}", id);
        return joueurRepository.findById(id);
    }

    /**
     * Delete the joueur by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Joueur : {}", id);        joueurRepository.deleteById(id);
    }
}
