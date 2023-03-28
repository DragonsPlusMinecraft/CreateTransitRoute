package plus.dragons.createdragontransit;

import com.simibubi.create.CreateClient;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import plus.dragons.createdragontransit.content.logistics.transit.DTManager;

public class DragonTransitClient {

    public static DTManager TRANSIT_MANAGER = new DTManager();

    public DragonTransitClient() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        forgeEventBus.addListener(DTManager::onClientPlayerLeave);
    }

    @SubscribeEvent
    public static void onJoin(ClientPlayerNetworkEvent.LoggingIn event) {
        CreateClient.checkGraphicsFanciness();
    }
}
