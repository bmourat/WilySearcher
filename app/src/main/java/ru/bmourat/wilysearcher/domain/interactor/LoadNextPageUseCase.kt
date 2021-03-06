package ru.bmourat.wilysearcher.domain.interactor

import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.bmourat.wilysearcher.domain.interactor.type.SingleUseCaseWithParameters
import ru.bmourat.wilysearcher.domain.repository.TweetsRepository

// Class is marked open for testing
open class LoadNextPageUseCase(
        private val onlineRepository: TweetsRepository,
        private val observerScheduler: Scheduler)
    : SingleUseCaseWithParameters<String, Tweet?, List<Tweet>> {

    override fun execute(hashTag: String, oldestTweet: Tweet?): Single<List<Tweet>> {
        return onlineRepository
                .loadTweets(hashTag, oldestTweet?.id)
                .observeOn(observerScheduler)
    }
}
