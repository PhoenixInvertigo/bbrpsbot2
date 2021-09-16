package com.backbeatballad.rpsbot.configs;

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

    @PostConstruct
    private void Login() {
        api = new DiscordApiBuilder()
                .setToken(token)
                .login().join();

        System.out.println("Connected");
    }
}
