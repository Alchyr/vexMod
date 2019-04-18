package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Frost;
import vexMod.VexMod;
import vexMod.actions.RelicTalkAction;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class NotEnergy extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("NotEnergy");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("NotEnergy.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("NotEnergy.png"));


    public NotEnergy() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart()
    {
        AbstractDungeon.actionManager.addToBottom(new RelicTalkAction(this, DESCRIPTIONS[1], 0.0F, 3.5F));
    }

    @Override
    public int getPrice() {
        return 1;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
