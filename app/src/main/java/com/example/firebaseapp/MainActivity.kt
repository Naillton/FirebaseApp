package com.example.firebaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    // iniciando variavel database que contem as referencias do nosso banco nosql no firebase
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textV: TextView = findViewById(R.id.textView)
        // Read and Write using RealTime database
        // https://fir-kotlin-cdc3c-default-rtdb.firebaseio.com/
        database = Firebase.database.reference

        // Escrevendo objetos no firebase
        val user1 = User("Jhon", "1234")
        val user2 = User("Jack", "123")
        database.child("Users").child(user1.userName).setValue(user1)
        database.child("Users").child(user2.userName).setValue(user2)


        val pl = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val objct = snapshot.value
                textV.text = objct.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        database.child("Users").addValueEventListener(pl)

        /* Escrevendo valores simples no firebase
        // para escrever valores basicos usamos o setValue, o child usado abaixo simboliza
        // as chaves existentes no nosso nosql por exemplo se temos um objeto users, que um userId
        // como chave e o username como valor usaremos o databse.child(users).child(userId)
        // .setValue(username) desa forma criaremos um objeto chave e valor no firebase que ficara assim
        /**
         * users: { userId: "zezinho", ... }
         */
        // exemplo de uso 2

        database.child("Produtos")
            .child("Chocolate Cookies And Cream").setValue(2.99)

        // Para conseguirmos ler os dados definimos uma variavel que vai ter como valor
        // uma lista de eventos onde conseguiremos retornar o nosso objeto
        val postListener = object : ValueEventListener {
            // metodo de chamada de dados
            override fun onDataChange(snapshot: DataSnapshot) {
                val price = snapshot.value
                Toast.makeText(
                    this@MainActivity,
                    price.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }

            // caso a chamada falhe ou ocorra algum erro chama o onCancelled
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

        // Aqui usamos a funcao addValueEventListener que ira retornar o resultado do nosso evento
        // note que se passarmos o objeto produtos receberemos um { choco...: 2.99 }
        // e se tentarmos acessar o nome do produto sem fazer antes a referencia ao child principal
        // retornaremos null
        database.child("Produtos")
            .child("Chocolate Cookies And Cream").addValueEventListener(postListener)*/
    }
}