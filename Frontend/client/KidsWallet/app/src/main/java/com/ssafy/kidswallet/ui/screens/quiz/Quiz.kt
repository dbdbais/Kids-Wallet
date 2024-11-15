import android.widget.Toast
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ssafy.kidswallet.R
import com.ssafy.kidswallet.ui.components.BlueButton
import com.ssafy.kidswallet.ui.components.BottomNavigationBar
import com.ssafy.kidswallet.ui.components.FontSizes
import com.ssafy.kidswallet.ui.components.LightGrayButton
import com.ssafy.kidswallet.ui.components.Top
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun QuizScreen(navController: NavController) {
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(1500) // 로딩 시간 조절
        isLoading = false
    }
    val quiz = listOf(
        mapOf(
            "title" to "은행에 돈을 저축하면 받을 수 있는 것은 무엇일까요?",
            "options" to listOf("1. 이자", "2. 세금", "3. 수수료", "4. 벌금"),
            "comment" to "은행에 돈을 저축하면 일정 기간 돈을 맡긴 대가로 '이자'라는 추가 금액을 받을 수 있습니다",
            "answer" to "1. 이자"
        ),
        mapOf(
            "title" to "신용카드를 사용했을 때 나중에 지불해야 하는 금액은 무엇인가요?",
            "options" to listOf("1. 원금", "2. 연체금", "3. 이자", "4. 청구서"),
            "comment" to "신용카드를 사용하면 사용 내역이 모여 '청구서'로 발송되며, 정해진 기한 내에 지불해야 합니다.",
            "answer" to "4. 청구서"
        ),
        mapOf(
            "title" to "은행 계좌의 비밀번호를 관리할 때 가장 중요한 것은 무엇인가요?",
            "options" to listOf("1. 타인과 공유", "2. 비밀번호를 자주 변경", "3. 같은 비밀번호 사용", "4. 기억하기 쉬운 비밀번호"),
            "comment" to "은행 계좌의 보안을 위해 비밀번호를 자주 변경하는 것이 좋습니다.",
            "answer" to "2. 비밀번호를 자주 변경"
        ),
        mapOf(
            "title" to "현금카드는 어디에서 사용할 수 있나요?",
            "options" to listOf("1. 은행 ATM", "2. 영화관", "3. 인터넷 쇼핑몰", "4. 해외 여행지"),
            "comment" to "현금카드는 은행의 ATM에서 주로 사용됩니다.",
            "answer" to "1. 은행 ATM"
        ),
        mapOf(
            "title" to "신용점수를 높이기 위해 좋은 방법은 무엇인가요?",
            "options" to listOf("1. 카드 대금 연체", "2. 카드 대금 완납", "3. 여러 카드 사용", "4. 잦은 대출"),
            "comment" to "신용점수를 높이기 위해 신용카드 대금을 제때 완납하는 것이 중요합니다.",
            "answer" to "2. 카드 대금 완납"
        ),
        mapOf(
            "title" to "대출을 받을 때 가장 중요한 것은 무엇인가요?",
            "options" to listOf("1. 낮은 금리", "2. 높은 이자율", "3. 대출 한도", "4. 상환 능력"),
            "comment" to "대출을 받을 때 본인의 상환 능력을 고려하는 것이 가장 중요합니다.",
            "answer" to "4. 상환 능력"
        ),
        mapOf(
            "title" to "카드의 결제일을 놓쳤을 경우 발생하는 것은 무엇인가요?",
            "options" to listOf("1. 상환 면제", "2. 연체료", "3. 포인트 지급", "4. 카드 해지"),
            "comment" to "결제일을 놓치면 연체료가 발생할 수 있습니다.",
            "answer" to "2. 연체료"
        ),
        mapOf(
            "title" to "ATM에서 출금할 때 필요한 것은 무엇인가요?",
            "options" to listOf("1. 카드 비밀번호", "2. 신분증", "3. 운전면허증", "4. 통장 잔액"),
            "comment" to "ATM에서 출금 시에는 보안상 카드 비밀번호가 필요합니다.",
            "answer" to "1. 카드 비밀번호"
        ),
        mapOf(
            "title" to "체크카드는 신용카드와 다르게 어떤 기능이 있나요?",
            "options" to listOf("1. 후불 결제 가능", "2. 계좌 잔액 내에서 사용", "3. 포인트 적립", "4. 대출 가능"),
            "comment" to "체크카드는 계좌 잔액 내에서만 사용할 수 있습니다.",
            "answer" to "2. 계좌 잔액 내에서 사용"
        ),
        mapOf(
            "title" to "적금은 어떤 방식으로 운영되나요?",
            "options" to listOf("1. 일시불 투자", "2. 매월 정해진 금액 납입", "3. 금액 제한 없음", "4. 무이자"),
            "comment" to "적금은 매월 정해진 금액을 일정 기간 동안 납입하는 방식으로 운영됩니다.",
            "answer" to "2. 매월 정해진 금액 납입"
        ),
        mapOf(
            "title" to "금융 사기를 방지하기 위해 가장 필요한 것은 무엇인가요?",
            "options" to listOf("1. 대출 자주 이용", "2. 낯선 링크 클릭", "3. 비밀번호 자주 변경", "4. 개인정보 공유"),
            "comment" to "금융 사기를 방지하려면 비밀번호를 자주 변경하고 관리하는 것이 필요합니다.",
            "answer" to "3. 비밀번호 자주 변경"
        ),
        mapOf(
            "title" to "연체가 길어질 경우 발생할 수 있는 문제는 무엇인가요?",
            "options" to listOf("1. 신용 점수 상승", "2. 신용 점수 하락", "3. 카드 보너스", "4. 연체금 면제"),
            "comment" to "연체가 길어지면 신용 점수가 하락할 수 있습니다.",
            "answer" to "2. 신용 점수 하락"
        ),
        mapOf(
            "title" to "은행 이자를 받을 때 중요한 요소는 무엇인가요?",
            "options" to listOf("1. 은행 위치", "2. 예금 기간", "3. 카드 종류", "4. 비밀번호"),
            "comment" to "이자를 받을 때 예금 기간이 중요한 요소로 작용합니다.",
            "answer" to "2. 예금 기간"
        ),
        mapOf(
            "title" to "예금의 장점은 무엇인가요?",
            "options" to listOf("1. 자산 증식 가능", "2. 현금 사용 제한", "3. 대출 이자 증가", "4. 세금 감면 없음"),
            "comment" to "예금은 자산 증식의 수단이 될 수 있습니다.",
            "answer" to "1. 자산 증식 가능"
        ),
        mapOf(
            "title" to "청약통장은 어떤 목적으로 주로 사용되나요?",
            "options" to listOf("1. 주택 구입", "2. 학자금 대출", "3. 해외 여행", "4. 차량 구매"),
            "comment" to "청약통장은 주로 주택 구입 목적으로 사용됩니다.",
            "answer" to "1. 주택 구입"
        ),
        mapOf(
            "title" to "은행에서 대출 이자를 줄이기 위한 방법은 무엇인가요?",
            "options" to listOf("1. 높은 금리 대출", "2. 조기 상환", "3. 대출 연장", "4. 금리 인상"),
            "comment" to "대출 이자를 줄이기 위해 조기 상환을 고려할 수 있습니다.",
            "answer" to "2. 조기 상환"
        ),
        mapOf(
            "title" to "신용카드의 혜택은 무엇인가요?",
            "options" to listOf("1. 금리 인상", "2. 연체료", "3. 포인트 적립", "4. 현금 인출 불가"),
            "comment" to "신용카드 사용 시 포인트 적립 등의 혜택을 누릴 수 있습니다.",
            "answer" to "3. 포인트 적립"
        ),
        mapOf(
            "title" to "자동이체를 설정하면 어떤 이점이 있나요?",
            "options" to listOf("1. 결제일 놓치기", "2. 자동 결제 관리", "3. 대출 신청", "4. 통장 해지"),
            "comment" to "자동이체는 자동으로 결제를 관리해주어 결제일을 놓치지 않도록 도와줍니다.",
            "answer" to "2. 자동 결제 관리"
        ),
        mapOf(
            "title" to "연금보험의 주된 목적은 무엇인가요?",
            "options" to listOf("1. 단기 저축", "2. 장기 노후 대비", "3. 해외 송금", "4. 생활비 절약"),
            "comment" to "연금보험은 주로 장기적으로 노후를 대비하기 위해 사용됩니다.",
            "answer" to "2. 장기 노후 대비"
        ),
        mapOf(
            "title" to "체크카드와 신용카드의 주요 차이점은 무엇인가요?",
            "options" to listOf("1. 이자 발생 여부", "2. 포인트 적립 여부", "3. 실시간 계좌 차감 여부", "4. 카드 디자인"),
            "comment" to "체크카드는 사용 시 실시간으로 계좌에서 차감됩니다.",
            "answer" to "3. 실시간 계좌 차감 여부"
        ),
        mapOf(
            "title" to "적금을 해지할 때 손해를 최소화하기 위해 필요한 것은 무엇인가요?",
            "options" to listOf("1. 조기 해지", "2. 만기 시 해지", "3. 추가 입금", "4. 대출 신청"),
            "comment" to "적금은 만기 시 해지할 때 손해를 최소화할 수 있습니다.",
            "answer" to "2. 만기 시 해지"
        ),
        mapOf(
            "title" to "금융 사기 예방을 위해 주의할 사항은 무엇인가요?",
            "options" to listOf("1. 의심스러운 연락 무시", "2. 신용카드 대출", "3. 개인정보 공유", "4. 자주 비밀번호 변경하지 않기"),
            "comment" to "금융 사기를 예방하기 위해 의심스러운 연락을 무시하는 것이 좋습니다.",
            "answer" to "1. 의심스러운 연락 무시"
        ),
        mapOf(
            "title" to "신용카드 사용 시 주의할 점은 무엇인가요?",
            "options" to listOf("1. 연체 대금 쌓기", "2. 신용 한도 초과", "3. 사용 내역 확인", "4. 무작위 결제"),
            "comment" to "신용카드 사용 시 반드시 사용 내역을 확인하고 관리하는 것이 중요합니다.",
            "answer" to "3. 사용 내역 확인"
        ),
        mapOf(
            "title" to "현금 영수증을 발급받는 주된 이유는 무엇인가요?",
            "options" to listOf("1. 포인트 적립", "2. 소득공제 혜택", "3. 세금 납부 증가", "4. 대출 금리 인하"),
            "comment" to "현금 영수증 발급은 소득공제 혜택을 받을 수 있는 주요 방법 중 하나입니다.",
            "answer" to "2. 소득공제 혜택"
        ),
        mapOf(
            "title" to "계좌 비밀번호를 설정할 때 피해야 할 것은 무엇인가요?",
            "options" to listOf("1. 생일이나 전화번호", "2. 복잡한 조합", "3. 자주 변경", "4. 강력한 보안"),
            "comment" to "비밀번호로 생일이나 전화번호와 같은 쉽게 유추할 수 있는 정보를 사용하지 않아야 합니다.",
            "answer" to "1. 생일이나 전화번호"
        ),
        mapOf(
            "title" to "대출을 받을 때 가장 먼저 확인해야 할 것은 무엇인가요?",
            "options" to listOf("1. 이자율", "2. 신청서 작성", "3. 은행 위치", "4. 연체 여부"),
            "comment" to "대출을 받을 때 가장 먼저 이자율을 확인하는 것이 중요합니다.",
            "answer" to "1. 이자율"
        ),
        mapOf(
            "title" to "체크카드를 사용할 때 이점은 무엇인가요?",
            "options" to listOf("1. 후불 결제", "2. 실시간 계좌 차감", "3. 연체금 발생", "4. 높은 이자"),
            "comment" to "체크카드는 사용 시 실시간으로 계좌에서 차감됩니다.",
            "answer" to "2. 실시간 계좌 차감"
        ),
        mapOf(
            "title" to "신용카드를 오랫동안 사용하지 않으면 어떤 일이 발생할 수 있나요?",
            "options" to listOf("1. 연체료 증가", "2. 신용 점수 변동 없음", "3. 카드 이용 제한", "4. 자동 결제"),
            "comment" to "신용카드를 오랫동안 사용하지 않으면 카드 이용 제한이 발생할 수 있습니다.",
            "answer" to "3. 카드 이용 제한"
        ),
        mapOf(
            "title" to "자동이체 설정 시 유용한 상황은 무엇인가요?",
            "options" to listOf("1. 매달 같은 금액을 결제할 때", "2. 일회성 지출 시", "3. 결제 일정을 임의로 변경할 때", "4. 수입이 없을 때"),
            "comment" to "자동이체는 매달 같은 금액을 결제할 때 유용합니다.",
            "answer" to "1. 매달 같은 금액을 결제할 때"
        ),
        mapOf(
            "title" to "대출을 받을 때 연체가 발생하지 않도록 하는 방법은 무엇인가요?",
            "options" to listOf("1. 상환 계획 세우기", "2. 높은 이자 선택", "3. 자주 대출", "4. 신용카드 사용"),
            "comment" to "대출 연체를 방지하기 위해서는 상환 계획을 세우는 것이 중요합니다.",
            "answer" to "1. 상환 계획 세우기"
        ),
        mapOf(
            "title" to "금융 거래 시 보안을 위해 중요한 것은 무엇인가요?",
            "options" to listOf("1. 타인과 비밀번호 공유", "2. 보안 프로그램 사용", "3. 공용 PC 사용", "4. 신용카드 정보 노출"),
            "comment" to "금융 거래 보안을 위해 보안 프로그램을 사용하는 것이 중요합니다.",
            "answer" to "2. 보안 프로그램 사용"
        ),
        mapOf(
            "title" to "신용카드 연회비를 내지 않으면 발생할 수 있는 일은?",
            "options" to listOf("1. 카드 혜택 유지", "2. 카드 사용 정지", "3. 포인트 증가", "4. 연체료 면제"),
            "comment" to "신용카드 연회비를 납부하지 않으면 카드 사용이 정지될 수 있습니다.",
            "answer" to "2. 카드 사용 정지"
        ),
        mapOf(
            "title" to "은행 예금의 주요 목적은 무엇인가요?",
            "options" to listOf("1. 즉시 사용", "2. 자산 증식", "3. 세금 납부", "4. 고위험 투자"),
            "comment" to "은행 예금은 자산 증식을 위한 안정적인 방법 중 하나입니다.",
            "answer" to "2. 자산 증식"
        ),
        mapOf(
            "title" to "신용카드를 사용한 후 어떤 문서를 확인해야 하나요?",
            "options" to listOf("1. 신용 보고서", "2. 청구서", "3. 대출 계약서", "4. 예금 증서"),
            "comment" to "신용카드를 사용한 후 발송되는 청구서를 반드시 확인해야 합니다.",
            "answer" to "2. 청구서"
        ),
        mapOf(
            "title" to "고정금리 대출의 특징은 무엇인가요?",
            "options" to listOf("1. 금리가 변동", "2. 일정한 금리", "3. 금리 인상만 가능", "4. 대출 불가"),
            "comment" to "고정금리 대출은 일정한 금리로 유지되는 대출입니다.",
            "answer" to "2. 일정한 금리"
        ),
        mapOf(
            "title" to "이체한도를 초과하면 어떤 일이 발생하나요?",
            "options" to listOf("1. 자동 연장", "2. 이체 실패", "3. 이자 증가", "4. 대출 승인"),
            "comment" to "이체 한도를 초과하면 이체가 실패할 수 있습니다.",
            "answer" to "2. 이체 실패"
        ),
        mapOf(
            "title" to "대출을 받을 때 가장 적합한 계획은 무엇인가요?",
            "options" to listOf("1. 상환 계획 수립", "2. 연체 대금 무시", "3. 이자율 무시", "4. 최대 한도 대출"),
            "comment" to "대출을 받을 때는 상환 계획을 수립하는 것이 중요합니다.",
            "answer" to "1. 상환 계획 수립"
        ),
        mapOf(
            "title" to "저축의 장점은 무엇인가요?",
            "options" to listOf("1. 즉시 소비 가능", "2. 자산 축소", "3. 미래를 위한 대비", "4. 세금 증가"),
            "comment" to "저축은 미래를 대비하는 중요한 자산 관리 방법입니다.",
            "answer" to "3. 미래를 위한 대비"
        ),
        mapOf(
            "title" to "ATM에서 현금을 입금할 때 필요한 것은?",
            "options" to listOf("1. 현금카드", "2. 신분증", "3. 패스포트", "4. 신용카드"),
            "comment" to "ATM에서 현금을 입금할 때는 현금카드를 사용합니다.",
            "answer" to "1. 현금카드"
        ),
        mapOf(
            "title" to "신용점수를 낮추지 않기 위해 중요한 것은?",
            "options" to listOf("1. 카드 대금 연체", "2. 대출 금액 초과", "3. 결제 기한 내 납부", "4. 무작위 대출 신청"),
            "comment" to "신용점수를 유지하기 위해 결제 기한 내에 납부하는 것이 중요합니다.",
            "answer" to "3. 결제 기한 내 납부"
        ),
        mapOf(
            "title" to "은행에서 대출을 받을 때 고려해야 할 사항은 무엇인가요?",
            "options" to listOf("1. 대출 금액", "2. 상환 능력", "3. 은행 위치", "4. 카드 혜택"),
            "comment" to "대출을 받을 때 상환 능력을 고려하는 것이 중요합니다.",
            "answer" to "2. 상환 능력"
        ),
        mapOf(
            "title" to "예금과 적금의 차이는 무엇인가요?",
            "options" to listOf("1. 매월 입금 여부", "2. 이자 지급 방식", "3. 신용 등급", "4. 대출 가능 여부"),
            "comment" to "예금은 일시불로 예치하고, 적금은 매월 정해진 금액을 입금하는 방식입니다.",
            "answer" to "1. 매월 입금 여부"
        ),
        mapOf(
            "title" to "체크카드 사용 시 중요한 것은 무엇인가요?",
            "options" to listOf("1. 신용 한도 초과", "2. 계좌 잔액 확인", "3. 카드 연회비", "4. 대출 한도"),
            "comment" to "체크카드는 계좌 잔액이 있어야 사용할 수 있습니다.",
            "answer" to "2. 계좌 잔액 확인"
        ),
        mapOf(
            "title" to "신용카드의 연체 시 발생할 수 있는 문제는?",
            "options" to listOf("1. 신용 점수 상승", "2. 연체 이자 부과", "3. 카드 보너스 제공", "4. 연회비 면제"),
            "comment" to "신용카드를 연체하면 연체 이자가 부과될 수 있습니다.",
            "answer" to "2. 연체 이자 부과"
        ),
        mapOf(
            "title" to "금융 거래 시 공인인증서가 필요한 경우는?",
            "options" to listOf("1. 계좌 개설", "2. 큰 금액 송금", "3. 카드 비밀번호 변경", "4. 소액 결제"),
            "comment" to "큰 금액을 송금할 때 공인인증서가 필요할 수 있습니다.",
            "answer" to "2. 큰 금액 송금"
        ),
        mapOf(
            "title" to "적금을 해지할 때 예상할 수 있는 결과는?",
            "options" to listOf("1. 모든 이자 지급", "2. 일부 이자 손실", "3. 추가 보너스 지급", "4. 원금 손실 없음"),
            "comment" to "적금을 조기 해지하면 일부 이자를 손실할 수 있습니다.",
            "answer" to "2. 일부 이자 손실"
        ),
        mapOf(
            "title" to "인터넷 뱅킹을 안전하게 사용하려면?",
            "options" to listOf("1. 공용 PC 사용", "2. 보안 프로그램 사용", "3. 비밀번호 노출", "4. 자동 로그인 설정"),
            "comment" to "인터넷 뱅킹을 사용할 때 보안 프로그램을 사용하는 것이 안전합니다.",
            "answer" to "2. 보안 프로그램 사용"
        ),
        mapOf(
            "title" to "대출을 받을 때 금리가 낮을수록 좋은 이유는?",
            "options" to listOf("1. 이자가 낮아짐", "2. 대출 한도 상승", "3. 상환 기간 연장", "4. 신용 점수 증가"),
            "comment" to "금리가 낮으면 상환해야 할 이자도 줄어듭니다.",
            "answer" to "1. 이자가 낮아짐"
        ),
        mapOf(
            "title" to "신용카드와 체크카드의 주요 차이점은?",
            "options" to listOf("1. 실시간 결제 여부", "2. 카드 디자인", "3. 연회비 유무", "4. 대출 한도"),
            "comment" to "체크카드는 실시간으로 계좌에서 금액이 차감됩니다.",
            "answer" to "1. 실시간 결제 여부"
        ),
        mapOf(
            "title" to "은행에서 제공하는 예금 상품의 주요 목적은?",
            "options" to listOf("1. 대출 보증", "2. 자산 보호", "3. 세금 감면", "4. 신용 등급 상승"),
            "comment" to "은행 예금 상품은 자산 보호 및 증식을 목적으로 합니다.",
            "answer" to "2. 자산 보호"
        ),
        mapOf(
            "title" to "금융 사기를 예방하기 위한 최선의 방법은?",
            "options" to listOf("1. 의심스러운 링크 클릭", "2. 금융 정보 공유", "3. 보안 프로그램 설치", "4. 비밀번호 노출"),
            "comment" to "금융 사기를 예방하기 위해 보안 프로그램을 설치하는 것이 좋습니다.",
            "answer" to "3. 보안 프로그램 설치"
        ),
        mapOf(
            "title" to "은행에서 계좌 개설 시 필요한 것은?",
            "options" to listOf("1. 현금카드", "2. 신분증", "3. 비밀번호", "4. 공인인증서"),
            "comment" to "은행에서 계좌를 개설할 때는 신분증이 필요합니다.",
            "answer" to "2. 신분증"
        ),
        mapOf(
            "title" to "이체 수수료를 줄이기 위한 방법은?",
            "options" to listOf("1. 은행 방문", "2. 인터넷 뱅킹 사용", "3. 현금 사용", "4. ATM 사용"),
            "comment" to "인터넷 뱅킹을 사용하면 이체 수수료를 줄일 수 있습니다.",
            "answer" to "2. 인터넷 뱅킹 사용"
        ),
        mapOf(
            "title" to "체크카드 사용 시 발생하지 않는 것은?",
            "options" to listOf("1. 신용 한도 초과", "2. 실시간 계좌 차감", "3. 잔액 부족 시 결제 불가", "4. 대출 이자 발생"),
            "comment" to "체크카드는 신용 한도 초과가 발생하지 않습니다.",
            "answer" to "1. 신용 한도 초과"
        ),
        mapOf(
            "title" to "예금 만기 시 할 수 있는 선택은?",
            "options" to listOf("1. 원금 인출", "2. 대출 연장", "3. 계좌 폐쇄", "4. 신용 점수 변경"),
            "comment" to "예금이 만기되면 원금을 인출할 수 있습니다.",
            "answer" to "1. 원금 인출"
        ),
        mapOf(
            "title" to "신용카드 연체가 계속될 경우 발생하는 문제는?",
            "options" to listOf("1. 이자 면제", "2. 신용 점수 하락", "3. 포인트 증가", "4. 카드 혜택 증가"),
            "comment" to "신용카드 연체가 계속되면 신용 점수가 하락할 수 있습니다.",
            "answer" to "2. 신용 점수 하락"
        ),
        mapOf(
            "title" to "대출 상환을 연체할 경우 발생하는 것은?",
            "options" to listOf("1. 상환액 면제", "2. 연체 이자 증가", "3. 대출 한도 상승", "4. 추가 대출"),
            "comment" to "대출 상환을 연체하면 연체 이자가 증가할 수 있습니다.",
            "answer" to "2. 연체 이자 증가"
        ),
        mapOf(
            "title" to "신용카드 사용 시 혜택 중 하나는?",
            "options" to listOf("1. 포인트 적립", "2. 이자 면제", "3. 대출 보증", "4. 수수료 면제"),
            "comment" to "신용카드 사용 시 포인트 적립 등의 혜택을 받을 수 있습니다.",
            "answer" to "1. 포인트 적립"
        ),
        mapOf(
            "title" to "은행에서 제공하는 정기 예금의 특징은?",
            "options" to listOf("1. 자주 입출금 가능", "2. 고정된 기간 동안 예치", "3. 높은 위험성", "4. 대출 가능"),
            "comment" to "정기 예금은 고정된 기간 동안 예치되어야 합니다.",
            "answer" to "2. 고정된 기간 동안 예치"
        ),
        mapOf(
            "title" to "체크카드와 신용카드 중 실시간으로 금액이 차감되는 카드는?",
            "options" to listOf("1. 신용카드", "2. 체크카드", "3. 연체 카드", "4. 포인트 카드"),
            "comment" to "체크카드는 실시간으로 금액이 계좌에서 차감됩니다.",
            "answer" to "2. 체크카드"
        )
    )
    val randomQuiz = remember { quiz[Random.nextInt(quiz.size)] }

    val options = randomQuiz["options"] as? List<*>

    val selectedOptionIndex = remember { mutableIntStateOf(-1) }

    val showDialog = remember { mutableStateOf(false) }
    val dialogMessage = remember { mutableStateOf("") }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isLoading) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(32.dp),
                        )
                        .background(Color(0xFFE9F8FE), shape = RoundedCornerShape(32.dp))
                        .padding(32.dp)
                        .size(200.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CustomLoadingIndicator()
                }
            } else {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(32.dp),
                        )
                        .background(Color(0xFFE9F8FE), shape = RoundedCornerShape(32.dp))
                        .padding(32.dp)
                ) {
                    Text(
                        text = "키즈 쑥쑥 퀴즈",
                        color = Color(0xFF6DCEF5),
                        fontWeight = FontWeight.Bold,
                        style = FontSizes.h16
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = randomQuiz["title"] as String,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        style = FontSizes.h24
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // 4개의 보기 그리드
                    Column {
                        options?.chunked(2)?.forEachIndexed { rowIndex, rowOptions ->
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                rowOptions.forEachIndexed { index, option ->
                                    val optionText = option as? String ?: ""
                                    val globalIndex = rowIndex * 2 + index // 전체 인덱스를 추적

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(4.dp)
                                            .padding(8.dp)
                                            .clickable(
                                                onClick = {
                                                    selectedOptionIndex.value = globalIndex
                                                },
                                                indication = null,
                                                interactionSource = remember { MutableInteractionSource() }
                                            ),
                                        contentAlignment = if (index == 0) Alignment.CenterStart else Alignment.CenterEnd
                                    ) {
                                        Text(
                                            text = optionText,
                                            color = if (selectedOptionIndex.value == globalIndex) Color(0xFF0000FF) else Color.Black, // 선택된 경우 파란색으로 변경
                                            style = FontSizes.h20,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

            }
            if (selectedOptionIndex.value == -1) {
                // LightGrayButton을 표시하는 경우
                LightGrayButton(
                    onClick = {

                    },
                    modifier = Modifier.width(380.dp),
                    height = 40,
                    text = "제출하기"
                )
            } else {
                // BlueButton을 표시하는 경우
                BlueButton(
                    onClick = {
                        val selectedOption = options?.getOrNull(selectedOptionIndex.value)
                        if (selectedOption == randomQuiz["answer"]) {
                            dialogMessage.value = "정답입니다!"
                        } else {
                            dialogMessage.value = "오답입니다!"
                        }
                        showDialog.value = true
                    },
                    modifier = Modifier.width(380.dp),
                    height = 40,
                    text = "제출하기"
                )
            }
            Image(
                painter = painterResource(id = R.drawable.logo_full), // 이미지 리소스를 불러옵니다.
                contentDescription = "Logo",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // 원하는 위치에 배치할 수 있도록 설정
                    .size(258.dp), // 원하는 크기 설정
            )

        }
        BottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter), navController
        )
    }
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
                if (dialogMessage.value == "정답입니다!") {
                    showDialog.value = true
                }},
            title = {
                Text(
                    text = dialogMessage.value,
                    color = if (dialogMessage.value == "정답입니다!") Color(0xFF77DD77) else Color(0xFFFFA1B0),
                    fontWeight = FontWeight.Bold // Makes the text bold
                )
            },
            text = {
                Text(
                    text = if (dialogMessage.value == "정답입니다!") {
                        randomQuiz["comment"] as String
                    } else {
                        "거의 다 왔어요! 다시 생각해보아요!"
                    },
                    fontWeight = FontWeight.Bold
                )
            },
            containerColor = Color.White,
            confirmButton = {
                BlueButton(
                    onClick = {
                        showDialog.value = false
                        if (dialogMessage.value == "정답입니다!") {
                            navController.navigate("mainPage") // Navigate to the main page
                        }
                    },
                    height = 40,
                    modifier = Modifier.width(260.dp), // 원하는 너비 설정
                    elevation = 0,
                    text = "확인"
                )
            }
        )
    }
}

@Composable
fun CustomLoadingIndicator() {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier
            .size(150.dp)
            .graphicsLayer {
                rotationZ = rotation
            }
            .background(
                brush = Brush.verticalGradient( // 수직 방향으로 그라데이션 적용
                    colors = listOf(
                        Color(0xFF99DDF8), // 시작 색상
                        Color(0xFF6DCEF5)  // 끝 색상
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "로딩", color = Color.White, fontWeight = FontWeight.Bold)
    }
}
