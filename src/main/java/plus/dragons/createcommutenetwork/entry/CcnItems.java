package plus.dragons.createcommutenetwork.entry;

import com.tterrag.registrate.util.entry.ItemEntry;
import plus.dragons.createcommutenetwork.content.commute.train.stationPlatform.TestRode;

import static plus.dragons.createcommutenetwork.CommuteNetwork.REGISTRATE;

public class CcnItems {

    public static final ItemEntry<TestRode> TEST_RODE = REGISTRATE.item("test_rode", TestRode::new)
            .properties(prop -> prop.stacksTo(1))
            .register();

    public static void register() {
    }
}
