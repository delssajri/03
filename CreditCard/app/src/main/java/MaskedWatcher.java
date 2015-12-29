import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Tatiana on 28.12.2015.
 */
public class MaskedWatcher implements TextWatcher {

    private String mMask;
    String mResult = "";

    public MaskedWatcher(String mask) {
        mMask = mask;
    }

    @Override
    public void afterTextChanged(Editable s) {

        String mask = mMask;
        String value = s.toString();

        if (value.equals(mResult))
            return;


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start,
                              int before, int count) {
    }

    public static String removeCharAt(String s, int pos) {

        StringBuffer buffer = new StringBuffer(s.length() - 1);
        buffer.append(s.substring(0, pos)).append(s.substring(pos + 1));
        return buffer.toString();

    }
}