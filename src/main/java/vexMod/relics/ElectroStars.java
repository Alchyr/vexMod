package vexMod.relics;

import Astrologer.Util.PhaseCheck;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class ElectroStars extends CustomRelic {

    public static final String ID = VexMod.makeID("ElectroStars");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ElectroStars.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("ElectroStars.png"));

    public ElectroStars() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.SOLID);
    }

    @Override
    public void atPreBattle() {
        AbstractDungeon.player.increaseMaxOrbSlots(3, false);
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (PhaseCheck.isStar(c)) {
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Lightning()));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
