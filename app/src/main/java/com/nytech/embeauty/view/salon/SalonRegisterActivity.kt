package com.nytech.embeauty.view.salon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nytech.embeauty.constants.ToastTextConstants.CADASTRO_REALIZADO_COM_SUCESSO
import com.nytech.embeauty.constants.ToastTextConstants.POR_FAVOR_INSIRA_SEU_EMAIL
import com.nytech.embeauty.constants.ToastTextConstants.POR_FAVOR_INSIRA_SUA_SENHA
import com.nytech.embeauty.databinding.ActivitySalonRegisterBinding

/**
 * Tela de Cadastro para salão
 */
class SalonRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySalonRegisterBinding
    private lateinit var firebase: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySalonRegisterBinding.inflate(layoutInflater)
        firebase = FirebaseAuth.getInstance()

        supportActionBar?.hide()

        setContentView(binding.root)

        // botão para a tela de login do salão
        binding.textLoginSalon.setOnClickListener {
            startActivity(Intent(this@SalonRegisterActivity, SalonLoginActivity::class.java))
        }

        // lógica de cadastro do novo salão no Firebase
        binding.buttonRegisterSalon.setOnClickListener {
            val email = binding.editSalonEmail.text.toString().trim { it <= ' ' }
            val password = binding.editSalonPassword.text.toString().trim { it <= ' ' }

            //checa se o e-mail foi colocado e notifica caso não tenha sido
            if (email.isEmpty()) {
                Toast.makeText(
                    this@SalonRegisterActivity,
                    POR_FAVOR_INSIRA_SEU_EMAIL,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //checa se a senha foi colocada e notifica caso não tenha sido
            if (password.isEmpty()) {
                Toast.makeText(
                    this@SalonRegisterActivity,
                    POR_FAVOR_INSIRA_SUA_SENHA,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //cria o salão no firebase auth(autenticação)
            firebase
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        //salão cadastrado no firebase
                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        //se o cadastro for completado com sucesso
                        Toast.makeText(
                            this@SalonRegisterActivity,
                            CADASTRO_REALIZADO_COM_SUCESSO,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        //se o cadastro não for completado com sucesso
                        Toast.makeText(
                            this@SalonRegisterActivity,
                            task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
