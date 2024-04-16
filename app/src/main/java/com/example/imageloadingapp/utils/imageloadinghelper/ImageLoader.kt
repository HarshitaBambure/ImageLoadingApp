package com.example.imageloadingapp.utils.imageloadinghelper

import android.R
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.lang.ref.SoftReference
import java.net.HttpURLConnection
import java.net.URL
import java.util.Collections
import java.util.WeakHashMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors



object LoadImage {
    fun loadImage(context: Context, url: String, imageView:ImageView) {
        val imageLoader = ImageLoader(context)
        imageLoader.displayImage(url,R.drawable.dark_header,imageView)
    }
}

class ImageLoader(context: Context?) {
    var memoryCache: MemoryCache = MemoryCache()
    var fileCache: FileCache
    private val imageViews = Collections.synchronizedMap(WeakHashMap<ImageView, String>())
    var executorService: ExecutorService
    var stub_id: Int = R.drawable.dark_header

    init {
        fileCache = context?.let { FileCache(it) }!!
        executorService = Executors.newFixedThreadPool(5)
    }

    fun displayImage(url: String, loader: Int, imageView: ImageView) {
        stub_id = loader
        imageViews[imageView] = url
        val bitmap: Bitmap? = memoryCache.get(url)
        if (bitmap != null) imageView.setImageBitmap(bitmap) else {
            queuePhoto(url, imageView)
            imageView.setImageResource(loader)
        }
    }

    private fun queuePhoto(url: String, imageView: ImageView) {
        val p = PhotoToLoad(url, imageView)
        executorService.submit(PhotosLoader(p))
    }

    private fun getBitmap(url: String): Bitmap? {
        val f: File = fileCache.getFile(url)

        //from SD cache
        val b = decodeFile(f)
        return b
            ?: try {
                var bitmap: Bitmap? = null
                val imageUrl = URL(url)
                val conn = imageUrl.openConnection() as HttpURLConnection
                conn.setConnectTimeout(30000)
                conn.setReadTimeout(30000)
                conn.instanceFollowRedirects = true
                val `is` = conn.inputStream
                val os: OutputStream = FileOutputStream(f)
                Utils.CopyStream(`is`, os)
                os.close()
                bitmap = decodeFile(f)
                bitmap
            } catch (ex: Exception) {
                ex.printStackTrace()
                null
            }

        //from web
    }

    //decodes image and scales it to reduce memory consumption
    private fun decodeFile(f: File): Bitmap? {
        try {
            //decode image size
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            BitmapFactory.decodeStream(FileInputStream(f), null, o)

            //Find the correct scale value. It should be the power of 2.
            val REQUIRED_SIZE = 70
            var width_tmp = o.outWidth
            var height_tmp = o.outHeight
            var scale = 1
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) break
                width_tmp /= 2
                height_tmp /= 2
                scale *= 2
            }

            //decode with inSampleSize
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            return BitmapFactory.decodeStream(FileInputStream(f), null, o2)
        } catch (e: FileNotFoundException) {
        }
        return null
    }

    //Task for the queue
    inner class PhotoToLoad(var url: String, var imageView: ImageView)
    internal inner class PhotosLoader(var photoToLoad: PhotoToLoad) : Runnable {
        override fun run() {
            if (imageViewReused(photoToLoad)) return
            val bmp = getBitmap(photoToLoad.url)
            bmp?.let { memoryCache.put(photoToLoad.url, it) }
            if (imageViewReused(photoToLoad)) return
            val bd = BitmapDisplayer(bmp, photoToLoad)
            val a = photoToLoad.imageView.context as Activity
            a.runOnUiThread(bd)
        }
    }

    fun imageViewReused(photoToLoad: PhotoToLoad): Boolean {
        val tag = imageViews[photoToLoad.imageView]
        return if (tag == null || tag != photoToLoad.url) true else false
    }

    //Used to display bitmap in the UI thread
    internal inner class BitmapDisplayer(var bitmap: Bitmap?, var photoToLoad: PhotoToLoad) :
        Runnable {
        override fun run() {
            if (imageViewReused(photoToLoad)) return
            if (bitmap != null) photoToLoad.imageView.setImageBitmap(bitmap) else photoToLoad.imageView.setImageResource(
                stub_id
            )
        }
    }

    fun clearCache() {
        memoryCache.clear()
        fileCache.clear()
    }
}


class FileCache(context: Context) {
    private var cacheDir: File? = null

    init {
        //Find the dir to save cached images
        cacheDir = context.cacheDir
        if (!cacheDir!!.exists()) cacheDir!!.mkdirs()
    }

    fun getFile(url: String): File {
        val filename = url.hashCode().toString()
        return File(cacheDir, filename)
    }

    fun clear() {
        val files = cacheDir!!.listFiles() ?: return
        for (f in files) f.delete()
    }
}


class MemoryCache {
    private val cache = Collections.synchronizedMap(HashMap<String, SoftReference<Bitmap>>())
    operator fun get(id: String): Bitmap? {
        if (!cache.containsKey(id)) return null
        val ref = cache[id]!!
        return ref.get()
    }

    fun put(id: String, bitmap: Bitmap) {
        cache[id] = SoftReference(bitmap)
    }

    fun clear() {
        cache.clear()
    }
}


object Utils {
    fun CopyStream(`is`: InputStream, os: OutputStream) {
        val buffer_size = 1024
        try {
            val bytes = ByteArray(buffer_size)
            while (true) {
                val count = `is`.read(bytes, 0, buffer_size)
                if (count == -1) break
                os.write(bytes, 0, count)
            }
        } catch (ex: java.lang.Exception) {
        }
    }
}