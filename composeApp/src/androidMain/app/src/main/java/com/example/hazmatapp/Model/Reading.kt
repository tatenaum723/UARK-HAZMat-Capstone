package com.example.hazmatapp.Model

import android.os.Parcel
import android.os.Parcelable

// Contains attributes of the readings that will be stored in the firebase realtime database
data class Reading(
    var name: String? = null,
    var location: String? = null,
    var notes: String? = null,
    var date: String? = null,
    var time: String? = null,
    var methaneLelPercentage: String? = null,
    var methaneVolumePercentage: String? = null,
    var id: String? = null
)  : Parcelable {

    // Methods used to parse the values of the task object

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(location)
        parcel.writeString(notes)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeString(methaneLelPercentage)
        parcel.writeString(methaneVolumePercentage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Reading> {
        override fun createFromParcel(parcel: Parcel): Reading {
            return Reading(parcel)
        }

        override fun newArray(size: Int): Array<Reading?> {
            return arrayOfNulls(size)
        }
    }
}