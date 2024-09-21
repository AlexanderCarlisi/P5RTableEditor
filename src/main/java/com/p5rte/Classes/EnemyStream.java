package com.p5rte.Classes;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import com.p5rte.Utils.Constants;
import com.p5rte.Utils.Enums;
import com.p5rte.Utils.Enums.AffinityDataIndex;
import com.p5rte.Utils.FileStreamUtil;


public class EnemyStream {

    private static Enemy[] m_enemies;

    public static void start() {
        readEnemies();
    }

    public static void restart() {
        m_enemies = null;
        start();
    }

    public static void releaseResources() {
        m_enemies = null;
    }

    private static void readEnemies() {
        m_enemies = new Enemy[783];

        try (FileInputStream unitStream = new FileInputStream(Constants.Path.INPUT_UNIT_TABLE);) {
            unitStream.skip(0x4); // Skip Array Size

            // Segment 0 | Enemy Data
            for (int e = 0; e < 783; e++) {
                Enemy enemy = new Enemy();
                byte[] enemyBytes = unitStream.readNBytes(68);

                enemy.flagBits = FileStreamUtil.getInt(enemyBytes[0], enemyBytes[1], enemyBytes[2], enemyBytes[3]);
                enemy.arcanaID = enemyBytes[4];
                enemy.level = FileStreamUtil.getShort(enemyBytes[6], enemyBytes[7]);
                enemy.hp = FileStreamUtil.getInt(enemyBytes[8], enemyBytes[9], enemyBytes[10], enemyBytes[11]);
                enemy.sp = FileStreamUtil.getInt(enemyBytes[12], enemyBytes[13], enemyBytes[14], enemyBytes[15]);
                for (int i = 16; i < 21; i++) {
                    enemy.stats[i - 16] = enemyBytes[i];
                }
                for (int i = 22; i < 36; i += 2) {
                    enemy.skillIDs[(i - 21) / 2] = FileStreamUtil.getShort(enemyBytes[i], enemyBytes[i + 1]);
                }
                enemy.expReward = FileStreamUtil.getShort(enemyBytes[38], enemyBytes[39]);
                enemy.moneyReward = FileStreamUtil.getShort(enemyBytes[40], enemyBytes[41]);
                for (int i = 42; i < 58; i += 4) {
                    enemy.itemDrops[(i - 42) / 4] = enemy.new ItemDrop(FileStreamUtil.getShort(enemyBytes[i], enemyBytes[i + 1]),
                            FileStreamUtil.getShort(enemyBytes[i + 2], enemyBytes[i + 3]));
                }
                enemy.eventDrop = enemy.new EventDrop(FileStreamUtil.getShort(enemyBytes[58], enemyBytes[59]),
                        FileStreamUtil.getShort(enemyBytes[60], enemyBytes[61]), FileStreamUtil.getShort(enemyBytes[62], enemyBytes[63]));
                enemy.attackAttribute = Enums.AffinityIndex.getAt(enemyBytes[64]);
                enemy.attackAccuracy = enemyBytes[65];
                enemy.attackDamage = FileStreamUtil.getShort(enemyBytes[66], enemyBytes[67]);

                m_enemies[e] = enemy;
            }

            // Segment 1 | Affinities
            unitStream.skip(0x4); // Skip Array Size
            byte[] affinityBytes = unitStream.readNBytes(313210); // Each affinity is 40 bytes for 783 enemies
            for (int i = 0; i < 783; i++) {
                for (int afi = 0; afi < 20; afi++) {
                    m_enemies[i].affinities.put(Enums.AffinityIndex.getAt(afi), new Affinity(affinityBytes[i * 40 + 1], new HashMap<>()));
                
                    HashMap<Enums.AffinityDataIndex, Boolean> data = new HashMap<>();
                    byte dataByte = affinityBytes[i * 40];
                    for (int shift = 0; shift < 8; shift++) {
                        data.put(AffinityDataIndex.values()[7 - shift], (dataByte >> shift & 1) == 1);
                    }
                    m_enemies[i].affinities.put(Enums.AffinityIndex.getAt(afi), new Affinity(affinityBytes[i * 40 + 1], data));
                }
            }
            
        } catch(IOException e) {
            System.err.println("You done goofed");
            e.printStackTrace();
        }
    }


    public static Enemy getEnemy(int index) {
        return m_enemies[index];
    }
    
}
