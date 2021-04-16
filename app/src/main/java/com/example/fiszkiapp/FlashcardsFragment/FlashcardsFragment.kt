package com.example.fiszkiapp.FlashcardsFragment

import android.app.AlertDialog
import android.content.ClipData
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fiszkiapp.R
import com.example.fiszkiapp.database.FiszkiDatabase
import com.example.fiszkiapp.databinding.FragmentFlashcardsBinding


class FlashcardsFragment: Fragment() {

    private lateinit var viewModel : FlashcardsViewModel
    private lateinit var viewModelFactory: FlashcardsViewModelFactory

   var topicId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dataSource = FiszkiDatabase.getInstance(application).fiszkiDatabaseDao

        topicId = FlashcardsFragmentArgs.fromBundle(arguments!!).topic

        viewModelFactory = FlashcardsViewModelFactory(dataSource, application, topicId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FlashcardsViewModel::class.java)

        val binding : FragmentFlashcardsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_flashcards, container, false)

        val adapter = FlashcardsAdapter(application.applicationContext)
        binding.listFlashcards.adapter = adapter
        binding.listFlashcards.layoutManager = LinearLayoutManager(context)
        setHasOptionsMenu(true)

        binding.addFlashcardButton.setOnClickListener {
            Navigation.findNavController(it).navigate(FlashcardsFragmentDirections.actionFlashcardsFragmentToAddEditFlashcardFragment(-1, topicId))
        }

        viewModel.flashcards.observe(viewLifecycleOwner, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.topic_delete){
            deleteTopic()
        }
        else if(item.itemId == R.id.topic_rename){
            renameTopic()
        }
        return true
    }

    private fun deleteTopic() {
        AlertDialog.Builder(context)
            .setTitle("Delete the topic")
            .setMessage("Are you sure you want to delete this topic with all flashcards?") // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton("Yes") { dialog, which ->
                viewModel.deleteTopic(topicId)
                val navController = this.findNavController()
                NavigationUI.navigateUp(navController, DrawerLayout(context!!))
            } // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton("No", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun renameTopic() {
        val view = layoutInflater.inflate(R.layout.topic_rename_dialog, null);
        val alertDialog = AlertDialog.Builder(context).create();
        alertDialog.setTitle("Your Title Here");
        alertDialog.setCancelable(false);
        alertDialog.setMessage("Your Message Here");

        val etComments = view.findViewById<EditText>(R.id.etComments);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { dialog, which ->
            val str = etComments.text.toString()
            viewModel.renameTopic(topicId, str)
        }
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { dialog, which ->
            alertDialog.dismiss()
        }
        alertDialog.setView(view);
        alertDialog.show();
    }
}