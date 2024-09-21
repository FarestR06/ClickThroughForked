package de.guntram.mcmod.clickthrough.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import net.minecraft.text.Text;

import static de.guntram.mcmod.clickthrough.config.ClickThroughForkedConfig.HANDLER;

public class ClickThroughForkedConfigScreen implements ModMenuApi {

    protected static final Option<Boolean> SNEAK_TO_DYE = Option.<Boolean>createBuilder()
            .name(Text.translatable("clickthrough.config.sneaktodye"))
            .description(OptionDescription.createBuilder().text(
                    Text.translatable("clickthrough.config.tt.sneaktodye")
            ).build())
            .binding(
                    true,
                    () -> HANDLER.instance().sneakToDyeSigns,
                    newVal -> HANDLER.instance().sneakToDyeSigns = newVal
            )
            .controller(opt -> BooleanControllerBuilder.create(opt)
                    .yesNoFormatter()
                    .coloured(true)
            )
            .build();

    protected static final Option<Boolean> ONLY_CONTAINERS = Option.<Boolean>createBuilder()
            .name(Text.translatable("clickthrough.config.onlycontainers"))
            .description(OptionDescription.createBuilder().text(
                    Text.translatable("clickthrough.config.tt.onlycontainers")
            ).build())
            .binding(
                    true,
                    () -> HANDLER.instance().onlyToContainers,
                    newVal -> HANDLER.instance().onlyToContainers = newVal
            )
            .controller(opt -> BooleanControllerBuilder.create(opt)
                    .yesNoFormatter()
                    .coloured(true)
            )
            .build();

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("clickthrough.config.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("clickthrough.config.category.general"))
                        .option(SNEAK_TO_DYE)
                        .option(ONLY_CONTAINERS)
                        .build())
                .save(HANDLER::save)
                .build()
                .generateScreen(screen);
    }
}
