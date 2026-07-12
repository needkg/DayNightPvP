package me.needkg.daynightpvp.feature.command;

import me.needkg.daynightpvp.feature.config.repository.models.MessagesSettings;
import me.needkg.daynightpvp.shared.lifecycle.Initializable;
import me.needkg.daynightpvp.shared.logging.Logger;

public class CommandInitializer implements Initializable {

    private final Logger logger;
    private final MessagesSettings messagesSettings;

    public CommandInitializer(Logger logger, MessagesSettings messageSettings) {
        this.logger = logger;
        this.messagesSettings = messageSettings;
    }

    @Override
    public void init() {
        
    }

}
