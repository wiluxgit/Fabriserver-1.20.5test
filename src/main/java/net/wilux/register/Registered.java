package net.wilux.register;

import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.wilux.objects.Crate;
import net.wilux.objects.WateringCan;
import net.wilux.objects.WireSpool;
import net.wilux.objects.base.block.FactoryBlock;
import net.wilux.objects.base.block.PolyHorizontalFacingBlock;
import net.wilux.objects.base.item.GuiItem;
import net.wilux.objects.base.item.PolyBlockItem;
import net.wilux.objects.XTerm;

import static net.wilux.PolyWorks.MOD_ID;
import static net.wilux.register.Registered.PolyRegisters.*;

public class Registered {
    public static final class WATERING_CAN {
        public static final Item ITEM = new WateringCan(new FabricItemSettings(), polymerModelData(Items.CLOCK, "item/watering_can"));
    }

    public static final class XTERM {
        public static final Block BLOCK = new XTerm.XTermBlock(FabricBlockSettings.copyOf(Blocks.NOTE_BLOCK),
                polymerBlockData(BlockModelType.FULL_BLOCK, 0, 0, "block/xterm"),
                polymerBlockData(BlockModelType.FULL_BLOCK, 0, 90, "block/xterm"),
                polymerBlockData(BlockModelType.FULL_BLOCK, 0, 180,  "block/xterm"),
                polymerBlockData(BlockModelType.FULL_BLOCK, 0, 270,"block/xterm")
        );
        public static final BlockItem ITEM = new PolyBlockItem(BLOCK, new FabricItemSettings(), polymerModelData(Items.BARRIER, "item/xterm"));
    }

    public static final class MACHINE_COAL_GENERATOR {
        public static final Block BLOCK = polyFactory(FabricBlockSettings.copyOf(Blocks.NOTE_BLOCK), BlockModelType.FULL_BLOCK, "block/machine/coal_generator");
        public static final BlockItem ITEM = new PolyBlockItem(BLOCK, new FabricItemSettings(), polymerModelData(Items.BARRIER, "item/machine/coal_generator"));
    }

    public static final class MACHINE_ELECTRIC_FURNACE {
        public static final Block BLOCK = polyFactory(FabricBlockSettings.copyOf(Blocks.NOTE_BLOCK), BlockModelType.FULL_BLOCK, "block/machine/electric_furnace");
        public static final BlockItem ITEM = new PolyBlockItem(BLOCK, new FabricItemSettings(), polymerModelData(Items.BARRIER, "item/machine/electric_furnace"));
    }
    public static final class MACHINE_GRINDER {
        public static final Block BLOCK = polyFactory(FabricBlockSettings.copyOf(Blocks.NOTE_BLOCK), BlockModelType.FULL_BLOCK, "block/machine/grinder");
        public static final BlockItem ITEM = new PolyBlockItem(BLOCK, new FabricItemSettings(), polymerModelData(Items.BARRIER, "item/machine/grinder"));
    }

    public static final class MACHINE_PLATER {
        public static final Block BLOCK = polyFactory(FabricBlockSettings.copyOf(Blocks.NOTE_BLOCK), BlockModelType.FULL_BLOCK, "block/machine/plater");
        public static final BlockItem ITEM = new PolyBlockItem(BLOCK, new FabricItemSettings(), polymerModelData(Items.BARRIER, "item/machine/plater"));
    }

    public static final class CRATE {
        public static final Block BLOCK = new Crate.CrateBlock(FabricBlockSettings.copyOf(Blocks.NOTE_BLOCK),
                polymerBlockData(BlockModelType.FULL_BLOCK, 0, 0, "block/crate"),
                polymerBlockData(BlockModelType.FULL_BLOCK, 0, 90, "block/crate"),
                polymerBlockData(BlockModelType.FULL_BLOCK, 0, 180,  "block/crate"),
                polymerBlockData(BlockModelType.FULL_BLOCK, 0, 270,"block/crate")
        );
        public static final BlockItem ITEM = new PolyBlockItem(BLOCK, new FabricItemSettings(), polymerModelData(Items.BARRIER, "item/crate"));
        public static final BlockEntityType<Crate.CrateBlockEntity> BLOCK_ENTITY_TYPE = FabricBlockEntityTypeBuilder.create(Crate.CrateBlockEntity::new, BLOCK).build();
    }

    public static final class WIRE_SPOOL {
        public static final Item ITEM = new WireSpool(new FabricItemSettings(), polymerModelData(Items.STICK, "item/todo"));
    }

    public static final class GUI_ITEMS {
        public static final GuiItem XTERM_L = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/xterm_left"));
        public static final GuiItem XTERM_R = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/xterm_right"));
        public static final GuiItem XTERM_EMPTY = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/xterm_empty"));

        public static final GuiItem XTERM_DIGIT_1XXS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_1xxs"));
        public static final GuiItem XTERM_DIGIT_10XS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_10xs"));
        public static final GuiItem XTERM_DIGIT_100S = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_100s"));
        public static final GuiItem XTERM_DIGIT_1XXK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_1xxk"));
        public static final GuiItem XTERM_DIGIT_10XK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_10xk"));
        public static final GuiItem XTERM_DIGIT_100K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_100k"));
        public static final GuiItem XTERM_DIGIT_1XXM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_1xxm"));
        public static final GuiItem XTERM_DIGIT_10XM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_10xm"));
        public static final GuiItem XTERM_DIGIT_100M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_100m"));
        public static final GuiItem XTERM_DIGIT_2XXS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_2xxs"));
        public static final GuiItem XTERM_DIGIT_20XS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_20xs"));
        public static final GuiItem XTERM_DIGIT_200S = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_200s"));
        public static final GuiItem XTERM_DIGIT_2XXK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_2xxk"));
        public static final GuiItem XTERM_DIGIT_20XK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_20xk"));
        public static final GuiItem XTERM_DIGIT_200K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_200k"));
        public static final GuiItem XTERM_DIGIT_2XXM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_2xxm"));
        public static final GuiItem XTERM_DIGIT_20XM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_20xm"));
        public static final GuiItem XTERM_DIGIT_200M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_200m"));
        public static final GuiItem XTERM_DIGIT_3XXS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_3xxs"));
        public static final GuiItem XTERM_DIGIT_30XS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_30xs"));
        public static final GuiItem XTERM_DIGIT_300S = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_300s"));
        public static final GuiItem XTERM_DIGIT_3XXK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_3xxk"));
        public static final GuiItem XTERM_DIGIT_30XK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_30xk"));
        public static final GuiItem XTERM_DIGIT_300K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_300k"));
        public static final GuiItem XTERM_DIGIT_3XXM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_3xxm"));
        public static final GuiItem XTERM_DIGIT_30XM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_30xm"));
        public static final GuiItem XTERM_DIGIT_300M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_300m"));
        public static final GuiItem XTERM_DIGIT_4XXS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_4xxs"));
        public static final GuiItem XTERM_DIGIT_40XS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_40xs"));
        public static final GuiItem XTERM_DIGIT_400S = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_400s"));
        public static final GuiItem XTERM_DIGIT_4XXK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_4xxk"));
        public static final GuiItem XTERM_DIGIT_40XK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_40xk"));
        public static final GuiItem XTERM_DIGIT_400K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_400k"));
        public static final GuiItem XTERM_DIGIT_4XXM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_4xxm"));
        public static final GuiItem XTERM_DIGIT_40XM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_40xm"));
        public static final GuiItem XTERM_DIGIT_400M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_400m"));
        public static final GuiItem XTERM_DIGIT_5XXS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_5xxs"));
        public static final GuiItem XTERM_DIGIT_50XS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_50xs"));
        public static final GuiItem XTERM_DIGIT_500S = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_500s"));
        public static final GuiItem XTERM_DIGIT_5XXK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_5xxk"));
        public static final GuiItem XTERM_DIGIT_50XK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_50xk"));
        public static final GuiItem XTERM_DIGIT_500K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_500k"));
        public static final GuiItem XTERM_DIGIT_5XXM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_5xxm"));
        public static final GuiItem XTERM_DIGIT_50XM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_50xm"));
        public static final GuiItem XTERM_DIGIT_500M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_500m"));
        public static final GuiItem XTERM_DIGIT_6XXS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_6xxs"));
        public static final GuiItem XTERM_DIGIT_60XS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_60xs"));
        public static final GuiItem XTERM_DIGIT_600S = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_600s"));
        public static final GuiItem XTERM_DIGIT_6XXK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_6xxk"));
        public static final GuiItem XTERM_DIGIT_60XK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_60xk"));
        public static final GuiItem XTERM_DIGIT_600K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_600k"));
        public static final GuiItem XTERM_DIGIT_6XXM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_6xxm"));
        public static final GuiItem XTERM_DIGIT_60XM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_60xm"));
        public static final GuiItem XTERM_DIGIT_600M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_600m"));
        public static final GuiItem XTERM_DIGIT_7XXS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_7xxs"));
        public static final GuiItem XTERM_DIGIT_70XS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_70xs"));
        public static final GuiItem XTERM_DIGIT_700S = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_700s"));
        public static final GuiItem XTERM_DIGIT_7XXK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_7xxk"));
        public static final GuiItem XTERM_DIGIT_70XK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_70xk"));
        public static final GuiItem XTERM_DIGIT_700K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_700k"));
        public static final GuiItem XTERM_DIGIT_7XXM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_7xxm"));
        public static final GuiItem XTERM_DIGIT_70XM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_70xm"));
        public static final GuiItem XTERM_DIGIT_700M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_700m"));
        public static final GuiItem XTERM_DIGIT_8XXS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_8xxs"));
        public static final GuiItem XTERM_DIGIT_80XS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_80xs"));
        public static final GuiItem XTERM_DIGIT_800S = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_800s"));
        public static final GuiItem XTERM_DIGIT_8XXK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_8xxk"));
        public static final GuiItem XTERM_DIGIT_80XK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_80xk"));
        public static final GuiItem XTERM_DIGIT_800K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_800k"));
        public static final GuiItem XTERM_DIGIT_8XXM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_8xxm"));
        public static final GuiItem XTERM_DIGIT_80XM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_80xm"));
        public static final GuiItem XTERM_DIGIT_800M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_800m"));
        public static final GuiItem XTERM_DIGIT_9XXS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_9xxs"));
        public static final GuiItem XTERM_DIGIT_90XS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_90xs"));
        public static final GuiItem XTERM_DIGIT_900S = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_900s"));
        public static final GuiItem XTERM_DIGIT_9XXK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_9xxk"));
        public static final GuiItem XTERM_DIGIT_90XK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_90xk"));
        public static final GuiItem XTERM_DIGIT_900K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_900k"));
        public static final GuiItem XTERM_DIGIT_9XXM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_9xxm"));
        public static final GuiItem XTERM_DIGIT_90XM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_90xm"));
        public static final GuiItem XTERM_DIGIT_900M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_900m"));;
        public static final GuiItem XTERM_DIGIT_SPECIAL_K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_special_k"));
        public static final GuiItem XTERM_DIGIT_SPECIAL_M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_special_m"));
        public static final GuiItem XTERM_DIGIT_SPECIAL_1K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_special_1k"));
        public static final GuiItem XTERM_DIGIT_SPECIAL_1M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_special_1m"));
    }

    protected static final class PolyRegisters {
        static PolymerModelData polymerModelData(Item itemType, String assetPath) {
            return PolymerResourcePackUtils.requestModel(itemType, new Identifier(MOD_ID, assetPath));
        }
        static BlockState polymerBlockData(BlockModelType blockModelType, String assetPath){
            return polymerBlockData(blockModelType, 0, 0, assetPath);
        }
        static BlockState polymerBlockData(BlockModelType blockModelType, int x, int y, String assetPath){
            BlockState bs = PolymerBlockResourceUtils.requestBlock(blockModelType, PolymerBlockModel.of(new Identifier(MOD_ID, assetPath), x ,y));
            if (bs == null) throw new RuntimeException("Could not register model, polymer is out of ids, this mod can not work in that case");
            return bs;
        }
        static PolyHorizontalFacingBlock polyHorizontalFacingBlock(FabricBlockSettings settings, BlockModelType blockModelType, String assetPath) {
            return new PolyHorizontalFacingBlock(settings,
                    polymerBlockData(blockModelType, 0, 0, assetPath),
                    polymerBlockData(blockModelType, 0, 90, assetPath),
                    polymerBlockData(blockModelType, 0, 180,  assetPath),
                    polymerBlockData(blockModelType, 0, 270,assetPath)
            );
        }
        static FactoryBlock polyFactory(FabricBlockSettings settings, BlockModelType blockModelType, String assetPath) {
            return new FactoryBlock(settings,
                    polymerBlockData(blockModelType, 0, 0, assetPath),
                    polymerBlockData(blockModelType, 0, 90, assetPath),
                    polymerBlockData(blockModelType, 0, 180,  assetPath),
                    polymerBlockData(blockModelType, 0, 270,assetPath)
            );
        }
    }
}
