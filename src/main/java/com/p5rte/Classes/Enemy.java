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

    private byte[] flagBytes = new byte[4];

    public short arcanaID;
    public short level;
    public int hp;
    public int sp;

    public byte[] stats = new byte[5];
    public short[] skillIDs = new short[8];

    public short expReward;
    public short moneyReward;
    public ItemDrop[] itemDrops = new ItemDrop[4];

    public AffinityIndex elementalAttribute;
    public byte attackAccuracy;
    public short attackDamage;


    // I need to relearn bitwise operations :(
    // public void setBegging(boolean value) { // 17th bit
    //     if (value) {
    //         flagBytes[2] |= (1); // Set the bit
    //     } else {
    //         flagBytes[2] &= ~(1); // Clear the bit
    //     }   
    // }
    // public boolean getBegging() {
    //     return (flagBytes[2] & 1) == 1;
    // }

    // public void setHiding(boolean value) { // 18th bit
    //     if (value) {
    //         flagBytes[2] |= (1 << 1); // Set the bit
    //     } else {
    //         flagBytes[2] &= ~(1 << 1); // Clear the bit
    //     }
    // }
    // public boolean getHiding() {
    //     return (flagBytes[2] & (1 << 1)) == 1;
    // }
}
