package org.scotia.githubfetcher.common

/**
 * used for network response, those should be emitted by a Flow
 * @see GithubDataRepositoryImpl
 */
sealed class Resource<T>(
    val data: T? = null,
    val exception: Throwable? = null,
    val message: String? = null
) {
    /**
     * Flow will emit this on success, along with the requested data
     */
    class Success<T>(data: T) : Resource<T>(data)

    /**
     * Flow will emit this, in case of errors. Remember to stop any loading indicator, since
     * Loading(false) will most likely not be called.
     * Optionally partial data can be sent too.
     */
    class Error<T>(
        data: T? = null,
        exception: Throwable,
        message: String = exception.stackTraceToString()
    ) : Resource<T>(data = data, message = message, exception = exception)

    /**
     * Flow will emit this, with a true value, at the beginning and again with false value at the
     * end. This way the UI knows exactly when the process of loading data starts and ends
     */
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>()
}
