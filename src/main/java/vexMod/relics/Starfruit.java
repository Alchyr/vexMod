package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import vexMod.VexMod;
import vexMod.cards.StarBlast;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class Starfruit extends CustomRelic {


    public static final String ID = VexMod.makeID("Starfruit");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Starfruit.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Starfruit.png"));


    public Starfruit() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
        this.tips.add(new PowerTip(DESCRIPTIONS[1], DESCRIPTIONS[2]));
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.increaseMaxHp(6, true);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new StarBlast(), 3, true, true));
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
