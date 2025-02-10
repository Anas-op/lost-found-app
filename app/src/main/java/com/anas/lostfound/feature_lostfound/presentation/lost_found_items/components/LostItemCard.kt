package com.anas.lostfound.feature_lostfound.presentation.lost_found_items.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.anas.lostfound.R
import com.anas.lostfound.core.util.CardStrings
import com.anas.lostfound.core.util.ContentDescriptions
import com.anas.lostfound.core.util.InputFormStrings
import com.anas.lostfound.core.util.getDateTime
import com.anas.lostfound.feature_lostfound.domain.model.Item
import com.example.compose.LostFoundTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LostItemCard(
    lostItem: Item,
    onCardClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(10.dp)
            ),
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.onPrimary),
        onClick = onCardClick
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Image section
            Image(
                painter = rememberAsyncImagePainter(lostItem.imagePath),
                contentDescription = ContentDescriptions.PHOTO_ITEM,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            // Text and buttons section
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(
                    text = lostItem.title.uppercase(Locale.ROOT),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp) ,
                    maxLines = 1
                )

                Text(
                    text = "${CardStrings.CATEGORY} ${lostItem.category}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1

                )
                Text(
                    text = "${CardStrings.LOCATION} ${lostItem.location}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                )

                Text(
                    text = "${CardStrings.LOST_DATE} ${getDateTime(lostItem.timestamp)}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1
                )
            }
        }
    }
}




@Preview
@Composable
fun LostItemCardPreview(){
    LostFoundTheme {
        LostItemCard(
            Item(
                title = "Lost Wallet",
                description = "A black leather wallet containing ID and credit card assas",
                category = "Abbigliamento",
                uid = "pippocaio",
                timestamp = 11234566,
                imagePath = "",
                found = false,
                location = "Milan, Italy",
                id = 0,
                contact = "+39 3213213213",
                email = "pippo@hotmail.it",
                lost = true,
                latitude = 0.0,
                longitude = 0.0
            ),
            onCardClick = {},

        )
    }
}