package ghana7.minecraftccg;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public class ModItemGroup extends ItemGroup {
    private Supplier<ItemStack> displayStack;

    public static final ModItemGroup TRADING_CARDS = new ModItemGroup("trading_cards", () -> new ItemStack(MinecraftCCG.BLANK_CARD.get()));

    private ModItemGroup(String label, Supplier<ItemStack> displayStack) {
        super(label);
        this.displayStack = displayStack;
    }

    @Override
    public ItemStack createIcon() {return displayStack.get();}
}
