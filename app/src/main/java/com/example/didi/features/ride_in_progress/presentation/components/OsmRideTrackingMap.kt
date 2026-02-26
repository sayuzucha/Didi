package com.example.didi.features.ride_in_progress.presentation.components

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.didi.R
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun OsmRideTrackingMap(
    modifier: Modifier,
    origin: GeoPoint,
    destination: GeoPoint,
    carLocation: GeoPoint?
) {
    AndroidView(
        modifier = modifier,
        factory = { ctx: Context ->
            Configuration.getInstance().userAgentValue = ctx.packageName

            fun scaledCarDrawableForZoom(zoom: Double): Drawable {
                val d = requireNotNull(ctx.getDrawable(R.drawable.ic_car)).mutate()

                // Ajusta estos números a tu gusto:
                // - scaleMin: tamaño mínimo cuando estás lejos
                // - scaleMax: tamaño máximo cuando estás cerca
                val z = zoom.toFloat()
                val scaleMin = 0.08f
                val scaleMax = 0.22f

                // Fórmula simple: aumenta con el zoom, con límites
                val scale = (0.02f + z * 0.01f).coerceIn(scaleMin, scaleMax)

                val w = (d.intrinsicWidth * scale).toInt().coerceAtLeast(24)
                val h = (d.intrinsicHeight * scale).toInt().coerceAtLeast(24)
                d.setBounds(0, 0, w, h)
                return d
            }

            val map = MapView(ctx).apply {
                setMultiTouchControls(true)
                controller.setZoom(16.0)
                controller.setCenter(origin)
            }

            val originMarker = Marker(map).apply {
                position = origin
                title = "Origen"
            }

            val destMarker = Marker(map).apply {
                position = destination
                title = "Destino"
            }

            val carMarker = Marker(map).apply {
                position = carLocation ?: origin
                title = "Carro"
                icon = scaledCarDrawableForZoom(map.zoomLevelDouble) // ✅ tamaño inicial
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
            }

            map.addMapListener(object : MapListener {
                override fun onZoom(event: ZoomEvent?): Boolean {
                    carMarker.icon = scaledCarDrawableForZoom(map.zoomLevelDouble)
                    map.invalidate()
                    return true
                }

                override fun onScroll(event: ScrollEvent?): Boolean = false
            })

            map.overlays.add(originMarker)
            map.overlays.add(destMarker)
            map.overlays.add(carMarker)

            map.tag = carMarker
            map
        },
        update = { map ->
            val carMarker = map.tag as? Marker ?: return@AndroidView
            if (carLocation != null) {
                carMarker.position = carLocation
                map.controller.animateTo(carLocation)
                map.invalidate()
            }
        }
    )
}