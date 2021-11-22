package com.sdm.mgp2021_1;

// Created by TanSiewLan2021

public interface Collidable {

    public String GetType();

    public float GetPosX();
    public float GetPosY();
    public float GetRadius();

    public void OnHit(Collidable _other);
}

