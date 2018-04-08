package com.twt.service.schedule2.model.school

import com.twt.service.schedule2.model.Classtable
import com.twt.wepeiyang.commons.experimental.cache.Cache
import com.twt.wepeiyang.commons.experimental.cache.hawk
import com.twt.wepeiyang.commons.experimental.extensions.awaitAndHandle
import com.twt.wepeiyang.commons.experimental.network.CommonBody
import com.twt.wepeiyang.commons.experimental.network.ServiceFactory
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import retrofit2.http.GET

interface TjuCourseApi {

    @GET("v1/classtable")
    fun getClassTable(): Deferred<CommonBody<Classtable>>

    companion object : TjuCourseApi by ServiceFactory()

}

val tjuCourseCache = Cache.hawk<Classtable>("schedule2_tju_classtable")

/**
 * 获取TJU课程表的逻辑
 * 有缓存的时候优先获取缓存 并且异步刷新
 * 强制刷新的时候 网络错误返回缓存 刷新成功则同时刷写缓存
 */
suspend fun TjuCourseApi.Companion.refresh(mustRefresh: Boolean = false): Classtable {
    val deferredClasstable = getClassTable()
    val handler: suspend (Throwable) -> Unit = { it.printStackTrace() }
    // 要么是必须刷新 要么是没有缓存
    if (mustRefresh || tjuCourseCache.get().await() == null) {
        // 刷新失败就拿缓存 缓存还没有就凉了
        val classtable: Classtable? = deferredClasstable.awaitAndHandle(handler)?.data
                ?: tjuCourseCache.get().await()
        classtable?.let {
            tjuCourseCache.set(it)
        }
        return classtable ?: throw IllegalStateException("凉了啊...无法获取课程表,稍后再试")
    } else {
        // 这种情况也要静默刷新一下 成功就刷
        async(CommonPool) {
            val classtable = deferredClasstable.awaitAndHandle(handler)?.data
            classtable?.let {
                tjuCourseCache.set(it)
            }
        }
        // 到这里的话 缓存不应该是空 如果真的是空我们只能抛出异常（我去你妈的吧）
        return tjuCourseCache.get().await() ?: throw IllegalStateException("课程表缓存系统问题。")
    }
}