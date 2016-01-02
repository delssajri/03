/**
 * Created by Tatiana on 01.01.2016.
 */

class IncompleteDateException extends Exception {
    public IncompleteDateException() {}

    public IncompleteDateException(String message)
    {
        super(message);
    }
}
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
        month = "";
        year = "";
    }
    public ExpirationDate (String month, String year) {
        this.month = month;
        this.year = year;
    }
    private String month;
    private String year;
    public String GetMonth() {
        return month;
    }
    private void SetMonth(String month) {
        this.month = month;
    }
    public String GetYear() {
        return year;
    }
    private void SetYear(String year) {
        this.year = year;
    }
    public void SetDate(String monthYear) throws IncompleteDateException, WrongDateException{
        String month = "";
        String year = "";
        if (monthYear.length() > 0) {
            month = monthYear.substring(0, Integer.min(monthYear.length(), 2));
        }
        if (monthYear.length() > 2) {
            year = monthYear.substring(2, Integer.min(monthYear.length(), 4));
        }
        SetYear(year);
        SetMonth(month);
        CheckDate();
    }
    private void CheckDate() throws IncompleteDateException, WrongDateException {
        if (year.length() < 2 || month.length() < 2)
            throw new IncompleteDateException();
        int y = Integer.parseInt(year, 10);
        if (y > 99 || y < 0) {
            throw new WrongDateException();
        }
        int m = Integer.parseInt(month, 10);
        if ( m > 12 || m < 1) {
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
    public ExpirationDate GetExpirationDate (){
        return new ExpirationDate(expiration.GetMonth(), expiration.GetYear());
    }
    public void SetExpirationDate (String monthYear) throws IncompleteDateException, WrongDateException {
        expiration.SetDate(monthYear);
    }
    public String GetCvv(){
        return cvv;
    }
    public void SetCvv(String cvv) throws WrongNumberException, IncompleteNumberException {
        this.cvv = cvv;
        CheckCvv();
    }
    public  void CheckCvv() throws WrongNumberException, IncompleteNumberException {
        if (number.length() < 3)
            throw new IncompleteNumberException();
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
    public void OnUserInput (String text, int pos) throws WrongNumberException, IncompleteNumberException, WrongDateException, IncompleteDateException {

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
    public String GetText(){
        String number = super.GetPayment().GetNumber();
        String lastDigits = "<font color=\"black\">" + number.substring(12) + "</font>";
        ExpirationDate expirationDate = super.GetPayment().GetExpirationDate();
        String month = "font color=\"gray\">MM</font>";
        String year = "font color=\"gray\">/YY</font>";
        String cvv = "</font> <font color=\"gray\"> CVV</font>";
        if (expirationDate.GetMonth().length() > 0) {
            month = "font color=\"black\">" + expirationDate.GetMonth() + "</font>";
        }
        if (expirationDate.GetYear().length() > 0) {
            year = "font color=\"black\">/" + expirationDate.GetYear() + "</font>";
        }
        return lastDigits + month + year + cvv;

    }
    public void OnUserInput (String text, int pos) throws WrongDateException, IncompleteDateException {
        ExpirationDate expirationDate = super.GetPayment().GetExpirationDate();
        String original_text = expirationDate.GetMonth() + expirationDate.GetYear();
        if (4 <= original_text.length()) {
            throw new WrongDateException();
        }

        String updated_text;
        if (pos < 0) {
            updated_text = original_text + text;
        } else {
            updated_text = original_text.substring(0, pos) + text + original_text.substring(pos);
        }
        super.GetPayment().SetExpirationDate(updated_text);
    }
    public void OnDelete (int pos, int direction) throws WrongNumberException, IncompleteNumberException {

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
class CvvView extends PaymentView {
    public CvvView(Payment payment){
        super(payment);
    }
    public String GetText(){
        String number = super.GetPayment().GetNumber();
        String lastDigits = "<font color=\"black\">" + number.substring(12) + "</font>";
        ExpirationDate expirationDate = super.GetPayment().GetExpirationDate();
        String month = "font color=\"black\">" + expirationDate.GetMonth() + "</font>";
        String year = "font color=\"black\">/" + expirationDate.GetYear() + "</font>";
        String cvv = "</font> <font color=\"gray\"> CVV</font>";
        if (super.GetPayment().GetCvv().length() > 0) {
            cvv = "font color=\"black\">" + super.GetPayment().GetCvv() + "</font>";
        }
        return lastDigits + month + year + cvv;

    }
    public void OnUserInput (String text, int pos) throws WrongNumberException, IncompleteNumberException {
        String original_text = super.GetPayment().GetCvv();
        if (3 <= original_text.length()) {
            throw new WrongNumberException();
        }

        String updated_text;
        if (pos < 0) {
            updated_text = original_text + text;
        } else {
            updated_text = original_text.substring(0, pos) + text + original_text.substring(pos);
        }
        super.GetPayment().SetCvv(updated_text);
    }
    public void OnDelete (int pos, int direction) throws WrongNumberException, IncompleteNumberException {

    }
    public CardIcon GetCardIcon (){
        return CardIcon.back;
    }
}
class PaymentForm {
    private Payment payment;
    private PaymentView[] views;
    private NumberView numberView;
    private DateView dateView;
    private CvvView cvvView;
    private int currentView;
    public PaymentForm(){
        payment = new Payment();
        views = new PaymentView[3];
        views[0] = new NumberView(payment);
        views[1] = new DateView(payment);
        views[2] = new CvvView(payment);
        currentView = 0;
    }
    public String GetText(){
        return views[currentView].GetText();
    }
    public void OnUserInput (String text, int pos) throws WrongNumberException, IncompleteNumberException, WrongDateException, IncompleteDateException {
        views[currentView].OnUserInput(text, pos);
        if ( currentView < 2)
            currentView += 1;
    }
    public void OnDelete (int pos, int direction) throws WrongNumberException, IncompleteNumberException {
        views[currentView].OnDelete(pos, direction);
    }
    public CardIcon GetCardIcon (){
        return views[currentView].GetCardIcon();
    }

}
public class JavaMain {
    public static void main (String[] args){
        TestNumber();
    }
    private static void TestNumber() {
        PaymentForm paymentForm = new PaymentForm();
        String cardNumber = "42763800236769481234567";
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
        }catch (WrongDateException e) {
            System.out.println("Wrong date");
        } catch (IncompleteDateException e) {
            System.out.println("Incomplete date");
        }

        System.out.println(digit + ": <" + paymentForm.GetText() + ">, icon: " + paymentForm.GetCardIcon());
    }
}
