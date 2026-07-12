package me.needkg.daynightpvp.feature.command;

import me.needkg.daynightpvp.shared.logging.Logger;
import me.needkg.daynightpvp.feature.config.providers.impl.MessagesConfigProvider;
import me.needkg.daynightpvp.shared.lifecycle.Initializable;

public class CommandInitializer implements Initializable {

    private final Logger logger;
    private final MessagesConfigProvider messagesConfigProvider;

    public CommandInitializer(Logger logger, MessagesConfigProvider messagesConfigProvider) {
        this.logger = logger;
        this.messagesConfigProvider = messagesConfigProvider;
    }

    @Override
    public void init() {
        
    }

}
