package ru.bmourat.wilysearcher

import com.twitter.sdk.android.core.models.Tweet

fun tweetWithId(id: Long): Tweet {
    return Tweet(null, null, null, null, null,
            null, false, null, id, null, null,
            0, null, 0,null,null,null,
            false,null,0,null,null,0,
            false,null,null,null,null,false,null,
            false, null,null,null)
}
