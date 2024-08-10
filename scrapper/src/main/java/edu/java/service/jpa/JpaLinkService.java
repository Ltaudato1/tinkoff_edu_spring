package edu.java.service.jpa;

import edu.java.domain.jpa.LinksJpaDao;
import edu.java.dto.LinkResponse;
import edu.java.dto.LinkUpdateRequest;
import edu.java.service.LinkService;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class JpaLinkService implements LinkService {

    private static final int CHECK_INTERVAL = 10;

    private final LinksJpaDao jpaDao;

    @Autowired
    public JpaLinkService(LinksJpaDao jpaDao) {
        this.jpaDao = jpaDao;
    }

    @Override
    public LinkResponse addLink(LinkUpdateRequest linkRequest) {
        return jpaDao.add(linkRequest);
    }

    @Override
    public void removeLink(long id) {
        jpaDao.remove(id);
    }

    @Override
    public List<LinkResponse> getAllLinks() {
        return jpaDao.findAll();
    }

    @Override
    public List<LinkResponse> getStaleLinks() {
        return jpaDao.findAll().stream()
            .filter(link -> ChronoUnit.MINUTES.between(link.getLastUpdateTime(), OffsetDateTime.now()) > CHECK_INTERVAL)
            .collect(Collectors.toList());
    }
}
