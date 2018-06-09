package ru.bmourat.wilysearcher.domain.interactor

import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.bmourat.wilysearcher.data.tweet.LocalTweetsRepository
import ru.bmourat.wilysearcher.data.tweet.OnlineTweetsRepository
import ru.bmourat.wilysearcher.domain.interactor.type.UseCaseWithParameters

class LoadRecentTweetsUseCase(
        private val onlineRepository: OnlineTweetsRepository,
        private val localRepository: LocalTweetsRepository)
    : UseCaseWithParameters<String, Boolean, List<Tweet>> {

    override fun execute(hashTag: String, includeCache: Boolean): Observable<List<Tweet>> {
        if (includeCache) {
            return Observable
                    .concat(localRepository.loadTweets(hashTag), onlineRepository.loadTweets(hashTag))
                    .observeOn(AndroidSchedulers.mainThread())
        }
        return onlineRepository.loadTweets(hashTag).observeOn(AndroidSchedulers.mainThread())
    }
}
