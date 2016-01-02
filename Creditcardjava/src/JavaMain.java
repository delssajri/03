/**
 * Created by Tatiana on 01.01.2016.
 */

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
