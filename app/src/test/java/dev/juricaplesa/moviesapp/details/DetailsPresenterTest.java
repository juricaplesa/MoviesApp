package dev.juricaplesa.moviesapp.details;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory;
import dev.juricaplesa.moviesapp.JsonResource;
import dev.juricaplesa.moviesapp.api.ApiProvider;
import dev.juricaplesa.moviesapp.api.MovieDetailsResponse;
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
public class DetailsPresenterTest {

    private static final String VALID_IMDB_ID = "tt1375666";
    private static final String INVALID_IMDB_ID = "";

    @Mock
    private DetailsContract.View mDetailsView;
    @Mock
    private ApiProvider mApiProvider;

    private DetailsPresenter mDetailsPresenter;
    private MovieDetailsResponse mSuccessfulMovieDetailsResponse;
    private MovieDetailsResponse mErrorMovieDetailsResponse;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(mDetailsView.isActive()).thenReturn(true);

        String successfulResponse = JsonResource.INSTANCE.fromResource("details_response_success.json");
        String errorResponse = JsonResource.INSTANCE.fromResource("details_response_error.json");
        Moshi moshi = new Moshi.Builder().add(new KotlinJsonAdapterFactory()).build();
        JsonAdapter<MovieDetailsResponse> jsonAdapter = moshi.adapter(MovieDetailsResponse.class);
        mSuccessfulMovieDetailsResponse = jsonAdapter.fromJson(successfulResponse);
        mErrorMovieDetailsResponse = jsonAdapter.fromJson(errorResponse);

        mDetailsPresenter = new DetailsPresenter(mApiProvider, Schedulers.trampoline(), Schedulers.trampoline());
        mDetailsPresenter.injectView(mDetailsView);
    }

    @Test
    public void getMovieDetails_invalidImdbId() {
        mDetailsPresenter.getMovieDetails(INVALID_IMDB_ID);

        verify(mDetailsView).showErrorMessage();

        verify(mDetailsView, never()).hideLoadingIndicator();
        verify(mApiProvider, never()).getMovieDetails(INVALID_IMDB_ID);
    }

    @Test
    public void getMovieDetails_validImdbId_successfulRequest_successfulResponse() {
        when(mApiProvider.getMovieDetails(VALID_IMDB_ID)).thenReturn(Observable.just(mSuccessfulMovieDetailsResponse));

        mDetailsPresenter.getMovieDetails(VALID_IMDB_ID);

        verify(mDetailsView).hideLoadingIndicator();

        verify(mDetailsView).displayTitle(mSuccessfulMovieDetailsResponse.getTitle());
        verify(mDetailsView).displayYear(mSuccessfulMovieDetailsResponse.getYear());
        verify(mDetailsView).displayGenre(mSuccessfulMovieDetailsResponse.getGenre());
        verify(mDetailsView).displayDirector(mSuccessfulMovieDetailsResponse.getDirector());
        verify(mDetailsView).displayActors(mSuccessfulMovieDetailsResponse.getActors());
        verify(mDetailsView).displayPlot(mSuccessfulMovieDetailsResponse.getPlot());
        verify(mDetailsView).displayPoster(mSuccessfulMovieDetailsResponse.getPoster());
    }

    @Test
    public void getMovieDetails_validImdbId_successfulRequest_errorResponse() {
        when(mApiProvider.getMovieDetails(VALID_IMDB_ID)).thenReturn(Observable.just(mErrorMovieDetailsResponse));

        mDetailsPresenter.getMovieDetails(VALID_IMDB_ID);

        verify(mDetailsView).showErrorMessage();
        verify(mDetailsView).hideLoadingIndicator();
    }

    @Test
    public void getMovieDetails_validImdbId_errorRequest() {
        when(mApiProvider.getMovieDetails(VALID_IMDB_ID)).thenReturn(Observable.<MovieDetailsResponse>error(new Throwable()));

        mDetailsPresenter.getMovieDetails(VALID_IMDB_ID);

        verify(mDetailsView).showErrorMessage();
        verify(mDetailsView).hideLoadingIndicator();
    }

    @After
    public void validate() {
        validateMockitoUsage();
    }

}
