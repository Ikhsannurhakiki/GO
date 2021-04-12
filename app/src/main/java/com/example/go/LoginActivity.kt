package com.example.go

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Log
import android.view.View
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.go.databinding.ActivityLoginBinding
import org.json.JSONObject

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding


    companion object {
        const val EXTRA_USER = "extra_person"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDaftar.setOnClickListener(this)
        binding.btnMasuk.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        if(v.id == binding.btnDaftar.id){
            startActivity(Intent(this, SignUpActivity::class.java))
        }else if (v.id == binding.btnMasuk.id) {
            login()
        }

    }

    private fun login() {
        val strusername: String = binding.txtUsername.text.toString().trim()
        val strpassword: String = binding.txtPassword.text.toString().trim()

        val loading = ProgressDialog(this)
        loading.setMessage("Logging In...")
        loading.show()

        AndroidNetworking.post(ApiEndPoint.USER_LOGIN)
            .addBodyParameter("username",strusername)
            .addBodyParameter("password",strpassword)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("message"), Toast.LENGTH_SHORT).show()

                    if(response?.getString("message")?.contains("successfully")!!){
//                        this@LoginActivity.finish()
//                        masuk()
//                        val intent = Intent (this,LoginActivity::class.java)
//                        startActivity(intent)
//                        finish()
//                        val status  = "berhasil"
//                        return status

                    }

                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    anError?.errorDetail?.toString()?.let { Log.d("ONERROR", it) }
                    Toast.makeText(applicationContext,"Connection Failure", Toast.LENGTH_SHORT).show()                    }


            })

    }

    private fun masuk() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}