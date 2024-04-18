package net.wilux.register;

import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.wilux.objects.Crate;
import net.wilux.objects.base.block.FactoryBlock;
import net.wilux.objects.base.block.PolyHorizontalFacingBlock;

import static net.wilux.PolyWorks.MOD_ID;

public final class RegisterHelpers {
    public static void registerAll() {
        // Register block entities
        registerWithPolyBlockEntity(new Identifier(MOD_ID, "entity_crate"), Registered.CRATE.BLOCK_ENTITY_TYPE);

        // Register blocks and items
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "watering_can"), Registered.WATERING_CAN.ITEM);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "xterm"), Registered.XTERM.BLOCK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "xterm"), Registered.XTERM.ITEM);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "coal_generator"), Registered.MACHINE_COAL_GENERATOR.BLOCK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "coal_generator"), Registered.MACHINE_COAL_GENERATOR.ITEM);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "furnace"), Registered.MACHINE_ELECTRIC_FURNACE.BLOCK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "furnace"), Registered.MACHINE_ELECTRIC_FURNACE.ITEM);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "grinder"), Registered.MACHINE_GRINDER.BLOCK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "grinder"), Registered.MACHINE_GRINDER.ITEM);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "plater"), Registered.MACHINE_PLATER.BLOCK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "plater"), Registered.MACHINE_PLATER.ITEM);
        Registry.register(Registries.BLOCK, new Identifier(MOD_ID, "crate"), Registered.CRATE.BLOCK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "crate"), Registered.CRATE.ITEM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "wirespool"), Registered.WIRE_SPOOL.ITEM);

        // Register gui items
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_left"), Registered.GUI_ITEMS.XTERM_L);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_right"), Registered.GUI_ITEMS.XTERM_R);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_empty"), Registered.GUI_ITEMS.XTERM_EMPTY);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_1xxs"), Registered.GUI_ITEMS.XTERM_DIGIT_1XXS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_10xs"), Registered.GUI_ITEMS.XTERM_DIGIT_10XS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_100s"), Registered.GUI_ITEMS.XTERM_DIGIT_100S);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_1xxk"), Registered.GUI_ITEMS.XTERM_DIGIT_1XXK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_10xk"), Registered.GUI_ITEMS.XTERM_DIGIT_10XK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_100k"), Registered.GUI_ITEMS.XTERM_DIGIT_100K);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_1xxm"), Registered.GUI_ITEMS.XTERM_DIGIT_1XXM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_10xm"), Registered.GUI_ITEMS.XTERM_DIGIT_10XM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_100m"), Registered.GUI_ITEMS.XTERM_DIGIT_100M);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_2xxs"), Registered.GUI_ITEMS.XTERM_DIGIT_2XXS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_20xs"), Registered.GUI_ITEMS.XTERM_DIGIT_20XS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_200s"), Registered.GUI_ITEMS.XTERM_DIGIT_200S);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_2xxk"), Registered.GUI_ITEMS.XTERM_DIGIT_2XXK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_20xk"), Registered.GUI_ITEMS.XTERM_DIGIT_20XK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_200k"), Registered.GUI_ITEMS.XTERM_DIGIT_200K);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_2xxm"), Registered.GUI_ITEMS.XTERM_DIGIT_2XXM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_20xm"), Registered.GUI_ITEMS.XTERM_DIGIT_20XM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_200m"), Registered.GUI_ITEMS.XTERM_DIGIT_200M);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_3xxs"), Registered.GUI_ITEMS.XTERM_DIGIT_3XXS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_30xs"), Registered.GUI_ITEMS.XTERM_DIGIT_30XS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_300s"), Registered.GUI_ITEMS.XTERM_DIGIT_300S);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_3xxk"), Registered.GUI_ITEMS.XTERM_DIGIT_3XXK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_30xk"), Registered.GUI_ITEMS.XTERM_DIGIT_30XK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_300k"), Registered.GUI_ITEMS.XTERM_DIGIT_300K);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_3xxm"), Registered.GUI_ITEMS.XTERM_DIGIT_3XXM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_30xm"), Registered.GUI_ITEMS.XTERM_DIGIT_30XM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_300m"), Registered.GUI_ITEMS.XTERM_DIGIT_300M);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_4xxs"), Registered.GUI_ITEMS.XTERM_DIGIT_4XXS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_40xs"), Registered.GUI_ITEMS.XTERM_DIGIT_40XS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_400s"), Registered.GUI_ITEMS.XTERM_DIGIT_400S);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_4xxk"), Registered.GUI_ITEMS.XTERM_DIGIT_4XXK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_40xk"), Registered.GUI_ITEMS.XTERM_DIGIT_40XK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_400k"), Registered.GUI_ITEMS.XTERM_DIGIT_400K);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_4xxm"), Registered.GUI_ITEMS.XTERM_DIGIT_4XXM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_40xm"), Registered.GUI_ITEMS.XTERM_DIGIT_40XM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_400m"), Registered.GUI_ITEMS.XTERM_DIGIT_400M);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_5xxs"), Registered.GUI_ITEMS.XTERM_DIGIT_5XXS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_50xs"), Registered.GUI_ITEMS.XTERM_DIGIT_50XS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_500s"), Registered.GUI_ITEMS.XTERM_DIGIT_500S);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_5xxk"), Registered.GUI_ITEMS.XTERM_DIGIT_5XXK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_50xk"), Registered.GUI_ITEMS.XTERM_DIGIT_50XK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_500k"), Registered.GUI_ITEMS.XTERM_DIGIT_500K);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_5xxm"), Registered.GUI_ITEMS.XTERM_DIGIT_5XXM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_50xm"), Registered.GUI_ITEMS.XTERM_DIGIT_50XM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_500m"), Registered.GUI_ITEMS.XTERM_DIGIT_500M);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_6xxs"), Registered.GUI_ITEMS.XTERM_DIGIT_6XXS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_60xs"), Registered.GUI_ITEMS.XTERM_DIGIT_60XS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_600s"), Registered.GUI_ITEMS.XTERM_DIGIT_600S);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_6xxk"), Registered.GUI_ITEMS.XTERM_DIGIT_6XXK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_60xk"), Registered.GUI_ITEMS.XTERM_DIGIT_60XK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_600k"), Registered.GUI_ITEMS.XTERM_DIGIT_600K);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_6xxm"), Registered.GUI_ITEMS.XTERM_DIGIT_6XXM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_60xm"), Registered.GUI_ITEMS.XTERM_DIGIT_60XM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_600m"), Registered.GUI_ITEMS.XTERM_DIGIT_600M);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_7xxs"), Registered.GUI_ITEMS.XTERM_DIGIT_7XXS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_70xs"), Registered.GUI_ITEMS.XTERM_DIGIT_70XS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_700s"), Registered.GUI_ITEMS.XTERM_DIGIT_700S);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_7xxk"), Registered.GUI_ITEMS.XTERM_DIGIT_7XXK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_70xk"), Registered.GUI_ITEMS.XTERM_DIGIT_70XK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_700k"), Registered.GUI_ITEMS.XTERM_DIGIT_700K);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_7xxm"), Registered.GUI_ITEMS.XTERM_DIGIT_7XXM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_70xm"), Registered.GUI_ITEMS.XTERM_DIGIT_70XM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_700m"), Registered.GUI_ITEMS.XTERM_DIGIT_700M);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_8xxs"), Registered.GUI_ITEMS.XTERM_DIGIT_8XXS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_80xs"), Registered.GUI_ITEMS.XTERM_DIGIT_80XS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_800s"), Registered.GUI_ITEMS.XTERM_DIGIT_800S);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_8xxk"), Registered.GUI_ITEMS.XTERM_DIGIT_8XXK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_80xk"), Registered.GUI_ITEMS.XTERM_DIGIT_80XK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_800k"), Registered.GUI_ITEMS.XTERM_DIGIT_800K);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_8xxm"), Registered.GUI_ITEMS.XTERM_DIGIT_8XXM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_80xm"), Registered.GUI_ITEMS.XTERM_DIGIT_80XM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_800m"), Registered.GUI_ITEMS.XTERM_DIGIT_800M);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_9xxs"), Registered.GUI_ITEMS.XTERM_DIGIT_9XXS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_90xs"), Registered.GUI_ITEMS.XTERM_DIGIT_90XS);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_900s"), Registered.GUI_ITEMS.XTERM_DIGIT_900S);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_9xxk"), Registered.GUI_ITEMS.XTERM_DIGIT_9XXK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_90xk"), Registered.GUI_ITEMS.XTERM_DIGIT_90XK);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_900k"), Registered.GUI_ITEMS.XTERM_DIGIT_900K);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_9xxm"), Registered.GUI_ITEMS.XTERM_DIGIT_9XXM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_90xm"), Registered.GUI_ITEMS.XTERM_DIGIT_90XM);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_900m"), Registered.GUI_ITEMS.XTERM_DIGIT_900M);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_special_k"), Registered.GUI_ITEMS.XTERM_DIGIT_SPECIAL_K);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_special_m"), Registered.GUI_ITEMS.XTERM_DIGIT_SPECIAL_M);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_special_1k"), Registered.GUI_ITEMS.XTERM_DIGIT_SPECIAL_1K);
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "itemgui_xterm_digit_special_1m"), Registered.GUI_ITEMS.XTERM_DIGIT_SPECIAL_1M);
    }

    private static void registerWithPolyBlockEntity(Identifier id, BlockEntityType<? extends BlockEntity> entityType) {
        var x = Registry.register(Registries.BLOCK_ENTITY_TYPE, id, entityType);
        PolymerBlockUtils.registerBlockEntity(x);
    }
}
