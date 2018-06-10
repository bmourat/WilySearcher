package ru.bmourat.wilysearcher.data.api

import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.models.Search
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.core.services.SearchService
import io.reactivex.Single
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class TwitterSdkApi: TwitterApi {

    private val searchService: SearchService = TwitterCore.getInstance().apiClient.searchService

    override fun loadTweets(hashTag: String, count: Int, sinceId: Long?, maxId: Long?): Single<List<Tweet>> {
        val hashTagEncoded = URLEncoder.encode(hashTag, StandardCharsets.UTF_8.toString())
        val searchCall = searchService.tweets(hashTagEncoded, null, null, null, null,
                count, null, sinceId, maxId, null)
        return Single.create { emitter ->
            searchCall.enqueue(object: Callback<Search>(){

                override fun success(result: Result<Search>?) {
                    result?.data?.tweets?.let {
                        emitter.onSuccess(it.filterNotNull())
                    }
                }

                override fun failure(exception: TwitterException?) {
                    exception?.let {
                        emitter.onError(it)
                    }
                }

            })
        }
    }
}
