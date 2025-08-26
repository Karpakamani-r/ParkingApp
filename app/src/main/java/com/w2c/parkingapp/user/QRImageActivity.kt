package com.w2c.parkingapp.user

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.w2c.parkingapp.databinding.ActivityQrimageBinding
import io.github.g0dkar.qrcode.QRCode
import java.io.ByteArrayOutputStream


class QRImageActivity : AppCompatActivity() {
    private val WIDTH = 400
    private val HEIGHT = 400

    private lateinit var binding: ActivityQrimageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrimageBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val id = intent.extras?.getInt("booking_id")

        try {
            binding.ivQR.setImageBitmap(encodeAsBitmap("$id"))
        } catch (e: Exception) {

        }
    }

    @Throws(WriterException::class)
    fun encodeAsBitmap(str: String): Bitmap? {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(str, BarcodeFormat.QR_CODE, WIDTH, HEIGHT)
        val w = bitMatrix.width
        val h = bitMatrix.height
        val pixels = IntArray(w * h)
        for (y in 0 until h) {
            for (x in 0 until w) {
                pixels[y * w + x] = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h)
        return bitmap
    }
}