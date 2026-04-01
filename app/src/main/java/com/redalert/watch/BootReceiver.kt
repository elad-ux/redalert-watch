package com.redalert.watch

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * מופעל לאחר ריסטרט השעון – מאתחל מחדש את הפולינג.
 * מכיוון שהפולינג רץ בתוך MainActivity, מספיק להפעיל את האפליקציה.
 * ב-production כדאי להפוך את הפולינג ל-ForegroundService.
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(ctx: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("BootReceiver", "Boot completed – launching main activity")
            val launch = Intent(ctx, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            ctx.startActivity(launch)
        }
    }
}
