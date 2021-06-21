package com.infinityraider.agricraft.content;

import com.infinityraider.agricraft.api.v1.content.IAgriContent;
import com.infinityraider.agricraft.content.core.BlockCropPlant;
import com.infinityraider.agricraft.content.core.BlockCropSticks;
import com.infinityraider.agricraft.content.core.BlockSeedAnalyzer;
import com.infinityraider.agricraft.content.core.CropStickVariant;
import com.infinityraider.agricraft.content.decoration.BlockGrate;
import com.infinityraider.agricraft.content.irrigation.BlockIrrigationChannelHollow;
import com.infinityraider.agricraft.content.irrigation.BlockIrrigationChannelNormal;
import com.infinityraider.agricraft.content.irrigation.BlockIrrigationTank;
import com.infinityraider.agricraft.content.irrigation.BlockSprinkler;
import com.infinityraider.agricraft.content.world.BlockGreenHouseAir;
import net.minecraft.block.Block;

public class AgriBlockRegistry implements IAgriContent.Blocks {

    private static final AgriBlockRegistry INSTANCE = new AgriBlockRegistry();

    public static AgriBlockRegistry getInstance() {
        return INSTANCE;
    }

    // crop plant
    public final BlockCropPlant crop_plant;

    // crop sticks
    public final BlockCropSticks crop_sticks_wood;
    public final BlockCropSticks crop_sticks_iron;
    public final BlockCropSticks crop_sticks_obsidian;

    // analyzer
    public final BlockSeedAnalyzer seed_analyzer;

    // irrigation
    public final BlockIrrigationTank tank;
    public final BlockIrrigationChannelNormal channel;
    public final BlockIrrigationChannelHollow channel_hollow;
    public final BlockSprinkler sprinkler;

    // Storage
    //public final BlockBase seed_storage;

    // Decoration
    public final BlockGrate grate;

    // World
    public final BlockGreenHouseAir greenhouse_air;

    private AgriBlockRegistry() {
        this.crop_plant = new BlockCropPlant();
        this.crop_sticks_wood = new BlockCropSticks(CropStickVariant.WOOD);
        this.crop_sticks_iron = new BlockCropSticks(CropStickVariant.IRON);
        this.crop_sticks_obsidian = new BlockCropSticks(CropStickVariant.OBSIDIAN);

        this.seed_analyzer = new BlockSeedAnalyzer();

        this.tank = new BlockIrrigationTank();
        this.channel = new BlockIrrigationChannelNormal();
        this.channel_hollow = new BlockIrrigationChannelHollow();
        this.sprinkler = new BlockSprinkler();

        //this.seed_storage = new BlockSeedStorage();

        this.grate = new BlockGrate();

        this.greenhouse_air = new BlockGreenHouseAir();
    }

    @Override
    public Block getCropPlantBlock() {
        return this.crop_plant;
    }

    @Override
    public Block getWoodCropSticksBlock() {
        return this.crop_sticks_wood;
    }

    @Override
    public Block getIronCropSticksBlock() {
        return this.crop_sticks_iron;
    }

    @Override
    public Block getObsidianCropSticksBlock() {
        return this.crop_sticks_obsidian;
    }

    @Override
    public Block getSeedAnalyzerBlock() {
        return this.seed_analyzer;
    }

    @Override
    public Block getTankBlock() {
        return this.tank;
    }

    @Override
    public Block getChannelBlock() {
        return this.channel;
    }

    @Override
    public Block getHollowChannelBlock() {
        return this.channel_hollow;
    }

    @Override
    public Block getSprinklerBlock() {
        return this.sprinkler;
    }

    @Override
    public Block getGrateBlock() {
        return this.grate;
    }

    @Override
    public Block getGreenHouseAirBlock() {
        return this.greenhouse_air;
    }
}
