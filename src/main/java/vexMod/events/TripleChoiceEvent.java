package vexMod.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.WhiteNoise;
import com.megacrit.cardcrawl.cards.green.Distraction;
import com.megacrit.cardcrawl.cards.red.InfernalBlade;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import vexMod.VexMod;

import static vexMod.VexMod.makeEventPath;

public class TripleChoiceEvent extends AbstractImageEvent {


    public static final String ID = VexMod.makeID("TripleChoiceEvent");
    public static final String IMG = makeEventPath("TripleChoiceEvent.png");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;


    public TripleChoiceEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);

        AbstractCard DemoBlade = new InfernalBlade().makeCopy();
        AbstractCard DemoDistraction = new Distraction().makeCopy();
        AbstractCard DemoWhiteNoise = new WhiteNoise().makeCopy();
        DemoBlade.upgrade();
        DemoDistraction.upgrade();
        DemoWhiteNoise.upgrade();


        imageEventText.setDialogOption(OPTIONS[0], DemoBlade);
        imageEventText.setDialogOption(OPTIONS[1], DemoDistraction);
        imageEventText.setDialogOption(OPTIONS[2], DemoWhiteNoise);
        imageEventText.setDialogOption(OPTIONS[3]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                        AbstractCard c = new InfernalBlade().makeCopy();
                        c.upgrade();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));

                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;


                        break;
                    case 1:
                        AbstractCard v = new Distraction().makeCopy();
                        v.upgrade();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(v, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));

                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;


                        break;
                    case 2:
                        AbstractCard d = new WhiteNoise().makeCopy();
                        d.upgrade();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(d, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));

                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;


                        break;
                    case 3:
                        openMap();
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
