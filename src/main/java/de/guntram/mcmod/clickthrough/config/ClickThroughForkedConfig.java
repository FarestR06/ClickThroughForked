package de.guntram.mcmod.clickthrough.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ClickThroughForkedConfig {

    public static ConfigClassHandler<ClickThroughForkedConfig> HANDLER = ConfigClassHandler.createBuilder(ClickThroughForkedConfig.class)
            .id(Identifier.of("clickthrough", "clickthrough-config"))
                    .serializer(config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(FabricLoader.getInstance().getConfigDir().resolve("my_mod.json5"))
                            .appendGsonBuilder(GsonBuilder::setPrettyPrinting) // not needed, pretty print by default
                            .setJson5(true)
                            .build())
                    .build();

    private static final String[] defaultPatterns = {
            "\\[\\D+\\]",
            "",
            "b\\s*\\d+|b\\s*\\d+\\s*:\\s*\\d+\\s*s|\\d+\\s*s",
            ""
    };

    @SerialEntry(comment = "When enabled, you can directly interact with signs by sneaking.")
    public boolean sneakToDyeSigns = true;

    @SerialEntry(comment = "When enabled, the clickthrough feature only applies to containers.")
    public boolean onlyToContainers = true;

    public Pattern[] compiledPatterns = new Pattern[4];

    @SerialEntry
    public String[] patterns = new String[4];

    public void init() {

        patterns = defaultPatterns;
        HANDLER.load();

        for (int i=0; i<4; i++) {
            try {
                if (patterns[i].isEmpty()) {
                    compiledPatterns[i] = null;
                } else {
                    compiledPatterns[i] = Pattern.compile(patterns[i], Pattern.CASE_INSENSITIVE);
                }
            } catch (PatternSyntaxException ex) {
                System.out.println("Pattern syntax exception with Pattern '"+patterns[i]+"' "+ex.getMessage());
                compiledPatterns[i] = null;
            }
        }
        HANDLER.save();
    }
}
