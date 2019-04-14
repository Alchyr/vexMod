package vexMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.CLINK);
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (room instanceof MonsterRoomElite) {
            this.pulse = true;
            this.beginPulse();
            AbstractCard card1 = AbstractDungeon.getCard(AbstractCard.CardRarity.RARE);
            AbstractCard card2 = AbstractDungeon.getCard(AbstractCard.CardRarity.RARE);
            AbstractCard card3 = AbstractDungeon.getCard(AbstractCard.CardRarity.RARE);
            RewardItem reward = new RewardItem();
            reward.cards.clear();
            reward.cards.add(card1);
            reward.cards.add(card2);
            reward.cards.add(card3);
            AbstractDungeon.getCurrRoom().addCardReward(reward);
        } else {
            this.pulse = false;
        }

    }

    @Override
    public void onVictory() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) {
            this.flash();
            this.pulse = false;
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
