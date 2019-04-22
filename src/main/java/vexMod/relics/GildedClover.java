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
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import vexMod.VexMod;
import vexMod.util.TextureLoader;

import static vexMod.VexMod.makeRelicOutlinePath;
import static vexMod.VexMod.makeRelicPath;

public class GildedClover extends CustomRelic implements PostSetupCombatRewardsRelic {

    // ID, images, text.
    public static final String ID = VexMod.makeID("GildedClover");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("GildedClover.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("GildedClover.png"));


    public GildedClover() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    public void onPostSetupCombatRewards(CombatRewardScreen __instance) {
        this.flash();
        __instance.rewards.removeIf((reward) -> reward.type == RewardItem.RewardType.CARD);
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
        __instance.rewards.add(reward);
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
