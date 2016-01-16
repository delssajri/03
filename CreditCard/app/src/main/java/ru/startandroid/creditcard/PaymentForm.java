/**
 * Created by Tatiana on 03.01.2016.
 */

package ru.startandroid.creditcard;

import java.io.IOException;
import java.lang.Math;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

class IncompletePaymentException extends Exception {
    public IncompletePaymentException() {}
}
class WrongPaymentException extends Exception {
    public WrongPaymentException() {}
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
    public void SetDate(String monthYear) throws IncompletePaymentException, WrongPaymentException{
        String month = "";
        String year = "";
        if (monthYear.length() > 0) {
            month = monthYear.substring(0, Math.min(monthYear.length(), 2));
        }
        if (monthYear.length() > 2) {
            year = monthYear.substring(2, Math.min(monthYear.length(), 4));
        }
        SetYear(year);
        SetMonth(month);
        CheckDate();
    }
    public void CheckDate() throws IncompletePaymentException, WrongPaymentException {
        if (year.length() < 2 || month.length() < 2)
            throw new IncompletePaymentException();
        int y = Integer.parseInt(year, 10);
        if (y > 99 || y < 0) {
            throw new WrongPaymentException();
        }
        int m = Integer.parseInt(month, 10);
        if ( m > 12 || m < 1) {
            throw new WrongPaymentException();
        }
    }
}
class Payment {
    private String number;
    private String cvv;
    private ExpirationDate expiration;
    private boolean confirmed;
    public Payment(){
        number = "";
        cvv = "";
        expiration = new ExpirationDate();
        confirmed = false;
    }
    public String GetNumber(){
        return number;
    }
    public void SetNumber(String number) throws WrongPaymentException, IncompletePaymentException {
        this.number = number;
        CheckNumber();
    }
    public  void CheckNumber() throws WrongPaymentException, IncompletePaymentException {
        if (number.length() < 16)
            throw new IncompletePaymentException();
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
            throw new WrongPaymentException();
    }
    public CardType GetCardType (){
        if (number.length() == 0){
            return CardType.unknown;
        }
        if (number.charAt(0) == '5'){
            return CardType.mastercard;
        }
        if (number.charAt(0) == '4'){
            return CardType.visa;
        }
        return CardType.unknown;
    }
    public ExpirationDate GetExpirationDate (){
        return new ExpirationDate(expiration.GetMonth(), expiration.GetYear());
    }
    public void SetExpirationDate (String monthYear) throws IncompletePaymentException, WrongPaymentException {
        expiration.SetDate(monthYear);
    }
    public String GetCvv(){
        return cvv;
    }
    public void SetCvv(String cvv) throws WrongPaymentException, IncompletePaymentException {
        this.cvv = cvv;
        CheckCvv();
    }
    public  void CheckCvv() throws WrongPaymentException, IncompletePaymentException {
        if (cvv.length() < 3)
            throw new IncompletePaymentException();
    }
    public boolean Valid(){
        try {
            CheckNumber();
            expiration.CheckDate();
            CheckCvv();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public boolean GetConfirmed (){
        return confirmed;
    }
    public void Confirm () {
        confirmed = true;
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
    public  int GetCursorPos() {
        return 0;
    }
    public String GetText(){
        return null;
    }
    public void OnUserInput (String text, int pos) throws WrongPaymentException, IncompletePaymentException {

    }
    public void OnDelete (int pos) throws WrongPaymentException, IncompletePaymentException {

    }
    public CardIcon GetCardIcon (){
        return CardIcon.unknown;
    }
    protected String PadWithSpaces(String s, int fieldWidth) {
        if (s.length() >= fieldWidth) {
            return s;
        }
        for (int i = s.length(); i < fieldWidth; i++) {
            s += "&nbsp;";
        }
        return s;
    }
}
class NumberView extends PaymentView {
    public NumberView(Payment payment){
        super(payment);
    }
    public int GetCursorPos(){
        String text = super.GetPayment().GetNumber();
        if (text.length() == 0)
            return 0;
        return GetText().length();
    }
    public String GetText(){
        String text = super.GetPayment().GetNumber();
        if (text.length() == 0){
            return "<font color=\"gray\">1234 5678 9012 3456</font>";
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

    public void OnUserInput (String text, int pos) throws WrongPaymentException, IncompletePaymentException {
        String original_text = super.GetPayment().GetNumber();
        if (16 <= original_text.length()) {
            throw new WrongPaymentException();
        }

        String updated_text;
        if (pos < 0) {
            updated_text = original_text + text;
        } else {
            updated_text = original_text.substring(0, pos) + text + original_text.substring(pos);
        }
        super.GetPayment().SetNumber(updated_text);
    }
    public void OnDelete (int pos) throws WrongPaymentException, IncompletePaymentException {
        String original_text = super.GetPayment().GetNumber();
        if (0 == original_text.length() || pos >= 0) {
            return;
        }
        String updated_text = original_text.substring(0, original_text.length() - 1);
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
    public  int GetCursorPos(){
        ExpirationDate expirationDate = super.GetPayment().GetExpirationDate();
        if (expirationDate.GetMonth().length() == 1) {
            return 8+1;
        }
        if (expirationDate.GetMonth().length() == 2) {
            return 8+3+expirationDate.GetYear().length();
        }
        return 8;
    }
    public String GetText(){
        String number = super.GetPayment().GetNumber();
        String lastDigits = "<font color=\"gray\">XXXX</font>&nbsp;&nbsp;&nbsp;&nbsp;";
        ExpirationDate expirationDate = super.GetPayment().GetExpirationDate();
        String monthYear = "<font color=\"gray\">MM/YY</font>";
        String cvv = "<font color=\"gray\">CVV</font>";
        if (number.length() == 16){
            lastDigits = "<font color=\"black\">" + number.substring(12) + "</font>&nbsp;&nbsp;&nbsp;&nbsp;";
        } else if (number.length() > 12) {
            lastDigits = "<font color=\"red\">" + PadWithSpaces(number.substring(12), 4) + "</font>&nbsp;&nbsp;&nbsp;&nbsp;";
        }
        if (expirationDate.GetMonth().length() > 0) {
            monthYear = "<font color=\"black\">" +
                    PadWithSpaces(
                            expirationDate.GetMonth() + "/" + expirationDate.GetYear(),
                            5
                    ) +
                    "</font>";
        }
        monthYear += "&nbsp;&nbsp;&nbsp;&nbsp;";
        if (super.GetPayment().GetCvv().length() > 0) {
            cvv = "<font color=\"black\">" + super.GetPayment().GetCvv() + "</font>";
        }
        return lastDigits + monthYear + cvv;

    }
    public void OnUserInput (String text, int pos) throws WrongPaymentException, IncompletePaymentException {
        ExpirationDate expirationDate = super.GetPayment().GetExpirationDate();
        String original_text = expirationDate.GetMonth() + expirationDate.GetYear();
        if (4 <= original_text.length()) {
            throw new WrongPaymentException();
        }

        String updated_text;
        if (pos < 0) {
            updated_text = original_text + text;
        } else {
            updated_text = original_text.substring(0, pos) + text + original_text.substring(pos);
        }
        super.GetPayment().SetExpirationDate(updated_text);
    }
    public void OnDelete (int pos) throws WrongPaymentException, IncompletePaymentException {
        ExpirationDate expirationDate = super.GetPayment().GetExpirationDate();
        String original_text = expirationDate.GetMonth() + expirationDate.GetYear();
        if (0 == original_text.length() || pos >= 0) {
            return;
        }
        String updated_text = original_text.substring(0, original_text.length() - 1);
        super.GetPayment().SetExpirationDate(updated_text);
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
    public  int GetCursorPos() {
        return 8 + (2 + 1 + 2) + 4 + super.GetPayment().GetCvv().length();
    }
    public String GetText(){
        String number = super.GetPayment().GetNumber();
        String lastDigits = "<font color=\"gray\">XXXX</font>&nbsp;&nbsp;&nbsp;&nbsp;";
        ExpirationDate expirationDate = super.GetPayment().GetExpirationDate();
        String month = "<font color=\"gray\">MM</font>";
        String year = "<font color=\"gray\">/YY</font>";
        String cvv = "<font color=\"gray\">CVV</font>";
        if (number.length() == 16){
            lastDigits = "<font color=\"black\">" + number.substring(12) + "</font>&nbsp;&nbsp;&nbsp;&nbsp;";
        } else if (number.length() > 12) {
            lastDigits = "<font color=\"red\">" + PadWithSpaces(number.substring(12), 4) + "</font>&nbsp;&nbsp;&nbsp;&nbsp;";
        }
        if (expirationDate.GetMonth().length() > 0) {
            month = "<font color=\"black\">" + expirationDate.GetMonth() + "</font>";
        }
        if (expirationDate.GetYear().length() > 0) {
            year = "<font color=\"black\">/" + expirationDate.GetYear() + "</font>";
        }
        year += "&nbsp;&nbsp;&nbsp;&nbsp;";
        if (super.GetPayment().GetCvv().length() > 0) {
            cvv = "<font color=\"black\">" + super.GetPayment().GetCvv() + "</font>";
        }

        return lastDigits + month + year + cvv;

    }
    public void OnUserInput (String text, int pos) throws WrongPaymentException, IncompletePaymentException {
        String original_text = super.GetPayment().GetCvv();
        if (3 <= original_text.length()) {
            throw new WrongPaymentException();
        }

        String updated_text;
        if (pos < 0) {
            updated_text = original_text + text;
        } else {
            updated_text = original_text.substring(0, pos) + text + original_text.substring(pos);
        }
        super.GetPayment().SetCvv(updated_text);
    }
    public void OnDelete (int pos) throws WrongPaymentException, IncompletePaymentException {
        String original_text = super.GetPayment().GetCvv();
        if (0 == original_text.length() || pos >= 0) {
            return;
        }
        String updated_text = original_text.substring(0, original_text.length() - 1);
        super.GetPayment().SetCvv(updated_text);
    }
    public CardIcon GetCardIcon (){
        return CardIcon.back;
    }
}
class PaymentInfo {
    public String owner;
    public String number;
    public String expiresAt;
    public String cvv;
}
class PaymentRequest {
    public String action;
    public PaymentInfo payment;
}
public class PaymentForm {
    private Payment payment;
    private PaymentView[] views;
    private int currentView;
    public PaymentForm(){
        payment = new Payment();
        views = new PaymentView[3];
        views[0] = new NumberView(payment);
        views[1] = new DateView(payment);
        views[2] = new CvvView(payment);
        currentView = 0;
    }
    public  int GetCursorPos() {
        return views[currentView].GetCursorPos();
    }
    public String GetText(){
        return views[currentView].GetText();
    }
    public void OnUserInput (String text, int pos) throws WrongPaymentException, IncompletePaymentException {
        views[currentView].OnUserInput(text, pos);
        if ( currentView < 2)
            currentView += 1;
    }
    public void OnDelete (int pos) throws WrongPaymentException, IncompletePaymentException {
        views[currentView].OnDelete(pos);
    }
    public CardIcon GetCardIcon (){
        return views[currentView].GetCardIcon();
    }

    public Payment GetPayment() {
        return payment;
    }
    public boolean GetPrevEnabled(){
        if (currentView > 0)
            return true;
        return false;
    }
    public boolean GetNextEnabled(){
        if (currentView < views.length - 1)
            return true;
        return false;
    }
    public void Next(){
        if (currentView < views.length - 1) {
            currentView += 1;
        }
    }
    public void Prev(){
        if (currentView > 0) {
            currentView -= 1;
        }
    }
    public String GetPaymentRequest() throws IOException {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.action = "placeOrder";
        paymentRequest.payment = new PaymentInfo();
        paymentRequest.payment.owner = "Tanya";
        paymentRequest.payment.number = GetPayment().GetNumber();
        ExpirationDate expirationDate = GetPayment().GetExpirationDate();
        paymentRequest.payment.expiresAt = expirationDate.GetMonth() + expirationDate.GetYear();
        paymentRequest.payment.cvv = GetPayment().GetCvv();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(paymentRequest);
    }
}