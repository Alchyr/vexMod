package vexMod.util;

import javafx.util.Pair;

import static vexMod.VexMod.makeAudioPath;

public class SoundEffects {

    public static final Pair<String, String> Attack = new Pair<>("vexMod:Attack", makeAudioPath("Attack.ogg"));
    public static final Pair<String, String> Block = new Pair<>("vexMod:Block", makeAudioPath("Block.ogg"));
    public static final Pair<String, String> Buff = new Pair<>("vexMod:Buff", makeAudioPath("Buff.ogg"));
    public static final Pair<String, String> Debuff = new Pair<>("vexMod:Debuff", makeAudioPath("Debuff.ogg"));

}