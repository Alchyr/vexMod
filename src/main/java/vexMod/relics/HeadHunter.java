package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class HeadHunter extends CustomRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("HeadHunter");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("HeadHunter.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("HeadHunter.png"));


    public HeadHunter() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) {
            this.beginPulse();
            CardGroup rareCards = CardLibrary.getEachRare(AbstractDungeon.player);
            rareCards.shuffle();
            AbstractCard card1 = rareCards.getTopCard();
            AbstractCard card2 = rareCards.getNCardFromTop(1);
            AbstractCard card3 = rareCards.getNCardFromTop(2);
            RewardItem reward = new RewardItem();
            reward.cards.clear();
            reward.cards.add(card1.makeCopy());
            reward.cards.add(card2.makeCopy());
            reward.cards.add(card3.makeCopy());
            AbstractDungeon.getCurrRoom().addCardReward(reward);
        }
    }

    @Override
    public void onVictory() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) {
            this.flash();
            this.stopPulse();
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
