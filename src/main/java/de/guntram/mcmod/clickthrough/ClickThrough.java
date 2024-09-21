package de.guntram.mcmod.clickthrough;

import de.guntram.mcmod.clickthrough.config.ClickThroughForkedConfig;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

public class ClickThrough implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClickThroughForkedConfig.HANDLER.instance().init();
    }
    
    static public boolean isDyeOnSign = false;
    static public boolean needToSneakAgain = false;
    
    public static String getSignRowText(SignBlockEntity sign) {
        StringBuilder builder =  new StringBuilder();
        OrderedText result = OrderedText.concat(sign.getText(true).getOrderedMessages(false, Text::asOrderedText));

        result.accept((index, style, codepoint) -> {
            builder.appendCodePoint(codepoint);
            return true;
        } );
        
        return builder.toString();
    }    
}
