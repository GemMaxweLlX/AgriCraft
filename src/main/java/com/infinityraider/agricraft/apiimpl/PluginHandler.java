/*
 */
package com.infinityraider.agricraft.apiimpl;

import com.agricraft.agricore.core.AgriCore;
import com.infinityraider.agricraft.api.AgriApi;
import com.infinityraider.agricraft.api.AgriPlugin;
import com.infinityraider.agricraft.api.IAgriPlugin;
import com.infinityraider.agricraft.api.adapter.IAgriAdapterizer;
import com.infinityraider.agricraft.api.fertilizer.IAgriFertilizer;
import com.infinityraider.agricraft.api.misc.IAgriRegistry;
import com.infinityraider.agricraft.api.mutation.IAgriMutationEngine;
import com.infinityraider.agricraft.api.mutation.IAgriMutationRegistry;
import com.infinityraider.agricraft.api.plant.IAgriPlant;
import com.infinityraider.agricraft.api.seed.AgriSeed;
import com.infinityraider.agricraft.api.soil.IAgriSoil;
import com.infinityraider.agricraft.api.stat.IAgriStat;
import com.infinityraider.agricraft.api.stat.IAgriStatCalculator;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 *
 *
 */
public final class PluginHandler {

    private static final Deque<IAgriPlugin> plugins = new ConcurrentLinkedDeque<>();

    public static void preInit(FMLPreInitializationEvent event) {
        plugins.addAll(getInstances(event.getAsmData(), AgriPlugin.class, IAgriPlugin.class));
    }

    public static void init() {
        plugins.stream().filter(IAgriPlugin::isEnabled).forEach(IAgriPlugin::initPlugin);
    }

    public static void postInit() {
        registerSoils(AgriApi.SoilRegistry().get());
        registerPlants(AgriApi.PlantRegistry().get());
        registerMutations(AgriApi.MutationRegistry().get());
        registerStats(AgriApi.StatRegistry().get());
        registerSeeds(AgriApi.SeedRegistry().get());
        registerFertilizers(AgriApi.FertilizerRegistry().get());
        registerStatCalculators(AgriApi.StatCalculatorRegistry().get());
        registerCrossStrategies(AgriApi.MutationEngine().get());
    }

    public static void loadTextures(Consumer<ResourceLocation> registry) {
        plugins.stream().filter(IAgriPlugin::isEnabled).forEach((p) -> p.registerTextures(registry));
    }

    public static void registerSoils(IAgriRegistry<IAgriSoil> soilRegistry) {
        plugins.stream().filter(IAgriPlugin::isEnabled).forEach((p) -> p.registerSoils(soilRegistry));
    }

    public static void registerPlants(IAgriRegistry<IAgriPlant> plantRegistry) {
        plugins.stream().filter(IAgriPlugin::isEnabled).forEach((p) -> p.registerPlants(plantRegistry));
    }

    public static void registerMutations(IAgriMutationRegistry mutationRegistry) {
        plugins.stream().filter(IAgriPlugin::isEnabled).forEach((p) -> p.registerMutations(mutationRegistry));
    }

    public static void registerStats(IAgriAdapterizer<IAgriStat> statRegistry) {
        plugins.stream().filter(IAgriPlugin::isEnabled).forEach((p) -> p.registerStats(statRegistry));
    }

    public static void registerSeeds(IAgriAdapterizer<AgriSeed> seedRegistry) {
        plugins.stream().filter(IAgriPlugin::isEnabled).forEach((p) -> p.registerSeeds(seedRegistry));
    }

    public static void registerFertilizers(IAgriAdapterizer<IAgriFertilizer> fertilizerRegistry) {
        plugins.stream().filter(IAgriPlugin::isEnabled).forEach((p) -> p.registerFertilizers(fertilizerRegistry));
    }

    public static void registerStatCalculators(IAgriAdapterizer<IAgriStatCalculator> calculatorRegistry) {
        plugins.stream().filter(IAgriPlugin::isEnabled).forEach((p) -> p.registerStatCalculators(calculatorRegistry));
    }

    public static void registerCrossStrategies(IAgriMutationEngine mutationEngine) {
        plugins.stream().filter(IAgriPlugin::isEnabled).forEach(p -> p.registerCrossStrategies(mutationEngine));
    }

    /**
     * Loads classes with a specific annotation from an asm data table.
     *
     * Borrowed from JEI's source code, which is licensed under the MIT license.
     *
     * @param <T> The type of class to load.
     * @param asm The asm data table to load classes from.
     * @param anno The annotation marking classes of interest.
     * @param type The class type to load, as to get around Type erasure.
     * @return A list of the loaded classes, instantiated.
     */
    private static <T> List<T> getInstances(ASMDataTable asm, Class anno, Class<T> type) {
        List<T> instances = new ArrayList<>();
        for (ASMDataTable.ASMData asmData : asm.getAll(anno.getCanonicalName())) {
            try {
                T instance = Class.forName(asmData.getClassName()).asSubclass(type).newInstance();
                instances.add(instance);
            } catch (ClassNotFoundException | NoClassDefFoundError | IllegalAccessException | InstantiationException e) {
                AgriCore.getLogger("AgriCraft-Plugins").debug(
                        "%nFailed to load AgriPlugin%n\tOf class: {0}!%n\tFor annotation: {1}!%n\tAs Instanceof: {2}!",
                        asmData.getClassName(),
                        anno.getCanonicalName(),
                        type.getCanonicalName()
                );
            }
        }
        return instances;
    }

}
