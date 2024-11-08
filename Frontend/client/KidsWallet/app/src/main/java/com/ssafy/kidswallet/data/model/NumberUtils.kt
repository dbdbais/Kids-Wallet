import java.text.NumberFormat
import java.util.Locale

object NumberUtils {
    fun formatNumberWithCommas(number: Int): String {
        // NumberFormat.getInstance()를 사용하여 숫자를 콤마로 구분
        val numberFormat = NumberFormat.getInstance(Locale.getDefault())
        return numberFormat.format(number)
    }
}

//val number = 1000000
//val formattedNumber = NumberUtils.formatNumberWithCommas(number)
//1,000,000