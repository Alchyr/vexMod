package vexMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import vexMod.vfx.RelicSpeechBubble;

public class RelicTalkAction extends AbstractGameAction {
    private AbstractRelic src;
    private String msg;
    private boolean used = false;
    private float bubbleDuration;

    public RelicTalkAction(AbstractRelic source, String text, float duration, float bubbleDuration) {
        src = source;
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_MED;
        } else {
            this.duration = duration;
        }
        this.msg = text;
        this.actionType = AbstractGameAction.ActionType.TEXT;
        this.bubbleDuration = bubbleDuration;
    }

    public void update() {
        if (!this.used) {
            AbstractDungeon.effectList.add(new RelicSpeechBubble(src.currentX, src.currentY, this.bubbleDuration, this.msg, false));
            this.used = true;
        }
        tickDuration();
    }
}
