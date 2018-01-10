package com.gt.active_education;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import fragment.Explore_signup;
import fragment.SignUp_Fragment;

/**
 * Created by GT on 11/26/2017.
 */

public class Signup_Guest_All_Activity extends AppCompatActivity implements View.OnClickListener {

    private ExpandableRelativeLayout exp_student,exp_partner,exp_school,exp_collge,exp_uni,exp_iti,exp_train,exp_coach;
    private RelativeLayout id_rltive_student,id_b_partner,id_school,id_college,id_uni,id_iti,id_coach,id_train;
    private FrameLayout id_fr_student,id_frm_back;
    private boolean bl=false;
    Bundle user=new Bundle();
    Bundle agent=new Bundle();
    Bundle b1=new Bundle();
    Bundle b2=new Bundle();
    Bundle b3=new Bundle();
    Bundle b4=new Bundle();
    Bundle b5=new Bundle();
    Bundle b6=new Bundle();

    //Explore_signup

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_signup__layout);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        id_fr_student=(FrameLayout)findViewById(R.id.id_fr_student);
        id_frm_back=(FrameLayout)findViewById(R.id.id_frm_back);id_frm_back.setOnClickListener(this);

        id_rltive_student=(RelativeLayout)findViewById(R.id.id_rltive_student);
        id_b_partner=(RelativeLayout)findViewById(R.id.id_b_partner);
        id_school=(RelativeLayout)findViewById(R.id.id_school);
        id_college=(RelativeLayout)findViewById(R.id.id_college);
        id_uni=(RelativeLayout)findViewById(R.id.id_uni);
        id_iti=(RelativeLayout)findViewById(R.id.id_iti);
        id_coach=(RelativeLayout)findViewById(R.id.id_coach);
        id_train=(RelativeLayout)findViewById(R.id.id_train);



        exp_student = (ExpandableRelativeLayout)findViewById(R.id.expandableLayout1_student);exp_student.collapse();
        exp_partner = (ExpandableRelativeLayout)findViewById(R.id.expandableLayout1_b_partner);exp_partner.collapse();
        exp_school = (ExpandableRelativeLayout)findViewById(R.id.expandableLayout1_school);exp_school.collapse();
        exp_collge = (ExpandableRelativeLayout)findViewById(R.id.expandableLayout1_college);exp_collge.collapse();
        exp_uni = (ExpandableRelativeLayout)findViewById(R.id.expandableLayout1_uni);exp_uni.collapse();
        exp_iti = (ExpandableRelativeLayout)findViewById(R.id.expandableLayout1_iti);exp_iti.collapse();
        exp_coach = (ExpandableRelativeLayout)findViewById(R.id.expandableLayout1_coach);exp_coach.collapse();
        exp_train = (ExpandableRelativeLayout)findViewById(R.id.expandableLayout1_train);exp_train.collapse();

        user.putString("type","user");
        agent.putString("type","agent");
        b1.putString("type","1");
        b2.putString("type","2");
        b3.putString("type","3");
        b4.putString("type","4");
        b5.putString("type","5");
        b6.putString("type","6");


        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Fragment frg_user=new SignUp_Fragment();frg_user.setArguments(user);
        fragmentTransaction.replace(R.id.id_fr_student,frg_user).commit();

        FragmentManager fragmentManager2=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction2=fragmentManager2.beginTransaction();
        Fragment frg_agent=new SignUp_Fragment();frg_agent.setArguments(agent);
        fragmentTransaction2.replace(R.id.id_fr_b_partner, frg_agent).commit();

        FragmentManager fragmentManager3=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction3=fragmentManager3.beginTransaction();
        Fragment frg_school=new Explore_signup();frg_school.setArguments(b1);
        fragmentTransaction3.replace(R.id.id_fr_school,frg_school).commit();

        FragmentManager fragmentManager4=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction4=fragmentManager4.beginTransaction();
        Fragment frg_college=new Explore_signup();frg_college.setArguments(b2);
        fragmentTransaction4.replace(R.id.id_fr_college,frg_college).commit();

        FragmentManager fragmentManager5=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction5=fragmentManager5.beginTransaction();
        Fragment frg_uni=new Explore_signup();frg_uni.setArguments(b3);
        fragmentTransaction5.replace(R.id.id_fr_uni,frg_uni).commit();

        FragmentManager fragmentManager6=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction6=fragmentManager6.beginTransaction();
        Fragment frg_iti=new Explore_signup();frg_iti.setArguments(b4);
        fragmentTransaction6.replace(R.id.id_fr_iti,frg_iti).commit();

        FragmentManager fragmentManager7=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction7=fragmentManager7.beginTransaction();
        Fragment frg_coach=new Explore_signup();frg_coach.setArguments(b5);
        fragmentTransaction7.replace(R.id.id_fr_coach,frg_coach).commit();

        FragmentManager fragmentManager8=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction8=fragmentManager8.beginTransaction();
        Fragment frg_train=new Explore_signup();frg_train.setArguments(b6);
        fragmentTransaction8.replace(R.id.id_fr_train,frg_train).commit();



        id_rltive_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Toast.makeText(Signup_Guest_Activity.this, "ckckckb  "+bl, Toast.LENGTH_SHORT).show();
                if(!bl)
                {
                    bl=true;
                    exp_student.expand();

                }else if(bl)
                { bl=false;
                    exp_student.collapse();

                }
            }
        });
        id_b_partner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Toast.makeText(Signup_Guest_Activity.this, "ckckckb  "+bl, Toast.LENGTH_SHORT).show();
                if(!bl)
                {
                    bl=true;
                    exp_partner.expand();

                }else if(bl)
                { bl=false;
                    exp_partner.collapse();

                }
            }
        });

        id_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Toast.makeText(Signup_Guest_Activity.this, "ckckckb  "+bl, Toast.LENGTH_SHORT).show();
                if(!bl)
                {
                    bl=true;
                    exp_school.expand();

                }else if(bl)
                { bl=false;
                    exp_school.collapse();

                }
            }
        });
        id_college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Toast.makeText(Signup_Guest_Activity.this, "ckckckb  "+bl, Toast.LENGTH_SHORT).show();
                if(!bl)
                {
                    bl=true;
                    exp_collge.expand();

                }else if(bl)
                { bl=false;
                    exp_collge.collapse();

                }
            }
        });

        id_uni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Toast.makeText(Signup_Guest_Activity.this, "ckckckb  "+bl, Toast.LENGTH_SHORT).show();
                if(!bl)
                {
                    bl=true;
                    exp_uni.expand();

                }else if(bl)
                { bl=false;
                    exp_uni.collapse();

                }
            }
        });
        id_iti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Toast.makeText(Signup_Guest_Activity.this, "ckckckb  "+bl, Toast.LENGTH_SHORT).show();
                if(!bl)
                {
                    bl=true;
                    exp_iti.expand();

                }else if(bl)
                { bl=false;
                    exp_iti.collapse();

                }
            }
        });

        id_coach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Toast.makeText(Signup_Guest_Activity.this, "ckckckb  "+bl, Toast.LENGTH_SHORT).show();
                if(!bl)
                {
                    bl=true;
                    exp_coach.expand();

                }else if(bl)
                { bl=false;
                    exp_coach.collapse();

                }
            }
        });
        id_train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Toast.makeText(Signup_Guest_Activity.this, "ckckckb  "+bl, Toast.LENGTH_SHORT).show();
                if(!bl)
                {
                    bl=true;
                    exp_train.expand();

                }else if(bl)
                { bl=false;
                    exp_train.collapse();

                }
            }
        });



    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_frm_back:
                finish();
                break;
        }
    }
}
