package ghana7.minecraftccg.cards;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.RegistryObject;

public class CardBuilder {
    public static ItemStack makeCard(RegistryObject<Item> cardType, int count, boolean isFoil) {
        ItemStack cardItemStack = new ItemStack(() -> cardType.get(), count);

        CompoundNBT nbt = cardItemStack.getOrCreateTag();
        nbt.putBoolean("foil", isFoil);

        return cardItemStack;
    }

    public static ItemStack makeSingleCopy(ItemStack stack) {
        ItemStack cardItemStack = stack.copy();
        cardItemStack.setCount(1);
        return cardItemStack;
    }
}
