package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.FireBreathingPower;
import com.megacrit.cardcrawl.relics.SneckoEye;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import vexMod.VexMod;
import vexMod.cards.StarBlast;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class FallenStar extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("FallenStar");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("FallenStar.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("FallenStar.png"));


    public FallenStar() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        this.tips.add(new PowerTip(DESCRIPTIONS[1], DESCRIPTIONS[2]));
    }

    @Override
    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new StarBlast(), 2, true, true));
    }

    @Override
    public boolean canSpawn() {
        return (!AbstractDungeon.player.hasRelic(SneckoEye.ID));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
