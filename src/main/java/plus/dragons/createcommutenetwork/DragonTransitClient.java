package plus.dragons.createcommutenetwork;

import com.simibubi.create.CreateClient;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import plus.dragons.createcommutenetwork.content.network.CommuteNetworkManager;

public class DragonTransitClient {

    public static CommuteNetworkManager COMMUTE_NETWORK_MANAGER = new CommuteNetworkManager();

    public DragonTransitClient() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        forgeEventBus.addListener(CommuteNetworkManager::onClientPlayerLeave);
    }

    @SubscribeEvent
    public static void onJoin(ClientPlayerNetworkEvent.LoggingIn event) {
        CreateClient.checkGraphicsFanciness();
    }
}
