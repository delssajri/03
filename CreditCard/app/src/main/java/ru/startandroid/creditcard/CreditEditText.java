package ru.startandroid.creditcard;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

/**
 * Created by Tatiana on 15.01.2016.
 */
public class CreditEditText extends EditText {
    int fixedSelection = 0;
    public CreditEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CreditEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CreditEditText(Context context) {
        super(context);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        //Passing FALSE as the SECOND ARGUMENT (fullEditor) to the constructor
        // will result in the key events continuing to be passed in to this
        // view.  Use our special BaseInputConnection-derived view
        InputConnectionAccomodatingLatinIMETypeNullIssues baseInputConnection =
                new InputConnectionAccomodatingLatinIMETypeNullIssues(this, false);

        //In some cases an IME may be able to display an arbitrary label for a
        // command the user can perform, which you can specify here.  A null value
        // here asks for the default for this key, which is usually something
        // like Done.
        outAttrs.actionLabel = null;

        //Special content type for when no explicit type has been specified.
        // This should be interpreted (by the IME that invoked
        // onCreateInputConnection())to mean that the target InputConnection
        // is not rich, it can not process and show things like candidate text
        // nor retrieve the current text, so the input method will need to run
        // in a limited "generate key events" mode.  This disables the more
        // sophisticated kinds of editing that use a text buffer.
        outAttrs.inputType = InputType.TYPE_CLASS_NUMBER;

        //This creates a Done key on the IME keyboard if you need one
        //outAttrs.imeOptions = EditorInfo.IME_ACTION_DONE;

        return baseInputConnection;
    }
    private int minPossibleSelection(int index) {
        return Math.min(index, getText().toString().length());
    }
    public void setFixedSelection(int index) {
        fixedSelection = minPossibleSelection(index);
        super.setSelection(fixedSelection);
    }
    @Override
    public void setSelection(int index) {
        fixedSelection = minPossibleSelection(fixedSelection);
        super.setSelection(fixedSelection);
    }
    @Override
    public void setSelection(int start, int end) {
        fixedSelection = minPossibleSelection(fixedSelection);
        super.setSelection(fixedSelection);
    }
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        fixedSelection = minPossibleSelection(fixedSelection);
        if (selStart != fixedSelection || selEnd != fixedSelection)
            super.setSelection(fixedSelection);
    }
}

