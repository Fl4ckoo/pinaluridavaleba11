package com.example.pinalurisdavaleba

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.add_dialog.view.*

data class User(
    val displayName: String = "",
    val state: String = ""

)







class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class MainActivity : AppCompatActivity() {
    private lateinit var rvUsers: RecyclerView
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        auth = Firebase.auth
        rvUsers = findViewById(R.id.rcView)
        db = Firebase.firestore





        val query = db.collection("users")


        val options = FirestoreRecyclerOptions.Builder<User>().setQuery(query, User::class.java)
                .setLifecycleOwner(this).build()




        val adapter = object : FirestoreRecyclerAdapter<User, UserViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
                val view = LayoutInflater.from(this@MainActivity).inflate(android.R.layout.simple_list_item_2, parent, false)
                return UserViewHolder(view)




            }

            override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: User) {
                val tvName: TextView = holder.itemView.findViewById(android.R.id.text1)
                val stateText: TextView = holder.itemView.findViewById(android.R.id.text2)
                tvName.text = model.displayName
                stateText.text = model.state


                holder.itemView.setOnClickListener {
                    Log.i(Constants.MAIN_ACTIVITY_TAG, "Clicked!")
                }
            }


        }

        rvUsers.adapter = adapter
        rvUsers.layoutManager = LinearLayoutManager(this)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if( item.itemId == R.id.ic_gamosvla ) {
            Log.d(Constants.MAIN_ACTIVITY_TAG, "Logout")
            auth.signOut()

            val logOutIntent = Intent(this, AuthenticationActivity::class.java);
            logOutIntent.flags =  Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(logOutIntent)
            finish()
        }
        else if (item.itemId == R.id.ic_damateba) {
            Log.i(Constants.MAIN_ACTIVITY_TAG, "Edit State")
            showActionDialog()
        }

        else if (item.itemId == R.id.ic_chamonatvali){
            Log.i(Constants.MAIN_ACTIVITY_TAG, "List of items")
            val mainActivityIntent = Intent(this, MainActivity2::class.java)
            mainActivityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(mainActivityIntent)
            finish()

        }







        return super.onOptionsItemSelected(item)

    }





    private fun showActionDialog(){


        val view = layoutInflater.inflate(R.layout.add_dialog, null)

        val displayNameEditText = view.findViewById<EditText>(R.id.displayNameEditText)
        val stateEditText = view.findViewById<EditText>(R.id.stateEditText)

        val dialog = AlertDialog.Builder(this)
                .setTitle("Update your reminder")
                .setView(view)
                .setNegativeButton("cancel", null)
                .setPositiveButton("OK", null)
                .show()






        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener{
            Log.i(Constants.MAIN_ACTIVITY_TAG, "Clicked on positive Button!")


        }

        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener{
            Log.i(Constants.MAIN_ACTIVITY_TAG, "Clicked on negative Button!")


        }

        val displayName = displayNameEditText.text.toString()
        val stateText = stateEditText.text.toString()

        if (stateText.isBlank() || displayName.isBlank() ) {
            Toast.makeText(this, "Cannot submit empty text", Toast.LENGTH_SHORT).show()
            return

        }

        val currentUser = auth.currentUser
        if(currentUser == null) {
            Toast.makeText(this, "No Signed In user", Toast.LENGTH_LONG).show()

        }
        val data = User(displayName, stateText)


        db.collection("users")
                .add(data)
                .addOnSuccessListener { documentReference ->
                    Log.d(Constants.MAIN_ACTIVITY_TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(Constants.MAIN_ACTIVITY_TAG, "Error adding document", e)
                }


          dialog.dismiss()
    }
}

