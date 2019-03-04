package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class BetterTron
        extends CustomRelic
{

    // ID, images, text.
    public static final String ID = VexMod.makeID("BetterTron");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public BetterTron() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
        this.counter = 3;
    }

    // Upgrade unupgraded gained cards.
    @Override
    public void onObtainCard(AbstractCard c)
    {
        if (this.counter >= 1 && c.canUpgrade() && !c.upgraded)
        {
            c.upgrade();
            this.counter -= 1;
            if (this.counter == 0) {
                this.usedUp();
                this.counter = 0;
            }
        }
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
