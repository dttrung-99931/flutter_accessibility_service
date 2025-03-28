package slayer.accessibility.service.flutter_accessibility_service

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri


class AccessibilityEventContentProvider : ContentProvider() {
    companion object {
        private const val AUTHORITY =
            "slayer.accessibility.service.flutter_accessibility_service.provider"
        private const val PATH = "accessibility_event"
        const val EVENT_JSON_COLUMN = "event_json"
        @JvmField
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$PATH")
        var currentEventJson: String = "" // Hold accessibility event json

        @JvmStatic
        fun createProviderValue(json: String): ContentValues {
            return ContentValues().apply {
                put(EVENT_JSON_COLUMN, json)
            }
        }
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val cursor = MatrixCursor(arrayOf(EVENT_JSON_COLUMN))
        cursor.addRow(arrayOf<Any>(currentEventJson))
        return cursor
    }

    override fun getType(uri: Uri): String {
         return "vnd.android.cursor.dir/slayer.accessibility_event"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        currentEventJson = values?.getAsString(EVENT_JSON_COLUMN) ?: ""
        context?.contentResolver?.notifyChange(uri, null)
        return uri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }
}