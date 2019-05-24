package vexMod.relics;

import Astrologer.Util.PhaseCheck;
import ThePokerPlayer.actions.MakePokerCardInHandAction;
import ThePokerPlayer.cards.PokerCard;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class HeavenlyCard extends CustomRelic {

    public static final String ID = VexMod.makeID("HeavenlyCard");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("HeavenlyCard.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GenericCardOutline.png"));

    public HeavenlyCard() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
        this.counter = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (PhaseCheck.isStar(card)) {// 33
            ++this.counter;// 34
            if (this.counter % 4 == 0) {// 36
                this.counter = 0;// 37
                this.flash();// 55
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));// 56
                AbstractDungeon.actionManager.addToBottom(new MakePokerCardInHandAction(PokerCard.Suit.values()[AbstractDungeon.cardRandomRng.random(3)], AbstractDungeon.cardRandomRng.random(1, 10), true));// 43 44 45
            }
        }
    }// 48

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
