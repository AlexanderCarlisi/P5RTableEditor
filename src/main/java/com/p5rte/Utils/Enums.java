package com.p5rte.Utils;

public final class Enums {
    

    public static enum Arcana {
        Fool(1),
        Magician(2),
        Priestess(3),
        Empress(4),
        Emperor(5),
        Hierophant(6),
        Lovers(7),
        Chariot(8),
        Justice(9),
        Hermit(10),
        Fortune(11),
        Strength(12),
        Hanged(13),
        Death(14),
        Temperance(15),
        Devil(16),
        Tower(17),
        Star(18),
        Moon(19),
        Sun(20),
        Judgement(21),
        Aeon(22),
        Other(23),
        World(24),
        Faith(29),
        Councillor(30);

        public final int ID;

        private Arcana(int id) {
            this.ID = id;
        }

        public static Arcana getArcana(int id) {
            for (Arcana arcana : Arcana.values()) {
                if (arcana.ID == id) {
                    return arcana;
                }
            }
            return Other;
        }
    }


    public static enum SkillInheritance {
        None(0),
        Physical(1),
        Fire(2),
        Ice(3),
        Electric(4),
        Wind(5),
        Bless(6),
        Curse(7),
        Healing(8),
        Ailment(10),
        Almighty(12),
        RESERVE(13),
        Nuke(14),
        Psy(15);

        public final int ID;

        private SkillInheritance(int id) {
            this.ID = id;
        }

        public static SkillInheritance getSkillInheritance(int id) {
            for (SkillInheritance skillInheritance : SkillInheritance.values()) {
                if (skillInheritance.ID == id) {
                    return skillInheritance;
                }
            }
            return None;
        }
    }

}