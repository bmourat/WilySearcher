package ru.bmourat.wilysearcher.app.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Search
import ru.bmourat.wilysearcher.R
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tag = URLEncoder.encode("#fifa2018", StandardCharsets.UTF_8.toString())
        TwitterCore.getInstance().apiClient.searchService
                .tweets(tag, null, null, null, null, null,
                        null, null, null, null)
                .enqueue(object: Callback<Search>(){
                    override fun success(result: Result<Search>?) {
                        result?.data?.tweets?.forEach {
                            Log.d("Tag", it.text)
                        }
                    }

                    override fun failure(exception: TwitterException?) {
                        Log.e("Tag", "Error retrieving tweets", exception)
                    }

                })
    }
}
