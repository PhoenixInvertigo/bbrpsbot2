package com.backbeatballad.rpsbot.listeners;

import com.backbeatballad.rpsbot.configs.DiscordConfig;
import com.backbeatballad.rpsbot.services.ThrowService;
import com.mewna.catnip.Catnip;
import com.mewna.catnip.entity.builder.command.InteractionResponseBuilder;
import com.mewna.catnip.entity.builder.component.ActionRowBuilder;
import com.mewna.catnip.entity.builder.component.ButtonBuilder;
import com.mewna.catnip.entity.channel.TextChannel;
import com.mewna.catnip.entity.guild.Member;
import com.mewna.catnip.entity.impl.channel.TextChannelImpl;
import com.mewna.catnip.entity.impl.interaction.CustomIdInteractionDataImpl;
import com.mewna.catnip.entity.impl.interaction.component.InteractionResponseImpl;
import com.mewna.catnip.entity.impl.misc.UnicodeEmojiImpl;
import com.mewna.catnip.entity.interaction.InteractionResponse;
import com.mewna.catnip.entity.interaction.InteractionType;
import com.mewna.catnip.entity.interaction.command.InteractionApplicationCommandCallbackData;
import com.mewna.catnip.entity.message.Message;
import com.mewna.catnip.entity.message.MessageOptions;
import com.mewna.catnip.entity.message.component.Button;
import com.mewna.catnip.entity.message.component.MessageComponent;
import com.mewna.catnip.entity.misc.Emoji;
import com.mewna.catnip.shard.DiscordEvent;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;

@Component
public class RpsListener {

    private DiscordConfig discordConfig;

    private ThrowService throwService;

    public RpsListener(DiscordConfig discordConfig, ThrowService throwService){
        this.discordConfig = discordConfig;
        this.throwService = throwService;
    }

    @PostConstruct
    private void AddListeners(){
        addRPSListener();
        addBombListener();
        discordConfig.getCatnip().connect();
    }

    private void addRPSListener(){
//        discordConfig.getApi().addMessageCreateListener(event -> {
//            if (event.getMessageContent().equalsIgnoreCase("!rps")) {
//                new MessageBuilder()
//                        .setContent("Choose your fighter!")
//                        .addComponents(
//                                ActionRow.of(Button.secondary("rock", "Rock", "\u270A"),
//                                        Button.secondary("paper", "Paper", "\u270B"),
//                                        Button.secondary("scissors", "Scissors", "\u270C"),
//                                        Button.secondary("throwForMe", "Throw for me", "\u2753")))
//                        .send(event.getChannel());
//            }
//        });

        discordConfig.getCatnip().observable(DiscordEvent.MESSAGE_CREATE)
                .filter(msg -> msg.content().equalsIgnoreCase("!rps2"))
                .subscribe(msg -> {
                    MessageOptions msgOps = new MessageOptions();
                    ActionRowBuilder actionRowBuilder = new ActionRowBuilder();

                    //Building the rock button
                    ButtonBuilder buttonBuilder = new ButtonBuilder();
                    buttonBuilder.style(Button.ButtonStyle.SECONDARY);
                    buttonBuilder.label("Rock");
                    buttonBuilder.customId("rock");
                    buttonBuilder.emoji(Emoji.fromUnicode(discordConfig.getCatnip(), "\u270A"));
                    Button rock = buttonBuilder.build();
                    actionRowBuilder.addComponent(rock);

                    //Building the paper button
                    buttonBuilder.style(Button.ButtonStyle.SECONDARY);
                    buttonBuilder.label("Paper");
                    buttonBuilder.customId("paper");
                    buttonBuilder.emoji(Emoji.fromUnicode(discordConfig.getCatnip(), "\u270B"));
                    Button paper = buttonBuilder.build();
                    actionRowBuilder.addComponent(paper);

                    //Building the scissors button
                    buttonBuilder.style(Button.ButtonStyle.SECONDARY);
                    buttonBuilder.label("Scissors");
                    buttonBuilder.customId("scissors");
                    buttonBuilder.emoji(Emoji.fromUnicode(discordConfig.getCatnip(), "\u270C"));
                    Button scissors = buttonBuilder.build();
                    actionRowBuilder.addComponent(scissors);

                    //Building the Throw for me button
                    buttonBuilder.style(Button.ButtonStyle.SECONDARY);
                    buttonBuilder.label("Throw for me");
                    buttonBuilder.customId("throwForMe");
                    buttonBuilder.emoji(Emoji.fromUnicode(discordConfig.getCatnip(), "\u2753"));
                    Button throwForMe = buttonBuilder.build();
                    actionRowBuilder.addComponent(throwForMe);

                    msgOps.addComponent(actionRowBuilder.build());
                    msgOps.content("Choose your fighter!");
                    msg.reply(msgOps, true);
                }, error -> error.printStackTrace());

        discordConfig.getCatnip().observable(DiscordEvent.INTERACTION_CREATE).
                subscribe(event -> {
                    CustomIdInteractionDataImpl data = (CustomIdInteractionDataImpl) event.data();
                    String customId = data.customId();
                    System.out.println(customId);

                    //Couldn't figure out how to send any data from this back to the thread/channel :/

                }, error -> error.printStackTrace());

//        discordConfig.getApi().addMessageComponentCreateListener(event -> {
//            MessageComponentInteraction messageComponentInteraction = event.getMessageComponentInteraction();
//            String customId = messageComponentInteraction.getCustomId();
//            String response = "";
//            if(event.getInteraction().getServer().isPresent()) {
//                response = event.getInteraction().getUser().getDisplayName(event.getInteraction().getServer().get()) + " has challenged the bot! \n";
//            } else{
//                response = event.getInteraction().getUser().getName() + " has challenged the bot! \n";
//            }
//
//            switch (customId) {
//                case "rock":
//                    messageComponentInteraction.createImmediateResponder()
//                            .setContent(response + throwService.rps("Rock"))
//                            .respond();
//                    messageComponentInteraction.getMessage().ifPresent(Message::delete);
//                    break;
//                case "paper":
//                    messageComponentInteraction.createImmediateResponder()
//                            .setContent(response + throwService.rps("Paper"))
//                            .respond();
//                    messageComponentInteraction.getMessage().ifPresent(Message::delete);
//                    break;
//                case "scissors":
//                    messageComponentInteraction.createImmediateResponder()
//                            .setContent(response + throwService.rps("Scissors"))
//                            .respond();
//                    messageComponentInteraction.getMessage().ifPresent(Message::delete);
//                    break;
//                case "throwForMe":
//                    messageComponentInteraction.createImmediateResponder()
//                            .setContent(response + throwService.throwForMe())
//                            .respond();
//                    messageComponentInteraction.getMessage().ifPresent(Message::delete);
//                    break;
//            }
//        });
    }

    private void addBombListener(){
//        discordConfig.getApi().addMessageCreateListener(event -> {
//            if (event.getMessageContent().equalsIgnoreCase("!rpsbomb")) {
//                new MessageBuilder()
//                        .setContent(event.getMessageAuthor().getDisplayName() + " has challenged the bot! \n" + throwService.throwBombForMe())
//                        .send(event.getChannel());
//            }
//        });

          discordConfig.getCatnip().observable(DiscordEvent.MESSAGE_CREATE)
                .filter(msg -> msg.content().equalsIgnoreCase("!rpsbomb2"))
                .subscribe(msg -> {
                    msg.reply(msg.author().username() + " has challenged the bot! \n" + throwService.throwBombForMe(), true);
                }, error -> error.printStackTrace());
    }
}
