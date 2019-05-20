package vexMod.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import vexMod.VexMod;

import java.util.ArrayList;

import static vexMod.VexMod.makeEventPath;

public class XCostLoverEvent extends AbstractImageEvent {


    public static final String ID = VexMod.makeID("XCostLoverEvent");
    public static final String IMG = makeEventPath("XCostLoverEvent.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;

    private boolean pickCard = false;

    public XCostLoverEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;


        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[1]);
        imageEventText.setDialogOption(OPTIONS[2]);
        imageEventText.setDialogOption(OPTIONS[3]);
    }

    private AbstractCard getXCostCard() {
        ArrayList<AbstractCard> allCards = new ArrayList<>();
        for (AbstractCard card : CardLibrary.getAllCards()) {
            if (card.rarity != AbstractCard.CardRarity.BASIC && card.rarity != AbstractCard.CardRarity.SPECIAL && card.cost == -1) {
                allCards.add(card);
            }
        }
        return allCards.get(AbstractDungeon.cardRandomRng.random(allCards.size() - 1)).makeCopy();
    }

    @Override
    public void update() {
        super.update();
        if (this.pickCard && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(getXCostCard().makeCopy(), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.pickCard = false;
        }
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                        AbstractDungeon.player.maxHealth = (int) ((float) AbstractDungeon.player.maxHealth * 0.8F);
                        if (AbstractDungeon.player.maxHealth <= 0) {
                            AbstractDungeon.player.maxHealth = 1;
                        }

                        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth) {
                            AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
                        }
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), new ChemicalX());
                        AbstractDungeon.shopRelicPool.remove(ChemicalX.ID);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 1:
                        this.pickCard = true;
                        AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 1, OPTIONS[5], false, false, false, true);
                        screenNum = 1;
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        break;
                    case 2:

                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractCard card1 = getXCostCard().makeCopy();
                        AbstractCard card2 = getXCostCard().makeCopy();
                        AbstractCard card3 = getXCostCard().makeCopy();
                        RewardItem reward = new RewardItem();
                        reward.cards.clear();
                        reward.cards.add(card1);
                        reward.cards.add(card2);
                        reward.cards.add(card3);
                        AbstractDungeon.getCurrRoom().addCardReward(reward);
                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        AbstractDungeon.combatRewardScreen.open();


                        break;
                    case 3:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
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
