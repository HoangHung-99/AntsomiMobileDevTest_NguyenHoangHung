package com.example.pushnotificationservice;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public interface MyCallback extends Serializable {
    void onCallback();
}
