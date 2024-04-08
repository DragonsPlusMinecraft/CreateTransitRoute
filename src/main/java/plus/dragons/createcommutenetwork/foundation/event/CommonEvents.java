package plus.dragons.createcommutenetwork.foundation.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import plus.dragons.createcommutenetwork.CommuteNetwork;

@Mod.EventBusSubscriber
public class CommonEvents {
    @SubscribeEvent
    public static void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        CommuteNetwork.COMMUTE_NETWORK_MANAGER.onPlayerLogin(player);
    }

    @SubscribeEvent
    public static void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        CommuteNetwork.COMMUTE_NETWORK_MANAGER.onPlayerLogout(player);
    }

    @SubscribeEvent
    public static void onLoadWorld(LevelEvent.Load event) {
        LevelAccessor world = event.getLevel();
        CommuteNetwork.COMMUTE_NETWORK_MANAGER.onWorldLoad(world);
    }
}
