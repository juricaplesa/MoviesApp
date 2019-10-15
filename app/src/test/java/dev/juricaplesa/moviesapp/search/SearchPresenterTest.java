package dev.juricaplesa.moviesapp.search;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;
import dev.juricaplesa.moviesapp.JsonResource;
import dev.juricaplesa.moviesapp.api.MoviesApi;
import dev.juricaplesa.moviesapp.api.SearchResponse;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

/**
 * Created by Jurica Pleša
 */
public class SearchPresenterTest {

    private static final String EMPTY_INPUT = "";
    private static final String SHORT_INPUT = "In";
    private static final String VALID_INPUT = "Inception";

    @Mock
    private SearchContract.View mSearchView;
    @Mock
    private MoviesApi mMoviesApi;

    private SearchPresenter mSearchPresenter;
    private SearchResponse mSuccessfulSearchResponse;
    private SearchResponse mErrorSearchResponse;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(mSearchView.isActive()).thenReturn(true);

        String successfulResponse = JsonResource.INSTANCE.fromResource("search_response_success.json");
        String errorResponse = JsonResource.INSTANCE.fromResource("search_response_error.json");
        Moshi moshi = new Moshi.Builder().add(new KotlinJsonAdapterFactory()).build();
        JsonAdapter<SearchResponse> jsonAdapter = moshi.adapter(SearchResponse.class);
        mSuccessfulSearchResponse = jsonAdapter.fromJson(successfulResponse);
        mErrorSearchResponse = jsonAdapter.fromJson(errorResponse);

        mSearchPresenter = new SearchPresenter(mMoviesApi, Schedulers.trampoline(), Schedulers.trampoline());
        mSearchPresenter.injectView(mSearchView);
    }

    @Test
    public void searchMovies_emptyInput() {
        mSearchPresenter.searchMovies(EMPTY_INPUT);

        verify(mSearchView).clearMovies();
        verify(mSearchView).showInitialMessage();

        verify(mMoviesApi, never()).searchMovies(EMPTY_INPUT);
    }

    @Test
    public void searchMovies_shortInput() {
        mSearchPresenter.searchMovies(SHORT_INPUT);

        verify(mSearchView).clearMovies();
        verify(mSearchView).showInitialMessage();

        verify(mMoviesApi, never()).searchMovies(EMPTY_INPUT);
    }

    @Test
    public void searchMovies_validInput_successfulRequest_successfulResponse() {
        when(mMoviesApi.searchMovies(VALID_INPUT)).thenReturn(Observable.just(mSuccessfulSearchResponse));

        mSearchPresenter.searchMovies(VALID_INPUT);

        verify(mSearchView).setMovies(mSuccessfulSearchResponse.getData());
        verify(mSearchView).hideLoadingIndicator();
    }

    @Test
    public void searchMovies_validInput_successfulRequest_errorResponse() {
        when(mMoviesApi.searchMovies(VALID_INPUT)).thenReturn(Observable.just(mErrorSearchResponse));

        mSearchPresenter.searchMovies(VALID_INPUT);

        verify(mSearchView).clearMovies();
        verify(mSearchView).showEmptyMessage();
        verify(mSearchView).hideLoadingIndicator();
    }

    @Test
    public void searchMovies_validInput_errorRequest() {
        when(mMoviesApi.searchMovies(VALID_INPUT)).thenReturn(Observable.<SearchResponse>error(new Throwable()));

        mSearchPresenter.searchMovies(VALID_INPUT);

        verify(mSearchView).showErrorMessage();
        verify(mSearchView).hideLoadingIndicator();
    }

    @After
    public void validate() {
        validateMockitoUsage();
    }

}
