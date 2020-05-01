package ar.edu.utn.frba.mobile.clases_2020c1.utils.storage.fileSystem

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ExternalStorage {
    companion object {
        fun getFileUri(fileName: String): String? {
            val file = File(getAppFolder() + fileName)
            if (file.exists()) {
                return file.toURI().toString()
            }
            return null
        }

        fun saveFile(bitmap: Bitmap, fileName: String) : File {
            val file = File(getAppFolder() + fileName + ".JPEG")
            try {
                file.createNewFile()
                val ostream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream)
                ostream.flush()
                ostream.close()
            } catch (e: IOException) {
                Log.e("IOException", e.localizedMessage)
            }

            return file
        }

        fun deleteFile(fileName: String) {
            val file = File(getAppFolder() + fileName)
            if (file.exists()) {
                file.delete()
            }
        }

        fun getCacheFileUri(context: Context, fileName: String): String? {
            val file = File(context.externalCacheDir!!.path + fileName)
            if (file.exists()) {
                return file.toURI().toString()
            }
            return null
        }

        fun saveFileInCache(context: Context, bitmap: Bitmap, fileName: String) : File {
            val file = File(context.externalCacheDir!!.path + fileName)
            try {
                file.createNewFile()
                val ostream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream)
                ostream.flush()
                ostream.close()
            } catch (e: IOException) {
                Log.e("IOException", e.localizedMessage)
            }

            return file
        }

        fun deleteFileFromCache(context: Context, fileName: String) {
            val file = File(context.externalCacheDir!!.path + fileName)
            if (file.exists()) {
                file.delete()
            }
        }

        fun getFiles(): Array<out File>? {
            val appFolder = ExternalStorage.getAppFolder()
            val directory = File(appFolder)
            return directory.listFiles()
        }

        private fun getAppFolder(): String {
            val path = Environment.getExternalStorageDirectory().path + "/clases_2020c1/"
            val folder = File(path)
            if (!folder.exists()) {
                folder.mkdirs()
            }
            return path
        }
    }
}