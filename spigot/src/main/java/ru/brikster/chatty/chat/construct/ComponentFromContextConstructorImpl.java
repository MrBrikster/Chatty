package ru.brikster.chatty.chat.construct;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import ru.brikster.chatty.api.chat.message.context.MessageContext;
import ru.brikster.chatty.convert.component.ComponentStringConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class ComponentFromContextConstructorImpl implements ComponentFromContextConstructor {

    @Inject
    private ComponentStringConverter componentStringConverter;

    private static final String PLAYER_FORMAT_PLACEHOLDER = "{player}";
    private static final String MESSAGE_FORMAT_PLACEHOLDER = "{message}";

    @Override
    public Component construct(MessageContext<Component> context) {
        String messageWithMmFormat = componentStringConverter.componentToString(context.getMessage());
        String formattedMessage = context.getMessageFormat()
                .replace("{original-message}", messageWithMmFormat);
        Component formattedMessageComponent = componentStringConverter.stringToComponent(formattedMessage);

        return context.getFormat()
                .replaceText(TextReplacementConfig.builder()
                        .matchLiteral(PLAYER_FORMAT_PLACEHOLDER)
                        .replacement(context.getSender().getDisplayName())
                        .build())
                .replaceText(TextReplacementConfig.builder()
                        .matchLiteral(MESSAGE_FORMAT_PLACEHOLDER)
                        .replacement(formattedMessageComponent)
                        .build());
    }

}
