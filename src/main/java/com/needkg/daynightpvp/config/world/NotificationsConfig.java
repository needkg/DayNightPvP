package com.needkg.daynightpvp.config.world;

import java.util.Optional;
import com.needkg.daynightpvp.feature.ChatNotificationFeature;
import com.needkg.daynightpvp.feature.SoundNotificationFeature;
import com.needkg.daynightpvp.feature.TitleNotificationFeature;

public record NotificationsConfig(
        Optional<ChatNotificationFeature.Config> chat,
        Optional<TitleNotificationFeature.Config> title,
        Optional<SoundNotificationFeature.Config> sound) {

}
