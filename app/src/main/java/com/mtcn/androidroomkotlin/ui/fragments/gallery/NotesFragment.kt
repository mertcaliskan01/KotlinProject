package com.mtcn.androidroomkotlin.ui.fragments.gallery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mtcn.androidroomkotlin.R
import com.mtcn.androidroomkotlin.models.adapters.WordListAdapter
import com.mtcn.androidroomkotlin.databinding.FragmentNotesBinding
import com.mtcn.androidroomkotlin.models.model.Word
import com.mtcn.androidroomkotlin.models.model.WordViewModel
import com.mtcn.androidroomkotlin.models.model.WordViewModelFactory
import com.mtcn.androidroomkotlin.repository.NotesApplication
import com.mtcn.androidroomkotlin.ui.NewWordActivity
import com.mtcn.androidroomkotlin.ui.fragments.home.HomeViewModel

class NotesFragment : Fragment() {
    private val newWordActivityRequestCode = 1
    private var _binding: FragmentNotesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((context?.applicationContext as NotesApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.headerTitle
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        init()
        return root
    }



    fun init(){
        val recyclerView = binding.recyclerview
        val adapter = WordListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        wordViewModel.allWords.observe(viewLifecycleOwner) { words ->
            // Update the cached copy of the words in the adapter.
            words.let { adapter.submitList(it) }
        }

        binding.btnNewNote.setOnClickListener {
            val intent = Intent(context, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            wordViewModel.deleteAll()
            var header = ""
            var content = ""

            intentData?.getStringExtra(NewWordActivity.HEADER)?.let { reply ->
                header = reply
            }

            intentData?.getStringExtra(NewWordActivity.CONTENT)?.let { reply ->
                content = reply
            }

            wordViewModel.insert(Word(header,content))

        } else {
            Toast.makeText(
                context,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}