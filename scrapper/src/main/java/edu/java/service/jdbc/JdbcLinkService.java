package edu.java.service.jdbc;

import edu.java.domain.LinksJdbcDao;
import edu.java.dto.LinkResponse;
import edu.java.dto.LinkUpdateRequest;
import edu.java.service.LinkService;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkService implements LinkService {
    private static final int CHECK_INTERVAL = 10;
    private final LinksJdbcDao linksJdbcDao;

    @Autowired
    public JdbcLinkService(LinksJdbcDao linksJdbcDao) {
        this.linksJdbcDao = linksJdbcDao;
    }

    @Override
    public LinkResponse addLink(LinkUpdateRequest linkRequest) {
        return linksJdbcDao.add(linkRequest);
    }

    @Override
    public void removeLink(long id) {
        linksJdbcDao.remove(id);
    }

    @Override
    public List<LinkResponse> getAllLinks() {
        return linksJdbcDao.findAll();
    }

    @Override
    public List<LinkResponse> getStaleLinks() {
        List<LinkResponse> links = linksJdbcDao.findAll();

        return linksJdbcDao.findAll().stream()
            .filter(link -> ChronoUnit.MINUTES.between(link.getLastUpdateTime(), OffsetDateTime.now()) > CHECK_INTERVAL)
            .collect(Collectors.toList());
    }
}
