package ghana7.minecraftccg.cardpacks;

import ghana7.minecraftccg.MinecraftCCG;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;

public class DebugCardPack extends CardPack{
    public DebugCardPack() {
        super();
        commonDrops = new ArrayList<RegistryObject<Item>>();
        uncommonDrops = new ArrayList<RegistryObject<Item>>();
        rareDrops = new ArrayList<RegistryObject<Item>>();
        legendaryDrops = new ArrayList<RegistryObject<Item>>();
        commonDrops.add(MinecraftCCG.COW_CARD);
        uncommonDrops.add(MinecraftCCG.VILLAGER_CARD);
        rareDrops.add(MinecraftCCG.FIRE_CARD);
        legendaryDrops.add(MinecraftCCG.BLANK_CARD);
        size = 10;
        foilChance = 0.1f;
    }
}
