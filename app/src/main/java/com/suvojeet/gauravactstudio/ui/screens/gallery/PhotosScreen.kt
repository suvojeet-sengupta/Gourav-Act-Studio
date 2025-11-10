package com.suvojeet.gauravactstudio.ui.screens.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.suvojeet.gauravactstudio.ui.model.PortfolioItem
import com.suvojeet.gauravactstudio.ui.screens.gallery.PortfolioCard

@Composable
fun PhotosScreen(navController: NavController, modifier: Modifier = Modifier) {
    val portfolioItems = listOf(
        PortfolioItem("Pre-wedding", "https://lh3.googleusercontent.com/pw/AP1GczNoNn9GeQvUIdclpmWPH-1z12Doisij77OnM1W4VBCtrA1aYzSc6cqThuU6Bt-gr0Hs9cMssVk1mYqLgJuUh0ThhndADvwwJUqF9Ov8HOmuJ-fsvVNXRsLxS8KbcSXRhm2jIHkeHpQ6DjpOBiD9pY458Q=w700-h466-s-no-gm?authuser=0"),
        PortfolioItem("Birthday", "https://lh3.googleusercontent.com/pw/AP1GczMvWUbuMW9A3HO43sP3KjxgarFqDKQxwz2aNRPyPGxdZV80nvYsP13jP4_S1KBKFLYQak5ZPbXEODV2pGlkS4Vb5l9Ov9I5hwJJtvufpf9I6O9a107-5wwZx_oc2YPSnNwxjQsKrhkgx_poQp5Z86iBYA=w1280-h853-s-no-gm?authuser=0"),
        PortfolioItem("Baby Shoot", "https://lh3.googleusercontent.com/pw/AP1GczMiTuKH7iVfYSYQSHJlan_LgL8cyVt-GfgjvzjkWE-e2q4N0xctRD4UIlMaW4ssf8RthnB_W9FFeZzbcNO0XRGQbfvDpXs6PkxyEZKohPpKkmxzT1CTfpb1OSnAXB98Wn6AqGlfEmjtSup28dZTtlQhPA=w1024-h683-s-no-gm?authuser=0"),
        PortfolioItem("Baby Shoot", "https://lh3.googleusercontent.com/pw/AP1GczNlECP759-_slbJhEi1br7Occb7fOkMkxNQpOg4OP7CEP9-Kt4t4NWoZ1CP9IE1OphbK1MBrXRvODRjUezRp3w4yMjQctIdP-pttKOtNlKMljm2-nh4cbN1dw_Pv1tfzf0BPS-hNsA1HAHmGnJwg5RJPw=w853-h1280-s-no-gm?authuser=0"),
        PortfolioItem("Baby Shoot", "https://lh3.googleusercontent.com/pw/AP1GczN9QPJB3fIn8pGGAyjtI-wfSAAA2dMoQsnOng0qcTAqdwy30k0LzU2ySQWneD1D2vNb5oIPWQuE84sKhqJ9yUWlaalwi1NTaV3-Pp-mO7DpZ04-vK2uolMHrvFPuqbCyj6AIm53z5LaFvxRHZX3rq11Gg=w683-h1024-s-no-gm?authuser=0"),
        PortfolioItem("Product Photography", "https://lh3.googleusercontent.com/pw/AP1GczN4OHR2tGmJ7T1ADMSyYqbAly10ZmIhnhkJ4vwYlnYiUq8DQhPsbCesTQus6SiEVvfg8Puk8MydH-qVxJTqO7VpxvxoIxbpWTEQDlDhUnCxfd9FbSomFN3BADuboxRbdoGzXNr6UZFFKsEeYyWJ2lgcwA=w736-h919-s-no-gm?authuser=0")
    )
    val categories = portfolioItems.groupBy { it.title }.map { (title, items) ->
        PortfolioItem(title, items.first().imageUrl)
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(top = 16.dp)
    ) {
        items(categories) { category ->
            PortfolioCard(item = category, navController = navController, isCategory = true)
        }
    }
}

// PortfolioCard and shimmerEffect have been moved to CategoryPhotosScreen.kt
