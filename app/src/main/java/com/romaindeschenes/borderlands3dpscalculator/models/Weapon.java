package com.romaindeschenes.borderlands3dpscalculator.models;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Weapon implements Serializable {

    /**
     * Period over which damage is calculated
     */
    public static final int DAMAGE_PERIOD = 60;

    public Weapon() {

    }

    @NonNull
    @Override
    public String toString() {
        return "damage " + mDamage + ", accuracy " + mAccuracy + ", handling "
                + mHandling + ", reloadTime " + mReloadTime + ", fireRate" + mFireRate + ", magazineSize" + mMagazineSize;
    }

    public Weapon(int damage, int accuracy, int handling, float reloadTime, float fireRate, int magazineSize) {
        mDamage = damage;
        mAccuracy = accuracy;
        mHandling = handling;
        mReloadTime = reloadTime;
        mFireRate = fireRate;
        mMagazineSize = magazineSize;
    }

    public int getDamage() {
        return mDamage;
    }

    public void setDamage(int damage) {
        this.mDamage = damage;
    }

    public int getAccuracy() {
        return mAccuracy;
    }

    public void setAccuracy(int accuracy) {
        this.mAccuracy = accuracy;
    }

    public int getHandling() {
        return mHandling;
    }

    public void setHandling(int handling) {
        this.mHandling = handling;
    }

    public float getReloadTime() {
        return mReloadTime;
    }

    public void setReloadTime(float reloadTime) {
        this.mReloadTime = reloadTime;
    }

    public float getFireRate() {
        return mFireRate;
    }

    public void setFireRate(float fireRate) {
        this.mFireRate = fireRate;
    }

    public int getMagazineSize() {
        return mMagazineSize;
    }

    public void setMagazineSize(int magazineSize) {
        this.mMagazineSize = magazineSize;
    }

    public int getDamagePerMagazine() {
        return mDamage * mMagazineSize;
    }

    public float getTimeToEmptyMagazine() {
        if (mFireRate <= 0) {
            return 0;
        }
        return Math.round((mMagazineSize / mFireRate) * 100f) / 100f;
    }

    /**
     * @return damage per second (over a 60 second period)
     */
    public int getDamagePerSecond () {
        float fireAndReloadTime = getTimeToEmptyMagazine() + mReloadTime;
        if (getTimeToEmptyMagazine() <= 0 || mReloadTime <= 0) {
            return 0;
        }
        float numberOfFullCycles = DAMAGE_PERIOD / fireAndReloadTime;

        // Get numbers after the decimal point : unfinishedCycle
        double unfinishedCyclePercentage = numberOfFullCycles - Math.floor(numberOfFullCycles);

        // At which point in the cycle the reloading starts
        double cycleReloadStart = getTimeToEmptyMagazine() / fireAndReloadTime;

        // Min value because we do not want the time spent reloading at the end to matter
        double unfinishedCycle = Math.min(unfinishedCyclePercentage, cycleReloadStart);

        return (int)((numberOfFullCycles + unfinishedCycle) * getDamagePerMagazine()) / DAMAGE_PERIOD;
    }

    private int mDamage;
    private int mAccuracy;
    private int mHandling;
    private float mReloadTime;
    private float mFireRate;
    private int mMagazineSize;

}
