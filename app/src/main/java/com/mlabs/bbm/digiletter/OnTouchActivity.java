package com.mlabs.bbm.digiletter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class OnTouchActivity extends AppCompatActivity{
    float x1, y1 , x2, y2, a, b;
    String msg_Quad = "", msg_XM="", msg_YM="";
    ImageView imageLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ontouch);

        final TextView tX1 = (TextView) findViewById(R.id.txt_X1);
        final TextView tX2 = (TextView) findViewById(R.id.txt_X2);
        final TextView tY1 = (TextView) findViewById(R.id.txt_Y1);
        final TextView tY2 = (TextView) findViewById(R.id.txt_Y2);
        final TextView tDiffX = (TextView) findViewById(R.id.txt_DifferenceX);
        final TextView tDiffY = (TextView) findViewById(R.id.txt_DifferenceY);
        final TextView tQuad = (TextView) findViewById(R.id.txt_Quadrant);
        final TextView tMotionX = (TextView) findViewById(R.id.txt_MotionX);
        final TextView tMotionY = (TextView) findViewById(R.id.txt_MotionY);

        imageLogo = (ImageView) findViewById(R.id.imageView);

        imageLogo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        y1 = event.getY();
                        tX1.setText(x1 + "");
                        tY1.setText(y1 + "");
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        y2 = event.getY();
                        tX2.setText(x2 + "");
                        tY2.setText(y2 + "");

                        a = x1-x2;
                        b = y1-y2;
                        tDiffX.setText(Math.abs(a) + "");
                        tDiffY.setText(Math.abs(b) + "");

                        if (a<0 & b>0){msg_Quad = "1st Quadrant";}
                        if (a>0 & b>0){msg_Quad = "2nd Quadrant";}
                        if (a>0 & b<0){msg_Quad = "3rd Quadrant";}
                        if (a<0 & b<0){msg_Quad = "4th Quadrant";}

                        if (x1 > x2){msg_XM +="Swiped Left ";}
                        if (x1 < x2){msg_XM +="Swiped Right ";}
                        if (y1 > y2){msg_YM +="Swiped Up";}
                        if (y1 < y2){msg_YM +="Swiped Bottom";}

                        tQuad.setText(msg_Quad);
                        msg_Quad="";
                        tMotionX.setText(msg_XM);
                        msg_XM="";
                        tMotionY.setText(msg_YM);
                        msg_YM="";
                }return true;
            }
        });
    }
}



