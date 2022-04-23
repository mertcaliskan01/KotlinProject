package com.mtcn.androidroomkotlin.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.mtcn.androidroomkotlin.R
import com.mtcn.androidroomkotlin.databinding.ActivityNavigationBinding
import com.mtcn.androidroomkotlin.databinding.ActivityNewWordBinding

class NewWordActivity : AppCompatActivity() {

    private lateinit var editHeader: EditText
    private lateinit var editContent: EditText
    private lateinit var buttonSave: AppCompatButton
    private lateinit var binding: ActivityNewWordBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        events()
    }

    fun init(){
        binding = ActivityNewWordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        editHeader = binding.editHeader
        editContent = binding.editContent
        buttonSave = binding.buttonSave
    }

    fun events(){
        buttonSave.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editHeader.text) || TextUtils.isEmpty(editContent.text)) {
                Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
                ).show()
                //setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val header = editHeader.text.toString()
                val content = editContent.text.toString()
                replyIntent.putExtra(HEADER, header)
                replyIntent.putExtra(CONTENT, content)
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            }
        }
    }

    companion object {
        const val CONTENT = "Content"
        const val HEADER = "Header"
    }
}