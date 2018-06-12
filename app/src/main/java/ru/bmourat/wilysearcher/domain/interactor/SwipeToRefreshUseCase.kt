package ru.bmourat.wilysearcher.domain.interactor

import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.bmourat.wilysearcher.domain.interactor.type.SingleUseCaseWithParameters
import ru.bmourat.wilysearcher.domain.repository.TweetsRepository

class SwipeToRefreshUseCase(
        private val onlineRepository: TweetsRepository)
    : SingleUseCaseWithParameters<String, Tweet?, List<Tweet>> {

    override fun execute(hashTag: String, latestTweet: Tweet?): Single<List<Tweet>> {
        return onlineRepository
                .loadTweets(hashTag, sinceId = latestTweet?.id)
                .observeOn(AndroidSchedulers.mainThread())
    }
}
