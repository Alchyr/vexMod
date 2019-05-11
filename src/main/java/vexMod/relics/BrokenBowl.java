package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class BrokenBowl extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("BrokenBowl");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BrokenBowl.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BrokenBowl.png"));

    public BrokenBowl() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.SOLID);
    }

    // Upgrade unupgraded gained cards at the cost of Max HP.
    @Override
    public void onObtainCard(AbstractCard c) {
        if (c.canUpgrade() && !c.upgraded) {
            AbstractDungeon.player.decreaseMaxHealth(2);
            c.upgrade();
        }
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
