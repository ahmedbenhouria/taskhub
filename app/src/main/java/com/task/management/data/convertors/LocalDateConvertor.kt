package com.task.management.data.convertors

import java.time.LocalDate

class LocalDateConverter : BaseConverter<LocalDate>() {

    override fun objectFromString(value: String): LocalDate? = try {
        LocalDate.parse(value)
    } catch (e: Exception) {
        null
    }
}
