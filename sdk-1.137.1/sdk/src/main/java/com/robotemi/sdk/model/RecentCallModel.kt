package com.robotemi.sdk.model

import android.os.Parcel
import android.os.Parcelable

data class RecentCallModel(
    val userId: String,
    val timestamp: Long?,
    val sessionId: String,
    val callType: Int
) : Parcelable {
    override fun describeContents(): Int = 0
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(userId)
        if (timestamp != null) {
            dest.writeByte(1)
            dest.writeLong(timestamp)
        } else {
            dest.writeByte(0)
        }
        dest.writeString(sessionId)
        dest.writeInt(callType)
    }
    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<RecentCallModel> {
            override fun createFromParcel(source: Parcel): RecentCallModel {
                val userId = source.readString()!!
                val timestamp = if (source.readByte() != 0.toByte()) source.readLong() else null
                val sessionId = source.readString()!!
                val callType = source.readInt()
                return RecentCallModel(userId, timestamp, sessionId, callType)
            }
            override fun newArray(size: Int): Array<RecentCallModel?> = arrayOfNulls(size)
        }
    }
}
