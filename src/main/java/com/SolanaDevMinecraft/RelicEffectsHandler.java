package com.SolanaDevMinecraft;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RelicEffectsHandler {

    private static class LightBlockEntry {
        net.minecraft.core.BlockPos pos;
        net.minecraft.world.level.Level level;
        int ticksLeft;

        LightBlockEntry(net.minecraft.core.BlockPos pos, net.minecraft.world.level.Level level, int ticksLeft) {
            this.pos = pos;
            this.level = level;
            this.ticksLeft = ticksLeft;
        }
    }

    private static final List<LightBlockEntry> activeLights = new ArrayList<>();

    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            // Player tick emulation
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                // Relíquia do Nether (Elmo) -> Resistência ao Fogo
                ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
                if (helmet.getItem() == ItemInit.NETHER_RELIC) {
                    player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0, false, false, true));
                }
                
                // Relíquia das Botas (Meow Cat Celestial Boots Relic) -> Trilha de Luz
                ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
                if (boots.getItem() == ItemInit.BOOT_RELIC) {
                    net.minecraft.core.BlockPos pos = player.blockPosition();
                    net.minecraft.world.level.Level world = player.level();
                    if (world.getBlockState(pos).isAir()) {
                        net.minecraft.world.level.block.state.BlockState lightState = net.minecraft.world.level.block.Blocks.LIGHT.defaultBlockState()
                                .setValue(net.minecraft.world.level.block.LightBlock.LEVEL, 15);
                        world.setBlock(pos, lightState, 3);
                        synchronized (activeLights) {
                            boolean exists = false;
                            for (LightBlockEntry entry : activeLights) {
                                if (entry.pos.equals(pos)) {
                                    entry.ticksLeft = 40; // reseta tempo
                                    exists = true;
                                    break;
                                }
                            }
                            if (!exists) {
                                activeLights.add(new LightBlockEntry(pos, world, 40));
                            }
                        }
                    }
                }

                // Relíquia das Asas (Amauris Wing Relic) -> Voo Criativo, Resistência ao Fogo e Foguetes
                ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
                if (chest.getItem() == ItemInit.WING_RELIC) {
                    player.getAbilities().mayfly = true;
                    player.onUpdateAbilities();
                    player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0, false, false, true));

                    // Dar foguetes a cada 5 segundos (100 ticks) se tiver menos que 3 no inventário
                    if (player.tickCount % 100 == 0) {
                        int fireworks = 0;
                        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                            ItemStack item = player.getInventory().getItem(i);
                            if (item.getItem() == net.minecraft.world.item.Items.FIREWORK_ROCKET) {
                                fireworks += item.getCount();
                            }
                        }
                        if (fireworks < 3) {
                            player.getInventory().add(new ItemStack(net.minecraft.world.item.Items.FIREWORK_ROCKET, 3 - fireworks));
                        }
                    }
                } else {
                    if (!player.isCreative() && !player.isSpectator() && player.getAbilities().mayfly) {
                        player.getAbilities().mayfly = false;
                        player.getAbilities().flying = false;
                        player.onUpdateAbilities();
                    }
                }
            }

            // Server tick (light cleanup)
            synchronized (activeLights) {
                Iterator<LightBlockEntry> it = activeLights.iterator();
                while (it.hasNext()) {
                    LightBlockEntry entry = it.next();
                    entry.ticksLeft--;
                    if (entry.ticksLeft <= 0) {
                        if (entry.level.getBlockState(entry.pos).is(net.minecraft.world.level.block.Blocks.LIGHT)) {
                            entry.level.setBlock(entry.pos, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
                        }
                        it.remove();
                    }
                }
            }
        });
    }
}
