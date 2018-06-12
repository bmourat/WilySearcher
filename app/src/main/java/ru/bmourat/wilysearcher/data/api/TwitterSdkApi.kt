package ru.bmourat.wilysearcher.data.api

import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Search
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.services.SearchService
import io.reactivex.Single
import ru.bmourat.wilysearcher.app.common.logger.Logger
import ru.bmourat.wilysearcher.app.common.logger.logTag
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class TwitterSdkApi(private val logger: Logger): TwitterApi {

    private val searchService: SearchService = TwitterCore.getInstance().apiClient.searchService

    override fun loadTweets(hashTag: String, count: Int, sinceId: Long?, maxId: Long?): Single<List<Tweet>> {
        val hashTagEncoded = URLEncoder.encode(hashTag, StandardCharsets.UTF_8.toString())
        val searchCall = searchService.tweets(hashTagEncoded, null, null, null, "recent",
                count, null, sinceId, maxId, null)
        return Single.create { emitter ->
            logger.verbose(logTag,  "Sending request to ${searchCall.request().url()}")
            logger.verbose(logTag, "Headers: ${searchCall.request().headers()}")
            logger.verbose(logTag, "Body: ${searchCall.request().body()}")

            searchCall.enqueue(object: Callback<Search>(){

                override fun success(result: Result<Search>?) {
                    logger.verbose(TwitterSdkApi@logTag, "Request succeeded with code ${result?.response?.code()}")
                    result?.data?.tweets?.let {
                        emitter.onSuccess(it.filterNotNull())
                    }
                }

                override fun failure(exception: TwitterException?) {
                    logger.verbose(TwitterSdkApi@logTag, "Request failed")
                    exception?.let {
                        emitter.onError(it)
                    }
                }

            })
        }
    }
}
