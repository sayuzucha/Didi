package com.example.didi.features.history.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.didi.features.history.domain.entities.RideHistoryItem

@Composable
fun HistoryCard(
    item: RideHistoryItem,
    modifier: Modifier = Modifier
) {
    val primaryBlue = Color(0xFF2962FF)
    val textBlack = Color(0xFF1B1B1B)
    val textGray = Color(0xFF6F6F6F)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono con el azul del Home
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = primaryBlue.copy(alpha = 0.1f),
                modifier = Modifier.size(52.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.DirectionsCar,
                        contentDescription = null,
                        tint = primaryBlue
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Viaje #${item.rideId.takeLast(6).uppercase()}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = textBlack
                )
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = textGray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = item.date,
                        style = MaterialTheme.typography.bodySmall,
                        color = textGray,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))
                
                // Badge de estado
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (item.status.lowercase() == "completed") Color(0xFFE8F5E9) else Color(0xFFF5F5F5),
                    modifier = Modifier.wrapContentSize()
                ) {
                    Text(
                        text = item.status.uppercase(),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (item.status.lowercase() == "completed") Color(0xFF2E7D32) else textGray,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Precio con azul primario
            Text(
                text = "$${String.format("%.2f", item.totalPaid)}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = primaryBlue,
                fontSize = 20.sp
            )
        }
    }
}
