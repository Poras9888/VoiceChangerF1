package com.voice.changer.sound.effects.recorder.audio.effects;

import java.util.ArrayList;
import java.util.List;

public final class EffectFactory {
    private EffectFactory() {
    }

    public static List<VoiceEffect> createAll() {
        List<VoiceEffect> effects = new ArrayList<>();
        effects.add(new NormalEffect());
        effects.add(new RobotEffect());
        effects.add(new MonsterEffect());
        effects.add(new CaveEffect());
        effects.add(new AlienEffect());
        effects.add(new NervousEffect());
        effects.add(new DeathEffect());
        effects.add(new DrunkEffect());
        effects.add(new UnderwaterEffect());
        effects.add(new BigRobotEffect());
        effects.add(new ZombieEffect());
        effects.add(new HexafluorideEffect());
        effects.add(new SmallAlienEffect());
        effects.add(new TelephoneEffect());
        effects.add(new HeliumEffect());
        effects.add(new GiantEffect());
        effects.add(new ChipmunkEffect());
        effects.add(new GhostEffect());
        effects.add(new DarthVaderEffect());
        effects.add(new ReverseEffect());
        return effects;
    }
}
