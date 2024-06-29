package ru.brikster.chatty.util;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

@UtilityClass
public class PaperUtil {

    private static Boolean IS_PAPER;

    public boolean isPaper() {
        if (IS_PAPER != null) return IS_PAPER;
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            IS_PAPER = true;
        } catch (Throwable t) {
            IS_PAPER = false;
        }
        return IS_PAPER;
    }

    public boolean isSupportAdventure() {
        try {
            //noinspection JavaReflectionMemberAccess
            Player.class.getMethod("sendMessage", Class.forName("net.kyori.adventure.text.Component"));
            return true;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return false;
        }
    }

}
