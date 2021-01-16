package ghana7.minecraftccg.game;

import ghana7.minecraftccg.MinecraftCCG;
import ghana7.minecraftccg.cards.TradingCard;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class CardTableTileEntity extends TileEntity {
    private ItemStackHandler itemHandler = createHandler();

    public CardTableTileEntity() {
        super(MinecraftCCG.CARD_TABLE_TE.get());
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() instanceof TradingCard;
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

    public ItemStack extractHeldCard() {
        return itemHandler.extractItem(0, 1, false);
    }

    public void insertHeldCard(ItemStack stack) {
        itemHandler.insertItem(0, stack, false);
    }

    public ItemStack getHeldCard() {
        return itemHandler.getStackInSlot(0);
    }

    public boolean hasHeldCard() {
        if(getHeldCard() == null || getHeldCard().isEmpty()) {
            return false;
        }
        return true;
    }

    public void read(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        super.read(state, nbt);
    }

    public CompoundNBT write(CompoundNBT nbt) {
        nbt.put("inv", itemHandler.serializeNBT());
        return super.write(nbt);
    }


    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT nbt) {
        super.handleUpdateTag(state, nbt);
        read(state, nbt);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }


}
