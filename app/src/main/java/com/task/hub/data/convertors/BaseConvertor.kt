package com.task.hub.data.convertors

import androidx.room.TypeConverter

abstract class BaseConverter<T> {

    /**
     * Converts the [value] to a String. The default implementation is to call [toString].
     *  This can be overridden.
     */
    @TypeConverter
    open fun toString(value: T?): String? = value?.toString()

    /**
     * Converts the [value] to a [T]. If the [value] is null or empty, the returned [T] is null.
     *  If not, this called [objectFromString]. This can be overridden.
     */
    @TypeConverter
    open fun fromString(value: String?): T? =
        if (value.isNullOrEmpty()) null else objectFromString(value)

    /**
     * Converts the [value] to a [T]
     */
    abstract fun objectFromString(value: String): T?
}