package org.scotia.githubfetcher.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.scotia.githubfetcher.common.Constants.ERROR_STRING
import org.scotia.githubfetcher.common.isoZonedDateToLocalDateTime
import org.scotia.githubfetcher.common.toFormattedString
import java.time.LocalDateTime

@Parcelize
data class Repository(
    val name: String,
    val ownerId: String,
    val description: String,
    private val updatedAt: String,
    val stargazersCount: Int,
    val forks: Int
): Parcelable {
    /**
     * returns the LocalDateTime object from the updatedAt string date
     */
    fun updatedAtDate(): LocalDateTime? = try {
        updatedAt.isoZonedDateToLocalDateTime()
    } catch (e: Exception) {
        null
    }

    /**
     * reformats the date to make it human readable
     */
    fun updatedAtDateFormatted(): String = try {
        updatedAt.isoZonedDateToLocalDateTime().toFormattedString()
    } catch (e: Exception) {
        ERROR_STRING
    }

    companion object {
        val emptyRepository = Repository(
            name = "",
            ownerId = "",
            description = "",
            updatedAt = "",
            stargazersCount = 0,
            forks = 0
        )
    }
}
