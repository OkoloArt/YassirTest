package com.example.yassir_test.ui.screen

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.swiftcart.utils.AuthResult
import com.example.yassir_test.R
import com.example.yassir_test.data.model.MovieDetailResponse
import com.example.yassir_test.ui.theme.courgetteFontFamily
import com.example.yassir_test.ui.theme.mate_italic
import com.example.yassir_test.ui.theme.tangerine_Bold
import com.example.yassir_test.viewmodel.MovieViewModel


@Composable
fun DetailScreen(
    movieViewModel: MovieViewModel
) {

    var movie by remember {
        mutableStateOf<MovieDetailResponse?>(null)
    }
    var isLoading by remember {
        mutableStateOf(true)
    }

    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        // Observe movieId LiveData to trigger actions
        val movieId by movieViewModel.movieId.observeAsState(initial = 0)

        LaunchedEffect(key1 = movieId) {
            movieViewModel.getMovieDetails(movieId)
        }

        val movieDetailResult by movieViewModel.movieDetail.collectAsState()

        when (val result = movieDetailResult) {
            is AuthResult.Error -> {
                Toast.makeText(context, result.error.message, Toast.LENGTH_SHORT).show()
            }

            AuthResult.Loading -> {
                isLoading = true
            }

            is AuthResult.Success -> {
                isLoading = false
                movie = result.data
            }
        }

        if (isLoading){
            LoadingIndicator(
                loadingVisibility = isLoading,
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        }else{
            MovieDetail(movie = movie!!)
        }
    }
}

@Composable
fun LoadingIndicator(loadingVisibility: Boolean, modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.width(64.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.primary,
    )
}

@Composable
fun MovieDetail(movie: MovieDetailResponse) {
    Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = "movieImage",
                    modifier = Modifier
                        .height(300.dp)
                        .width(200.dp)
                        .clip(shape = RoundedCornerShape(14.dp))
                        .align(alignment = Alignment.CenterHorizontally),
                    contentScale = ContentScale.FillBounds
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = movie.originalTitle,
                            fontFamily = tangerine_Bold,
                            fontSize = 30.sp,
                            fontWeight = FontWeight(700),
                        )
                        Text(
                            text = extractYearFromDate(movie.releaseDate),
                            fontFamily = mate_italic,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(400)
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = String.format("%.1f", movie.voteAverage),
                        fontFamily = FontFamily.Cursive,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                        modifier = Modifier.border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = CircleShape
                        ).padding(10.dp)
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = movie.overview,
                    fontFamily = courgetteFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                )
            }
    }
}

