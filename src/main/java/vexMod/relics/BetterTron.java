package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class BetterTron extends CustomRelic {


    public static final String ID = VexMod.makeID("BetterTron");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BetterTron.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BetterTron.png"));

    public BetterTron() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
        this.counter = 3;
    }


    @Override
    public void onObtainCard(AbstractCard c) {
        if (this.counter >= 1 && c.canUpgrade() && !c.upgraded) {
            c.upgrade();
            this.counter -= 1;
            if (this.counter == 0) {
                this.usedUp();
                this.counter = 0;
            }
        }
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 40;
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
