package net.minecraft.item;

import net.canarymod.api.entity.living.humanoid.CanaryPlayer;
import net.canarymod.api.world.blocks.CanaryBlock;
import net.canarymod.hook.player.BlockPlaceHook;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSlab extends ItemBlock {

    private final BlockSlab b;
    private final BlockSlab c;

    public ItemSlab(Block block, BlockSlab blockslab, BlockSlab blockslab1) {
        super(block);
        this.b = blockslab;
        this.c = blockslab1;
        this.d(0);
        this.a(true);
    }

    public int a(int i0) {
        return i0;
    }

    public String e_(ItemStack itemstack) {
        return this.b.b(itemstack.i());
    }

    public boolean a(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos, EnumFacing enumfacing, float f0, float f1, float f2) {
        // CanaryMod: BlockPlaceHook
        CanaryBlock clicked = new CanaryBlock(world.p(blockpos), blockpos, world); // Store Clicked
        clicked.setFaceClicked(enumfacing.asBlockFace()); // Set face clicked
        //

        if (itemstack.b == 0) {
            return false;
        }
        else if (!entityplayer.a(blockpos.a(enumfacing), enumfacing, itemstack)) {
            return false;
        }
        else {
            Object object = this.b.a(itemstack);
            IBlockState iblockstate = world.p(blockpos);

            if (iblockstate.c() == this.b) {
                IProperty iproperty = this.b.l();
                Comparable comparable = iblockstate.b(iproperty);
                BlockSlab.EnumBlockHalf blockslab_enumblockhalf = (BlockSlab.EnumBlockHalf)iblockstate.b(BlockSlab.a);

                if ((enumfacing == EnumFacing.UP && blockslab_enumblockhalf == BlockSlab.EnumBlockHalf.BOTTOM || enumfacing == EnumFacing.DOWN && blockslab_enumblockhalf == BlockSlab.EnumBlockHalf.TOP) && comparable == object) {
                    IBlockState iblockstate1 = this.c.P().a(iproperty, comparable);

                    // CanaryMod: BlockPlaceHook
                    if (new BlockPlaceHook(((EntityPlayerMP) entityplayer).getPlayer(), clicked, new CanaryBlock(iblockstate1, blockpos.a(enumfacing), world)).call().isCanceled()) {
                        return false;
                    }
                    //

                    if (world.b(this.c.a(world, blockpos, iblockstate1)) && world.a(blockpos, iblockstate1, 3)) {
                        world.a((double)((float)blockpos.n() + 0.5F), (double)((float)blockpos.o() + 0.5F), (double)((float)blockpos.p() + 0.5F), this.c.H.b(), (this.c.H.d() + 1.0F) / 2.0F, this.c.H.e() * 0.8F);
                        --itemstack.b;
                    }

                    return true;
                }
            }
            byte ret = this.a(itemstack, world, blockpos.a(enumfacing), object, ((EntityPlayerMP) entityplayer).getPlayer(), clicked);
            return ret == 1 ? true : ret == 0 ? super.a(itemstack, entityplayer, world, blockpos, enumfacing, f0, f1, f2) : false;
        }
    }

    // CanaryMod: add player/clicked to signature, change from boolean to byte 0=false, 1=true, 2=canceled
    private byte a(ItemStack itemstack, World world, BlockPos blockpos, Object object, CanaryPlayer player, CanaryBlock clicked) {
        IBlockState iblockstate = world.p(blockpos);

        if (iblockstate.c() == this.b) {
            Comparable comparable = iblockstate.b(this.b.l());

            if (comparable == object) {
                IBlockState iblockstate1 = this.c.P().a(this.b.l(), comparable);

                // Call hook
                if (new BlockPlaceHook(player, clicked, new CanaryBlock(iblockstate1, blockpos, world)).call().isCanceled()) {
                    return 2;
                }
                //

                if (world.b(this.c.a(world, blockpos, iblockstate1)) && world.a(blockpos, iblockstate1, 3)) {
                    world.a((double) ((float) blockpos.n() + 0.5F), (double) ((float) blockpos.o() + 0.5F), (double) ((float) blockpos.p() + 0.5F), this.c.H.b(), (this.c.H.d() + 1.0F) / 2.0F, this.c.H.e() * 0.8F);
                    --itemstack.b;
                }

                return 1;
            }
        }

        return 0;
    }
}
