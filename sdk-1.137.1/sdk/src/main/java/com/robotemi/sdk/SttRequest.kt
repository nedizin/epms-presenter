package com.robotemi.sdk

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.IntRange

data class SttRequest(
    val languages: List<SttLanguage> = listOf(),
    @IntRange(from = 0, to = MAX_TIMEOUT) val timeout: Int = 0,
    val multipleConversation: Boolean = false
) : Parcelable {
    override fun describeContents(): Int = 0
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeIntArray(IntArray(languages.size) { languages[it].value })
        dest.writeInt(timeout)
        dest.writeByte(if (multipleConversation) 1 else 0)
    }
    companion object {
        const val MAX_TIMEOUT = 120L
        @JvmField
        val CREATOR = object : Parcelable.Creator<SttRequest> {
            override fun createFromParcel(source: Parcel): SttRequest {
                val arr = source.createIntArray() ?: IntArray(0)
                val languages = arr.map { SttLanguage.valueToEnum(it) }
                val timeout = source.readInt()
                val multipleConversation = source.readByte() != 0.toByte()
                return SttRequest(languages, timeout, multipleConversation)
            }
            override fun newArray(size: Int): Array<SttRequest?> = arrayOfNulls(size)
        }
    }
}
