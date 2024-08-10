package edu.java.service.jdbc;

import edu.java.BotClient;
import edu.java.clients.github.GitHubClient;
import edu.java.clients.github.GitHubEvent;
import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.clients.stackoverflow.StackOverflowItem;
import edu.java.domain.jdbc.LinksJdbcDao;
import edu.java.dto.LinkResponse;
import edu.java.dto.LinkUpdateRequest;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdateService;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkUpdateService implements LinkUpdateService {
    private final LinksJdbcDao jdbcDao;
    private final LinkService linksService;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final BotClient botClient;

    @Autowired
    public JdbcLinkUpdateService(
        LinksJdbcDao jdbcDao,
        LinkService linksService, GitHubClient gitHubClient,
        StackOverflowClient stackOverflowClient,
        BotClient botClient
    ) {
        this.jdbcDao = jdbcDao;
        this.linksService = linksService;
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;
        this.botClient = botClient;
    }

    @Override
    public int update(Long linkId) {
        return jdbcDao.update(linkId);
    }

    @Override
    public void updateStaleLinks() {
        for (LinkResponse response : linksService.getStaleLinks()) {
            if (response.getUrl().contains("github")) {
                List<GitHubEvent> eventList = gitHubClient.getRepositoryEvents(
                    GitHubClient.getOwnerFromLink(response.getUrl()),
                    GitHubClient.getRepoFromLink(response.getUrl()),
                    response.getLastUpdateTime(),
                    OffsetDateTime.now()
                );

                if (!eventList.isEmpty()) {
                    botClient.processUpdate(new LinkUpdateRequest(response.getUrl(), List.of(response.getId())));
                }
            } else {
                List<StackOverflowItem> itemList = stackOverflowClient.getQuestionUpdates(
                    StackOverflowClient.getQuestionIdFromLink(response.getUrl()),
                    response.getLastUpdateTime(),
                    OffsetDateTime.now()
                ).getItems();

                if (!itemList.isEmpty()) {
                    botClient.processUpdate(new LinkUpdateRequest(response.getUrl(), List.of(response.getId())));
                }
            }
        }
    }
}
