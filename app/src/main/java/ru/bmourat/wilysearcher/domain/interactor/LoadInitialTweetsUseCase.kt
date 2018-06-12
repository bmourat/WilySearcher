package ru.bmourat.wilysearcher.domain.interactor

import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.bmourat.wilysearcher.domain.interactor.type.UseCaseWithParameters
import ru.bmourat.wilysearcher.domain.repository.TweetsRepository

class LoadInitialTweetsUseCase(
        private val onlineRepository: TweetsRepository,
        private val localRepository: TweetsRepository)
    : UseCaseWithParameters<String, Boolean, List<Tweet>> {

    override fun execute(hashTag: String, includeCache: Boolean): Observable<List<Tweet>> {
        if (includeCache) {
            return Observable
                    .concat(localRepository.loadTweets(hashTag).toObservable(),
                            onlineRepository
                                .loadTweets(hashTag)
                                .doAfterSuccess{ localRepository.saveTweets(hashTag, it) }
                                .toObservable())
                    .observeOn(AndroidSchedulers.mainThread())
        }
        return onlineRepository.loadTweets(hashTag).toObservable().observeOn(AndroidSchedulers.mainThread())
    }
}
