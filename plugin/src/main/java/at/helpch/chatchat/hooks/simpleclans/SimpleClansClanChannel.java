package at.helpch.chatchat.hooks.simpleclans;

import at.helpch.chatchat.api.holder.FormatsHolder;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SimpleClansClanChannel extends AbstractSimpleClansChannel {

    public SimpleClansClanChannel(@NotNull final String name,
                                  @NotNull final String messagePrefix,
                                  @NotNull final List<String> toggleCommands,
                                  @NotNull final String channelPrefix,
                                  @NotNull final FormatsHolder formats,
                                  final int radius) {
        super(name, messagePrefix, toggleCommands, channelPrefix, formats, radius);
    }

    @Contract("_ -> new")
    @Override
    protected @NotNull Set<ClanPlayer> clanPlayerList(@NotNull final ClanPlayer clanPlayer) {
        final Clan clan = clanPlayer.getClan();
        if (clan == null) return Set.of();
        return new HashSet<>(clan.getOnlineMembers());
    }

}
