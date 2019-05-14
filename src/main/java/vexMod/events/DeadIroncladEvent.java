package vexMod.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import vexMod.VexMod;

import java.util.ArrayList;

import static vexMod.VexMod.makeEventPath;

public class DeadIroncladEvent extends AbstractImageEvent {


    public static final String ID = VexMod.makeID("DeadIroncladEvent");
    public static final String IMG = makeEventPath("DeadIroncladEvent.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;

    private boolean canPurge;

    public DeadIroncladEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;
        for (int q = AbstractDungeon.player.masterDeck.group.size() - 1; q >= 0; --q) {
            if (AbstractDungeon.player.masterDeck.group.get(q).type == AbstractCard.CardType.CURSE && !AbstractDungeon.player.masterDeck.group.get(q).inBottleFlame && !AbstractDungeon.player.masterDeck.group.get(q).inBottleLightning && !AbstractDungeon.player.masterDeck.group.get(q).cardID.equals("AscendersBane") && !AbstractDungeon.player.masterDeck.group.get(q).cardID.equals("Necronomicurse")) {
                this.canPurge = true;
            }
        }

        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[1]);
        if (this.canPurge) {
            this.imageEventText.setDialogOption(OPTIONS[2]);
        } else {
            this.imageEventText.setDialogOption(OPTIONS[3], true);
        }

        imageEventText.setDialogOption(OPTIONS[4]);
    }

    private static AbstractCard getRandomRewardColorSpecificCard(AbstractCard.CardColor color) {
        ArrayList<AbstractCard> tmp = new ArrayList<>();
        for (AbstractCard card : CardLibrary.getAllCards()) {
            if (card.color == color && card.rarity != AbstractCard.CardRarity.BASIC && card.rarity != AbstractCard.CardRarity.SPECIAL) {
                tmp.add(card);
            }
        }
        return tmp.get(AbstractDungeon.cardRandomRng.random(tmp.size() - 1));
    }


    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                        int goldAmount = AbstractDungeon.miscRng.random(80, 120);
                        AbstractDungeon.effectList.add(new RainingGoldEffect(goldAmount));
                        AbstractDungeon.player.gainGold(goldAmount);

                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;

                        break;
                    case 1:

                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractCard card1 = getRandomRewardColorSpecificCard(AbstractCard.CardColor.RED).makeCopy();
                        AbstractCard card2 = getRandomRewardColorSpecificCard(AbstractCard.CardColor.RED).makeCopy();
                        AbstractCard card3 = getRandomRewardColorSpecificCard(AbstractCard.CardColor.RED).makeCopy();
                        RewardItem reward = new RewardItem();
                        AbstractCard card4 = getRandomRewardColorSpecificCard(AbstractCard.CardColor.RED).makeCopy();
                        AbstractCard card5 = getRandomRewardColorSpecificCard(AbstractCard.CardColor.RED).makeCopy();
                        AbstractCard card6 = getRandomRewardColorSpecificCard(AbstractCard.CardColor.RED).makeCopy();
                        RewardItem reward2 = new RewardItem();
                        AbstractCard card7 = getRandomRewardColorSpecificCard(AbstractCard.CardColor.RED).makeCopy();
                        AbstractCard card8 = getRandomRewardColorSpecificCard(AbstractCard.CardColor.RED).makeCopy();
                        AbstractCard card9 = getRandomRewardColorSpecificCard(AbstractCard.CardColor.RED).makeCopy();
                        RewardItem reward3 = new RewardItem();
                        reward.cards.clear();
                        reward.cards.add(card1);
                        reward.cards.add(card2);
                        reward.cards.add(card3);
                        reward2.cards.clear();
                        reward2.cards.add(card4);
                        reward2.cards.add(card5);
                        reward2.cards.add(card6);
                        reward3.cards.clear();
                        reward3.cards.add(card7);
                        reward3.cards.add(card8);
                        reward3.cards.add(card9);
                        AbstractDungeon.getCurrRoom().addCardReward(reward);
                        AbstractDungeon.getCurrRoom().addCardReward(reward2);
                        AbstractDungeon.getCurrRoom().addCardReward(reward3);
                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        AbstractDungeon.combatRewardScreen.open();


                        break;
                    case 2:
                        if (this.canPurge) {

                            for (int q = AbstractDungeon.player.masterDeck.group.size() - 1; q >= 0; --q) {
                                if (AbstractDungeon.player.masterDeck.group.get(q).type == AbstractCard.CardType.CURSE && !AbstractDungeon.player.masterDeck.group.get(q).inBottleFlame && !AbstractDungeon.player.masterDeck.group.get(q).inBottleLightning && !AbstractDungeon.player.masterDeck.group.get(q).cardID.equals("AscendersBane") && !AbstractDungeon.player.masterDeck.group.get(q).cardID.equals("Necronomicurse")) {
                                    AbstractDungeon.effectList.add(new PurgeCardEffect(AbstractDungeon.player.masterDeck.group.get(q)));
                                    AbstractDungeon.player.masterDeck.removeCard(AbstractDungeon.player.masterDeck.group.get(q));
                                }
                            }

                            this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                            this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                            this.imageEventText.clearRemainingOptions();
                            screenNum = 1;
                            break;
                        }
                    case 3:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
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
