package edu.java.service.jpa;

import edu.java.BotClient;
import edu.java.clients.github.GitHubClient;
import edu.java.clients.github.GitHubEvent;
import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.clients.stackoverflow.StackOverflowItem;
import edu.java.dto.LinkResponse;
import edu.java.dto.LinkUpdateRequest;
import edu.java.service.LinkService;
import edu.java.service.LinkUpdateService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class JpaLinkUpdateService implements LinkUpdateService {

    private final LinkService linksService;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final BotClient botClient;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public JpaLinkUpdateService(
        LinkService linksService,
        GitHubClient gitHubClient,
        StackOverflowClient stackOverflowClient,
        BotClient botClient
    ) {
        this.linksService = linksService;
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;
        this.botClient = botClient;
    }

    @Override
    public int update(Long linkId) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        String sql = "UPDATE links SET last_update_time = :time WHERE id = :linkId";
        return entityManager.createNativeQuery(sql)
            .setParameter("time", currentTime)
            .setParameter("linkId", linkId)
            .executeUpdate();
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
