package at.helpch.chatchat.hooks.simpleclans;

import at.helpch.chatchat.ChatChatPlugin;
import at.helpch.chatchat.api.holder.FormatsHolder;
import at.helpch.chatchat.api.user.ChatUser;
import at.helpch.chatchat.api.user.User;
import at.helpch.chatchat.channel.AbstractChannel;
import at.helpch.chatchat.util.ChannelUtils;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class AbstractSimpleClansChannel extends AbstractChannel {

    protected AbstractSimpleClansChannel(@NotNull final String name,
                                         @NotNull final String messagePrefix,
                                         @NotNull final List<String> toggleCommands,
                                         @NotNull final String channelPrefix,
                                         @NotNull final FormatsHolder formats,
                                         final int radius) {
        super(name, messagePrefix, toggleCommands, channelPrefix, formats, radius);
        if (Bukkit.getPluginManager().getPlugin("SimpleClans") == null) {
            throw new RuntimeException("Attempting to use a Clans channel but SimpleClans is not installed.");
        }
    }

    private @NotNull Optional<ClanPlayer> clanPlayerList(@NotNull final UUID uuid) {
        var clanManager = SimpleClans.getInstance().getClanManager();
        return Objects.requireNonNull(clanManager.getClanByPlayerUniqueId(uuid)).getOnlineMembers().stream()
            .filter(clanPlayer -> clanPlayer.getUniqueId().equals(uuid))
            .findFirst();
    }

    @Override
    public boolean isUsableBy(@NotNull final ChatUser user) {
        return super.isUsableBy(user) && clanPlayerList(user.uuid()).isPresent();
    }

    protected abstract @Nullable Set<ClanPlayer> clanPlayerList(@NotNull final ClanPlayer clanPlayer);

    private final ChatChatPlugin plugin = JavaPlugin.getPlugin(ChatChatPlugin.class);

    @Override
    public Set<User> targets(final @NotNull User source) {
        return clanPlayerList(source.uuid())
            .map(this::clanPlayerList).stream()
            .filter(Objects::nonNull)
            .flatMap(Set::stream)
            .map(ClanPlayer::getUniqueId)
            .map(plugin.usersHolder()::getUser)
            .filter(User::chatEnabled)
            .filter(target -> ChannelUtils.isTargetWithinRadius(source, target, radius()))
            .collect(Collectors.toSet());
    }

}
