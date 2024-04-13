package net.wilux;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.item.PolymerItemUtils;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
;
import java.util.Arrays;
import java.util.List;

import static net.wilux.PolyWorks.polymerModelData;

public class RegisterGuiItem {
    public final static class GuiItem extends Item implements PolymerItem {
        public final PolymerModelData polymerModelData;

        public GuiItem(Settings settings, PolymerModelData polymerModelData) {
            super(settings);
            this.polymerModelData = polymerModelData;
        }

        public ItemStack getStack() {
            return new ItemStack(this);
        }
        public ItemStack getDataOnlyStack() {
            Item clientsideItem = this.polymerModelData.item();
            ItemStack stack = new ItemStack(clientsideItem);
            NbtCompound tag = stack.getOrCreateNbt();
            tag.putInt("CustomModelData", this.polymerModelData.value());
            stack.setNbt(tag);
            return stack;
        }

        @Override
        public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
            return polymerModelData.item();
        }

        @Override
        public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipContext context, ServerPlayerEntity player) {
            return PolymerItemUtils.createItemStack(itemStack, context, player);
        }

        @Override
        public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
            return this.polymerModelData.value();
        }
    }

    public static final GuiItem ITEM_GUI_XTERM_L = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/xterm_left"));
    public static final GuiItem ITEM_GUI_XTERM_R = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/xterm_right"));
    public static final GuiItem ITEM_GUI_XTERM_EMPTY = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/xterm_empty"));

    public static final GuiItem ITEM_GUI_XTERM_DIGIT_1XXS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_1xxs"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_10XS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_10xs"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_100S = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_100s"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_1XXK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_1xxk"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_10XK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_10xk"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_100K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_100k"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_1XXM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_1xxm"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_10XM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_10xm"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_100M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_100m"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_2XXS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_2xxs"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_20XS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_20xs"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_200S = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_200s"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_2XXK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_2xxk"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_20XK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_20xk"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_200K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_200k"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_2XXM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_2xxm"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_20XM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_20xm"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_200M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_200m"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_3XXS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_3xxs"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_30XS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_30xs"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_300S = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_300s"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_3XXK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_3xxk"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_30XK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_30xk"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_300K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_300k"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_3XXM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_3xxm"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_30XM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_30xm"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_300M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_300m"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_4XXS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_4xxs"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_40XS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_40xs"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_400S = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_400s"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_4XXK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_4xxk"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_40XK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_40xk"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_400K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_400k"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_4XXM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_4xxm"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_40XM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_40xm"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_400M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_400m"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_5XXS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_5xxs"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_50XS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_50xs"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_500S = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_500s"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_5XXK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_5xxk"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_50XK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_50xk"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_500K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_500k"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_5XXM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_5xxm"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_50XM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_50xm"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_500M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_500m"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_6XXS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_6xxs"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_60XS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_60xs"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_600S = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_600s"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_6XXK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_6xxk"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_60XK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_60xk"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_600K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_600k"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_6XXM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_6xxm"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_60XM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_60xm"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_600M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_600m"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_7XXS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_7xxs"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_70XS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_70xs"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_700S = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_700s"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_7XXK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_7xxk"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_70XK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_70xk"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_700K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_700k"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_7XXM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_7xxm"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_70XM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_70xm"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_700M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_700m"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_8XXS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_8xxs"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_80XS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_80xs"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_800S = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_800s"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_8XXK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_8xxk"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_80XK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_80xk"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_800K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_800k"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_8XXM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_8xxm"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_80XM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_80xm"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_800M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_800m"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_9XXS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_9xxs"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_90XS = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_90xs"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_900S = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_900s"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_9XXK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_9xxk"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_90XK = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_90xk"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_900K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_900k"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_9XXM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_9xxm"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_90XM = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_90xm"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_900M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_900m"));;
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_SPECIAL_K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_special_k"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_SPECIAL_M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_special_m"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_SPECIAL_1K = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_special_1k"));
    public static final GuiItem ITEM_GUI_XTERM_DIGIT_SPECIAL_1M = new GuiItem(new FabricItemSettings(), polymerModelData(Items.CLOCK, "guiitem/digit/digit_special_1m"));

    public static final List<GuiItem> COLLECTION_ITEM_GUI_XTERM_DIGIT_NXX_S = Arrays.asList(null, ITEM_GUI_XTERM_DIGIT_1XXS, ITEM_GUI_XTERM_DIGIT_2XXS, ITEM_GUI_XTERM_DIGIT_3XXS, ITEM_GUI_XTERM_DIGIT_4XXS, ITEM_GUI_XTERM_DIGIT_5XXS, ITEM_GUI_XTERM_DIGIT_6XXS, ITEM_GUI_XTERM_DIGIT_7XXS, ITEM_GUI_XTERM_DIGIT_8XXS, ITEM_GUI_XTERM_DIGIT_9XXS);
    public static final List<GuiItem> COLLECTION_ITEM_GUI_XTERM_DIGIT_N0X_S = Arrays.asList(null, ITEM_GUI_XTERM_DIGIT_10XS, ITEM_GUI_XTERM_DIGIT_20XS, ITEM_GUI_XTERM_DIGIT_30XS, ITEM_GUI_XTERM_DIGIT_40XS, ITEM_GUI_XTERM_DIGIT_50XS, ITEM_GUI_XTERM_DIGIT_60XS, ITEM_GUI_XTERM_DIGIT_70XS, ITEM_GUI_XTERM_DIGIT_80XS, ITEM_GUI_XTERM_DIGIT_90XS);
    public static final List<GuiItem> COLLECTION_ITEM_GUI_XTERM_DIGIT_N00_S = Arrays.asList(null, ITEM_GUI_XTERM_DIGIT_100S, ITEM_GUI_XTERM_DIGIT_200S, ITEM_GUI_XTERM_DIGIT_300S, ITEM_GUI_XTERM_DIGIT_400S, ITEM_GUI_XTERM_DIGIT_500S, ITEM_GUI_XTERM_DIGIT_600S, ITEM_GUI_XTERM_DIGIT_700S, ITEM_GUI_XTERM_DIGIT_800S, ITEM_GUI_XTERM_DIGIT_900S);
    public static final List<GuiItem> COLLECTION_ITEM_GUI_XTERM_DIGIT_NXX_K = Arrays.asList(null, ITEM_GUI_XTERM_DIGIT_1XXK, ITEM_GUI_XTERM_DIGIT_2XXK, ITEM_GUI_XTERM_DIGIT_3XXK, ITEM_GUI_XTERM_DIGIT_4XXK, ITEM_GUI_XTERM_DIGIT_5XXK, ITEM_GUI_XTERM_DIGIT_6XXK, ITEM_GUI_XTERM_DIGIT_7XXK, ITEM_GUI_XTERM_DIGIT_8XXK, ITEM_GUI_XTERM_DIGIT_9XXK);
    public static final List<GuiItem> COLLECTION_ITEM_GUI_XTERM_DIGIT_N0X_K = Arrays.asList(null, ITEM_GUI_XTERM_DIGIT_10XK, ITEM_GUI_XTERM_DIGIT_20XK, ITEM_GUI_XTERM_DIGIT_30XK, ITEM_GUI_XTERM_DIGIT_40XK, ITEM_GUI_XTERM_DIGIT_50XK, ITEM_GUI_XTERM_DIGIT_60XK, ITEM_GUI_XTERM_DIGIT_70XK, ITEM_GUI_XTERM_DIGIT_80XK, ITEM_GUI_XTERM_DIGIT_90XK);
    public static final List<GuiItem> COLLECTION_ITEM_GUI_XTERM_DIGIT_N00_K = Arrays.asList(null, ITEM_GUI_XTERM_DIGIT_100K, ITEM_GUI_XTERM_DIGIT_200K, ITEM_GUI_XTERM_DIGIT_300K, ITEM_GUI_XTERM_DIGIT_400K, ITEM_GUI_XTERM_DIGIT_500K, ITEM_GUI_XTERM_DIGIT_600K, ITEM_GUI_XTERM_DIGIT_700K, ITEM_GUI_XTERM_DIGIT_800K, ITEM_GUI_XTERM_DIGIT_900K);
    public static final List<GuiItem> COLLECTION_ITEM_GUI_XTERM_DIGIT_NXX_M = Arrays.asList(null, ITEM_GUI_XTERM_DIGIT_1XXM, ITEM_GUI_XTERM_DIGIT_2XXM, ITEM_GUI_XTERM_DIGIT_3XXM, ITEM_GUI_XTERM_DIGIT_4XXM, ITEM_GUI_XTERM_DIGIT_5XXM, ITEM_GUI_XTERM_DIGIT_6XXM, ITEM_GUI_XTERM_DIGIT_7XXM, ITEM_GUI_XTERM_DIGIT_8XXM, ITEM_GUI_XTERM_DIGIT_9XXM);
    public static final List<GuiItem> COLLECTION_ITEM_GUI_XTERM_DIGIT_N0X_M = Arrays.asList(null, ITEM_GUI_XTERM_DIGIT_10XM, ITEM_GUI_XTERM_DIGIT_20XM, ITEM_GUI_XTERM_DIGIT_30XM, ITEM_GUI_XTERM_DIGIT_40XM, ITEM_GUI_XTERM_DIGIT_50XM, ITEM_GUI_XTERM_DIGIT_60XM, ITEM_GUI_XTERM_DIGIT_70XM, ITEM_GUI_XTERM_DIGIT_80XM, ITEM_GUI_XTERM_DIGIT_90XM);
    public static final List<GuiItem> COLLECTION_ITEM_GUI_XTERM_DIGIT_N00_M = Arrays.asList(null, ITEM_GUI_XTERM_DIGIT_100M, ITEM_GUI_XTERM_DIGIT_200M, ITEM_GUI_XTERM_DIGIT_300M, ITEM_GUI_XTERM_DIGIT_400M, ITEM_GUI_XTERM_DIGIT_500M, ITEM_GUI_XTERM_DIGIT_600M, ITEM_GUI_XTERM_DIGIT_700M, ITEM_GUI_XTERM_DIGIT_800M, ITEM_GUI_XTERM_DIGIT_900M);

    public static void register() {
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_left"), ITEM_GUI_XTERM_L);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_right"), ITEM_GUI_XTERM_R);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_empty"), ITEM_GUI_XTERM_EMPTY);

        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_1xxs"), ITEM_GUI_XTERM_DIGIT_1XXS);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_10xs"), ITEM_GUI_XTERM_DIGIT_10XS);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_100s"), ITEM_GUI_XTERM_DIGIT_100S);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_1xxk"), ITEM_GUI_XTERM_DIGIT_1XXK);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_10xk"), ITEM_GUI_XTERM_DIGIT_10XK);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_100k"), ITEM_GUI_XTERM_DIGIT_100K);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_1xxm"), ITEM_GUI_XTERM_DIGIT_1XXM);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_10xm"), ITEM_GUI_XTERM_DIGIT_10XM);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_100m"), ITEM_GUI_XTERM_DIGIT_100M);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_2xxs"), ITEM_GUI_XTERM_DIGIT_2XXS);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_20xs"), ITEM_GUI_XTERM_DIGIT_20XS);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_200s"), ITEM_GUI_XTERM_DIGIT_200S);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_2xxk"), ITEM_GUI_XTERM_DIGIT_2XXK);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_20xk"), ITEM_GUI_XTERM_DIGIT_20XK);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_200k"), ITEM_GUI_XTERM_DIGIT_200K);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_2xxm"), ITEM_GUI_XTERM_DIGIT_2XXM);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_20xm"), ITEM_GUI_XTERM_DIGIT_20XM);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_200m"), ITEM_GUI_XTERM_DIGIT_200M);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_3xxs"), ITEM_GUI_XTERM_DIGIT_3XXS);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_30xs"), ITEM_GUI_XTERM_DIGIT_30XS);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_300s"), ITEM_GUI_XTERM_DIGIT_300S);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_3xxk"), ITEM_GUI_XTERM_DIGIT_3XXK);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_30xk"), ITEM_GUI_XTERM_DIGIT_30XK);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_300k"), ITEM_GUI_XTERM_DIGIT_300K);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_3xxm"), ITEM_GUI_XTERM_DIGIT_3XXM);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_30xm"), ITEM_GUI_XTERM_DIGIT_30XM);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_300m"), ITEM_GUI_XTERM_DIGIT_300M);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_4xxs"), ITEM_GUI_XTERM_DIGIT_4XXS);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_40xs"), ITEM_GUI_XTERM_DIGIT_40XS);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_400s"), ITEM_GUI_XTERM_DIGIT_400S);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_4xxk"), ITEM_GUI_XTERM_DIGIT_4XXK);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_40xk"), ITEM_GUI_XTERM_DIGIT_40XK);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_400k"), ITEM_GUI_XTERM_DIGIT_400K);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_4xxm"), ITEM_GUI_XTERM_DIGIT_4XXM);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_40xm"), ITEM_GUI_XTERM_DIGIT_40XM);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_400m"), ITEM_GUI_XTERM_DIGIT_400M);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_5xxs"), ITEM_GUI_XTERM_DIGIT_5XXS);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_50xs"), ITEM_GUI_XTERM_DIGIT_50XS);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_500s"), ITEM_GUI_XTERM_DIGIT_500S);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_5xxk"), ITEM_GUI_XTERM_DIGIT_5XXK);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_50xk"), ITEM_GUI_XTERM_DIGIT_50XK);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_500k"), ITEM_GUI_XTERM_DIGIT_500K);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_5xxm"), ITEM_GUI_XTERM_DIGIT_5XXM);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_50xm"), ITEM_GUI_XTERM_DIGIT_50XM);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_500m"), ITEM_GUI_XTERM_DIGIT_500M);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_6xxs"), ITEM_GUI_XTERM_DIGIT_6XXS);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_60xs"), ITEM_GUI_XTERM_DIGIT_60XS);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_600s"), ITEM_GUI_XTERM_DIGIT_600S);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_6xxk"), ITEM_GUI_XTERM_DIGIT_6XXK);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_60xk"), ITEM_GUI_XTERM_DIGIT_60XK);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_600k"), ITEM_GUI_XTERM_DIGIT_600K);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_6xxm"), ITEM_GUI_XTERM_DIGIT_6XXM);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_60xm"), ITEM_GUI_XTERM_DIGIT_60XM);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_600m"), ITEM_GUI_XTERM_DIGIT_600M);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_7xxs"), ITEM_GUI_XTERM_DIGIT_7XXS);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_70xs"), ITEM_GUI_XTERM_DIGIT_70XS);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_700s"), ITEM_GUI_XTERM_DIGIT_700S);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_7xxk"), ITEM_GUI_XTERM_DIGIT_7XXK);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_70xk"), ITEM_GUI_XTERM_DIGIT_70XK);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_700k"), ITEM_GUI_XTERM_DIGIT_700K);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_7xxm"), ITEM_GUI_XTERM_DIGIT_7XXM);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_70xm"), ITEM_GUI_XTERM_DIGIT_70XM);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_700m"), ITEM_GUI_XTERM_DIGIT_700M);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_8xxs"), ITEM_GUI_XTERM_DIGIT_8XXS);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_80xs"), ITEM_GUI_XTERM_DIGIT_80XS);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_800s"), ITEM_GUI_XTERM_DIGIT_800S);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_8xxk"), ITEM_GUI_XTERM_DIGIT_8XXK);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_80xk"), ITEM_GUI_XTERM_DIGIT_80XK);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_800k"), ITEM_GUI_XTERM_DIGIT_800K);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_8xxm"), ITEM_GUI_XTERM_DIGIT_8XXM);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_80xm"), ITEM_GUI_XTERM_DIGIT_80XM);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_800m"), ITEM_GUI_XTERM_DIGIT_800M);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_9xxs"), ITEM_GUI_XTERM_DIGIT_9XXS);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_90xs"), ITEM_GUI_XTERM_DIGIT_90XS);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_900s"), ITEM_GUI_XTERM_DIGIT_900S);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_9xxk"), ITEM_GUI_XTERM_DIGIT_9XXK);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_90xk"), ITEM_GUI_XTERM_DIGIT_90XK);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_900k"), ITEM_GUI_XTERM_DIGIT_900K);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_9xxm"), ITEM_GUI_XTERM_DIGIT_9XXM);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_90xm"), ITEM_GUI_XTERM_DIGIT_90XM);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_900m"), ITEM_GUI_XTERM_DIGIT_900M);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_special_k"), ITEM_GUI_XTERM_DIGIT_SPECIAL_K);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_special_m"), ITEM_GUI_XTERM_DIGIT_SPECIAL_M);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_special_1k"), ITEM_GUI_XTERM_DIGIT_SPECIAL_1K);
        Registry.register(Registries.ITEM, new Identifier(PolyWorks.MOD_ID, "itemgui_xterm_digit_special_1m"), ITEM_GUI_XTERM_DIGIT_SPECIAL_1M);
    }
}
