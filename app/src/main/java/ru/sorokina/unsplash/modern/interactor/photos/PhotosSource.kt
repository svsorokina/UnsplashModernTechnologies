package ru.sorokina.unsplash.modern.interactor.photos

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.sorokina.unsplash.modern.domain.Photo

class PhotosSource(
    private val photosInteractor: PhotosInteractor
) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val nextPage = params.key ?: 1
            val photos = photosInteractor.getPhotos(nextPage)

            LoadResult.Page(
                data = photos,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (photos.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }
}