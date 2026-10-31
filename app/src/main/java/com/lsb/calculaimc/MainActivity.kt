package com.lsb.calculaimc

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.pow

class MainActivity : AppCompatActivity() {


    private lateinit var etPeso: EditText
    private lateinit var etAltura: EditText
    private lateinit var tvResultado: TextView
    private lateinit var btCalcular: Button
    private lateinit var btLimpar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main) // Aqui a tela é criada.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        etPeso = findViewById(R.id.etPeso)
        etAltura = findViewById(R.id.etAltura)
        tvResultado = findViewById(R.id.tvResultado)
        btCalcular = findViewById(R.id.btCalcular)
        btLimpar = findViewById(R.id.btLimpar)

        btCalcular.setOnClickListener {
            btCalcularOnClick()
        }

        btLimpar.setOnClickListener {
            btLimparonClick()
        }

        btCalcular.setOnLongClickListener {
            Toast.makeText(
                this,
                getString(R.string.msg_longclick_calcular),
                Toast.LENGTH_SHORT
            ).show()
            true
        }

        btLimpar.setOnLongClickListener {
            Toast.makeText(
                this,
                getString(R.string.msg_longclick_limpar),
                Toast.LENGTH_SHORT
            ).show()
            true
        }
    }

    private fun btLimparonClick() {
        etPeso.text.clear()
        etAltura.text.clear()
        tvResultado.text = formatterNumberFromLocale(0.0)
        etPeso.requestFocus()
    }

    fun btCalcularOnClick() {
        val peso = etPeso.text.toString().toDoubleOrNull()?: run {
            etPeso.error = getString(R.string.error_peso)
            return
        }
        val altura = etAltura.text.toString().toDoubleOrNull()?: run {
            etAltura.error = getString(R.string.error_altura)
            return
        }

        val language = Locale.getDefault().language

        val resultado = if (language == "pt") {
            peso / altura.pow(2)
        } else {
            703 * (peso / altura.pow(2))
        }

        tvResultado.text = formatterNumberFromLocale(resultado)

    }

    private fun formatterNumberFromLocale(number: Double): String {
        val nf = NumberFormat.getNumberInstance(Locale.getDefault())
        val df = nf as DecimalFormat

        return df.format(number)
    }
}