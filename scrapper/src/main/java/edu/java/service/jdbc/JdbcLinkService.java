package edu.java.service.jdbc;

import edu.java.domain.jdbc.LinksJdbcDao;
import edu.java.dto.LinkResponse;
import edu.java.dto.LinkUpdateRequest;
import edu.java.service.LinkService;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;

public class JdbcLinkService implements LinkService {
    private final LinksJdbcDao linksJdbcDao;

    @Autowired
    public JdbcLinkService(LinksJdbcDao linksJdbcDao) {
        this.linksJdbcDao = linksJdbcDao;
    }

    @Override
    public void addLink(LinkUpdateRequest linkRequest) {
        linksJdbcDao.add(linkRequest.getUrl(), linkRequest.getTgChatIds());
    }

    @Override
    public void removeLink(long id) {
        linksJdbcDao.remove(id);
    }

    @Override
    public List<LinkResponse> getAllLinks() {
        Map<Long, Pair<String, OffsetDateTime>> list = linksJdbcDao.findAll();
        List<LinkResponse> result = new ArrayList<>();
        for (Long id : list.keySet()) {
            result.add(new LinkResponse(id, list.get(id).getLeft(), list.get(id).getRight()));
        }
        return result;
    }

    @Override
    public List<LinkResponse> getStaleLinks() {
        Map<Long, Pair<String, OffsetDateTime>> list = linksJdbcDao.findStale();
        List<LinkResponse> result = new ArrayList<>();
        for (Long id : list.keySet()) {
            result.add(new LinkResponse(id, list.get(id).getLeft(), list.get(id).getRight()));
        }
        return result;
    }
}
