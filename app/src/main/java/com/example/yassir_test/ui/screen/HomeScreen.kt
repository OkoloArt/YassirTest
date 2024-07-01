package com.example.yassir_test.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.swiftcart.utils.AuthResult
import com.example.yassir_test.R
import com.example.yassir_test.data.model.Movie
import com.example.yassir_test.navigation.Screen
import com.example.yassir_test.ui.component.AnimatedShimmer
import com.example.yassir_test.ui.theme.fredricka
import com.example.yassir_test.ui.theme.mate_italic
import com.example.yassir_test.ui.theme.tangerine_Bold
import com.example.yassir_test.viewmodel.MovieViewModel

@Composable
fun HomeScreen(
    movieViewModel: MovieViewModel,
    navController: NavController
) {
    var movieResult by remember {
        mutableStateOf<List<Movie>>(emptyList())
    }
    var isLoading by remember {
        mutableStateOf(true)
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = movieViewModel) {
        movieViewModel.movie.collect { result ->
            when (result) {
                is AuthResult.Error -> {
                    Toast.makeText(context, result.error.message, Toast.LENGTH_SHORT).show()
                }

                AuthResult.Loading -> {
                    isLoading = true
                }
                is AuthResult.Success -> {
                    isLoading = false
                    movieResult = result.data.results
                }
            }
        }
    }

    Column(
        modifier = Modifier.padding(12.dp)
    ) {
        Text(
            text = "Movies",
            fontFamily = fredricka,
            fontSize = MaterialTheme.typography.headlineMedium.fontSize
        )
        Spacer(modifier = Modifier.height(15.dp))

        if (isLoading){
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy((20.dp)),
                content = {
                    items(5) {index ->
                        AnimatedShimmer()
                    }
                }
            )
        }else {
            MovieList(
                result = movieResult,
                navigateToDetail = {
                    navController.navigate(Screen.Details.route)
                },
                movieViewModel = movieViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieItem(
    movie: Movie,
    modifier: Modifier = Modifier,
    navigateToDetail: () -> Unit,
    movieViewModel: MovieViewModel
) {
    Card(
        onClick = {
            movieViewModel.setMovieId(movie.id)
            navigateToDetail()
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.broken),
                contentDescription = "movieImage",
                modifier = Modifier
                    .size(100.dp)
                    .clip(shape = RoundedCornerShape(14.dp)),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = movie.originalTitle,
                    fontFamily = tangerine_Bold,
                    fontSize = 22.sp,
                    fontWeight = FontWeight(900)
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = extractYearFromDate(movie.releaseDate),
                    fontFamily = mate_italic,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400)
                )
            }
        }
    }
}

@Composable
fun MovieList(
    result: List<Movie>,
    modifier: Modifier = Modifier,
    navigateToDetail: () -> Unit,
    movieViewModel: MovieViewModel
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(result.size) { index ->
            MovieItem(
                movie = result[index],
                navigateToDetail = navigateToDetail,
                movieViewModel = movieViewModel
            )
        }
    }
}


//@Composable
//@Preview
//fun PreviewComposable(){
//    Yassir_TestTheme {
//        MovieItem()
//    }
//}

fun extractYearFromDate(dateString: String): String {
    return dateString.substring(0, 4)
}