package com.nytech.embeauty.view.salon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.nytech.embeauty.constants.ToastTextConstants.LOGIN_REALIZADO_COM_SUCESSO
import com.nytech.embeauty.constants.ToastTextConstants.POR_FAVOR_INSIRA_SEU_EMAIL
import com.nytech.embeauty.constants.ToastTextConstants.POR_FAVOR_INSIRA_SUA_SENHA
import com.nytech.embeauty.databinding.ActivitySalonLoginBinding

/**
 * Tela de Login para salão
 */
class SalonLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySalonLoginBinding
    private lateinit var firebase: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySalonLoginBinding.inflate(layoutInflater)
        firebase = FirebaseAuth.getInstance()

        supportActionBar?.hide()

        setContentView(binding.root)

        // Verificar se o usuário já está autenticado
        if (firebase.currentUser != null) {
            // Usuário já autenticado, direcionar para a SalonMainActivity
            val intent = Intent(this, SalonMainActivity::class.java)
            startActivity(intent)
            finish() // Finalizar a atividade atual para que o usuário não possa voltar à tela de login pressionando o botão "Voltar"
            return // Encerrar o método para evitar que o código abaixo seja executado
        }

        // botão para tela de cadastro de salão
        binding.textSignupSalon.setOnClickListener {
            val intent = Intent(this@SalonLoginActivity, SalonRegisterActivity::class.java)
            startActivity(intent)
        }

        //botão de login do salão
        binding.buttonLoginSalon.setOnClickListener{
            //armazenam os dados digitados nos campos e-mail e senha pelo cliente
            val email = binding.editSalonEmail.text.toString().trim { it <= ' ' }
            val password = binding.editSalonPassword.text.toString().trim { it <= ' ' }

            //checa se o e-mail foi colocado e notifica caso não tenha sido
            if (email.isEmpty()) {
                Toast.makeText(
                    this@SalonLoginActivity,
                    POR_FAVOR_INSIRA_SEU_EMAIL,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //checa se a senha foi colocada e notifica caso não tenha sido
            if (password.isEmpty()) {
                Toast.makeText(
                    this@SalonLoginActivity,
                    POR_FAVOR_INSIRA_SUA_SENHA,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            //loga e autentica o salão no firebase
            firebase
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@SalonLoginActivity,
                            LOGIN_REALIZADO_COM_SUCESSO,
                            Toast.LENGTH_SHORT
                        ).show()
                        //onde estou (@SalonLoginActivity) e para onde vou (SalonHomeActivity)... direciona o usuário p a próxima tela (home)
                        val intent =
                            Intent(this@SalonLoginActivity, SalonMainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        //se o login não for completado com sucesso
                        Toast.makeText(
                            this@SalonLoginActivity,
                            task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
