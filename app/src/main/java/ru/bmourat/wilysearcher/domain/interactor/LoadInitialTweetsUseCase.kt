package ru.bmourat.wilysearcher.domain.interactor

import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.bmourat.wilysearcher.domain.interactor.type.UseCaseWithParameters
import ru.bmourat.wilysearcher.domain.repository.TweetsRepository

// Class is marked open for testing
open class LoadInitialTweetsUseCase(
        private val onlineRepository: TweetsRepository,
        private val localRepository: TweetsRepository,
        private val observerScheduler: Scheduler)
    : UseCaseWithParameters<String, Boolean, List<Tweet>> {

    override fun execute(hashTag: String, includeCache: Boolean): Observable<List<Tweet>> {
        if (includeCache) {
            return Observable
                    .concat(localRepository.loadTweets(hashTag).toObservable(),
                            onlineRepository
                                .loadTweets(hashTag)
                                .doAfterSuccess{ localRepository.saveTweets(hashTag, it) }
                                .toObservable())
                    .observeOn(observerScheduler)
        }
        return onlineRepository.loadTweets(hashTag).toObservable().observeOn(observerScheduler)
    }
}
