package edu.java;

import edu.java.service.LinkUpdateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class LinkUpdaterScheduler {
    private final LinkUpdateService linkUpdateService;

    @Autowired
    public LinkUpdaterScheduler(LinkUpdateService linkUpdateService) {
        this.linkUpdateService = linkUpdateService;
    }

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        linkUpdateService.updateStaleLinks();
    }
}
