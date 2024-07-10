package com.example.darckoum.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.darckoum.api.AnnouncementService
import com.example.darckoum.data.model.Announcement

class SearchPagingSource(
    private val announcementService: AnnouncementService,
    private val token: String,
    private val query: String
) : PagingSource<Int, Announcement>() {
    val tag = "DiscoverPagingSource"
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Announcement> {
        val currentPage = params.key ?: 0
        return try {
            val response = announcementService.searchAnnouncements(
                token = token,
                currentPage = currentPage,
                query = query
            )
            Log.d(tag, "response: ${response.content}")
            val endOfPaginationReached = response.content.isEmpty()
            if (response.content.isNotEmpty()) {
                LoadResult.Page(
                    data = response.content,
                    prevKey = if (currentPage == 0) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Announcement>): Int? {
        return state.anchorPosition
    }

}