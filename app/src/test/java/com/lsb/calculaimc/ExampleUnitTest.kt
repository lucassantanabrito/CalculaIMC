package com.lsb.calculaimc

import org.junit.Test

import org.junit.Assert.*
import java.util.Locale

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val ptBR = Locale.forLanguageTag("pt-BR")

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun imc_isCorrect() {
        assertTrue(
            "O valor do IMC esta correto?",
            MainActivity.calculaIMC(90.0, 1.9, "pt") in 24.93 .. 24.94
        )
    }

    // Cálculo do IMC

    @Test
    fun imc_metrico_valoresExatos() {
        assertEquals(25.0, MainActivity.calculaIMC(100.0, 2.0, "pt"), 0.0001)
        assertEquals(20.0, MainActivity.calculaIMC(80.0, 2.0, "pt"), 0.0001)
    }

    @Test
    fun imc_metrico_alturaDecimal() {
        assertEquals(22.857, MainActivity.calculaIMC(70.0, 1.75, "pt"), 0.001)
    }

    @Test
    fun imc_imperial_ingles() {
        // 180 lb, 70 in -> 703 * 180 / 70²
        assertEquals(25.824, MainActivity.calculaIMC(180.0, 70.0, "en"), 0.001)
    }

    @Test
    fun imc_imperial_outroIdiomaUsaFormulaImperial() {
        assertEquals(
            MainActivity.calculaIMC(180.0, 70.0, "en"),
            MainActivity.calculaIMC(180.0, 70.0, "es"),
            0.0
        )
    }

    @Test
    fun imc_mesmosValores_resultadosDiferentesPorIdioma() {
        val metrico = MainActivity.calculaIMC(90.0, 1.9, "pt")
        val imperial = MainActivity.calculaIMC(90.0, 1.9, "en")
        assertEquals(metrico * 703, imperial, 0.0001)
    }

    @Test
    fun imc_pesoZero_retornaZero() {
        assertEquals(0.0, MainActivity.calculaIMC(0.0, 1.8, "pt"), 0.0)
    }

    @Test
    fun imc_alturaZero_retornaInfinito() {
        assertTrue(MainActivity.calculaIMC(70.0, 0.0, "pt").isInfinite())
    }

    // Formatação do resultado

    @Test
    fun formatacao_ptBR_usaVirgulaDecimal() {
        assertEquals("24,931", MainActivity.formatterNumberFromLocale(24.930747922437675, ptBR))
    }

    @Test
    fun formatacao_enUS_usaPontoDecimal() {
        assertEquals("24.931", MainActivity.formatterNumberFromLocale(24.930747922437675, Locale.US))
    }

    @Test
    fun formatacao_zero() {
        assertEquals("0", MainActivity.formatterNumberFromLocale(0.0, ptBR))
        assertEquals("0", MainActivity.formatterNumberFromLocale(0.0, Locale.US))
    }

    @Test
    fun formatacao_semCasasDecimaisDesnecessarias() {
        assertEquals("25", MainActivity.formatterNumberFromLocale(25.0, ptBR))
        assertEquals("22,5", MainActivity.formatterNumberFromLocale(22.5, ptBR))
    }

    @Test
    fun formatacao_separadorDeMilhar() {
        assertEquals("1.234,5", MainActivity.formatterNumberFromLocale(1234.5, ptBR))
        assertEquals("1,234.5", MainActivity.formatterNumberFromLocale(1234.5, Locale.US))
    }

    @Test
    fun calculoEFormatacao_fluxoCompleto() {
        val imc = MainActivity.calculaIMC(70.0, 1.75, "pt")
        assertEquals("22,857", MainActivity.formatterNumberFromLocale(imc, ptBR))
    }
}
