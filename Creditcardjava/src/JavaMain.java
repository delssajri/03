/**
 * Created by Tatiana on 01.01.2016.
 */

class WrongDateException extends Exception {
    public WrongDateException() {}

    public WrongDateException(String message)
    {
        super(message);
    }
}

class WrongNumberException extends Exception {
    public WrongNumberException() {}

    public WrongNumberException(String message)
    {
        super(message);
    }
}
enum CardType {
    unknown, visa, mastercard
}
enum CardIcon {
    unknown, visafront, mastercardfront, back
}
class ExpirationDate {
    public ExpirationDate () {
        month = -1;
        year = -1;
    }
    public ExpirationDate (int month, int year) {
        this.month = month;
        this.year = year;
    }
    private int month;
    private int year;
    public int GetMonth() {
        return month;
    }
    public void SetMonth(int month) throws WrongDateException {
        this.month = month;
        if (month > 12 || month < 1) {
            throw new WrongDateException();
        }
    }
    public int GetYear() {
        return year;
    }
    public void SetYear(int year) throws WrongDateException {
        this.year = year;
        if (year > 99 || year < 0) {
            throw new WrongDateException();
        }
    }

}
class Payment {
    private String number;
    private String cvv;
    private ExpirationDate expiration;
    public Payment(){
        number = "";
        cvv = "";
        expiration = new ExpirationDate();
    }
    public String GetNumber(){
        return number;
    }
    public void SetNumber(String number){
        this.number = number;
    }
    public CardType GetCardType (){
        if (number.length() == 0){
            return CardType.unknown;
        }
        if (number.charAt(0) == '4'){
            return CardType.mastercard;
        }
        if (number.charAt(0) == '5'){
            return CardType.visa;
        }
        return CardType.unknown;
    }
}
class  PaymentView {
    private Payment payment;
    public PaymentView (Payment payment){
        this.payment = payment;
    }
    public Payment GetPayment (){
        return payment;
    }
}
class NumberView extends PaymentView {
    public NumberView(Payment payment){
        super(payment);
    }
    public  int GetCursorPos(){
        return  0;
    }
    public String GetText(){
        String text = super.GetPayment().GetNumber();
        if (text.length() == 0){
            return text;
        }
        String otext = "";
        for (int i = 0; i < text.length(); i++) {
            if ( 0 < i && i % 4 == 0)
                otext += " ";
            otext += text.charAt(i);
        }
        if (text.length() > 0 && text.length() % 4 == 0)
            otext += " ";
            return otext;
        }

    public void SetText (String text) {
    }
    public void OnUserInput (String text, int pos) throws WrongNumberException {
        String original_text = super.GetPayment().GetNumber();
        if (16 <= original_text.length()) {
            throw new WrongNumberException();
        }

        String updated_text;
        if (pos < 0) {
            updated_text = original_text + text;
        } else {
            updated_text = original_text.substring(0, pos) + text + original_text.substring(pos);
        }
        super.GetPayment().SetNumber(updated_text);
    }
    public void OnDelete (int pos, int direction){
        String original_text = super.GetPayment().GetNumber();
        if (0 == original_text.length()) {
            return;
        }
        int real_pos = 0;
        for (int i = 0; i < original_text.length(); i++){
            if (i > 0 && i % 5 != 0){
                real_pos++;
            }
        }
        String updated_text = original_text.substring(0, pos) + original_text.substring(pos + 1);
        super.GetPayment().SetNumber(updated_text);
    }
    public CardIcon GetCardIcon (){
        CardType cardType = super.GetPayment().GetCardType();
        if (cardType == CardType.visa)
            return CardIcon.visafront;
        if (cardType == CardType.mastercard){
            return CardIcon.mastercardfront;
        }
        return CardIcon.unknown;
    }
}
class DateView extends PaymentView {
    public DateView(Payment payment){
        super(payment);
    }
}
class CvvView extends PaymentView {
    public CvvView(Payment payment){
        super(payment);
    }
}
public class JavaMain {
    public static void main (String[] args){
        TestNumber();

    }
    private static void TestNumber() {
        Payment payment = new Payment();
        NumberView numberView = new NumberView(payment);
        String cardNumber = "4276380023676948";
        System.out.println("initial:" + numberView.GetText() + "icon: " + numberView.GetCardIcon());
        for (int i = 0; i < cardNumber.length(); i++){
            TestOneDigit(numberView, "" + cardNumber.charAt(i));
        }
    }
    private static void TestOneDigit(NumberView numberView, String digit){
        try {
            numberView.OnUserInput(digit, -1);
        } catch (WrongNumberException e) {
            e.printStackTrace();
        }
        System.out.println(digit + ": <" + numberView.GetText() + ">, icon: " + numberView.GetCardIcon());
    }
}
