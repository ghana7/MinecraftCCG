package ghana7.minecraftccg.cards;

import ghana7.minecraftccg.MinecraftCCG;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class CowCard extends TradingCard{
    public CowCard() {
        super();
    }

    @Override
    public void onGameActivate(ItemStack stackIn, PlayerEntity playerIn) {
        MinecraftCCG.LOGGER.debug("cow card activated");
    }
}
