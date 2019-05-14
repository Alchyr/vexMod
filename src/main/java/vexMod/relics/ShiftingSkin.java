package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import vexMod.VexMod;
import vexMod.actions.BlazeAction;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class ShiftingSkin extends CustomRelic {


    public static final String ID = VexMod.makeID("ShiftingSkin");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ShiftingSkin.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ShiftingSkin.png"));

    public ShiftingSkin() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new BlazeAction(AbstractDungeon.player, 1, true));
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    public void transformDescription(String incomingPower) {
        this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + incomingPower + DESCRIPTIONS[2];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
