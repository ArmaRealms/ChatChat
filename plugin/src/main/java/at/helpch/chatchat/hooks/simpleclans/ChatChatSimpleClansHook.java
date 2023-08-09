package at.helpch.chatchat.hooks.simpleclans;

import at.helpch.chatchat.ChatChatPlugin;
import at.helpch.chatchat.hooks.AbstractInternalHook;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class ChatChatSimpleClansHook extends AbstractInternalHook {

    private static final String SIMPLECLANS = "SimpleClans";

    private final ChatChatPlugin plugin;

    public ChatChatSimpleClansHook(@NotNull final ChatChatPlugin plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public boolean register() {
        return plugin.configManager().extensions().addons().simpleClansChannels() &&
            Bukkit.getPluginManager().isPluginEnabled(SIMPLECLANS);
    }

    @Override
    public @NotNull String name() {
        return SIMPLECLANS + "Hook";
    }

    @Override
    public void enable() {
        plugin.channelTypeRegistry().add("SIMPLECLANS_CLAN", SimpleClansClanChannel::new);
        plugin.channelTypeRegistry().add("SIMPLECLANS_ALLY", SimpleClansAllyChannel::new);
    }
}
