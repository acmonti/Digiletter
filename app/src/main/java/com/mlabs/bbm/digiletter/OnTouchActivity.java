package com.mlabs.bbm.digiletter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class OnTouchActivity extends AppCompatActivity{
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

        tX1.setKeyListener(null);
        tX2.setKeyListener(null);
        tY1.setKeyListener(null);
        tY2.setKeyListener(null);
        tDiffX.setKeyListener(null);
        tDiffY.setKeyListener(null);
        tQuad.setKeyListener(null);
        tMotionX.setKeyListener(null);
        tMotionY.setKeyListener(null);

        imageLogo = (ImageView) findViewById(R.id.imageView);

        imageLogo.setOnTouchListener(new View.OnTouchListener() {
            float x1,y1,x2,y2;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                String actionX = "";
                String actionY = "";
                String quadrant = "";

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        y1 = event.getY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        float X = imageLogo.getRight()/2;
                        float Y = imageLogo.getBottom()/2;

                        x2 = event.getX();
                        y2 = event.getY();

                        actionX = "";
                        actionY = "";
                        quadrant = "";

                        if (x1<x2){
                            actionX = "Swiped Right ";
                        }
                        if (x1>x2){
                            actionX = "Swiped Left ";
                        }
                        if (y1<y2){
                            actionY = "Swiped Down ";
                        }
                        if (y1>y2)
                        {
                            actionY = "Swiped Up ";
                        }

                        if(x2>X && y2>Y){
                            quadrant = "Quadrant 4";
                        }
                        if(x2<X && y2>Y){
                            quadrant = "Quadrant 3";
                        }
                        if(x2<X && y2<Y){
                            quadrant = "Quadrant 2";
                        }
                        if(x2>X && y2<Y){
                            quadrant = "Quadrant 1";
                        }

                        tX1.setText(x1 + "");
                        tY1.setText(y1 + "");
                        tX2.setText(x2 + "");
                        tY2.setText(y2 + "");
                        tDiffX.setText((Math.abs(x2-x1))+"");
                        tDiffY.setText((Math.abs(y2-y1))+"");
                        tMotionX.setText(actionX);
                        tMotionY.setText(actionY);
                        tQuad.setText(quadrant);
                }
                return  false;
            }

        });
    }

}



