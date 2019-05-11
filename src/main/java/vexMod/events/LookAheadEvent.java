package vexMod.events;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.WhiteNoise;
import com.megacrit.cardcrawl.cards.curses.Writhe;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.beyond.MindBloom;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.NeowsLament;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import vexMod.VexMod;
import vexMod.relics.*;

import java.util.*;

import static vexMod.VexMod.makeEventPath;

public class LookAheadEvent extends AbstractImageEvent {


    public static final String ID = VexMod.makeID("LookAheadEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("LookAheadEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    private boolean pickCard = false;

    public LookAheadEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;

        // The first dialogue options available to us.
        imageEventText.setDialogOption(OPTIONS[0]); // Into the Clash: Fight another Floor Boss.
        imageEventText.setDialogOption(OPTIONS[1]); // Into the City: Fight a simple City battle.
        if (AbstractDungeon.player.hasRelic(NeowsLament.ID)) {
            if (!AbstractDungeon.player.getRelic(NeowsLament.ID).usedUp) {
                imageEventText.setDialogOption(OPTIONS[5]);
            } else {
                imageEventText.setDialogOption(OPTIONS[2]);
            }
        } else {
            imageEventText.setDialogOption(OPTIONS[2]);
        }
        imageEventText.setDialogOption(OPTIONS[3]); // Leave
    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0:
                        AbstractDungeon.player.loseGold(20);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));// 99
                        ArrayList<AbstractCard> upgradableCards = new ArrayList();// 100
                        Iterator var2 = AbstractDungeon.player.masterDeck.group.iterator();// 101

                        while (var2.hasNext()) {
                            AbstractCard c = (AbstractCard) var2.next();
                            if (c.canUpgrade()) {// 102
                                upgradableCards.add(c);// 103
                            }
                        }

                        List<String> cardMetrics = new ArrayList();// 107
                        Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));// 109
                        if (!upgradableCards.isEmpty()) {// 111
                            ((AbstractCard) upgradableCards.get(0)).upgrade();// 114
                            cardMetrics.add(((AbstractCard) upgradableCards.get(0)).cardID);// 115
                            AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard) upgradableCards.get(0));// 116
                            AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(((AbstractCard) upgradableCards.get(0)).makeStatEquivalentCopy()));// 117
                        }
                        screenNum = 1;
                        break; // Onto screen 1 we go.
                    case 1: // If you press button the second button (Button at index 1), in this case: Ease
                        AbstractDungeon.player.maxHealth -= 5;
                        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth) {
                            AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
                        }

                        if (AbstractDungeon.player.maxHealth < 1) {
                            AbstractDungeon.player.maxHealth = 1;
                        }
                        AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.COMMON);

                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), r);

                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        screenNum = 1;
                        break; // Onto screen 1 we go.
                    case 2: // If you press button the third button (Button at index 2), in this case: Acceptance
                        AbstractCard b = AbstractDungeon.getCard(AbstractCard.CardRarity.CURSE);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(b, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        if (AbstractDungeon.player.hasRelic(NeowsLament.ID)) {
                            if (!AbstractDungeon.player.getRelic(NeowsLament.ID).usedUp) {
                                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.UNCOMMON));
                            } else {
                                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), new NeowsLament());
                            }
                        } else {
                            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), new NeowsLament());
                        }

                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]); // Update the text of the event
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]); // 1. Change the first button to the [Leave] button
                        this.imageEventText.clearRemainingOptions(); // 2. and remove all others
                        screenNum = 1;
                        break; // Onto screen 1 we go.
                    case 3:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                }
                break;
            case 1: // Welcome to screenNum = 1;
                openMap(); // You'll open the map and end the event.
                break;

        }
    }
}

