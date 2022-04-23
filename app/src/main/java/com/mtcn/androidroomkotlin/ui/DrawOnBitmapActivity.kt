package com.mtcn.androidroomkotlin.ui

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.Options
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.mtcn.androidroomkotlin.R
import com.mtcn.androidroomkotlin.components.DrawableImageView


class DrawOnBitmapActivity : Activity(), View.OnClickListener {
    var choosenImageView: DrawableImageView? = null
    var choosePicture: Button? = null
    var savePicture: Button? = null
    var bmp: Bitmap? = null
    var alteredBitmap: Bitmap? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_on_bitmap)
        choosenImageView = findViewById<View>(R.id.ChoosenImageView) as DrawableImageView
        choosePicture = findViewById<View>(R.id.ChoosePictureButton) as Button
        savePicture = findViewById<View>(R.id.SavePictureButton) as Button
        savePicture!!.setOnClickListener(this)
        choosePicture!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v === choosePicture) {
            val choosePictureIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(choosePictureIntent, 0)
        } else if (v === savePicture) {
            if (alteredBitmap != null) {
                val contentValues = ContentValues(3)
                contentValues.put(MediaStore.Audio.Media.DISPLAY_NAME, "Draw On Me")
                val imageFileUri = contentResolver.insert(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, contentValues
                )
                try {
                    val imageFileOS = contentResolver
                        .openOutputStream(imageFileUri!!)
                    alteredBitmap!!
                        .compress(CompressFormat.JPEG, 90, imageFileOS)
                    val t = Toast
                        .makeText(this, "Saved!", Toast.LENGTH_SHORT)
                    t.show()
                } catch (e: Exception) {
                    Log.v("EXCEPTION", e.message!!)
                }
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        intent: Intent
    ) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == RESULT_OK) {
            val imageFileUri = intent.data
            try {
                val bmpFactoryOptions = Options()
                bmpFactoryOptions.inJustDecodeBounds = true
                bmp = BitmapFactory
                    .decodeStream(
                        contentResolver.openInputStream(
                            imageFileUri!!
                        ), null, bmpFactoryOptions
                    )
                bmpFactoryOptions.inJustDecodeBounds = false
                bmp = BitmapFactory
                    .decodeStream(
                        contentResolver.openInputStream(
                            imageFileUri
                        ), null, bmpFactoryOptions
                    )
                alteredBitmap = Bitmap.createBitmap(
                    bmp!!.width,
                    bmp!!.height, bmp!!.config
                )
                choosenImageView?.setNewImage(alteredBitmap, bmp)
            } catch (e: Exception) {
                Log.v("ERROR", e.toString())
            }
        }
    }
}