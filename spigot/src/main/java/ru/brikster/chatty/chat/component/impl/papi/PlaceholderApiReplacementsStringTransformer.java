package ru.brikster.chatty.chat.component.impl.papi;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import ru.brikster.chatty.chat.component.impl.ReplacementsStringTransformer;

public final class PlaceholderApiReplacementsStringTransformer implements ReplacementsStringTransformer {

    @Override
    public String transform(@NotNull OfflinePlayer sender, @NotNull String message) {
        return PlaceholderAPI.setPlaceholders(sender, message);
    }

}
