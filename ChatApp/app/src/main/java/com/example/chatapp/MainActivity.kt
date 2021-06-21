package com.example.chatapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.database.FirebaseListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


open class MainActivity : AppCompatActivity() {

    private var adapter: FirebaseListAdapter<ChatMessage>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

// Create and launch sign-in intent

        if(FirebaseAuth.getInstance().currentUser == null) {

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN)}
        else{

            Toast.makeText(this,
                "Witaj " + FirebaseAuth.getInstance().currentUser.displayName,
                Toast.LENGTH_LONG)
                .show()

            // Load chat room contents
            displayChatMessages();
        }
        val fab =
            findViewById<View>(R.id.fab) as FloatingActionButton

        fab.setOnClickListener {
            val input = findViewById<View>(R.id.input) as EditText

            // Read the input field and push a new instance
            // of ChatMessage to the Firebase database
            FirebaseDatabase.getInstance()
                .reference
                .push()
                .setValue(
                    ChatMessage(
                        input.text.toString(),
                        messageUser = FirebaseAuth.getInstance().currentUser.displayName
                    )
                )

            // Clear the input
            input.setText("")
        }

    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(
                    this,
                    "Zalogowano pomyślnie. Witamy!",
                    Toast.LENGTH_LONG
                )
                    .show()
                displayChatMessages()
            } else {
                Toast.makeText(
                    this,
                    "Wystąpił błąd podczas logowania. Spróbuj później",
                    Toast.LENGTH_LONG
                )
                    .show()

                // Close the app
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === R.id.menu_sign_out) {
            AuthUI.getInstance().signOut(this)
                .addOnCompleteListener {
                    Toast.makeText(
                        this@MainActivity,
                        "Wylogowano pomyślnie.",
                        Toast.LENGTH_LONG
                    )
                        .show()

                    // Close activity
                    finish()
                }
        }
        return true
    }

    private fun displayChatMessages() {
        val listOfMessages =
            findViewById<View>(R.id.list_of_messages) as ListView

        adapter = object : FirebaseListAdapter<ChatMessage>(
            this, ChatMessage::class.java,
            R.layout.message, FirebaseDatabase.getInstance().reference
        ) {
            override fun populateView(
                v: View,
                model: ChatMessage,
                position: Int
            ) {
                // Get references to the views of message.xml
                val messageText =
                    v.findViewById<View>(R.id.message_text) as TextView
                val messageUser =
                    v.findViewById<View>(R.id.message_user) as TextView
                val messageTime =
                    v.findViewById<View>(R.id.message_time) as TextView

                // Set their text
                messageText.text = model.messageText
                messageUser.text = model.messageUser

                // Format the date before showing it
                messageTime.text = DateFormat.format(
                    "dd-MM-yyyy (HH:mm:ss)",
                    model.messageTime
                )
            }
        }

        listOfMessages.adapter = adapter
    }

    companion object{
        var RC_SIGN_IN = 123
    }
}