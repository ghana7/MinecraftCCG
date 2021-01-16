package ghana7.minecraftccg.deck;

import ghana7.minecraftccg.MinecraftCCG;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;


public class DeckContainer extends Container {
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;
    private ItemStack deckItemStack;
    private IItemHandler deckItemHandler;
    public DeckContainer(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        super(MinecraftCCG.DECK_CONTAINER.get(), windowId);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.deckItemStack = getHeldDeck(player);
        this.deckItemHandler = ((Deck)deckItemStack.getItem()).getInventory(deckItemStack);

        addSlotBox(deckItemHandler, 0, 8, 18, 9, 18, 6, 18);
        layoutPlayerInventorySlots(8, 140);
    }

    private static ItemStack getHeldDeck(PlayerEntity player) {
        if(player.getHeldItemMainhand().getItem() instanceof Deck) {
            return player.getHeldItemMainhand();
        } else if(player.getHeldItemOffhand().getItem() instanceof Deck) {
            return player.getHeldItemOffhand();
        }
        return ItemStack.EMPTY;
    }
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    //helper methods for adding slots
    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for(int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for(int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }
    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        //Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        //Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
}
