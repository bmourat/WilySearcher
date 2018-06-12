package ru.bmourat.wilysearcher.domain.interactor

import com.twitter.sdk.android.core.models.Tweet
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.bmourat.wilysearcher.domain.interactor.type.SingleUseCaseWithParameters
import ru.bmourat.wilysearcher.domain.repository.TweetsRepository

class SwipeToRefreshUseCase(
        private val onlineRepository: TweetsRepository)
    : SingleUseCaseWithParameters<String, Long, List<Tweet>> {

    override fun execute(hashTag: String, sinceId: Long): Single<List<Tweet>> {
        return onlineRepository
                .loadTweets(hashTag, sinceId = sinceId)
                .observeOn(AndroidSchedulers.mainThread())
    }
}
