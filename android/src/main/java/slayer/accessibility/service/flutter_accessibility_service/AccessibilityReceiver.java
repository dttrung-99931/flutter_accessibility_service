package slayer.accessibility.service.flutter_accessibility_service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import io.flutter.plugin.common.EventChannel;

public class AccessibilityReceiver extends BroadcastReceiver {

    private final EventChannel.EventSink eventSink;

    public AccessibilityReceiver(EventChannel.EventSink eventSink) {
        this.eventSink = eventSink;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Cursor cursor = context.getContentResolver().query(
                    AccessibilityEventContentProvider.CONTENT_URI,
                    null, null, null, null
            );
            if (cursor != null && cursor.moveToFirst()){
                int colIndex = cursor.getColumnIndex(AccessibilityEventContentProvider.EVENT_JSON_COLUMN);
                String json = cursor.getString(colIndex);
                if (json != null && !json.isEmpty()){
                    eventSink.success(json);
                }
                cursor.close();
            }

        } catch (Exception e) {
            Log.d("Accessibility", "Read event json from content provider error: " + e.getMessage());
        }
    }
}
