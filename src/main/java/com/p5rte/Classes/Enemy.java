package com.p5rte.Classes;

import com.p5rte.Utils.Enums.AffinityIndex;

public class Enemy {

    public class ItemDrop {
        public short itemID;
        public short dropRate;

        public ItemDrop(short item, short drop) {
            itemID = item;
            dropRate = drop;
        }
    }
    public class EventDrop extends ItemDrop {
        public short eventID;

        public EventDrop(short event, short item, short drop) {
            super(item, drop);
            eventID = event;
        }
    }

    private int flagBits;

    public short arcanaID;
    public short level;
    public int hp;
    public int sp;

    public byte[] stats = new byte[5];
    public short[] skillIDs = new short[8];

    public short expReward;
    public short moneyReward;
    public ItemDrop[] itemDrops = new ItemDrop[4];
    public EventDrop eventDrop;

    public AffinityIndex attackAttribute;
    public byte attackAccuracy;
    public short attackDamage;

    public String name;


    public void flipFlag(int position) {
        position--; // get shift amount from Bit position
        flagBits = flagBits ^ (1 << position);
    }
    public boolean getFlagAsBoolean(int position) {
        position--; // get shift amount from Bit position
        return (flagBits & (1 << position)) != 0;
    }
}
