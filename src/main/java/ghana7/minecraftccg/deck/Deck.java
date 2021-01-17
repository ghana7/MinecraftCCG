package ghana7.minecraftccg.deck;

import ghana7.minecraftccg.MinecraftCCG;
import ghana7.minecraftccg.ModItemGroup;
import ghana7.minecraftccg.cards.TradingCard;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Deck extends Item {
    public IItemHandler getInventory(ItemStack stack) {
        ItemStackHandler stackHandler = createHandler();
        stackHandler.deserializeNBT(stack.getOrCreateTag().getCompound("Inventory"));
        return stackHandler;
    }
    public void saveInventory(ItemStack stack, IItemHandler itemHandler) {
        if(itemHandler instanceof ItemStackHandler) {
            stack.getOrCreateTag().put("Inventory", ((ItemStackHandler)itemHandler).serializeNBT());
        }
    }
    private ItemStackHandler createHandler() {
        return new ItemStackHandler(54) {
            @Override
            protected void onContentsChanged(int slot) {
                //markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                Item stackItem = stack.getItem();
                if(stackItem instanceof TradingCard) {
                    int numCopiesInDeck = 0;
                    int maxCopies = ((TradingCard) stackItem).getMaxCopiesInDeck();
                    if(maxCopies == -1) {
                        return true;
                    }
                    for(int i = 0; i < this.getSlots(); i++) {
                        if(this.getStackInSlot(i).getItem() == stackItem) {
                            numCopiesInDeck++;
                        }
                    }
                    return numCopiesInDeck < maxCopies;
                } else {
                    return false;
                }
            }

            @Override
            public int getSlotLimit(int slot)
            {
                return 1;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return super.insertItem(slot, stack, simulate);
            }

            @Nonnull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                return super.extractItem(slot, amount, simulate);
            }
        };
    }
    public Deck() {
        super(new Item.Properties().group(ModItemGroup.TRADING_CARDS));
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if(!worldIn.isRemote() && itemstack.getItem() instanceof Deck) {
            MinecraftCCG.LOGGER.debug("deck right clicked");
            INamedContainerProvider containerProvider = new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    return new TranslationTextComponent("screen.minecraftccg.deck");
                }

                @Nullable
                @Override
                public Container createMenu(int i, PlayerInventory playerInv, PlayerEntity player) {
                    return new DeckContainer(i, playerInv, player);
                }
            };

            NetworkHooks.openGui((ServerPlayerEntity)playerIn, containerProvider);
        }

        return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
    }
}
