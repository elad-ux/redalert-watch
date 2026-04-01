package com.redalert.watch

import android.content.Context
import androidx.wear.tiles.LayoutElementBuilders
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.ResourceBuilders
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.TileService
import androidx.wear.tiles.TimelineBuilders
import androidx.wear.tiles.DimensionBuilders
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture

/**
 * AlertTileService – אריח שמוצג על פרצוף השעון.
 *
 * מציג מצב ממתין / פעיל ישירות על פרצוף השעון.
 * המשתמש מוסיף אותו ב:
 *   לחיצה ארוכה על פרצוף → Tiles → + → "צבע אדום"
 */
class AlertTileService : TileService() {

    override fun onTileRequest(
        requestParams: RequestBuilders.TileRequest
    ): ListenableFuture<TileBuilders.Tile> =
        Futures.immediateFuture(buildTile(this))

    override fun onResourcesRequest(
        requestParams: RequestBuilders.ResourcesRequest
    ): ListenableFuture<ResourceBuilders.Resources> =
        Futures.immediateFuture(
            ResourceBuilders.Resources.Builder().setVersion("1").build()
        )

    private fun buildTile(ctx: Context): TileBuilders.Tile {
        val prefs     = AppPreferences(ctx)
        val p         = prefs.state.value
        val areaCount = p.watchedAreas.size + (if (p.useGps && p.gpsCity != null) 1 else 0)
        val statusText = if (areaCount == 0) "הגדר אזורים" else "$areaCount אזורים פעילים"

        val titleText = LayoutElementBuilders.Text.Builder()
            .setText("פיקוד העורף")
            .setFontStyle(
                LayoutElementBuilders.FontStyle.Builder()
                    .setSize(DimensionBuilders.SpProp.Builder().setValue(14f).build())
                    .setBold(true)
                    .build()
            ).build()

        val statusTextEl = LayoutElementBuilders.Text.Builder()
            .setText(statusText)
            .setFontStyle(
                LayoutElementBuilders.FontStyle.Builder()
                    .setSize(DimensionBuilders.SpProp.Builder().setValue(11f).build())
                    .build()
            ).build()

        val column = LayoutElementBuilders.Column.Builder()
            .addContent(titleText)
            .addContent(statusTextEl)
            .build()

        val root = LayoutElementBuilders.Box.Builder()
            .setWidth(DimensionBuilders.expand())
            .setHeight(DimensionBuilders.expand())
            .addContent(column)
            .build()

        return TileBuilders.Tile.Builder()
            .setResourcesVersion("1")
            .setFreshnessIntervalMillis(60_000)
            .setTileTimeline(
                TimelineBuilders.Timeline.Builder()
                    .addTimelineEntry(
                        TimelineBuilders.TimelineEntry.Builder()
                            .setLayout(
                                LayoutElementBuilders.Layout.Builder()
                                    .setRoot(root)
                                    .build()
                            ).build()
                    ).build()
            ).build()
    }
}
