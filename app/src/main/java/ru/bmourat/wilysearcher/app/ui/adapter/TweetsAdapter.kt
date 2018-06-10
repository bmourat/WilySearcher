package ru.bmourat.wilysearcher.app.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.CompactTweetView
import kotlinx.android.synthetic.main.layout_tweet_list_item.view.*
import ru.bmourat.wilysearcher.R

class TweetsAdapter: RecyclerView.Adapter<TweetsAdapter.TweetHolder>() {

    var tweets = mutableListOf<Tweet>()

    fun replaceTweets(tweetList: List<Tweet>) {
        tweets = tweetList.toMutableList()
        notifyDataSetChanged()
    }

    fun addTweets(tweetList: List<Tweet>) {
        tweets.addAll(tweetList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = tweets.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_tweet_list_item, parent, false)
        return TweetHolder(v)
    }

    override fun onBindViewHolder(holder: TweetHolder, position: Int) {
        holder.bind(tweets[position])
    }

    class TweetHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(tweet: Tweet) {
            if (itemView.flRoot.childCount > 0) {
                val tweetView = itemView.flRoot.getChildAt(0) as? CompactTweetView
                tweetView?.tweet = tweet
            } else {
                itemView.flRoot.addView(CompactTweetView(itemView.context, tweet))
            }
        }
    }
}
