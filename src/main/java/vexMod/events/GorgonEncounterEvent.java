package vexMod.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import vexMod.VexMod;
import vexMod.cards.EyeBeam;
import vexMod.cards.GorgonsGaze;
import vexMod.cards.GorgonsGlare;
import vexMod.cards.SnakeSkin;
import vexMod.relics.GorgonsHead;

import java.util.ArrayList;

import static vexMod.VexMod.makeEventPath;

public class GorgonEncounterEvent extends AbstractImageEvent {


    public static final String ID = VexMod.makeID("GorgonEncounterEvent");
    public static final String IMG = makeEventPath("GorgonEncounterEvent.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int goldAmount;

    private int screenNum = 0;

    public GorgonEncounterEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;


        imageEventText.setDialogOption(OPTIONS[0]);
        this.goldAmount = this.getGoldAmount();
        if (goldAmount != 0) {
            this.imageEventText.setDialogOption(OPTIONS[1] + goldAmount + OPTIONS[6]);
        } else {
            this.imageEventText.setDialogOption(OPTIONS[5], true);
        }
        imageEventText.setDialogOption(OPTIONS[2]);
        imageEventText.setDialogOption(OPTIONS[3]);
    }


    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                AbstractCard gorgonCard;
                switch (i) {
                    case 0:

                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        AbstractDungeon.player.increaseMaxHp(12, true);
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new GorgonsGaze(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));

                        break;
                    case 1:


                        AbstractDungeon.player.loseGold(this.goldAmount);

                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;

                        int c = AbstractDungeon.cardRandomRng.random(2);
                        if (c == 0) {
                            gorgonCard = new GorgonsGlare().makeCopy();
                        } else if (c == 1) {
                            gorgonCard = new EyeBeam().makeCopy();
                        } else {
                            gorgonCard = new SnakeSkin().makeCopy();
                        }


                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(gorgonCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));

                        break;
                    case 2:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        ArrayList<String> list = new ArrayList<>();
                        list.add("vexMod:GildedGorgon");
                        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter((list.get(0)));
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().addRelicToRewards(new GorgonsHead());
                        AbstractDungeon.lastCombatMetricKey = "Gilded Gorgon";
                        this.enterCombatFromImage();
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

    private int getGoldAmount() {
        if (AbstractDungeon.player.gold < 127) {
            return 0;
        } else {
            return 127;
        }
    }

}
