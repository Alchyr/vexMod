package vexMod.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import vexMod.VexMod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static vexMod.VexMod.makeEventPath;

public class LookAheadEvent extends AbstractImageEvent {


    public static final String ID = VexMod.makeID("LookAheadEvent");
    public static final String IMG = makeEventPath("LookAheadEvent.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;

    public LookAheadEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;


        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[1]);
        imageEventText.setDialogOption(OPTIONS[3]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        AbstractCard b = AbstractDungeon.getCard(AbstractCard.CardRarity.CURSE);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(b, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        ArrayList<AbstractCard> upgradableCards = new ArrayList<>();

                        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                            if (c.canUpgrade()) {
                                upgradableCards.add(c);
                            }
                        }

                        Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
                        if (!upgradableCards.isEmpty()) {
                            upgradableCards.get(0).upgrade();
                            upgradableCards.get(1).upgrade();
                            AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(0));
                            AbstractDungeon.player.bottledCardUpgradeCheck(upgradableCards.get(1));
                            AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(upgradableCards.get(0).makeStatEquivalentCopy()));
                            AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(upgradableCards.get(1).makeStatEquivalentCopy()));
                        }
                        screenNum = 1;
                        break;
                    case 1:
                        AbstractDungeon.player.maxHealth -= 7;
                        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth) {
                            AbstractDungeon.player.currentHealth = AbstractDungeon.player.maxHealth;
                        }

                        if (AbstractDungeon.player.maxHealth < 1) {
                            AbstractDungeon.player.maxHealth = 1;
                        }
                        AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.COMMON);

                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), r);

                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                }
                break;
            case 1:
                openMap();
                break;

        }
    }
}

