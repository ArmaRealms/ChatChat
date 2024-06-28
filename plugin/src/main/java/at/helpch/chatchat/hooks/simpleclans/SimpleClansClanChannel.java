package at.helpch.chatchat.hooks.simpleclans;

import at.helpch.chatchat.api.holder.FormatsHolder;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.Rank;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        return clan.getOnlineMembers().stream()
            .filter(cp -> cp.toPlayer() != null)
            .filter(cp -> hasClanRankPermission(clan, cp))
            .collect(Collectors.toSet());
    }

    private boolean hasClanRankPermission(@NotNull final Clan clan, @NotNull final ClanPlayer clanPlayer) {
        if (clanPlayer.hasRank()) {
            final Rank rank = clan.getRank(clanPlayer.getRankId());
            return rank.getPermissions().contains("clan.chat");
        }
        return clanPlayer.isTrusted() || clanPlayer.isLeader();
    }

}
