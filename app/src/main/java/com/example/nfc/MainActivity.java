package com.example.nfc;

import static com.example.nfc.R.id.ActivateButton;
import static com.example.nfc.R.id.nfc_contents;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {
    public static final String Error_Detected="No NFC tag Detected";
    public static final String Write_Success="Text Written Successfully";
    public static final String Write_Error="Error during Writing!";
    NfcAdapter adapter;
    PendingIntent pendingIntent;
    IntentFilter writingTagFilters[];
    boolean writeMode;
    Tag myTag;
    Context context;
    TextView edit_message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_message=(TextView) findViewById(R.id.edit_message);
        TextView nfc_contents=findViewById(R.id.nfc_contents);
        Button ActivateButton =findViewById(R.id.ActivateButton);
        context=this;
        ActivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(myTag==null)
                        Toast.makeText(context,Error_Detected,Toast.LENGTH_LONG).show();
                    else {
                        write("PlainText|" + edit_message.getText().toString(), myTag);
                        Toast.makeText(context, Write_Success, Toast.LENGTH_LONG).show();
                    }

                }catch (IOException e){
                    Toast.makeText(context,Write_Error,Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }catch (FormatException e){
                    Toast.makeText(context,Write_Error,Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        adapter=NfcAdapter.getDefaultAdapter(this);
        if (adapter==null){
            Toast.makeText(this,"This device doesn't support NFC",Toast.LENGTH_SHORT).show();
            finish();
        }
        readFromIntent(getIntent());
        pendingIntent=PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_IMMUTABLE);
        IntentFilter tagDetected=new IntentFilter(adapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writingTagFilters=new IntentFilter[]{tagDetected};

    }
    private void readFromIntent(Intent intent){
        String action=intent.getAction();
        if(adapter.ACTION_TAG_DISCOVERED.equals(action) || adapter.ACTION_TAG_DISCOVERED.equals(action) || adapter.ACTION_TAG_DISCOVERED.equals(action)){
            Parcelable[] rawMsgs=intent.getParcelableArrayExtra(adapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs=null;
            if(rawMsgs!=null){
                msgs=new NdefMessage[rawMsgs.length];
                for(int i=0;i<rawMsgs.length;i++){
                    msgs[i]=(NdefMessage) rawMsgs[i];
                }
            }
            buildTagViews(msgs);
        }
    }
    private void buildTagViews (NdefMessage [] msgs) {
        if (msgs == null || msgs.length == 0)return;
        String text = " ";
        String tagId = new String(msgs[0].getRecords()[0].getType());
        byte[] payload = msgs[0].getRecords()[0].getPayload();
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16"; // Get the Text Encoding
        int languageCodeLength = payload[0] & 0063; // Get the Language Code, e.g. "en"
// String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
        try{
            text=new String(payload,languageCodeLength+1,payload.length-languageCodeLength-1,textEncoding );

        }
        catch (UnsupportedEncodingException e){
            Log.e("UnsupportedEncoding",e.toString());
        }
        BreakIterator nfc_contents = null;
        nfc_contents.setText("NFC Contents: "+text);
    }
    private void write(String text, Tag tag) throws FormatException, IOException {
        NdefRecord[] records = {createRecord(text)};
        NdefMessage message = new NdefMessage(records);
        // Get an instance of Ndef for the tag.
        Ndef ndef = Ndef.get(tag);
        //Enable I / 0
        ndef.connect();
        //Write the message
        ndef.writeNdefMessage(message);
        //Close the connection
        ndef .close();
    }
    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang = "en";
        byte[] textBytes = text.getBytes();
        byte[] langBytes = lang.getBytes("US-ASCII");
        int langLength = langBytes.length;
        int textLength = textBytes.length;
        byte[] payload = new byte[1 + langLength + textLength];
        // set status byte (see NDEF spec for actual bits)
        payload[0] = (byte) langLength;
        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1, langLength);
        System.arraycopy(textBytes, 0, payload,1 + langLength, textLength);
        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);
        return recordNFC;
    }
    protected void onNewIntent (Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        readFromIntent(intent);
        if (adapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
    }
            @Override
            public void onPause() {
                super.onPause();
                WriteModeoff();
            }
            @Override
                public void onResume (){
                super. onResume ();
                WriteModeon();
            }
    private void WriteModeon() {
        writeMode = true;
        adapter.enableForegroundDispatch(this, pendingIntent, writingTagFilters, null);
    }
    private void WriteModeoff() {
        writeMode = false;
        adapter.disableForegroundDispatch(this);
    }
}