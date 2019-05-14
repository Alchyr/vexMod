package vexMod.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import vexMod.VexMod;

import static vexMod.VexMod.makeEventPath;

public class RareCardShrine extends AbstractImageEvent {


    public static final String ID = VexMod.makeID("RareCardShrine");
    public static final String IMG = makeEventPath("RareCardShrine.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;

    public RareCardShrine() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;

        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[2]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        CardGroup rareCards = CardLibrary.getEachRare(AbstractDungeon.player);
                        rareCards.shuffle();
                        AbstractCard card1 = rareCards.getTopCard();
                        AbstractCard card2 = rareCards.getNCardFromTop(1);
                        AbstractCard card3 = rareCards.getNCardFromTop(2);
                        RewardItem reward = new RewardItem();
                        reward.cards.clear();
                        reward.cards.add(card1);
                        reward.cards.add(card2);
                        reward.cards.add(card3);
                        AbstractDungeon.getCurrRoom().addCardReward(reward);
                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        AbstractDungeon.combatRewardScreen.open();

                        break;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                }
                break;
            case 1:
                if (i == 0) {
                    openMap();
                }
                break;
        }
    }

}
