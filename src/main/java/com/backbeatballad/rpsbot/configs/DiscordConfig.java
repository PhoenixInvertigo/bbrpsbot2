package com.backbeatballad.rpsbot.configs;

import com.mewna.catnip.Catnip;
import lombok.Getter;
import lombok.Setter;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Getter
@Setter
public class DiscordConfig {

    @Value("${discord.token}")
    private String token;

    DiscordApi api;

    Catnip catnip;

    @PostConstruct
    private void Login() {
        catnip = Catnip.catnip(token);
    }
}
