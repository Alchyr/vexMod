package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.FireBreathingPower;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class Dragonfruit extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("Dragonfruit");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DragonFruit.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DragonFruit.png"));


    public Dragonfruit() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.increaseMaxHp(5, true);
    }

    @Override
    public void atPreBattle() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FireBreathingPower(AbstractDungeon.player, 1)));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
