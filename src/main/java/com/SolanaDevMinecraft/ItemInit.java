package com.SolanaDevMinecraft;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemInit {
    public static final Item NETHER_RELIC = new ArmorItem(ArmorMaterials.GOLD, ArmorItem.Type.HELMET, new Item.Properties().fireResistant());
    public static final Item THOR_AXE = new Item(new Item.Properties().stacksTo(1).durability(2031));
    public static final Item BOOT_RELIC = new ArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.BOOTS, new Item.Properties().fireResistant());
    public static final Item WING_RELIC = new ElytraItem(new Item.Properties().stacksTo(1).durability(432).rarity(Rarity.EPIC).fireResistant());
    public static final Item LEG_RELIC = new ArmorItem(ArmorMaterials.NETHERITE, ArmorItem.Type.LEGGINGS, new Item.Properties().fireResistant().rarity(Rarity.EPIC));

    public static void registerItems() {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SolanaForge.MODID, "nether_relic"), NETHER_RELIC);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SolanaForge.MODID, "thor_axe"), THOR_AXE);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SolanaForge.MODID, "boot_relic"), BOOT_RELIC);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SolanaForge.MODID, "wing_relic"), WING_RELIC);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(SolanaForge.MODID, "leg_relic"), LEG_RELIC);
    }
}
