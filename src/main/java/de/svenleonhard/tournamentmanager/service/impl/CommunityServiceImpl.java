package de.svenleonhard.tournamentmanager.service.impl;

import de.svenleonhard.tournamentmanager.domain.Community;
import de.svenleonhard.tournamentmanager.repository.CommunityRepository;
import de.svenleonhard.tournamentmanager.service.CommunityService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Community}.
 */
@Service
@Transactional
public class CommunityServiceImpl implements CommunityService {
    private final Logger log = LoggerFactory.getLogger(CommunityServiceImpl.class);

    private final CommunityRepository communityRepository;

    public CommunityServiceImpl(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    /**
     * Save a community.
     *
     * @param community the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Community save(Community community) {
        log.debug("Request to save Community : {}", community);
        return communityRepository.save(community);
    }

    /**
     * Get all the communities.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Community> findAll() {
        log.debug("Request to get all Communities");
        return communityRepository.findAll();
    }

    /**
     * Get one community by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Community> findOne(Long id) {
        log.debug("Request to get Community : {}", id);
        return communityRepository.findById(id);
    }

    /**
     * Delete the community by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Community : {}", id);

        communityRepository.deleteById(id);
    }
}
