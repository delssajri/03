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
class IncompleteNumberException extends Exception {
    public IncompleteNumberException() {}

    public IncompleteNumberException(String message)
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
    public void SetNumber(String number) throws WrongNumberException, IncompleteNumberException {
        this.number = number;
        CheckNumber();
    }
    public  void CheckNumber() throws WrongNumberException, IncompleteNumberException {
        if (number.length() < 16)
            throw new IncompleteNumberException();
        int sum = 0;
        for (int i = 0; i < number.length(); i++){
           int r = number.charAt(i) - '0';
           if (i % 2 == 0)
               r *= 2;
           if (r > 9)
               r = (r / 10) + (r % 10);
           sum += r;
        }
        if (sum % 10 != 0)
            throw new WrongNumberException();
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
class PaymentView {
    private Payment payment;
    public PaymentView (Payment payment){
        this.payment = payment;
    }
    public Payment GetPayment (){
        return payment;
    }
    public String GetText(){
        return null;
    }
    public void OnUserInput (String text, int pos) throws WrongNumberException, IncompleteNumberException {

    }
    public void OnDelete (int pos, int direction) throws WrongNumberException, IncompleteNumberException {

    }
    public CardIcon GetCardIcon (){
        return CardIcon.unknown;
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


    public void OnUserInput (String text, int pos) throws WrongNumberException, IncompleteNumberException {
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
    public void OnDelete (int pos, int direction) throws WrongNumberException, IncompleteNumberException {
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
class PaymentForm {
    private Payment payment;
    private NumberView numberView;
    private DateView dateView;
    private CvvView cvvView;
    private PaymentView currentView;
    public PaymentForm(){
        payment = new Payment();
        numberView = new NumberView(payment);
        dateView = new DateView(payment);
        cvvView = new CvvView(payment);
        currentView = numberView;
    }
    public String GetText(){
        return currentView.GetText();
    }
    public void OnUserInput (String text, int pos) throws WrongNumberException, IncompleteNumberException {
        currentView.OnUserInput(text, pos);
    }
    public void OnDelete (int pos, int direction) throws WrongNumberException, IncompleteNumberException {
        currentView.OnDelete(pos, direction);
    }
    public CardIcon GetCardIcon (){
        return currentView.GetCardIcon();
    }

}
public class JavaMain {
    public static void main (String[] args){
        TestNumber();

    }
    private static void TestNumber() {
        PaymentForm paymentForm = new PaymentForm();
        String cardNumber = "4276380023676948";
        System.out.println("initial:" + paymentForm.GetText() + "icon: " + paymentForm.GetCardIcon());
        for (int i = 0; i < cardNumber.length(); i++){
            TestOneDigit(paymentForm, "" + cardNumber.charAt(i));
        }
    }
    private static void TestOneDigit(PaymentForm paymentForm, String digit){
        try {
            paymentForm.OnUserInput(digit, -1);
        } catch (WrongNumberException e) {
            System.out.println("Wrong number");
        } catch (IncompleteNumberException e) {
            System.out.println("Incomplete number");
        }

        System.out.println(digit + ": <" + paymentForm.GetText() + ">, icon: " + paymentForm.GetCardIcon());
    }
}
