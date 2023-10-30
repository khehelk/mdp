package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable
import kotlin.collections.ArrayList

data class TestServiceItem(
    val image: Int,
    val name: String?,
    val animals: ArrayList<String>?,
    val price: Double
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image)
        parcel.writeString(name)
        parcel.writeStringList(animals)
        parcel.writeDouble(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TestServiceItem> {
        override fun createFromParcel(parcel: Parcel): TestServiceItem {
            return TestServiceItem(parcel)
        }

        override fun newArray(size: Int): Array<TestServiceItem?> {
            return arrayOfNulls(size)
        }
    }
}

fun getServices(): List<TestServiceItem>{
    return listOf(
        TestServiceItem(R.drawable.image_service, "Inoculation", arrayListOf("Dogs", "Cats","Dogs", "Cats","Dogs", "Cats","Dogs", "Cats","Dogs", "Cats","Dogs", "Cats","Dogs", "Cats",), 20.0),
        TestServiceItem(R.drawable.image_service, "Inoculation", arrayListOf("Dogs", "Cats"), 20.0),
        TestServiceItem(R.drawable.image_service, "Inoculation", arrayListOf("Dogs", "Cats"), 20.0),
        TestServiceItem(R.drawable.image_service, "Inoculation", arrayListOf("Dogs", "Cats"), 20.0),
        TestServiceItem(R.drawable.image_service, "Inoculation", arrayListOf("Dogs", "Cats"), 20.0),
        TestServiceItem(R.drawable.image_service, "Inoculation", arrayListOf("Dogs", "Cats"), 20.0),
        TestServiceItem(R.drawable.image_service, "Inoculation", arrayListOf("Dogs", "Cats"), 20.0),
        TestServiceItem(R.drawable.image_service, "Inoculation", arrayListOf("Dogs", "Cats"), 20.0),
        TestServiceItem(R.drawable.image_service, "Inoculation", arrayListOf("Dogs", "Cats"), 20.0),
        TestServiceItem(R.drawable.image_service, "Inoculation", arrayListOf("Dogs", "Cats"), 20.0),
        TestServiceItem(R.drawable.image_service, "Inoculation", arrayListOf("Dogs", "Cats"), 20.0),
        TestServiceItem(R.drawable.image_service, "Inoculation", arrayListOf("Dogs", "Cats"), 20.0),
    )
}