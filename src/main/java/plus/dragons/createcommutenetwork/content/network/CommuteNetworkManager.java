package plus.dragons.createcommutenetwork.content.network;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.fml.DistExecutor;
import org.apache.commons.lang3.mutable.MutableObject;
import plus.dragons.createcommutenetwork.CommuteNetwork;
import plus.dragons.createcommutenetwork.CommuteNetworkClient;

public class CommuteNetworkManager {
    public plus.dragons.createcommutenetwork.content.network.CommuteNetwork network = new plus.dragons.createcommutenetwork.content.network.CommuteNetwork();
    CommuteNetworkSavedData savedData;

    public CommuteNetworkManager() {
        cleanUp();
    }

    @SuppressWarnings("ConstantConditions")
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        var manager = CommuteNetwork.COMMUTE_NETWORK_MANAGER;
        if (player instanceof ServerPlayer serverPlayer) {
            manager.loadRouteData(serverPlayer.getServer());
            //DTNetworkSyncPacket.Initialize.of(manager.network)
            //        .forEach(packet -> CcnPackets.channel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), packet));
        }
    }

    public static void onClientPlayerLeave(ClientPlayerNetworkEvent.LoggingOut event) {
        CommuteNetworkClient.COMMUTE_NETWORK_MANAGER.cleanUp();
    }

    public static void onLoadWorld(LevelEvent.Load event) {
        LevelAccessor level = event.getLevel();
        var manager = CommuteNetwork.COMMUTE_NETWORK_MANAGER;
        MinecraftServer server = level.getServer();
        if (server == null || server.overworld() != level)
            return;
        manager.cleanUp();
        manager.savedData = null;
        manager.loadRouteData(server);
    }

    private void loadRouteData(MinecraftServer server) {
        if (savedData != null)
            return;
        this.savedData = CommuteNetworkSavedData.load(server);
        this.network = savedData.network;
    }

    public void cleanUp() {
        this.network = new plus.dragons.createcommutenetwork.content.network.CommuteNetwork();
    }

    public void markDirty() {
        if (this.savedData != null)
            this.savedData.setDirty();
    }

    public CommuteNetworkManager sided(LevelAccessor level) {
        if (level != null && !level.isClientSide())
            return this;
        MutableObject<CommuteNetworkManager> m = new MutableObject<>();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> clientManager(m));
        return m.getValue();
    }

    @OnlyIn(Dist.CLIENT)
    private void clientManager(MutableObject<CommuteNetworkManager> m) {
        m.setValue(CommuteNetworkClient.COMMUTE_NETWORK_MANAGER);
    }
}
