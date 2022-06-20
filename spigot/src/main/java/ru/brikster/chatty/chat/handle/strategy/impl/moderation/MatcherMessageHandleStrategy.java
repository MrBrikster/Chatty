package ru.brikster.chatty.chat.handle.strategy.impl.moderation;

import ru.brikster.chatty.api.chat.handle.context.MessageContext;
import ru.brikster.chatty.api.chat.handle.strategy.MessageHandleStrategy;
import ru.brikster.chatty.chat.handle.context.MessageContextImpl;
import ru.brikster.chatty.chat.handle.strategy.result.ResultImpl;

public interface MatcherMessageHandleStrategy extends MessageHandleStrategy<String, String> {

    default Result<String> getMatcherResult(MessageContext<String> context, String matchedMessage,
                                    boolean messageMatches,
                                    boolean useBlock) {
        if (messageMatches) {
            return ResultImpl.<String>builder()
                    .newContext(new MessageContextImpl<>(context))
                    .build();
        } else {
            if (useBlock) {
                MessageContext<String> newContext = new MessageContextImpl<>(context);
                newContext.setMessage(matchedMessage);
                newContext.setCancelled(true);
                return ResultImpl.<String>builder()
                        .newContext(newContext)
                        .messageUpdated(true)
                        .becameCancelled(!context.isCancelled())
                        .build();
            } else {
                MessageContext<String> newContext = new MessageContextImpl<>(context);
                newContext.setMessage(matchedMessage);
                return ResultImpl.<String>builder()
                        .newContext(newContext)
                        .messageUpdated(true)
                        .build();
            }
        }
    }

}
