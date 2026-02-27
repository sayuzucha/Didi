package com.example.didi.features.home.presentation.components


import android.view.MotionEvent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay

@Composable
fun OsmRidePickerMap(
    modifier: Modifier = Modifier,
    origin: GeoPoint?,
    destination: GeoPoint?,
    pickingOrigin: Boolean,
    onPick: (GeoPoint) -> Unit,
) {
    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            Configuration.getInstance().userAgentValue = ctx.packageName

            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)

                controller.setZoom(18.0)
                controller.setCenter(origin ?: GeoPoint(19.4326, -99.1332))

                val rotation = RotationGestureOverlay(this)
                rotation.isEnabled = true
                overlays.add(rotation)

                overlays.add(object : Overlay() {
                    override fun onSingleTapConfirmed(e: MotionEvent?, mapView: MapView?): Boolean {
                        if (e == null || mapView == null) return false
                        val geo = mapView.projection.fromPixels(e.x.toInt(), e.y.toInt()) as GeoPoint
                        onPick(geo)
                        return true
                    }
                })
            }
        },
        update = { map ->
            map.overlays.removeAll { it is Marker }

            origin?.let {
                Marker(map).apply {
                    position = it
                    title = "Origen"
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    map.overlays.add(this)
                }
            }

            destination?.let {
                Marker(map).apply {
                    position = it
                    title = "Destino"
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    map.overlays.add(this)
                }
            }

            val center = if (pickingOrigin) origin else destination
            center?.let { map.controller.animateTo(it) }

            map.invalidate()
        }
    )
}